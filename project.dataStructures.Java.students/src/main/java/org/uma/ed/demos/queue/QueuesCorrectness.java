package org.uma.ed.demos.queue;

import org.uma.ed.datastructures.queue.ArrayQueue;
import org.uma.ed.datastructures.queue.LinkedQueue;
import org.uma.ed.datastructures.queue.Queue;

/**
 * A test class to verify the behavioral equivalence of different {@link Queue} implementations.
 * <p>
 * This program runs a series of tests to confirm that {@link LinkedQueue} and {@link ArrayQueue}
 * produce identical results when subjected to the same sequence of random operations.
 * <p>
 * The test proceeds as follows for each run:
 * <ol>
 *     <li>Two queues, one of each type, are created.</li>
 *     <li>Both queues are populated using the same sequence of random operations (enqueue/dequeue)
 *         generated from the same seed.</li>
 *     <li>The elements are then dequeued from both queues one by one and compared.</li>
 *     <li>If any discrepancy is found, or if one queue becomes empty before the other, an
 *         error is reported and the program terminates.</li>
 * </ol>
 * If all tests complete without error, it provides strong evidence that the implementations are correct
 * with respect to each other.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class QueuesCorrectness {

  public static void main(String[] args) {
    final int numberOfTests = 100;
    final int numberOfOperations = 100_000;

    System.out.println("Starting correctness test for Queue implementations...");
    System.out.printf("Comparing %s against %s.%n", LinkedQueue.class.getSimpleName(), ArrayQueue.class.getSimpleName());
    System.out.printf("Number of test runs: %d%n", numberOfTests);
    System.out.printf("Number of random operations per run: %,d%n", numberOfOperations);
    System.out.println("-----------------------------------------------------");

    for (int seed = 0; seed < numberOfTests; seed++) {
      Queue<Integer> linkedQueue = LinkedQueue.empty();
      Queue<Integer> arrayQueue = ArrayQueue.empty();

      // 1. Fill both queues with the same sequence of random operations.
      RandomQueue.fill(linkedQueue, seed, numberOfOperations);
      RandomQueue.fill(arrayQueue, seed, numberOfOperations);

      // 2. Verify that both queues have the same size.
      if (linkedQueue.size() != arrayQueue.size()) {
        System.err.printf("Test failed with seed %d: Sizes do not match after fill.%n", seed);
        System.err.printf("  - LinkedQueue size: %d%n", linkedQueue.size());
        System.err.printf("  - ArrayQueue size:  %d%n", arrayQueue.size());
        System.exit(1);
      }

      // 3. Dequeue and compare all elements.
      while (!linkedQueue.isEmpty()) {
        int elemFromLinked = linkedQueue.first();
        int elemFromArray = arrayQueue.first();

        if (elemFromLinked != elemFromArray) {
          System.err.printf("Test failed with seed %d: Mismatch found during dequeue.%n", seed);
          System.err.printf("  - Mismatch at size %d: LinkedQueue had %d, ArrayQueue had %d.%n",
              linkedQueue.size(), elemFromLinked, elemFromArray);
          System.exit(1);
        }
        linkedQueue.dequeue();
        arrayQueue.dequeue();
      }

      // 4. At this point, the array-based queue should also be empty.
      if (!arrayQueue.isEmpty()) {
        System.err.printf("Test failed with seed %d: ArrayQueue was not empty when LinkedQueue was.%n", seed);
        System.exit(1);
      }

      // Optional: Print progress for long runs.
      if ((seed + 1) % 10 == 0) {
        System.out.printf("... %d tests passed.%n", seed + 1);
      }
    }

    System.out.println("-----------------------------------------------------");
    System.out.println("All " + numberOfTests + " tests passed successfully!");
  }
}