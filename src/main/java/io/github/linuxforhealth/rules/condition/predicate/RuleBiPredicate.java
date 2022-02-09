/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.condition.predicate;

import java.util.Collection;
import java.util.function.BiPredicate;

/**
 * This is rules predicate enum class that defines different types of predicates and type of input
 * object it acts on
 * 
 * @author pbhallam@us.ibm.com
 *
 */
public enum RuleBiPredicate {

  L_GREATER_THAN(RulePredicateDefinitions.GREATER_THAN_L, Long.class, Long.class), //
  L_EQUAL_TO(RulePredicateDefinitions.EQUAL_TO_L, Long.class, Long.class), //
  L_LESS_THAN(RulePredicateDefinitions.LESS_THAN_L, Long.class, Long.class), //
  L_GREATER_THAN_OR_EQUAL_TO(RulePredicateDefinitions.GREATER_THAN_OR_EQUAL_TO_L, Long.class,
      Long.class), //
  L_LESS_THAN_OR_EQUAL_TO(RulePredicateDefinitions.LESS_THAN_OR_EQUAL_TO_L, Long.class, Long.class), //


  GREATER_THAN(RulePredicateDefinitions.GREATER_THAN, Integer.class, Integer.class), //
  EQUAL_TO(RulePredicateDefinitions.EQUAL_TO, Integer.class, Integer.class), //
  LESS_THAN(RulePredicateDefinitions.LESS_THAN, Integer.class, Integer.class), //
  GREATER_THAN_OR_EQUAL_TO(RulePredicateDefinitions.GREATER_THAN_OR_EQUAL_TO, Integer.class,
      Integer.class), //
  LESS_THAN_OR_EQUAL_TO(RulePredicateDefinitions.LESS_THAN_OR_EQUAL_TO, Integer.class,
      Integer.class), //

  CONTAINS(RulePredicateDefinitions.CONTAINS, String.class, String.class), //
  NOT_CONTAINS(RulePredicateDefinitions.NOT_CONTAINS, String.class, String.class), //
  EQUALS(RulePredicateDefinitions.EQUALS_IC, String.class, String.class), //
  NOT_EQUALS(RulePredicateDefinitions.NOT_EQUALS, String.class, String.class), //
  STARTS_WITH(RulePredicateDefinitions.STARTS_WITH, String.class, String.class), //
  NOT_STARTS_WITH(RulePredicateDefinitions.NOT_STARTS_WITH, String.class, String.class), //
  ENDS_WITH(RulePredicateDefinitions.ENDS_WITH, String.class, String.class), //
  NOT_ENDS_WITH(RulePredicateDefinitions.NOT_ENDS_WITH, String.class, String.class), //

  IN_EQUALS(RulePredicateDefinitions.IN_EQUALS, String.class, Collection.class), //
  IN_CONTAINS(RulePredicateDefinitions.IN_CONTAINS, String.class, Collection.class), //
  NOT_IN_EQUALS(RulePredicateDefinitions.NOT_IN_EQUALS, String.class, Collection.class), //
  NOT_IN_CONTAINS(RulePredicateDefinitions.NOT_IN_CONTAINS, String.class, Collection.class), //

  ANY_CONTAINS(RulePredicateDefinitions.ANY_CONTAINS, Collection.class, String.class), //
  ANY_NOT_CONTAINS(RulePredicateDefinitions.ANY_NOT_CONTAINS, Collection.class, String.class), //

  ANY_EQUALS(RulePredicateDefinitions.ANY_EQUALS, Collection.class, String.class), //
  ANY_NOT_EQUALS(RulePredicateDefinitions.ANY_NOT_EQUALS, Collection.class, String.class), //

  ANY_IN_EQUALS(RulePredicateDefinitions.ANY_IN_EQUALS, Collection.class, Collection.class), //
  ANY_NOT_IN_EQUALS(RulePredicateDefinitions.ANY_NOT_IN_EQUALS, Collection.class, Collection.class), //

  ANY_IN_CONTAINS(RulePredicateDefinitions.ANY_IN_CONTAINS, Collection.class, Collection.class), //
  ANY_NOT_IN_CONTAINS(RulePredicateDefinitions.ANY_NOT_IN_CONTAINS, Collection.class,
      Collection.class), //


  NOT_CONTAINS_ANY(RulePredicateDefinitions.NOT_CONTAINS_ANY, Collection.class, Collection.class), //
  CONTAINS_ALL(RulePredicateDefinitions.CONTAINS_ALL, Collection.class, Collection.class), //

  LIST_SIZE_EQUAL(RulePredicateDefinitions.LIST_SIZE_EQUAL, Collection.class, Integer.class), //

  LIST_SIZE_GREATER_THAN(RulePredicateDefinitions.LIST_SIZE_GREATER_THAN, Collection.class,
      Integer.class), //

  LIST_SIZE_LESS_THAN(RulePredicateDefinitions.LIST_SIZE_LESS_THAN, Collection.class,
      Integer.class); //

  private BiPredicate<?, ?> predicate;
  private Class<?> klassT;
  private Class<?> klassU;

  RuleBiPredicate(BiPredicate<?, ?> p, Class<?> klassT, Class<?> klassU) {
    this.predicate = p;
    this.klassT = klassT;
    this.klassU = klassU;
  }

  public BiPredicate getPredicate() {
    return this.predicate;

  }

  public Class<?> getKlassT() {
    return this.klassT;
  }

  public Class<?> getKlassU() {
    return this.klassU;
  }

}
