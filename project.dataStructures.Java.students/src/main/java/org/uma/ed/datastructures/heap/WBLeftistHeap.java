package org.uma.ed.datastructures.heap;

import java.util.Comparator;
import org.uma.ed.datastructures.list.JDKArrayList;

/**
 * An implementation of the {@link Heap} interface using a weight-biased leftist heap.
 * <p>
 * A weight-biased leftist heap is a type of mergeable heap implemented as a binary tree.
 * It maintains two key properties:
 * <ol>
 *     <li><b>Min-Heap Property:</b> The element in any node is less than or equal to the
 *         elements in its children.</li>
 *     <li><b>Weight-Biased Leftist Property:</b> For any node, the weight (number of nodes)
 *         of its left subtree is greater than or equal to the weight of its right subtree.</li>
 * </ol>
 * The leftist property ensures that the right spine of the tree is short, which guarantees
 * that the core {@code merge} operation has a logarithmic time complexity O(log n).
 *
 * @param <T> Type of elements in heap.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class WBLeftistHeap<T> implements Heap<T> {

  /**
   * Internal class representing a node in the leftist heap tree.
   */
  private static final class Node<E> {
    E element;
    int weight; // The number of nodes in the subtree rooted at this node.
    Node<E> left, right;

    Node(E element, int weight, Node<E> left, Node<E> right) {
      this.element = element;
      this.weight = weight;
      this.left = left;
      this.right = right;
    }

    // Constructor for a singleton node (a leaf).
    Node(E element) {
      this(element, 1, null, null);
    }
  }

  /*
   * INVARIANT:
   * - Min-Heap Property: For any node, its element is <= its children's elements.
   * - Weight-Biased Leftist Property: For any node `x`, `weight(x.left) >= weight(x.right)`.
   *   This keeps the right spine short.
   */

  /**
   * Comparator used to order elements in the heap.
   */
  private final Comparator<T> comparator;

  /**
   * Reference to the root node of this heap.
   */
  private Node<T> root;

  /**
   * Private constructor for internal use.
   */
  private WBLeftistHeap(Comparator<T> comparator, Node<T> root) {
    this.comparator = comparator;
    this.root = root;
  }

  /**
   * Constructs an empty {@code WBLeftistHeap} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public WBLeftistHeap(Comparator<T> comparator) {
    this(comparator, null);
  }

  /**
   * Creates an empty {@code WBLeftistHeap} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public static <T> WBLeftistHeap<T> empty(Comparator<T> comparator) {
    return new WBLeftistHeap<>(comparator);
  }

  /**
   * Creates an empty {@code WBLeftistHeap} with natural ordering.
   * <p> Time complexity: O(1)
   */
  public static <T extends Comparable<? super T>> WBLeftistHeap<T> empty() {
    return new WBLeftistHeap<T>(Comparator.naturalOrder());
  }

  /**
   * Creates a new {@code WBLeftistHeap} from the given elements using an efficient O(n) batch-build algorithm.
   * <p> Time complexity: O(n)
   */
  @SafeVarargs
  public static <T> WBLeftistHeap<T> of(Comparator<T> comparator, T... elements) {
    JDKArrayList<Node<T>> nodes = new JDKArrayList<>(elements.length);
    for (T element : elements) {
      nodes.append(new Node<>(element));
    }
    return merge(comparator, nodes);
  }

  /**
   * Creates a new {@code WBLeftistHeap} from the given elements with natural ordering.
   * <p> Time complexity: O(n)
   */
  @SafeVarargs
  public static <T extends Comparable<? super T>> WBLeftistHeap<T> of(T... elements) {
    return of(Comparator.naturalOrder(), elements);
  }

  /**
   * Creates a new {@code WBLeftistHeap} from an iterable using an efficient O(n) batch-build algorithm.
   * <p> Time complexity: O(n)
   */
  public static <T> WBLeftistHeap<T> from(Comparator<T> comparator, Iterable<T> iterable) {
    JDKArrayList<Node<T>> nodes = new JDKArrayList<>();
    for (T element : iterable) {
      nodes.append(new Node<>(element));
    }
    return merge(comparator, nodes);
  }

  /**
   * Creates a new {@code WBLeftistHeap} from an iterable with natural ordering.
   * <p> Time complexity: O(n)
   */
  public static <T extends Comparable<? super T>> WBLeftistHeap<T> from(Iterable<T> iterable) {
    return from(Comparator.naturalOrder(), iterable);
  }

  /**
   * Helper method to build a heap from a list of singleton nodes in O(n).
   */
  private static <T> WBLeftistHeap<T> merge(Comparator<T> comparator, JDKArrayList<Node<T>> nodes) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Creates a new {@code WBLeftistHeap} containing the same elements as the given heap.
   * <p> Time complexity: O(n)
   */
  public static <T> WBLeftistHeap<T> copyOf(WBLeftistHeap<T> that) {
    return new WBLeftistHeap<>(that.comparator, copyOf(that.root));
  }

  private static <T> Node<T> copyOf(Node<T> node) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public Comparator<T> comparator() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public boolean isEmpty() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public int size() { throw new UnsupportedOperationException("Not implemented yet"); }

  private static int weight(Node<?> node) {
    return (node == null) ? 0 : node.weight;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void clear() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public void insert(T element) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * The core merge operation for two leftist heaps.
   *
   * @return The root of the newly merged heap.
   */
  private Node<T> merge(Node<T> node1, Node<T> node2) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public T minimum() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public void deleteMinimum() { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public String toString() {
    String className = getClass().getSimpleName();
    StringBuilder sb = new StringBuilder(className).append("(");
    toString(sb, root);
    sb.append(")");
    return sb.toString();
  }

  private static void toString(StringBuilder sb, Node<?> node) {
    if (node == null) {
      sb.append("null");
    } else {
      String className = "Node";
      sb.append(className).append("(");
      toString(sb, node.left);
      sb.append(", ");
      sb.append(node.element);
      sb.append(", ");
      toString(sb, node.right);
      sb.append(")");
    }
  }
}