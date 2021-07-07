/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package io.github.linuxforhealth.rules.dicom;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class DicomRulesEvaluator extends RulesEvaluator {


  private Set<String> tags;
  private DICOMReader dicomreader;

  public DicomRulesEvaluator(List<Rule> rules) {
    super(rules);
    init();

  }



  private void init() {
    this.tags = new HashSet<>();
    initRequiredTags();
    this.dicomreader = new DICOMReader(this.tags);
  }



  public DicomRulesEvaluator(File rulesFile) {
    super(rulesFile);
    init();

  }


  private void initRequiredTags() {
    this.getRules().forEach(r -> r.getAttributNames().forEach(e -> tags.add(e)));
  }



  public RulesEvaluationResult evaluateRules(File file) throws IOException {
    Preconditions.checkArgument(file != null && file.exists() && file.isFile(),
        "File must exists and should be of File type.");

    ContextValues attrs = dicomreader.parse(file);
    return this.getEngine().evaluateRules(attrs);

  }



  public RulesEvaluationResult evaluateRules(String dicomJson) throws IOException {

    ContextValues attrs = dicomreader.parse(dicomJson);
    return this.getEngine().evaluateRules(attrs);
  }


}
