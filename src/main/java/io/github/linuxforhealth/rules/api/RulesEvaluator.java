/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.api;

import java.io.File;
import java.util.List;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import io.github.linuxforhealth.rules.eval.RuleEngine;
import io.github.linuxforhealth.rules.fact.ContextValues;
import io.github.linuxforhealth.rules.util.RuleParser;

/**
 * This rules engine evaluates rules on a DICOM fact using rules that support DicomRuleCondition.
 * 
 * @author pbhallam
 *
 */
public class RulesEvaluator {

  private static final String LIST_OF_RULES_CANNOT_BE_NULL = "list of rules cannot be null";

  private RuleEngine engine;

  private ImmutableList<Rule> rules;

  public RulesEvaluator(List<Rule> rules) {
    Preconditions.checkArgument(rules != null, LIST_OF_RULES_CANNOT_BE_NULL);
    this.rules = ImmutableList.copyOf(rules);
    this.engine = new RuleEngine(this.rules);
  }


  public RulesEvaluator(File rulesFile) {
    Preconditions.checkArgument(rulesFile != null, "rulesFile cannot be null");
    var localrules = RuleParser.loadRulesFromFile(rulesFile);
    Preconditions.checkArgument(localrules != null, LIST_OF_RULES_CANNOT_BE_NULL);
    this.rules = ImmutableList.copyOf(localrules);
    this.engine = new RuleEngine(this.rules);
  }

  public RulesEvaluator(String rulesJson, Format format) {
    Preconditions.checkArgument(rulesJson != null, "rulesJson cannot be null");
    var localrules = RuleParser.loadRules(rulesJson, format);
    Preconditions.checkArgument(localrules != null, LIST_OF_RULES_CANNOT_BE_NULL);
    this.rules = ImmutableList.copyOf(localrules);
    this.engine = new RuleEngine(this.rules);
  }

  public RulesEvaluationResult evaluateRules(ContextValues attrs) {
    return engine.evaluateRules(attrs);
  }


  public List<Rule> getRules() {
    return rules;
  }

  public RuleEngine getEngine() {
    return engine;
  }



}
