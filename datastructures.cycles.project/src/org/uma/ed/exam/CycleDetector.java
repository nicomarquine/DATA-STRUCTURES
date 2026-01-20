///////////////////////////////////////////////////////////////////////////////
// Student's name: [Your Name]
// Identity number:  [Your DNI or Passport Number]
///////////////////////////////////////////////////////////////////////////////

package org.uma.ed.exam;

import org.uma.ed.datastructures.dictionary.Dictionary;
import org.uma.ed.datastructures.dictionary.JDKHashDictionary;
import org.uma.ed.datastructures.graph.Graph;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;
import org.uma.ed.datastructures.set.JDKHashSet;
import org.uma.ed.datastructures.set.Set;
import org.uma.ed.datastructures.stack.Stack;
import org.uma.ed.datastructures.stack.JDKStack;

/**
 * Class to detect cycles in a graph.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class CycleDetector<V> {
  // A record to represent a pair of vertices in the stack
  private record Pair<V>(V predecessor, V current) {
    // Factory method
    static <V> Pair<V> of(V predecessor, V current) { // factory method
      return new Pair<>(predecessor, current);
    }
  }

  private boolean cyclic;
  private final Dictionary<V, V> predecessors;
  private final List<V> cycle;

  //// DO NOT EDIT ANY CODE ABOVE

  //// BEGIN (A)
  public CycleDetector(Graph<V> graph) {
    this.cyclic = false;
    this.predecessors = new JDKHashDictionary<>();
    this.cycle = new JDKArrayList<>();

    // Grafo vacío => acíclico
    if (graph.isEmpty()) {
      return;
    }

    Set<V> visited = new JDKHashSet<>();
    Stack<Pair<V>> stack = new JDKStack<>();

    // Elegimos un vértice cualquiera como source
    V source = graph.vertices().iterator().next();

    // Predecesor ficticio para el vértice inicial
    stack.push(Pair.of(source, source));

    while (!cyclic && !stack.isEmpty()) {
      Pair<V> pair = stack.top();
      stack.pop();
      V predecessor = pair.predecessor();
      V current = pair.current();

      if (visited.contains(current)) {
        cyclic = true;
        reconstructCycle(predecessor, current);
      } else {
        visited.insert(current);
        predecessors.insert(current, predecessor);

        for (V successor : graph.successors(current)) {
          if (!successor.equals(predecessor)) {
            stack.push(Pair.of(current, successor));
          }
        }
      }
    }
  }

  //// END (A)

  //// BEGIN (B)
  // Reconstructs a cycle from the first vertex revisited and its predecessor. Stores it in cycle list
  private void reconstructCycle(V predecessor, V revisited) {
    // Complete the cycle reconstruction algorithm here
    cycle.clear();

    cycle.append(revisited);
    V current = predecessor;

    while(!current.equals(revisited)){
      cycle.append(current);
      current = predecessors.valueOf(current);
    }
    cycle.append(revisited);
  }
  //// END (B)

  //// DO NOT EDIT ANY CODE BELOW

  // Factory method
  public static <V> CycleDetector<V> of(Graph<V> graph) {  // factory method
    return new CycleDetector<>(graph);
  }

  // Returns true if graph is cyclic
  public boolean isCyclic() {
    return cyclic;
  }

  // Returns the cycle detected in graph, if any
  public List<V> cycle() {
    return cyclic ? cycle : null;
  }

  public static void main(String[] args) {
    Test.main(args);
  }
}



