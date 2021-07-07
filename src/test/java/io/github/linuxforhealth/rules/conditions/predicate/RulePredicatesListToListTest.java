/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.conditions.predicate;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.function.BiPredicate;
import org.assertj.core.util.Lists;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import com.google.common.collect.Sets;


class RulePredicatesListToListTest {

  public static Object[] parametersToTestPredicate() throws ClassNotFoundException {
    return new Object[] {
        new Object[] {"RulePredicates.ANY_IN_EQUALS", RulePredicateDefinitions.ANY_IN_EQUALS,
            Lists.newArrayList("bat", "val"), Lists.newArrayList("Mat", "val"), true},
        new Object[] {"RulePredicates.ANY_IN_EQUALS", RulePredicateDefinitions.ANY_IN_EQUALS,
            Lists.newArrayList("bat", "val"), Lists.newArrayList("bal", "kal"), false},
        new Object[] {"RulePredicates.ANY_IN_EQUALS", RulePredicateDefinitions.ANY_IN_EQUALS,
            Lists.newArrayList(), Lists.newArrayList("bat", "val"), false},
        new Object[] {"RulePredicates.ANY_IN_EQUALS", RulePredicateDefinitions.ANY_IN_EQUALS,
            Lists.newArrayList("bat", "val"), Lists.newArrayList(), false},
        new Object[] {"RulePredicates.ANY_IN_EQUALS", RulePredicateDefinitions.ANY_IN_EQUALS, null,
            Lists.newArrayList("bat", "val"), false},
        new Object[] {"RulePredicates.ANY_IN_EQUALS", RulePredicateDefinitions.ANY_IN_EQUALS,
            Lists.newArrayList("bat", "val"), null, false},
        new Object[] {"RulePredicates.ANY_IN_EQUALS", RulePredicateDefinitions.ANY_IN_EQUALS,
            Lists.newArrayList("bat", "val"), Lists.newArrayList("sal", "bat", "val"), true},
        new Object[] {"RulePredicates.ANY_IN_EQUALS", RulePredicateDefinitions.ANY_IN_EQUALS,
            Lists.newArrayList("bat", "val"), Lists.newArrayList("bat", "VAL"), true},
        new Object[] {"RulePredicates.ANY_IN_EQUALS", RulePredicateDefinitions.ANY_IN_EQUALS,
            Lists.newArrayList("bat", "vAL"), Lists.newArrayList("bat", "VAL"), true},

        new Object[] {"RulePredicates.ANY_NOT_IN_EQUALS",
            RulePredicateDefinitions.ANY_NOT_IN_EQUALS, Lists.newArrayList("bat", "val"),
            Lists.newArrayList("bat", "val"), false},
        new Object[] {"RulePredicates.ANY_NOT_IN_EQUALS",
            RulePredicateDefinitions.ANY_NOT_IN_EQUALS, Lists.newArrayList("bat", "val"),
            Lists.newArrayList("bal", "mal"), true},
        new Object[] {"RulePredicates.ANY_NOT_IN_EQUALS",
            RulePredicateDefinitions.ANY_NOT_IN_EQUALS, Lists.newArrayList(),
            Lists.newArrayList("bat", "val"), true},
        new Object[] {"RulePredicates.ANY_NOT_IN_EQUALS",
            RulePredicateDefinitions.ANY_NOT_IN_EQUALS, Lists.newArrayList("bat", "val"),
            Lists.newArrayList(), true},
        new Object[] {"RulePredicates.ANY_NOT_IN_EQUALS",
            RulePredicateDefinitions.ANY_NOT_IN_EQUALS, null, Lists.newArrayList("bat", "val"),
            true},
        new Object[] {"RulePredicates.ANY_NOT_IN_EQUALS",
            RulePredicateDefinitions.ANY_NOT_IN_EQUALS, Lists.newArrayList("bat", "val"), null,
            true},
        new Object[] {"RulePredicates.ANY_NOT_IN_EQUALS",
            RulePredicateDefinitions.ANY_NOT_IN_EQUALS, Lists.newArrayList("bat", "val"),
            Lists.newArrayList("ba", "VAL"), false},
        new Object[] {"RulePredicates.ANY_NOT_IN_EQUALS",
            RulePredicateDefinitions.ANY_NOT_IN_EQUALS, Lists.newArrayList("bat", "VAl"),
            Lists.newArrayList("bal", "vAl"), false},

        new Object[] {"RulePredicates.ANY_IN_CONTAINS", RulePredicateDefinitions.ANY_IN_CONTAINS,
            Lists.newArrayList("bat", "val"), Lists.newArrayList("bal", "val"), true},
        new Object[] {"RulePredicates.ANY_IN_CONTAINS", RulePredicateDefinitions.ANY_IN_CONTAINS,
            Lists.newArrayList("bat", "val"), Lists.newArrayList("da", "al"), true},
        new Object[] {"RulePredicates.ANY_IN_CONTAINS", RulePredicateDefinitions.ANY_IN_CONTAINS,
            Lists.newArrayList("bat", "val"), Lists.newArrayList("ba", "sl"), true},
        new Object[] {"RulePredicates.ANY_IN_CONTAINS", RulePredicateDefinitions.ANY_IN_CONTAINS,
            Lists.newArrayList("bat", "val"), Lists.newArrayList("ka", "pa"), false},
        new Object[] {"RulePredicates.ANY_IN_CONTAINS", RulePredicateDefinitions.ANY_IN_CONTAINS,
            Lists.newArrayList(), Lists.newArrayList("bal", "val"), false},
        new Object[] {"RulePredicates.ANY_IN_CONTAINS", RulePredicateDefinitions.ANY_IN_CONTAINS,
            Lists.newArrayList("bat", "val"), Lists.newArrayList(), false},
        new Object[] {"RulePredicates.ANY_IN_CONTAINS", RulePredicateDefinitions.ANY_IN_CONTAINS,
            null, Lists.newArrayList(), false},
        new Object[] {"RulePredicates.ANY_IN_CONTAINS", RulePredicateDefinitions.ANY_IN_CONTAINS,
            Lists.newArrayList("bat", "val"), null, false},
        new Object[] {"RulePredicates.ANY_IN_CONTAINS", RulePredicateDefinitions.ANY_IN_CONTAINS,
            Lists.newArrayList("bat", "val"), Lists.newArrayList("bal", "AL"), true},
        new Object[] {"RulePredicates.ANY_IN_CONTAINS", RulePredicateDefinitions.ANY_IN_CONTAINS,
            Lists.newArrayList("bat", "vAl"), Lists.newArrayList("VA"), true},

        new Object[] {"RulePredicates.ANY_NOT_IN_CONTAINS",
            RulePredicateDefinitions.ANY_NOT_IN_CONTAINS, Lists.newArrayList("bat", "val"),
            Lists.newArrayList("bal", "val"), false},
        new Object[] {"RulePredicates.ANY_NOT_IN_CONTAINS",
            RulePredicateDefinitions.ANY_NOT_IN_CONTAINS, Lists.newArrayList("bat", "val"),
            Lists.newArrayList("bal", "vat"), true},
        new Object[] {"RulePredicates.ANY_NOT_IN_CONTAINS",
            RulePredicateDefinitions.ANY_NOT_IN_CONTAINS, Lists.newArrayList("bat", "val"),
            Lists.newArrayList(), true},
        new Object[] {"RulePredicates.ANY_NOT_IN_CONTAINS",
            RulePredicateDefinitions.ANY_NOT_IN_CONTAINS, null, Lists.newArrayList(), true},
        new Object[] {"RulePredicates.ANY_NOT_IN_CONTAINS",
            RulePredicateDefinitions.ANY_NOT_IN_CONTAINS, Lists.newArrayList("bat", "val"), null,
            true},
        new Object[] {"RulePredicates.ANY_NOT_IN_CONTAINS",
            RulePredicateDefinitions.ANY_NOT_IN_CONTAINS, Lists.newArrayList("bat", "val"),
            Lists.newArrayList("bal", "VAL"), false},
        new Object[] {"RulePredicates.ANY_NOT_IN_CONTAINS",
            RulePredicateDefinitions.ANY_NOT_IN_CONTAINS, Lists.newArrayList("bat", "VAl"),
            Lists.newArrayList("baT", "vAl"), false},

        new Object[] {"RulePredicates.ANY_IN_CONTAINS_Set",
            RulePredicateDefinitions.ANY_IN_CONTAINS, Sets.newHashSet("bat", "VAl"),
            Lists.newArrayList("bt", "va"), true},
        new Object[] {"RulePredicates.ANY_NOT_IN_CONTAINS_set",
            RulePredicateDefinitions.ANY_NOT_IN_CONTAINS, Lists.newArrayList("bat", "val"),
            Sets.newHashSet("bal", "VAL"), false},
        new Object[] {"RulePredicates.ANY_IN_EQUALS_set", RulePredicateDefinitions.ANY_IN_EQUALS,
            Lists.newArrayList("bat", "VAl"), Sets.newHashSet("bak", "vAl"), true},

        new Object[] {"RulePredicates.NOT_CONTAINS_ANY", RulePredicateDefinitions.NOT_CONTAINS_ANY,
            Lists.newArrayList("CT", "MR"), Sets.newHashSet("SR", "MT"), true},
        new Object[] {"RulePredicates.NOT_CONTAINS_ANY", RulePredicateDefinitions.NOT_CONTAINS_ANY,
            Lists.newArrayList("CT", "MR"), Sets.newHashSet("SR", "mt"), true},

        new Object[] {"RulePredicates.NOT_CONTAINS_ANY", RulePredicateDefinitions.NOT_CONTAINS_ANY,
            Lists.newArrayList("CT", "MR"), Sets.newHashSet("MR", "US"), false},

        new Object[] {"RulePredicates.CONTAINS_ALL", RulePredicateDefinitions.CONTAINS_ALL,
            Lists.newArrayList("CT", "MR"), Sets.newHashSet("CT", "MR"), true},
        new Object[] {"RulePredicates.CONTAINS_ALL", RulePredicateDefinitions.CONTAINS_ALL,
            Lists.newArrayList("MR", "CP", "CT"), Sets.newHashSet("CT", "MR"), true},
        new Object[] {"RulePredicates.CONTAINS_ALL", RulePredicateDefinitions.CONTAINS_ALL,
            Lists.newArrayList("MR", "CT"), Sets.newHashSet("CT", "CP", "MR"), false},

        // the match is case sensitive
        new Object[] {"RulePredicates.CONTAINS_ALL", RulePredicateDefinitions.CONTAINS_ALL,
            Lists.newArrayList("CT", "MR"), Sets.newHashSet("CT", "mr"), false},
        new Object[] {"RulePredicates.CONTAINS_ALL", RulePredicateDefinitions.CONTAINS_ALL,
            Lists.newArrayList("CT", "MR"), Sets.newHashSet("US", "MR"), false},

    };

  }

  @ParameterizedTest
  @MethodSource("parametersToTestPredicate")
  public void test_predicate_with_values(String testName,
      BiPredicate<Iterable<String>, Iterable<String>> predicate, Iterable<String> var1,
      Iterable<String> var2, boolean expected) {

    assertThat(predicate.test(var1, var2)).isEqualTo(expected);
  }

}
