package org.uma.ed.exam;

import org.uma.ed.datastructures.graph.DictionaryGraph;
import org.uma.ed.datastructures.graph.Graph;
import org.uma.ed.datastructures.list.List;
import org.uma.ed.datastructures.set.JDKHashSet;
import org.uma.ed.datastructures.set.Set;

import java.util.Iterator;

/**
 * Simple class to test CycleDetector implementation.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class Test {
  private static <V> boolean isCycle(Graph<V> graph, List<V> cycle) {
    boolean ok = true;
    if (cycle.isEmpty()) {
      ok = false;
    } else {
      Iterator<V> iterator = cycle.iterator();
      V vertex = iterator.next();
      V first = vertex;
      int count = 1;
      Set<V> vertices = JDKHashSet.empty();
      while (ok && iterator.hasNext()) {
        V next = iterator.next();
        if (vertices.contains(next) || !graph.successors(vertex).contains(next)) {
          ok = false;
        } else {
          vertices.insert(next);
          count++;
          vertex = next;
        }
      }
      ok = ok && count > 3 && vertex.equals(first);
    }
    return ok;
  }

  private static <V> void testCyclic(Graph<V> graph, String name, boolean expected) {
    Graph<V> graphCopy = DictionaryGraph.copyOf(graph);
    CycleDetector<V> cycleDetector = CycleDetector.of(graph);
    boolean cyclic = cycleDetector.isCyclic();

    if (!graph.equals(graphCopy)) {
      System.out.println("Test isCyclic " + name + ": failed: graph was modified and it shouldn't");
    } else if (cyclic != expected) {
      System.out.println("Test isCyclic " + name + ": failed: expected result for isCyclic was " + expected +
          " but result was " + cyclic);
    } else {
      System.out.println("Test isCyclic " + name + ": passed");
    }
  }

  private static <V> void testCycle(Graph<V> graph, String name) {
    Graph<V> graphCopy = DictionaryGraph.copyOf(graph);
    CycleDetector<V> cycleDetector = CycleDetector.of(graph);
    List<V> cycleDetected = cycleDetector.cycle();

    if (!graph.equals(graphCopy)) {
      System.out.println("Test cycle " + name + ": failed: graph was modified and it shouldn't");
    } else if (!isCycle(graph, cycleDetected)) {
      System.out.println("Test cycle " + name + ": failed: detected cycle is not valid");
    }  else {
      System.out.println("Test cycle " + name + ": passed");
    }
  }

  public static void main(String[] args) {
    Graph<Character> graph00 = DictionaryGraph.empty();

    Graph<Character> graph01 = DictionaryGraph.empty();
    graph01.addVertex('A');

    Graph<Character> graph02 = DictionaryGraph.empty();
    graph02.addVertex('A');
    graph02.addVertex('B');
    graph02.addEdge('A', 'B');

    Graph<Character> graph03 = DictionaryGraph.empty();
    graph03.addVertex('A');
    graph03.addVertex('B');
    graph03.addVertex('C');
    graph03.addVertex('D');
    graph03.addVertex('E');
    graph03.addVertex('F');
    graph03.addVertex('G');
    graph03.addVertex('H');
    graph03.addVertex('I');
    graph03.addEdge('A', 'B');
    graph03.addEdge('B', 'C');
    graph03.addEdge('B', 'D');
    graph03.addEdge('A', 'E');
    graph03.addEdge('E', 'F');
    graph03.addEdge('F', 'G');
    graph03.addEdge('E', 'H');
    graph03.addEdge('A', 'I');

    Graph<Character> graph04 = DictionaryGraph.empty();
    graph04.addVertex('A');
    graph04.addVertex('B');
    graph04.addVertex('C');
    graph04.addEdge('A', 'B');
    graph04.addEdge('B', 'C');
    graph04.addEdge('C', 'A');

    Graph<Character> graph05 = DictionaryGraph.empty();
    graph05.addVertex('A');
    graph05.addVertex('B');
    graph05.addVertex('C');
    graph05.addVertex('D');
    graph05.addVertex('E');
    graph05.addVertex('F');
    graph05.addEdge('A', 'B');
    graph05.addEdge('B', 'C');
    graph05.addEdge('C', 'D');
    graph05.addEdge('D', 'F');
    graph05.addEdge('F', 'E');
    graph05.addEdge('E', 'C');

    Graph<Character> graph06 = DictionaryGraph.empty();
    graph06.addVertex('A');
    graph06.addVertex('B');
    graph06.addVertex('C');
    graph06.addVertex('D');
    graph06.addVertex('E');
    graph06.addVertex('F');
    graph06.addEdge('A', 'B');
    graph06.addEdge('B', 'C');
    graph06.addEdge('C', 'D');
    graph06.addEdge('D', 'F');
    graph06.addEdge('F', 'E');
    graph06.addEdge('E', 'B');
    graph06.addEdge('F', 'C');

    Graph<Character> graph07 = DictionaryGraph.empty();
    graph07.addVertex('A');
    graph07.addVertex('B');
    graph07.addVertex('C');
    graph07.addVertex('D');
    graph07.addVertex('E');
    graph07.addVertex('F');
    graph07.addVertex('G');
    graph07.addVertex('H');
    graph07.addEdge('A', 'C');
    graph07.addEdge('A', 'D');
    graph07.addEdge('A', 'H');
    graph07.addEdge('B', 'H');
    graph07.addEdge('C', 'G');
    graph07.addEdge('D', 'F');
    graph07.addEdge('D', 'E');
    graph07.addEdge('E', 'F');
    graph07.addEdge('E', 'G');
    graph07.addEdge('E', 'H');

    System.out.println("Tests for isCyclic");
    testCyclic(graph00, "graph00", false);
    testCyclic(graph01, "graph01", false);
    testCyclic(graph02, "graph02", false);
    testCyclic(graph03, "graph03", false);
    testCyclic(graph04, "graph04", true);
    testCyclic(graph05, "graph05", true);
    testCyclic(graph06, "graph06", true);
    testCyclic(graph07, "graph07", true);

    System.out.println("\nTests for cycle");
    testCycle(graph04, "graph04");
    testCycle(graph05, "graph05");
    testCycle(graph06, "graph06");
    testCycle(graph07, "graph07");
  }
}
