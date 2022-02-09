/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import io.github.linuxforhealth.rules.util.RulesUtils;

/**
 * This class holds details on what all rules were evaluated on a fact and what is the outcome of
 * rules evaluation
 * 
 * @author pbhallam@us.ibm.com
 *
 */


public class RulesEvaluationResult {

  private Map<String, RuleEvaluationResult> ruleSuccessResults;
  private Map<String, RuleEvaluationResult> ruleFailureResults;
  private Map<String, RuleEvaluationResult> exceptions;



  public RulesEvaluationResult() {
    this.ruleSuccessResults = new ConcurrentHashMap<>();
    this.ruleFailureResults = new ConcurrentHashMap<>();
    this.exceptions = new ConcurrentHashMap<>();

  }



  public RulesEvaluationResult(RulesEvaluationResult a) {
    this.ruleSuccessResults = new ConcurrentHashMap<>(a.ruleSuccessResults);
    this.ruleFailureResults = new ConcurrentHashMap<>(a.ruleFailureResults);
    this.exceptions = new ConcurrentHashMap<>(a.exceptions);

  }


  /**
   * Rule id is combination of rule name and groupId
   * 
   * @param ruleId
   * @return {@link RuleEvaluationResult}
   */
  public RuleEvaluationResult getResultOfRule(String ruleId) {

    RuleEvaluationResult success = ruleSuccessResults.get(ruleId);
    if (success != null) {
      return success;
    }

    RuleEvaluationResult failure = ruleFailureResults.get(ruleId);
    if (failure != null) {
      return failure;
    }

    RuleEvaluationResult exception = exceptions.get(ruleId);
    if (exception != null) {
      return exception;
    }


    throw new IllegalArgumentException("no results for rule :" + ruleId);
  }


  /**
   * Rule id is combination of rule name and groupId
   * 
   * @param rulename
   * @param groupid
   * @return {@link RuleEvaluationResult}
   */
  public RuleEvaluationResult getResultOfRule(String rulename, String groupid) {
    return getResultOfRule(RulesUtils.getRuleIdentifier(rulename, groupid));

  }



  public boolean isEmpty() {
    return this.ruleFailureResults.isEmpty() && this.ruleSuccessResults.isEmpty()
        && this.exceptions.isEmpty();
  }



  public boolean hasFailedRules() {
    return !(this.ruleFailureResults.isEmpty() && this.getExceptions().isEmpty());
  }

  public Map<String, RuleEvaluationResult> getRuleFailureResults() {
    return ruleFailureResults;
  }



  public Map<String, RuleEvaluationResult> getExceptions() {
    return exceptions;
  }






  public RuleEvaluationResult add(RuleEvaluationResult res) {
    if (res.getException() != null) {
      this.exceptions.put(res.getRuleIdentifier(), res);
    } else if (res.isSuccess()) {
      this.ruleSuccessResults.put(res.getRuleIdentifier(), res);
    } else {
      this.ruleFailureResults.put(res.getRuleIdentifier(), res);
    }
    return res;
  }


  public Map<String, RuleEvaluationResult> getRuleSuccessResults() {
    return ruleSuccessResults;
  }



  @Override
  public String toString() {
    return "RulesEvaluationResult [ruleSuccessResults=" + ruleSuccessResults
        + ", ruleFailureResults=" + ruleFailureResults + ", exceptions=" + exceptions + "]";
  }



}
