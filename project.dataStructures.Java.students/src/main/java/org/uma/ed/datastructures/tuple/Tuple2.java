package org.uma.ed.datastructures.tuple;

import java.util.StringJoiner;

/**
 * Represents an immutable, ordered pair of elements, also known as a 2-tuple.
 * <p>
 * This class is implemented as a Java {@code record}, which provides essential methods
 * like {@code equals()}, {@code hashCode()}, and accessor methods automatically. It is
 * primarily used to group two related values of potentially different types.
 *
 * @param <A> The type of the first component.
 * @param <B> The type of the second component.
 * @param first The first component of the tuple.
 * @param second The second component of the tuple.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public record Tuple2<A, B>(A first, B second) {

  /**
   * Factory method to create a new {@code Tuple2} instance.
   * <p>
   * This provides a concise way to instantiate a tuple, consistent with other
   * factory methods in the library.
   *
   * @param <A>    the type of the first component.
   * @param <B>    the type of the second component.
   * @param first  the value of the first component.
   * @param second the value of the second component.
   * @return a new {@code Tuple2} instance containing the provided values.
   */
  public static <A, B> Tuple2<A, B> of(A first, B second) {
    return new Tuple2<>(first, second);
  }

  /**
   * Swaps the components of this tuple.
   *
   * @return a new {@code Tuple2} instance with the components in reversed order.
   */
  public Tuple2<B, A> swap() {
    return Tuple2.of(second, first);
  }

  /**
   * Returns a standardized string representation of this tuple.
   * <p>
   * The format is {@code "Tuple2(firstValue, secondValue)"}. This overrides the default
   * record {@code toString()} method to provide a cleaner format consistent with the
   * rest of the library and to handle {@code null} values safely.
   *
   * @return a formatted string representation of this tuple.
   */
  @Override
  public String toString() {
    String className = getClass().getSimpleName();
    StringJoiner stringJoiner = new StringJoiner(", ", className + "(", ")");
    stringJoiner.add(String.valueOf(first));  // Safely handles nulls
    stringJoiner.add(String.valueOf(second)); // Safely handles nulls
    return stringJoiner.toString();
  }
}