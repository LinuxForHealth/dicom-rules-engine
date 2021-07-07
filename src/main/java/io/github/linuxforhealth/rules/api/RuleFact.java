/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.api;

import io.github.linuxforhealth.rules.fact.ContextValues;

public class RuleFact {

  public static final String RULE_FACT_TYPE = "RULE_FACT";


  private RulesEvaluationResult ruleResults;

  private ContextValues attributes;

  public RuleFact(ContextValues attributes) {
    ruleResults = new RulesEvaluationResult();
    this.attributes = attributes;
  }



  public void putResultOfRule(String parentRuleGroup, String ruleId, Boolean result) {
    ruleResults.addResultOfRule(parentRuleGroup, ruleId, result);
  }



  public RulesEvaluationResult getAllRuleResults() {
    return ruleResults;
  }


  public ContextValues getData() {
    return this.attributes;
  }



  public void addExceptions(String parentRuleGroup, String ruleId, Throwable exception) {
    ruleResults.addExceptions(parentRuleGroup, ruleId, exception);

  }

}
