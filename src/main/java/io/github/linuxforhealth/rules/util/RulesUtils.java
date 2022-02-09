/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.util;

import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import com.google.common.base.Preconditions;
import io.github.linuxforhealth.rules.api.Condition;
import io.github.linuxforhealth.rules.api.Rule;
import io.github.linuxforhealth.rules.api.Rules;
import io.github.linuxforhealth.rules.fact.DataValues;
import io.github.linuxforhealth.rules.rule.BasicRule;
import io.github.linuxforhealth.rules.rule.RuleDef;

/**
 * This rules engine evaluates rules on a DICOM fact using rules that support DicomRuleCondition.
 * 
 * @author pbhallam
 *
 */
public class RulesUtils {

  private RulesUtils() {}

  public static Rules<DataValues> generateRulesFrom(Iterable<RuleDef> values) {
    var rules = new Rules<DataValues>();
    for (RuleDef val : values) {
      compoundConditions(rules, val);
    }
    return rules;
  }

  private static void compoundConditions(Rules<DataValues> rules,
      io.github.linuxforhealth.rules.rule.RuleDef rule) {
    Map<String, io.github.linuxforhealth.rules.api.Condition> conditions = rule.getRules();
    for (Entry<String, Condition> rawC : conditions.entrySet()) {

      Rule<DataValues> r = new BasicRule(rawC.getKey(), rule.getGroupId(), rawC.getValue());
      rules.register(r);
    }
  }

  public static String getRuleIdentifier(String ruleName, String groupId) {
    Preconditions.checkArgument(StringUtils.isNotBlank(ruleName), "ruleName cannot be blank.");
    Preconditions.checkArgument(StringUtils.isNotBlank(groupId), "groupId cannot be blank.");
    return groupId + ":" + ruleName;
  }

}
