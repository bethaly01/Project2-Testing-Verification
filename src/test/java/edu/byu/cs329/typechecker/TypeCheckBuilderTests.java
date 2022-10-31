package edu.byu.cs329.typechecker;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import edu.byu.cs329.utils.JavaSourceUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.jdt.core.dom.ASTNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("Tests for TypeCheckerBuilder")
public class TypeCheckBuilderTests {
  static final Logger log = LoggerFactory.getLogger(TypeCheckBuilderTests.class);

  private boolean getTypeChecker(final String fileName, List<DynamicNode> tests) {
    ASTNode compilationUnit = JavaSourceUtils.getAstNodeFor(this, fileName);
    SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();
    ISymbolTable symbolTable = symbolTableBuilder.getSymbolTable(compilationUnit);
    TypeCheckBuilder typeCheckerBuilder = new TypeCheckBuilder();
    return typeCheckerBuilder.getTypeChecker(symbolTable, compilationUnit, tests);
  }
 
  @TestFactory
  @DisplayName("Should prove type safe when given empty class")
  Stream<DynamicNode> should_proveTypeSafe_when_givenEmptyClass() {
    String fileName = "typeChecker/should_proveTypeSafe_when_givenEmptyClass.java";
    List<DynamicNode> tests = new ArrayList<>();
    boolean isTypeSafe = getTypeChecker(fileName, tests);
    DynamicTest test = DynamicTest.dynamicTest("isTypeSafe", () -> assertTrue(isTypeSafe));
    tests.add((DynamicNode)test);
    return tests.stream();
  }

  @TestFactory
  @DisplayName("Should prove type safe when given empty method")
  Stream<DynamicNode> should_proveTypeSafe_when_givenEmptyMethod() {
    String fileName = "typeChecker/should_proveTypeSafe_when_givenEmptyMethod.java";
    List<DynamicNode> tests = new ArrayList<>();
    boolean isTypeSafe = getTypeChecker(fileName, tests);
    DynamicTest test = DynamicTest.dynamicTest("isTypeSafe", () -> assertTrue(isTypeSafe));
    tests.add((DynamicNode)test);
    return tests.stream();
  }

  @TestFactory
  @DisplayName("Should prove type safe when given empty block")
  Stream<DynamicNode> should_proveTypeSafe_when_givenEmptyBlock() {
    String fileName = "typeChecker/should_proveTypeSafe_when_givenEmptyBlock.java";
    List<DynamicNode> tests = new ArrayList<>();
    boolean isTypeSafe = getTypeChecker(fileName, tests);
    DynamicTest test = DynamicTest.dynamicTest("isTypeSafe", () -> assertTrue(isTypeSafe));
    tests.add((DynamicNode)test);
    return tests.stream();
  }

  @TestFactory
  @DisplayName("Should prove type safe when given variable declarations no inits")
  Stream<DynamicNode> should_proveTypeSafe_when_givenVariableDeclrationsNoInits() {
    String fileName = "typeChecker/should_proveTypeSafe_when_givenVariableDeclrationsNoInits.java";
    List<DynamicNode> tests = new ArrayList<>();
    boolean isTypeSafe = getTypeChecker(fileName, tests);
    DynamicTest test = DynamicTest.dynamicTest("isTypeSafe", () -> assertTrue(isTypeSafe));
    tests.add((DynamicNode)test);
    return tests.stream();
  }

  @TestFactory
  @DisplayName("Should prove type safe when given variable declarations with compatible inits")
  Stream<DynamicNode> should_proveTypeSafe_when_givenVariableDeclrationsWithCompatibleInits() {
    String fileName = "typeChecker/should_proveTypeSafe_when_givenVariableDeclrationsWithCompatibleInits.java";
    List<DynamicNode> tests = new ArrayList<>();
    boolean isTypeSafe = getTypeChecker(fileName, tests);
    DynamicTest test = DynamicTest.dynamicTest("isTypeSafe", () -> assertTrue(isTypeSafe));
    tests.add((DynamicNode)test);
    return tests.stream();
  }

  @TestFactory
  @DisplayName("Should not prove type safe when given bad inits")
  Stream<DynamicNode> should_NotProveTypeSafe_when_givenBadInits() {
    String fileName = "typeChecker/should_NotProveTypeSafe_when_givenBadInits.java";
    List<DynamicNode> tests = new ArrayList<>();
    boolean isTypeSafe = getTypeChecker(fileName, tests);

    // Toggle as desired
    // 
    // Option 1: mvn exec:java shows the details of the typeproof for visual inspection
    // return tests.stream();
    //
    // Option 2: test only isNotTypeSafe and show no details
    DynamicTest test = DynamicTest.dynamicTest("isNotTypeSafe", () -> assertFalse(isTypeSafe));
    return Arrays.asList((DynamicNode)test).stream();
  }
}
