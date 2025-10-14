package org.uma.ed.datastructures.dictionary;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * A {@link Dictionary} that further provides a total ordering on its keys.
 * <p>
 * The dictionary is ordered according to the natural ordering of its keys, or by a
 * {@link Comparator} typically provided at sorted dictionary creation time.
 * The iterators for the {@code keys()}, {@code values()}, and {@code entries()}
 * views will traverse the elements in ascending key order.
 * <p>
 * All keys inserted into a sorted dictionary must be mutually comparable using the
 * dictionary's ordering criterion.
 *
 * @param <K> The type of keys maintained by this sorted dictionary.
 * @param <V> The type of mapped values.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public interface SortedDictionary<K, V> extends Dictionary<K, V> {

  /**
   * Returns the comparator used to order the keys in this sorted dictionary.
   * <p>
   * If this dictionary uses the natural ordering of its keys, this method returns
   * a comparator that reflects that ordering (e.g., {@code Comparator.naturalOrder()}).
   *
   * @return the comparator used to order the keys.
   */
  Comparator<K> comparator();

  /**
   * Returns the entry corresponding to the first (lowest) key in this sorted dictionary.
   *
   * @return the entry with the minimum key.
   * @throws NoSuchElementException if the dictionary is empty.
   */
  Entry<K, V> minimum();

  /**
   * Returns the entry corresponding to the last (highest) key in this sorted dictionary.
   *
   * @return the entry with the maximum key.
   * @throws NoSuchElementException if the dictionary is empty.
   */
  Entry<K, V> maximum();
}