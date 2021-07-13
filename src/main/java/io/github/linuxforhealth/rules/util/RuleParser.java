/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import io.github.linuxforhealth.rules.api.Format;
import io.github.linuxforhealth.rules.api.Rule;
import io.github.linuxforhealth.rules.api.RulesConfiguration;

public class RuleParser {

  private RuleParser() {}


  public static List<Rule> loadRulesFromFile(File file) {
    TypeReference<RulesConfiguration> typeref = new TypeReference<RulesConfiguration>() {};
    RulesConfiguration rulesConfigurations = null;
    if (FilenameUtils.isExtension(file.getName(), "json")) {
      rulesConfigurations = readFromJsonFile(file, typeref);
    } else if (FilenameUtils.isExtension(file.getName(), "yml", "yaml")) {
      rulesConfigurations = readFromYamlFile(file, typeref);
    } else {
      throw new IllegalArgumentException(
          "Rules file only supports reading rules from JSON or YAML files. The provided file was :"
              + file);
    }

    return rulesConfigurations.getRules();

  }


  public static List<Rule> loadRules(String rules, Format format) {
    if (format == Format.JSON) {
      return loadRulesFromJson(rules);
    } else if (format == Format.YAML) {
      return loadRulesFromYaml(rules);
    } else {
      throw new IllegalArgumentException(
          "Format of rules content not supported, provided format: " + format);
    }
  }

  public static List<Rule> loadRulesFromJson(String json) {
    TypeReference<RulesConfiguration> typeref = new TypeReference<RulesConfiguration>() {};


    try {
      RulesConfiguration conf = ObjectMapperUtil.getJSONInstance().readValue(json, typeref);
      return conf.getRules();
    } catch (IOException e) {
      throw new IllegalArgumentException(
          "Json parsing exception when trying to read data : " + json, e);
    }

  }



  public static List<Rule> loadRulesFromYaml(String yaml) {
    TypeReference<RulesConfiguration> typeref = new TypeReference<RulesConfiguration>() {};


    try {
      RulesConfiguration conf = ObjectMapperUtil.getYAMLInstance().readValue(yaml, typeref);
      return conf.getRules();
    } catch (IOException e) {
      throw new IllegalArgumentException(
          "Json parsing exception when trying to read data : " + yaml, e);
    }


  }



  private static RulesConfiguration readFromJsonFile(File file,
      TypeReference<RulesConfiguration> typeref) {
    Preconditions.checkArgument(file != null, "file cannot be null");

    try {
      return ObjectMapperUtil.getJSONInstance().readValue(file, typeref);
    } catch (IOException e) {
      throw new IllegalArgumentException(
          "Json parsing exception when trying to read data from file " + file.getAbsolutePath(), e);
    }

  }


  private static RulesConfiguration readFromYamlFile(File file,
      TypeReference<RulesConfiguration> typeref) {
    Preconditions.checkArgument(file != null, "file cannot be null");

    try {
      return ObjectMapperUtil.getYAMLInstance().readValue(file, typeref);
    } catch (IOException e) {
      throw new IllegalArgumentException(
          "Json parsing exception when trying to read data from file " + file.getAbsolutePath(), e);
    }

  }



}
