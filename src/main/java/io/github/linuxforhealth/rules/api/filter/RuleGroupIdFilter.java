
/*
 * (C) Copyright IBM Corp. 2021
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package io.github.linuxforhealth.rules.api.filter;

import org.apache.commons.lang3.StringUtils;
import com.google.common.base.Preconditions;
import io.github.linuxforhealth.rules.api.Rule;

/**
 * Rules with the provided group id will be applied.
 * 
 * @author pbhallam
 *
 */
public class RuleGroupIdFilter implements RuleFilter {

  private String groupid;

  public RuleGroupIdFilter(String groupid) {

    Preconditions.checkArgument(StringUtils.isNotBlank(groupid), "groupid  cannot be blank.");

    this.groupid = groupid;
  }

  @Override
  public boolean test(Rule t) {
   
    return StringUtils.equalsIgnoreCase(groupid, t.getGroupId());
  }

}
