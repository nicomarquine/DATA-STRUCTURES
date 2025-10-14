package org.uma.ed.datastructures.graph;

import org.uma.ed.datastructures.set.Set;

/**
 * An interface representing a directed graph (or digraph).
 * <p>
 * A directed graph is a collection of vertices and directed edges. Each edge is an
 * ordered pair of vertices {@code (u, v)}, representing a connection from a source
 * vertex {@code u} to a destination vertex {@code v}.
 * <p>
 * This interface assumes the graph is simple, meaning it does not allow loops
 * (edges from a vertex to itself) or multiple edges between the same ordered pair of vertices.
 * <p>
 * Like {@link Graph}, this interface extends {@link Traversable}. For a directed graph,
 * the "successors" of a vertex are the vertices reachable via an outgoing edge.
 *
 * @param <V> The type of the vertices in the graph.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public interface DiGraph<V> extends Traversable<V> {

  /**
   * Checks if this digraph is empty (contains no vertices).
   *
   * @return {@code true} if this digraph contains no vertices, {@code false} otherwise.
   */
  boolean isEmpty();

  /**
   * Adds the specified vertex to this digraph.
   * <p>
   * If the vertex is already in the graph, this operation has no effect.
   *
   * @param vertex the vertex to be added.
   */
  void addVertex(V vertex);

  /**
   * Removes the specified vertex from this digraph.
   * <p>
   * If the vertex is present, it is removed, along with all incoming and outgoing edges.
   * If the vertex is not in the graph, this operation has no effect.
   *
   * @param vertex the vertex to be removed.
   */
  void deleteVertex(V vertex);

  /**
   * Adds a directed edge from a source vertex to a destination vertex.
   *
   * @param source      the source vertex of the edge (tail).
   * @param destination the destination vertex of the edge (head).
   * @throws GraphException if either {@code source} or {@code destination} is not a vertex in this graph.
   */
  void addDiEdge(V source, V destination);

  /**
   * Removes the directed edge from a source vertex to a destination vertex, if it exists.
   *
   * @param source      the source vertex of the edge.
   * @param destination the destination vertex of the edge.
   */
  void deleteDiEdge(V source, V destination);

  /**
   * Returns the number of vertices in this digraph.
   *
   * @return the number of vertices.
   */
  int numberOfVertices();

  /**
   * Returns the number of edges in this digraph.
   *
   * @return the number of edges.
   */
  int numberOfEdges();

  /**
   * Returns a set view of all vertices in this digraph.
   *
   * @return a {@code Set} containing all the vertices in the graph.
   */
  Set<V> vertices();

  /**
   * Returns a set view of all directed edges in this digraph.
   *
   * @return a {@code Set} containing all the directed edges.
   */
  Set<DiEdge<V>> edges();

  /**
   * Returns the in-degree of a specified vertex.
   * <p>
   * The in-degree is the number of incoming edges to the vertex.
   *
   * @param vertex the vertex whose in-degree is to be returned.
   * @return the in-degree of the vertex.
   * @throws GraphException if the vertex is not in the graph.
   */
  int inDegree(V vertex);

  /**
   * Returns the out-degree of a specified vertex.
   * <p>
   * The out-degree is the number of outgoing edges from the vertex. It is equivalent
   * to the size of the set returned by {@code successors(vertex)}.
   *
   * @param vertex the vertex whose out-degree is to be returned.
   * @return the out-degree of the vertex.
   * @throws GraphException if the vertex is not in the graph.
   */
  int outDegree(V vertex);

  /**
   * Returns a set of all predecessors of a specified vertex.
   * <p>
   * A predecessor of a vertex {@code v} is a vertex {@code u} such that there is a
   * directed edge from {@code u} to {@code v}.
   *
   * @param destination the vertex whose predecessors are to be returned.
   * @return a {@code Set} of all vertices that are predecessors of the specified vertex.
   * @throws GraphException if the vertex is not in the graph.
   */
  Set<V> predecessors(V destination);
}