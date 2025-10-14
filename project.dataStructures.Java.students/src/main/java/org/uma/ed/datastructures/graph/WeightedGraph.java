package org.uma.ed.datastructures.graph;

import org.uma.ed.datastructures.set.Set;

/**
 * An interface representing an undirected, weighted graph.
 * <p>
 * A weighted graph is a graph where each edge has an associated value,
 * called a weight or cost. This interface extends the concepts of an undirected graph
 * to include these weights.
 * <p>
 * Operations that involve neighbors or successors must now also provide access to the
 * weight of the connecting edge.
 *
 * @param <V> The type of the vertices in the graph.
 * @param <W> The type of the weights on the edges.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public interface WeightedGraph<V, W> {

  /**
   * Checks if this graph is empty (contains no vertices).
   *
   * @return {@code true} if this graph contains no vertices, {@code false} otherwise.
   */
  boolean isEmpty();

  /**
   * Adds the specified vertex to this graph.
   * <p>
   * If the vertex is already in the graph, this operation has no effect.
   *
   * @param vertex the vertex to be added.
   */
  void addVertex(V vertex);

  /**
   * Removes the specified vertex from this graph.
   * <p>
   * If the vertex is present, it is removed, along with all edges incident to it.
   *
   * @param vertex the vertex to be removed.
   */
  void deleteVertex(V vertex);

  /**
   * Adds a weighted, undirected edge between two specified vertices in this graph.
   * <p>
   * If an edge already exists between these vertices, its weight is updated to the
   * new specified weight.
   *
   * @param vertex1     one vertex of the edge.
   * @param vertex2     the other vertex of the edge.
   * @param weight the weight of the edge.
   * @throws GraphException if either {@code vertex1} or {@code vertex2} is not a vertex in this graph.
   */
  void addEdge(V vertex1, V vertex2, W weight);

  /**
   * Removes the edge between two specified vertices from this graph, if it exists.
   *
   * @param vertex1 one vertex of the edge.
   * @param vertex2 the other vertex of the edge.
   */
  void deleteEdge(V vertex1, V vertex2);

  /**
   * Represents a successor of a vertex in a weighted graph.
   * <p>
   * This record conveniently pairs an adjacent vertex with the weight of the edge
   * connecting to it.
   *
   * @param <V> The type of the vertex.
   * @param <W> The type of the weight.
   * @param vertex the adjacent vertex.
   * @param weight the weight of the edge to the adjacent vertex.
   */
  record Successor<V, W>(V vertex, W weight) {

    /**
     * Factory method for creating a {@code Successor} instance.
     */
    public static <V, W> Successor<V, W> of(V vertex, W weight) {
      return new Successor<>(vertex, weight);
    }
  }

  /**
   * Returns a set of all successors of a specified vertex, along with the weights of the
   * connecting edges.
   * <p>
   * For an undirected graph, the successors are the adjacent vertices (neighbors).
   *
   * @param vertex the vertex whose successors are to be returned.
   * @return a {@code Set} of {@code Successor} objects, each representing an adjacent
   *         vertex and the weight of the edge to it.
   * @throws GraphException if the vertex is not in the graph.
   */
  Set<Successor<V, W>> successors(V vertex);

  /**
   * Returns a set view of all vertices in this graph.
   *
   * @return a {@code Set} containing all the vertices.
   */
  Set<V> vertices();

  /**
   * Returns a set view of all weighted edges in this graph.
   *
   * @return a {@code Set} containing all the weighted edges.
   */
  Set<WeightedEdge<V, W>> edges();

  /**
   * Returns the number of vertices in this graph.
   *
   * @return the number of vertices.
   */
  int numberOfVertices();

  /**
   * Returns the number of edges in this graph.
   *
   * @return the number of edges.
   */
  int numberOfEdges();
}