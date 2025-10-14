package org.uma.ed.demos.stack;

import org.uma.ed.datastructures.stack.LinkedStack;
import org.uma.ed.datastructures.stack.Stack;

/**
 * A simple demonstration class for the {@link LinkedStack} implementation.
 * <p>
 * This class shows basic stack operations: pushing multiple elements, popping some of them,
 * and clearing the stack.
 *
 * @author Blas Ruiz, Data Structures, Grado en Informática. UMA.
 */
public class LinkedStackDemo {

  public static void main(String[] args) {
    Stack<Integer> stack = LinkedStack.empty();

    System.out.println("Pushing numbers from 0 to 54...");
    for (int i = 0; i < 55; i++) {
      stack.push(i);
    }
    System.out.println("Current stack state: " + stack);
    System.out.println();

    System.out.println("Popping 10 elements and then pushing the number 1000...");
    for (int i = 0; i < 10; i++) {
      stack.pop();
    }
    stack.push(1000);
    System.out.println("Current stack state: " + stack);
    System.out.println();

    System.out.println("Popping all remaining elements...");
    while (!stack.isEmpty()) {
      stack.pop();
    }
    System.out.println("Current stack state after popping all elements: " + stack);
    System.out.println();

    System.out.println("Pushing numbers from 0 to 13...");
    for (int i = 0; i < 14; i++) {
      stack.push(i);
    }
    System.out.println("Final stack state: " + stack);
  }
}