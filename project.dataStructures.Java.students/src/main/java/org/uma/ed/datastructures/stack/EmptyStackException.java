package org.uma.ed.datastructures.stack;

import java.io.Serial;

/**
 * This class represents a custom exception that is thrown when non-valid operations
 * (such as attempting to peek or pop from an empty stack) are attempted on a stack.
 * <p>
 * It extends {@link RuntimeException}, making it an unchecked exception.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class EmptyStackException extends RuntimeException {

  /**
   * Serial version UID for serialization.
   */
  @Serial
  private static final long serialVersionUID = 6302850509118881244L;

  /**
   * Constructs a new {@code EmptyStackException} with no detail message.
   */
  public EmptyStackException() {
    super();
  }

  /**
   * Constructs a new {@code EmptyStackException} with the specified detail message.
   * <p>
   * A detail message is a String that describes this particular exception.
   *
   * @param msg the detail message.
   */
  public EmptyStackException(String msg) {
    super(msg);
  }
}