/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import com.google.common.base.Preconditions;

/**
 * This class holds details on what all rules were evaluated on a fact and what is the outcome of
 * rules evaluation
 * 
 * @author pbhallam@us.ibm.com
 *
 */


public class RulesEvaluationResult {

  private Map<String, Set<String>> ruleSuccessResults;
  private Map<String, Set<String>> ruleFailureResults;
  private Map<String, Map<String, Throwable>> exceptions;

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



  public Boolean getResultOfRule(String parentRuleGroup, String ruleId) {

    Set<String> groupSuccessResults = ruleSuccessResults.get(parentRuleGroup);

    if (groupSuccessResults != null && groupSuccessResults.contains(ruleId)) {
      return true;
    }
    Set<String> groupFailureResults = ruleFailureResults.get(parentRuleGroup);
    if (groupFailureResults != null && groupFailureResults.contains(ruleId)) {
      return false;
    }

    return null;
  }



  public Map<String, Map<String, Throwable>> getExceptions() {
    return new HashMap<>(exceptions);
  }

  public Map<String, Set<String>> getRuleSuccessResults() {
    return new HashMap<>(ruleSuccessResults);
  }



  public Map<String, Set<String>> getRuleFailureResults() {
    return new HashMap<>(ruleFailureResults);
  }

  void addExceptions(String parentRuleGroup, String ruleId, Throwable exception) {

    Map<String, Throwable> groupResults = exceptions.get(parentRuleGroup);
    if (groupResults == null) {
      groupResults = new ConcurrentHashMap<>();
      exceptions.put(parentRuleGroup, groupResults);
    }
    groupResults.put(ruleId, exception);
  }

  void addResultOfRule(String parentRuleGroup, String ruleId, boolean result) {

    if (result) {
      Set<String> groupSuccessResults = ruleSuccessResults.get(parentRuleGroup);
      if (groupSuccessResults == null) {
        groupSuccessResults = new ConcurrentHashMap<>().newKeySet();
        ruleSuccessResults.put(parentRuleGroup, groupSuccessResults);
      }
      groupSuccessResults.add(ruleId);


    } else {
      Set<String> groupFailureResults = ruleFailureResults.get(parentRuleGroup);
      if (groupFailureResults == null) {
        groupFailureResults = new ConcurrentHashMap<>().newKeySet();
        ruleFailureResults.put(parentRuleGroup, groupFailureResults);
      }
      groupFailureResults.add(ruleId);

    }


  }


  public static RulesEvaluationResult aggregate(RulesEvaluationResult a, RulesEvaluationResult b) {
    Preconditions.checkArgument(a != null, "RulesEvaluationResult a  cannot be null.");
    Preconditions.checkArgument(b != null, "RulesEvaluationResult b cannot be null.");

    if (a.isEmpty()) {
      return new RulesEvaluationResult(b);
    }
    if (b.isEmpty()) {
      return new RulesEvaluationResult(a);
    }

    RulesEvaluationResult aggr = new RulesEvaluationResult(a);



    for (Entry<String, Set<String>> e : b.ruleFailureResults.entrySet()) {
      Set<String> tempCopy = new HashSet<>(e.getValue());
      if (aggr.ruleSuccessResults.get(e.getKey()) != null) {
        tempCopy.removeAll(aggr.ruleSuccessResults.get(e.getKey()));
      }

      Set<String> temp = aggr.ruleFailureResults.get(e.getKey());
      if (temp == null) {
        temp = new ConcurrentHashMap<>().newKeySet();
        aggr.ruleFailureResults.put(e.getKey(), temp);
      }
      temp.addAll(tempCopy);


    }


    for (Entry<String, Set<String>> e : b.ruleSuccessResults.entrySet()) {
      Set<String> tempF = aggr.ruleFailureResults.get(e.getKey());
      if (tempF != null) {
        tempF.removeAll(e.getValue());
      }

      Set<String> temp = aggr.ruleSuccessResults.get(e.getKey());
      if (temp == null) {
        temp = new ConcurrentHashMap<>().newKeySet();
        aggr.ruleSuccessResults.put(e.getKey(), temp);
      }
      temp.addAll(e.getValue());


    }



    for (Entry<String, Map<String, Throwable>> e : b.getExceptions().entrySet()) {
      Map<String, Throwable> temp = aggr.exceptions.get(e.getKey());
      if (temp == null) {
        temp = new ConcurrentHashMap<>();
      }
      temp.putAll(e.getValue());
    }

    return aggr;

  }



  private boolean isEmpty() {
    return this.ruleFailureResults.isEmpty() && this.ruleSuccessResults.isEmpty()
        && this.exceptions.isEmpty();
  }



  public boolean hasFailedRules() {
    return !(this.ruleFailureResults.isEmpty() && this.getExceptions().isEmpty());
  }



}
