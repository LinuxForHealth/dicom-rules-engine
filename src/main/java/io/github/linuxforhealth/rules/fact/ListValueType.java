/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.fact;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ListValueType<T> implements ValueType {


  private List<T> values;

  public ListValueType(List<T> values) {

    this.values = new ArrayList<>();
    if (values != null) {
      this.values.addAll(values);
    }


  }


  @Override
  public List<T> getValue() {

    return values;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }


}
