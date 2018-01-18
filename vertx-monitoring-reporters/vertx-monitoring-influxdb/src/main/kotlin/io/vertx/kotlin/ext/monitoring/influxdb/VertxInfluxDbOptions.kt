package io.vertx.kotlin.ext.monitoring.influxdb

import io.vertx.monitoring.influxdb.VertxInfluxDbOptions
import io.vertx.core.http.HttpClientOptions
import io.vertx.monitoring.common.MetricsCategory
import io.vertx.monitoring.influxdb.AuthenticationOptions

/**
 * A function providing a DSL for building [io.vertx.monitoring.influxdb.VertxInfluxDbOptions] objects.
 *
 * Vert.x InfluxDb monitoring configuration.
 *
 * @param authenticationOptions  Set the options for authentication.
 * @param batchDelay
 * @param batchSize
 * @param database  Set the InfluxDb database. Defaults to <code>default</code>.
 * @param disabledMetricsCategories
 * @param enabled
 * @param gzipEnabled
 * @param host  Set the InfluxDb Metrics service host. Defaults to <code>localhost</code>.
 * @param httpHeaders  Set specific headers to include in HTTP requests.
 * @param httpOptions  Set the configuration of the InfluxDb Metrics HTTP client.
 * @param measurement  Set the InfluxDb measurement. Defaults to <code>vert.x</code>.
 * @param metricsBridgeAddress
 * @param metricsBridgeEnabled
 * @param metricsServiceUri  Set the InfluxDb Metrics service URI. Defaults to <code>/InfluxDb/metrics</code>. This can be useful if you host the InfluxDb server behind a proxy and manipulate the default service URI.
 * @param port  Set the InfluxDb Metrics service port.  Defaults to <code>8080</code>.
 * @param prefix
 * @param schedule
 *
 * <p/>
 * NOTE: This function has been automatically generated from the [io.vertx.monitoring.influxdb.VertxInfluxDbOptions original] using Vert.x codegen.
 */
fun VertxInfluxDbOptions(
  authenticationOptions: io.vertx.monitoring.influxdb.AuthenticationOptions? = null,
  batchDelay: Int? = null,
  batchSize: Int? = null,
  database: String? = null,
  disabledMetricsCategories: Iterable<MetricsCategory>? = null,
  enabled: Boolean? = null,
  gzipEnabled: Boolean? = null,
  host: String? = null,
  httpHeaders: io.vertx.core.json.JsonObject? = null,
  httpOptions: io.vertx.core.http.HttpClientOptions? = null,
  measurement: String? = null,
  metricsBridgeAddress: String? = null,
  metricsBridgeEnabled: Boolean? = null,
  metricsServiceUri: String? = null,
  port: Int? = null,
  prefix: String? = null,
  schedule: Int? = null): VertxInfluxDbOptions = io.vertx.monitoring.influxdb.VertxInfluxDbOptions().apply {

  if (authenticationOptions != null) {
    this.setAuthenticationOptions(authenticationOptions)
  }
  if (batchDelay != null) {
    this.setBatchDelay(batchDelay)
  }
  if (batchSize != null) {
    this.setBatchSize(batchSize)
  }
  if (database != null) {
    this.setDatabase(database)
  }
  if (disabledMetricsCategories != null) {
    this.setDisabledMetricsCategories(disabledMetricsCategories.toSet())
  }
  if (enabled != null) {
    this.setEnabled(enabled)
  }
  if (gzipEnabled != null) {
    this.setGzipEnabled(gzipEnabled)
  }
  if (host != null) {
    this.setHost(host)
  }
  if (httpHeaders != null) {
    this.setHttpHeaders(httpHeaders)
  }
  if (httpOptions != null) {
    this.setHttpOptions(httpOptions)
  }
  if (measurement != null) {
    this.setMeasurement(measurement)
  }
  if (metricsBridgeAddress != null) {
    this.setMetricsBridgeAddress(metricsBridgeAddress)
  }
  if (metricsBridgeEnabled != null) {
    this.setMetricsBridgeEnabled(metricsBridgeEnabled)
  }
  if (metricsServiceUri != null) {
    this.setMetricsServiceUri(metricsServiceUri)
  }
  if (port != null) {
    this.setPort(port)
  }
  if (prefix != null) {
    this.setPrefix(prefix)
  }
  if (schedule != null) {
    this.setSchedule(schedule)
  }
}

