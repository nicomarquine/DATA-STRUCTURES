package org.uma.ed.datastructures.graph;

import org.uma.ed.datastructures.set.Set;

/**
 * An interface representing an undirected graph (or simply, a graph).
 * <p>
 * A graph is a collection of vertices (or nodes) and edges, where each edge connects
 * a pair of vertices. In an undirected graph, an edge {@code (u, v)} is the same as
 * an edge {@code (v, u)}. This interface assumes the graph is simple, meaning it does not
 * allow loops (edges from a vertex to itself) or multiple edges between the same
 * pair of vertices.
 * <p>
 * This interface extends {@link Traversable}, allowing graph traversal algorithms
 * (like DFS and BFS) to operate on its implementations. For an undirected graph,
 * the "successors" of a vertex are its adjacent vertices.
 *
 * @param <V> The type of the vertices in the graph.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public interface Graph<V> extends Traversable<V> {

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
   * Vertices must be added before they can be connected by an edge.
   *
   * @param vertex the vertex to be added to the graph.
   */
  void addVertex(V vertex);

  /**
   * Removes the specified vertex from this graph.
   * <p>
   * If the vertex is present, it is removed, along with all edges incident to it.
   * If the vertex is not in the graph, this operation has no effect.
   *
   * @param vertex the vertex to be removed.
   */
  void deleteVertex(V vertex);

  /**
   * Adds an undirected edge between two specified vertices in this graph.
   *
   * @param vertex1 one vertex of the edge.
   * @param vertex2 the other vertex of the edge.
   * @throws GraphException if either {@code vertex1} or {@code vertex2} is not a vertex in this graph.
   */
  void addEdge(V vertex1, V vertex2);

  /**
   * Removes the edge between two specified vertices from this graph.
   * <p>
   * If there is no edge between the vertices, the graph remains unchanged.
   *
   * @param vertex1 one vertex of the edge.
   * @param vertex2 the other vertex of the edge.
   */
  void deleteEdge(V vertex1, V vertex2);

  /**
   * Returns the number of vertices in this graph.
   *
   * @return the number of vertices in the graph.
   */
  int numberOfVertices();

  /**
   * Returns the number of edges in this graph.
   *
   * @return the number of edges in the graph.
   */
  int numberOfEdges();

  /**
   * Returns a set view of all vertices in this graph.
   *
   * @return a {@code Set} containing all the vertices in the graph.
   */
  Set<V> vertices();

  /**
   * Returns a set view of all edges in this graph.
   *
   * @return a {@code Set} containing all the edges in the graph.
   */
  Set<Edge<V>> edges();

  /**
   * Returns the degree of a specified vertex.
   * <p>
   * The degree of a vertex is the number of edges incident to it.
   *
   * @param vertex the vertex whose degree is to be returned.
   * @return the degree of the vertex.
   * @throws GraphException if the vertex is not in the graph.
   */
  int degree(V vertex);

  /**
   * Returns a set of all vertices adjacent to a specified vertex.
   * <p>
   * This method is required by the {@link Traversable} interface. For an undirected graph,
   * the successors of a vertex are its neighbors (adjacent vertices).
   *
   * @param vertex the vertex whose adjacent vertices are to be returned.
   * @return a {@code Set} of all vertices adjacent to the specified vertex.
   * @throws GraphException if the vertex is not in the graph.
   */
  @Override
  Set<V> successors(V vertex);
}