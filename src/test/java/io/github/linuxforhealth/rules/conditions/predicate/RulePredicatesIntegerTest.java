/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.conditions.predicate;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.function.BiPredicate;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import io.github.linuxforhealth.rules.condition.predicate.RulePredicateDefinitions;



class RulePredicatesIntegerTest {

  public static Stream<Arguments> parametersToTestPredicate() throws ClassNotFoundException {
    return Stream.of(
        Arguments.of("RulePredicates.EQUAL_TO", RulePredicateDefinitions.EQUAL_TO, 5, 5, true),
        Arguments.of("RulePredicates.EQUAL_TO", RulePredicateDefinitions.EQUAL_TO, 3, 7, false),
        Arguments.of("RulePredicates.EQUAL_TO", RulePredicateDefinitions.EQUAL_TO, 4, null, false),
        Arguments.of("RulePredicates.GREATER_THAN", RulePredicateDefinitions.GREATER_THAN, 3, 3,
            false),
        Arguments.of("RulePredicates.GREATER_THAN", RulePredicateDefinitions.GREATER_THAN, 3, 2,
            true),
        Arguments.of("RulePredicates.GREATER_THAN", RulePredicateDefinitions.GREATER_THAN, 3, 4,
            false),
        Arguments.of("RulePredicates.GREATER_THAN_OR_EQUAL_TO",
            RulePredicateDefinitions.GREATER_THAN_OR_EQUAL_TO, 3, 3, true),
        Arguments.of("RulePredicates.GREATER_THAN_OR_EQUAL_TO",
            RulePredicateDefinitions.GREATER_THAN_OR_EQUAL_TO, 3, 4, false),
        Arguments.of("RulePredicates.GREATER_THAN_OR_EQUAL_TO",
            RulePredicateDefinitions.GREATER_THAN_OR_EQUAL_TO, 3, -3, true),

        Arguments.of("RulePredicates.LESS_THAN", RulePredicateDefinitions.LESS_THAN, 3, 2, false),
        Arguments.of("RulePredicates.LESS_THAN", RulePredicateDefinitions.LESS_THAN, 3, 3, false),
        Arguments.of("RulePredicates.LESS_THAN_OR_EQUAL_TO",
            RulePredicateDefinitions.LESS_THAN_OR_EQUAL_TO, 3, 3, true),
        Arguments.of("RulePredicates.LESS_THAN_OR_EQUAL_TO",
            RulePredicateDefinitions.LESS_THAN_OR_EQUAL_TO, 3, 4, true),
        Arguments.of("RulePredicates.LESS_THAN_OR_EQUAL_TO",
            RulePredicateDefinitions.LESS_THAN_OR_EQUAL_TO, -3, 3, true));


  }


  @ParameterizedTest
  @MethodSource("parametersToTestPredicate")

  public void test_predicate_with_values(String testName, BiPredicate<Integer, Integer> predicate,
      Integer var1, Integer var2, boolean expected) {

    assertThat(predicate.test(var1, var2)).isEqualTo(expected);
  }

}
