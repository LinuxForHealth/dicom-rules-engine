
package io.github.linuxforhealth.rules.rule;

import java.time.Duration;
import io.github.linuxforhealth.rules.api.Condition;
import io.github.linuxforhealth.rules.api.Fact;
import io.github.linuxforhealth.rules.api.Rule;
import io.github.linuxforhealth.rules.api.RuleEvaluationResult;
import io.github.linuxforhealth.rules.fact.DataValues;
import io.github.linuxforhealth.rules.util.RulesUtils;
import io.smallrye.mutiny.Uni;

public class BasicRule implements Rule<DataValues> {

  private Condition condition;
  private String name;
  private String groupId;
  private String ruleIdentifier;

  public BasicRule(String name, String groupid, Condition condition) {
    this.condition = condition;
    this.name = name;
    this.groupId = groupid;
    this.ruleIdentifier = RulesUtils.getRuleIdentifier(this.name, this.groupId);
  }

  @Override
  public RuleEvaluationResult evaluate(Fact<DataValues> fact) {

    return Uni.createFrom().item(fact).onItem()
        .transform(f -> condition.evaluate(f.getValue())).onItemOrFailure()
        .transform((b, th) -> getRuleEvaluationResult(b, th, fact.geIdentifier()))
        .log().await()
        .atMost(Duration.ofSeconds(30));
        
   
  }

  private RuleEvaluationResult getRuleEvaluationResult(Boolean res, Throwable th, String factId) {

    if(th!=null) {
      return new RuleEvaluationResult(th, this.ruleIdentifier, factId);
    }else {
      return new RuleEvaluationResult(res, this.ruleIdentifier, factId);
    }
    
  }

  @Override
  public String toString() {
    return "BasicRule [condition=" + condition + ", ruleIdentifier=" + ruleIdentifier 
        + "]";
  }



  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getGroupId() {
    return groupId;
  }

  @Override
  public String getIdentifier() {
    return ruleIdentifier;
  }



}
