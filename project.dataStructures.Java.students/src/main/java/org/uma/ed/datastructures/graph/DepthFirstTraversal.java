package org.uma.ed.datastructures.graph;

import org.uma.ed.datastructures.stack.JDKStack;
import org.uma.ed.datastructures.stack.Stack;

/**
 * An implementation of the {@link Traversal} framework that performs a Depth-First Search (DFS).
 * <p>
 * Depth-first traversal explores as far as possible along each branch before backtracking.
 * It follows a path from the source until it reaches the end, then backtracks and explores
 * the next available path.
 * <p>
 * This class achieves the DFS strategy by providing a LIFO (Last-In-First-Out) {@link Store},
 * which is implemented using a {@link Stack}.
 *
 * @param <V> The type of the elements (vertices) in the traversable structure.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class DepthFirstTraversal<V> extends Traversal<V> {

  /**
   * Constructs a new {@code DepthFirstTraversal} starting from a given source vertex.
   *
   * @param traversable the graph-like data structure to be traversed.
   * @param source      the starting vertex for the traversal.
   */
  public DepthFirstTraversal(Traversable<V> traversable, V source) {
    super(traversable, source);
  }

  /**
   * Factory method to create a {@code DepthFirstTraversal} instance.
   *
   * @param <V>         the type of elements in the structure.
   * @param traversable the data structure to be traversed.
   * @param source      the starting point for the traversal.
   * @return a new {@code DepthFirstTraversal} object ready for iteration.
   */
  public static <V> DepthFirstTraversal<V> of(Traversable<V> traversable, V source) {
    return new DepthFirstTraversal<>(traversable, source);
  }

  /**
   * An adapter class that implements the {@link Store} interface using a {@link Stack}.
   * This provides the LIFO behavior required for a depth-first search.
   */
  private static final class LIFOStore<T> extends JDKStack<T> implements Store<T> {

    /**
     * Inserts an element into the store by pushing it onto the stack.
     *
     * @param element the element to be inserted.
     */
    @Override
    public void insert(T element) {
      push(element);
    }

    /**
     * Extracts an element from the store by popping it from the stack.
     *
     * @return the extracted element (the one at the top of the stack).
     */
    @Override
    public T extract() {
      T element = top();
      pop();
      return element;
    }
  }

  /**
   * Provides the concrete implementation of the template method from the {@code Traversal} superclass.
   * <p>
   * This method returns a new {@code LIFOStore}, which uses a stack to ensure that vertices
   * are explored in a depth-first manner.
   *
   * @return a new LIFO {@code Store} instance.
   */
  @Override
  Store<TraversalEdge<V>> newStore() {
    return new LIFOStore<>();
  }
}