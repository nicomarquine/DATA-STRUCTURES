package org.uma.ed.datastructures.bag;

import org.uma.ed.datastructures.utils.equals.Equals;

/**
 * Provides a skeletal implementation of the {@link SortedBag} interface to minimize
 * the effort required to implement this interface.
 * <p>
 * This class extends {@link AbstractBag} and overrides the {@code equals()} method
 * to provide an efficient, order-dependent comparison when comparing two sorted bags.
 *
 * @param <T> The type of elements held in this sorted bag.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public abstract class AbstractSortedBag<T> extends AbstractBag<T> {

  /**
   * Compares the specified object with this sorted bag for equality.
   * <p>
   * This method overrides the order-independent comparison from {@link AbstractBag}.
   * It provides an optimized, order-dependent comparison when the other object is
   * also a {@code SortedBag}. This is more efficient (O(n)) because it can rely
   * on both iterators producing elements in the same sorted sequence.
   * <p>
   * If the other object is a general {@code Bag} but not a {@code SortedBag}, it falls
   * back to the order-independent comparison provided by the superclass to correctly
   * maintain the {@code equals} contract (since a sorted bag can be equal to an
   * unordered bag if they contain the same elements with the same frequencies).
   *
   * @param obj the object to be compared for equality with this sorted bag.
   * @return {@code true} if the specified object is equal to this sorted bag.
   */
  @Override
  public boolean equals(Object obj) {
    return this == obj || switch (obj) {
      case AbstractSortedBag<?> otherSortedBag ->
          this.size() == otherSortedBag.size() && Equals.orderDependent(this, otherSortedBag);
      default -> super.equals(obj);
    };
  }

  /*
   * Note on hashCode:
   * We do not override hashCode() here. According to general collection contracts,
   * if two objects are equal, they must have the same hash code. Since a SortedBag
   * can be equal to an unordered Bag, they must both produce the same order-independent
   * hash code. Therefore, we rely on the hashCode() implementation from the
   * AbstractBag superclass.
   */
}