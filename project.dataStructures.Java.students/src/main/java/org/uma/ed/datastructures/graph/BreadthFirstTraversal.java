package org.uma.ed.datastructures.graph;

import org.uma.ed.datastructures.queue.JDKQueue;
import org.uma.ed.datastructures.queue.Queue;

/**
 * An implementation of the {@link Traversal} framework that performs a Breadth-First Search (BFS).
 * <p>
 * Breadth-first traversal explores the nearest vertices first before moving to vertices that are
 * further away. It visits all vertices at a given distance (number of edges) from the source
 * vertex before visiting any vertices at a greater distance.
 * <p>
 * This class achieves the BFS strategy by providing a FIFO (First-In-First-Out) {@link Store},
 * which is implemented using a {@link Queue}.
 *
 * @param <V> The type of the elements (vertices) in the traversable structure.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class BreadthFirstTraversal<V> extends Traversal<V> {

  /**
   * Constructs a new {@code BreadthFirstTraversal} starting from a given source vertex.
   *
   * @param traversable the graph-like data structure to be traversed.
   * @param source      the starting vertex for the traversal.
   */
  public BreadthFirstTraversal(Traversable<V> traversable, V source) {
    super(traversable, source);
  }

  /**
   * Factory method to create a {@code BreadthFirstTraversal} instance.
   *
   * @param <V>         the type of elements in the structure.
   * @param traversable the data structure to be traversed.
   * @param source      the starting point for the traversal.
   * @return a new {@code BreadthFirstTraversal} object ready for iteration.
   */
  public static <V> BreadthFirstTraversal<V> of(Traversable<V> traversable, V source) {
    return new BreadthFirstTraversal<>(traversable, source);
  }

  /**
   * An adapter class that implements the {@link Store} interface using a {@link Queue}.
   * This provides the FIFO behavior required for a breadth-first search.
   */
  private static final class FIFOStore<T> extends JDKQueue<T> implements Store<T> {

    /**
     * Inserts an element into the store by enqueuing it.
     *
     * @param element the element to be inserted.
     */
    @Override
    public void insert(T element) {
      enqueue(element);
    }

    /**
     * Extracts an element from the store by dequeueing it.
     *
     * @return the extracted element (the one at the front of the queue).
     */
    @Override
    public T extract() {
      T element = first();
      dequeue();
      return element;
    }
  }

  /**
   * Provides the concrete implementation of the template method from the {@code Traversal} superclass.
   * <p>
   * This method returns a new {@code FIFOStore}, which uses a queue to ensure that vertices
   * are explored in a breadth-first manner.
   *
   * @return a new FIFO {@code Store} instance.
   */
  @Override
  Store<TraversalEdge<V>> newStore() {
    return new FIFOStore<>();
  }
}