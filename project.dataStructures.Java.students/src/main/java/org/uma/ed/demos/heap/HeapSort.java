package org.uma.ed.demos.heap;

import org.uma.ed.datastructures.heap.BinaryHeap;
import org.uma.ed.datastructures.heap.Heap;
import org.uma.ed.datastructures.heap.MaxiphobicHeap;
import org.uma.ed.datastructures.heap.WBLeftistHeap;

import java.util.Arrays;
import java.util.Random;

/**
 * A demonstration of the HeapSort algorithm using various {@link Heap} implementations.
 * <p>
 * HeapSort is a comparison-based sorting algorithm that uses a heap data structure.
 * The algorithm consists of two main phases:
 * <ol>
 *     <li><b>Build Heap:</b> All elements of the input array are inserted into a min-heap.</li>
 *     <li><b>Extract Elements:</b> The smallest element is repeatedly extracted from the
 *         heap's root and placed into the resulting sorted array.</li>
 * </ol>
 * This process results in a sorted array. The efficiency of HeapSort is largely
 * determined by the performance of the underlying heap implementation.
 *
 * @author Pablo López, Data Structures, Grado en Informática. UMA.
 */
public class HeapSort {

  /**
   * Sorts an array of integers using the HeapSort algorithm with a given heap implementation.
   *
   * @param data the array of integers to be sorted.
   * @param heap an empty heap instance that will be used for the sorting process.
   * @return a new array containing the sorted elements.
   */
  public static int[] heapSort(int[] data, Heap<Integer> heap) {
    // Ensure the heap is empty before starting.
    heap.clear();

    // 1. Build Heap Phase: Insert all elements into the heap.
    for (int x : data) {
      heap.insert(x);
    }

    // 2. Extraction Phase: Repeatedly extract the minimum element.
    int[] sortedArray = new int[data.length];
    for (int i = 0; i < data.length; i++) {
      sortedArray[i] = heap.minimum();
      heap.deleteMinimum();
    }
    return sortedArray;
  }

  /**
   * Main method to run correctness and performance tests for HeapSort
   * using various heap implementations.
   */
  public static void main(String[] args) {
    System.out.println("--- Correctness & Performance Tests for HeapSort ---");
    System.out.println();

    runTestsFor(BinaryHeap.empty(), "BinaryHeap");
    runTestsFor(WBLeftistHeap.empty(), "WBLeftistHeap");
    runTestsFor(MaxiphobicHeap.empty(), "MaxiphobicHeap");
  }

  /**
   * Runs a standardized suite of tests for a given heap implementation.
   *
   * @param heap an empty instance of the heap implementation to test.
   * @param implementationName a string name for the implementation, for reporting.
   */
  public static void runTestsFor(Heap<Integer> heap, String implementationName) {
    final int NUMBER_OF_TESTS = 1000;
    final int ARRAY_SIZE = 1000;

    System.out.println("Testing implementation: " + implementationName);
    long startTime = System.currentTimeMillis();

    for (int seed = 0; seed < NUMBER_OF_TESTS; seed++) {
      if (!testHeapSortOnce(seed, ARRAY_SIZE, heap)) {
        System.err.println("ERROR: HeapSort failed for implementation " + implementationName + " with seed " + seed);
        System.exit(1);
      }
    }

    long endTime = System.currentTimeMillis();
    double avgTime = (endTime - startTime) / (double) NUMBER_OF_TESTS;

    System.out.printf(" -> All %d tests passed successfully.%n", NUMBER_OF_TESTS);
    System.out.printf(" -> Average time per sort of %d elements: %.4f ms%n", ARRAY_SIZE, avgTime);
    System.out.println();
  }

  /**
   * Performs a single randomized test of the HeapSort algorithm.
   *
   * @param seed     the random seed.
   * @param size     the size of the array to sort.
   * @param heap     the heap instance to use.
   * @return {@code true} if the array was sorted correctly, {@code false} otherwise.
   */
  public static boolean testHeapSortOnce(int seed, int size, Heap<Integer> heap) {
    Random rnd = new Random(seed);

    int[] originalArray = new int[size];
    for (int i = 0; i < size; i++) {
      originalArray[i] = rnd.nextInt();
    }

    // Sort a copy to compare against
    int[] expectedSortedArray = Arrays.copyOf(originalArray, originalArray.length);
    Arrays.sort(expectedSortedArray);

    // Sort using our HeapSort implementation
    int[] actualSortedArray = heapSort(originalArray, heap);

    if (!heap.isEmpty()) {
      System.err.println("Error: Heap should be empty after sorting.");
      return false;
    }

    return Arrays.equals(expectedSortedArray, actualSortedArray);
  }
}