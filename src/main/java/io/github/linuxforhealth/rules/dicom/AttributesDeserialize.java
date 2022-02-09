/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.dicom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.linuxforhealth.rules.fact.DataValues;
import io.github.linuxforhealth.rules.fact.ListValueType;
import io.github.linuxforhealth.rules.fact.MapValueType;
import io.github.linuxforhealth.rules.fact.NullAttribute;
import io.github.linuxforhealth.rules.fact.ValueType;

public class AttributesDeserialize extends StdDeserializer<DataValues> {
  /**
   * 
   */
  private static final long serialVersionUID = -85202262082356465L;
  private static final List<String> STRING_VR_TYPES = List.of("AE", "AS", "AT", "CS", "DS",
      "LO", "LT", "OB", "OD", "OF", "OW", "PN", "SH", "UI", "UT");
  private static final List<String> DATE_VR_TYPES = List.of("DA");
  private static final List<String> TIME_VR_TYPES = List.of("TM");
  private static final List<String> DATE_TIME_VR_TYPES = List.of("DT");
  private static final List<String> FLOAT_VR_TYPES = List.of("FL", "FD");
  private static final List<String> INTEGER_VR_TYPES = List.of("IS", "SS", "US");
  private static final List<String> LONG_VR_TYPES = List.of("SL", "UL");
  private static final List<String> NUMBER_VR_TYPES =
      List.of("SL", "UL", "FL", "FD", "IS", "SS", "US");



  private Set<String> tags;

  public AttributesDeserialize(Set<String> tags) {
    this(null, tags);
  }



  public AttributesDeserialize(Class<?> vc, Set<String> tags) {
    super(vc);
    this.tags = tags;

  }

  @Override
  public DataValues deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

    JsonNode node = p.readValueAsTree();
    if (node == null) {
      return null;
    }
    DataValues attrs = new DataValues();
    if (node instanceof ObjectNode) {
      ObjectNode objNode = (ObjectNode) node;
      objNode.fields().forEachRemaining(a -> {
        if (this.tags.stream().anyMatch(ot -> StringUtils.equalsIgnoreCase(ot, a.getKey()))) {
          attrs.addValue(a.getKey(), generateAttribute(a.getValue()));
        }
      });
    }


    return attrs;
  }


  private ValueType generateAttribute(JsonNode node) {
    if (node == null) {
      return new NullAttribute();
    }
    String type = node.get("vr").asText();
    ArrayNode arrayNode = (ArrayNode) node.get("Value");

    if (arrayNode == null) {
      return new NullAttribute();
    }

    ValueType a;
    if (StringUtils.equalsAnyIgnoreCase("SQ", type)) {
      List<ValueType> listOfmaps = new ArrayList<>();
      arrayNode.iterator().forEachRemaining(l -> listOfmaps.add(generateMap(l)));
      a = new ListValueType<>(listOfmaps);

    } else if (STRING_VR_TYPES.stream().anyMatch(i -> StringUtils.equalsAnyIgnoreCase(i, type))) {
      List<String> list = new ArrayList<>();

      arrayNode.iterator().forEachRemaining(l -> list.add(l.asText()));
      a = new ListValueType<String>(list);
    } else if (NUMBER_VR_TYPES.stream().anyMatch(i -> StringUtils.equalsAnyIgnoreCase(i, type))) {

      List<Number> list = new ArrayList<>();
      arrayNode.iterator().forEachRemaining(l -> list.add(NumberUtils.createNumber(l.asText())));
      a = new ListValueType<Number>(list);

    } else {
      a = new NullAttribute();
    }
    return a;
  }

  private ValueType generateMap(JsonNode l) {
    if (l != null && l.getNodeType() == JsonNodeType.OBJECT) {
      Map<String, ValueType> mp = new HashMap<>();
      ObjectNode objNode = (ObjectNode) l;
      objNode.fields().forEachRemaining(a -> mp.put(a.getKey(), generateAttribute(a.getValue())));
      return new MapValueType(mp);
    }
    throw new IllegalStateException("No object values for SQ tag found, node: " + l);
  }



}
