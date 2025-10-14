package org.uma.ed.datastructures.utils.equals;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Provides utility methods to compare two {@link Iterable} collections for equality.
 * <p>
 * This class offers two modes of comparison:
 * 1. Order-dependent: Two iterables are equal if they contain the same elements in the exact same sequence.
 * 2. Order-independent: Two iterables are equal if they contain the same elements with the same frequencies,
 *    regardless of their order (multiset or bag equality).
 * <p>
 * All comparisons are safely performed using {@link java.util.Objects#equals(Object, Object)}, making them
 * robust against {@code null} elements.
 * <p>
 * This is a utility class and is not meant to be instantiated.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public final class Equals {

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private Equals() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Compares two iterables for order-dependent equality.
   * <p>
   * Two iterables are considered equal if they contain the same number of elements, and
   * each element in the first iterable is equal to the corresponding element in the
   * second iterable at the same position. The comparison is performed using
   * {@code Objects.equals()}.
   *
   * @param <T>           the type of elements in the first iterable.
   * @param firstIterable the first iterable to compare.
   * @param secondIterable the second iterable to compare.
   * @return {@code true} if both iterables have the same elements in the same sequence,
   *         {@code false} otherwise.
   */
  public static <T> boolean orderDependent(Iterable<T> firstIterable, Iterable<?> secondIterable) {
    Iterator<T> firstIterator = firstIterable.iterator();
    Iterator<?> secondIterator = secondIterable.iterator();

    while (firstIterator.hasNext() && secondIterator.hasNext()) {
      T firstElement = firstIterator.next();
      Object secondElement = secondIterator.next();
      if (!Objects.equals(firstElement, secondElement)) {
        return false; // Found a mismatch
      }
    }

    // Both iterators must be exhausted at the same time for the iterables to be equal.
    return !firstIterator.hasNext() && !secondIterator.hasNext();
  }

  /**
   * Builds a frequency map from an iterable.
   * Each key in the map is an element from the iterable, and its value is the
   * number of times that element appears.
   *
   * @param <T>      the type of elements in the iterable.
   * @param iterable the iterable to process.
   * @return a {@code Map} representing the frequency of each element.
   */
  private static <T> Map<T, Integer> occurrences(Iterable<T> iterable) {
    Map<T, Integer> frequencyMap = new HashMap<>();
    for (var element : iterable) {
      frequencyMap.put(element, frequencyMap.getOrDefault(element, 0) + 1);
    }
    return frequencyMap;
  }

  /**
   * Compares two iterables for order-independent equality (multiset/bag equality).
   * <p>
   * Two iterables are considered equal if they contain the same elements with the same
   * frequencies, regardless of their order. This is achieved by creating a frequency
   * map for each iterable and then comparing the maps for equality.
   *
   * @param <T>           the type of elements in the first iterable.
   * @param firstIterable the first iterable to compare.
   * @param secondIterable the second iterable to compare.
   * @return {@code true} if both iterables contain the same elements with the same
   *         frequencies, {@code false} otherwise.
   */
  public static <T> boolean orderIndependent(Iterable<T> firstIterable, Iterable<?> secondIterable) {
    // Two iterables are equal in an order-independent fashion if their
    // frequency maps are equal.
    return occurrences(firstIterable).equals(occurrences(secondIterable));
  }
}