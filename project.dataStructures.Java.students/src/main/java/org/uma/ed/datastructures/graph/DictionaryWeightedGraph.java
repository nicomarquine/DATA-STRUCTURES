package org.uma.ed.datastructures.graph;

import org.uma.ed.datastructures.dictionary.Dictionary;
import org.uma.ed.datastructures.dictionary.JDKHashDictionary;
import org.uma.ed.datastructures.set.JDKHashSet;
import org.uma.ed.datastructures.set.Set;

import java.util.StringJoiner;

/**
 * An implementation of the {@link WeightedGraph} interface for undirected, weighted graphs.
 * <p>
 * This class uses an adjacency map representation, which is stored as a "dictionary of
 * dictionaries". The main dictionary maps each vertex to a secondary dictionary. This
 * secondary dictionary, in turn, maps each adjacent vertex to the weight of the
 * connecting edge.
 * <p>
 * Example: An edge (u, v) with weight w is stored as two entries:
 * <ul>
 *     <li>The map for vertex u will contain the entry (v -> w).</li>
 *     <li>The map for vertex v will contain the entry (u -> w).</li>
 * </ul>
 * This representation is efficient for sparse graphs.
 *
 * @param <V> The type of the vertices in the graph.
 * @param <W> The type of the weights on the edges.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class DictionaryWeightedGraph<V, W> implements WeightedGraph<V, W> {

  /**
   * The core data structure: a dictionary mapping each vertex to its own adjacency map.
   * The adjacency map maps a neighbor vertex to the weight of the connecting edge.
   */
  private final Dictionary<V, Dictionary<V, W>> dictionaryOf;

  /**
   * Constructs an empty {@code DictionaryWeightedGraph}.
   */
  public DictionaryWeightedGraph() {
    this.dictionaryOf = JDKHashDictionary.empty();
  }

  /**
   * Creates an empty undirected weighted graph.
   */
  public static <V, W> DictionaryWeightedGraph<V, W> empty() {
    return new DictionaryWeightedGraph<>();
  }

  /**
   * Creates a new {@code DictionaryWeightedGraph} with a given set of vertices and edges.
   */
  public static <V, W> DictionaryWeightedGraph<V, W> of(Set<V> vertices, Set<WeightedEdge<V, W>> edges) {
    DictionaryWeightedGraph<V, W> weightedGraph = new DictionaryWeightedGraph<>();
    for (V vertex : vertices) {
      weightedGraph.addVertex(vertex);
    }
    for (WeightedEdge<V, W> edge : edges) {
      weightedGraph.addEdge(edge.vertex1(), edge.vertex2(), edge.weight());
    }
    return weightedGraph;
  }

  /**
   * Creates a new {@code DictionaryWeightedGraph} that is a copy of the given graph.
   */
  public static <V, W> DictionaryWeightedGraph<V, W> copyOf(WeightedGraph<V, W> graph) {
    return of(graph.vertices(), graph.edges());
  }

  @Override
  public boolean isEmpty() {
    return dictionaryOf.isEmpty();
  }

  @Override
  public void addVertex(V vertex) {
    if (!dictionaryOf.isDefinedAt(vertex)) {
      dictionaryOf.insert(vertex, JDKHashDictionary.empty());
    }
  }

  @Override
  public void deleteVertex(V vertex) {
    // Remove the vertex from the main dictionary. This also removes all its outgoing edge information.
    Dictionary<V, W> neighbors = dictionaryOf.valueOf(vertex);
    dictionaryOf.delete(vertex);

    // If the vertex existed, we must also remove it from the adjacency maps of its former neighbors.
    if (neighbors != null) {
      for (V neighbor : neighbors.keys()) {
        Dictionary<V, W> neighborAdjacencyMap = dictionaryOf.valueOf(neighbor);
        if (neighborAdjacencyMap != null) {
          neighborAdjacencyMap.delete(vertex);
        }
      }
    }
  }

  @Override
  public void addEdge(V vertex1, V vertex2, W weight) {
    Dictionary<V, W> adjMap1 = dictionaryOf.valueOf(vertex1);
    if (adjMap1 == null) {
      throw new GraphException("addEdge: vertex " + vertex1 + " is not in the graph.");
    }
    Dictionary<V, W> adjMap2 = dictionaryOf.valueOf(vertex2);
    if (adjMap2 == null) {
      throw new GraphException("addEdge: vertex " + vertex2 + " is not in the graph.");
    }

    // Add entries in both directions to represent the undirected edge.
    adjMap1.insert(vertex2, weight);
    adjMap2.insert(vertex1, weight);
  }

  @Override
  public void deleteEdge(V vertex1, V vertex2) {
    Dictionary<V, W> adjMap1 = dictionaryOf.valueOf(vertex1);
    if (adjMap1 != null) {
      adjMap1.delete(vertex2);
    }
    Dictionary<V, W> adjMap2 = dictionaryOf.valueOf(vertex2);
    if (adjMap2 != null) {
      adjMap2.delete(vertex1);
    }
  }

  @Override
  public Set<Successor<V, W>> successors(V vertex) {
    Dictionary<V, W> adjMap = dictionaryOf.valueOf(vertex);
    if (adjMap == null) {
      throw new GraphException("successors: vertex " + vertex + " is not in the graph.");
    }

    Set<Successor<V, W>> successorsSet = JDKHashSet.empty();
    for (Dictionary.Entry<V, W> entry : adjMap.entries()) {
      successorsSet.insert(Successor.of(entry.key(), entry.value()));
    }
    return successorsSet;
  }

  @Override
  public Set<V> vertices() {
    return JDKHashSet.from(dictionaryOf.keys());
  }

  @Override
  public Set<WeightedEdge<V, W>> edges() {
    Set<WeightedEdge<V, W>> edgesSet = JDKHashSet.empty();
    for (Dictionary.Entry<V, Dictionary<V, W>> entry1 : dictionaryOf.entries()) {
      V source = entry1.key();
      Dictionary<V, W> adjMap = entry1.value();
      for (Dictionary.Entry<V, W> entry2 : adjMap.entries()) {
        V destination = entry2.key();
        // The HashSet handles duplicates since Edge.equals is order-independent.
        edgesSet.insert(WeightedEdge.of(source, destination, entry2.value()));
      }
    }
    return edgesSet;
  }

  @Override
  public int numberOfVertices() {
    return dictionaryOf.size();
  }

  @Override
  public int numberOfEdges() {
    int totalDegree = 0;
    for (Dictionary<V, W> adjMap : dictionaryOf.values()) {
      totalDegree += adjMap.size();
    }
    // By the handshaking lemma, the sum of degrees is 2 * |E|.
    return totalDegree / 2;
  }

  @Override
  public String toString() {
    String className = getClass().getSimpleName();

    StringJoiner verticesSJ = new StringJoiner(", ", "vertices(", ")");
    for (V vertex : vertices()) {
      verticesSJ.add(vertex.toString());
    }

    StringJoiner edgesSJ = new StringJoiner(", ", "edges(", ")");
    for (WeightedEdge<V, W> edge : edges()) {
      edgesSJ.add(edge.toString());
    }

    StringJoiner sj = new StringJoiner(", ", className + "(", ")");
    sj.add(verticesSJ.toString());
    sj.add(edgesSJ.toString());
    return sj.toString();
  }
}