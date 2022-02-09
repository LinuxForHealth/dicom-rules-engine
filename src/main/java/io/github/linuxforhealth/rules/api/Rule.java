/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.api;

public interface Rule<T> {

  String getName();

  String getGroupId();

  String getIdentifier();

  /**
   * This method implements the rule's condition(s). <strong>Implementations should handle any
   * runtime exception and return true/false accordingly</strong>
   *
   * @return true if the rule should be applied given the provided facts, false otherwise
   */
  RuleEvaluationResult evaluate(Fact<T> fact);



}
