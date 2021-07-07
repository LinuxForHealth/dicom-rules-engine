package io.github.linuxforhealth.rules.api;


import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import io.github.linuxforhealth.rules.fact.ContextValues;
import io.github.linuxforhealth.rules.fact.ListValueType;
import io.github.linuxforhealth.rules.fact.NumberValueType;
import io.github.linuxforhealth.rules.fact.StringValueType;


class RulesEvaluatorTest {

  @TempDir
  File tempFolder;



  @Test
  public void evaluate_compound_condition_result_to_true() throws Exception {
    String rule = "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n"
        + "      \"groupid\": \"queue1rules\",\n" + "      \"conditions\": {\n"
        + "        \"condition1\": \"$(0008,0060) EQUALS 77068 DIGITAL MAMM SCREENING\",\n"
        + "        \"condition2\": \"$(00098,2335) CONTAINS SCREE\",\n"
        + "        \"condition3\": \"$(00098,2336) GREATER_THAN 5\",\n"
        + "        \"condition4\": \"$(00098,2335) CONTAINS DIGITAL MAMM\"\n" + "      },\n"
        + "      \"rules\": {\n"
        + "        \"relevance_rule\": \"condition1 || condition2 && ( condition3 || condition4 )\"\n"
        + "      }\n" + "    }\n" + "  ]\n" + "}";

    File ruleFile = new File(tempFolder, "rule1.json");
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());


    RulesEvaluator eval = new RulesEvaluator(ruleFile);
    ContextValues attrs = new ContextValues();
    attrs.addValue("(0008,0060)", new StringValueType("77068 DIGITAL MAMM SCREENING"));
    attrs.addValue("(00098,2335)", new StringValueType("SCREENING"));
    attrs.addValue("(00098,2336)", new NumberValueType(6));

    RulesEvaluationResult results = eval.evaluateRules(attrs);



    assertThat(results.getResultOfRule("queue1rules", "relevance_rule")).isTrue();

  }



  @Test
  public void evaluate_compound_condition_result_to_true_with_one_condition_false()
      throws Exception {
    String rule = "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n"
        + "      \"groupid\": \"queue1rules\",\n" + "      \"conditions\": {\n"
        + "        \"condition1\": \"$(0008,0060) EQUALS 77068 DIGITAL MAMM SCREENING\",\n"
        + "        \"condition2\": \"$(00098,2335) CONTAINS SCREE\",\n"
        + "        \"condition3\": \"$(00098,2336) GREATER_THAN 5\",\n"
        + "        \"condition4\": \"$(00098,2335) CONTAINS DIGITAL MAMM\"\n" + "      },\n"
        + "      \"rules\": {\n"
        + "        \"relevance_rule\": \"condition1 || condition2 && ( condition3 || condition4 )\"\n"
        + "      }\n" + "    }\n" + "  ]\n" + "}";

    File ruleFile = new File(tempFolder, "rule1.json");
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());


    RulesEvaluator eval = new RulesEvaluator(ruleFile);
    ContextValues attrs = new ContextValues();
    // this will result condition 1 to be false, but as this condition is OR ,the overall result
    // will still be true.
    attrs.addValue("(0008,0060)", new StringValueType("77088 DIGITAL MAMM SCREENING"));
    attrs.addValue("(00098,2335)", new StringValueType("SCREENING"));
    attrs.addValue("(00098,2336)", new NumberValueType(6));

    RulesEvaluationResult results = eval.evaluateRules(attrs);



    assertThat(results.getResultOfRule("queue1rules", "relevance_rule")).isTrue();

  }



  @Test
  public void evaluate_compound_condition_result_to_false() throws Exception {
    String rule = "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n"
        + "      \"groupid\": \"queue1rules\",\n" + "      \"conditions\": {\n"
        + "        \"condition1\": \"$(0008,0060) EQUALS 77068 DIGITAL MAMM SCREENING\",\n"
        + "        \"condition2\": \"$(00098,2335) CONTAINS SCREE\",\n"
        + "        \"condition3\": \"$(00098,2336) GREATER_THAN 5\",\n"
        + "        \"condition4\": \"$(00098,2335) CONTAINS DIGITAL MAMM\"\n" + "      },\n"
        + "      \"rules\": {\n"
        + "        \"relevance_rule\": \"condition1 && condition2 && ( condition3 || condition4 )\"\n"
        + "      }\n" + "    }\n" + "  ]\n" + "}";

    File ruleFile = new File(tempFolder, "rule1.json");
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());


    RulesEvaluator eval = new RulesEvaluator(ruleFile);
    ContextValues attrs = new ContextValues();
    // this will result condition 1 to be false, but as this condition is OR ,the overall result
    // will still be true.
    attrs.addValue("(0008,0060)", new StringValueType("77088 DIGITAL MAMM SCREENING"));
    attrs.addValue("(00098,2335)", new StringValueType("SCREENING"));
    attrs.addValue("(00098,2336)", new NumberValueType(6));

    RulesEvaluationResult results = eval.evaluateRules(attrs);



    assertThat(results.getResultOfRule("queue1rules", "relevance_rule")).isFalse();

  }


  @Test
  public void evaluate_compound_condition_with_list_result_to_true() throws Exception {
    String rule = "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n"
        + "      \"groupid\": \"queue1rules\",\n" + "      \"conditions\": {\n"
        + "        \"condition1\": \"$(0008,0060) EQUALS 77068 DIGITAL MAMM SCREENING\",\n"
        + "        \"condition2\": \"$(00098,2335) ANY_IN_EQUALS SR\",\n"
        + "        \"condition3\": \"$(00098,2336) GREATER_THAN 5\",\n"
        + "        \"condition4\": \"$(00098,2335) CONTAINS DIGITAL MAMM\"\n" + "      },\n"
        + "      \"rules\": {\n" + "        \"relevance_rule\": \"condition1 && condition3 \"\n"
        + "      }\n" + "    }\n" + "  ]\n" + "}";

    File ruleFile = new File(tempFolder, "rule1.json");
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());
    List<String> tModalityList = new ArrayList<String>();
    tModalityList.add("CT");
    tModalityList.add("US");
    tModalityList.add("SR");


    RulesEvaluator eval = new RulesEvaluator(ruleFile);
    ContextValues attrs = new ContextValues();
    attrs.addValue("(0008,0060)", new StringValueType("77068 DIGITAL MAMM SCREENING"));
    attrs.addValue("(00098,2335)", new ListValueType(tModalityList));
    attrs.addValue("(00098,2336)", new NumberValueType(6));

    RulesEvaluationResult results = eval.evaluateRules(attrs);



    assertThat(results.getResultOfRule("queue1rules", "relevance_rule")).isTrue();

  }


  @Test
  public void evaluate_compound_condition_with_in_list_result_to_true() throws Exception {
    String rule = "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n"
        + "      \"groupid\": \"queue1rules\",\n" + "      \"conditions\": {\n"
        + "        \"condition1\": \"$(0008,0060) EQUALS 77068 DIGITAL MAMM SCREENING\",\n"
        + "        \"condition2\": \"$(00098,2335) IN_EQUALS SR, CT, MR\",\n"
        + "        \"condition3\": \"$(00098,2336) GREATER_THAN 5\",\n"
        + "        \"condition4\": \"$(00098,2335) CONTAINS DIGITAL MAMM\"\n" + "      },\n"
        + "      \"rules\": {\n" + "        \"relevance_rule\": \"condition1 && condition3 \"\n"
        + "      }\n" + "    }\n" + "  ]\n" + "}";

    File ruleFile = new File(tempFolder, "rule1.json");
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());


    RulesEvaluator eval = new RulesEvaluator(ruleFile);
    ContextValues attrs = new ContextValues();
    attrs.addValue("(0008,0060)", new StringValueType("77068 DIGITAL MAMM SCREENING"));
    attrs.addValue("(00098,2335)", new StringValueType("SR"));
    attrs.addValue("(00098,2336)", new NumberValueType(6));

    RulesEvaluationResult results = eval.evaluateRules(attrs);



    assertThat(results.getResultOfRule("queue1rules", "relevance_rule")).isTrue();

  }


  @Test
  public void evaluate_compound_condition_with_in_list_result_to_false() throws Exception {
    String rule = "{\n" + "  \"ruleDefinitions\": [\n" + "    {\n"
        + "      \"groupid\": \"queue1rules\",\n" + "      \"conditions\": {\n"
        + "        \"condition1\": \"$(0008,0060) EQUALS 77068 DIGITAL MAMM SCREENING\",\n"
        + "        \"condition2\": \"$(00098,2335) IN_EQUALS SR, CT, MR\",\n"
        + "        \"condition3\": \"$(00098,2336) GREATER_THAN 5\",\n"
        + "        \"condition4\": \"$(00098,2335) CONTAINS DIGITAL MAMM\"\n" + "      },\n"
        + "      \"rules\": {\n" + "        \"relevance_rule\": \"condition2 &&  condition3\"\n"
        + "      }\n" + "    }\n" + "  ]\n" + "}";

    File ruleFile = new File(tempFolder, "rule1.json");
    FileUtils.writeStringToFile(ruleFile, rule, Charset.defaultCharset());


    RulesEvaluator eval = new RulesEvaluator(ruleFile);
    assertThat(eval.getRules().get(0).getAttributNames()).containsExactlyInAnyOrder("(0008,0060)",
        "(00098,2335)", "(00098,2336)");
    ContextValues attrs = new ContextValues();
    attrs.addValue("(0008,0060)", new StringValueType("77068 DIGITAL MAMM SCREENING"));
    attrs.addValue("(00098,2335)", new StringValueType("SRT"));
    attrs.addValue("(00098,2336)", new NumberValueType(6));

    RulesEvaluationResult results = eval.evaluateRules(attrs);



    assertThat(results.getResultOfRule("queue1rules", "relevance_rule")).isNull();

  }



}
