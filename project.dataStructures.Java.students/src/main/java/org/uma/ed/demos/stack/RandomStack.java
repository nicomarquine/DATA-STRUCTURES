package org.uma.ed.demos.stack;

import org.uma.ed.datastructures.stack.Stack;

import java.util.Random;

/**
 * A utility class for performing a sequence of random operations on a {@link Stack}.
 * <p>
 * This is primarily used for testing and performance benchmarking of stack implementations.
 * It simulates a typical workload by mixing push and pop operations.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public final class RandomStack {

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private RandomStack() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Fills a given stack by performing a specified number of random operations.
   * <p>
   * The sequence of operations is determined by the provided seed, making the test
   * repeatable. The operations are distributed with the following probabilities:
   * <ul>
   *     <li>{@code push}: 2/3 probability (cases 0 and 1)</li>
   *     <li>{@code pop}: 1/3 probability (case 2)</li>
   * </ul>
   * The {@code pop} operation is only attempted if the stack is not empty. The integer
   * pushed onto the stack is always 0, as the value itself is typically irrelevant for
   * performance tests focused on the structure's behavior.
   *
   * @param stack              the stack to operate on.
   * @param seed               the seed for the random number generator to ensure reproducibility.
   * @param numberOfOperations the total number of random operations to perform.
   */
  public static void fill(Stack<Integer> stack, int seed, int numberOfOperations) {
    Random randomGenerator = new Random(seed);

    for (int i = 0; i < numberOfOperations; i++) {
      // Generates a random integer in [0, 2]
      int operationType = randomGenerator.nextInt(3);

      switch (operationType) {
        case 0:
        case 1:
          // Two cases for push, giving it a 2/3 probability.
          stack.push(0);
          break;
        case 2:
          // One case for pop, giving it a 1/3 probability.
          if (!stack.isEmpty()) {
            stack.pop();
          }
          break;
      }
    }
  }
}