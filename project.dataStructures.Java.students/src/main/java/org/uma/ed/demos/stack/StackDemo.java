package org.uma.ed.demos.stack;

import org.uma.ed.datastructures.range.Range;
import org.uma.ed.datastructures.stack.ArrayStack;
import org.uma.ed.datastructures.stack.LinkedStack;
import org.uma.ed.datastructures.stack.Stack;

/**
 * A demonstration class for various {@link Stack} implementations.
 * <p>
 * This demo illustrates:
 * <ul>
 *     <li>Basic stack operations (push, pop, top) on a {@code LinkedStack}.</li>
 *     <li>Creation of stacks using different factory methods like {@code of()} and {@code from()}.</li>
 *     <li>The behavior of the {@code equals()} method, showing that two stacks are equal if they
 *         contain the same elements in the same order, regardless of their underlying implementation.</li>
 * </ul>
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class StackDemo {
  public static void main(String[] args) {
    System.out.println("--- Demo 1: Basic operations with LinkedStack ---");
    Stack<Integer> stack1 = LinkedStack.empty();
    stack1.push(1);
    stack1.push(2);
    stack1.push(3);
    stack1.push(4);

    System.out.println("Stack after pushing 1, 2, 3, 4: " + stack1);
    int topElement = stack1.top();
    System.out.println("Top element is: " + topElement);
    stack1.pop();
    System.out.println("Stack after one pop: " + stack1);
    System.out.println();

    System.out.println("--- Demo 2: Comparing different implementations ---");
    // stack2 is an ArrayStack with the same logical content as stack1 after the pop.
    Stack<Integer> stack2 = ArrayStack.empty();
    stack2.push(1);
    stack2.push(2);
    stack2.push(3);
    System.out.println("An ArrayStack with elements 1, 2, 3 pushed: " + stack2);
    System.out.println();

    // stack3 is created using the `of` factory method. Note the order of arguments vs final stack.
    Stack<Integer> stack3 = ArrayStack.of(1, 2, 3);
    System.out.println("An ArrayStack created with `of(1, 2, 3)`: " + stack3);
    System.out.println();

    System.out.println("--- Equality Checks ---");
    System.out.println("Is stack1 (LinkedStack) equal to stack2 (ArrayStack)? -> " + stack1.equals(stack2));
    System.out.println("Is stack1 (LinkedStack) equal to stack3 (ArrayStack)? -> " + stack1.equals(stack3));
    System.out.println("Is stack2 (ArrayStack) equal to stack3 (ArrayStack)? -> " + stack2.equals(stack3));
    System.out.println();

    System.out.println("--- Demo 3: Creating a stack from a Range iterable ---");
    // Elements from the range [0, 2, 4, 6, 8, 10] will be pushed onto the stack.
    Stack<Integer> stack4 = ArrayStack.from(Range.inclusive(0, 10, 2));
    System.out.println("Stack created from a Range(0 to 10, step 2): " + stack4);
  }
}