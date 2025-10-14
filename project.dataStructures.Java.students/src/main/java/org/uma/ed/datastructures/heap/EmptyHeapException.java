package org.uma.ed.datastructures.heap;

import java.io.Serial;

/**
 * Thrown to indicate that a non-valid operation has been attempted on an empty heap.
 * <p>
 * This typically occurs when methods like {@link Heap#minimum()} or {@link Heap#deleteMinimum()}
 * are invoked on a heap that contains no elements.
 * <p>
 * It extends {@link RuntimeException}, making it an unchecked exception.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class EmptyHeapException extends RuntimeException {

  /**
   * Serial version UID for serialization.
   */
  @Serial
  private static final long serialVersionUID = -429627186120663874L;

  /**
   * Constructs a new {@code EmptyHeapException} with no detail message.
   */
  public EmptyHeapException() {
    super();
  }

  /**
   * Constructs a new {@code EmptyHeapException} with the specified detail message.
   * <p>
   * A detail message is a String that describes this particular exception.
   *
   * @param msg the detail message.
   */
  public EmptyHeapException(String msg) {
    super(msg);
  }
}