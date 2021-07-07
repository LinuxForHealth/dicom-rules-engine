/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.condition.variable;



import java.util.List;
import java.util.Optional;
import io.github.linuxforhealth.rules.condition.Specification;
import io.github.linuxforhealth.rules.fact.ContextValues;
import io.github.linuxforhealth.rules.fact.ValueType;


/**
 * Defines Variable object that can be used during the condition evaluation.
 * 
 *
 * @author pbhallam
 */
public interface Variable {



  List<Specification> getSpec();


  List<Object> extractVariableValue(ContextValues attributes, Class<?> klass);

  Optional<ValueType> extractAttribute(ContextValues attributes);

}
