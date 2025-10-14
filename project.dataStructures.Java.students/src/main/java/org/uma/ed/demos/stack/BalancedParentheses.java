package org.uma.ed.demos.stack;

import org.uma.ed.datastructures.stack.LinkedStack;
import org.uma.ed.datastructures.stack.Stack;


/**
 * Solves the "Balanced Parentheses" problem using a Stack.
 * <p>
 * The problem: Given a string containing brackets—'(', ')', '{', '}', '[', ']'—determine
 * if the input string is valid. An input string is valid if:
 * <ol>
 *     <li>Open brackets must be closed by the same type of brackets.</li>
 *     <li>Open brackets must be closed in the correct order.</li>
 * </ol>
 * For instance, {@code "()[]{}"} is valid, but {@code "([)]"} is not.
 * <p>
 * This solution uses a stack to keep track of the open brackets encountered. The LIFO
 * (Last-In-First-Out) property of the stack perfectly models the "last opened must be
 * first closed" rule.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class BalancedParentheses {

  /**
   * Returns the matching opening bracket for a given closing bracket.
   *
   * @param c A closing bracket character: ')', '}', or ']'.
   * @return The corresponding opening bracket: '(', '{', or '['. Returns '\0' if the input
   *         character is not a recognized closing bracket.
   */
  private static char openingBracketFor(char c) {
    return switch (c) {
      case ')' -> '(';
      case '}' -> '{';
      case ']' -> '[';
      default -> '\0'; // Not a closing bracket
    };
  }

  /**
   * Checks if a string containing brackets is balanced.
   *
   * @param expression The string to check.
   * @return {@code true} if the brackets in the string are balanced, {@code false} otherwise.
   */
  public static boolean areBalanced(String expression) {
    if (expression == null) {
      return true; // Or false, depending on requirements for null input. True is a safe default.
    }

    Stack<Character> stack = LinkedStack.empty();

    for (char c : expression.toCharArray()) {
      if (isOpenBracket(c)) {
        // 1. If it's an opening bracket, push it onto the stack.
        stack.push(c);
      } else if (isCloseBracket(c)) {
        // 2. If it's a closing bracket:
        //    a. If the stack is empty, there's no matching open bracket. Invalid.
        if (stack.isEmpty()) {
          return false;
        }

        //    b. Pop the top element and check if it's the correct opening bracket.
        char lastOpenBracket = stack.top();
        stack.pop();

        if (lastOpenBracket != openingBracketFor(c)) {
          return false; // Mismatched brackets. Invalid.
        }
      }
      // Non-bracket characters are ignored.
    }

    // 3. After iterating through the whole string, if the stack is empty,
    //    all brackets were correctly matched and closed. Valid.
    //    If not, there are unclosed opening brackets. Invalid.
    return stack.isEmpty();
  }

  /**
   * Helper method to check if a character is an opening bracket.
   */
  private static boolean isOpenBracket(char c) {
    return c == '(' || c == '{' || c == '[';
  }

  /**
   * Helper method to check if a character is a closing bracket.
   */
  private static boolean isCloseBracket(char c) {
    return openingBracketFor(c) != '\0';
  }

  public static void main(String[] args) {
    System.out.println("--- Checking for Balanced Parentheses ---");

    String[] testCases = {
        "()",                  // Valid
        "()[]{}",              // Valid
        "(]",                  // Invalid
        "([)]",                // Invalid
        "{[]}",                // Valid
        "public static void main(String[] args) { System.out.println(); }", // Valid (ignores other chars)
        "((())",               // Invalid (unclosed)
        "())",                 // Invalid (premature close)
        ""                     // Valid (empty)
    };

    for (String testCase : testCases) {
      boolean isBalanced = areBalanced(testCase);
      System.out.printf("Expression: \"%-50s\" -> Is balanced? %b%n", testCase, isBalanced);
    }
  }
}