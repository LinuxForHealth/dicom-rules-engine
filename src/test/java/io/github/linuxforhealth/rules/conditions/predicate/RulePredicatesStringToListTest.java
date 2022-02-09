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


class RulePredicatesStringToListTest {

  public static Object[] parametersToTestPredicate() throws ClassNotFoundException {
    return new Object[] {
        new Object[] {"RulePredicates.IN_EQUALS", RulePredicateDefinitions.IN_EQUALS, "val",
            Lists.newArrayList("Mat", "val"), true},
        new Object[] {"RulePredicates.IN_EQUALS", RulePredicateDefinitions.IN_EQUALS, "val",
            Lists.newArrayList("bal", "kal"), false},
        new Object[] {"RulePredicates.IN_EQUALS", RulePredicateDefinitions.IN_EQUALS, "",
            Lists.newArrayList("bat", "val"), false},
        new Object[] {"RulePredicates.IN_EQUALS", RulePredicateDefinitions.IN_EQUALS, "val",
            Lists.newArrayList(), false},
        new Object[] {"RulePredicates.IN_EQUALS", RulePredicateDefinitions.IN_EQUALS, null,
            Lists.newArrayList("bat", "val"), false},
        new Object[] {"RulePredicates.IN_EQUALS", RulePredicateDefinitions.IN_EQUALS, "val", null,
            false},

        new Object[] {"RulePredicates.IN_EQUALS", RulePredicateDefinitions.IN_EQUALS, "val",
            Lists.newArrayList("bat", "VAL"), true},
        new Object[] {"RulePredicates.IN_EQUALS", RulePredicateDefinitions.IN_EQUALS, "vAL",
            Lists.newArrayList("bat", "VAL"), true},

        new Object[] {"RulePredicates.NOT_IN_EQUALS", RulePredicateDefinitions.NOT_IN_EQUALS, "val",
            Lists.newArrayList("bat", "val"), false},
        new Object[] {"RulePredicates.NOT_IN_EQUALS", RulePredicateDefinitions.NOT_IN_EQUALS, "val",
            Lists.newArrayList("bal", "mal"), true},
        new Object[] {"RulePredicates.NOT_IN_EQUALS", RulePredicateDefinitions.NOT_IN_EQUALS, "",
            Lists.newArrayList("bat", "val"), true},
        new Object[] {"RulePredicates.NOT_IN_EQUALS", RulePredicateDefinitions.NOT_IN_EQUALS, "val",
            Lists.newArrayList(), true},
        new Object[] {"RulePredicates.NOT_IN_EQUALS", RulePredicateDefinitions.NOT_IN_EQUALS, null,
            Lists.newArrayList("bat", "val"), true},
        new Object[] {"RulePredicates.NOT_IN_EQUALS", RulePredicateDefinitions.NOT_IN_EQUALS, "val",
            null, true},
        new Object[] {"RulePredicates.NOT_IN_EQUALS", RulePredicateDefinitions.NOT_IN_EQUALS, "val",
            Lists.newArrayList("ba", "VAL"), false},
        new Object[] {"RulePredicates.NOT_IN_EQUALS", RulePredicateDefinitions.NOT_IN_EQUALS, "VAl",
            Lists.newArrayList("bal", "vAl"), false},

        new Object[] {"RulePredicates.IN_CONTAINS", RulePredicateDefinitions.IN_CONTAINS, "val",
            Lists.newArrayList("bal", "val"), true},
        new Object[] {"RulePredicates.IN_CONTAINS", RulePredicateDefinitions.IN_CONTAINS, "val",
            Lists.newArrayList("da", "al"), true},
        new Object[] {"RulePredicates.IN_CONTAINS", RulePredicateDefinitions.IN_CONTAINS, "val",
            Lists.newArrayList("va", "sl"), true},
        new Object[] {"RulePredicates.IN_CONTAINS", RulePredicateDefinitions.IN_CONTAINS, "val",
            Lists.newArrayList("ka", "pa"), false},
        new Object[] {"RulePredicates.IN_CONTAINS", RulePredicateDefinitions.IN_CONTAINS, "",
            Lists.newArrayList("bal", "val"), false},
        new Object[] {"RulePredicates.IN_CONTAINS", RulePredicateDefinitions.IN_CONTAINS, "val",
            Lists.newArrayList(), false},
        new Object[] {"RulePredicates.IN_CONTAINS", RulePredicateDefinitions.IN_CONTAINS, null,
            Lists.newArrayList(), false},
        new Object[] {"RulePredicates.IN_CONTAINS", RulePredicateDefinitions.IN_CONTAINS, "val",
            null, false},
        new Object[] {"RulePredicates.IN_CONTAINS", RulePredicateDefinitions.IN_CONTAINS, "val",
            Lists.newArrayList("bal", "AL"), true},
        new Object[] {"RulePredicates.IN_CONTAINS", RulePredicateDefinitions.IN_CONTAINS, "vAl",
            Lists.newArrayList("VA"), true},

        new Object[] {"RulePredicates.NOT_IN_CONTAINS", RulePredicateDefinitions.NOT_IN_CONTAINS,
            "val", Lists.newArrayList("bal", "val"), false},
        new Object[] {"RulePredicates.NOT_IN_CONTAINS", RulePredicateDefinitions.NOT_IN_CONTAINS,
            "val", Lists.newArrayList("bal", "vat"), true},
        new Object[] {"RulePredicates.NOT_IN_CONTAINS", RulePredicateDefinitions.NOT_IN_CONTAINS,
            "val", Lists.newArrayList(), true},
        new Object[] {"RulePredicates.NOT_IN_CONTAINS", RulePredicateDefinitions.NOT_IN_CONTAINS,
            null, Lists.newArrayList(), true},
        new Object[] {"RulePredicates.NOT_IN_CONTAINS", RulePredicateDefinitions.NOT_IN_CONTAINS,
            "val", null, true},
        new Object[] {"RulePredicates.NOT_IN_CONTAINS", RulePredicateDefinitions.NOT_IN_CONTAINS,
            "val", Lists.newArrayList("bal", "VA"), false},
        new Object[] {"RulePredicates.NOT_IN_CONTAINS", RulePredicateDefinitions.NOT_IN_CONTAINS,
            "VAl", Lists.newArrayList("baT", "vAl"), false},

    };

  }

  @ParameterizedTest
  @MethodSource("parametersToTestPredicate")
  public void test_predicate_with_values(String testName,
      BiPredicate<String, List<String>> predicate, String var1, List<String> var2,
      boolean expected) {

    assertThat(predicate.test(var1, var2)).isEqualTo(expected);
  }

}
