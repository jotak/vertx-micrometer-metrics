/*
 * Copyright (c) 2011-2017 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 *     The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 *     The Apache License v2.0 is available at
 *     http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.monitoring;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.metrics.MetricsOptions;
import io.vertx.core.spi.VertxMetricsFactory;
import io.vertx.core.spi.metrics.VertxMetrics;

/**
 * @author Joel Takvorian
 */
public class VertxMetricsFactoryImpl implements VertxMetricsFactory {
  @Override
  public VertxMetrics metrics(Vertx vertx, VertxOptions vertxOptions) {
    MetricsOptions metricsOptions = vertxOptions.getMetricsOptions();
    if (metricsOptions instanceof VertxMonitoringOptions) {
      return new VertxMonitoring(vertx, (VertxMonitoringOptions) metricsOptions);
    } else {
      return new VertxMonitoring(vertx, new VertxMonitoringOptions(metricsOptions.toJson()));
    }
  }

  @Override
  public MetricsOptions newOptions() {
    return new VertxMonitoringOptions();
  }
}
