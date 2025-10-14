package org.uma.ed.demos.queue;

import java.util.Random;
import org.uma.ed.datastructures.queue.Queue;

/**
 * A utility class for performing a sequence of random operations on a {@link Queue}.
 * <p>
 * This is primarily used for testing and performance benchmarking of queue implementations.
 * It simulates a typical workload by mixing enqueue and dequeue operations.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public final class RandomQueue {

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private RandomQueue() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Fills a given queue by performing a specified number of random operations.
   * <p>
   * The sequence of operations is determined by the provided seed, making tests
   * repeatable. The operations are distributed with the following probabilities:
   * <ul>
   *     <li>{@code enqueue}: 2/3 probability (cases 0 and 1)</li>
   *     <li>{@code dequeue}: 1/3 probability (case 2)</li>
   * </ul>
   * The {@code dequeue} operation is only attempted if the queue is not empty. The integer
   * enqueued is always 0, as the value itself is typically irrelevant for
   * performance tests focused on the structure's behavior.
   *
   * @param queue              the queue to operate on.
   * @param seed               the seed for the random number generator to ensure reproducibility.
   * @param numberOfOperations the total number of random operations to perform.
   */
  public static void fill(Queue<Integer> queue, int seed, int numberOfOperations) {
    Random randomGenerator = new Random(seed);

    for (int i = 0; i < numberOfOperations; i++) {
      // Generates a random integer in [0, 2]
      int operationType = randomGenerator.nextInt(3);

      switch (operationType) {
        case 0:
        case 1:
          // Two cases for enqueue, giving it a 2/3 probability.
          queue.enqueue(0);
          break;
        case 2:
          // One case for dequeue, giving it a 1/3 probability.
          if (!queue.isEmpty()) {
            queue.dequeue();
          }
          break;
      }
    }
  }
}