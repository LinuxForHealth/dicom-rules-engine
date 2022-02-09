/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.conditions.predicate;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import io.github.linuxforhealth.rules.api.Condition;
import io.github.linuxforhealth.rules.condition.SimpleBiCondition;
import io.github.linuxforhealth.rules.condition.SimpleCondition;
import io.github.linuxforhealth.rules.condition.predicate.RuleBiPredicate;
import io.github.linuxforhealth.rules.condition.predicate.RulePredicate;
import io.github.linuxforhealth.rules.condition.variable.SimpleVariable;
import io.github.linuxforhealth.rules.fact.DataValues;
import io.github.linuxforhealth.rules.fact.DicomAttributeFact;
import io.github.linuxforhealth.rules.fact.StringValueType;
import io.github.linuxforhealth.rules.fact.ValueType;



class PredicateForStringTypeTest {

  @Test
  public void test_contain_operator_evaluated() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new StringValueType("ANY_ACCESSION_NUMBER")));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.CONTAINS, "ANY_ACCESSION");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();


  }

  private DataValues generateFact(String key, ValueType value) {
    DataValues attrs = new DataValues();
    attrs.addValue(key, value);
    return attrs;

  }


  @Test
  public void test_contain_than_operator_with_invalid_input() {
    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new StringValueType("ANY_ACCESSION_NUMBER")));
    Condition tDicomRuleCondition =
        new SimpleBiCondition(new SimpleVariable("(0020,1206)"), RuleBiPredicate.CONTAINS, 4);
    Assertions.assertThatIllegalStateException()
        .isThrownBy(() -> tDicomRuleCondition.evaluate(fact.getValue()));


  }

  @Test
  public void test_contain_operator_with_empty_input() {
    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new StringValueType("ANY_ACCESSION_NUMBER")));
    Condition tDicomRuleCondition =
        new SimpleBiCondition(new SimpleVariable("(0020,1206)"), RuleBiPredicate.CONTAINS, "");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();


  }

  @Test
  public void test_equals_operator() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new StringValueType("ANY_ACCESSION")));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.EQUALS, "ANY_ACCESSION");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();



  }

  @Test
  public void test_equals_operator_false() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new StringValueType("ANY_ACCESSION_N")));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.EQUALS, "ANY_ACCESSION");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isFalse();

  }

  @Test
  public void test_start_with_operator() {


    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new StringValueType("ANY_ACCESSION_N")));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.STARTS_WITH, "ANY_ACCESSION");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();



  }

  @Test
  public void test_not_start_with_operator_false() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new StringValueType("ANY_ACCESSION_N")));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.NOT_STARTS_WITH, "ANY_ACCESSION");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isFalse();


  }

  @Test
  public void test_not_start_with_operator_true() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new StringValueType("TANY_ACCESSION")));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.NOT_STARTS_WITH, "ANY_ACCESSION");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();


  }

  @Test
  public void test_ends_with_operator() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new StringValueType("ANY_ACCESSION")));
    Condition tDicomRuleCondition =
        new SimpleBiCondition(new SimpleVariable("(0020,1206)"), RuleBiPredicate.ENDS_WITH, "ION");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();



  }

  @Test
  public void test_not_ends_with_operator_false_case() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new StringValueType("ANY_ACCESSION")));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.NOT_ENDS_WITH, "ION");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isFalse();



  }

  @Test
  public void test_not_ends_with_operator_true_case() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new StringValueType("ANY_ACCESSION")));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.NOT_ENDS_WITH, "IOG");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();



  }

  @Test
  public void test_not_contains_operator_false_case() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new StringValueType("ACCESSION_AANY")));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.NOT_CONTAINS, "ACCESSION");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isFalse();


  }

  @Test
  public void test_not_contains_operator_true_case() {
    DicomAttributeFact fact =
        new DicomAttributeFact("", generateFact("(0020,1206)", new StringValueType("ACCESSION")));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.NOT_CONTAINS, "NUM");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();



  }

  @Test
  public void test_in_equal_operator_true_case() {

    List<String> tModalityList = new ArrayList<String>();
    tModalityList.add("CT");
    tModalityList.add("US");
    tModalityList.add("SR");
    DicomAttributeFact fact =
        new DicomAttributeFact("", generateFact("(0020,1206)", new StringValueType("CT")));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.IN_EQUALS, tModalityList);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();



  }


  @Test
  public void test_in_equal_operator_false_case() {

    List<String> tModalityList = new ArrayList<String>();
    tModalityList.add("CT");
    tModalityList.add("US");
    tModalityList.add("SR");
    DicomAttributeFact fact =
        new DicomAttributeFact("", generateFact("(0020,1206)", new StringValueType("TCT")));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.IN_EQUALS, tModalityList);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isFalse();



  }


  @Test
  public void test_in_contain_operator_false_case() {


    List<String> tModalityList = new ArrayList<String>();
    tModalityList.add("CT");
    tModalityList.add("US");
    tModalityList.add("SR");
    DicomAttributeFact fact =
        new DicomAttributeFact("", generateFact("(0020,1206)", new StringValueType("C")));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.IN_CONTAINS, tModalityList);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isFalse();



  }


  @Test
  public void test_in_contain_operator_true_case() {


    List<String> tModalityList = new ArrayList<String>();
    tModalityList.add("CT");
    tModalityList.add("US");
    tModalityList.add("SR");
    DicomAttributeFact fact =
        new DicomAttributeFact("", generateFact("(0020,1206)", new StringValueType("CTR")));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.IN_CONTAINS, tModalityList);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();



  }



  @Test
  public void test_string_is_null_true() {

    DicomAttributeFact fact =
        new DicomAttributeFact("", generateFact("(0020,1206)", new StringValueType(null)));
    Condition tDicomRuleCondition =
        new SimpleCondition(new SimpleVariable("(0020,1206)"), RulePredicate.IS_NULL);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();

  }



  @Test
  public void test_string_is_null() {

    DicomAttributeFact fact =
        new DicomAttributeFact("", generateFact("(0020,1206)", new StringValueType("CTR")));
    Condition tDicomRuleCondition =
        new SimpleCondition(new SimpleVariable("(0020,1206)"), RulePredicate.IS_NULL);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isFalse();

  }


}
