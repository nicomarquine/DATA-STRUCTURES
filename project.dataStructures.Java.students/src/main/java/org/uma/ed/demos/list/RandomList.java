/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 * <p>
 * Perform random operations on a list
 */

package org.uma.ed.demos.list;

import java.util.Random;
import org.uma.ed.datastructures.list.List;

/**
 * A utility class for performing a sequence of random operations on a {@link List}.
 * <p>
 * This is primarily used for testing and performance benchmarking of list implementations.
 * It simulates a workload that includes insertions, deletions, and access operations at
 * random positions within the list.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public final class RandomList {

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private RandomList() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Fills a given list by performing a specified number of random operations.
   * <p>
   * The sequence of operations is determined by the provided seed, making tests
   * repeatable. The operations are distributed with the following probabilities:
   * <ul>
   *     <li>{@code insert}: 2/4 (50%) probability. Inserts at a random valid index.</li>
   *     <li>{@code delete}: 1/4 (25%) probability. Deletes from a random valid index.</li>
   *     <li>{@code get}:    1/4 (25%) probability. Accesses an element at a random valid index.</li>
   * </ul>
   * The {@code delete} and {@code get} operations are only attempted if the list is not empty.
   * The integer inserted is always 0, as the value itself is typically irrelevant for
   * performance tests focused on the structure's behavior.
   *
   * @param list               the list to operate on.
   * @param seed               the seed for the random number generator to ensure reproducibility.
   * @param numberOfOperations the total number of random operations to perform.
   */
  public static void fill(List<Integer> list, int seed, int numberOfOperations) {
    Random randomGenerator = new Random(seed);

    for (int i = 0; i < numberOfOperations; i++) {
      // Generates a random integer in [0, 3]
      int operationType = randomGenerator.nextInt(4);

      switch (operationType) {
        case 0:
        case 1: {
          // Two cases for insert, giving it a 50% probability.
          int indexToInsert = randomGenerator.nextInt(list.size() + 1);
          list.insert(indexToInsert, 0);
          break;
        }
        case 2: {
          // One case for delete, giving it a 25% probability.
          if (!list.isEmpty()) {
            int indexToDelete = randomGenerator.nextInt(list.size());
            list.delete(indexToDelete);
          }
          break;
        }
        case 3: {
          // One case for get, giving it a 25% probability.
          if (!list.isEmpty()) {
            int indexToGet = randomGenerator.nextInt(list.size());
            // The returned value is intentionally ignored as we are benchmarking the operation itself.
            list.get(indexToGet);
          }
          break;
        }
      }
    }
  }
}