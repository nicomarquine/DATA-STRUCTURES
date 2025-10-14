package org.uma.ed.demos.queue;

import org.uma.ed.datastructures.queue.ArrayQueue;
import org.uma.ed.datastructures.queue.Queue;

import java.util.Iterator;

/**
 * Generates the sequence of Hamming numbers using an elegant queue-based algorithm.
 * <p>
 * Hamming numbers are positive integers of the form 2^i * 3^j * 5^k, for non-negative
 * integers i, j, and k. The sequence begins: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, ...
 * <p>
 * This implementation uses three queues to efficiently generate the numbers in ascending order
 * without duplicates. Each queue stores multiples of the previous Hamming numbers by 2, 3, and 5
 * respectively. The class implements {@link Iterable}, producing an infinite sequence.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class HammingNumbers implements Iterable<Integer> {

  @Override
  public Iterator<Integer> iterator() {
    return new HammingIterator();
  }

  /**
   * An iterator that generates the infinite sequence of Hamming numbers.
   * <p>
   * It maintains three internal queues, one for multiples of 2, one for multiples of 3,
   * and one for multiples of 5, to produce the next number in the sequence.
   */
  private static final class HammingIterator implements Iterator<Integer> {
    private final Queue<Integer> multiplesOf2;
    private final Queue<Integer> multiplesOf3;
    private final Queue<Integer> multiplesOf5;

    /**
     * Constructs the iterator and initializes the queues with the first Hamming number, 1.
     */
    public HammingIterator() {
      // Initialize queues. The first element to consider is 1 for all multiples.
      this.multiplesOf2 = ArrayQueue.of(1);
      this.multiplesOf3 = ArrayQueue.of(1);
      this.multiplesOf5 = ArrayQueue.of(1);
    }

    /**
     * Returns {@code true} as the sequence of Hamming numbers is infinite.
     */
    @Override
    public boolean hasNext() {
      return true;
    }

    /**
     * Computes and returns the next Hamming number in the sequence.
     *
     * @return the next smallest Hamming number.
     */
    @Override
    public Integer next() {
      // 1. Find the smallest candidate at the front of the queues.
      int head2 = multiplesOf2.first();
      int head3 = multiplesOf3.first();
      int head5 = multiplesOf5.first();
      int nextHammingNumber = Math.min(head2, Math.min(head3, head5));

      // 2. Remove the chosen number from any queue where it appears at the head.
      //    This handles duplicates, e.g., 6 is both 2*3 and 3*2.
      if (nextHammingNumber == head2) {
        multiplesOf2.dequeue();
      }
      if (nextHammingNumber == head3) {
        multiplesOf3.dequeue();
      }
      if (nextHammingNumber == head5) {
        multiplesOf5.dequeue();
      }

      // 3. Generate and enqueue the next set of candidates.
      multiplesOf2.enqueue(nextHammingNumber * 2);
      multiplesOf3.enqueue(nextHammingNumber * 3);
      multiplesOf5.enqueue(nextHammingNumber * 5);

      return nextHammingNumber;
    }
  }

  /**
   * Main method to demonstrate the generation of Hamming numbers.
   */
  public static void main(String[] args) {
    final int HAMMING_NUMBERS_TO_PRINT = 30;

    System.out.println("Generating the first " + HAMMING_NUMBERS_TO_PRINT + " Hamming numbers:");

    HammingNumbers hammingSequence = new HammingNumbers();
    Iterator<Integer> iterator = hammingSequence.iterator();

    // We manually limit the output since the sequence is infinite.
    for (int i = 0; i < HAMMING_NUMBERS_TO_PRINT; i++) {
      System.out.print(iterator.next());
      if (i < HAMMING_NUMBERS_TO_PRINT - 1) {
        System.out.print(", ");
      }
    }
    System.out.println();
  }
}