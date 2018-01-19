/*
 * Copyright 2015 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */
package examples;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.docgen.Source;
import io.vertx.ext.web.Router;
import io.vertx.monitoring.common.MetricsCategory;
import io.vertx.monitoring.prometheus.VertxPrometheusOptions;
import io.vertx.monitoring.prometheus.impl.PrometheusVertxMetrics;

/**
 * @author Joel Takvorian
 */
@Source
@SuppressWarnings("unused")
public class MetricsExamples {

  Vertx vertx;

  public void setup() {
    Vertx vertx = Vertx.vertx(new VertxOptions().setMetricsOptions(
      new VertxPrometheusOptions().setEnabled(true)));
  }

  public void setupEmbeddedServer() {
    Vertx vertx = Vertx.vertx(new VertxOptions().setMetricsOptions(
      new VertxPrometheusOptions().setEnabled(true)
        .setEmbeddedServerOptions(new HttpServerOptions())));
  }

  public void setupEmbeddedServerWithOptions() {
    Vertx vertx = Vertx.vertx(new VertxOptions().setMetricsOptions(
      new VertxPrometheusOptions().setEnabled(true)
        .setEmbeddedServerOptions(new HttpServerOptions().setPort(8080))
        .setEmbeddedServerEndpoint("/metrics/vertx")));
  }

  public void setupBoundRouter() {
    Vertx vertx = Vertx.vertx(new VertxOptions().setMetricsOptions(
      new VertxPrometheusOptions().setEnabled(true)));

    // Later on, creating a router
    Router router = Router.router(vertx);
    router.route("/custom").handler(PrometheusVertxMetrics.createMetricsHandler());
  }

  public void setupBoundRouterWithCustomRegistry() {
    Vertx vertx = Vertx.vertx(new VertxOptions().setMetricsOptions(
      new VertxPrometheusOptions().setRegistryName("my registry").setEnabled(true)));

    // Later on, creating a router
    Router router = Router.router(vertx);
    router.route("/custom").handler(PrometheusVertxMetrics.createMetricsHandler("my registry"));
  }

  public void setupDisabledMetricsTypes() {
    Vertx vertx = Vertx.vertx(new VertxOptions().setMetricsOptions(
      new VertxPrometheusOptions()
        .addDisabledMetricsCategory(MetricsCategory.HTTP_CLIENT)
        .addDisabledMetricsCategory(MetricsCategory.NET_CLIENT)
        .setEnabled(true)));
  }
}