/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.conditions.predicate;

import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import io.github.linuxforhealth.rules.api.RuleFact;
import io.github.linuxforhealth.rules.condition.Condition;
import io.github.linuxforhealth.rules.condition.SimpleBiCondition;
import io.github.linuxforhealth.rules.condition.variable.SimpleVariable;
import io.github.linuxforhealth.rules.fact.ContextValues;
import io.github.linuxforhealth.rules.fact.NumberValueType;
import io.github.linuxforhealth.rules.fact.ValueType;


public class PredicateForIntegerTypeTest {

  @Test
  public void test_greater_than_operator_evaluated() {
    RuleFact fact = new RuleFact(generateFact("(0020,1206)", new NumberValueType(2)));
    Condition tDicomRuleCondition =
        new SimpleBiCondition(new SimpleVariable("(0020,1206)"), RuleBiPredicate.GREATER_THAN, 2);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getData());
    assertThat(tConditionEvaluated).isEqualTo(false);

  }


  @Test
  public void test_greater_than_operator_evaluated_to_true() {
    RuleFact fact = new RuleFact(generateFact("(0020,1206)", new NumberValueType(6)));
    Condition tDicomRuleCondition =
        new SimpleBiCondition(new SimpleVariable("(0020,1206)"), RuleBiPredicate.GREATER_THAN, 2);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getData());
    assertThat(tConditionEvaluated).isEqualTo(true);

  }



  @Test
  public void test_greater_than_operator_evaluated_to_false_if_attribute_is_missing() {
    RuleFact fact = new RuleFact(generateFact("(0020,1207)", new NumberValueType(2)));
    Condition tDicomRuleCondition =
        new SimpleBiCondition(new SimpleVariable("(0020,1206)"), RuleBiPredicate.GREATER_THAN, 2);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getData());
    assertThat(tConditionEvaluated).isEqualTo(false);

  }


  @Test
  public void test_greater_than_operator_with_invalid_input() {
    RuleFact fact = new RuleFact(generateFact("(0020,1206)", new NumberValueType(2.9)));
    Condition tDicomRuleCondition =
        new SimpleBiCondition(new SimpleVariable("(0020,1206)"), RuleBiPredicate.GREATER_THAN, 2);
    Assertions.assertThatIllegalStateException()
        .isThrownBy(() -> tDicomRuleCondition.evaluate(fact.getData()));

  }


  private ContextValues generateFact(String key, ValueType value) {
    ContextValues attrs = new ContextValues();
    attrs.addValue(key, value);
    return attrs;

  }


}
