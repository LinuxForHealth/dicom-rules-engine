/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.eval.action;

import org.apache.commons.lang3.StringUtils;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;
import com.google.common.base.Preconditions;
import io.github.linuxforhealth.rules.api.RuleFact;

/**
 * Tagging rule action puts a tag value true when the rule is evaluated to true. Tagging rule
 * requires specifying the tag value through the constructor.
 * 
 * @author pbhallam@us.ibm.com
 *
 */
public class DefaultRuleAction implements Action {


  private String ruleId;
  private String parentGroupLable;

  public DefaultRuleAction(String parentGroupLable, String ruleId) {
    Preconditions.checkArgument(StringUtils.isNotBlank(ruleId));
    this.ruleId = ruleId;
    this.parentGroupLable = parentGroupLable;
  }

  public String getRuleId() {
    return ruleId;
  }


  @Override
  public void execute(Facts facts) throws Exception {
    Preconditions.checkArgument(facts != null && facts.get(RuleFact.RULE_FACT_TYPE) != null,
        "Fact cannot be null.");
    RuleFact fact = facts.get(RuleFact.RULE_FACT_TYPE);
    fact.putResultOfRule(this.parentGroupLable, this.ruleId, Boolean.TRUE);

  }

}
