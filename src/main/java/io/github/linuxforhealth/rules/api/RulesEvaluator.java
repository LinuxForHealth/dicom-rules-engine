/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.api;

import io.github.linuxforhealth.rules.api.filter.RuleFilter;

/**
 * Implementation of this interface executes set of rules for the given fact and returns a
 * {@link RulesEvaluationResult}
 * 
 * @author pbhallam
 *
 * @param <T>
 */
public interface RulesEvaluator<T> {

  RulesEvaluationResult apply(Fact<T> fact);

  RulesEvaluationResult apply(Fact<T> fact, RuleFilter filter);


}
