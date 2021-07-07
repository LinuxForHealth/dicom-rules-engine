/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.conditions.predicate;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import org.assertj.core.util.Lists;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


public class RulePredicatesListToStringTest {

  public static Object[] parametersToTestBiPredicate() throws ClassNotFoundException {
    return new Object[] {
        new Object[] {"RulePredicates.EQUALS", RulePredicateDefinitions.ANY_EQUALS,
            Lists.newArrayList("bat", "val"), "val", true},
        new Object[] {"RulePredicates.EQUALS", RulePredicateDefinitions.ANY_EQUALS,
            Lists.newArrayList("bat", "val"), "bal", false},
        new Object[] {"RulePredicates.EQUALS", RulePredicateDefinitions.ANY_EQUALS,
            Lists.newArrayList(), "va", false},
        new Object[] {"RulePredicates.EQUALS", RulePredicateDefinitions.ANY_EQUALS,
            Lists.newArrayList("bat", "val"), "", false},
        new Object[] {"RulePredicates.EQUALS", RulePredicateDefinitions.ANY_EQUALS, null, "",
            false},
        new Object[] {"RulePredicates.EQUALS", RulePredicateDefinitions.ANY_EQUALS,
            Lists.newArrayList("bat", "val"), null, false},
        new Object[] {"RulePredicates.EQUALS", RulePredicateDefinitions.ANY_EQUALS,
            Lists.newArrayList("bat", "val"), "VAL", true},
        new Object[] {"RulePredicates.EQUALS", RulePredicateDefinitions.ANY_EQUALS,
            Lists.newArrayList("bat", "vAL"), "VAl", true},

        new Object[] {"RulePredicates.ANY_NOT_EQUALS", RulePredicateDefinitions.ANY_NOT_EQUALS,
            Lists.newArrayList("bat", "val"), "val", false},
        new Object[] {"RulePredicates.ANY_NOT_EQUALS", RulePredicateDefinitions.ANY_NOT_EQUALS,
            Lists.newArrayList("bat", "val"), "bal", true},
        new Object[] {"RulePredicates.ANY_NOT_EQUALS", RulePredicateDefinitions.ANY_NOT_EQUALS,
            Lists.newArrayList(), "va", true},
        new Object[] {"RulePredicates.ANY_NOT_EQUALS", RulePredicateDefinitions.ANY_NOT_EQUALS,
            Lists.newArrayList("bat", "val"), "", true},
        new Object[] {"RulePredicates.ANY_NOT_EQUALS", RulePredicateDefinitions.ANY_NOT_EQUALS,
            null, "", true},
        new Object[] {"RulePredicates.ANY_NOT_EQUALS", RulePredicateDefinitions.ANY_NOT_EQUALS,
            Lists.newArrayList("bat", "val"), null, true},
        new Object[] {"RulePredicates.ANY_NOT_EQUALS", RulePredicateDefinitions.ANY_NOT_EQUALS,
            Lists.newArrayList("bat", "val"), "VAL", false},
        new Object[] {"RulePredicates.ANY_NOT_EQUALS", RulePredicateDefinitions.ANY_NOT_EQUALS,
            Lists.newArrayList("bat", "VAl"), "VAl", false},

        new Object[] {"RulePredicates.ANY_CONTAINS", RulePredicateDefinitions.ANY_CONTAINS,
            Lists.newArrayList("bat", "val"), "val", true},
        new Object[] {"RulePredicates.ANY_CONTAINS", RulePredicateDefinitions.ANY_CONTAINS,
            Lists.newArrayList("bat", "val"), "al", true},
        new Object[] {"RulePredicates.ANY_CONTAINS", RulePredicateDefinitions.ANY_CONTAINS,
            Lists.newArrayList("bat", "val"), "ca", false},
        new Object[] {"RulePredicates.ANY_CONTAINS", RulePredicateDefinitions.ANY_CONTAINS,
            Lists.newArrayList(), "va", false},
        new Object[] {"RulePredicates.ANY_CONTAINS", RulePredicateDefinitions.ANY_CONTAINS,
            Lists.newArrayList("bat", "val"), "", true},
        new Object[] {"RulePredicates.ANY_CONTAINS", RulePredicateDefinitions.ANY_CONTAINS, null,
            "", false},
        new Object[] {"RulePredicates.ANY_CONTAINS", RulePredicateDefinitions.ANY_CONTAINS,
            Lists.newArrayList("bat", "val"), null, false},
        new Object[] {"RulePredicates.ANY_CONTAINS", RulePredicateDefinitions.ANY_CONTAINS,
            Lists.newArrayList("bat", "val"), "AL", true},
        new Object[] {"RulePredicates.ANY_CONTAINS", RulePredicateDefinitions.ANY_CONTAINS,
            Lists.newArrayList("bat", "vAl"), "VA", true},

        new Object[] {"RulePredicates.ANY_NOT_CONTAINS", RulePredicateDefinitions.ANY_NOT_CONTAINS,
            Lists.newArrayList("bat", "val"), "val", false},
        new Object[] {"RulePredicates.ANY_NOT_CONTAINS", RulePredicateDefinitions.ANY_NOT_CONTAINS,
            Lists.newArrayList("bat", "val"), "bal", true},
        new Object[] {"RulePredicates.ANY_NOT_CONTAINS", RulePredicateDefinitions.ANY_NOT_CONTAINS,
            Lists.newArrayList("bat", "val"), "", false},
        new Object[] {"RulePredicates.ANY_NOT_CONTAINS", RulePredicateDefinitions.ANY_NOT_CONTAINS,
            null, "", true},
        new Object[] {"RulePredicates.ANY_NOT_CONTAINS", RulePredicateDefinitions.ANY_NOT_CONTAINS,
            Lists.newArrayList("bat", "val"), null, true},
        new Object[] {"RulePredicates.ANY_NOT_CONTAINS", RulePredicateDefinitions.ANY_NOT_CONTAINS,
            Lists.newArrayList("bat", "val"), "VAL", false},
        new Object[] {"RulePredicates.ANY_NOT_CONTAINS", RulePredicateDefinitions.ANY_NOT_CONTAINS,
            Lists.newArrayList("bat", "VAl"), "VAl", false},



    };

  }



  public static Object[] parametersToTestPredicate() throws ClassNotFoundException {
    return new Object[] {

        new Object[] {"RulePredicates.EMPTY", RulePredicateDefinitions.IS_EMPTY,
            Lists.newArrayList("bat", "val"), false},
        new Object[] {"RulePredicates.EMPTY", RulePredicateDefinitions.IS_EMPTY,
            Lists.newArrayList(), true},
        new Object[] {"RulePredicates.NOT_EMPTY", RulePredicateDefinitions.IS_NOT_EMPTY,
            Lists.newArrayList("bat", "VAl"), true},
        new Object[] {"RulePredicates.NOT_EMPTY", RulePredicateDefinitions.IS_NOT_EMPTY,
            Lists.newArrayList(), false},

    };

  }

  @ParameterizedTest
  @MethodSource("parametersToTestBiPredicate")
  public void test_Bipredicate_with_values(String testName,
      BiPredicate<List<String>, String> predicate, List<String> var1, String var2,
      boolean expected) {

    assertThat(predicate.test(var1, var2)).isEqualTo(expected);
  }

  @ParameterizedTest
  @MethodSource("parametersToTestPredicate")
  public void test_predicate_with_values(String testName, Predicate<List<String>> predicate,
      List<String> var1, boolean expected) {

    assertThat(predicate.test(var1)).isEqualTo(expected);
  }

}
