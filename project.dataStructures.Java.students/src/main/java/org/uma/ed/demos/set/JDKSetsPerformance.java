package org.uma.ed.demos.set;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Compares the performance of the standard JDK {@link TreeSet} and {@link HashSet} implementations.
 * <p>
 * This program benchmarks Java's primary {@code Set} implementations by subjecting them to a
 * workload of random operations (add, remove, contains). The results typically highlight the
 * performance trade-offs between a hash-based and a tree-based structure.
 * <p>
 * Expected outcome:
 * <ul>
 *     <li>{@code HashSet} (based on a hash table) is expected to be significantly faster,
 *         offering average-case O(1) time complexity for basic operations.</li>
 *     <li>{@code TreeSet} (based on a red-black tree) provides O(log n) complexity, which is
 *         slower but offers the benefit of maintaining elements in a sorted order.</li>
 * </ul>
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class JDKSetsPerformance {

  public enum Implementation {
    Tree,
    Hash
  }

  public static void main(String[] args) {
    final int numberOfTests = 10;
    final int numberOfOperations = 50_000;

    System.out.println("Running performance benchmark for JDK Set implementations...");
    System.out.printf("Number of tests per implementation: %d%n", numberOfTests);
    System.out.printf("Number of operations per test: %,d%n", numberOfOperations);
    System.out.println("------------------------------------------------------------------");

    double avgTimeTreeSet = avgTime(Implementation.Tree, numberOfTests, numberOfOperations);
    double avgTimeHashSet = avgTime(Implementation.Hash, numberOfTests, numberOfOperations);

    System.out.printf("Average execution time for java.util.TreeSet: %.4f seconds%n", avgTimeTreeSet);
    System.out.printf("Average execution time for java.util.HashSet: %.4f seconds%n", avgTimeHashSet);
    System.out.println();
    System.out.printf("HashSet is %.2f times faster than TreeSet for this workload.%n", avgTimeTreeSet / avgTimeHashSet);
  }

  /**
   * Runs a single performance test for a given JDK Set implementation.
   *
   * @param implementation     the set implementation to test.
   * @param seed               the random seed for the operations.
   * @param numberOfOperations the number of random operations to perform.
   * @return the execution time in seconds.
   */
  public static double test(Implementation implementation, int seed, int numberOfOperations) {
    long startTime = System.currentTimeMillis();

    Set<Integer> set = switch (implementation) {
      case Tree -> new TreeSet<>();
      case Hash -> new HashSet<>();
    };

    fillWithRandomOps(set, seed, numberOfOperations);

    long endTime = System.currentTimeMillis();
    return (endTime - startTime) / 1000.0;
  }

  /**
   * Calculates the average execution time over multiple test runs for a given implementation.
   *
   * @param implementation     the set implementation to test.
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

  /**
   * Fills a given {@code java.util.Set} by performing a specified number of random operations.
   * <p>
   * The operation distribution is 50% add, 25% remove, 25% contains.
   *
   * @param set                the set to operate on.
   * @param seed               the seed for the random number generator.
   * @param numberOfOperations the total number of random operations to perform.
   */
  public static void fillWithRandomOps(Set<Integer> set, int seed, int numberOfOperations) {
    Random randomGenerator = new Random(seed);
    for (int i = 0; i < numberOfOperations; i++) {
      int operationType = randomGenerator.nextInt(4);
      switch (operationType) {
        case 0:
        case 1: {
          int valueToAdd = randomGenerator.nextInt();
          set.add(valueToAdd);
          break;
        }
        case 2: {
          int valueToRemove = randomGenerator.nextInt();
          set.remove(valueToRemove);
          break;
        }
        case 3: {
          int valueToFind = randomGenerator.nextInt();
          set.contains(valueToFind);
          break;
        }
      }
    }
  }
}