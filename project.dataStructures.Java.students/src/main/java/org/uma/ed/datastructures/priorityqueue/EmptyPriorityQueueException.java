package org.uma.ed.datastructures.priorityqueue;

import java.io.Serial;

/**
 * Thrown to indicate that a non-valid operation has been attempted on an empty
 * priority queue.
 * <p>
 * This typically occurs when methods like {@link PriorityQueue#first()} or
 * {@link PriorityQueue#dequeue()} are invoked on a priority queue that contains no elements.
 * <p>
 * It extends {@link RuntimeException}, making it an unchecked exception.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class EmptyPriorityQueueException extends RuntimeException {

  /**
   * Serial version UID for serialization.
   */
  @Serial
  private static final long serialVersionUID = 2551856243837331348L;

  /**
   * Constructs a new {@code EmptyPriorityQueueException} with no detail message.
   */
  public EmptyPriorityQueueException() {
    super();
  }

  /**
   * Constructs a new {@code EmptyPriorityQueueException} with the specified detail message.
   * <p>
   * A detail message is a String that describes this particular exception.
   *
   * @param msg the detail message.
   */
  public EmptyPriorityQueueException(String msg) {
    super(msg);
  }
}