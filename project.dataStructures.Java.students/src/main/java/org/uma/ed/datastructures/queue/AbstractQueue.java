package org.uma.ed.datastructures.queue;

import org.uma.ed.datastructures.utils.equals.Equals;
import org.uma.ed.datastructures.utils.hashCode.HashCode;
import org.uma.ed.datastructures.utils.toString.ToString;

/**
 * Provides a skeletal implementation of the {@link Queue} interface to minimize
 * the effort required to implement this interface.
 * <p>
 * This abstract class offers standard, order-dependent implementations for the
 * {@code equals()}, {@code hashCode()}, and {@code toString()} methods. Concrete
 * queue implementations should extend this class and provide implementations for
 * the {@link #size()} and {@link #elements()} methods.
 *
 * @param <T> The type of elements held in this queue.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public abstract class AbstractQueue<T> {

  /**
   * This abstract method must be implemented by concrete subclasses to provide
   * an iterable over the elements in the queue.
   * <p>
   * The iterator returned by this iterable must traverse the elements from the
   * front of the queue to the rear for {@code equals}, {@code hashCode}, and
   * {@code toString} to function correctly.
   *
   * @return an {@code Iterable} that yields elements from front to rear.
   */
  protected abstract Iterable<T> elements();

  /**
   * This abstract method must be implemented by concrete subclasses to provide
   * the number of elements in the queue.
   *
   * @return the number of elements in the queue.
   */
  public abstract int size();

  /**
   * Compares this queue with the specified object for equality.
   * <p>
   * Returns {@code true} if and only if the specified object is also a queue,
   * both queues have the same size, and all corresponding pairs of elements in
   * the two queues are equal. The comparison is order-dependent, reflecting the
   * FIFO nature of a queue.
   *
   * @param obj the object to be compared for equality with this queue.
   * @return {@code true} if the specified object is equal to this queue.
   */
  @Override
  public boolean equals(Object obj) {
    return this == obj ||
        (obj instanceof AbstractQueue<?> otherQueue &&
            this.size() == otherQueue.size() &&
            Equals.orderDependent(this.elements(), otherQueue.elements()));
  }

  /**
   * Returns the hash code value for this queue.
   * <p>
   * The hash code of a queue is defined consistently with other sequential collections.
   * This ensures that {@code q1.equals(q2)} implies that
   * {@code q1.hashCode()==q2.hashCode()} for any two queues {@code q1} and {@code q2},
   * as required by the general contract of {@link Object#hashCode}.
   *
   * @return the hash code value for this queue.
   */
  @Override
  public int hashCode() {
    return HashCode.orderDependent(elements());
  }

  /**
   * Returns a string representation of this queue.
   * <p>
   * The string representation consists of the class name, followed by the queue's
   * elements in front-to-rear order, enclosed in parentheses (e.g., "ClassName(front, ..., rear)").
   *
   * @return a string representation of this queue.
   */
  @Override
  public String toString() {
    return ToString.toString(this, elements());
  }
}