/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.fact;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Preconditions;


public class DataValues implements ValueType {
  private static final Logger LOGGER = LoggerFactory.getLogger(DataValues.class);



  private Map<String, ValueType> values;

  public DataValues() {
    this(new HashMap<>());

  }

  public DataValues(Map<String, ValueType> values) {
    Preconditions.checkArgument(values != null, "input map of values caannot be null");
    this.values = new HashMap<>();
    this.values.putAll(values);

  }


  @Override
  public Map<String, ValueType> getValue() {
    return values;
  }


  public Optional<ValueType> getValue(String name) {
    Optional<ValueType> val = Optional.ofNullable(values.get(name));
    LOGGER.debug("Returning value: {} for {} ", val, name);
    return val;

  }

  public void addValue(String key, ValueType a) {
    values.put(key, a);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

  public Optional<MapValueType> getGroup(String name) {
    ValueType v = values.get(name);
    if (v instanceof MapValueType) {
      return Optional.ofNullable((MapValueType) v);
    }
    return Optional.empty();

  }

  public void addGroup(String groupName, MapValueType value) {
    Preconditions.checkArgument(values != null, "input map of values caannot be null");
    values.put(groupName, value);
  }

}
