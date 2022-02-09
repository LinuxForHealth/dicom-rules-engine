package io.github.linuxforhealth.rules.api;

public interface Fact<T> {


  /**
   * Get the fact name.
   * 
   * @return fact name
   */
  String geIdentifier();

  /**
   * Get the fact value.
   * 
   * @return fact value
   */
  T getValue();

  // void putResultOfRule(String parentGroupLable, String ruleName, boolean result);

  // void addExceptions(String parentGroupLable, String ruleName, Exception e);





}
