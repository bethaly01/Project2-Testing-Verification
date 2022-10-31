package edu.byu.cs329.typechecker;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import edu.byu.cs329.utils.JavaSourceUtils;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for the SymbolTableBuilder")
public class SymbolTableBuilderTests {
  SymbolTableBuilder stb = null;

  @BeforeEach
  void beforeEach() {
    stb = new SymbolTableBuilder();
  }

  @Test
  @DisplayName("Should throw Exception when program has imports")
  void should_throwException_when_programHasImports() {
    ASTNode compilationUnit = JavaSourceUtils.getAstNodeFor(this, "symbolTable/should_throwException_when_programHasImports.java");
    assertThrows(RuntimeException.class, () -> stb.getSymbolTable(compilationUnit));
  }

  @Test
  @DisplayName("Should throw Exception when program defines two classes")
  void should_throwException_when_programDefinesTwoClasses() {
    ASTNode compilationUnit = JavaSourceUtils.getAstNodeFor(this, "symbolTable/should_throwException_when_programDefinesTwoClasses.java");
    assertThrows(RuntimeException.class, () -> stb.getSymbolTable(compilationUnit));
  }

  @Test
  @DisplayName("Should throw Exception when program defines inner class")
  void should_throwException_when_programDefinesInnerClass() {
    ASTNode compilationUnit = JavaSourceUtils.getAstNodeFor(this, "symbolTable/should_throwException_when_programDefinesInnerClass.java");
    assertThrows(RuntimeException.class, () -> stb.getSymbolTable(compilationUnit));
  }

  @Test
  @DisplayName("Should throw Exception when multiple variables in fragments")
  void should_throwException_when_multipleVariablesInFragments() {
    ASTNode compilationUnit = JavaSourceUtils.getAstNodeFor(this, "symbolTable/should_throwException_when_multipleVariablesInFragments.java");
    assertThrows(RuntimeException.class, () -> stb.getSymbolTable(compilationUnit));
  }

  @Test
  @DisplayName("Should throw Exception when type name is not simple")
  void should_throwException_when_typeNameIsNotSimple() {
    ASTNode compilationUnit = JavaSourceUtils.getAstNodeFor(this, "symbolTable/should_throwException_when_typeNameIsNotSimple.java");
    assertThrows(RuntimeException.class, () -> stb.getSymbolTable(compilationUnit));
  }

  @Test
  @DisplayName("Should throw Exception when primitive type is not int or boolean")
  void should_throwException_when_primitiveTypeIsNotIntOrBoolean() {
    ASTNode compilationUnit = JavaSourceUtils.getAstNodeFor(this, "symbolTable/should_throwException_when_primitiveTypeIsNotIntOrBoolean.java");
    assertThrows(RuntimeException.class, () -> stb.getSymbolTable(compilationUnit));
  }

  @Test
  @DisplayName("Should throw Exception when modifiers not private, public, or protected")
  void should_throwException_when_modifiersNotPrivatePublicProtected() {
    ASTNode compilationUnit = JavaSourceUtils.getAstNodeFor(this, "symbolTable/should_throwException_when_modifiersNotPrivatePublicProtected.java");
    assertThrows(RuntimeException.class, () -> stb.getSymbolTable(compilationUnit));
  }

  @Test
  @DisplayName("Should throw Exception when methods have same name but different parameter types")
  void should_throwException_when_methodsHaveSameNameButDifferentParameterTypes() {
    ASTNode compilationUnit = JavaSourceUtils.getAstNodeFor(this, "symbolTable/should_throwException_when_methodsHaveSameNameButDifferentParameterTypes.java");
    assertThrows(RuntimeException.class, () -> stb.getSymbolTable(compilationUnit));
  }

  @Test
  @DisplayName("Should add all fields when all fields correctly declared")
  void should_addAllFields_when_allFieldsCorrectlyDeclared() {
    ASTNode compilationUnit = JavaSourceUtils.getAstNodeFor(this, "symbolTable/should_addAllFields_when_allFieldsCorrectlyDeclared.java");
    ISymbolTable st = stb.getSymbolTable(compilationUnit);
    assertAll(
        () -> assertEquals(TypeCheckTypes.INT, st.getType("should_addAllFields_when_allFieldsCorrectlyDeclared.i")),
        () -> assertEquals(TypeCheckTypes.INT, st.getType("should_addAllFields_when_allFieldsCorrectlyDeclared.j")),
        () -> assertEquals("should_addAllFields_when_allFieldsCorrectlyDeclared", st.getType("should_addAllFields_when_allFieldsCorrectlyDeclared.k")),
        () -> assertEquals("Integer", st.getType("should_addAllFields_when_allFieldsCorrectlyDeclared.m"))
    );
  }

  @Test
  @DisplayName("Should create parameter type maps when methods defined")
  void should_createParameterTypeMaps_when_methodsExist() {
    ASTNode compilationUnit = JavaSourceUtils.getAstNodeFor(this, "symbolTable/should_createParameterTypeMaps_when_methodsExist.java");
    ISymbolTable st = stb.getSymbolTable(compilationUnit);
    String className = "should_createParameterTypeMaps_when_methodsExist";
    String nameForM = TypeCheckUtils.buildName(className, "m");
    String nameForN = TypeCheckUtils.buildName(className, "n");
    List<SimpleImmutableEntry<String, String>> typeListForM = st.getParameterTypeList(nameForM);
    List<SimpleImmutableEntry<String, String>> typeListForN = st.getParameterTypeList(nameForN);
    
    assertAll(
        () -> assertEquals(TypeCheckTypes.VOID, st.getType(nameForM)),
        () -> assertEquals(2, typeListForM.size()),
        () -> assertEquals(0, typeListForM.indexOf(
              new SimpleImmutableEntry<String, String>("i", TypeCheckTypes.INT))),
        () -> assertEquals(1, typeListForM.indexOf(
                new SimpleImmutableEntry<String, String>("j", "Integer"))),
        () -> assertEquals(TypeCheckTypes.INT, st.getType(nameForN)),
        () -> assertEquals(0, typeListForN.size())
    );  
  }

  @Test
  @DisplayName("Should add and remove scopes when adding and removing locals")
  void should_addAndRemoveScopes_when_addingAndRemovingLocals() {
    ASTNode compilationUnit = JavaSourceUtils.getAstNodeFor(this, "symbolTable/should_addAllFields_when_allFieldsCorrectlyDeclared.java");
    ISymbolTable st = stb.getSymbolTable(compilationUnit);
    st.pushScope();
    st.addLocal("i", "int");
    st.addLocal("j", "Integer");
    assertEquals(TypeCheckTypes.INT, st.getType("i"));
    assertEquals("Integer", st.getType("j"));
    st.popScope();
    assertEquals(TypeCheckTypes.ERROR, st.getType("i"));
    assertEquals(TypeCheckTypes.ERROR, st.getType("j"));
  }

  @Test
  @DisplayName("Should throw exception when adding duplicate local variables")
  void should_throwException_when_addingDuplicateLocalVariables() {
    ASTNode compilationUnit = JavaSourceUtils.getAstNodeFor(this, "symbolTable/should_addAllFields_when_allFieldsCorrectlyDeclared.java");
    ISymbolTable st = stb.getSymbolTable(compilationUnit);
    assertThrows(RuntimeException.class, () -> {
      st.pushScope();
      st.addLocal("i", "int");
      st.addLocal("i", "Integer");
    });
  }
}
