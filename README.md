# LinuxForHealth DICOM rules engine 

The LinuxForHealth DICOM rules engine is a Java based library that enables writing filtering rules for DICOM using standard DICOM tags. The rules for the engine cane be defined in a YAML or JSON format.


## Development Quickstart

The DICOM rules engine has the following dependencies:

* JDK 11 or later
* Gradle 

Note: The DICOM rules engine includes a Gradle Wrapper, so a local Gradle install is not required.

Clone and build the project:
```
git clone git@github.com:LinuxForHealth/dicom-rules-engine.git
cd dicom-rules-engine
./gradlew build
```

## Using The DICOM Rules Engine In A Java Application

The DICOM rules engine library is available as a maven dependency. 

Library Coordinates
```
groupId = io.github.linuxforhealth
artifactId = dicom-rules-engine
version = 1.0.0-alpha
```

Maven dependency
```
<dependency>
  <groupId>io.github.linuxforhealth</groupId>
  <artifactId>dicom-rules-engine</artifactId>
  <version>1.0.0-alpha</version>
</dependency>
```

Gradle dependency:
```
    implementation 'io.github.linuxforhealth:dicom-rules-engine:1.0.0-alpha'
```     


### Usage
Example sample rule file:


```json
{
  "ruleDefinitions": [
    {
      "groupid": "queue1rules",
      "conditions": {
        "condition1": "$00080018 EQUALS 1.2.826.0.1.3680043.8.1055.1.20111102150800481.27482048.30798145",
        "condition2": "$0020000E EQUALS 1.2.826.0.1.3680043.8.1055.1.20111102150758591.96842950.07877442"
            },
      "rules": {
        "relevance_rule": "condition1 && condition2"
      }
    }
  ]
}
```
If rules are stored in rules file, example:rules.json

```
    File ruleFile = new File(<path to rules.json>);
    DicomRulesEvaluator eval = new DicomRulesEvaluator(ruleFile);
    RulesEvaluationResult results = eval.evaluateRules(study); // provide path to DICOM study directory
    assertThat(results.getResultOfRule("queue1rules", "relevance_rule")).isTrue();

```

If JSON representation of rules are read and stored in String object rulesDefination

```
   
    DicomRulesEvaluator eval = new DicomRulesEvaluator(rulesDefination, Format.JSON);
    RulesEvaluationResult results = eval.evaluateRules(study); // provide path to DICOM study directory
    assertThat(results.getResultOfRule("queue1rules", "relevance_rule")).isTrue();

```


# Introduction:
The purpose of the rule engine is to provide mechanism to specify conditions to identify /filter DICOM studies for conditional processing based on preconfigured rules. 
The rule will evaluate a DICOM study and return the evaluation result.

### DICOM rules have following concepts
 * Rule Definition: is a collection of rules and conditions.
 * Rule Group Id: unique lable for group of rules using common conditions.
 * Condition - Condition evaluates a single DICOM tag against a predefined predicate. 
 * Rule – Rule is collection of conditions joined with AND/ORs.
 * RuleEvaluationResult: This object holds the results of the rules evaluation. The rules evaluator evaluates all rules defined in the rules definition file. 

 
## How to create and configure rules
* Rules for the engine ccan be defined in a JSON or YAML file.
* Each rule has the following attributes.
    
## Structure of a DICOM Rule
    * groupid: (REQUIRED) unique lable (no spaces aallowed)
    * attribute_name_delimiter: (OPTIONAL) character used to split nested attributes, example 000982335.000982333
    * conditions: Map of conditions. <Unique name>:<condition>
       - Condition: Condition can be of two types, BiCondition or SimpleCondition. BiCondition has variable, predicate and constant value for maatching.
           Variable always staarts with a '$'. Simple condition only has a variable and predicate.
    * rules:  this is map of rules. <unique ruleIdentifier>:<rule>
      - Rule: Each rule is defined using conditions, where conditions are joined using &&/|| and can be nested using ().   

 ## Structure of a DICOM Condition
 ### Condition
   
    * variable: DICOM tag example: 00081030,    * 
    * operator: condition operator to use for this condition, details below.
    * match_value: value/values for matching
   
  Supported DICOM Data element value type, for match_value/match_values.
  
   * String
   * Integer
   * List
   
 ## List of supported String type tag operators
 
    * EQUALS - DICOM string tag value is equal to  the value provided in the rule.
    * STRING_IS_NULL - DICOM string tag value is null.
    * NOT_EQUALS - DICOM string tag value is not equal to  the value provided in the rule.
    * STARTS_WITH - DICOM string tag value starts with to  the prefix value provided in the rule.
    * NOT_STARTS_WITH - DICOM string tag value does not starts with the prefix value provided in the rule.
    * ENDS_WITH - DICOM string tag value ends with the suffix value provided in the rule.
    * NOT_ENDS_WITH - DICOM string tag value does not ends with the suffix value provided in the rule.
    * CONTAINS - DICOM string tag value contains the value provided in the rule.
    * NOT_CONTAINS - DICOM string tag value does not contains  the value provided in the rule.
    * IN_EQUALS - DICOM string tag value is equal to (ignore case) any of the values provided in the rule.
    * IN_CONTAINS - DICOM string tag value contains (ignore case) any of the values provided in the rule.
    * NOT_IN_EQUALS - DICOM string tag value is not equal to (ignore case) any of the values provided in the rule.
    * NOT_IN_CONTAINS - DICOM string tag value does not contains (ignore case) any of the values provided in the rule.
 
 ## List of supported Integer type tag operators
 
    * EQUAL_TO - DICOM integer tag value is equal to the values provided in the rule.
    * GREATER_THAN  - DICOM integer tag value is greater than the values provided in the rule.
    * LESS_THAN - DICOM integer tag value is less than the values provided in the rule.
    * GREATER_OR_EQUAL - DICOM integer tag value is greater than or equal to the values provided in the rule.
    * LESS_OR_EQUAL - DICOM integer tag value is less than or equal the values provided in the rule.
 
 ## List of supported List type tag operators
 
    * EMPTY - DICOM tag value of list type is empty.
    * NOT_EMPTY - DICOM tag value of list type is not empty.
    * ANY_CONTAINS -  Any of the list of values for a DICOM tag contains value provided in the rule.
    * ANY_NOT_CONTAINS- None of the list of values for a DICOM tag contains value provided in the rule.
    * ANY_EQUALS- Any of the list of values for a DICOM tag is equal to the value provided in the rule.
    * ANY_NOT_EQUALS - None of the list of values for a DICOM tag contains value provided in the rule.
    * ANY_IN_EQUALS - Any of the list of values for a DICOM tag is equal to (ignore case) any of the values provided in the rule.
    * ANY_NOT_IN_EQUALS - None of the list of values for a DICOM tag is equal to (ignore case) any of the values provided in the rule.
    * ANY_IN_CONTAINS -Any of the list of values for a DICOM tag contains (ignore case) any of the values provided in the rule.
    * ANY_NOT_IN_CONTAINS- Any of the list of values for a DICOM tag not contains (ignore case) any of the values provided in the rule.
    * LIST_SIZE_EQUAL- DICOM tag value list size is equal to the value provided in the rule.
    * LIST_SIZE_GREATER_THAN- DICOM tag value list size is greater than the value provided in the rule.
    * LIST_SIZE_LESS_THAN- DICOM tag value list size is less than the value provided in the rule.
    * NOT_CONTAINS_ALL- List of values for a DICOM tag not contain (ignore case) all the values provided in the rule.
    * CONTAINS_ALL- List of values for a DICOM tag contain (match is case sensitive) all the values provided in the rule.
    


