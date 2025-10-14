package org.uma.ed.datastructures.set;

import org.uma.ed.datastructures.utils.equals.Equals;

/**
 * Provides a skeletal implementation of the {@link SortedSet} interface to minimize
 * the effort required to implement this interface.
 * <p>
 * This class extends {@link AbstractSet} and overrides the {@code equals()} method
 * to provide an efficient, order-dependent comparison for sorted sets.
 *
 * @param <T> The type of elements held in this sorted set.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public abstract class AbstractSortedSet<T> extends AbstractSet<T> {

  /**
   * Compares the specified object with this sorted set for equality.
   * <p>
   * This method overrides the order-independent comparison from {@link AbstractSet}.
   * It provides an optimized, order-dependent comparison when the other object is
   * also a {@code SortedSet}, taking advantage of the fact that both sets are sorted.
   * This check is more efficient (O(n)) than the generic order-independent check.
   * <p>
   * If the other object is a general {@code Set} but not a {@code SortedSet}, it falls
   * back to the order-independent comparison provided by the superclass to correctly
   * maintain the {@code equals} contract.
   *
   * @param obj the object to be compared for equality with this sorted set.
   * @return {@code true} if the specified object is equal to this sorted set.
   */
  @Override
  public boolean equals(Object obj) {
    return this == obj || switch (obj) {
      case AbstractSortedSet<?> otherSortedSet ->
          this.size() == otherSortedSet.size() && Equals.orderDependent(this, otherSortedSet);
      default -> super.equals(obj);
    };
  }

  /*
   * Note on hashCode:
   * We do not override hashCode() here. According to the contract of java.util.Set,
   * the hash code must be order-independent. Since a SortedSet can be equal to a
   * regular HashSet (which is unordered), they must both produce the same hash code.
   * Therefore, we rely on the order-independent hashCode() implementation from
   * the AbstractSet superclass.
   */
}