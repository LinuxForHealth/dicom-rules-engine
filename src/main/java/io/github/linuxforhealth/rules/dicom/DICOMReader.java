/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.dicom;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import javax.json.Json;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.json.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.github.linuxforhealth.rules.fact.ContextValues;

public class DICOMReader {
  private static final Logger LOGGER = LoggerFactory.getLogger(DICOMReader.class);

  private ObjectMapper mapper;
  private TypeFactory typeFactory;

  private JavaType javaType;


  public DICOMReader() {
    this(new HashSet<>());

  }


  public DICOMReader(Set<String> tags) {
    mapper = new ObjectMapper();
    var deserialization = new SimpleModule();
    deserialization.addDeserializer(io.github.linuxforhealth.rules.fact.ContextValues.class,
        new AttributesDeserialize(tags));
    mapper.registerModule(deserialization);
    mapper
        .setDefaultPropertyInclusion(JsonInclude.Value.construct(Include.ALWAYS, Include.NON_NULL));

    typeFactory = mapper.getTypeFactory();
    javaType = typeFactory.constructType(io.github.linuxforhealth.rules.fact.ContextValues.class);

  }



  public String parseToAttr(File dcmFile) throws IOException {

    Attributes attrs = null;
    try (var imagereader = new DicomInputStream(dcmFile)) {
      attrs = imagereader.readDatasetUntilPixelData();
      attrs.addAll(imagereader.readFileMetaInformation());
    }
    String s = null;
    try (var stream = new ByteArrayOutputStream();
        javax.json.stream.JsonGenerator gen = Json.createGenerator(stream);) {

      new JSONWriter(gen).write(attrs);
      gen.flush();
      s = new String(stream.toByteArray(), StandardCharsets.UTF_8);
    }
    LOGGER.debug("Parsed file {} , extracted: {}", dcmFile, s);
    return s;
  }



  public ContextValues parse(File dcmFile) throws IOException {
    Attributes attrs = null;
    try (var imagereader = new DicomInputStream(dcmFile)) {
      attrs = imagereader.readDatasetUntilPixelData();
      attrs.addAll(imagereader.readFileMetaInformation());
    }
    ContextValues mp = null;
    try (var stream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8);
        javax.json.stream.JsonGenerator gen = Json.createGenerator(writer);) {

      new JSONWriter(gen).write(attrs);
      gen.flush();
      mp = mapper.readValue(stream.toByteArray(), javaType);
    }

    LOGGER.debug("Parsed file {} , extracted: {}", dcmFile, mp);
    return mp;
  }

  public ContextValues parse(String dicomJson) throws IOException {
    return mapper.readValue(dicomJson, javaType);

  }



}


