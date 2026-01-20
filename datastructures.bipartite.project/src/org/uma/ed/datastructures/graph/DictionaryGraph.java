package org.uma.ed.datastructures.graph;

import java.util.StringJoiner;
import org.uma.ed.datastructures.dictionary.Dictionary;
import org.uma.ed.datastructures.dictionary.JDKHashDictionary;
import org.uma.ed.datastructures.set.JDKHashSet;
import org.uma.ed.datastructures.set.Set;

/**
 * Class for undirected graphs implemented with a dictionary
 *
 * @param <V> Type for vertices in graph
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class DictionaryGraph<V> implements Graph<V> {
  // dictionary with keys as vertices in Graph and values as adjacent vertices
  private final Dictionary<V, Set<V>> adjacentsOf;  

  /**
   * Creates an empty graph.
   */
  public DictionaryGraph() {
    adjacentsOf = JDKHashDictionary.empty();
  }

  /**
   * Creates an empty graph.
   *
   * @param <V> Type for vertices in graph.
   *
   * @return An empty DictionaryGraph.
   */
  public static <V> DictionaryGraph<V> empty() {
    return new DictionaryGraph<>();
  }

  /**
   * Creates a graph with given vertices and edges.
   *
   * @param vertices vertices to add to graph.
   * @param edges edges to add to graph.
   * @param <V> Type for vertices in graph.
   *
   * @return A DictionaryGraph with given vertices and edges.
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
   * Creates a graph with same vertices and edges as given graph.
   *
   * @param graph Graph to copy.
   * @param <V> Type for vertices in graph.
   *
   * @return A DictionaryGraph with same vertices and edges as given graph.
   */
  public static <V> DictionaryGraph<V> copyOf(Graph<V> graph) {
    return DictionaryGraph.of(graph.vertices(), graph.edges());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEmpty() {
    return adjacentsOf.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addVertex(V vertex) {
    if (!adjacentsOf.isDefinedAt(vertex)) {
      adjacentsOf.insert(vertex, JDKHashSet.empty());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addEdge(V vertex1, V vertex2) {
    Set<V> adjacents1 = adjacentsOf.valueOf(vertex1);
    if (adjacents1 == null) {
      throw new GraphException("vertex " + vertex1 + " is not in graph");
    }
    Set<V> adjacents2 = adjacentsOf.valueOf(vertex2);
    if (adjacents2 == null) {
      throw new GraphException("vertex " + vertex2 + " is not in graph");
    }
    adjacents1.insert(vertex2);
    adjacents2.insert(vertex1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteEdge(V vertex1, V vertex2) {
    Set<V> adjacents1 = adjacentsOf.valueOf(vertex1);
    if (adjacents1 != null) {
      adjacents1.delete(vertex2);
    }
    Set<V> adjacents2 = adjacentsOf.valueOf(vertex2);
    if (adjacents2 != null) {
      adjacents2.delete(vertex1);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteVertex(V vertex) {
    adjacentsOf.delete(vertex); // remove vertex from dictionary and its adjacent vertices
    // remove vertex from all adjacent vertices
    for (Set<V> adjacents : adjacentsOf.values()) {
      adjacents.delete(vertex);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<V> vertices() {
    return JDKHashSet.from(adjacentsOf.keys());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<Edge<V>> edges() {
    Set<Edge<V>> edges = JDKHashSet.empty();
    for (Dictionary.Entry<V, Set<V>> entry : adjacentsOf.entries()) {
      V vertex = entry.key();
      Set<V> adjacents = entry.value();
      for (V adjacent : adjacents) {
        edges.insert(Edge.of(vertex, adjacent));
      }
    }
    return edges;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int numberOfVertices() {
    return adjacentsOf.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int numberOfEdges() {
    int numberOfEdges = 0;
    for (Set<V> adjacents : adjacentsOf.values()) {
      numberOfEdges += adjacents.size();
    }
    return numberOfEdges / 2; // each edge has been counted twice
  }

  /**
   * Returns the successors of a vertex in graph (i.e. vertices to which there is an edge from given vertex).
   *
   * @param vertex vertex for which we want to obtain its successors.
   *
   * @return Successors of a vertex.
   */
  @Override
  public Set<V> successors(V vertex) {
    Set<V> adjacents = adjacentsOf.valueOf(vertex);
    if (adjacents == null) {
      throw new GraphException("vertex " + vertex + " is not in graph");
    }
    return adjacents;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int degree(V vertex) {
    Set<V> adjacents = adjacentsOf.valueOf(vertex);
    return adjacents == null ? 0 : adjacents.size();
  }

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
