package org.uma.ed.demos.heap;

import org.uma.ed.datastructures.heap.BinaryHeap;
import org.uma.ed.datastructures.heap.Heap;
import org.uma.ed.datastructures.heap.MaxiphobicHeap;
import org.uma.ed.datastructures.heap.WBLeftistHeap;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;

import java.util.Arrays;
import java.util.Random;

/**
 * A demonstration class for various {@link Heap} implementations.
 * <p>
 * This class serves two main purposes:
 * <ol>
 *     <li>It demonstrates that different heap implementations (leftist, skew, maxiphobic, etc.)
 *         correctly extract elements in ascending order (min-heap behavior).</li>
 *     <li>It includes a randomized property test to rigorously verify the correctness
 *         of the {@code BinaryHeap} implementation over many trials.</li>
 * </ol>
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class HeapDemo {

  /**
   * Drains a given heap by repeatedly extracting its minimum element and printing it.
   * The output should be a sequence of elements in non-decreasing order.
   *
   * @param heap The heap to process. Note that this operation is destructive.
   */
  private static void printHeapInOrder(Heap<Integer> heap) {
    System.out.println("Heap structure: " + heap);
    System.out.print("Elements extracted in order: ");
    while (!heap.isEmpty()) {
      System.out.print(heap.minimum() + " ");
      heap.deleteMinimum();
    }
    System.out.println("\n");
  }

  public static void main(String[] args) {
    List<Integer> initialData = JDKArrayList.of(10, 2, 13, 0, 5, 7, 10, 3, 1, 6);
    System.out.println("Initial data for heaps: " + initialData);
    System.out.println("==================================================");

    System.out.println("--- Testing WBLeftistHeap ---");
    printHeapInOrder(WBLeftistHeap.from(initialData));

    System.out.println("--- Testing MaxiphobicHeap ---");
    printHeapInOrder(MaxiphobicHeap.from(initialData));

    System.out.println("--- Testing BinaryHeap ---");
    printHeapInOrder(BinaryHeap.from(initialData));

    System.out.println("==================================================");

    runRandomizedCorrectnessTest();
  }

  /**
   * Performs a series of randomized tests on the BinaryHeap to verify its correctness.
   * <p>
   * For each test, it shuffles an array of integers, builds a heap from it, and then
   * verifies that extracting all elements from the heap yields a sorted sequence.
   */
  public static void runRandomizedCorrectnessTest() {
    System.out.println("--- Running randomized correctness test for BinaryHeap ---");
    Integer[] array = {10, 2, 13, 0, 5, 7, 10, 3, 1, 6};
    Random rnd = new Random();
    int numberOfTests = 10000;

    for (int test = 0; test < numberOfTests; test++) {
      // Shuffle the array to create a random insertion order
      for (int i = 0; i < array.length; i++) {
        int j = i + rnd.nextInt(array.length - i);
        int temp = array[j];
        array[j] = array[i];
        array[i] = temp;
      }

      Heap<Integer> heap = BinaryHeap.from(Arrays.asList(array));

      // Check that elements are extracted in non-decreasing order
      Integer previous = null;
      while (!heap.isEmpty()) {
        Integer next = heap.minimum();
        if (previous != null && next < previous) {
          System.err.println("ERROR: Heap property violated!");
          System.err.println("  - Previous element: " + previous);
          System.err.println("  - Current element:  " + next);
          System.exit(1);
        }
        previous = next;
        heap.deleteMinimum();
      }
    }
    System.out.println("All " + numberOfTests + " randomized tests passed successfully!");
  }
}