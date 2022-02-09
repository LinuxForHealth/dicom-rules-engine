/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.api.filter;

import java.util.function.Predicate;
import io.github.linuxforhealth.rules.api.Rule;

/**
 * Implement this interface for filter rules. The rules that pass the predicate will be applied
 * during rules evaluations.
 * 
 * @author pbhallam
 *
 */
public interface RuleFilter extends Predicate<Rule> {



}
