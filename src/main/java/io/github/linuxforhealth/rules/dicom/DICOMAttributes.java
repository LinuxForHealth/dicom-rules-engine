/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.dicom;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import io.github.linuxforhealth.rules.fact.ContextValues;
import io.github.linuxforhealth.rules.fact.ValueType;


public class DICOMAttributes extends ContextValues {

  private Map<String, ValueType> values;


  public DICOMAttributes() {
    this.values = new HashMap<>();
  }



  public Map<String, ValueType> getValues() {
    return new HashMap<>(values);
  }


  public Optional<ValueType> getValue(String name) {
    return Optional.ofNullable(values.get(name));

  }

  public void addValue(String key, ValueType a) {
    values.put(key, a);
  }


}
