/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.condition.variable;



import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Lists;
import io.github.linuxforhealth.rules.condition.Specification;
import io.github.linuxforhealth.rules.condition.VariableUtils;
import io.github.linuxforhealth.rules.fact.DataValues;
import io.github.linuxforhealth.rules.fact.ValueType;


/**
 * Defines Variable object that can be used during the condition evaluation.
 * 
 *
 * @author pbhallam
 */
public class SimpleVariable extends AbstractVariable {
  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleVariable.class);

  private Specification spec;


  public SimpleVariable(Specification spec) {
    this.spec = spec;
  }

  public SimpleVariable(String spec) {
    this.spec = VariableUtils.extractSpec(spec);
  }


  @Override
  public List<Specification> getSpec() {
    return Lists.newArrayList(this.spec);
  }

  @Override
  public Optional<ValueType> extractAttribute(DataValues attributes) {
    Optional<ValueType> val = this.spec.getAttribute(attributes, null);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Returning value {} for context {} for specs {}", val, attributes, this.spec);
    }
    return val;
  }



  @Override
  protected Optional<Object> fetchValue(DataValues attributes, Class<?> klass) {
    Optional<ValueType> optionalAttr = extractAttribute(attributes);
    Object value = null;
    if (optionalAttr.isPresent()) {
      value = VariableUtils.getValue(optionalAttr.get(), klass, spec.getIndex());
    }
    return Optional.ofNullable(value);

  }

  @Override
  public String toString() {
    return "SimpleVariable [spec=" + spec + "]";
  }


}
