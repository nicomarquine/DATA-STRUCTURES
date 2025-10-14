package org.uma.ed.datastructures.list;

import java.io.Serial;

/**
 * Thrown to indicate that a non-valid operation has been attempted on a list.
 * <p>
 * This typically occurs when an index is out of bounds for the list's size
 * (e.g., trying to access an element at a negative index or an index greater than or
 * equal to the size of the list).
 * <p>
 * It extends {@link RuntimeException}, making it an unchecked exception.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class ListException extends RuntimeException {

  /**
   * Serial version UID for serialization.
   */
  @Serial
  private static final long serialVersionUID = -743311599113782047L;

  /**
   * Constructs a new {@code ListException} with no detail message.
   */
  public ListException() {
    super();
  }

  /**
   * Constructs a new {@code ListException} with the specified detail message.
   * <p>
   * A detail message is a String that describes this particular exception.
   *
   * @param msg the detail message.
   */
  public ListException(String msg) {
    super(msg);
  }
}