# Part 2: Regression Testing

The objective of this assignment is to write tests that test the tests generated for the type checker to create a regression set of tests, and then run mutation analysis on the regression set adding new tests as needed to kill all mutants.

## Reading

[PIT Mutation Analysis](https://bitbucket.org/byucs329/byu-cs-329-lecture-notes/src/master/pit-mutation-analysis/mutation-analysis.md)

## Requirements

You will continue with your submission from [Part 1](./Part_1.md), so first merge pull-request from [Part 1](./Part_1.md) in the the `main` branch. Then create another feature branch based to start on this assignment--that branch should not include the commits from [Part 1](./Part_1.md).

## Tests to Test the Dynamic Tests

Part 1 uses visual inspection to check that the generated type proof (e.g., the `DynamicNode` returned from `TypeCheckBuilder` holding the tests for the type proof) is correct. This assignment requires tests to test those tests. In other words, create tests that make sure the set of generated dynamic tests from the `TypeCheckBuilder` is correct. A test to test the tests needs to inspect the contents and structure of the `DynamicNode` returned from the `TypeCheckBuilder` and make sure it has its expected structure and content for the type proof. If it does not, then the test fails.

The `DynamicNode` class provides a `DynamicNode.getDisplayName()` method. The `DynamicContainer` class gives access to the stream of nodes in the container with `DynamicContainer.getChildren()`. The `DynamicTest` class gives access to the embedded executable with `DynamicTest.getExecutable()`. The `Executable` class is able to run the test with `Executable.execute()`. If the test fails, then it throws an instance of `AssertionFailedError`. These methods provide the interface to write tests to test tests. Here is a simplistic example.

```java
@Test
  void myTest() {
    String fileName = "typeChecker/should_proveTypeSafe_when_givenVariableDeclrationsWithCompatibleInits.java";
    List<DynamicNode> tests = new ArrayList<>();
    boolean isTypeSafe = getTypeChecker(fileName, tests);
    Assertions.assertTrue(isTypeSafe);
    Iterator<DynamicNode> iter = tests.iterator();
    DynamicNode n = iter.next();
    Assertions.assertTrue(n instanceof DynamicTest);
    DynamicTest t = (DynamicTest) n;
    Assertions.assertDoesNotThrow(t.getExecutable());
    n = iter.next();
    Assertions.assertTrue(n instanceof DynamicTest);
    t = (DynamicTest) n;
    Assertions.assertThrows(AssertionFailedError.class, t.getExecutable());
  }
```

The above is more detailed than it should be for the type checker test code, but it shows some of what is possible. The `DynamicContainer` only give access to a stream, but from there it should be possible to roughly test for an expected structure it terms of display names, containers, number of tests, or even number of tests in each container.  The [lecture notes](https://bitbucket.org/byucs329/byu-cs-329-lecture-notes/src/master/type-checking/type-checking.md) have a detailed discussion to what these tests might look like for the type checker.

Write a minimum set of regression tests to cover the behavior of the type checker. The tests from Part 1 are an excellent starting point. The only difference here is that those tests do not run directly but are rather inspected for correctness by the tests to be written for this assignment.

## PIT Mutation Analysis

Analyze the fitness of the regression test suite using [PIT](http://pitest.org) for mutations analysis with the `STRONGER` group. The `pom.xml` is already configured for the PIT analysis. That analysis is run with `mvn test org.pitest:pitest-maven:mutationCoverage`. The report is in the `./target/pit-reports` directory organized by time-stamp.

Add tests to kill all mutants or argue why a mutant cannot be killed. Clearly indicate where the explanations are located in the pull request. Note that the analysis covers everything in the `edu.byu.cs329.typechecker` package so additional tests for the `SymbolTableBuilder` may be required.

## What to turn in?

Create a pull request of your feature branch containing the solution and ensure that your Github workflow build passes (be sure you have pushed any changes to `project-utils` and have updated the submodules appropriately--see [README.md](README.md)). Submit to Canvas the URL of the pull request.

## Rubric

| Item | Point Value |
| ------- | ----------- |
| Tests to test the dynamic tests | 50 |
| PIT Mutation Analysis | 50 |
| Style, documentation, naming conventions, test organization, readability, etc. | 25 |
