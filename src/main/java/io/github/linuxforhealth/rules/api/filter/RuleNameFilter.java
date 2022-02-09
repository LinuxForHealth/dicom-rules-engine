
package io.github.linuxforhealth.rules.api.filter;

import org.apache.commons.lang3.StringUtils;
import com.google.common.base.Preconditions;
import io.github.linuxforhealth.rules.api.Rule;

public class RuleNameFilter implements RuleFilter {

  private String rulename;


  public RuleNameFilter(String rulename) {
    Preconditions.checkArgument(StringUtils.isNotBlank(rulename), "Rule name cannot be blank.");

    this.rulename = rulename;

  }
  @Override
  public boolean test(Rule rule) {
    return StringUtils.equalsIgnoreCase(rulename, rule.getName());
  }

}
