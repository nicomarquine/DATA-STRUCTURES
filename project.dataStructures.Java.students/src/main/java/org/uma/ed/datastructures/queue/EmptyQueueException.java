package org.uma.ed.datastructures.queue;

import java.io.Serial;

/**
 * This class represents a custom exception that is thrown when non-valid operations
 * (such as attempting to access or remove an element from an empty queue) are attempted.
 * <p>
 * It extends {@link RuntimeException}, making it an unchecked exception. This is typically
 * thrown by methods like {@code first()} and {@code dequeue()} when invoked on an empty queue.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class EmptyQueueException extends RuntimeException {

  /**
   * Serial version UID for serialization.
   */
  @Serial
  private static final long serialVersionUID = -3362713207652643584L;

  /**
   * Constructs a new {@code EmptyQueueException} with no detail message.
   */
  public EmptyQueueException() {
    super();
  }

  /**
   * Constructs a new {@code EmptyQueueException} with the specified detail message.
   * <p>
   * A detail message is a String that describes this particular exception.
   *
   * @param msg the detail message.
   */
  public EmptyQueueException(String msg) {
    super(msg);
  }
}