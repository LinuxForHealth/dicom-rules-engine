
package io.github.linuxforhealth.rules.api.filter;

import org.apache.commons.lang3.StringUtils;
import com.google.common.base.Preconditions;
import io.github.linuxforhealth.rules.api.Rule;

public class RuleIdentifierFilter implements RuleFilter {

  private String rulename;
  private String groupid;

  public RuleIdentifierFilter(String rulename, String groupid) {
    Preconditions.checkArgument(StringUtils.isNotBlank(rulename), "Rule name cannot be blank.");
    Preconditions.checkArgument(StringUtils.isNotBlank(groupid), "groupid  cannot be blank.");
    this.rulename = rulename;
    this.groupid = groupid;
  }
  @Override
  public boolean test(Rule t) {
   
    return StringUtils.equalsIgnoreCase(rulename, t.getName())
        && StringUtils.equalsIgnoreCase(groupid, t.getGroupId());
  }

}
