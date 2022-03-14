/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.rule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringTokenizer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import io.github.linuxforhealth.rules.api.Condition;
import io.github.linuxforhealth.rules.condition.Constants;
import io.github.linuxforhealth.rules.condition.SimpleBiCondition;
import io.github.linuxforhealth.rules.condition.SimpleCondition;
import io.github.linuxforhealth.rules.condition.variable.NestedVariable;
import io.github.linuxforhealth.rules.condition.variable.SimpleVariable;
import io.github.linuxforhealth.rules.condition.variable.Variable;

/**
 * This interface define the basic rule for a generic rule condition.
 * 
 * @author pbhallam@us.ibm.com
 *
 *
 */



public class RuleDef {

  private String groupId;
  private Map<String, Condition> conditions;
  private Map<String, Condition> rules;
  private Set<String> attributNames;



  private String attributeNameDelimiter;


  @JsonCreator
  public RuleDef(@JsonProperty("groupid") String groupId,
      @JsonProperty("conditions") Map<String, String> conditions,
      @JsonProperty("rules") Map<String, String> compositeRules,
      @JsonProperty("attributeNameDelimiter") String attributeNameDelimiter) {
    Preconditions.checkArgument(
        !StringUtils.containsAny(groupId, StringUtils.SPACE)
            && !StringUtils.containsAny(groupId, ":"),
        "groupId should not contain any space character or :");
    this.groupId = groupId;
    if (attributeNameDelimiter == null) {
      this.attributeNameDelimiter = Constants.DEFAULT_VARIABLE_DELIMITER;
    } else {
      this.attributeNameDelimiter = attributeNameDelimiter;
    }
    this.attributNames = new HashSet<>();
    this.conditions = new HashMap<>();
    if (conditions != null) {
      this.conditions.putAll(parseConditions(conditions));
    }
    this.rules = parseRules(compositeRules);


  }

  private Map<String, Condition> parseRules(Map<String, String> rulesRaw) {
    Map<String, Condition> ruleMap = new HashMap<>();
    for (Entry<String, String> e : rulesRaw.entrySet()) {
      StringTokenizer tok = new StringTokenizer(e.getValue());
      Condition c = generateRule(tok, false);
      if (c != null) {
        ruleMap.put(e.getKey(), c);
      }
    }


    return ruleMap;
  }

  private Map<? extends String, ? extends Condition> parseConditions(
      Map<String, String> conditionRaw) {
    Map<String, Condition> conds = new HashMap<>();
    conditionRaw.forEach((k, v) -> conds.put(k, generateCondition(v)));
    return conds;
  }

  private Condition generateCondition(String statement) {
    String[] toks = StringUtils.split(statement, StringUtils.SPACE, 3);

    if (toks[2] == null && toks[0] != null && toks[1] != null) {

      return new SimpleCondition(generateVariable(toks[0]), toks[1]);

    } else if (toks[2] != null && toks[0] != null && toks[1] != null) {

      return new SimpleBiCondition(generateVariable(toks[0]), toks[1], toks[2]);
    }
    throw new IllegalStateException("String cannot be pased to a condition. Value:" + statement);
  }

  private Variable generateVariable(String var) {
    Preconditions.checkArgument(StringUtils.startsWith(var, "$"), "Variable should start with $");
    String varName = StringUtils.removeStart(var, "$");
    StringTokenizer stk = new StringTokenizer(varName, this.attributeNameDelimiter);

    Variable v = null;
    if (stk.getTokenList().size() == 0) {
      throw new IllegalArgumentException("Variable name not valid,varName: " + varName);
    } else if (stk.getTokenList().size() == 1) {
      v = new SimpleVariable(varName);
    } else {
      v = new NestedVariable(varName, this.attributeNameDelimiter);
    }

    attributNames.add(v.getSpec().get(0).getSpec());
    return v;
  }

  private Condition generateRule(StringTokenizer tok, boolean conditionStarted) {


    if (tok == null || !tok.hasNext()) {
      return null;
    }

    String firstTok = tok.nextToken();
    Condition cond;
    if ("(".equals(firstTok)) {
      cond = generateRule(tok, true);
    } else {
      cond = fetchCondition(firstTok);
    }


    boolean conditionCompleted = false;
    while (tok.hasNext() || conditionCompleted) {


      String tok2 = tok.nextToken();
      if (")".equals(tok2)) {
        conditionCompleted = true;
        break;
      }

      String tok3 = tok.nextToken();
      Condition c;
      if ("(".equals(tok3)) {
        c = generateRule(tok, true);
      } else {
        c = fetchCondition(tok3);
      }



      if ("&&".equals(tok2)) {
        cond = cond.andCondition(c);
      } else if ("||".equals(tok2)) {
        cond = cond.orCondition(c);
      }


    }

    if ((conditionStarted && conditionCompleted) || (!conditionStarted && !conditionCompleted)) {
      return cond;
    }
    throw new IllegalStateException("Condition parsing error");
  }

  private Condition fetchCondition(String nextToken) {
    Condition c = this.conditions.get(nextToken);
    if (c != null) {
      return c;
    }
    throw new IllegalArgumentException(
        "Condition with id:" + nextToken + " not defined in the condition list");
  }

  public String getGroupId() {
    return this.groupId;
  }

  public Map<String, Condition> getConditions() {
    return conditions;
  }

  public String getAttributeNameDelimiter() {
    return attributeNameDelimiter;
  }



  public Map<String, Condition> getRules() {
    return new HashMap<>(rules);
  }



  public Set<String> getAttributNames() {
    return attributNames;
  }

}
