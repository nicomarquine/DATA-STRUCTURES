///////////////////////////////////////////////////////////////////////////////
// Student's name: [Your Name]
// Identity number:  [Your DNI or Passport Number] 
///////////////////////////////////////////////////////////////////////////////


package org.uma.ed.exam;


import org.uma.ed.datastructures.dictionary.Dictionary;
import org.uma.ed.datastructures.dictionary.JDKHashDictionary;
import org.uma.ed.datastructures.graph.Graph;
import org.uma.ed.datastructures.set.Set;
import org.uma.ed.datastructures.stack.JDKStack;
import org.uma.ed.datastructures.stack.Stack;
import org.uma.ed.datastructures.set.JDKHashSet;

/**
 * This class is used to test if a given graph is bipartite using depth-first traversal. A bipartite graph is a graph
 * whose vertices can be divided into two disjoint sets such that every edge connects a vertex in the first set to one
 * in the second set.
 *
 * @param <V> The type of the vertices in the graph.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class BipartiteExam<V> {
  public enum Color { Red, Blue;
    Color opposite() {
      return (this == Red) ? Blue : Red;
    }
  }

  private record Pair<V>(V vertex, Color color) {
    static <V> Pair<V> of(V vertex, Color color) {
      return new Pair<>(vertex, color);
    }
  }

  private boolean bipartite;
  private final Dictionary<V, Color> assignedColor;

  static <V> BipartiteExam<V> of(Graph<V> graph) {
    return new BipartiteExam<>(graph);
  }

  public BipartiteExam(Graph<V> graph) {
    // the algorithm must be implemented here

    //INICIALIZAMOS
    Set<V> vertices = JDKHashSet.copyOf(graph.vertices());
    Stack<Pair<V>> stack = new JDKStack<>();
    assignedColor=new JDKHashDictionary<>();
    bipartite=true;
    Color color;
    //SI EL GRAFO ESTÁ VACÍO --> ES BIPARTITO
    //SI EL GRAFO NO ESTÁ VACÍO:
    if (!vertices.isEmpty()) {
    //ESTADO INICIAL
      V source = vertices.iterator().next();
      vertices.delete(source);
      Pair<V> par = new Pair<>(source,Color.Red);
    stack.push(par);
    //BUCLE
    while (bipartite && !stack.isEmpty()){
    //SACAMOS EL PRIMER PAR DE LA PILA
      par=stack.top();
      stack.pop();
      color=par.color;
      source=par.vertex;
      vertices.delete(source);
      //SI NO LO HEMOS ALMACENADO TODAVÍA EN EL DICCIONARIO --> LO ALMACENAMOS
      if (!assignedColor.isDefinedAt(source)){
        assignedColor.insert(source,color);
        vertices.delete(source);
        //GUARDAMOS EN LA PILA LOS VÉRTICES ADYACENTES A ESE POR LOS QUE NO HAYAMOS PASADO YA
        for (V successor : graph.successors(source)){
          if (!assignedColor.isDefinedAt(successor)){
            stack.push(new Pair<>(successor, color.opposite()));
          }
        }
      //SI YA LO HEMOS ALMACENADO EN EL DICCIONARIO --> COMPROBAMOS SI SIGUE SIENDO BIPARTITO
      } else {
      //SI NO COINCIDEN LOS COLORES --> NO ES BIPARTITO (SE SALE DEL BUCLE)
        if (color!=assignedColor.valueOf(source)){
          bipartite=false;
        }
      }
    }
  }
}

  public boolean isBipartite() {
    return bipartite;
  }

  public Dictionary<V, Color> assignedColor() {
    return bipartite ? assignedColor : null;
  }

  public static void main(String[] args) {
    Test.main(args);
  }
}

