
package io.github.linuxforhealth.rules.api.filter;

import org.apache.commons.lang3.StringUtils;
import com.google.common.base.Preconditions;
import io.github.linuxforhealth.rules.api.Rule;

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
