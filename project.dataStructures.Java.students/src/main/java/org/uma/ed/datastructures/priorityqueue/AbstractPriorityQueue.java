package org.uma.ed.datastructures.priorityqueue;

import org.uma.ed.datastructures.utils.equals.Equals;
import org.uma.ed.datastructures.utils.hashCode.HashCode;
import org.uma.ed.datastructures.utils.toString.ToString;

/**
 * Provides a skeletal implementation of the {@link PriorityQueue} interface to minimize
 * the effort required to implement this interface.
 * <p>
 * This abstract class offers standard implementations for the {@code equals()},
 * {@code hashCode()}, and {@code toString()} methods. These implementations rely on
 * an {@code Iterable} provided by the concrete subclass through the {@link #elements()}
 * method.
 * <p>
 * The {@code equals} and {@code hashCode} methods are order-dependent. They assume that
 * the iterator provided by {@code elements()} yields items in priority order (from highest
 * priority to lowest).
 *
 * @param <T> The type of elements held in this priority queue.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public abstract class AbstractPriorityQueue<T> {

  /**
   * This abstract method must be implemented by concrete subclasses to provide
   * an iterable over the elements in the priority queue.
   * <p>
   * The iterator returned by this iterable is expected to traverse the elements
   * in priority order (from the element with the highest priority to the one with
   * the lowest). This order is essential for {@code equals}, {@code hashCode}, and
   * {@code toString} to function correctly and consistently.
   *
   * @return an {@code Iterable} that yields elements in priority order.
   */
  protected abstract Iterable<T> elements();

  /**
   * This abstract method must be implemented by concrete subclasses to provide
   * the number of elements in the priority queue.
   *
   * @return the number of elements in the priority queue.
   */
  public abstract int size();

  /**
   * Compares this priority queue with the specified object for equality.
   * <p>
   * Returns {@code true} if and only if the specified object is also a priority queue,
   * both have the same size, and their iterators (which must yield elements in
   * priority order) produce the same sequence of elements.
   *
   * @param obj the object to be compared for equality with this priority queue.
   * @return {@code true} if the specified object is equal to this priority queue.
   */
  @Override
  public boolean equals(Object obj) {
    return this == obj ||
        (obj instanceof AbstractPriorityQueue<?> otherQueue &&
            this.size() == otherQueue.size() &&
            Equals.orderDependent(this.elements(), otherQueue.elements()));
  }

  /**
   * Returns the hash code value for this priority queue.
   * <p>
   * The hash code is calculated based on the sequence of elements yielded by the
   * priority-ordered iterator. This ensures that two equal priority queues will
   * have the same hash code.
   *
   * @return the hash code value for this priority queue.
   */
  @Override
  public int hashCode() {
    return HashCode.orderDependent(elements());
  }

  /**
   * Returns a string representation of this priority queue.
   * <p>
   * The string consists of the class name, followed by the elements in priority
   * order, enclosed in parentheses.
   *
   * @return a string representation of this priority queue.
   */
  @Override
  public String toString() {
    return ToString.toString(this, elements());
  }
}