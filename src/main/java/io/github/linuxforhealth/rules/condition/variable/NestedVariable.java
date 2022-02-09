/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package io.github.linuxforhealth.rules.condition.variable;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.text.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.linuxforhealth.rules.condition.Constants;
import io.github.linuxforhealth.rules.condition.Specification;
import io.github.linuxforhealth.rules.condition.VariableUtils;
import io.github.linuxforhealth.rules.fact.DataValues;
import io.github.linuxforhealth.rules.fact.ListValueType;
import io.github.linuxforhealth.rules.fact.MapValueType;
import io.github.linuxforhealth.rules.fact.ValueType;


/**
 * Defines Variable object that can be used during the condition evaluation.
 * 
 *
 * @author pbhallam
 */
public class NestedVariable extends AbstractVariable {
  private static final Logger LOGGER = LoggerFactory.getLogger(NestedVariable.class);

  private List<Specification> specs;
  private Specification lastSpec;


  public NestedVariable(String spec) {
    this(spec, Constants.DEFAULT_VARIABLE_DELIMITER);
  }

  public NestedVariable(String spec, String delimiter) {
    specs = new ArrayList<>();
    StringTokenizer tokn = new StringTokenizer(spec, delimiter);
    while (tokn.hasNext()) {
      String tok = tokn.nextToken();

      lastSpec = VariableUtils.extractSpec(tok);

      specs.add(lastSpec);
    }
  }



  @Override
  public List<Specification> getSpec() {
    return this.specs;
  }



  @Override
  public Optional<ValueType> extractAttribute(DataValues attributes) {
    return Optional.ofNullable(fetchAttribute(attributes, this.specs));
  }



  public ValueType fetchAttribute(DataValues attributes, List<Specification> specs) {

    DataValues temp = attributes;
    List<Specification> localSpecs = new ArrayList<>(specs);

    ValueType v = null;
    boolean extracted = false;
    while (!localSpecs.isEmpty() && !extracted) {
      Specification s = localSpecs.remove(0);


      v = getValueFromSpec(s, temp);
      if (localSpecs.isEmpty() || v == null) {
        return v;
      } else if (v instanceof ListValueType) {
        List<Object> values = new ArrayList<Object>();
        ListValueType listAttr = (ListValueType) v;
        List<Specification> tempSpecs = new ArrayList<>(localSpecs);
        for (Object item : listAttr.getValue()) {
          if (item instanceof MapValueType) {
            MapValueType mapAttr = (MapValueType) item;
            temp = new DataValues(mapAttr.getValue());

            ValueType optionalValue = fetchAttribute(temp, tempSpecs);
            if (optionalValue != null) {
              values.add(optionalValue.getValue());
            }
          }
        }
        v = new ListValueType(values);
        extracted = true;

      } else if (v instanceof MapValueType) {
        MapValueType mapAttr = (MapValueType) v;
        temp = new DataValues(mapAttr.getValue());
        v = fetchAttribute(temp, localSpecs);
        extracted = true;
      } else {
        throw new IllegalStateException("Expected value type to be a map");
      }
    }
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Returning value {} for context {} for specs {}", v, attributes, specs);
    }
    return v;
  }


  private ValueType getValueFromSpec(Specification s, DataValues temp) {

    Optional<ValueType> att = s.getAttribute(temp, null);
    if (att.isPresent()) {
      return att.get();
    }

    return null;
  }



  @Override
  protected Optional<Object> fetchValue(DataValues attributes, Class<?> klass) {
    Optional<ValueType> optionalAttr = extractAttribute(attributes);
    Object value = null;
    if (optionalAttr.isPresent()) {
      value = VariableUtils.getValue(optionalAttr.get(), klass, lastSpec.getIndex());
    }
    return Optional.ofNullable(value);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("specs", specs).toString();
  }
}
