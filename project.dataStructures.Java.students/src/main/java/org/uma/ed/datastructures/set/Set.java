package org.uma.ed.datastructures.set;

/**
 * Represents a Set, a collection that contains no duplicate elements.
 * <p>
 * More formally, sets contain no pair of elements {@code e1} and {@code e2} such that
 * {@code e1.equals(e2)}. As implied by its name, this interface models the mathematical
 * set abstraction.
 * <p>
 * This interface places no stipulations on the order of its elements; concrete
 * implementations may or may not maintain a specific order.
 *
 * @param <T> The type of elements held in this set.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public interface Set<T> extends Iterable<T> {

  /**
   * Checks if this set is empty.
   *
   * @return {@code true} if this set contains no elements, {@code false} otherwise.
   */
  boolean isEmpty();

  /**
   * Returns the number of elements in this set (its cardinality).
   *
   * @return the number of elements in the set.
   */
  int size();

  /**
   * Adds the specified element to this set if it is not already present.
   * <p>
   * If the set already contains the element, the call replaces the existing element
   * with the specified element (e.g., to update an element that is "equal" but has
   * different data).
   *
   * @param element the element to be added to the set.
   */
  void insert(T element);

  /**
   * Adds all of the elements in the specified varargs array to this set.
   * <p>
   * The effect is the same as calling {@link #insert(Object)} on this set once for
   * each element in the array.
   *
   * @param elements the elements to be added to the set.
   */
  @SuppressWarnings("unchecked")
  default void insert(T... elements) {
    for (T element : elements) {
      insert(element);
    }
  }

  /**
   * Returns {@code true} if this set contains the specified element.
   * <p>
   * More formally, returns {@code true} if and only if this set contains an
   * element {@code e} such that {@code Objects.equals(element, e)}.
   *
   * @param element the element whose presence in this set is to be tested.
   * @return {@code true} if this set contains the specified element.
   */
  boolean contains(T element);

  /**
   * Removes the specified element from this set if it is present.
   * <p>
   * If the set does not contain the element, it is unchanged.
   *
   * @param element the element to be removed from the set.
   */
  void delete(T element);

  /**
   * Removes all of the elements from this set, leaving it empty.
   */
  void clear();
}