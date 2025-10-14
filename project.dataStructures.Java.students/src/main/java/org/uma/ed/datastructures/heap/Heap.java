package org.uma.ed.datastructures.heap;

import java.util.Comparator;

/**
 * Represents a Min-Heap data structure, a specialized tree-based structure that
 * satisfies the heap property.
 * <p>
 * In a Min-Heap, for any given node, the element it contains is smaller than or
 * equal to the elements in its children nodes, according to a specified
 * {@link Comparator}. This property ensures that the element with the highest
 * priority (i.e., the smallest value) is always at the root of the tree, allowing for
 * efficient retrieval.
 * <p>
 * This interface provides the core operations for a heap, which is commonly used
 * to implement priority queues.
 *
 * @param <T> The type of elements held in this heap.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public interface Heap<T> {

  /**
   * Returns the comparator used to order the elements in this heap.
   * <p>
   * If this heap uses the natural ordering of its elements, this method returns
   * a comparator that reflects that ordering.
   *
   * @return the comparator used to order the elements in this heap.
   */
  Comparator<T> comparator();

  /**
   * Checks if this heap is empty.
   *
   * @return {@code true} if this heap contains no elements, {@code false} otherwise.
   */
  boolean isEmpty();

  /**
   * Returns the number of elements in this heap.
   *
   * @return the total number of elements in the heap.
   */
  int size();

  /**
   * Removes all of the elements from this heap, leaving it empty.
   */
  void clear();

  /**
   * Inserts the specified element into this heap, maintaining the heap property.
   *
   * @param element the element to be inserted.
   */
  void insert(T element);

  /**
   * Retrieves, but does not remove, the element with the highest priority
   * (i.e., the minimum element) from this heap.
   *
   * @return the minimum element in the heap.
   * @throws EmptyHeapException if the heap is empty.
   */
  T minimum();

  /**
   * Removes the element with the highest priority (i.e., the minimum element)
   * from this heap, maintaining the heap property.
   *
   * @throws EmptyHeapException if the heap is empty.
   */
  void deleteMinimum();
}