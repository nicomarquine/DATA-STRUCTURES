package org.uma.ed.datastructures.graph;

import org.uma.ed.datastructures.dictionary.Dictionary;
import org.uma.ed.datastructures.dictionary.JDKHashDictionary;
import org.uma.ed.datastructures.set.Set;

import java.util.StringJoiner;

/**
 * An implementation of the {@link Graph} interface using an adjacency list representation.
 * <p>
 * The adjacency list is stored in a {@link Dictionary}, where each key is a vertex in the
 * graph, and the corresponding value is a {@link Set} of its adjacent vertices (neighbors).
 * <p>
 * This representation is efficient for sparse graphs (graphs with relatively few edges).
 * Operations like adding a vertex or an edge, and finding the neighbors of a vertex,
 * are typically very fast.
 *
 * @param <V> The type of the vertices in the graph.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class DictionaryGraph<V> implements Graph<V> {

  /**
   * The core data structure: a dictionary mapping each vertex to its set of neighbors.
   */
  private final Dictionary<V, Set<V>> adjacentsOf;

  /**
   * Constructs an empty {@code DictionaryGraph}.
   */
  public DictionaryGraph() {
    this.adjacentsOf = JDKHashDictionary.empty();
  }

  /**
   * Creates an empty {@code DictionaryGraph}.
   *
   * @param <V> The type for vertices in the graph.
   * @return An empty {@code DictionaryGraph}.
   */
  public static <V> DictionaryGraph<V> empty() {
    return new DictionaryGraph<>();
  }

  /**
   * Creates a new {@code DictionaryGraph} with a given set of vertices and edges.
   *
   * @param <V>      The type for vertices in the graph.
   * @param vertices The initial set of vertices.
   * @param edges    The initial set of edges.
   * @return A new {@code DictionaryGraph} populated with the given data.
   */
  public static <V> DictionaryGraph<V> of(Set<V> vertices, Set<Edge<V>> edges) {
    DictionaryGraph<V> graph = new DictionaryGraph<>();
    for (V vertex : vertices) {
      graph.addVertex(vertex);
    }
    for (Edge<V> edge : edges) {
      graph.addEdge(edge.vertex1(), edge.vertex2());
    }
    return graph;
  }

  /**
   * Creates a new {@code DictionaryGraph} that is a copy of the given graph.
   *
   * @param <V>   The type for vertices in the graph.
   * @param graph The graph to be copied.
   * @return A new {@code DictionaryGraph} with the same vertices and edges.
   */
  public static <V> DictionaryGraph<V> copyOf(Graph<V> graph) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public boolean isEmpty() { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public void addVertex(V vertex) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public void addEdge(V vertex1, V vertex2) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public void deleteEdge(V vertex1, V vertex2) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public void deleteVertex(V vertex) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public Set<V> vertices() { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public Set<Edge<V>> edges() { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public int numberOfVertices() { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public int numberOfEdges() { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public Set<V> successors(V vertex) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public int degree(V vertex) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public String toString() {
    String className = getClass().getSimpleName();

    StringJoiner verticesSJ = new StringJoiner(", ", "vertices(", ")");
    for (V vertex : vertices()) {
      verticesSJ.add(vertex.toString());
    }

    StringJoiner edgesSJ = new StringJoiner(", ", "edges(", ")");
    for (Edge<V> edge : edges()) {
      edgesSJ.add(edge.toString());
    }

    StringJoiner sj = new StringJoiner(", ", className + "(", ")");
    sj.add(verticesSJ.toString());
    sj.add(edgesSJ.toString());
    return sj.toString();
  }
}