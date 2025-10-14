package org.uma.ed.demos.queue;

import org.uma.ed.datastructures.queue.ArrayQueue;
import org.uma.ed.datastructures.queue.LinkedQueue;
import org.uma.ed.datastructures.queue.Queue;

/**
 * Compares the performance of different {@link Queue} implementations.
 * <p>
 * This program benchmarks {@code ArrayQueue} versus {@code LinkedQueue} by performing a
 * large number of random enqueue and dequeue operations on each. It calculates the average
 * execution time over several runs to provide a more stable and reliable comparison.
 * <p>
 * The results typically show that {@code ArrayQueue} is significantly faster due to
 * superior memory locality (cache-friendliness) and lower overhead per operation compared
 * to the node-based, pointer-chasing approach of {@code LinkedQueue}.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class QueuesPerformance {

  public enum Implementation {
    Linked,
    WithArray
  }

  public static void main(String[] args) {
    final int numberOfTests = 10;
    final int numberOfOperations = 10_000_000;

    System.out.println("Running performance benchmark for Queue implementations...");
    System.out.printf("Number of tests per implementation: %d%n", numberOfTests);
    System.out.printf("Number of operations per test: %,d%n", numberOfOperations);
    System.out.println("-----------------------------------------------------");

    double avgTimeLinked = avgTime(Implementation.Linked, numberOfTests, numberOfOperations);
    double avgTimeWithArray = avgTime(Implementation.WithArray, numberOfTests, numberOfOperations);

    System.out.printf("Average execution time for LinkedQueue: %.4f seconds%n", avgTimeLinked);
    System.out.printf("Average execution time for ArrayQueue:  %.4f seconds%n", avgTimeWithArray);
    System.out.println();
    System.out.printf("ArrayQueue is %.2f times faster than LinkedQueue.%n", avgTimeLinked / avgTimeWithArray);
  }

  /**
   * Runs a single performance test for a given queue implementation.
   *
   * @param implementation     the queue implementation to test.
   * @param seed               the random seed for the operations.
   * @param numberOfOperations the number of random operations to perform.
   * @return the execution time in seconds.
   */
  public static double test(Implementation implementation, int seed, int numberOfOperations) {
    long startTime = System.currentTimeMillis();

    Queue<Integer> queue = switch (implementation) {
      case WithArray -> ArrayQueue.empty();
      case Linked    -> LinkedQueue.empty();
    };

    RandomQueue.fill(queue, seed, numberOfOperations);

    long endTime = System.currentTimeMillis();
    return (endTime - startTime) / 1000.0; // execution time in seconds
  }

  /**
   * Calculates the average execution time over multiple test runs for a given implementation.
   *
   * @param implementation     the queue implementation to test.
   * @param numberOfTests      the number of times to run the test.
   * @param numberOfOperations the number of operations per test.
   * @return the average execution time in seconds.
   */
  static double avgTime(Implementation implementation, int numberOfTests, int numberOfOperations) {
    double totalTime = 0.0;

    for (int i = 0; i < numberOfTests; i++) {
      // Use a different seed for each test run for a better statistical average.
      totalTime += test(implementation, i, numberOfOperations);
    }

    return totalTime / numberOfTests;
  }
}