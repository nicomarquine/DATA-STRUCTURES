package org.uma.ed.datastructures.bag;

import org.uma.ed.datastructures.utils.equals.Equals;
import org.uma.ed.datastructures.utils.hashCode.HashCode;
import org.uma.ed.datastructures.utils.toString.ToString;

/**
 * Provides a skeletal implementation of the {@link Bag} interface to minimize the
 * effort required to implement this interface.
 * <p>
 * This abstract class offers standard, order-independent implementations for the
 * {@code equals()} and {@code hashCode()} methods, reflecting the unordered nature of a bag.
 * The {@code toString()} method also provides a consistent representation, though its
 * output order depends on the concrete implementation's iterator.
 * <p>
 * Concrete bag implementations should extend this class and provide implementations for
 * the {@link #size()} and {@link #iterator()} methods.
 *
 * @param <T> The type of elements held in this bag.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public abstract class AbstractBag<T> implements Iterable<T> {

  /**
   * This abstract method must be implemented by concrete subclasses to provide
   * the total number of elements in the bag (including duplicates).
   *
   * @return the number of elements in the bag.
   */
  public abstract int size();

  /**
   * Compares this bag with the specified object for equality.
   * <p>
   * Returns {@code true} if the specified object is also a bag, the two bags have
   * the same size, and they contain the same elements with the same frequencies
   * (cardinalities). The comparison is order-independent, as is standard for bags.
   *
   * @param obj the object to be compared for equality with this bag.
   * @return {@code true} if the specified object is equal to this bag.
   */
  @Override
  public boolean equals(Object obj) {
    return this == obj ||
        (obj instanceof AbstractBag<?> otherBag &&
            this.size() == otherBag.size() &&
            Equals.orderIndependent(this, otherBag));
  }

  /**
   * Returns the hash code value for this bag.
   * <p>
   * The hash code of a bag is defined as the sum of the hash codes of all its
   * elements. This calculation is order-independent, ensuring that two equal bags
   * will have the same hash code.
   *
   * @return the hash code value for this bag.
   */
  @Override
  public int hashCode() {
    return HashCode.orderIndependent(this);
  }

  /**
   * Returns a string representation of this bag.
   * <p>
   * The string representation consists of the class name, followed by a list of the
   * bag's elements, enclosed in parentheses. The order of the elements depends on
   * the specific implementation's iterator.
   *
   * @return a string representation of this bag.
   */
  @Override
  public String toString() {
    return ToString.toString(this);
  }
}