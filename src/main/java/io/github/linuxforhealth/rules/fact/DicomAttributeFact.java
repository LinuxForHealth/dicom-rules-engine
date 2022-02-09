/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.fact;

import io.github.linuxforhealth.rules.api.Fact;
import io.github.linuxforhealth.rules.api.RulesEvaluationResult;

public class DicomAttributeFact implements Fact<DataValues> {

  public static final String RULE_FACT_TYPE = "RULE_FACT";


  private RulesEvaluationResult ruleResults;

  protected DataValues attributes;
  protected String factIdentifier;

  public DicomAttributeFact(String factIdentifier, DataValues attributes) {
    ruleResults = new RulesEvaluationResult();
    this.attributes = attributes;
    this.factIdentifier = factIdentifier;
  }

  protected DicomAttributeFact(String factIdentifier) {
    this.factIdentifier = factIdentifier;
  }




  public RulesEvaluationResult getAllRuleResults() {
    return ruleResults;
  }









  @Override
  public DataValues getValue() {
    return this.attributes;
  }



  @Override
  public String toString() {
    return "BasicRuleFact [ruleResults=" + ruleResults + ", attributes=" + attributes
        + ", identifier="
        + factIdentifier + "]";
  }







  @Override
  public String geIdentifier() {
    return factIdentifier;
  }


}
