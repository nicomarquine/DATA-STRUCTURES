package org.uma.ed.demos.stack;

import org.uma.ed.datastructures.stack.ArrayStack;
import org.uma.ed.datastructures.stack.LinkedStack;
import org.uma.ed.datastructures.stack.Stack;

/**
 * Compares the performance of different {@link Stack} implementations.
 * <p>
 * This program benchmarks {@code ArrayStack} versus {@code LinkedStack} by performing a
 * large number of random push and pop operations on each. It calculates the average
 * execution time over several runs to provide a more stable comparison.
 * <p>
 * The results typically show that {@code ArrayStack} is significantly faster due to
 * better memory locality and lower overhead per operation compared to the node-based
 * approach of {@code LinkedStack}.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class StacksPerformance {

  public enum Implementation {
    WithArray,
    Linked
  }

  public static void main(String[] args) {
    final int numberOfTests = 10;
    final int numberOfOperations = 10_000_000; // Increased for a more meaningful benchmark

    System.out.println("Running performance benchmark for Stack implementations...");
    System.out.printf("Number of tests per implementation: %d%n", numberOfTests);
    System.out.printf("Number of operations per test: %,d%n", numberOfOperations);
    System.out.println("-----------------------------------------------------");

    double avgTimeLinked = avgTime(Implementation.Linked, numberOfTests, numberOfOperations);
    double avgTimeWithArray = avgTime(Implementation.WithArray, numberOfTests, numberOfOperations);

    System.out.printf("Average execution time for LinkedStack: %.4f seconds%n", avgTimeLinked);
    System.out.printf("Average execution time for ArrayStack:  %.4f seconds%n", avgTimeWithArray);
    System.out.println();
    System.out.printf("ArrayStack is %.2f times faster than LinkedStack.%n", avgTimeLinked / avgTimeWithArray);
  }

  /**
   * Runs a single performance test for a given stack implementation.
   *
   * @param implementation     the stack implementation to test.
   * @param seed               the random seed for the operations.
   * @param numberOfOperations the number of random operations to perform.
   * @return the execution time in seconds.
   */
  public static double test(Implementation implementation, int seed, int numberOfOperations) {
    long startTime = System.currentTimeMillis();

    Stack<Integer> stack = switch (implementation) {
      case WithArray -> ArrayStack.empty();
      case Linked    -> LinkedStack.empty();
    };

    RandomStack.fill(stack, seed, numberOfOperations);

    long endTime = System.currentTimeMillis();
    return (endTime - startTime) / 1000.0; // execution time in seconds
  }

  /**
   * Calculates the average execution time over multiple test runs for a given implementation.
   *
   * @param implementation     the stack implementation to test.
   * @param numberOfTests      the number of times to run the test.
   * @param numberOfOperations the number of operations per test.
   * @return the average execution time in seconds.
   */
  static double avgTime(Implementation implementation, int numberOfTests, int numberOfOperations) {
    double totalTime = 0.0;

    for (int i = 0; i < numberOfTests; i++) {
      // Use a different seed for each test run for better statistical average
      totalTime += test(implementation, i, numberOfOperations);
    }

    return totalTime / numberOfTests;
  }
}