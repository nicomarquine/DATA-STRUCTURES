package org.uma.ed.datastructures.either;

/**
 * Represents a value of one of two possible types, often called a disjoint union or sum type.
 * <p>
 * An {@code Either} instance can hold either a value of type {@code A} (contained in a {@code Left})
 * or a value of type {@code B} (contained in a {@code Right}), but not both.
 * <p>
 * By convention, {@code Right} is used to represent a successful computation or a primary result,
 * while {@code Left} is used to represent an error, an exceptional case, or an alternative result.
 * <p>
 * This is a {@code sealed interface}, meaning the compiler knows that {@code Left} and {@code Right}
 * are its only direct permitted implementations.
 *
 * @param <A> The type of the 'Left' value.
 * @param <B> The type of the 'Right' value.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public sealed interface Either<A, B> permits Either.Left, Either.Right {

  /**
   * Represents the 'Left' case of an {@code Either}.
   * <p>
   * Typically used to hold an error value or an alternative result.
   *
   * @param <A> The type of the 'Left' value.
   * @param <B> The type of the 'Right' value (unused in this case).
   * @param value The value contained within this {@code Left}.
   */
  record Left<A, B>(A value) implements Either<A, B> {
    /**
     * Returns a standardized string representation of this {@code Left} instance.
     * The format is {@code "Left(value)"}.
     *
     * @return a formatted string representation.
     */
    @Override
    public String toString() {
      return "Left(" + String.valueOf(value) + ")";
    }
  }

  /**
   * Represents the 'Right' case of an {@code Either}.
   * <p>
   * Typically used to hold a successful result.
   *
   * @param <A> The type of the 'Left' value (unused in this case).
   * @param <B> The type of the 'Right' value.
   * @param value The value contained within this {@code Right}.
   */
  record Right<A, B>(B value) implements Either<A, B> {
    /**
     * Returns a standardized string representation of this {@code Right} instance.
     * The format is {@code "Right(value)"}.
     *
     * @return a formatted string representation.
     */
    @Override
    public String toString() {
      return "Right(" + String.valueOf(value) + ")";
    }
  }

  /**
   * Factory method to create an {@code Either} instance holding a 'Left' value.
   *
   * @param <A>   the type of the 'Left' value.
   * @param <B>   the type of the 'Right' value.
   * @param value the value to be wrapped in a {@code Left}.
   * @return a new {@code Left} instance containing the provided value.
   */
  static <A, B> Either<A, B> left(A value) {
    return new Left<>(value);
  }

  /**
   * Factory method to create an {@code Either} instance holding a 'Right' value.
   *
   * @param <A>   the type of the 'Left' value.
   * @param <B>   the type of the 'Right' value.
   * @param value the value to be wrapped in a {@code Right}.
   * @return a new {@code Right} instance containing the provided value.
   */
  static <A, B> Either<A, B> right(B value) {
    return new Right<>(value);
  }

  /**
   * Checks if this {@code Either} instance is a {@code Left}.
   *
   * @return {@code true} if this instance is a {@code Left}, {@code false} otherwise.
   */
  default boolean isLeft() {
    return this instanceof Left;
  }

  /**
   * Checks if this {@code Either} instance is a {@code Right}.
   *
   * @return {@code true} if this instance is a {@code Right}, {@code false} otherwise.
   */
  default boolean isRight() {
    return this instanceof Right;
  }

  /**
   * Retrieves the 'Left' value from this {@code Either} instance.
   *
   * @return the value contained if this is a {@code Left}.
   * @throws IllegalStateException if this method is called on a {@code Right} instance.
   */
  default A left() {
    return switch (this) {
      case Left(var value) -> value;
      case Right(var ignored) -> throw new IllegalStateException("Cannot call left() on a Right instance.");
    };
  }

  /**
   * Retrieves the 'Right' value from this {@code Either} instance.
   *
   * @return the value contained if this is a {@code Right}.
   * @throws IllegalStateException if this method is called on a {@code Left} instance.
   */
  default B right() {
    return switch (this) {
      case Left(var ignored) -> throw new IllegalStateException("Cannot call right() on a Left instance.");
      case Right(var value) -> value;
    };
  }
}