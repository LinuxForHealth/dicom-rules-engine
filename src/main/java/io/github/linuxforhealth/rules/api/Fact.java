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





}
