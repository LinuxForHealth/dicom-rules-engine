/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.rule;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RulesConfiguration {

  private List<RuleDef> rules;

  @JsonCreator
  public RulesConfiguration(@JsonProperty("ruleDefinitions") List<RuleDef> rules) {
    this.rules = rules;
  }

  public List<RuleDef> getRules() {
    return rules;
  }

  public void setRules(List<RuleDef> rules) {
    this.rules = rules;
  }

}
