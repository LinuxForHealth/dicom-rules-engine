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
import io.github.linuxforhealth.rules.fact.InputStreamFact;
import io.github.linuxforhealth.rules.rule.RuleDef;
import io.github.linuxforhealth.rules.rule.RuleFormat;

/**
 * This rules engine evaluates rules on a DICOM file fact using rules that support
 * DicomRuleCondition.
 * 
 * @author pbhallam
 *
 */
public class DicomStreamRulesEvaluator extends BasicRulesEvaluator {
  private DICOMReader dicomreader;

  public DicomStreamRulesEvaluator(List<RuleDef> ruleDef) {
    super(ruleDef);
    init();

  }



  public DicomStreamRulesEvaluator(File rulesFile) {
    super(rulesFile);
    init();

  }


  public DicomStreamRulesEvaluator(String ruleDefinition, RuleFormat format) {
    super(ruleDefinition, format);
    init();

  }



  private void init() {
    Set<String> tags = new HashSet<>();
    this.getRuleDef().forEach(r -> r.getAttributNames().forEach(tags::add));
    this.dicomreader = new DICOMReader(tags);
  }



  /**
   * Evaluates rules on either single DICOM file or on each DICOM file in the study folder.
   * 
   * @param file -- Input can either be a single DICOM file or DICOM study directory.
   * @return single result {@link RulesEvaluationResult} object for rules evaluation and in case of
   *         study this is a consolidated result.
   * @throws IOException
   */


  public RulesEvaluationResult apply(InputStreamFact fact) {
    
    Preconditions.checkArgument(fact != null,
        " Inputstream fact cannot be null.");
    Fact<DataValues> f = new DicomAttributeFact(fact.geIdentifier(), fact.getValue(dicomreader));
    return super.apply(f);


  }


  public RulesEvaluationResult apply(InputStreamFact fact, RuleFilter filter) throws IOException {
    Preconditions.checkArgument(fact != null, " Inputstream fact cannot be null.");
    Fact<DataValues> f = new DicomAttributeFact(fact.geIdentifier(), fact.getValue(dicomreader));
    return super.apply(f, filter);


  }


}
