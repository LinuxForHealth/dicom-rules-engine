/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.condition;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import io.github.linuxforhealth.rules.fact.ListValueType;
import io.github.linuxforhealth.rules.fact.ValueType;

public class VariableUtils {

  private VariableUtils() {}


  public static Object getValue(ValueType attribute, Class<?> classType, int index) {
    if (attribute == null) {
      return null;
    } else if (!(attribute instanceof ListValueType)) {
      return attribute.getValue();
    }

    Object value = attribute.getValue();

    if (value != null && Collection.class == classType
        && Collection.class.isAssignableFrom(value.getClass())) {
      return value;
    }


    else if (value instanceof List && Collection.class != classType) {

      List<?> c = (List<?>) value;
      if (!c.isEmpty() && index > -1) {
        return c.get(index);
      } else if (!c.isEmpty() && c.get(0).getClass() != classType && c.get(0) instanceof List) {
        List<List<?>> temp = (List<List<?>>) c;
        return temp.stream().flatMap(List::stream).collect(Collectors.toList());
      } else if (!c.isEmpty() && c.get(0).getClass() == classType) {
        return c;
      } else {
        return null;
      }
    } else {
      throw new IllegalStateException("Cannot extract value from  Attribute  " + attribute);
    }


  }


  public static Specification extractSpec(String tok) {
    if (StringUtils.endsWith(tok, "]")) {
      String[] sections = StringUtils.split(tok, "[");
      return new Specification(sections[0],
          NumberUtils.toInt(StringUtils.remove(sections[1], "]")));
    } else {
      return new Specification(tok);
    }
  }

}
