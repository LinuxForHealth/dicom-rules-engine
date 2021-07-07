/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.fact;

public class NullAttribute implements ValueType {


  @Override
  public String getValue() {
    return null;
  }


}
