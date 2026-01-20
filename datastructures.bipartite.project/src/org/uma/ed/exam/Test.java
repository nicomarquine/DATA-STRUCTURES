package org.uma.ed.exam;

import org.uma.ed.datastructures.graph.DictionaryGraph;
import org.uma.ed.datastructures.graph.Graph;

/**
 * Simple class to test bipartiteness of graphs.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
class Test {
  public static void main(String[] args) {
    Graph<Integer> graph00 = DictionaryGraph.empty();
    test(graph00, "graph00", true);

    Graph<Integer> graph01 = DictionaryGraph.empty();
    graph01.addVertex(0);
    test(graph01, "graph01", true);

    Graph<Integer> graph02 = DictionaryGraph.copyOf(graph01);
    graph02.addVertex(1);
    graph02.addEdge(0, 1);
    test(graph02, "graph02", true);

    Graph<Integer> graph03 = DictionaryGraph.copyOf(graph02);
    graph03.addVertex(2);
    graph03.addEdge(1, 2);
    test(graph03, "graph03", true);

    Graph<Integer> graph04 = DictionaryGraph.copyOf(graph03);
    graph04.addEdge(0, 2);
    test(graph04, "graph04", false);

    Graph<Integer> graph05 = DictionaryGraph.empty();
    graph05.addVertex(0);
    graph05.addVertex(1);
    graph05.addVertex(2);
    graph05.addVertex(3);
    graph05.addVertex(4);

    graph05.addEdge(0, 3);
    graph05.addEdge(0, 4);
    graph05.addEdge(1, 3);
    graph05.addEdge(1, 4);
    graph05.addEdge(2, 3);
    graph05.addEdge(2, 4);
    test(graph05, "graph05", true);

    Graph<Integer> graph06 = DictionaryGraph.copyOf(graph05);
    graph06.addEdge(3, 4);
    test(graph06, "graph06", false);

    Graph<Integer> graph07 = completeBipartite(5, 5);
    test(graph07, "graph07", true);

    Graph<Integer> graph08 = DictionaryGraph.copyOf(graph07);
    graph08.addEdge(0, 1);
    test(graph08, "graph08", false);

    Graph<Integer> graph09 = completeBipartite(30, 55);
    test(graph09, "graph09", true);

    Graph<Integer> graph10 = DictionaryGraph.copyOf(graph09);
    graph10.addEdge(0, 1);
    test(graph10, "graph10", false);
  }

  /**
   * Creates a complete bipartite graph with n vertices in one part and m vertices in the other part.
   * @param n number of vertices in first part
   * @param m number of vertices in second part
   *
   * @return complete bipartite graph with n+m vertices
   */
  static Graph<Integer> completeBipartite(int n, int m) {
    Graph<Integer> graph = DictionaryGraph.empty();
    for (int v = 0; v < n + m; v++) {
      graph.addVertex(v);
    }

    for (int v = 0; v < n; v++) {
      for (int w = 0; w < m; w++) {
        graph.addEdge(v, n + w);
      }
    }

    return graph;
  }

  static <V> void test(Graph<V> graph, String name, boolean isBipartite) {
    BipartiteExam<V> bipartite = BipartiteExam.of(graph);
    if (bipartite.isBipartite() == isBipartite) {
      System.out.print("Test for " + name + " is correct.");
      if (isBipartite) {
        System.out.println(" It is bipartite.");
      } else {
        System.out.println(" It is not bipartite.");
      }
    } else {
      System.out.print("Test for " + name + " is wrong.");
      if (isBipartite) {
        System.out.println(" Expected: is bipartite but obtained is not bipartite");
      } else {
        System.out.println(" Expected: is not bipartite but obtained is bipartite");
      }
    }
  }
}
