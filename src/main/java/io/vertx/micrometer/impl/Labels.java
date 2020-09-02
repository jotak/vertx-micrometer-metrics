/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertx.micrometer.impl;

import io.micrometer.core.instrument.Tag;
import io.vertx.core.net.SocketAddress;
import io.vertx.micrometer.Label;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Joel Takvorian
 */
public final class Labels {
  private Labels() {
  }

  static String address(SocketAddress address) {
    return address(address, null);
  }

  static String address(SocketAddress address, String nameOverride) {
    if (address == null) {
      return "?";
    }
    if (nameOverride == null) {
      return address.toString();
    }
    SocketAddress addrOverride = address.port() >= 0 ? SocketAddress.inetSocketAddress(address.port(), nameOverride)
      : SocketAddress.domainSocketAddress(nameOverride);
    return addrOverride.toString();
  }

  static String getSide(boolean local) {
    return local ? "local" : "remote";
  }

  public static List<Tag> toTags(Label[] keys, String[] values) {
    if (keys.length == 0) {
      return Collections.emptyList();
    }
    List<Tag> tags = new ArrayList<>(keys.length);
    for (int i = 0; i < keys.length; i++) {
      if (values[i] != null) {
        String lowKey = keys[i].toString();
        tags.add(Tag.of(lowKey, values[i]));
      }
    }
    return tags;
  }
}
