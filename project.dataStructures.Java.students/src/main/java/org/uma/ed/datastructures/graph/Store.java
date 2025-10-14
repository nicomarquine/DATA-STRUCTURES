package org.uma.ed.datastructures.graph;

/**
 * An interface defining a simple storage mechanism for elements, used by traversal algorithms.
 * <p>
 * This interface abstracts the core operations of a "fringe" or "frontier" collection
 * in a graph traversal. Concrete implementations will determine the traversal strategy:
 * <ul>
 *     <li>A LIFO (Last-In-First-Out) implementation, like a {@code Stack}, will produce a
 *         Depth-First Traversal.</li>
 *     <li>A FIFO (First-In-First-Out) implementation, like a {@code Queue}, will produce a
 *         Breadth-First Traversal.</li>
 * </ul>
 * This forms the basis of the strategy pattern used within the {@link Traversal} framework.
 *
 * @param <T> The type of elements held in this store.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
interface Store<T> {

  /**
   * Checks if this store is empty.
   *
   * @return {@code true} if the store contains no elements, {@code false} otherwise.
   */
  boolean isEmpty();

  /**
   * Adds an element to the store.
   * <p>
   * The specific behavior of this method (e.g., adding to the top of a stack or the
   * rear of a queue) is defined by the implementing class.
   *
   * @param element the element to be inserted into the store.
   */
  void insert(T element);

  /**
   * Retrieves and removes an element from the store.
   * <p>
   * The specific element returned (e.g., the top of a stack or the front of a queue)
   * is defined by the implementing class and dictates the traversal order.
   *
   * @return the extracted element.
   * @throws java.util.NoSuchElementException if the store is empty.
   */
  T extract();
}