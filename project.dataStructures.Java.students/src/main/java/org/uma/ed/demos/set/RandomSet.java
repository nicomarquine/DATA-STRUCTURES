package org.uma.ed.demos.set;

import java.util.Random;
import org.uma.ed.datastructures.set.Set;

/**
 * A utility class for performing a sequence of random operations on a {@link Set}.
 * <p>
 * This is primarily used for testing and performance benchmarking of set implementations.
 * It provides two different workload simulations: one with fully random integer insertions,
 * and another that inserts integers in ascending order, which can highlight performance
 * differences in tree-based versus hash-based implementations.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public final class RandomSet {

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private RandomSet() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Fills a given set by performing a specified number of random operations on fully
   * random integer values.
   * <p>
   * The sequence of operations is determined by the provided seed, making tests
   * repeatable. The operations are distributed with the following probabilities:
   * <ul>
   *     <li>{@code insert}:  2/4 (50%) probability.</li>
   *     <li>{@code delete}:  1/4 (25%) probability.</li>
   *     <li>{@code contains}: 1/4 (25%) probability.</li>
   * </ul>
   * This workload is suitable for testing the average-case performance of hash-based
   * sets and the balanced behavior of tree-based sets.
   *
   * @param set                the set to operate on.
   * @param seed               the seed for the random number generator.
   * @param numberOfOperations the total number of random operations to perform.
   */
  public static void fill(Set<Integer> set, int seed, int numberOfOperations) {
    Random randomGenerator = new Random(seed);
    for (int i = 0; i < numberOfOperations; i++) {
      int operationType = randomGenerator.nextInt(4);
      switch (operationType) {
        case 0:
        case 1: {
          int valueToInsert = randomGenerator.nextInt();
          set.insert(valueToInsert);
          break;
        }
        case 2: {
          int valueToDelete = randomGenerator.nextInt();
          set.delete(valueToDelete);
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

  /**
   * Fills a given set by performing random operations with integers that are inserted
   * in ascending order.
   * <p>
   * While the sequence of operations (insert, delete, contains) is random, the values
   * used for insertion always increase. This specific workload can reveal worst-case
   * performance scenarios, particularly for unbalanced Binary Search Trees, which may
   * degenerate into a linear structure.
   * <p>
   * The operation probabilities are the same as in the {@link #fill(Set, int, int)} method.
   *
   * @param set                the set to operate on.
   * @param seed               the seed for the random number generator.
   * @param numberOfOperations the total number of random operations to perform.
   */
  public static void fillAscending(Set<Integer> set, int seed, int numberOfOperations) {
    int lastInsertedValue = 0;
    Random randomGenerator = new Random(seed);
    for (int i = 0; i < numberOfOperations; i++) {
      int operationType = randomGenerator.nextInt(4);
      switch (operationType) {
        case 0:
        case 1: {
          // Insert a new value that is slightly larger than the previous one.
          lastInsertedValue += randomGenerator.nextInt(5) + 1; // Ensure it's always increasing
          set.insert(lastInsertedValue);
          break;
        }
        case 2: {
          if (lastInsertedValue > 0) {
            int valueToDelete = randomGenerator.nextInt(lastInsertedValue);
            set.delete(valueToDelete);
          }
          break;
        }
        case 3: {
          if (lastInsertedValue > 0) {
            int valueToFind = randomGenerator.nextInt(lastInsertedValue);
            set.contains(valueToFind);
          }
          break;
        }
      }
    }
  }
}