/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.condition;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.linuxforhealth.rules.api.Condition;
import io.github.linuxforhealth.rules.condition.predicate.RulePredicate;
import io.github.linuxforhealth.rules.condition.variable.Variable;
import io.github.linuxforhealth.rules.fact.DataValues;

public class SimpleCondition implements Condition {
  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCondition.class);

  private Variable factVariable;
  private RulePredicate conditionOperator;



  public SimpleCondition(Variable factVariable, RulePredicate conditionOperator) {
    this.factVariable = factVariable;
    this.conditionOperator = conditionOperator;

  }



  public SimpleCondition(Variable varible, String operator) {
    this(varible, RulePredicate.valueOf(operator));
  }



  @Override
  public boolean evaluate(DataValues attributes) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.info("Evaluating condition {} for attributes {} ", this, attributes);
    }
    List<Object> var1Values =
        this.factVariable.extractVariableValue(attributes, conditionOperator.getKlassT());

    boolean result = false;

      if (var1Values.isEmpty()) {
        result = conditionOperator.getPredicate().test(null);
      } else {
        result = var1Values.stream().anyMatch(a -> conditionOperator.getPredicate().test(a));

      }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.info("returning result  {} for condition {} for attributes {} ", result, this,
          attributes);
    }
    return result;
  }



  @Override
  public ConditionType getType() {
    return ConditionType.SIMPLE;
  }



  @Override
  public String toString() {
    return "SimpleCondition [factVariable=" + factVariable + ", conditionOperator="
        + conditionOperator + "]";
  }



}
