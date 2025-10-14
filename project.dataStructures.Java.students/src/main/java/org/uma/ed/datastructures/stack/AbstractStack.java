package org.uma.ed.datastructures.stack;

import org.uma.ed.datastructures.utils.equals.Equals;
import org.uma.ed.datastructures.utils.hashCode.HashCode;
import org.uma.ed.datastructures.utils.toString.ToString;

/**
 * Provides a skeletal implementation of the {@link Stack} interface to minimize
 * the effort required to implement this interface.
 * <p>
 * This abstract class offers standard, order-dependent implementations for the
 * {@code equals()}, {@code hashCode()}, and {@code toString()} methods. Concrete
 * stack implementations should extend this class and provide implementations for
 * the {@link #size()} and {@link #elements()} methods.
 *
 * @param <T> The type of elements held in this stack.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public abstract class AbstractStack<T> {

  /**
   * This abstract method must be implemented by concrete subclasses to provide
   * an iterable over the elements in the stack.
   * <p>
   * The iterator returned by this iterable must traverse the elements from the
   * top of the stack to the bottom for {@code equals} and {@code hashCode}
   * to function correctly.
   *
   * @return an {@code Iterable} that yields elements from top to bottom.
   */
  protected abstract Iterable<T> elements();

  /**
   * This abstract method must be implemented by concrete subclasses to provide
   * the number of elements in the stack.
   *
   * @return the number of elements in the stack.
   */
  public abstract int size();

  /**
   * Compares this stack with the specified object for equality.
   * <p>
   * Returns {@code true} if and only if the specified object is also a stack,
   * both stacks have the same size, and all corresponding pairs of elements in
   * the two stacks are equal. The comparison is order-dependent, reflecting the
   * LIFO nature of a stack.
   *
   * @param obj the object to be compared for equality with this stack.
   * @return {@code true} if the specified object is equal to this stack.
   */
  @Override
  public boolean equals(Object obj) {
    return this == obj ||
        (obj instanceof AbstractStack<?> otherStack &&
            this.size() == otherStack.size() &&
            Equals.orderDependent(this.elements(), otherStack.elements()));
  }

  /**
   * Returns the hash code value for this stack.
   * <p>
   * The hash code of a stack is defined to be the result of the following calculation:
   * <pre>{@code
   *   int hashCode = 1;
   *   for (E e : stack)
   *       hashCode = 31 * hashCode + (e==null ? 0 : e.hashCode());
   * }</pre>
   * This ensures that {@code s1.equals(s2)} implies that
   * {@code s1.hashCode()==s2.hashCode()} for any two stacks {@code s1} and {@code s2},
   * as required by the general contract of {@link Object#hashCode}.
   *
   * @return the hash code value for this stack.
   */
  @Override
  public int hashCode() {
    return HashCode.orderDependent(elements());
  }

  /**
   * Returns a string representation of this stack.
   * <p>
   * The string representation consists of the class name, followed by the stack's
   * elements in top-to-bottom order, enclosed in parentheses (e.g., "ClassName(top, ..., bottom)").
   *
   * @return a string representation of this stack.
   */
  @Override
  public String toString() {
    return ToString.toString(this, elements());
  }
}