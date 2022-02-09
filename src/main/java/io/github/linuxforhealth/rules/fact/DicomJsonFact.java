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

package io.github.linuxforhealth.rules.fact;

import io.github.linuxforhealth.rules.api.Fact;

public class DicomJsonFact implements Fact<String> {
  protected String json;
  protected String factIdentifier;

  public DicomJsonFact(String factIdentifier, String json) {
    this.json = json;
    this.factIdentifier = factIdentifier;
  }

  @Override
  public String getValue() {
    return this.json;
  }



  @Override
  public String geIdentifier() {
    return this.factIdentifier;
  }


}
