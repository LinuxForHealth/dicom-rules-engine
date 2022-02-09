/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.fact;

import java.io.File;
import io.github.linuxforhealth.rules.api.Fact;

public class FileFact implements Fact<File> {

  protected File file;
  protected String factIdentifier;

  public FileFact(String factIdentifier, File file) {
    this.file = file;
    this.factIdentifier = factIdentifier;
  }


  public FileFact(File file) {
    this.file = file;
    this.factIdentifier = file.getName();
  }

  @Override
  public File getValue() {
    return this.file;
  }




  @Override
  public String geIdentifier() {
    return this.factIdentifier;
  }



}
