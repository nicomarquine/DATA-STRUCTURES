package org.uma.ed.datastructures.bag;

/**
 * Represents a Bag, also known as a Multiset.
 * <p>
 * A bag is a collection that allows multiple instances of the same element. Unlike a {@code Set},
 * which only stores unique elements, a bag keeps track of the number of occurrences (or "count")
 * of each element.
 * <p>
 * This interface places no stipulations on the order of its elements; concrete
 * implementations may or may not maintain a specific order. Equality of elements is
 * determined by the {@code equals} method.
 *
 * @param <T> The type of elements held in this bag.
 *
 * @author Pablo López, Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public interface Bag<T> extends Iterable<T> {

  /**
   * Checks if this bag is empty.
   *
   * @return {@code true} if this bag contains no elements, {@code false} otherwise.
   */
  boolean isEmpty();

  /**
   * Returns the total number of elements in this bag.
   * <p>
   * This count includes all occurrences of all elements. For example, if a bag contains
   * {a, a, b}, its size is 3.
   *
   * @return the total number of elements in the bag.
   */
  int size();

  /**
   * Adds one occurrence of the specified element to this bag.
   * <p>
   * Bags allow duplicate elements, so this operation will always increase the bag's size by one.
   *
   * @param element the element to be added.
   */
  void insert(T element);

  /**
   * Adds all of the elements in the specified varargs array to this bag.
   * <p>
   * The effect is the same as calling {@link #insert(Object)} on this bag once for
   * each element in the array.
   *
   * @param elements the elements to be added to the bag.
   */
  @SuppressWarnings("unchecked")
  default void insert(T... elements) {
    for (T element : elements) {
      insert(element);
    }
  }

  /**
   * Removes a single occurrence of the specified element from this bag.
   * <p>
   * If the element is present multiple times, only one instance is removed. If the
   * element is not in the bag, the bag remains unchanged.
   *
   * @param element the element to be removed.
   */
  void delete(T element);

  /**
   * Removes all elements from this bag, leaving it empty.
   */
  void clear();

  /**
   * Returns the number of occurrences of the specified element in this bag.
   *
   * @param element the element whose occurrences are to be counted.
   * @return the number of times the specified element appears in the bag.
   */
  int occurrences(T element);

  /**
   * Returns {@code true} if this bag contains at least one occurrence of the specified element.
   * <p>
   * This is equivalent to checking if {@code occurrences(element) > 0}.
   *
   * @param element the element whose presence in this bag is to be tested.
   * @return {@code true} if this bag contains the specified element.
   */
  default boolean contains(T element) {
    return occurrences(element) > 0;
  }
}