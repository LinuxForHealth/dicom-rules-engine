/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.dicom;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import java.nio.charset.Charset;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import io.github.linuxforhealth.rules.api.RulesEvaluationResult;
import io.github.linuxforhealth.rules.eval.DicomJsonRulesEvaluator;
import io.github.linuxforhealth.rules.fact.DicomJsonFact;

class DicomRulesEvaluatorJsonTest {

  @TempDir
  File tempFolder;


  @Test
  void evaluate_compound_condition_result_simple_string_type_to_true() throws Exception {
    String rule = "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n"
        + "      \"groupid\": \"queue1rules\",\n" + "      \"conditions\": {\n"
        + "        \"condition1\": \"$00080005 EQUALS ISO_IR192\",\n"
        + "        \"condition2\": \"$00080050 EQUALS 11235813\"\n" + "            },\n"
        + "      \"rules\": {\n" + "        \"relevance_rule\": \"condition1 && condition2 \"\n"
        + "      }\n" + "    }\n" + "  ]\n" + "}";

    File ruleFile = ruleFile();
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());
    String json = getJSON();

    DicomJsonRulesEvaluator eval = new DicomJsonRulesEvaluator(ruleFile);
    RulesEvaluationResult results = eval.apply(new DicomJsonFact("", json));
    assertThat(results.getResultOfRule("relevance_rule", "queue1rules").isSuccess()).isTrue();

  }


  @Test
  void evaluate_compound_condition_result_simple_number_type_to_true() throws Exception {
    String rule =
        "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n" + "      \"groupid\": \"queue1rules\",\n"
            + "      \"conditions\": {\n" + "        \"condition1\": \"$00201206 EQUAL_TO 4\",\n"
            + "        \"condition2\": \"$00201208 EQUAL_TO 942\"\n" + "            },\n"
            + "      \"rules\": {\n" + "        \"relevance_rule\": \"condition1 && condition2 \"\n"
            + "      }\n" + "    }\n" + "  ]\n" + "}";

    File ruleFile = ruleFile();
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());
    String json = getJSON();

    DicomJsonRulesEvaluator eval = new DicomJsonRulesEvaluator(ruleFile);
    RulesEvaluationResult results = eval.apply(new DicomJsonFact("", json));
    assertThat(results.getResultOfRule("relevance_rule", "queue1rules").isSuccess()).isTrue();

  }



  @Test
  public void evaluate_compound_condition_result_simple_number_type_to_false_with_one_condition_false()
      throws Exception {
    String rule =
        "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n" + "      \"groupid\": \"queue1rules\",\n"
            + "      \"conditions\": {\n" + "        \"condition1\": \"$00201206 EQUAL_TO 5\",\n"
            + "        \"condition2\": \"$00201208 EQUAL_TO 942\"\n" + "            },\n"
            + "      \"rules\": {\n" + "        \"relevance_rule\": \"condition1 && condition2 \"\n"
            + "      }\n" + "    }\n" + "  ]\n" + "}";

    File ruleFile = ruleFile();
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());
    String json = getJSON();

    DicomJsonRulesEvaluator eval = new DicomJsonRulesEvaluator(ruleFile);
    RulesEvaluationResult results = eval.apply(new DicomJsonFact("", json));
    assertThat(results.getResultOfRule("relevance_rule", "queue1rules").isSuccess()).isFalse();

  }

  @Test
  public void evaluate_compound_condition_result_simple_number_type_to_true_select_value_from_list()
      throws Exception {
    String rule =
        "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n" + "      \"groupid\": \"queue1rules\",\n"
            + "      \"conditions\": {\n" + "        \"condition1\": \"$00201206[0] EQUAL_TO 4\",\n"
            + "        \"condition2\": \"$00201208 EQUAL_TO 942\"\n" + "            },\n"
            + "      \"rules\": {\n" + "        \"relevance_rule\": \"condition1 && condition2 \"\n"
            + "      }\n" + "    }\n" + "  ]\n" + "}";

    File ruleFile = ruleFile();
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());
    String json = getJSON();

    DicomJsonRulesEvaluator eval = new DicomJsonRulesEvaluator(ruleFile);
    RulesEvaluationResult results = eval.apply(new DicomJsonFact("", json));
    assertThat(results.getResultOfRule("relevance_rule", "queue1rules").isSuccess()).isTrue();

  }


  @Test
  void loading_rules_with_incorrect_condition_fails() throws Exception {
    String rule =
        "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n" + "      \"groupid\": \"queue1rules\",\n"
            + "      \"conditions\": {\n" + "        \"condition1\": \"$00201206 EQUAL_TO 4.0\",\n"
            + "        \"condition2\": \"$00201208 EQUAL_TO 942\"\n" + "            },\n"
            + "      \"rules\": {\n" + "        \"relevance_rule\": \"condition1 && condition2 \"\n"
            + "      }\n" + "    }\n" + "  ]\n" + "}";

    File ruleFile = ruleFile();
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());

    org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new DicomJsonRulesEvaluator(ruleFile);
    });



  }



  @Test
  void evaluate_compound_condition_result_sq_type() throws Exception {
    String rule = "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n"
        + "      \"groupid\": \"queue1rules\",\n" + "      \"conditions\": {\n"
        + "        \"condition1\": \"$00101002.00100020 EQUALS 54321\",\n"
        + "        \"condition2\": \"$00201208 EQUAL_TO 942\"\n" + "            },\n"
        + "      \"rules\": {\n" + "        \"relevance_rule1\": \"condition1 && condition2 \"\n"
        + "      }\n" + "    }\n" + "  ]\n" + "}";

    File ruleFile = ruleFile();
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());
    String json = getJSON();

    DicomJsonRulesEvaluator eval = new DicomJsonRulesEvaluator(ruleFile);
    RulesEvaluationResult results = eval.apply(new DicomJsonFact("123", json));

    assertThat(results.getResultOfRule("relevance_rule1", "queue1rules").isSuccess()).isTrue();



  }


  private File ruleFile() {
    String uid = UUID.randomUUID().toString();
    File ruleFile = new File(tempFolder, "rule1" + uid + ".json");
    return ruleFile;
  }


  @Test
  void evaluate_compound_condition_result_simple_string_type_with_multiple_values_to_true()
      throws Exception {
    String rule = "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n"
        + "      \"groupid\": \"queue1rules\",\n" + "      \"conditions\": {\n"
        + "        \"condition1\": \"$00080005 EQUALS ISO_IR192\",\n"
        + "        \"condition2\": \"$00080050 EQUALS 11235814\"\n" + "            },\n"
        + "      \"rules\": {\n" + "        \"relevance_rule\": \"condition1 && condition2 \"\n"
        + "      }\n" + "    }\n" + "  ]\n" + "}";

    File ruleFile = ruleFile();
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());
    String json = getJSON();

    DicomJsonRulesEvaluator eval = new DicomJsonRulesEvaluator(ruleFile);
    RulesEvaluationResult results = eval.apply(new DicomJsonFact("", json));
    assertThat(results.getResultOfRule("relevance_rule", "queue1rules").isSuccess()).isTrue();

  }

  @Test
  public void evaluate_compound_condition_result_simple_string_type_with_multiple_values_to_false()
      throws Exception {
    String rule = "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n"
        + "      \"groupid\": \"queue1rules\",\n" + "      \"conditions\": {\n"
        + "        \"condition1\": \"$00080005 EQUALS ISO_IR192\",\n"
        + "        \"condition2\": \"$00080050 EQUALS 11235815\"\n" + "            },\n"
        + "      \"rules\": {\n" + "        \"relevance_rule\": \"condition1 && condition2 \"\n"
        + "      }\n" + "    }\n" + "  ]\n" + "}";

    File ruleFile = ruleFile();
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());
    String json = getJSON();

    DicomJsonRulesEvaluator eval = new DicomJsonRulesEvaluator(ruleFile);
    RulesEvaluationResult results = eval.apply(new DicomJsonFact("", json));
    assertThat(results.getResultOfRule("relevance_rule", "queue1rules").isSuccess()).isFalse();

  }

  private String getJSON() {
 // @formatter:off
    return "{\n"
        + "    \"00080005\": {\n"
        + "        \"vr\": \"CS\",\n"
        + "        \"Value\": [\n"
        + "            \"ISO_IR192\"\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00080020\": {\n"
        + "        \"vr\": \"DT\",\n"
        + "        \"Value\": [\n"
        + "            \"20130409\"\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00080030\": {\n"
        + "        \"vr\": \"TM\",\n"
        + "        \"Value\": [\n"
        + "            \"131600.0000\"\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00080050\": {\n"
        + "        \"vr\": \"SH\",\n"
        + "        \"Value\": [\n"
        + "            \"11235813\",\n"
        + "             \"11235814\"\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00080056\": {\n"
        + "        \"vr\": \"CS\",\n"
        + "        \"Value\": [\n"
        + "            \"ONLINE\"\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00080061\": {\n"
        + "        \"vr\": \"CS\",\n"
        + "        \"Value\": [\n"
        + "            \"CT\",\n"
        + "            \"PET\"\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00080090\": {\n"
        + "        \"vr\": \"PN\",\n"
        + "        \"Value\": [\n"
        + "            {\n"
        + "                \"Alphabetic\": {\n"
        + "                    \"Given\": [\n"
        + "                        \"Bob\"\n"
        + "                    ],\n"
        + "                    \"Suffix\": [\n"
        + "                        \"Dr.\"\n"
        + "                    ]\n"
        + "                }\n"
        + "            }\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00081190\": {\n"
        + "        \"vr\": \"UT\",\n"
        + "        \"Value\": [\n"
        + "            \"http://wado.nema.org/studies/1.2.392.200036.9116.2.2.2.1762893313.1029997326.945873\"\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00090010\": {\n"
        + "        \"vr\": \"LO\",\n"
        + "        \"Value\": [\n"
        + "            \"Vendor A\"\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00091002\": {\n"
        + "        \"vr\": \"UN\",\n"
        + "        \"Value\": [\n"
        + "            \"z0x9c8v7\"\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00100010\": {\n"
        + "        \"vr\": \"PN\",\n"
        + "        \"Value\": [\n"
        + "            {\n"
        + "                \"Alphabetic\": {\n"
        + "                    \"Family\": [\n"
        + "                        \"Wang\"\n"
        + "                    ],\n"
        + "                    \"Given\": [\n"
        + "                        \"XiaoDong\"\n"
        + "                    ]\n"
        + "                },\n"
        + "                \"Ideographic\": {\n"
        + "                    \"Family\": [\n"
        + "                        \"王\"\n"
        + "                    ],\n"
        + "                    \"Given\": [\n"
        + "                        \"小東\"\n"
        + "                    ]\n"
        + "                }\n"
        + "            }\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00100020\": {\n"
        + "        \"vr\": \"LO\",\n"
        + "        \"Value\": [\n"
        + "            \"12345\"\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00100021\": {\n"
        + "        \"vr\": \"LO\",\n"
        + "        \"Value\": [\n"
        + "            \"Hospital A\"\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00100030\": {\n"
        + "        \"vr\": \"DT\",\n"
        + "        \"Value\": [\n"
        + "            \"19670701\"\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00100040\": {\n"
        + "        \"vr\": \"CS\",\n"
        + "        \"Value\": [\n"
        + "            \"M\"\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00101002\": {\n"
        + "        \"vr\": \"SQ\",\n"
        + "        \"Value\": [\n"
        + "            {\n"
        + "                \"00100020\": {\n"
        + "                    \"vr\": \"LO\",\n"
        + "                    \"Value\": [\n"
        + "                        \"54321\"\n"
        + "                    ]\n"
        + "                },\n"
        + "                \"00100021\": {\n"
        + "                    \"vr\": \"LO\",\n"
        + "                    \"Value\": [\n"
        + "                        \"Hospital B\"\n"
        + "                    ]\n"
        + "                }\n"
        + "            },\n"
        + "            {\n"
        + "                \"00100020\": {\n"
        + "                    \"vr\": \"LO\",\n"
        + "                    \"Value\": [\n"
        + "                        \"24680\"\n"
        + "                    ]\n"
        + "                },\n"
        + "                \"00100021\": {\n"
        + "                    \"vr\": \"LO\",\n"
        + "                    \"Value\": [\n"
        + "                        \"Hospital C\"\n"
        + "                    ]\n"
        + "                }\n"
        + "            }\n"
        + "        ]\n"
        + "    },\n"
        + "    \"0020000D\": {\n"
        + "        \"vr\": \"UI\",\n"
        + "        \"Value\": [\n"
        + "            \"1.2.392.200036.9116.2.2.2.1762893313.1029997326.945873\"\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00200010\": {\n"
        + "        \"vr\": \"SH\",\n"
        + "        \"Value\": [\n"
        + "            \"11235813\"\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00201206\": {\n"
        + "        \"vr\": \"IS\",\n"
        + "        \"Value\": [\n"
        + "            4\n"
        + "        ]\n"
        + "    },\n"
        + "    \"00201208\": {\n"
        + "        \"vr\": \"IS\",\n"
        + "        \"Value\": [\n"
        + "            942\n"
        + "        ]\n"
        + "    }\n"
        + "}";
    
  }
//@formatter:on


}
