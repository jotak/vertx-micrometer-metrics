package io.vertx.micrometer;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static io.vertx.micrometer.RegistryInspector.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Joel Takvorian
 */
@RunWith(VertxUnitRunner.class)
public class VertxDatagramSocketMetricsTest {

  private Vertx vertx;

  @After
  public void tearDown(TestContext context) {
    vertx.close(context.asyncAssertSuccess());
  }

  @Test
  public void shouldReportDatagramMetrics(TestContext context) {
    vertx = Vertx.vertx(new VertxOptions().setMetricsOptions(new MicrometerMetricsOptions()
        .setPrometheusOptions(new VertxPrometheusOptions().setEnabled(true))
      .addLabels(Label.LOCAL)
      .setEnabled(true)))
      .exceptionHandler(context.exceptionHandler());

    String datagramContent = "some text";
    int loops = 5;

    // Setup server
    int port = 9192;
    String host = "localhost";
    Async receiveLatch = context.async(loops);
    Async listenLatch = context.async();
    vertx.createDatagramSocket().listen(port, host, context.asyncAssertSuccess(so -> {
      so.handler(packet -> receiveLatch.countDown());
      listenLatch.complete();
    }));
    listenLatch.awaitSuccess(15000);

    // Send to server
    DatagramSocket client = vertx.createDatagramSocket();
    for (int i = 0; i < loops; i++) {
      client.send(datagramContent, port, host, context.asyncAssertSuccess());
    }
    receiveLatch.awaitSuccess(15000);

    waitForValue(vertx, context, "vertx.datagram.bytes.written[]$COUNT", value -> value.intValue() == 5);
    List<RegistryInspector.Datapoint> datapoints = listDatapoints(startsWith("vertx.datagram."));
    assertThat(datapoints).containsOnly(
      dp("vertx.datagram.bytes.written[]$COUNT", 5),
      dp("vertx.datagram.bytes.written[]$TOTAL", 45),  // 45 = size("some text") * loops
      dp("vertx.datagram.bytes.read[local=localhost:9192]$COUNT", 5),
      dp("vertx.datagram.bytes.read[local=localhost:9192]$TOTAL", 45));
  }

  @Test
  public void shouldReportInCompatibilityMode(TestContext context) {
    vertx = Vertx.vertx(new VertxOptions().setMetricsOptions(new MicrometerMetricsOptions()
      .setPrometheusOptions(new VertxPrometheusOptions().setEnabled(true))
      .setMetricsNaming(MetricsNaming.v3Names())
      .setEnabled(true)))
      .exceptionHandler(context.exceptionHandler());

    String datagramContent = "some text";

    // Setup server
    int port = 9192;
    String host = "localhost";
    Async receiveLatch = context.async();
    Async listenLatch = context.async();
    vertx.createDatagramSocket().listen(port, host, context.asyncAssertSuccess(so -> {
      so.handler(packet -> receiveLatch.countDown());
      listenLatch.complete();
    }));
    listenLatch.awaitSuccess(15000);

    // Send to server
    DatagramSocket client = vertx.createDatagramSocket();
    client.send(datagramContent, port, host, context.asyncAssertSuccess());
    receiveLatch.awaitSuccess(15000);

    waitForValue(vertx, context, "vertx.datagram.bytesSent[]$COUNT", value -> value.intValue() == 1);
    List<RegistryInspector.Datapoint> datapoints = listDatapoints(startsWith("vertx.datagram."));
    assertThat(datapoints).containsOnly(
      dp("vertx.datagram.bytesSent[]$COUNT", 1),
      dp("vertx.datagram.bytesSent[]$TOTAL", 9),
      dp("vertx.datagram.bytesReceived[]$COUNT", 1),
      dp("vertx.datagram.bytesReceived[]$TOTAL", 9));
  }
}
