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
  private boolean success;
  private String ruleIdentifier;
  private String factIdentifier;
  private Throwable exception;

  /**
   * 
   * @param isSuccess
   * @param ruleIdentifier
   * @param factIdentifier
   */
  public RuleEvaluationResult(boolean isSuccess, String ruleIdentifier, String factIdentifier) {
    this.success = isSuccess;
    this.ruleIdentifier = ruleIdentifier;
    this.factIdentifier = factIdentifier;
  }

  /**
   * 
   * @param exception
   * @param ruleIdentifier
   * @param factIdentifier
   */
  public RuleEvaluationResult(Throwable exception, String ruleIdentifier, String factIdentifier) {
    this.success = false;
    this.exception = exception;
    this.ruleIdentifier = ruleIdentifier;
    this.factIdentifier = factIdentifier;
  }


  public boolean isSuccess() {
    return success;
  }


  public String getRuleIdentifier() {
    return ruleIdentifier;
  }


  public String getFactIdentifier() {
    return factIdentifier;
  }

  public Throwable getException() {
    return exception;
  }

  @Override
  public String toString() {
    return "RuleEvaluationResult [success=" + success + ", ruleIdentifier=" + ruleIdentifier
        + ", factIdentifier=" + factIdentifier + ", exception=" + exception + "]";
  }


}
