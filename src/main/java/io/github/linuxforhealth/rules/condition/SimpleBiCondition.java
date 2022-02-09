/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.condition;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.linuxforhealth.rules.api.Condition;
import io.github.linuxforhealth.rules.condition.predicate.RuleBiPredicate;
import io.github.linuxforhealth.rules.condition.variable.Variable;
import io.github.linuxforhealth.rules.fact.DataValues;

public class SimpleBiCondition implements Condition {
  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleBiCondition.class);


  private Variable factVariable;
  private Object matchConstant;
  private RuleBiPredicate conditionOperator;



  public SimpleBiCondition(Variable var1, RuleBiPredicate conditionOperator, Object var2) {
    this.factVariable = var1;
    this.matchConstant = var2;
    this.conditionOperator = conditionOperator;

  }



  public SimpleBiCondition(Variable var1, String predicate, String var2) {
    this.factVariable = var1;
    this.conditionOperator = RuleBiPredicate.valueOf(predicate);
    this.matchConstant = getObject(var2, this.conditionOperator.getKlassU());

  }



  private Object getObject(String var2, Class<?> klass) {
    if (Integer.class == klass) {
      return NumberUtils.createInteger(var2);
    } else if (Long.class == klass) {
      return NumberUtils.createLong(var2);
    } else if (Collections.class == klass) {
      String[] values = StringUtils.split(var2, ",");
      return Arrays.asList(values);
    } else {
      return var2;
    }


  }



  @Override
  public boolean evaluate(DataValues attributes) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Evaluating condition {} for attributes {} ", this, attributes);
    }

    List<Object> var1Values =
        this.factVariable.extractVariableValue(attributes, conditionOperator.getKlassT());

    if (matchConstant != null
        && !conditionOperator.getKlassU().isAssignableFrom(matchConstant.getClass())) {
      throw new IllegalStateException(
          "Value type for provider camparision  does not match value type required by the Condition operator. Expected type:"
              + conditionOperator.getKlassT() + " actual type: "
              + ClassUtils.getPackageCanonicalName(matchConstant, null));
    }
    boolean result = false;

    if (matchConstant != null && conditionOperator != null) {
      result = var1Values.stream()
          .anyMatch(a -> conditionOperator.getPredicate().test(a, matchConstant));
    }
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("returning result  {} for condition {} for attributes {} ", result, this,
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
    return "SimpleBiCondition [factVariable=" + factVariable + ", matchConstant=" + matchConstant
        + ", conditionOperator=" + conditionOperator + "]";
  }


}
