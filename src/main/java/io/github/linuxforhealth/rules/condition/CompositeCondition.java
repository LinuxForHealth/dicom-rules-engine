/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.linuxforhealth.rules.api.Condition;
import io.github.linuxforhealth.rules.fact.DataValues;

public class CompositeCondition implements Condition {
  private static final Logger LOGGER = LoggerFactory.getLogger(CompositeCondition.class);

  private Condition condition1;
  private Condition condition2;
  private CompositionType conditionOperator;

  public CompositeCondition(Condition condition1, Condition condition2,
      CompositionType conditionOperator) {
    this.condition1 = condition1;
    this.condition2 = condition2;
    this.conditionOperator = conditionOperator;
  }



  @Override
  public boolean evaluate(DataValues attributes) {
    boolean eval;
    if (CompositionType.AND == conditionOperator) {
      eval = condition1.evaluate(attributes) && condition2.evaluate(attributes);

    } else if (CompositionType.OR == conditionOperator) {
      eval = condition1.evaluate(attributes) || condition2.evaluate(attributes);
    } else {
      throw new IllegalArgumentException(
          "Unpupported condition operator, conditionOperator: " + conditionOperator);

    }
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("CompositeCondition {} evaluated to {}", this, eval);
    }
    return eval;
  }



  @Override
  public ConditionType getType() {
    return ConditionType.COMPOSITE_CONDITION;
  }



  @Override
  public String toString() {
    return "CompositeCondition [condition1=" + condition1 + ", condition2=" + condition2
        + ", conditionOperator=" + conditionOperator + "]";
  }




}
