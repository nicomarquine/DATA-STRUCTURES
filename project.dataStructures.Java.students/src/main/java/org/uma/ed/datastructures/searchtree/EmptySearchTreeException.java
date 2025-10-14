package org.uma.ed.datastructures.searchtree;

import java.io.Serial;

/**
 * Thrown to indicate that an operation requiring a non-empty search tree was
 * attempted on an empty tree.
 * <p>
 * This typically occurs when methods like {@link SearchTree#minimum()},
 * {@link SearchTree#maximum()}, {@link SearchTree#deleteMinimum()}, or
 * {@link SearchTree#deleteMaximum()} are invoked on a search tree that
 * contains no elements.
 * <p>
 * It extends {@link RuntimeException}, making it an unchecked exception.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class EmptySearchTreeException extends RuntimeException {

  /**
   * Serial version UID for serialization.
   */
  @Serial
  private static final long serialVersionUID = 2682430661350355887L;

  /**
   * Constructs a new {@code EmptySearchTreeException} with no detail message.
   */
  public EmptySearchTreeException() {
    super();
  }

  /**
   * Constructs a new {@code EmptySearchTreeException} with the specified detail message.
   * <p>
   * A detail message is a String that describes this particular exception.
   *
   * @param msg the detail message.
   */
  public EmptySearchTreeException(String msg) {
    super(msg);
  }
}