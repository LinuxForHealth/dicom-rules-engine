/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package io.github.linuxforhealth.rules.eval;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.google.common.base.Preconditions;
import io.github.linuxforhealth.rules.api.Fact;
import io.github.linuxforhealth.rules.api.RulesEvaluationResult;
import io.github.linuxforhealth.rules.api.filter.RuleFilter;
import io.github.linuxforhealth.rules.dicom.DICOMReader;
import io.github.linuxforhealth.rules.fact.DataValues;
import io.github.linuxforhealth.rules.fact.DicomAttributeFact;
import io.github.linuxforhealth.rules.fact.FileFact;
import io.github.linuxforhealth.rules.rule.RuleDef;
import io.github.linuxforhealth.rules.rule.RuleFormat;

/**
 * This rules engine evaluates rules on a DICOM file fact using rules that support
 * DicomRuleCondition.
 * 
 * @author pbhallam
 *
 */
public class DicomFileRulesEvaluator extends BasicRulesEvaluator {

  private DICOMReader dicomreader;


  public DicomFileRulesEvaluator(List<RuleDef> ruleDef) {
    super(ruleDef);
    init();

  }



  public DicomFileRulesEvaluator(File rulesFile) {
    super(rulesFile);
    init();

  }


  public DicomFileRulesEvaluator(String ruleDefinition, RuleFormat format) {
    super(ruleDefinition, format);
    init();

  }



  private void init() {
    Set<String> tags = new HashSet<>();
    this.getRuleDef().forEach(r -> r.getAttributNames().forEach(e -> tags.add(e)));
    this.dicomreader = new DICOMReader(tags);
  }



  /**
   * Evaluates rules on either single DICOM file
   * 
   * @param file -- Input can either be a single DICOM file
   * @return single result {@link RulesEvaluationResult} object for rules evaluation .
   * @throws IOException
   */

  public RulesEvaluationResult apply(FileFact fact) throws IOException {
    Preconditions.checkArgument(fact != null && fact.getValue() != null && fact.getValue().exists()
        && fact.getValue().isFile(), " file must exist and should be of type file.");

    DataValues attrs = dicomreader.parse(fact.getValue());
    Fact<DataValues> f = new DicomAttributeFact(fact.geIdentifier(), attrs);
    return super.apply(f);


  }


  /**
   * 
   * @param fact
   * @param filter
   * @return
   * @throws IOException
   */

  public RulesEvaluationResult apply(FileFact fact, RuleFilter filter) throws IOException {
    Preconditions.checkArgument(fact != null && fact.getValue() != null && fact.getValue().exists()
        && fact.getValue().isFile(), " file must exist and should be of type file.");

    DataValues attrs = dicomreader.parse(fact.getValue());
    Fact<DataValues> f = new DicomAttributeFact(fact.geIdentifier(), attrs);
    return super.apply(f, filter);


  }



}
