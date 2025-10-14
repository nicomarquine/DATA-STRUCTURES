package org.uma.ed.demos.list;

import org.uma.ed.datastructures.list.ArrayList;
import org.uma.ed.datastructures.list.LinkedList;
import org.uma.ed.datastructures.list.List;

/**
 * Compares the performance of different {@link List} implementations.
 * <p>
 * This program benchmarks {@link ArrayList} versus {@link LinkedList} by performing a
 * large number of random operations on each. The mix of operations includes insertions
 * and deletions at random positions, which are typically expensive for array-based lists
 * but can be more efficient for linked lists if the position is near an end.
 * <p>
 * The results will vary based on the specific mix of operations. This benchmark, with
 * random-access insertions and deletions, often highlights the O(n) cost of shifting
 * elements in an {@code ArrayList}, potentially making {@code LinkedList} appear more
 * competitive than in benchmarks that only test appends or sequential access.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class ListsPerformance {

  public enum Implementation {
    WithArray,
    Linked
  }

  public static void main(String[] args) {
    final int numberOfTests = 10;
    final int numberOfOperations = 100_000; // A suitable number for this workload

    System.out.println("Running performance benchmark for List implementations...");
    System.out.printf("Number of tests per implementation: %d%n", numberOfTests);
    System.out.printf("Number of operations per test: %,d%n", numberOfOperations);
    System.out.println("Workload: A mix of random insertions, deletions, and gets.");
    System.out.println("-----------------------------------------------------");

    double avgTimeLinked = avgTime(Implementation.Linked, numberOfTests, numberOfOperations);
    double avgTimeWithArray = avgTime(Implementation.WithArray, numberOfTests, numberOfOperations);

    System.out.printf("Average execution time for LinkedList: %.4f seconds%n", avgTimeLinked);
    System.out.printf("Average execution time for ArrayList:  %.4f seconds%n", avgTimeWithArray);
    System.out.println();

    if (avgTimeWithArray < avgTimeLinked) {
      System.out.printf("ArrayList is %.2f times faster than LinkedList.%n", avgTimeLinked / avgTimeWithArray);
    } else {
      System.out.printf("LinkedList is %.2f times faster than ArrayList.%n", avgTimeWithArray / avgTimeLinked);
    }
  }

  /**
   * Runs a single performance test for a given list implementation.
   *
   * @param implementation     the list implementation to test.
   * @param seed               the random seed for the operations.
   * @param numberOfOperations the number of random operations to perform.
   * @return the execution time in seconds.
   */
  public static double test(Implementation implementation, int seed, int numberOfOperations) {
    long startTime = System.currentTimeMillis();

    List<Integer> list = switch (implementation) {
      case WithArray -> ArrayList.empty();
      case Linked    -> LinkedList.empty();
    };

    RandomList.fill(list, seed, numberOfOperations);

    long endTime = System.currentTimeMillis();
    return (endTime - startTime) / 1000.0;
  }

  /**
   * Calculates the average execution time over multiple test runs for a given implementation.
   *
   * @param implementation     the list implementation to test.
   * @param numberOfTests      the number of times to run the test.
   * @param numberOfOperations the number of operations per test.
   * @return the average execution time in seconds.
   */
  static double avgTime(Implementation implementation, int numberOfTests, int numberOfOperations) {
    double totalTime = 0.0;

    for (int i = 0; i < numberOfTests; i++) {
      totalTime += test(implementation, i, numberOfOperations);
    }

    return totalTime / numberOfTests;
  }
}