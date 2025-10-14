package org.uma.ed.datastructures.dictionary;

import org.uma.ed.datastructures.utils.equals.Equals;

/**
 * Provides a skeletal implementation of the {@link SortedDictionary} interface.
 * <p>
 * This class extends {@link AbstractDictionary} and overrides the {@code equals()} method
 * to provide an efficient, order-dependent comparison for sorted dictionaries. This
 * comparison is based on the sequence of key-value mappings, which are ordered by key.
 *
 * @param <K> The type of keys maintained by this sorted dictionary.
 * @param <V> The type of mapped values.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public abstract class AbstractSortedDictionary<K, V> extends AbstractDictionary<K, V> {

  /**
   * Compares the specified object with this sorted dictionary for equality.
   * <p>
   * This method overrides the order-independent comparison from {@link AbstractDictionary}.
   * It provides an optimized, order-dependent comparison when the other object is
   * also a {@code SortedDictionary}, taking advantage of the fact that both dictionaries'
   * entries are sorted by key. This check is more efficient (O(n)) than a generic
   * order-independent comparison.
   * <p>
   * If the other object is a general {@code Dictionary} but not a {@code SortedDictionary}, it
   * falls back to the order-independent comparison provided by the superclass to correctly
   * maintain the general dictionary {@code equals} contract.
   *
   * @param obj the object to be compared for equality with this sorted dictionary.
   * @return {@code true} if the specified object is equal to this sorted dictionary.
   */
  @Override
  public boolean equals(Object obj) {
    return this == obj || switch (obj) {
      case AbstractSortedDictionary<?, ?> otherSortedDict ->
          this.size() == otherSortedDict.size() && Equals.orderDependent(this, otherSortedDict);
      default -> super.equals(obj);
    };
  }

  /*
   * Note on hashCode:
   * We do not override hashCode() here. According to the general contract for maps/dictionaries,
   * the hash code must be order-independent (usually the sum of the hash codes of its entries).
   * Since a SortedDictionary can be equal to an unordered HashDictionary if they contain the
   * same key-value mappings, they must both produce the same hash code. Therefore, we rely on
   * the order-independent hashCode() implementation from the AbstractDictionary superclass.
   */
}