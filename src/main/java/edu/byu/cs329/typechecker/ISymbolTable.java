package edu.byu.cs329.typechecker;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;

public interface ISymbolTable {

  /**
   * Gives the type associated with a name.
   * 
   * @ensures name \in INT ==> type = INT
   * @ensures name \in BOOL ==> type = BOOL
   * @ensures name \in TypeMap ==> type = TypeMap(name)
   * @ensures else ==> type = ERROR
   * 
   * @param name is a local variable or parameter as in "v",
   *     an object instance reference as "this", 
   *     a field reference as "class"."field" as in "A.f",
   *     a method reference as "class"."method" as in "A.m",
   *     a boolean literal as in "true" or "false", or
   *     a integer literal as in "10"
   * @return associated type for name
   */
  public String getType(String name);

  /**
   * Gives the parameters and types associated with the named method.
   * 
   * @param name a method reference as "class"."method" as in "A.m",
   * @return the type list for associated parameter names
   */
  public List<SimpleImmutableEntry<String, String>> getParameterTypeList(String name);
  
  /**
   * Pushes new scope for local variables.
   */
  public void pushScope();
  
  /**
   * Pops the current local variable scope.
   */
  public void popScope();

  /**
   * Associates the type with the name in the top scope on the stack.
   * 
   * @requires name \not\in Types
   * 
   * @ensures name \in Types /\ Types(name) = type
   * 
   * @param name a local variable name
   * @param type the type of the local variable
   */
  public void addLocal(String name, String type);
}
