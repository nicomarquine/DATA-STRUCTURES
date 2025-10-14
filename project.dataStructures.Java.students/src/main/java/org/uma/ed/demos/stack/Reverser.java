package org.uma.ed.demos.stack;

import org.uma.ed.datastructures.stack.ArrayStack;
import org.uma.ed.datastructures.stack.Stack;

/**
 * A simple demonstration of using a {@link Stack} to reverse a string.
 * <p>
 * The program iterates through a given string, pushing each character onto a stack.
 * Afterwards, it pops each character from the stack and prints it. Due to the LIFO
 * (Last-In-First-Out) nature of the stack, the characters are retrieved and printed
 * in the reverse of their original order.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class Reverser {

  public static void main(String[] args) {
    String message = "hello world!";
    System.out.println("Original message: " + message);

    // Use a Stack to store the characters of the message.
    Stack<Character> stack = ArrayStack.empty();

    // 1. Push all characters from the string onto the stack.
    System.out.println("Pushing characters onto the stack...");
    for (int i = 0; i < message.length(); i++) {
      stack.push(message.charAt(i));
    }

    System.out.println("Current stack state: " + stack);
    System.out.println();

    // 2. Pop characters from the stack to build the reversed message.
    System.out.print("Reversed message: ");
    while (!stack.isEmpty()) {
      System.out.print(stack.top());
      stack.pop();
    }
    System.out.println(); // For a final newline.
  }
}