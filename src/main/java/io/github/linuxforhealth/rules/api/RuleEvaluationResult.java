/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.api;


/**
 * This class holds result of rule evaluation on a fact with provided rules.
 * 
 * @author pbhallam@us.ibm.com
 *
 */
public class RuleEvaluationResult {
  private boolean ruleSuccess;
  private String ruleIdentifier;
  private String factIdentifier;

  public RuleEvaluationResult(boolean ruleSuccess, String ruleIdentifier, String factIdentifier) {
    this.ruleSuccess = ruleSuccess;
    this.ruleIdentifier = ruleIdentifier;
    this.factIdentifier = factIdentifier;
  }

  public boolean isRuleSuccess() {
    return ruleSuccess;
  }


  public String getRuleIdentifier() {
    return ruleIdentifier;
  }


  public String getFactIdentifier() {
    return factIdentifier;
  }


}
