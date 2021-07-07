/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.fact;

public class NumberValueType implements ValueType {


  private Number value;

  public NumberValueType(Number value) {
    this.value = value;
  }


  @Override
  public Number getValue() {
    return value;
  }


}
