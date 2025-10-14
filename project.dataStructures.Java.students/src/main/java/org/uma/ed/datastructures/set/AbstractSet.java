package org.uma.ed.datastructures.set;

import org.uma.ed.datastructures.utils.equals.Equals;
import org.uma.ed.datastructures.utils.hashCode.HashCode;
import org.uma.ed.datastructures.utils.toString.ToString;

/**
 * Provides a skeletal implementation of the {@link Set} interface to minimize
 * the effort required to implement this interface.
 * <p>
 * This abstract class offers standard, order-independent implementations for the
 * {@code equals()} and {@code hashCode()} methods, reflecting the unordered nature
 * of a general set. The {@code toString()} method provides a consistent representation.
 * <p>
 * Concrete set implementations should extend this class and provide implementations for
 * the {@link #size()} and {@link #iterator()} methods.
 *
 * @param <T> The type of elements held in this set.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public abstract class AbstractSet<T> implements Iterable<T> {

  /**
   * This abstract method must be implemented by concrete subclasses to provide
   * the number of elements in the set.
   *
   * @return the number of elements in the set.
   */
  public abstract int size();

  /**
   * Compares this set with the specified object for equality.
   * <p>
   * Returns {@code true} if the specified object is also a set, the two sets have
   * the same size, and every member of this set is contained in the specified set.
   * The comparison is order-independent, as required for sets.
   *
   * @param obj the object to be compared for equality with this set.
   * @return {@code true} if the specified object is equal to this set.
   */
  @Override
  public boolean equals(Object obj) {
    return this == obj ||
        (obj instanceof AbstractSet<?> otherSet &&
            this.size() == otherSet.size() &&
            Equals.orderIndependent(this, otherSet));
  }

  /**
   * Returns the hash code value for this set.
   * <p>
   * The hash code of a set is defined to be the sum of the hash codes of the
   * elements in the set, where the hash code of a {@code null} element is defined to be 0.
   * This ensures that {@code s1.equals(s2)} implies that
   * {@code s1.hashCode()==s2.hashCode()} for any two sets {@code s1} and {@code s2},
   * as required by the general contract of {@link Object#hashCode}.
   *
   * @return the hash code value for this set.
   */
  @Override
  public int hashCode() {
    return HashCode.orderIndependent(this);
  }

  /**
   * Returns a string representation of this set.
   * <p>
   * The string representation consists of the class name, followed by a list of the
   * set's elements, enclosed in parentheses. The order of the elements is determined
   * by the set's iterator.
   *
   * @return a string representation of this set.
   */
  @Override
  public String toString() {
    return ToString.toString(this);
  }
}