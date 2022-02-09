/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.conditions.predicate;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.function.BiPredicate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import io.github.linuxforhealth.rules.condition.predicate.RulePredicateDefinitions;

class RulePredicatesStringTest {

  public static Object[] parametersToTestPredicate() throws ClassNotFoundException {
    return new Object[] {
        new Object[] {"RulePredicates.EQUALS", RulePredicateDefinitions.EQUALS_IC, "val", "val",
            true},
        new Object[] {"RulePredicates.EQUALS", RulePredicateDefinitions.EQUALS_IC, "val", "bal",
            false},
        new Object[] {"RulePredicates.EQUALS", RulePredicateDefinitions.EQUALS_IC, "", "va", false},
        new Object[] {"RulePredicates.EQUALS", RulePredicateDefinitions.EQUALS_IC, "val", "",
            false},
        new Object[] {"RulePredicates.EQUALS", RulePredicateDefinitions.EQUALS_IC, null, "", false},
        new Object[] {"RulePredicates.EQUALS", RulePredicateDefinitions.EQUALS_IC, "val", null,
            false},
        new Object[] {"RulePredicates.EQUALS", RulePredicateDefinitions.EQUALS_IC, "val", "VAL",
            true},
        new Object[] {"RulePredicates.EQUALS", RulePredicateDefinitions.EQUALS_IC, "vAl", "VAl",
            true},

        new Object[] {"RulePredicates.NOT_EQUALS", RulePredicateDefinitions.NOT_EQUALS, "val",
            "val", false},
        new Object[] {"RulePredicates.NOT_EQUALS", RulePredicateDefinitions.NOT_EQUALS, "val",
            "bal", true},
        new Object[] {"RulePredicates.NOT_EQUALS", RulePredicateDefinitions.NOT_EQUALS, "", "va",
            true},
        new Object[] {"RulePredicates.NOT_EQUALS", RulePredicateDefinitions.NOT_EQUALS, "val", "",
            true},
        new Object[] {"RulePredicates.NOT_EQUALS", RulePredicateDefinitions.NOT_EQUALS, null, "",
            true},
        new Object[] {"RulePredicates.NOT_EQUALS", RulePredicateDefinitions.NOT_EQUALS, "val", null,
            true},
        new Object[] {"RulePredicates.NOT_EQUALS", RulePredicateDefinitions.NOT_EQUALS, "val",
            "VAL", false},
        new Object[] {"RulePredicates.NOT_EQUALS", RulePredicateDefinitions.NOT_EQUALS, "vAl",
            "VAl", false},

        new Object[] {"RulePredicates.CONTAINS", RulePredicateDefinitions.CONTAINS, "val", "val",
            true},
        new Object[] {"RulePredicates.CONTAINS", RulePredicateDefinitions.CONTAINS, "val", "al",
            true},
        new Object[] {"RulePredicates.CONTAINS", RulePredicateDefinitions.CONTAINS, "val", "ba",
            false},
        new Object[] {"RulePredicates.CONTAINS", RulePredicateDefinitions.CONTAINS, "", "va",
            false},
        new Object[] {"RulePredicates.CONTAINS", RulePredicateDefinitions.CONTAINS, "val", "",
            true},
        new Object[] {"RulePredicates.CONTAINS", RulePredicateDefinitions.CONTAINS, null, "",
            false},
        new Object[] {"RulePredicates.CONTAINS", RulePredicateDefinitions.CONTAINS, "val", null,
            false},
        new Object[] {"RulePredicates.CONTAINS", RulePredicateDefinitions.CONTAINS, "val", "AL",
            true},
        new Object[] {"RulePredicates.CONTAINS", RulePredicateDefinitions.CONTAINS, "vAl", "VA",
            true},

        new Object[] {"RulePredicates.NOT_CONTAINS", RulePredicateDefinitions.NOT_CONTAINS, "val",
            "val", false},
        new Object[] {"RulePredicates.NOT_CONTAINS", RulePredicateDefinitions.NOT_CONTAINS, "val",
            "bal", true},
        new Object[] {"RulePredicates.NOT_CONTAINS", RulePredicateDefinitions.NOT_CONTAINS, "val",
            "", false},
        new Object[] {"RulePredicates.NOT_CONTAINS", RulePredicateDefinitions.NOT_CONTAINS, null,
            "", true},
        new Object[] {"RulePredicates.NOT_CONTAINS", RulePredicateDefinitions.NOT_CONTAINS, "val",
            null, true},
        new Object[] {"RulePredicates.NOT_CONTAINS", RulePredicateDefinitions.NOT_CONTAINS, "val",
            "VAL", false},
        new Object[] {"RulePredicates.NOT_CONTAINS", RulePredicateDefinitions.NOT_CONTAINS, "vAl",
            "VAl", false},

        new Object[] {"RulePredicates.STARTS_WITH", RulePredicateDefinitions.STARTS_WITH, "val",
            "va", true},
        new Object[] {"RulePredicates.STARTS_WITH", RulePredicateDefinitions.STARTS_WITH, "val",
            "tva", false},
        new Object[] {"RulePredicates.STARTS_WITH", RulePredicateDefinitions.STARTS_WITH, "val",
            "Va", true},
        new Object[] {"RulePredicates.STARTS_WITH", RulePredicateDefinitions.STARTS_WITH, "VAl",
            "va", true},
        new Object[] {"RulePredicates.STARTS_WITH", RulePredicateDefinitions.STARTS_WITH, "", "va",
            false},
        new Object[] {"RulePredicates.STARTS_WITH", RulePredicateDefinitions.STARTS_WITH, "val", "",
            true},
        new Object[] {"RulePredicates.STARTS_WITH", RulePredicateDefinitions.STARTS_WITH, null, "",
            false},

        new Object[] {"RulePredicates.NOT_STARTS_WITH", RulePredicateDefinitions.NOT_STARTS_WITH,
            "val", "va", false},
        new Object[] {"RulePredicates.NOT_STARTS_WITH", RulePredicateDefinitions.NOT_STARTS_WITH,
            "val", "tva", true},
        new Object[] {"RulePredicates.NOT_STARTS_WITH", RulePredicateDefinitions.NOT_STARTS_WITH,
            "val", "Va", false},
        new Object[] {"RulePredicates.NOT_STARTS_WITH", RulePredicateDefinitions.NOT_STARTS_WITH,
            "val", "VaLL", true},
        new Object[] {"RulePredicates.NOT_STARTS_WITH", RulePredicateDefinitions.NOT_STARTS_WITH,
            "VAl", "KVAL", true},
        new Object[] {"RulePredicates.NOT_STARTS_WITH", RulePredicateDefinitions.NOT_STARTS_WITH,
            "", "va", true},
        new Object[] {"RulePredicates.NOT_STARTS_WITH", RulePredicateDefinitions.NOT_STARTS_WITH,
            "val", "", false},
        new Object[] {"RulePredicates.NOT_STARTS_WITH", RulePredicateDefinitions.NOT_STARTS_WITH,
            null, "", true},

        new Object[] {"RulePredicates.ENDS_WITH", RulePredicateDefinitions.ENDS_WITH, "val", "val",
            true},
        new Object[] {"RulePredicates.ENDS_WITH", RulePredicateDefinitions.ENDS_WITH, "val", "tva",
            false},
        new Object[] {"RulePredicates.ENDS_WITH", RulePredicateDefinitions.ENDS_WITH, "val", "aL",
            true},
        new Object[] {"RulePredicates.ENDS_WITH", RulePredicateDefinitions.ENDS_WITH, "VAl", "Al",
            true},
        new Object[] {"RulePredicates.ENDS_WITH", RulePredicateDefinitions.ENDS_WITH, "", "va",
            false},
        new Object[] {"RulePredicates.ENDS_WITH", RulePredicateDefinitions.ENDS_WITH, "val", "",
            true},
        new Object[] {"RulePredicates.ENDS_WITH", RulePredicateDefinitions.ENDS_WITH, null, "",
            false},

        new Object[] {"RulePredicates.NOT_ENDS_WITH", RulePredicateDefinitions.NOT_ENDS_WITH, "val",
            "val", false},
        new Object[] {"RulePredicates.NOT_ENDS_WITH", RulePredicateDefinitions.NOT_ENDS_WITH, "val",
            "vab", true},
        new Object[] {"RulePredicates.NOT_ENDS_WITH", RulePredicateDefinitions.NOT_ENDS_WITH, "val",
            "aL", false},
        new Object[] {"RulePredicates.NOT_ENDS_WITH", RulePredicateDefinitions.NOT_ENDS_WITH, "val",
            "aLL", true},
        new Object[] {"RulePredicates.NOT_ENDS_WITH", RulePredicateDefinitions.NOT_ENDS_WITH, "VAl",
            "VALK", true},
        new Object[] {"RulePredicates.NOT_ENDS_WITH", RulePredicateDefinitions.NOT_ENDS_WITH, "",
            "va", true},
        new Object[] {"RulePredicates.NOT_ENDS_WITH", RulePredicateDefinitions.NOT_ENDS_WITH, "val",
            "", false},
        new Object[] {"RulePredicates.NOT_ENDS_WITH", RulePredicateDefinitions.NOT_ENDS_WITH, null,
            "", true},

        new Object[] {"RulePredicates.STRING_IS_NULL", RulePredicateDefinitions.STRING_IS_NULL,
            null, null, true},
        new Object[] {"RulePredicates.STRING_IS_NULL", RulePredicateDefinitions.STRING_IS_NULL,
            "CT", null, false},

        new Object[] {"RulePredicates.STRING_IS_NULL", RulePredicateDefinitions.STRING_IS_NULL, "",
            null, false},
        new Object[] {"RulePredicates.STRING_IS_NULL", RulePredicateDefinitions.STRING_IS_NULL,
            "  ", null, false}

    };

  }

  @ParameterizedTest
  @MethodSource("parametersToTestPredicate")
  public void test_predicate_with_values(String testName, BiPredicate<String, String> predicate,
      String var1, String var2, boolean expected) {

    assertThat(predicate.test(var1, var2)).isEqualTo(expected);
  }

}
