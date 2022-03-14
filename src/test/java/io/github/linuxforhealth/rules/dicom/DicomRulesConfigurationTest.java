/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.dicom;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import io.github.linuxforhealth.rules.rule.RuleDef;
import io.github.linuxforhealth.rules.rule.RulesConfiguration;
import io.github.linuxforhealth.rules.util.RuleParser;

class DicomRulesConfigurationTest {

  @TempDir
  File tempFolder;


  @Test
  void test_rules_generated_from_file() throws Exception {
    String rule = "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n"
        + "      \"groupid\": \"queue1rules\",\n" + "      \"conditions\": {\n"
        + "        \"condition1\": \"$00020002 EQUALS 1.2.840.10008.5.1.4.1.1.2\",\n"
        + "        \"condition2\": \"$00020013 EQUALS dicomlibrary-100\"\n" + "            },\n"
        + "      \"rules\": {\n" + "        \"relevance_rule\": \"condition1 && condition2 \"\n"
        + "      }\n" + "    }\n" + "  ]\n" + "}";

    File ruleFile = new File(tempFolder, "rule1.json");
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());

    List<RuleDef> ruleDefs = RuleParser.loadRulesFromFile(ruleFile);
    assertThat(ruleDefs).isNotNull().hasSize(1);
    assertThat(ruleDefs.get(0).getRules()).isNotNull().hasSize(1);
    assertThat(ruleDefs.get(0).getRules().get("relevance_rule")).isNotNull();
    assertThat(ruleDefs.get(0).getConditions()).hasSize(2);
    assertThat(ruleDefs.get(0).getGroupId()).isEqualTo("queue1rules");
  }

  @Test
  void test_rules_generated_from_json() throws Exception {
    String rule = "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n"
        + "      \"groupid\": \"queue1rules\",\n" + "      \"conditions\": {\n"
        + "        \"condition1\": \"$00020002 EQUALS 1.2.840.10008.5.1.4.1.1.2\",\n"
        + "        \"condition2\": \"$00020013 EQUALS dicomlibrary-100\"\n" + "            },\n"
        + "      \"rules\": {\n" + "        \"relevance_rule\": \"condition1 && condition2 \"\n"
        + "      }\n" + "    }\n" + "  ]\n" + "}";


    RulesConfiguration config = RuleParser.loadRulesConfigurationFromJson(rule);
    List<RuleDef> ruleDefs = config.getRules();
    assertThat(ruleDefs).isNotNull().hasSize(1);
    assertThat(ruleDefs.get(0).getRules()).isNotNull().hasSize(1);
    assertThat(ruleDefs.get(0).getRules().get("relevance_rule")).isNotNull();
    assertThat(ruleDefs.get(0).getConditions()).hasSize(2);
    assertThat(ruleDefs.get(0).getGroupId()).isEqualTo("queue1rules");
  }




}
