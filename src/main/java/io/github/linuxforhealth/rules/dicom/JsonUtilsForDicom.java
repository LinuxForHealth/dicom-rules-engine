// @formatter:off
/*******************************************************************************
 * Watson Health Imaging Analytics
 *
 * IBM Confidential
 *
 * OCO Source Materials
 *
 * (C) Copyright IBM Corp. 2022
 *
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright Office.
 *******************************************************************************/
// @formatter:on

package io.github.linuxforhealth.rules.dicom;

import java.io.IOException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.github.linuxforhealth.rules.fact.DataValues;

public class JsonUtilsForDicom {
  private ObjectMapper mapper;


  private TypeFactory typeFactory;
  private JavaType javaType;


  private JsonUtilsForDicom(SimpleModule sm) {
    mapper = new ObjectMapper();
    var deserialization = new SimpleModule();

    mapper.registerModule(deserialization);
    mapper
        .setDefaultPropertyInclusion(JsonInclude.Value.construct(Include.ALWAYS, Include.NON_NULL));

    typeFactory = mapper.getTypeFactory();
    javaType = typeFactory.constructType(io.github.linuxforhealth.rules.fact.DataValues.class);
    mapper.registerModule(sm);
  }

  public ObjectMapper getMapper() {
    return mapper;
  }



  public JavaType getJavaType() {
    return javaType;
  }

  public static JsonUtilsForDicom getNewInstance(SimpleModule sm) {

    return new JsonUtilsForDicom(sm);

  }

  public DataValues parse(String dicomJson) throws IOException {
    return mapper.readValue(dicomJson, javaType);

  }

  public DataValues parse(byte[] byteArray) {
    try {
      return mapper.readValue(byteArray, javaType);
    } catch (IOException e) {
      throw new IllegalStateException(
          "Exception encountered when reading context values from bytearray", e);
    }
  }


}
