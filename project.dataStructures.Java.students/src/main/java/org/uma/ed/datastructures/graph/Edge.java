package org.uma.ed.datastructures.graph;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Represents an undirected edge in a {@link Graph}.
 * <p>
 * An edge is a connection between two vertices, {@code vertex1} and {@code vertex2}.
 * Since the edge is undirected, the order of the vertices does not matter.
 * An edge connecting {@code u} and {@code v} is considered equal to an edge connecting
 * {@code v} and {@code u}.
 * <p>
 * This class is implemented as a {@code record}, but overrides {@code equals()} and
 * {@code hashCode()} to enforce the unordered nature of the edge.
 *
 * @param <V> The type of the vertices connected by this edge.
 * @param vertex1 one vertex of the edge.
 * @param vertex2 the other vertex of the edge.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public record Edge<V>(V vertex1, V vertex2) {

  /**
   * Factory method to create a new {@code Edge} instance.
   *
   * @param <V>     the type of the vertices.
   * @param vertex1 one vertex of the edge.
   * @param vertex2 the other vertex of the edge.
   * @return a new {@code Edge} connecting the two vertices.
   */
  public static <V> Edge<V> of(V vertex1, V vertex2) {
    return new Edge<>(vertex1, vertex2);
  }

  /**
   * Compares this edge to the specified object for equality.
   * <p>
   * Returns {@code true} if the object is also an {@code Edge} and the two edges
   * connect the same pair of vertices, regardless of their order. For example,
   * {@code Edge(u, v).equals(Edge(v, u))} is {@code true}.
   *
   * @param obj the object to compare this {@code Edge} against.
   * @return {@code true} if the given object is an equivalent edge, {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    return this == obj ||
        (obj instanceof Edge<?> edge &&
            ((Objects.equals(vertex1, edge.vertex1) && Objects.equals(vertex2, edge.vertex2)) ||
                (Objects.equals(vertex1, edge.vertex2) && Objects.equals(vertex2, edge.vertex1))
            )
        );
  }

  /**
   * Returns a hash code value for this edge.
   * <p>
   * The hash code is computed as the sum of the hash codes of the two vertices.
   * This ensures that the hash code is order-independent, satisfying the contract
   * that if two edges are equal, they must have the same hash code.
   * {@code Edge(u, v).hashCode() == Edge(v, u).hashCode()}.
   *
   * @return a hash code value for this edge.
   */
  @Override
  public int hashCode() { // must return same code for two equal edges
    return Objects.hashCode(vertex1) + Objects.hashCode(vertex2);
  }

  /**
   * Returns a string representation of the edge.
   *
   * @return a string in the format {@code "Edge(vertex1, vertex2)"}.
   */
  @Override
  public String toString() {
    String className = getClass().getSimpleName();
    StringJoiner sj = new StringJoiner(", ", className + "(", ")");
    sj.add(String.valueOf(vertex1));
    sj.add(String.valueOf(vertex2));
    return sj.toString();
  }
}