/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.action;


import static org.assertj.core.api.Assertions.assertThat;
import org.jeasy.rules.api.Facts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.github.linuxforhealth.rules.api.RuleFact;
import io.github.linuxforhealth.rules.eval.action.DefaultRuleAction;

class DefaultActionTest {

  private static final String PARENT_RULE_LABEL = "ParentRuleLabel";
  private static final String ANY_RULE_NAME = "any_rule_name";


  @Test
  public void boolean_true_is_set_when_rule_action_is_evaluated_to_true() throws Exception {
    DefaultRuleAction booleanAction = new DefaultRuleAction(PARENT_RULE_LABEL, ANY_RULE_NAME);
    RuleFact fact = new RuleFact(TestUtils.generateFact());
    Facts facts = new Facts();
    facts.put(RuleFact.RULE_FACT_TYPE, fact);
    booleanAction.execute(facts);

    assertThat(fact.getAllRuleResults().getResultOfRule(PARENT_RULE_LABEL, ANY_RULE_NAME))
        .isEqualTo(true);
  }

  @Test
  public void rule_action_with_null_fact_throws_exception() throws Exception {

    DefaultRuleAction booleanAction = new DefaultRuleAction(PARENT_RULE_LABEL, ANY_RULE_NAME);
    Facts facts = new Facts();
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      booleanAction.execute(facts);
    });

  }

}
