/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.conditions.predicate;

import java.util.Collection;
import java.util.function.Predicate;


/**
 * This is rules predicate enum class that defines different types of predicates and type of input
 * object it acts on
 * 
 * @author pbhallam@us.ibm.com
 *
 */
public enum RulePredicate {


  IS_EMPTY(RulePredicateDefinitions.IS_EMPTY, Collection.class), //
  IS_NOT_EMPTY(RulePredicateDefinitions.IS_NOT_EMPTY, Collection.class), //
  IS_NULL(RulePredicateDefinitions.IS_NULL, Object.class), //
  IS_NOT_NULL(RulePredicateDefinitions.IS_NON_NULL, Collection.class); //


  private Predicate<?> predicate;
  private Class<?> klassT;


  RulePredicate(Predicate<?> p, Class<?> klassT) {
    this.predicate = p;
    this.klassT = klassT;

  }

  public Predicate getPredicate() {
    return this.predicate;

  }

  public Class<?> getKlassT() {
    return this.klassT;
  }


}
