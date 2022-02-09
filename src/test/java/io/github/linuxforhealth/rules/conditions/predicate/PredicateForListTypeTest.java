/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.conditions.predicate;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.google.common.collect.Lists;
import io.github.linuxforhealth.rules.api.Condition;
import io.github.linuxforhealth.rules.condition.SimpleBiCondition;
import io.github.linuxforhealth.rules.condition.SimpleCondition;
import io.github.linuxforhealth.rules.condition.predicate.RuleBiPredicate;
import io.github.linuxforhealth.rules.condition.predicate.RulePredicate;
import io.github.linuxforhealth.rules.condition.variable.SimpleVariable;
import io.github.linuxforhealth.rules.fact.DataValues;
import io.github.linuxforhealth.rules.fact.DicomAttributeFact;
import io.github.linuxforhealth.rules.fact.ListValueType;
import io.github.linuxforhealth.rules.fact.ValueType;



class PredicateForListTypeTest {



  @Test
  public void test_anycontain_operator_evaluated() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("SRT"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.ANY_CONTAINS, "SR");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();


  }


  private DataValues generateFact(String key, ValueType value) {
    DataValues attrs = new DataValues();
    attrs.addValue(key, value);
    return attrs;

  }



  @Test
  public void test_any_equal_true_case() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("SR"))));
    Condition tDicomRuleCondition =
        new SimpleBiCondition(new SimpleVariable("(0020,1206)"), RuleBiPredicate.ANY_EQUALS, "SR");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();


  }

  @Test
  public void test_any_equal_false_case() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("SRT"))));
    Condition tDicomRuleCondition =
        new SimpleBiCondition(new SimpleVariable("(0020,1206)"), RuleBiPredicate.ANY_EQUALS, "SR");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isEqualTo(false);


  }


  @Test
  public void test_any_not_equal_operator_false_case() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("SRT"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.ANY_NOT_EQUALS, "SRT");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isEqualTo(false);

  }



  @Test
  public void test_any_not_equal_operator_true_case() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("SRT"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.ANY_NOT_EQUALS, "SR");
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();



  }


  @Test
  public void test_any_in_equal_operator_false_case() {

    List<String> tModalityList = new ArrayList<String>();
    tModalityList.add("CT");
    tModalityList.add("US");
    tModalityList.add("SR");

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("SRT"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.ANY_IN_EQUALS, tModalityList);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isEqualTo(false);



  }



  @Test
  public void test_any_in_equal_operator_true_case() {

    List<String> tModalityList = new ArrayList<String>();
    tModalityList.add("CT");
    tModalityList.add("US");
    tModalityList.add("SR");

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("SR"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.ANY_IN_EQUALS, tModalityList);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();



  }


  @Test
  public void test_any_not_in_equal_operator_true_case() {


    List<String> tModalityList = new ArrayList<String>();
    tModalityList.add("CT");
    tModalityList.add("US");
    tModalityList.add("SR");

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("FT"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.ANY_NOT_IN_EQUALS, tModalityList);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();



  }

  @Test
  public void test_any_not_in_equal_operator_false_case() {


    List<String> tModalityList = new ArrayList<String>();
    tModalityList.add("CT");
    tModalityList.add("US");
    tModalityList.add("SR");

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("CTR"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.ANY_NOT_IN_EQUALS, tModalityList);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();



  }

  @Test
  public void test_any_in_contain_operator_false_case() {



    List<String> tModalityList = new ArrayList<String>();
    tModalityList.add("CT");
    tModalityList.add("US");
    tModalityList.add("SR");

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("CTR"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.ANY_IN_CONTAINS, tModalityList);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();



  }

  @Test
  public void test_any_in_contains_operator_true_case() {



    List<String> tModalityList = new ArrayList<String>();
    tModalityList.add("CT");
    tModalityList.add("US");
    tModalityList.add("SR");

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("CTR"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.ANY_IN_CONTAINS, tModalityList);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();



  }

  @Test
  public void test_any_not_in_contain_operator_false_case() {



    List<String> tModalityList = new ArrayList<String>();
    tModalityList.add("CT");
    tModalityList.add("US");
    tModalityList.add("SR");

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("C"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.ANY_NOT_IN_CONTAINS, tModalityList);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();



  }

  @Test
  public void test_list_is_empty_false() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("ggt"))));
    Condition tDicomRuleCondition =
        new SimpleCondition(new SimpleVariable("(0020,1206)"), RulePredicate.IS_EMPTY);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isFalse();


  }

  @Test
  public void test_list_is_empty_true() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList())));
    Condition tDicomRuleCondition =
        new SimpleCondition(new SimpleVariable("(0020,1206)"), RulePredicate.IS_EMPTY);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());

    assertThat(tConditionEvaluated).isTrue();

  }

  @Test
  public void test_list_is_not_empty() {
    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("GDR"))));
    Condition tDicomRuleCondition =
        new SimpleCondition(new SimpleVariable("(0020,1206)"), RulePredicate.IS_NOT_EMPTY);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();



  }

  @Test
  public void test_not_contains_any_operator_true() {



    List<String> tModalityList = new ArrayList<String>();
    tModalityList.add("CT");
    tModalityList.add("US");
    tModalityList.add("SR");

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("C"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.NOT_CONTAINS_ANY, tModalityList);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();



  }


  @Test
  public void test_not_contain_all_operator_false() {



    List<String> tModalityList = new ArrayList<String>();
    tModalityList.add("CT");
    tModalityList.add("US");
    tModalityList.add("SR");

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("CT", "US", "SR", "HY"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.NOT_CONTAINS_ANY, tModalityList);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isFalse();



  }


  @Test
  public void test_contain_all_operator_true() {



    List<String> tModalityList = new ArrayList<String>();
    tModalityList.add("CT");
    tModalityList.add("US");
    tModalityList.add("SR");

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("CT", "US", "SR", "HY"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.CONTAINS_ALL, tModalityList);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isTrue();



  }


  @Test
  public void test_contain_all_operator_false() {



    List<String> tModalityList = new ArrayList<String>();
    tModalityList.add("CT");
    tModalityList.add("US");
    tModalityList.add("SR");

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("CT", "US"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.CONTAINS_ALL, tModalityList);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());
    assertThat(tConditionEvaluated).isFalse();



  }


  @Test
  public void test_list_size_equal() {

    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("CT", "US"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.LIST_SIZE_EQUAL, 2);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());

    assertThat(tConditionEvaluated).isTrue();

  }

  @Test
  public void test_list_size_greater_than_false() {
    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("CT", "US"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.LIST_SIZE_GREATER_THAN, 3);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());

    assertThat(tConditionEvaluated).isFalse();



  }

  @Test
  public void test_list_size_greater_than_true() {
    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("CT", "US"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.LIST_SIZE_GREATER_THAN, 1);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());

    assertThat(tConditionEvaluated).isTrue();



  }


  @Test
  public void test_list_size_less_than() {
    DicomAttributeFact fact = new DicomAttributeFact("",
        generateFact("(0020,1206)", new ListValueType(Lists.newArrayList("CT", "US"))));
    Condition tDicomRuleCondition = new SimpleBiCondition(new SimpleVariable("(0020,1206)"),
        RuleBiPredicate.LIST_SIZE_LESS_THAN, 3);
    boolean tConditionEvaluated = tDicomRuleCondition.evaluate(fact.getValue());

    assertThat(tConditionEvaluated).isTrue();



  }



}
