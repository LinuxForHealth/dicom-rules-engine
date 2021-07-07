/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.condition;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * ENUM class that defines different composition conditions.
 * 
 * @author pbhallam@us.ibm.com
 *
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public enum CompositionType {
  AND, // "All (AND)")
  OR, // ("Any (OR)")
  NAND, // ("Not all (NOT AND)")
  NOR; // ("None (NOT OR)")

}
