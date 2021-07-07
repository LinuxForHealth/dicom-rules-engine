/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.dicom;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.linuxforhealth.rules.util.ObjectMapperUtil;

class DICOMReaderTest {



  @Test
  void test_that_dicom_reader_returns_json() throws IOException {
    DICOMReader reader = new DICOMReader();
    String s = reader.parseToAttr(new File("src/test/resources/dcmfiles/image-000341.dcm"));
    assertThat(s).isNotBlank();
    JsonNode jnode = ObjectMapperUtil.getJSONInstance().readTree(s);
    assertThat(jnode).isNotNull();

    assertThat(jnode.get("00020001").get("vr").asText()).isEqualTo("OB");
    assertThat(jnode.get("00080012").get("Value").isArray()).isTrue();
    ArrayNode a = (ArrayNode) jnode.get("00080012").get("Value");
    assertThat(a.get(0).asText()).isEqualTo("20061012");


  }



}
