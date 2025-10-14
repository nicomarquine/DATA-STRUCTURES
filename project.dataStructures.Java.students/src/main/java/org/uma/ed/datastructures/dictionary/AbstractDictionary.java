package org.uma.ed.datastructures.dictionary;

import org.uma.ed.datastructures.utils.equals.Equals;
import org.uma.ed.datastructures.utils.hashCode.HashCode;
import org.uma.ed.datastructures.utils.toString.ToString;

/**
 * Provides a skeletal implementation of the {@link Dictionary} interface to minimize
 * the effort required to implement this interface.
 * <p>
 * This abstract class offers standard, order-independent implementations for the
 * {@code equals()} and {@code hashCode()} methods. This reflects the nature of a general
 * dictionary (or map), where the order of key-value pairs is not considered significant
 * for equality.
 * <p>
 * Concrete dictionary implementations should extend this class and provide implementations
 * for {@link #size()} and the {@code iterator()} method (inherited from {@code Iterable}).
 *
 * @param <K> The type of keys maintained by this dictionary.
 * @param <V> The type of mapped values.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public abstract class AbstractDictionary<K, V> implements Iterable<Dictionary.Entry<K, V>> {

  /**
   * This abstract method must be implemented by concrete subclasses to provide
   * the number of key-value mappings in this dictionary.
   *
   * @return the number of entries in the dictionary.
   */
  public abstract int size();

  /**
   * Compares this dictionary with the specified object for equality.
   * <p>
   * Returns {@code true} if the given object is also a dictionary and the two
   * dictionaries represent the same mappings. More formally, two dictionaries
   * {@code d1} and {@code d2} are equal if {@code d1.entries()} and {@code d2.entries()}
   * have the same elements, regardless of order.
   *
   * @param obj the object to be compared for equality with this dictionary.
   * @return {@code true} if the specified object is equal to this dictionary.
   */
  @Override
  public boolean equals(Object obj) {
    return this == obj ||
        (obj instanceof AbstractDictionary<?, ?> otherDict &&
            this.size() == otherDict.size() &&
            Equals.orderIndependent(this, otherDict)); // Compares the sets of entries
  }

  /**
   * Returns the hash code value for this dictionary.
   * <p>
   * The hash code of a dictionary is defined as the sum of the hash codes of each of its
   * entries. This ensures that {@code d1.equals(d2)} implies that
   * {@code d1.hashCode()==d2.hashCode()} for any two dictionaries {@code d1} and {@code d2},
   * as required by the general contract of {@link Object#hashCode}.
   *
   * @return the hash code value for this dictionary.
   */
  @Override
  public int hashCode() {
    // The iterator provided by the class is over its entries.
    return HashCode.orderIndependent(this);
  }

  /**
   * Returns a string representation of this dictionary.
   * <p>
   * The string representation consists of the class name, followed by a list of the
   * key-value mappings in the order they are returned by its iterator, enclosed in
   * parentheses.
   *
   * @return a string representation of this dictionary.
   */
  @Override
  public String toString() {
    return ToString.toString(this);
  }
}