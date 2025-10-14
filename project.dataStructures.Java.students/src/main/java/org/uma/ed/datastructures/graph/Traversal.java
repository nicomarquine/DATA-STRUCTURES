package org.uma.ed.datastructures.graph;

import org.uma.ed.datastructures.dictionary.Dictionary;
import org.uma.ed.datastructures.dictionary.JDKHashDictionary;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;
import org.uma.ed.datastructures.set.JDKHashSet;
import org.uma.ed.datastructures.set.Set;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An abstract class that provides a generic framework for traversing graph-like structures.
 * <p>
 * This class implements the <strong>Template Method design pattern</strong> for graph traversal.
 * It defines the common algorithm structure: maintaining a set of visited vertices and exploring
 * from a source vertex.
 * <p>
 * The specific traversal strategy (e.g., Depth-First or Breadth-First) is determined by the
 * concrete implementation of the {@link #newStore()} method provided by subclasses. This method
 * must return a {@link Store} instance (like a stack for DFS or a queue for BFS) that dictates
 * the order in which vertices are visited.
 * <p>
 * This framework can produce an iteration of the visited vertices or the paths from the source
 * to each visited vertex.
 *
 * @param <V> The type of the elements (vertices) in the traversable structure.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public abstract class Traversal<V> {
  private final Traversable<V> traversable; // The graph-like structure to be traversed.
  private final V source;                   // The starting vertex for the traversal.

  /**
   * Constructs a new Traversal object.
   *
   * @param traversable The data structure to be traversed.
   * @param source      The starting point for the traversal.
   */
  public Traversal(Traversable<V> traversable, V source) {
    this.traversable = traversable;
    this.source = source;
  }

  /**
   * Represents a directed edge from a source to a destination, used internally
   * to track the traversal path.
   */
  record TraversalEdge<V>(V source, V destination) {
    static <V> TraversalEdge<V> of(V source, V destination) {
      return new TraversalEdge<>(source, destination);
    }
  }

  /**
   * Abstract template method that must be implemented by concrete subclasses.
   * <p>
   * This method is responsible for providing the specific storage mechanism (the "store")
   * that defines the traversal strategy.
   * <ul>
   *     <li>A LIFO store (like a {@code Stack}) will result in a Depth-First Traversal.</li>
   *     <li>A FIFO store (like a {@code Queue}) will result in a Breadth-First Traversal.</li>
   * </ul>
   *
   * @return A new, empty {@code Store} instance that dictates the traversal order.
   */
  abstract Store<TraversalEdge<V>> newStore();

  /**
   * Provides the shared, core logic for all traversal iterators.
   * It uses a "lookahead" mechanism (`nextVertex`) to implement `hasNext()` and `next()`.
   */
  private abstract class BaseIterator {
    protected final Set<V> visited;                // Tracks already visited vertices to avoid cycles and redundant work.
    protected final Store<TraversalEdge<V>> store; // The "fringe" of the traversal, holding edges to explore.
    protected final Dictionary<V, V> sources;      // Maps each visited vertex to its predecessor on the traversal path.
    protected V nextVertex;                        // The next vertex to be yielded by the iterator.

    public BaseIterator() {
      this.visited = JDKHashSet.empty();
      this.store = newStore();
      this.sources = JDKHashDictionary.empty();

      // Start the traversal by adding the source vertex to the store.
      // It has no predecessor, so we use null.
      this.store.insert(TraversalEdge.of(null, source));

      // Find the first vertex to yield.
      advanceTraversal();
    }

    /**
     * The engine of the traversal. It repeatedly extracts edges from the store until it
     * finds an unvisited vertex. This vertex becomes the next result of the iterator.
     */
    protected void advanceTraversal() {
      nextVertex = null;
      while (!store.isEmpty() && nextVertex == null) {
        TraversalEdge<V> edge = store.extract();
        V vertex = edge.destination;

        if (!visited.contains(vertex)) {
          // Found a new vertex to visit.
          nextVertex = vertex;
          visited.insert(vertex);
          sources.insert(vertex, edge.source);

          // Add all unvisited successors of this new vertex to the store for future exploration.
          for (V successor : traversable.successors(vertex)) {
            if (!visited.contains(successor)) {
              store.insert(TraversalEdge.of(vertex, successor));
            }
          }
        }
      }
    }

    public boolean hasNext() {
      return nextVertex != null;
    }
  }

  /**
   * An iterator that yields the vertices of the graph in the order of traversal.
   */
  private final class VerticesIterator extends BaseIterator implements Iterator<V> {
    @Override
    public V next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      V vertexToReturn = nextVertex;
      advanceTraversal(); // Prepare for the next call.
      return vertexToReturn;
    }
  }

  /**
   * Returns an iterator over the vertices in the traversal order.
   */
  public Iterator<V> verticesIterator() {
    return new VerticesIterator();
  }

  /**
   * Returns an {@code Iterable} of the vertices in the traversal order.
   */
  public Iterable<V> vertices() {
    return this::verticesIterator;
  }

  /**
   * An iterator that yields the paths from the source to each vertex in the order of traversal.
   */
  private final class PathsIterator extends BaseIterator implements Iterator<List<V>> {

    /**
     * Reconstructs the path from the initial traversal source to a given vertex
     * by backtracking using the `sources` map.
     */
    private List<V> pathTo(V vertex) {
      List<V> path = JDKArrayList.empty();
      V current = vertex;
      // Backtrack from vertex to the source, which points to itself.
      while (current != source) {
        path.prepend(current);
        current = sources.valueOf(current);
      }
      path.prepend(source); // Add the source at the beginning.
      return path;
    }

    @Override
    public List<V> next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      List<V> pathToReturn = pathTo(nextVertex);
      advanceTraversal(); // Prepare for the next call.
      return pathToReturn;
    }
  }

  /**
   * Returns an iterator over the paths from the source to each vertex.
   */
  public Iterator<List<V>> pathsIterator() {
    return new PathsIterator();
  }

  /**
   * Returns an {@code Iterable} of the paths from the source to each vertex.
   */
  public Iterable<List<V>> paths() {
    return this::pathsIterator;
  }
}