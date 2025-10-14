package org.uma.ed.datastructures.graph;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Represents a directed edge (or arc) in a {@link DiGraph}.
 * <p>
 * A directed edge is an ordered connection from a {@code source} vertex to a
 * {@code destination} vertex. Unlike an undirected {@link Edge}, the order of
 * vertices is significant. An edge from {@code u} to {@code v} is distinct from an
 * edge from {@code v} to {@code u}.
 * <p>
 * This class is implemented as a {@code record}, and it uses the default,
 * order-sensitive implementations of {@code equals()} and {@code hashCode()}.
 *
 * @param <V>         The type of the vertices connected by this edge.
 * @param source      the source vertex of the edge (its tail).
 * @param destination the destination vertex of the edge (its head).
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public record DiEdge<V>(V source, V destination) {

  /**
   * Factory method to create a new {@code DiEdge} instance.
   *
   * @param <V>         the type of the vertices.
   * @param source      the source vertex.
   * @param destination the destination vertex.
   * @return a new {@code DiEdge} representing a directed connection from source to destination.
   */
  public static <V> DiEdge<V> of(V source, V destination) {
    return new DiEdge<>(source, destination);
  }

  /**
   * Compares this directed edge to the specified object for equality.
   * <p>
   * Returns {@code true} if the object is also a {@code DiEdge} and has the same
   * source and destination vertices in the same order. For example,
   * {@code DiEdge(u, v).equals(DiEdge(u, v))} is {@code true}, but
   * {@code DiEdge(u, v).equals(DiEdge(v, u))} is {@code false}.
   *
   * @param obj the object to compare this {@code DiEdge} against.
   * @return {@code true} if the given object is an equivalent directed edge, {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    return this == obj ||
        (obj instanceof DiEdge<?> diEdge &&
            Objects.equals(source, diEdge.source) &&
            Objects.equals(destination, diEdge.destination)
        );
  }

  /**
   * Returns a hash code value for this directed edge.
   * <p>
   * The hash code is computed based on both the source and destination vertices, making
   * it order-sensitive. This is consistent with the {@code equals} method.
   *
   * @return a hash code value for this directed edge.
   */
  @Override
  public int hashCode() { // must return same code for two equal edges
    int hash = 1;
    hash = 31 * hash + Objects.hashCode(source);
    hash = 31 * hash + Objects.hashCode(destination);
    return hash;
  }

  /**
   * Returns a string representation of the directed edge.
   *
   * @return a string in the format {@code "DiEdge(source, destination)"}.
   */
  @Override
  public String toString() {
    String className = getClass().getSimpleName();
    StringJoiner sj = new StringJoiner(", ", className + "(", ")");
    sj.add(String.valueOf(source));
    sj.add(String.valueOf(destination));
    return sj.toString();
  }
}