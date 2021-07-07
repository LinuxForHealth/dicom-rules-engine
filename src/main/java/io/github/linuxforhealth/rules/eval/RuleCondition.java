/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.eval;

import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import io.github.linuxforhealth.rules.api.RuleFact;
import io.github.linuxforhealth.rules.fact.ContextValues;

public class RuleCondition implements Condition {

  private io.github.linuxforhealth.rules.condition.Condition condition;
  private String ruleName;
  private String parentGroupLable;

  public RuleCondition(String parentGroupLable, String ruleName,
      io.github.linuxforhealth.rules.condition.Condition condition) {
    this.condition = condition;
    this.ruleName = ruleName;
    this.parentGroupLable = parentGroupLable;;

  }

  @Override
  public boolean evaluate(Facts facts) {
    RuleFact fact = facts.get(RuleFact.RULE_FACT_TYPE);
    ContextValues attributes = fact.getData();
    try {
      boolean result = condition.evaluate(attributes);
      fact.putResultOfRule(this.parentGroupLable, this.ruleName, result);
      return result;
    } catch (Exception e) {
      fact.addExceptions(this.parentGroupLable, this.ruleName, e);
      return false;
    }



  }


}


