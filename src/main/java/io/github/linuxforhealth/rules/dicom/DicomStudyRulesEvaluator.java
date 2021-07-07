/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package io.github.linuxforhealth.rules.dicom;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Preconditions;
import io.github.linuxforhealth.rules.api.Rule;
import io.github.linuxforhealth.rules.api.RulesEvaluationResult;
import io.github.linuxforhealth.rules.api.RulesEvaluator;
import io.github.linuxforhealth.rules.fact.ContextValues;

/**
 * This rules engine evaluates rules on a DICOM fact using rules that support DicomRuleCondition.
 * 
 * @author pbhallam
 *
 */
public class DicomStudyRulesEvaluator extends RulesEvaluator {
  private static final Logger LOGGER = LoggerFactory.getLogger(DicomStudyRulesEvaluator.class);


  private Set<String> tags;
  private DICOMReader dicomreader;

  public DicomStudyRulesEvaluator(List<Rule> rules) {
    super(rules);
    init();

  }



  private void init() {
    this.tags = new HashSet<>();
    initRequiredTags();
    this.dicomreader = new DICOMReader(this.tags);
  }



  public DicomStudyRulesEvaluator(File rulesFile) {
    super(rulesFile);
    init();

  }


  private void initRequiredTags() {
    this.getRules().forEach(r -> r.getAttributNames().forEach(e -> tags.add(e)));
  }



  public RulesEvaluationResult evaluateRules(File file) throws IOException {
    Preconditions.checkArgument(file != null && file.exists(), " file must exist.");
    if (file.isFile()) {
      ContextValues attrs = dicomreader.parse(file);
      return this.getEngine().evaluateRules(attrs);
    } else if (file.isDirectory()) {
      Map<String, RulesEvaluationResult> multiple = evaluateRulesForStudy(file);
      return multiple.values().stream().reduce(new RulesEvaluationResult(),
          RulesEvaluationResult::aggregate);

    } else {
      throw new IllegalArgumentException("Input file object is neither a file nor directory.");
    }

  }



  public Map<String, RulesEvaluationResult> evaluateRulesForStudy(File studyDir)
      throws IOException {
    Preconditions.checkArgument(studyDir != null && studyDir.exists() && studyDir.isDirectory(),
        "StudyDir must exists and should be a directory.");

    return evaluateStudyDir(studyDir);

  }



  private Map<String, RulesEvaluationResult> evaluateStudyDir(File studyDir) throws IOException {

    Collection<File> files = FileUtils.listFiles(studyDir, null, false);

    Map<String, RulesEvaluationResult> finalResults = new HashMap<>();
    for (File f : files) {
      if (f.isFile()) {
        RulesEvaluationResult res = evaluateRules(f);
        finalResults.put(FilenameUtils.getBaseName(f.getName()), res);
        if (!res.hasFailedRules()) {
          LOGGER.debug("Returning results, skipping other dcm files. File successfully run:{}",
              f.getName());
          return finalResults;
        }
      }
    }
    return finalResults;
  }


  public RulesEvaluationResult evaluateRules(String dicomJson) throws IOException {

    ContextValues attrs = dicomreader.parse(dicomJson);
    return this.getEngine().evaluateRules(attrs);
  }


}
