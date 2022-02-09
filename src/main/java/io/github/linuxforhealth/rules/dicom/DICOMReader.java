/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.dicom;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.linuxforhealth.rules.fact.DataValues;

public class DICOMReader {
  private static final Logger LOGGER = LoggerFactory.getLogger(DICOMReader.class);

  private JsonUtilsForDicom jsonUtils;
 

  public DICOMReader() {
    this(new HashSet<>());
  }


  public DICOMReader(Set<String> tags) {
    var deserialization = new SimpleModule();
    deserialization.addDeserializer(io.github.linuxforhealth.rules.fact.DataValues.class,
        new AttributesDeserialize(tags));
    jsonUtils = JsonUtilsForDicom.getNewInstance(deserialization);


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



  public DataValues parse(File dcmFile) throws IOException {
    Attributes attrs = null;
    try (var imagereader = new DicomInputStream(dcmFile)) {
      attrs = imagereader.readDatasetUntilPixelData();
      attrs.addAll(imagereader.readFileMetaInformation());
    }
    DataValues mp = null;
    try (var stream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8);
        javax.json.stream.JsonGenerator gen = Json.createGenerator(writer);) {

      new JSONWriter(gen).write(attrs);
      gen.flush();
      mp = this.jsonUtils.parse(stream.toByteArray());
    }

    LOGGER.debug("Parsed file {} , extracted: {}", dcmFile, mp);
    return mp;
  }


  public DataValues parse(InputStream dcmInputStream) throws IOException {
    Attributes attrs = null;
    try (var imagereader = new DicomInputStream(dcmInputStream)) {
      attrs = imagereader.readDatasetUntilPixelData();
      attrs.addAll(imagereader.readFileMetaInformation());
    }
    DataValues mp = null;
    try (var stream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8);
        javax.json.stream.JsonGenerator gen = Json.createGenerator(writer);) {

      new JSONWriter(gen).write(attrs);
      gen.flush();
      mp = this.jsonUtils.parse(stream.toByteArray());
    }

    LOGGER.debug("Parsed dcmInputStream and extracted: {}", mp);
    return mp;
  }

  public DataValues parse(String dicomJson) throws IOException {
    return this.jsonUtils.parse(dicomJson);

  }



}


