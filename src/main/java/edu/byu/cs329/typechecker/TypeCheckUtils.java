package edu.byu.cs329.typechecker;

import edu.byu.cs329.utils.AstNodePropertiesUtils;
import edu.byu.cs329.utils.ExceptionUtils;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class TypeCheckUtils {

  public static String buildName(String className, String name) {
    return className + "." + name;
  }

  public static String getType(FieldDeclaration field) {
    return getType(field.getType());
  }

  public static String getType(MethodDeclaration method) {
    return getType(method.getReturnType2());
  }

  public static String getType(SingleVariableDeclaration declaration) {
    return getType(declaration.getType());
  }

  public static String getType(VariableDeclarationStatement declaration) {
    return getType(declaration.getType());
  }

  private static String getType(Type type) {
    String typeName = null;
    if (type.isPrimitiveType()) {
      typeName = getType((PrimitiveType) type);
    } else if (type.isSimpleType()) {
      typeName = getType((SimpleType) type);
    } else {
      ExceptionUtils.throwRuntimeException(type.toString() + " is not a simple or primitive type");
    }
    return typeName;
  }

  private static String getType(SimpleType type) {
    Name name = type.getName();
    String typeName = null;
    if (name instanceof SimpleName) {
      typeName = AstNodePropertiesUtils.getName((SimpleName) name);
    } else {
      ExceptionUtils.throwRuntimeException(name.getFullyQualifiedName() + " is not a SimpleName");
    }
    return typeName;
  }

  private static String getType(PrimitiveType type) {
    String typeName = null;
    if (type.getPrimitiveTypeCode() == PrimitiveType.INT) {
      typeName = TypeCheckTypes.INT;
    } else if (type.getPrimitiveTypeCode() == PrimitiveType.BOOLEAN) {
      typeName = TypeCheckTypes.BOOL;
    } else if (type.getPrimitiveTypeCode() == PrimitiveType.VOID) {
      typeName = TypeCheckTypes.VOID;
    } else {
      // Not tested
      ExceptionUtils.throwRuntimeException(
          "primitive type " + type.toString() + " is not an int, boolean, or void");
    }
    return typeName;
  }
}
