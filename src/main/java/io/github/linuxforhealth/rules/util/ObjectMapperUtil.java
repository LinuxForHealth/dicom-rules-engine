/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * Json util to serialize objects.
 * 
 * @author pbhallam@us.ibm.com
 *
 */
public class ObjectMapperUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(ObjectMapperUtil.class);

  private static ObjectMapperUtil objectMapperUtilYAML = new ObjectMapperUtil(true);
  private static ObjectMapperUtil objectMapperUtilJSON = new ObjectMapperUtil(false);

  private ObjectMapper objectMapper;

  private ObjectMapperUtil(boolean isyaml) {
    if (isyaml) {
      objectMapper = new ObjectMapper(new YAMLFactory());
    } else {
      objectMapper = new ObjectMapper();
      objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      // StdDateFormat is ISO8601 since jackson 2.9
      objectMapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
    }
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  public static ObjectMapper getYAMLInstance() {
    return objectMapperUtilYAML.objectMapper;
  }

  public static ObjectMapper getJSONInstance() {
    return objectMapperUtilJSON.objectMapper;
  }

  public static String getJsonObjectRepresentation(Object obj) {
    if (obj == null) {
      return null;
    }
    try {
      return objectMapperUtilJSON.objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      LOGGER.warn("Cannot serialize the Object {} using Jackson Object mapper", obj.getClass(), e);
      return null;
    }

  }



}
