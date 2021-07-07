/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import io.github.linuxforhealth.rules.api.Rule;
import io.github.linuxforhealth.rules.api.RulesConfiguration;

public class RuleParser {


  public static List<Rule> loadRulesFromFile(File file) {
    TypeReference<RulesConfiguration> typeref = new TypeReference<RulesConfiguration>() {};
    RulesConfiguration rulesConfigurations = (RulesConfiguration) readFromFile(file, typeref);

    return rulesConfigurations.getRules();

  }


  private static Object readFromFile(File file, TypeReference<?> typeref) {
    Preconditions.checkArgument(file != null, "file cannot be null");

    try {
      return ObjectMapperUtil.getYAMLInstance().readValue(file, typeref);
    } catch (IOException e) {
      throw new IllegalArgumentException(
          "Json parsing exception when trying to data from file " + file.getAbsolutePath(), e);
    }

  }
}
