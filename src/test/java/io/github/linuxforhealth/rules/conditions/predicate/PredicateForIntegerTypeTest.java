/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.conditions.predicate;

import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import io.github.linuxforhealth.rules.api.Condition;
import io.github.linuxforhealth.rules.condition.SimpleBiCondition;
import io.github.linuxforhealth.rules.condition.predicate.RuleBiPredicate;
import io.github.linuxforhealth.rules.condition.variable.SimpleVariable;
import io.github.linuxforhealth.rules.fact.DataValues;
import io.github.linuxforhealth.rules.fact.DicomAttributeFact;
import io.github.linuxforhealth.rules.fact.NumberValueType;
import io.github.linuxforhealth.rules.fact.ValueType;



class PredicateForIntegerTypeTest {

  @Test
  void test_greater_than_operator_evaluated() {
    DicomAttributeFact fact =
        new DicomAttributeFact("", generateFact("(0020,1206)", new NumberValueType(2)));
    Condition tDicomRuleCondition =
        new SimpleBiCondition(new SimpleVariable("(0020,1206)"), RuleBiPredicate.GREATER_THAN, 2);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isEqualTo(false);

  }


  @Test
  void test_greater_than_operator_evaluated_to_true() {
    DicomAttributeFact fact =
        new DicomAttributeFact("", generateFact("(0020,1206)", new NumberValueType(6)));
    Condition tDicomRuleCondition =
        new SimpleBiCondition(new SimpleVariable("(0020,1206)"), RuleBiPredicate.GREATER_THAN, 2);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isEqualTo(true);

  }



  @Test
  void test_greater_than_operator_evaluated_to_false_if_attribute_is_missing() {
    DicomAttributeFact fact =
        new DicomAttributeFact("", generateFact("(0020,1207)", new NumberValueType(2)));
    Condition tDicomRuleCondition =
        new SimpleBiCondition(new SimpleVariable("(0020,1206)"), RuleBiPredicate.GREATER_THAN, 2);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isEqualTo(false);

  }


  @Test
  void test_greater_than_operator_with_invalid_input() {
    DicomAttributeFact fact =
        new DicomAttributeFact("", generateFact("(0020,1206)", new NumberValueType(2.9)));
    Condition tDicomRuleCondition =
        new SimpleBiCondition(new SimpleVariable("(0020,1206)"), RuleBiPredicate.GREATER_THAN, 2);
    Assertions.assertThatIllegalStateException()
        .isThrownBy(() -> tDicomRuleCondition.evaluate(fact.getValue()));

  }


  private DataValues generateFact(String key, ValueType value) {
    DataValues attrs = new DataValues();
    attrs.addValue(key, value);
    return attrs;

  }


}
