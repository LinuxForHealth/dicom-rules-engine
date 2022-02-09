/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.api;

import io.github.linuxforhealth.rules.condition.CompositeCondition;
import io.github.linuxforhealth.rules.condition.CompositionType;
import io.github.linuxforhealth.rules.condition.ConditionType;
import io.github.linuxforhealth.rules.fact.DataValues;

/**
 * 
 * Interface that represents a condition. The implementing class should define how to resolve the
 * condition using the context values.
 *
 * @author pbhallam
 */

public interface Condition {
  /**
   * Returns True if the condition is satisfied.
   * 
   * @param contextValues - Map of String, {@link EvaluationResult}
   * @return boolean
   */
  boolean evaluate(DataValues attributes);

  ConditionType getType();

  /**
   * Create a new Condition that returns true only if both of the specified Conditions are true.
   *
   *
   * @param condition1 the first Condition, may not be null
   * @param condition2 the second Condition, may not be null
   * @return the <code>and</code> Condition
   * @throws NullPointerException if either Condition is null
   * 
   */
  default Condition andCondition(final Condition condition) {
    return new CompositeCondition(this, condition, CompositionType.AND);
  }


  /**
   * Create a new Condition that returns true only if either of the specified Conditions are true.
   *
   *
   * @param condition1 the first Condition, may not be null
   * @param condition2 the second Condition, may not be null
   * @return the <code>and</code> Condition
   * @throws NullPointerException if either Condition is null
   * 
   */
  default Condition orCondition(final Condition condition) {
    return new CompositeCondition(this, condition, CompositionType.OR);
  }



}
