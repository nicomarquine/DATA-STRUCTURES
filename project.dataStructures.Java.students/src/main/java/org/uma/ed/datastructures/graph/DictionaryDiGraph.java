package org.uma.ed.datastructures.graph;

import org.uma.ed.datastructures.dictionary.Dictionary;
import org.uma.ed.datastructures.dictionary.JDKHashDictionary;
import org.uma.ed.datastructures.set.Set;

import java.util.StringJoiner;

/**
 * An implementation of the {@link DiGraph} interface using an adjacency list representation.
 * <p>
 * The adjacency list is stored in a {@link Dictionary}, where each key is a vertex,
 * and the corresponding value is a {@link Set} of its successor vertices (i.e., the
 * vertices reachable via a direct outgoing edge).
 * <p>
 * This representation is efficient for sparse graphs.
 *
 * @param <V> The type of the vertices in the graph.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class DictionaryDiGraph<V> implements DiGraph<V> {

  /**
   * The core data structure: a dictionary mapping each vertex to its set of successors.
   */
  private final Dictionary<V, Set<V>> successorsOf;

  /**
   * Constructs an empty {@code DictionaryDiGraph}.
   */
  public DictionaryDiGraph() {
    this.successorsOf = JDKHashDictionary.empty();
  }

  /**
   * Creates an empty directed graph.
   *
   * @param <V> Type for vertices in the graph.
   * @return An empty {@code DictionaryDiGraph}.
   */
  public static <V> DictionaryDiGraph<V> empty() {
    return new DictionaryDiGraph<>();
  }

  /**
   * Creates a new {@code DictionaryDiGraph} with a given set of vertices and edges.
   *
   * @param <V>      The type for vertices.
   * @param vertices The initial set of vertices.
   * @param edges    The initial set of directed edges.
   * @return A new {@code DictionaryDiGraph} populated with the given data.
   */
  public static <V> DictionaryDiGraph<V> of(Set<V> vertices, Set<DiEdge<V>> edges) {
    DictionaryDiGraph<V> diGraph = new DictionaryDiGraph<>();
    for (V vertex : vertices) {
      diGraph.addVertex(vertex);
    }
    for (DiEdge<V> edge : edges) {
      diGraph.addDiEdge(edge.source(), edge.destination());
    }
    return diGraph;
  }

  /**
   * Creates a new {@code DictionaryDiGraph} that is a copy of the given directed graph.
   *
   * @param <V>     The type for vertices.
   * @param diGraph The directed graph to be copied.
   * @return A new {@code DictionaryDiGraph} with the same vertices and edges.
   */
  public static <V> DictionaryDiGraph<V> copyOf(DiGraph<V> diGraph) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public boolean isEmpty() { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public void addVertex(V vertex) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public void addDiEdge(V source, V destination) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public void deleteDiEdge(V source, V destination) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public void deleteVertex(V vertex) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public Set<V> vertices() { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public Set<DiEdge<V>> edges() { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public int numberOfVertices() { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public int numberOfEdges() { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public Set<V> successors(V source) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public Set<V> predecessors(V destination) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public int inDegree(V vertex) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public int outDegree(V vertex) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public String toString() {
    String className = getClass().getSimpleName();

    StringJoiner verticesSJ = new StringJoiner(", ", "vertices(", ")");
    for (V vertex : this.vertices()) {
      verticesSJ.add(vertex.toString());
    }

    StringJoiner edgesSJ = new StringJoiner(", ", "edges(", ")");
    for (DiEdge<V> edge : this.edges()) {
      edgesSJ.add(edge.toString());
    }

    StringJoiner sj = new StringJoiner(", ", className + "(", ")");
    sj.add(verticesSJ.toString());
    sj.add(edgesSJ.toString());
    return sj.toString();
  }
}