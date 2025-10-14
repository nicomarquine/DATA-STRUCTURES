package org.uma.ed.datastructures.set;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * A {@link Set} that further provides a total ordering on its elements.
 * <p>
 * The elements are ordered either by their natural ordering (if they implement
 * {@link Comparable}) or by a {@link Comparator} provided at set creation time.
 * The set's iterator will traverse the elements in ascending order.
 * <p>
 * All elements inserted into a sorted set must be mutually comparable using the
 * set's ordering criterion: they must all be comparable with one another.
 *
 * @param <T> The type of elements held in this sorted set.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public interface SortedSet<T> extends Set<T> {

  /**
   * Returns the comparator used to order the elements in this set.
   * <p>
   * If this set uses the natural ordering of its elements, this method returns
   * a comparator that reflects that ordering (e.g., {@code Comparator.naturalOrder()}).
   *
   * @return the comparator used to order the elements in this set.
   */
  Comparator<T> comparator();

  /**
   * Returns the first (lowest) element currently in this set.
   *
   * @return the first (lowest) element in this set.
   * @throws NoSuchElementException if this set is empty.
   */
  T minimum();

  /**
   * Returns the last (highest) element currently in this set.
   *
   * @return the last (highest) element in this set.
   * @throws NoSuchElementException if this set is empty.
   */
  T maximum();
}