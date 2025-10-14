package org.uma.ed.datastructures.graph;

import org.uma.ed.datastructures.set.Set;

/**
 * An interface for data structures that can be traversed in a graph-like manner.
 * <p>
 * This interface abstracts the core operation needed for graph traversal algorithms:
 * given an element (or "node"), find all its direct "successors" or neighbors.
 * <p>
 * By implementing this interface, data structures like {@link Graph} and {@link DiGraph}
 * can be processed by generic traversal algorithms (e.g., Breadth-First Search,
 * Depth-First Search) without exposing their internal implementation details.
 *
 * @param <T> The type of the elements (or nodes) in the traversable structure.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public interface Traversable<T> {

  /**
   * Returns a set of all direct successors for a given element in the structure.
   * <p>
   * The meaning of "successor" depends on the specific data structure:
   * <ul>
   *     <li>For an undirected {@link Graph}, successors are the adjacent vertices (neighbors).</li>
   *     <li>For a directed {@link DiGraph}, successors are the vertices reachable via an outgoing edge.</li>
   * </ul>
   *
   * @param element the element for which to find the successors.
   * @return a {@code Set} containing all successor elements.
   * @throws GraphException if the specified element does not exist in the structure.
   */
  Set<T> successors(T element);
}