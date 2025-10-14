package org.uma.ed.datastructures.graph;

import java.io.Serial;

/**
 * Thrown to indicate that an invalid operation has been attempted on a graph.
 * <p>
 * This typically occurs when an operation references a vertex that does not exist in the graph
 * (e.g., trying to add an edge to a non-existent vertex) or when an operation
 * violates a graph's structural constraints.
 * <p>
 * It extends {@link RuntimeException}, making it an unchecked exception.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class GraphException extends RuntimeException {

  /**
   * Serial version UID for serialization.
   */
  @Serial
  private static final long serialVersionUID = -7807923404308749451L;

  /**
   * Constructs a new {@code GraphException} with no detail message.
   */
  public GraphException() {
    super();
  }

  /**
   * Constructs a new {@code GraphException} with the specified detail message.
   * <p>
   * A detail message is a String that describes this particular exception.
   *
   * @param msg the detail message.
   */
  public GraphException(String msg) {
    super(msg);
  }
}