package org.uma.ed.datastructures.queue;

/**
 * Represents a Queue, a First-In-First-Out (FIFO) data structure.
 * <p>
 * This interface defines the standard operations for a queue. Elements are inserted
 * at the rear (end) of the queue and are removed from the front (head). This ensures
 * that the first element to be inserted is the first one to be removed.
 *
 * @param <T> The type of elements held in this queue.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public interface Queue<T> {

  /**
   * Checks if this queue is empty.
   *
   * @return {@code true} if this queue contains no elements, {@code false} otherwise.
   */
  boolean isEmpty();

  /**
   * Returns the number of elements in this queue.
   *
   * @return the total number of elements in the queue.
   */
  int size();

  /**
   * Inserts an element at the rear of this queue.
   *
   * @param element the element to be added to the queue.
   */
  void enqueue(T element);

  /**
   * Retrieves, but does not remove, the element at the front of this queue.
   *
   * @return the element at the front of the queue.
   * @throws EmptyQueueException if the queue is empty.
   */
  T first();

  /**
   * Removes the element at the front of this queue.
   *
   * @throws EmptyQueueException if the queue is empty.
   */
  void dequeue();

  /**
   * Removes all of the elements from this queue, leaving it empty.
   */
  void clear();
}