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

package io.vertx.ext.monitoring.collector.impl;

import io.vertx.core.net.SocketAddress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Aggregates values from {@link NetClientMetricsImpl} instances and exposes metrics for collection.
 *
 * @author Thomas Segismont
 */
public class NetClientMetricsSupplier implements MetricSupplier {
  private final String baseName;
  private final Set<NetClientMetricsImpl> metricsSet = new CopyOnWriteArraySet<>();

  public NetClientMetricsSupplier(String prefix) {
    baseName = prefix + (prefix.isEmpty() ? "" : ".") + "vertx.net.client.";
  }

  @Override
  public List<DataPoint> collect() {
    long timestamp = System.currentTimeMillis();

    Map<SocketAddress, NetClientConnectionsMeasurements.Snapshot> values = new HashMap<>();

    for (NetClientMetricsImpl netClientMetrics : metricsSet) {
      netClientMetrics.getMeasurementsSnapshot().forEach((address, snapshot) -> {
        values.merge(address, snapshot, NetClientConnectionsMeasurements.Snapshot::merge);
      });
    }

    List<DataPoint> res = new ArrayList<>();

    values.forEach((address, snapshot) -> {
      String addressId = address.host() + ":" + address.port();
      res.add(new GaugePoint(baseName + addressId + ".connections", timestamp, snapshot.getConnections()));
      res.add(new CounterPoint(baseName + addressId + ".bytesReceived", timestamp, snapshot.getBytesReceived()));
      res.add(new CounterPoint(baseName + addressId + ".bytesSent", timestamp, snapshot.getBytesSent()));
      res.add(new CounterPoint(baseName + addressId + ".errorCount", timestamp, snapshot.getErrorCount()));
    });
    return res;
  }

  public void register(NetClientMetricsImpl netClientMetrics) {
    metricsSet.add(netClientMetrics);
  }

  public void unregister(NetClientMetricsImpl netClientMetrics) {
    metricsSet.remove(netClientMetrics);
  }
}