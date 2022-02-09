/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.condition;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import io.github.linuxforhealth.rules.fact.DataValues;
import io.github.linuxforhealth.rules.fact.ListValueType;
import io.github.linuxforhealth.rules.fact.MapValueType;
import io.github.linuxforhealth.rules.fact.ValueType;

public class Specification {

  private String spec;
  private int index = -1;


  public Specification(String spec) {
    this(spec, -1);
  }

  public Specification(String spec, int index) {
    this.spec = spec;
    this.index = index;
  }



  public String getSpec() {
    return this.spec;
  }



  public int getIndex() {
    return this.index;
  }


  public Optional<ValueType> getAttribute(DataValues attributes, String group) {
    DataValues context = attributes;
    if (group != null) {
      MapValueType map = attributes.getGroup(group).orElse(new MapValueType(new HashMap<>()));
      context = new DataValues(map.getValue());

    }

    Optional<ValueType> attr = context.getValue(this.spec);
    if (attr.isPresent() && attr.get() instanceof ListValueType) {
      List<?> listData = (List<?>) attr.get().getValue();
      if (ValueType.class.isAssignableFrom(listData.getClass().getTypeParameters().getClass())
          && !listData.isEmpty()) {
        if (listData.size() > this.index) {
          ValueType a = (ValueType) listData.get(this.index);
          return Optional.ofNullable(a);
        } else {
          throw new IllegalArgumentException("No enough elements to fetch the value");
        }
      }

    }

    return attr;
  }

  @Override
  public String toString() {
    return "Specification [spec=" + spec + ", index=" + index + "]";
  }



}
