package org.uma.ed.demos.graph;

import org.uma.ed.datastructures.dictionary.Dictionary;
import org.uma.ed.datastructures.graph.DictionaryWeightedGraph;
import org.uma.ed.datastructures.graph.Dijkstra;
import org.uma.ed.datastructures.graph.WeightedGraph;
import org.uma.ed.datastructures.list.List;
import org.uma.ed.datastructures.tuple.Tuple2;

/**
 * A demonstration class for the {@link Dijkstra} algorithm implementation.
 * <p>
 * This class builds a sample weighted, undirected graph and then runs Dijkstra's algorithm
 * to find the shortest paths from a designated source vertex to all other vertices.
 * <p>
 * It demonstrates both functionalities provided by the {@code Dijkstra} class:
 * <ol>
 *     <li>Calculating just the minimum costs of the shortest paths.</li>
 *     <li>Calculating both the minimum costs and the sequence of vertices (the path itself).</li>
 * </ol>
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class DijkstraDemo {

  /**
   * Creates a sample weighted graph for demonstration purposes.
   * The graph is connected and has non-negative edge weights, suitable for Dijkstra's algorithm.
   * <pre>
   *       (3)
   *    a ----- b
   *    | \   / |
   *    |  \ /  | (4)
   * (7)|   X   c
   *    |  / \  |
   *    | /   \ | (6)
   *    d ----- e
   *       (4)
   * Edge (b,d) has weight 2.
   * Edge (d,c) has weight 5.
   * </pre>
   * @return a sample {@code WeightedGraph}.
   */
  public static WeightedGraph<Character, Integer> createSampleGraph() {
    WeightedGraph<Character, Integer> graph = DictionaryWeightedGraph.empty();
    char[] vertices = {'a', 'b', 'c', 'd', 'e'};
    for (char v : vertices) {
      graph.addVertex(v);
    }

    graph.addEdge('a', 'b', 3);
    graph.addEdge('a', 'd', 7);
    graph.addEdge('b', 'c', 4);
    graph.addEdge('b', 'd', 2);
    graph.addEdge('c', 'd', 5);
    graph.addEdge('c', 'e', 6);
    graph.addEdge('d', 'e', 4);

    return graph;
  }

  public static void main(String[] args) {
    WeightedGraph<Character, Integer> graph = createSampleGraph();
    char sourceVertex = 'a';

    System.out.println("--- Dijkstra's Algorithm Demo ---");
    System.out.println("Graph structure: " + graph.edges());
    System.out.println("Finding shortest paths from source vertex: '" + sourceVertex + "'");
    System.out.println("-------------------------------------\n");


    System.out.println("--- 1. Calculating Shortest Path Costs Only ---");
    // Run Dijkstra's algorithm to get only the minimum costs.
    Dictionary<Character, Integer> costs = Dijkstra.dijkstra(graph, sourceVertex);

    System.out.println("Minimum cost to each vertex:");
    for (Dictionary.Entry<Character, Integer> entry : costs) {
      System.out.printf("  - to '%c': %d%n", entry.key(), entry.value());
    }
    System.out.println();


    System.out.println("--- 2. Calculating Shortest Paths (Cost and Vertex List) ---");
    // Run the version of Dijkstra's algorithm that returns paths as well.
    Dictionary<Character, Tuple2<Integer, List<Character>>> paths = Dijkstra.dijkstraPaths(graph, sourceVertex);

    System.out.println("Shortest path to each vertex:");
    for (Dictionary.Entry<Character, Tuple2<Integer, List<Character>>> entry : paths) {
      char destination = entry.key();
      int cost = entry.value().first();
      List<Character> path = entry.value().second();
      System.out.printf("  - to '%c': (Cost: %d, Path: %s)%n", destination, cost, path);
    }
  }
}