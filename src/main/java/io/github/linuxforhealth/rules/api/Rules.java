/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.api;


import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import io.github.linuxforhealth.rules.api.filter.RuleFilter;

public class Rules<T> {

  private Map<String, Rule<T>> ruleList;

  public Rules() {
    this.ruleList = new ConcurrentHashMap<>();
  }

  /**
     * Create a new {@link Rules} object.
     *
     * @param rules to register
     */
  public Rules(Set<Rule<T>> rules) {
    this.ruleList = new ConcurrentHashMap<>();

    if (rules != null) {
      this.ruleList.putAll(
          rules.stream().collect(Collectors.toMap(Rule::getIdentifier, Function.identity())));
    }
    }


  /**
   * Unregister a rule by ruleIdentifier.
   *
   * @param ruleName name of the rule to unregister, must not be null
   */
  public void unregister(final String ruleIdentifier) {
    this.ruleList.remove(ruleIdentifier);
  }



  /**
   * Check if the rule set is empty.
   *
   * @return true if the rule set is empty, false otherwise
   */
  public boolean isEmpty() {
    return ruleList.isEmpty();
  }

  /**
   * Clear rules.
   */
  public void clear() {
    ruleList.clear();
  }

  /**
   * Return how many rules are currently registered.
   *
   * @return the number of rules currently registered
   */
  public int size() {
    return ruleList.size();
  }




  /**
   * Register one or more new rules.
   *
   * @param rules to register, must not be null
   */
  public void register(Rule<T> rule) {
    Objects.requireNonNull(rule);
    this.ruleList.put(rule.getIdentifier(), rule);

  }


  public List<Rule<T>> getRules() {
    return List.copyOf(this.ruleList.values());
  }

  public List<Rule<T>> getRules(RuleFilter filter) {
    return this.ruleList.values().stream().filter(filter::test)
        .collect(Collectors.toUnmodifiableList());
  }
}
