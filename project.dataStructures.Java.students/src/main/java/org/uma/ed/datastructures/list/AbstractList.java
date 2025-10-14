package org.uma.ed.datastructures.list;

import org.uma.ed.datastructures.utils.equals.Equals;
import org.uma.ed.datastructures.utils.hashCode.HashCode;
import org.uma.ed.datastructures.utils.toString.ToString;

/**
 * Provides a skeletal implementation of the {@link List} interface to minimize
 * the effort required to implement this interface.
 * <p>
 * This abstract class offers standard, order-dependent implementations for the
 * {@code equals()}, {@code hashCode()}, and {@code toString()} methods. These methods
 * rely on the {@code iterator()} and {@code size()} methods, which must be provided
 * by any concrete subclass.
 *
 * @param <T> The type of elements held in this list.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public abstract class AbstractList<T> implements Iterable<T> {

  /**
   * This abstract method must be implemented by concrete subclasses to provide
   * the number of elements in the list.
   *
   * @return the number of elements in the list.
   */
  public abstract int size();

  /**
   * Compares this list with the specified object for equality.
   * <p>
   * Returns {@code true} if and only if the specified object is also a list,
   * both lists have the same size, and all corresponding pairs of elements in
   * the two lists are equal. The comparison is order-dependent. In other words,
   * two lists are defined to be equal if they contain the same elements in the
   * same order.
   *
   * @param obj the object to be compared for equality with this list.
   * @return {@code true} if the specified object is equal to this list.
   */
  @Override
  public boolean equals(Object obj) {
    return this == obj ||
        (obj instanceof AbstractList<?> otherList &&
            this.size() == otherList.size() &&
            Equals.orderDependent(this, otherList));
  }

  /**
   * Returns the hash code value for this list.
   * <p>
   * The hash code of a list is defined to be the result of the following calculation:
   * <pre>{@code
   *   int hashCode = 1;
   *   for (E e : list)
   *       hashCode = 31 * hashCode + (e==null ? 0 : e.hashCode());
   * }</pre>
   * This ensures that {@code list1.equals(list2)} implies that
   * {@code list1.hashCode()==list2.hashCode()} for any two lists {@code list1} and
   * {@code list2}, as required by the general contract of {@link Object#hashCode}.
   *
   * @return the hash code value for this list.
   */
  @Override
  public int hashCode() {
    return HashCode.orderDependent(this);
  }

  /**
   * Returns a string representation of this list.
   * <p>
   * The string representation consists of the class name, followed by a list of the
   * list's elements in the order they are returned by its iterator, enclosed in
   * parentheses (e.g., "ClassName(elem1, elem2, ..., elemN)").
   *
   * @return a string representation of this list.
   */
  @Override
  public String toString() {
    return ToString.toString(this);
  }
}