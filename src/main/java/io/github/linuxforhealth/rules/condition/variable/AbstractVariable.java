/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.condition.variable;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.ClassUtils;
import io.github.linuxforhealth.rules.fact.DataValues;


/**
 * Defines Variable object that can be used during the condition evaluation.
 * 
 *
 * @author pbhallam
 */
abstract class AbstractVariable implements Variable {



  @Override
  public List<Object> extractVariableValue(DataValues attributes, Class<?> klass) {
    Optional<Object> value = fetchValue(attributes, klass);
    if (value.isEmpty()) {
      return new ArrayList<>();
    }
    return getValue(value.get(), klass);

  }


  protected abstract Optional<Object> fetchValue(DataValues attributes, Class<?> klass);

  protected List<Object> getValue(Object value, Class<?> klass) {
    List<Object> values = new ArrayList<>();
    if (value instanceof List && Collection.class != klass) {
      values.addAll((List) value);
    } else if (value != null && klass.isAssignableFrom(value.getClass())) {
      values.add(value);
    } else if (value != null) {
      throw new IllegalStateException(
          "Value type for the attribute extracted from fact does not match value type required by the Condition operator. Expected type:"
              + klass + " actual type: " + ClassUtils.getPackageCanonicalName(value, null));
    }

    return values;
  }

}
