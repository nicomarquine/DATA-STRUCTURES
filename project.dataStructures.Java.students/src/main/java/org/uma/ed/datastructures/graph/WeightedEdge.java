package org.uma.ed.datastructures.graph;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Represents an undirected, weighted edge in a {@link WeightedGraph}.
 * <p>
 * An edge is a connection between two vertices, {@code vertex1} and {@code vertex2},
 * with an associated {@code weight} or cost. Since the edge is undirected, the
 * order of the vertices does not matter. An edge connecting {@code u} and {@code v}
 * with weight {@code w} is considered equal to an edge connecting {@code v} and {@code u}
 * with the same weight {@code w}.
 * <p>
 * This class overrides {@code equals()} and {@code hashCode()} to enforce this
 * unordered-pair-with-weight equality.
 *
 * @param <V> The type of the vertices connected by this edge.
 * @param <W> The type of the weight associated with this edge.
 * @param vertex1 one vertex of the edge.
 * @param vertex2 the other vertex of the edge.
 * @param weight  the weight of the edge.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public record WeightedEdge<V, W>(V vertex1, V vertex2, W weight) {

  /**
   * Factory method to create a new {@code WeightedEdge} instance.
   *
   * @param <V>     the type of the vertices.
   * @param <W>     the type of the weight.
   * @param vertex1 one vertex of the edge.
   * @param vertex2 the other vertex of the edge.
   * @param weight  the weight of the edge.
   * @return a new {@code WeightedEdge} connecting the two vertices with the given weight.
   */
  public static <V, W> WeightedEdge<V, W> of(V vertex1, V vertex2, W weight) {
    return new WeightedEdge<>(vertex1, vertex2, weight);
  }

  /**
   * Compares this weighted edge to the specified object for equality.
   * <p>
   * Returns {@code true} if the object is also a {@code WeightedEdge}, the two edges
   * connect the same pair of vertices (regardless of order), and they have the
   * same weight.
   *
   * @param obj the object to compare this {@code WeightedEdge} against.
   * @return {@code true} if the given object is an equivalent weighted edge, {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    return this == obj ||
        (obj instanceof WeightedEdge<?, ?> weightedEdge &&
            ((Objects.equals(vertex1, weightedEdge.vertex1) && Objects.equals(vertex2, weightedEdge.vertex2)) ||
                (Objects.equals(vertex1, weightedEdge.vertex2) && Objects.equals(vertex2, weightedEdge.vertex1))
            ) &&
            Objects.equals(weight, weightedEdge.weight)
        );
  }

  /**
   * Returns a hash code value for this weighted edge.
   * <p>
   * The hash code is computed based on the hash codes of the two vertices and the weight.
   * The vertex part of the hash is order-independent (by summing their hash codes),
   * ensuring that {@code WeightedEdge(u, v, w).hashCode() == WeightedEdge(v, u, w).hashCode()}.
   *
   * @return a hash code value for this weighted edge.
   */
  @Override
  public int hashCode() {
    int hash = 1;
    hash = 31 * hash + (Objects.hashCode(vertex1) + Objects.hashCode(vertex2));
    hash = 31 * hash + Objects.hashCode(weight);
    return hash;
  }

  /**
   * Returns a string representation of the weighted edge.
   *
   * @return a string in the format {@code "WeightedEdge(vertex1, vertex2, weight)"}.
   */
  @Override
  public String toString() {
    String className = getClass().getSimpleName();
    StringJoiner sj = new StringJoiner(", ", className + "(", ")");
    sj.add(String.valueOf(vertex1));
    sj.add(String.valueOf(vertex2));
    sj.add(String.valueOf(weight));
    return sj.toString();
  }
}

