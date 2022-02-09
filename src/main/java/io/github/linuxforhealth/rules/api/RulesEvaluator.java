package io.github.linuxforhealth.rules.api;

import io.github.linuxforhealth.rules.api.filter.RuleFilter;

public interface RulesEvaluator<T> {

  RulesEvaluationResult apply(Fact<T> fact);

  RulesEvaluationResult apply(Fact<T> fact, RuleFilter filter);


}
