/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.conditions.predicate;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import io.github.linuxforhealth.rules.api.Condition;
import io.github.linuxforhealth.rules.condition.SimpleBiCondition;
import io.github.linuxforhealth.rules.condition.predicate.RuleBiPredicate;
import io.github.linuxforhealth.rules.condition.variable.NestedVariable;
import io.github.linuxforhealth.rules.fact.DataValues;
import io.github.linuxforhealth.rules.fact.DicomAttributeFact;
import io.github.linuxforhealth.rules.fact.MapValueType;
import io.github.linuxforhealth.rules.fact.StringValueType;
import io.github.linuxforhealth.rules.fact.ValueType;



class PredicateForMapTypeTest {

  @Test
  public void test_contain_operator_evaluated_to_true() {
    Map<String, ValueType> attrs = new HashMap<>();
    attrs.put("(0020,1207)", new StringValueType("ANY_ACCESSION_NUMBER"));
    DicomAttributeFact fact =
        new DicomAttributeFact("", generateFact("(0020,1206)", new MapValueType(attrs)));
    Condition tDicomRuleCondition = new SimpleBiCondition(
        new NestedVariable("(0020,1206).(0020,1207)"), RuleBiPredicate.CONTAINS, "ANY_ACCESSION");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();


  }



  @Test
  public void test_contain_operator_evaluated_to_false() {
    Map<String, ValueType> attrs = new HashMap<>();
    attrs.put("(0020,1208)", new StringValueType("ANY_ACCESSION_NUMBER"));
    DicomAttributeFact fact =
        new DicomAttributeFact("", generateFact("(0020,1206)", new MapValueType(attrs)));
    Condition tDicomRuleCondition = new SimpleBiCondition(
        new NestedVariable("(0020,1206).(0020,1207)"), RuleBiPredicate.CONTAINS, "ANY_ACCESSION");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isFalse();


  }


  @Test
  public void exception_happens_when_nexted_variable_but_attribute_value_is_string_type_for_parent() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new StringValueType("ANY_ACCESSION")));
    Condition tDicomRuleCondition = new SimpleBiCondition(
        new NestedVariable("(0020,1206).(0020,1207)"), RuleBiPredicate.CONTAINS, "ANY_ACCESSION");
    Assertions.assertThatIllegalStateException()
        .isThrownBy(() -> tDicomRuleCondition.evaluate(fact.getValue()));



  }

  private DataValues generateFact(String key, ValueType value) {
    DataValues attrs = new DataValues();
    attrs.addValue(key, value);
    return attrs;

  }



}
