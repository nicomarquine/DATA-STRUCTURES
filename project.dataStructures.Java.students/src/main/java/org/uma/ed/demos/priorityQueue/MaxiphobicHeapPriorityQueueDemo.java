package org.uma.ed.demos.priorityQueue;

import org.uma.ed.datastructures.priorityqueue.EmptyPriorityQueueException;
import org.uma.ed.datastructures.priorityqueue.MaxiphobicHeapPriorityQueue;
import org.uma.ed.datastructures.priorityqueue.PriorityQueue;

/**
 * A simple demonstration class for the {@link MaxiphobicHeapPriorityQueue} implementation.
 * <p>
 * This class illustrates the basic functionality of a priority queue implemented with a
 * maxiphobic heap, including:
 * <ul>
 *     <li>Creating a priority queue from a collection of elements using the {@code of()} factory method.</li>
 *     <li>Inspecting the element with the highest priority using {@code first()}.</li>
 *     <li>Removing elements in priority order using {@code dequeue()}.</li>
 * </ul>
 * The output demonstrates the min-priority queue behavior, where smaller numbers are
 * dequeued first.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class MaxiphobicHeapPriorityQueueDemo {

  public static void main(String[] args) {

    System.out.println("--- Demo: Basic operations with MaxiphobicHeapPriorityQueue ---");
    // Create a priority queue from a list of integers.
    // The `of` factory method uses an efficient O(n) build algorithm.
    PriorityQueue<Integer> pQueue = MaxiphobicHeapPriorityQueue.of(5, 3, 2, 1, 2, 0, 4, 3, 5, 1, 8);

    System.out.println("Initial priority queue state: " + pQueue);
    System.out.println("Size: " + pQueue.size());
    System.out.println("Is empty? " + pQueue.isEmpty());
    System.out.println();

    try {
      System.out.println("The element with the highest priority (minimum value) is: " + pQueue.first());
      System.out.println();

      System.out.println("Dequeueing the highest priority element (" + pQueue.first() + ")...");
      pQueue.dequeue();
      System.out.println("Priority queue state after one dequeue: " + pQueue);
      System.out.println();

      System.out.println("Dequeueing all remaining elements in priority order:");
      while (!pQueue.isEmpty()) {
        Integer elem = pQueue.first();
        pQueue.dequeue();
        System.out.println(" -> Dequeued: " + elem + ". Remaining queue: " + pQueue);
      }
      System.out.println();
      System.out.println("Final queue state: " + pQueue);
      System.out.println("Is empty? " + pQueue.isEmpty());

    } catch (EmptyPriorityQueueException e) {
      System.err.println("Caught an unexpected exception: " + e.getMessage());
    }
  }
}