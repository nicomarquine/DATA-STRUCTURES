package org.uma.ed.datastructures.priorityqueue;

import java.util.Comparator;

/**
 * Represents a Priority Queue, a data structure where each element has an associated priority.
 * <p>
 * Elements are retrieved based on their priority. Specifically, this interface defines a
 * min-priority queue, where the element with the smallest value according to the queue's
 * {@link Comparator} has the highest priority and is the first to be retrieved.
 * <p>
 * This interface does not place restrictions on whether duplicate elements are permitted.
 * The behavior regarding ties in priority (equal elements) is defined by the specific
 * implementation.
 *
 * @param <T> The type of elements held in this priority queue.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public interface PriorityQueue<T> {

  /**
   * Returns the comparator used to order the elements in this priority queue.
   * <p>
   * The element with the smallest value according to this comparator has the highest priority.
   *
   * @return the comparator used for ordering elements.
   */
  Comparator<T> comparator();

  /**
   * Checks if this priority queue is empty.
   *
   * @return {@code true} if this priority queue contains no elements, {@code false} otherwise.
   */
  boolean isEmpty();

  /**
   * Removes all of the elements from this priority queue, leaving it empty.
   */
  void clear();

  /**
   * Returns the number of elements in this priority queue.
   *
   * @return the total number of elements in the priority queue.
   */
  int size();

  /**
   * Inserts the specified element into this priority queue.
   * <p>
   * The position of the new element is determined by its priority relative to the
   * other elements in the queue.
   *
   * @param element the element to be inserted.
   */
  void enqueue(T element);

  /**
   * Retrieves, but does not remove, the element with the highest priority from this queue.
   * The highest priority element is the smallest element according to the queue's comparator.
   *
   * @return the element at the front of the priority queue.
   * @throws EmptyPriorityQueueException if the priority queue is empty.
   */
  T first();

  /**
   * Removes the element with the highest priority from this queue.
   *
   * @throws EmptyPriorityQueueException if the priority queue is empty.
   */
  void dequeue();
}