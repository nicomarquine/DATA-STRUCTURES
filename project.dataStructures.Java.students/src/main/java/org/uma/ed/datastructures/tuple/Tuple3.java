package org.uma.ed.datastructures.tuple;

import java.util.StringJoiner;

/**
 * Represents an immutable, ordered triple of elements, also known as a 3-tuple.
 * <p>
 * This class is implemented as a Java {@code record}, which provides essential methods
 * like {@code equals()}, {@code hashCode()}, and accessor methods automatically. It is
 * useful for grouping three related values of potentially different types.
 *
 * @param <A> The type of the first component.
 * @param <B> The type of the second component.
 * @param <C> The type of the third component.
 * @param first The first component of the tuple.
 * @param second The second component of the tuple.
 * @param third The third component of the tuple.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public record Tuple3<A, B, C>(A first, B second, C third) {

  /**
   * Factory method to create a new {@code Tuple3} instance.
   * <p>
   * This provides a concise way to instantiate a tuple, consistent with other
   * factory methods in the library.
   *
   * @param <A>    the type of the first component.
   * @param <B>    the type of the second component.
   * @param <C>    the type of the third component.
   * @param first  the value of the first component.
   * @param second the value of the second component.
   * @param third  the value of the third component.
   * @return a new {@code Tuple3} instance containing the provided values.
   */
  public static <A, B, C> Tuple3<A, B, C> of(A first, B second, C third) {
    return new Tuple3<>(first, second, third);
  }

  /**
   * Returns a standardized string representation of this tuple.
   * <p>
   * The format is {@code "Tuple3(firstValue, secondValue, thirdValue)"}. This overrides the
   * default record {@code toString()} method to provide a cleaner format consistent
   * with the rest of the library and to handle {@code null} values safely.
   *
   * @return a formatted string representation of this tuple.
   */
  @Override
  public String toString() {
    String className = getClass().getSimpleName();
    StringJoiner stringJoiner = new StringJoiner(", ", className + "(", ")");
    stringJoiner.add(String.valueOf(first));  // Safely handles nulls
    stringJoiner.add(String.valueOf(second)); // Safely handles nulls
    stringJoiner.add(String.valueOf(third));  // Safely handles nulls
    return stringJoiner.toString();
  }
}