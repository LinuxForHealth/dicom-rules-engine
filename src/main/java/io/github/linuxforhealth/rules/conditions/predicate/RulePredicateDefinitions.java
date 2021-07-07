/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.conditions.predicate;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * This is a static class that defines different types of Rule predicate and there operations.
 * 
 * @author pbhallam@us.ibm.com
 *
 */

public class RulePredicateDefinitions {

  public static final BiPredicate<Integer, Integer> GREATER_THAN =
      (x, y) -> x != null && y != null && x > y;
  public static final BiPredicate<Integer, Integer> EQUAL_TO =
      (x, y) -> x != null && y != null && x.equals(y);
  public static final BiPredicate<Integer, Integer> LESS_THAN =
      (x, y) -> x != null && y != null && x < y;
  public static final BiPredicate<Integer, Integer> GREATER_THAN_OR_EQUAL_TO =
      (x, y) -> x != null && y != null && x >= y;
  public static final BiPredicate<Integer, Integer> LESS_THAN_OR_EQUAL_TO =
      (x, y) -> x != null && y != null && x <= y;


  public static final BiPredicate<Long, Long> GREATER_THAN_L = (x, y) -> x > y;
  public static final BiPredicate<Long, Long> EQUAL_TO_L = (x, y) -> x.equals(y);
  public static final BiPredicate<Long, Long> LESS_THAN_L = (x, y) -> x < y;
  public static final BiPredicate<Long, Long> GREATER_THAN_OR_EQUAL_TO_L = (x, y) -> x >= y;
  public static final BiPredicate<Long, Long> LESS_THAN_OR_EQUAL_TO_L = (x, y) -> x <= y;

  public static final BiPredicate<String, String> EQUALS_IC = StringUtils::equalsIgnoreCase;
  public static final BiPredicate<String, String> NOT_EQUALS = EQUALS_IC.negate();

  public static final BiPredicate<String, String> STARTS_WITH = StringUtils::startsWithIgnoreCase;
  public static final BiPredicate<String, String> NOT_STARTS_WITH = STARTS_WITH.negate();

  public static final BiPredicate<String, String> ENDS_WITH = StringUtils::endsWithIgnoreCase;
  public static final BiPredicate<String, String> NOT_ENDS_WITH = ENDS_WITH.negate();

  public static final BiPredicate<String, String> CONTAINS = StringUtils::containsIgnoreCase;
  public static final BiPredicate<String, String> NOT_CONTAINS = CONTAINS.negate();
  public static final BiPredicate<String, String> STRING_IS_NULL = (x, y) -> x == null;

  public static final BiPredicate<String, Collection<String>> IN_EQUALS =
      (x, y) -> y != null && y.stream().anyMatch(d -> StringUtils.equalsIgnoreCase(x, d));
  public static final BiPredicate<String, Collection<String>> NOT_IN_EQUALS = IN_EQUALS.negate();
  public static final BiPredicate<String, Collection<String>> IN_CONTAINS =
      (x, y) -> y != null && y.stream().anyMatch(d -> StringUtils.containsIgnoreCase(x, d));
  public static final BiPredicate<String, Collection<String>> NOT_IN_CONTAINS =
      IN_CONTAINS.negate();

  public static final BiPredicate<Collection<String>, String> ANY_CONTAINS =
      (x, y) -> x != null && x.stream().anyMatch(d -> StringUtils.containsIgnoreCase(d, y));
  public static final BiPredicate<Collection<String>, String> ANY_NOT_CONTAINS =
      ANY_CONTAINS.negate();

  public static final BiPredicate<Collection<String>, String> ANY_EQUALS =
      (x, y) -> x != null && x.stream().anyMatch(d -> StringUtils.equalsIgnoreCase(d, y));
  public static final BiPredicate<Collection<String>, String> ANY_NOT_EQUALS = ANY_EQUALS.negate();

  public static final BiPredicate<Collection<String>, Collection<String>> ANY_IN_EQUALS =
      (x, y) -> x != null && y != null && x.stream()
          .anyMatch(a -> y.stream().anyMatch(b -> StringUtils.equalsAnyIgnoreCase(b, a)));
  public static final BiPredicate<Collection<String>, Collection<String>> ANY_NOT_IN_EQUALS =
      ANY_IN_EQUALS.negate();

  public static final BiPredicate<Collection<String>, Collection<String>> ANY_IN_CONTAINS =
      (x, y) -> x != null && y != null && x.stream()
          .anyMatch(a -> y.stream().anyMatch(b -> StringUtils.containsIgnoreCase(a, b)));
  public static final BiPredicate<Collection<String>, Collection<String>> ANY_NOT_IN_CONTAINS =
      ANY_IN_CONTAINS.negate();


  public static final BiPredicate<Collection<String>, Integer> LIST_SIZE_EQUAL =
      (x, y) -> x != null && y != null && x.size() == y;

  public static final BiPredicate<Collection<String>, Integer> LIST_SIZE_GREATER_THAN =
      (x, y) -> x != null && y != null && x.size() > y;

  public static final BiPredicate<Collection<String>, Integer> LIST_SIZE_LESS_THAN =
      (x, y) -> x != null && y != null && x.size() < y;

  public static final BiPredicate<Collection<String>, Collection<String>> CONTAINS_ALL =
      (x, y) -> x != null && y != null && x.containsAll(y);

  public static final BiPredicate<Collection<String>, Collection<String>> NOT_CONTAINS_ANY =
      (x, y) -> x != null && y != null && x.stream()
          .allMatch(a -> y.stream().allMatch(b -> !StringUtils.containsIgnoreCase(a, b)));


  public static final Predicate<Collection<?>> IS_EMPTY = CollectionUtils::isEmpty;
  public static final Predicate<Collection<?>> IS_NOT_EMPTY = IS_EMPTY.negate();
  public static final Predicate<Object> IS_NULL = Objects::isNull;
  public static final Predicate<Object> IS_NON_NULL = Objects::nonNull;


  private RulePredicateDefinitions() {}

}
