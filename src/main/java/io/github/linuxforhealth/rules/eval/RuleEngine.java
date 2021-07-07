/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.eval;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;
import io.github.linuxforhealth.rules.api.Rule;
import io.github.linuxforhealth.rules.api.RuleFact;
import io.github.linuxforhealth.rules.api.RulesEvaluationResult;
import io.github.linuxforhealth.rules.eval.action.DefaultRuleAction;
import io.github.linuxforhealth.rules.fact.ContextValues;

/**
 * This rules engine evaluates rules on a DICOM fact using rules that support DicomRuleCondition.
 * 
 * @author pbhallam
 *
 */
public class RuleEngine {

  private Rules easyRules;


  public RuleEngine(List<Rule> rules) {
    this.easyRules = generateRulesFrom(Collections.unmodifiableList(rules));
  }

  private void evaluateRules(Facts facts) {
    DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
    rulesEngine.fire(easyRules, facts);
  }


  public RulesEvaluationResult evaluateRules(ContextValues attrs) {
    Facts facts = new Facts();
    RuleFact fact = new RuleFact(attrs);
    facts.put(RuleFact.RULE_FACT_TYPE, fact);
    evaluateRules(facts);
    return fact.getAllRuleResults();
  }

  private static Rules generateRulesFrom(Iterable<Rule> values) {
    Rules rules = new Rules();
    for (Rule val : values) {
      compoundConditions(rules, val);
    }
    return rules;
  }

  private static void compoundConditions(Rules rules,
      io.github.linuxforhealth.rules.api.Rule rule) {
    Map<String, io.github.linuxforhealth.rules.condition.Condition> conditions = rule.getRules();
    for (Entry<String, io.github.linuxforhealth.rules.condition.Condition> rawC : conditions
        .entrySet()) {
      Condition c = new RuleCondition(rule.getGroupId(), rawC.getKey(), rawC.getValue());
      org.jeasy.rules.api.Rule r = new RuleBuilder().name(rawC.getKey()).when(c)
          .then(new DefaultRuleAction(rule.getGroupId(), rawC.getKey())).build();


      rules.register(r);
    }

  }

}
