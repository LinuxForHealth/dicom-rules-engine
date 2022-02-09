/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.conditions.predicate;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.function.BiPredicate;
import org.assertj.core.util.Lists;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import io.github.linuxforhealth.rules.condition.predicate.RulePredicateDefinitions;


class RulePredicatesListToIntegerTest {

  public static Object[] parametersToTestPredicate() throws ClassNotFoundException {
    return new Object[] {
        new Object[] {"RulePredicates.LIST_SIZE_EQUAL", RulePredicateDefinitions.LIST_SIZE_EQUAL,
            Lists.newArrayList("CT", "US"), 2, true},
        new Object[] {"RulePredicates.LIST_SIZE_EQUAL", RulePredicateDefinitions.LIST_SIZE_EQUAL,
            Lists.newArrayList("SR", "CT"), 1, false},

        new Object[] {"RulePredicates.LIST_SIZE_GREATER_THAN",
            RulePredicateDefinitions.LIST_SIZE_GREATER_THAN, Lists.newArrayList("CT", "US", "MR"),
            2, true},
        new Object[] {"RulePredicates.LIST_SIZE_GREATER_THAN",
            RulePredicateDefinitions.LIST_SIZE_GREATER_THAN, Lists.newArrayList("SR", "CT"), 3,
            false},

        new Object[] {"RulePredicates.LIST_SIZE_LESS_THAN",
            RulePredicateDefinitions.LIST_SIZE_LESS_THAN, Lists.newArrayList("CT", "US", "MR"), 4,
            true},
        new Object[] {"RulePredicates.LIST_SIZE_LESS_THAN",
            RulePredicateDefinitions.LIST_SIZE_LESS_THAN, Lists.newArrayList("SR", "CT"), 2, false},

    };

  }

  @ParameterizedTest
  @MethodSource("parametersToTestPredicate")
  public void test_predicate_with_values(String testName,
      BiPredicate<List<String>, Integer> predicate, List<String> var1, Integer var2,
      boolean expected) {
    assertThat(predicate.test(var1, var2)).isEqualTo(expected);
  }

}
