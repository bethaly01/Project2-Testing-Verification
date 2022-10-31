package edu.byu.cs329.typechecker;

public class TypeCheckTypes {
  
  public static final String INT = "int";
  public static final String BOOL = "boolean";
  public static final String VOID = "void";
  public static final String NULL = "nullType";
  public static final String ERROR = "ERROR";

  public static boolean isPrimitive(String type) {
    return type.equals(INT) || type.equals(BOOL);
  }

  public static boolean isError(String type) {
    return type.equals(ERROR);
  }

  public static boolean isNullType(String type) {
    return type.equals(NULL);
  }

  public static boolean isVoidType(String type) {
    return type.equals(VOID);
  }

  /**
   * Determines if two types are assignment compatible.
   * 
   * @param leftType type
   * @param rightType type
   * @return true if assignment compatible
   */
  public static boolean isAssignmentCompatible(String leftType, String rightType) {
    if (isError(leftType) || isError(rightType)) {
      return false;
    }
    if (isNullType(leftType) || isVoidType(leftType)) {
      return false;
    } 

    return (!isPrimitive(leftType) && isNullType(rightType))
        || leftType.equals(rightType);
  }

}
