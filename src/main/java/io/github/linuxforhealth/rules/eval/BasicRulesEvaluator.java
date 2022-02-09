/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.eval;

import java.io.File;
import java.time.Duration;
import java.util.List;
import com.google.common.base.Preconditions;
import io.github.linuxforhealth.rules.api.Fact;
import io.github.linuxforhealth.rules.api.Rules;
import io.github.linuxforhealth.rules.api.RulesEvaluationResult;
import io.github.linuxforhealth.rules.api.RulesEvaluator;
import io.github.linuxforhealth.rules.api.filter.RuleFilter;
import io.github.linuxforhealth.rules.fact.DataValues;
import io.github.linuxforhealth.rules.rule.RuleDef;
import io.github.linuxforhealth.rules.rule.RuleFormat;
import io.github.linuxforhealth.rules.util.RuleParser;
import io.github.linuxforhealth.rules.util.RulesUtils;
import io.smallrye.mutiny.Multi;

/**
 * This rules engine evaluates rules on a DICOM Context Values that have already been extracted
 * using rules that support DicomRuleCondition.
 * 
 * @author pbhallam
 *
 */

public class BasicRulesEvaluator implements RulesEvaluator<DataValues> {

  private static final String LIST_OF_RULES_CANNOT_BE_NULL = "list of rules cannot be null";

  private Rules<DataValues> rules;
  private List<RuleDef> ruleDef;

  public BasicRulesEvaluator(List<RuleDef> ruleDef) {
    Preconditions.checkArgument(rules != null, LIST_OF_RULES_CANNOT_BE_NULL);
    this.ruleDef = ruleDef;
    this.rules = RulesUtils.generateRulesFrom(ruleDef);
  }


  public BasicRulesEvaluator(File rulesFile) {
    Preconditions.checkArgument(rulesFile != null, "rulesFile cannot be null");
    this.ruleDef = RuleParser.loadRulesFromFile(rulesFile);
    Preconditions.checkArgument(this.ruleDef != null, LIST_OF_RULES_CANNOT_BE_NULL);
    this.rules = RulesUtils.generateRulesFrom(this.ruleDef);
  }

  public BasicRulesEvaluator(String rulesJson, RuleFormat format) {
    Preconditions.checkArgument(rulesJson != null, "rulesJson cannot be null");
    this.ruleDef = RuleParser.loadRules(rulesJson, format);
    Preconditions.checkArgument(this.ruleDef != null, LIST_OF_RULES_CANNOT_BE_NULL);
    this.rules = RulesUtils.generateRulesFrom(this.ruleDef);
  }

  @Override
  public RulesEvaluationResult apply(Fact<DataValues> fact) {

    return Multi.createFrom().iterable(rules.getRules()).log().onItem()
        .transform(r -> r.evaluate(fact))
        .log()
        .collect()
        .in(RulesEvaluationResult::new,
            (col, item) -> col.add(item))
        .await()
        .atMost(Duration.ofSeconds(60));

  }

  public Rules<DataValues> getRules() {
    return rules;
  }


  public List<RuleDef> getRuleDef() {
    return ruleDef;
  }


  @Override
  public RulesEvaluationResult apply(Fact<DataValues> fact, RuleFilter filter) {
    return Multi.createFrom().iterable(rules.getRules(filter)).log().onItem()
        .transform(r -> r.evaluate(fact)).log().collect()
        .in(RulesEvaluationResult::new, (col, item) -> col.add(item)).await()
        .atMost(Duration.ofSeconds(60));
  }

}
