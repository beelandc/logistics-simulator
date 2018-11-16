package net.cbeeland.util;

import net.cbeeland.exception.DataValidationException;

public class InputValidator {

  public static void validateStringNotNullOrEmpty(String value, String variableName, String className, String methodSignature) throws DataValidationException {
    if (value == null || value.isEmpty()) {
      throw new DataValidationException((value == null ? "Null " : "Empty ") + variableName + " string passed into " + className + "." + methodSignature);
    }
  }

  public static void validatePositiveInteger(int value, String fieldName, String className, String methodSignature) throws DataValidationException {
    if (value <= 0) {
      throw new DataValidationException("Non-positive integer value for " + fieldName + " passed into " + className + "." + methodSignature + "." + fieldName
          + " must be an integer value > 0");
    }
  }

  public static void validateNonNegativeInteger(int value, String fieldName, String className, String methodSignature) throws DataValidationException {
    if (value < 0) {
      throw new DataValidationException(
          "Negative integer value for " + fieldName + " passed into " + className + "." + methodSignature + "." + fieldName + " must be an integer value >= 0");
    }
  }

  public static void validateInstantiatedObject(Object value, String fieldName, String className, String methodSignature) throws DataValidationException {
    if (value == null) {
      throw new DataValidationException("Null value for " + fieldName + " passed into " + className + "." + methodSignature);
    }
  }

}
