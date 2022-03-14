/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.fact;

import java.io.IOException;
import java.io.InputStream;
import org.dcm4che3.util.SafeClose;
import io.github.linuxforhealth.rules.api.Fact;
import io.github.linuxforhealth.rules.dicom.DICOMReader;

public class InputStreamFact implements Fact<InputStream>, AutoCloseable {


  private InputStream inputStream;
  private String factIdentifier;

  public InputStreamFact(String factIdentifier, InputStream inputStream) {

    this.inputStream = inputStream;
    this.factIdentifier = factIdentifier;
  }

  protected InputStreamFact(String factIdentifier) {
    this.factIdentifier = factIdentifier;
  }



  @Override
  public InputStream getValue() {
    throw new IllegalStateException("Method not supported.");
  }


  public DataValues getValue(DICOMReader reader) {
    try {
      return reader.parse(this.inputStream);
    } catch (IOException e) {
      throw new IllegalStateException(
          "IO Exception encountered when extracting tag values from DICOM inputstream.");
    }
  }

  @Override
  public String geIdentifier() {
    return factIdentifier;
  }

  @Override
  public void close() {
    SafeClose.close(inputStream);
  }


}
