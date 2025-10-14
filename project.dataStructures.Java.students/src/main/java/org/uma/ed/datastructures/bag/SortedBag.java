package org.uma.ed.datastructures.bag;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * A {@link Bag} that further provides a total ordering on its elements.
 * <p>
 * The elements are ordered either by their natural ordering (if they implement
 * {@link Comparable}) or by a {@link Comparator} provided at bag creation time.
 * The bag's iterator will traverse the elements in ascending order, yielding all
 * occurrences of a smaller element before any occurrences of a larger element.
 * <p>
 * All elements inserted into a sorted bag must be mutually comparable using the
 * bag's ordering criterion.
 *
 * @param <T> The type of elements held in this sorted bag.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public interface SortedBag<T> extends Bag<T> {

  /**
   * Returns the comparator used to order the elements in this bag.
   * <p>
   * If this bag uses the natural ordering of its elements, this method returns
   * a comparator that reflects that ordering.
   *
   * @return the comparator used to order the elements in this bag.
   */
  Comparator<T> comparator();

  /**
   * Returns the first (lowest, according to the comparator) element currently in this bag.
   * <p>
   * If the minimum element has multiple occurrences, this method returns one of them.
   *
   * @return the first (lowest) element in this bag.
   * @throws NoSuchElementException if this bag is empty.
   */
  T minimum();

  /**
   * Returns the last (highest, according to the comparator) element currently in this bag.
   * <p>
   * If the maximum element has multiple occurrences, this method returns one of them.
   *
   * @return the last (highest) element in this bag.
   * @throws NoSuchElementException if this bag is empty.
   */
  T maximum();
}