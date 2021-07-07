/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.fact;

public class StringValueType implements ValueType {


  private String value;

  public StringValueType(String value) {

    this.value = value;
  }


  @Override
  public String getValue() {
    return value;
  }


}
