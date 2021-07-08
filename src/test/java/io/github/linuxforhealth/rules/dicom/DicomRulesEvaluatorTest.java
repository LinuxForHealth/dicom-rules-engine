/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.dicom;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import io.github.linuxforhealth.rules.api.RulesEvaluationResult;

class DicomRulesEvaluatorTest {

  @TempDir
  File tempFolder;


  @Test
  public void evaluate_compound_condition_result_simple_string_type_to_true_with_one_condition_true()
      throws Exception {
    String rule = "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n"
        + "      \"groupid\": \"queue1rules\",\n" + "      \"conditions\": {\n"
        + "        \"condition1\": \"$00020002 EQUALS 1.2.840.10008.5.1.4.1.1.2\",\n"
        + "        \"condition2\": \"$00020013 EQUALS dicomlibrary-100\"\n" + "            },\n"
        + "      \"rules\": {\n" + "        \"relevance_rule\": \"condition1 && condition2 \"\n"
        + "      }\n" + "    }\n" + "  ]\n" + "}";

    File ruleFile = new File(tempFolder, "rule1.json");
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());
    File dcm = new File("src/test/resources/dcmfiles/image-000341.dcm");


    DicomRulesEvaluator eval = new DicomRulesEvaluator(ruleFile);
    RulesEvaluationResult results = eval.evaluateRules(dcm);
    assertThat(results.getResultOfRule("queue1rules", "relevance_rule")).isTrue();

  }


  @Test
  public void evaluate_compound_condition_result_simple_string_type_to_true_with_one_condition_false()
      throws Exception {
    String rule = "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n"
        + "      \"groupid\": \"queue1rules\",\n" + "      \"conditions\": {\n"
        + "        \"condition1\": \"$00020002 NOT_EQUALS 1.2.840.10008.5.1.4.1.1.2\",\n"
        + "        \"condition2\": \"$0020000E EQUALS dicomlibrary-100\"\n" + "            },\n"
        + "      \"rules\": {\n" + "        \"relevance_rule\": \"condition1 && condition2 \"\n"
        + "      }\n" + "    }\n" + "  ]\n" + "}";

    File ruleFile = new File(tempFolder, "rule2.json");
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());
    File dcm = new File("src/test/resources/dcmfiles/image-000341.dcm");

    DicomRulesEvaluator eval = new DicomRulesEvaluator(ruleFile);


    RulesEvaluationResult results = eval.evaluateRules(dcm);
    assertThat(results.getResultOfRule("queue1rules", "relevance_rule")).isFalse();

  }

  //
  @Test
  public void evaluate_compound_condition_result_simple_string_type_to_true_with_one_condition_true_study()
      throws Exception {

    String rule = "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n"
        + "      \"groupid\": \"queue1rules\",\n" + "      \"conditions\": {\n"
        + "        \"condition1\": \"$00080018 EQUALS 1.2.826.0.1.3680043.8.1055.1.20111102150800481.27482048.30798145\",\n"
        + "        \"condition2\": \"$0020000E EQUALS 1.2.826.0.1.3680043.8.1055.1.20111102150758591.96842950.07877442\"\n"
        + "            },\n" + "      \"rules\": {\n"
        + "        \"relevance_rule\": \"condition1 && condition2 \"\n" + "      }\n" + "    }\n"
        + "  ]\n" + "}";

    File ruleFile = new File(tempFolder, "rule3.json");
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());
    File study = new File("src/test/resources/dcmfiles/study");

    DicomRulesEvaluator eval = new DicomRulesEvaluator(ruleFile);

    for (File f : study.listFiles()) {

      RulesEvaluationResult results = eval.evaluateRules(f);
      if (f.getName().contains("image-000025")) {
        assertThat(results.getResultOfRule("queue1rules", "relevance_rule")).isTrue();
      } else {
        assertThat(results.getResultOfRule("queue1rules", "relevance_rule")).isFalse();
      }

    }

  }

  @Test
  public void evaluate_compound_condition_result_simple_string_type_to_true_with_one_condition_false_study()
      throws Exception {
    String rule = "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n"
        + "      \"groupid\": \"queue1rules\",\n" + "      \"conditions\": {\n"
        + "        \"condition1\": \"$00080018 EQUALS 1.2.826.0.1.3680043.8.1055.1.20111102150800481.27482048.30798145\",\n"
        + "        \"condition2\": \"$0020000E EQUALS 1.2.826.0.1.3680043.8.1055.1.20111102150758591.96842950.07877442\"\n"
        + "            },\n" + "      \"rules\": {\n"
        + "        \"relevance_rule\": \"condition1 || condition2 \"\n" + "      }\n" + "    }\n"
        + "  ]\n" + "}";

    File ruleFile = new File(tempFolder, "rule4.json");
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());
    File study = new File("src/test/resources/dcmfiles/study");

    DicomRulesEvaluator eval = new DicomRulesEvaluator(ruleFile);

    for (File f : study.listFiles()) {

      RulesEvaluationResult results = eval.evaluateRules(f);

      assertThat(results.getResultOfRule("queue1rules", "relevance_rule")).isTrue();


    }

  }



}
