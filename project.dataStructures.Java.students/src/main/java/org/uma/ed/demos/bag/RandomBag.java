package org.uma.ed.demos.bag;

import org.uma.ed.datastructures.bag.Bag;
import java.util.Random;

/**
 * A utility class for performing a sequence of random operations on a {@link Bag}.
 * <p>
 * This is primarily used for testing and performance benchmarking of bag implementations.
 * It simulates a typical workload by mixing {@code insert}, {@code delete}, and
 * {@code occurrences} operations.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public final class RandomBag {

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private RandomBag() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Fills a given bag by performing a specified number of random operations.
   * <p>
   * The sequence of operations is determined by the provided seed, making tests
   * repeatable. The operations are distributed with the following probabilities:
   * <ul>
   *     <li>{@code insert}:      2/4 (50%) probability.</li>
   *     <li>{@code delete}:      1/4 (25%) probability.</li>
   *     <li>{@code occurrences}: 1/4 (25%) probability.</li>
   * </ul>
   * The {@code delete} operation is attempted even if the element is not in the bag.
   * The integers used in the operations are fully random, simulating a general-purpose workload.
   *
   * @param bag                the bag to operate on.
   * @param seed               the seed for the random number generator to ensure reproducibility.
   * @param numberOfOperations the total number of random operations to perform.
   */
  public static void fill(Bag<Integer> bag, int seed, int numberOfOperations) {
    Random randomGenerator = new Random(seed);

    for (int i = 0; i < numberOfOperations; i++) {
      // Generates a random integer in [0, 3]
      int operationType = randomGenerator.nextInt(4);

      switch (operationType) {
        case 0:
        case 1: {
          // Two cases for insert, giving it a 50% probability.
          int valueToInsert = randomGenerator.nextInt();
          bag.insert(valueToInsert);
          break;
        }
        case 2: {
          // One case for delete, giving it a 25% probability.
          int valueToDelete = randomGenerator.nextInt();
          bag.delete(valueToDelete);
          break;
        }
        case 3: {
          // One case for occurrences, giving it a 25% probability.
          int valueToCount = randomGenerator.nextInt();
          bag.occurrences(valueToCount);
          break;
        }
      }
    }
  }
}