package org.uma.ed.datastructures.heap;

import java.util.Comparator;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.queue.JDKQueue;

/**
 * An implementation of the {@link Heap} interface using a maxiphobic heap.
 * <p>
 * A maxiphobic heap is a type of mergeable heap implemented as a binary tree, first
 * described by Chris Okasaki. It maintains the standard min-heap property (a node is
 * smaller than or equal to its children).
 * <p>
 * Its key feature is its elegant and simple merging strategy. When merging two heaps
 * {@code H1} and {@code H2}, the smaller of the two roots becomes the new root.
 * Then, there are three candidate subtrees to become the children of this new root:
 * the two children of the original root, and the entire other heap. The maxiphobic
 * ("biggest-avoiding") strategy dictates that:
 * <ol>
 *     <li>The largest of these three subtrees (by number of nodes) is kept as one child.</li>
 *     <li>The other two (smaller) subtrees are recursively merged to become the other child.</li>
 * </ol>
 * This strategy leads to logarithmic time for {@code insert} and {@code deleteMinimum} operations.
 *
 * @param <T> Type of elements in heap.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class MaxiphobicHeapPractice<T> implements Heap<T> {
  /**
   * Internal class representing a node in the maxiphobic heap tree.
   */
  private static final class Node<E> {
    E element;
    int weight; // The number of nodes in the subtree rooted at this node (size of subtree).
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
   * - Min-Heap Property: For any node, its element is less than or equal to the elements
   *   in its left and right children.
   * - The structure is maintained by the maxiphobic merge algorithm.
   */

  /**
   * Comparator used to order elements in heap.
   */
  private final Comparator<T> comparator;

  /**
   * Reference to root of this heap.
   */
  private Node<T> root;

  private MaxiphobicHeapPractice(Comparator<T> comparator, Node<T> root) {
    this.comparator = comparator;
    this.root = root;
  }

  /**
   * Creates an empty Maxiphobic Heap.
   * <p> Time complexity: O(1)
   */
  public MaxiphobicHeapPractice(Comparator<T> comparator) {
    this(comparator, null);
  }

  /**
   * Creates an empty Maxiphobic Heap with provided comparator.
   * <p> Time complexity: O(1)
   * @param comparator comparator to use.
   * @param <T> type of elements.
   *
   * @return empty maxiphobic heap with provided comparator.
   */
  public static <T> MaxiphobicHeapPractice<T> empty(Comparator<T> comparator) {
    return new MaxiphobicHeapPractice<>(comparator);
  }

  /**
   * Creates an empty Maxiphobic Heap with natural order comparator.
   * <p> Time complexity: O(1)
   * @param <T> type of elements.
   *
   * @return empty maxiphobic heap with natural order comparator.
   */
  public static <T extends Comparable<? super T>> MaxiphobicHeapPractice<T> empty() {
    return new MaxiphobicHeapPractice<T>(Comparator.naturalOrder());
  }

  /**
   * Constructs a Maxiphobic Heap from a list of singleton nodes in O(n) time.
   * @param comparator comparator to use
   * @param nodes list of singleton nodes
   * @param <T> type of elements
   *
   * @return skew heap with elements in nodes
   */
  private static <T> MaxiphobicHeapPractice<T> merge(Comparator<T> comparator, JDKArrayList<Node<T>> nodes) {
    MaxiphobicHeapPractice<T> heap = new MaxiphobicHeapPractice<>(comparator);
    JDKQueue<MaxiphobicHeapPractice.Node<T>> queue = JDKQueue.from(nodes); // Alternative to comment at bottom
     /*JDKQueue<Node<T>> queue = JDKQueue.empty();
     for(Node<T> node: nodes){
         queue.enqueue(node);
     } */
    if(!nodes.isEmpty()) {
      while (queue.size() > 1) {
        MaxiphobicHeapPractice.Node<T> tree1 = queue.first();
        queue.dequeue();

        MaxiphobicHeapPractice.Node<T> tree2 = queue.first();
        queue.dequeue();
        MaxiphobicHeapPractice.Node<T> tree3 = heap.merge(tree1, tree2);
        queue.enqueue(tree3);
      }
      heap.root = queue.first();
    }
    return heap;
  }

  /**
   * Constructs a Maxiphobic Heap from a sequence of elements and a comparator.
   * <p>
   * Time complexity: O(n)
   * @param comparator comparator to use
   * @param elements elements to insert in heap
   * @param <T> type of elements
   *
   * @return maxiphobic heap with elements in sequence
   */
  @SafeVarargs
  public static <T> MaxiphobicHeapPractice<T> of(Comparator<T> comparator, T... elements) {
    JDKArrayList<Node<T>> nodes = JDKArrayList.withCapacity(elements.length);
    for (T element : elements) {
      nodes.append(new Node<>(element));
    }
    return merge(comparator, nodes);
  }

  /**
   * Constructs a Maxiphobic Heap from a sequence of elements and natural order comparator.
   * <p>
   * Time complexity: O(n)
   * @param elements elements to insert in heap
   * @param <T> type of elements
   *
   * @return maxiphobic heap with elements in sequence
   */
  @SafeVarargs
  public static <T extends Comparable<? super T>> MaxiphobicHeapPractice<T> of(T... elements) {
    return of(Comparator.naturalOrder(), elements);
  }

  /**
   * Constructs a Maxiphobic Heap from an iterable of elements and a comparator.
   * <p>
   * Time complexity: O(n)
   * @param comparator comparator to use
   * @param iterable elements to insert in heap
   * @param <T> type of elements
   *
   * @return maxiphobic heap with elements in iterable
   */
  public static <T> MaxiphobicHeapPractice<T> from(Comparator<T> comparator, Iterable<T> iterable) {
    JDKArrayList<Node<T>> nodes = JDKArrayList.empty();
    for (T element : iterable) {
      nodes.append(new Node<>(element));
    }
    return merge(comparator, nodes);
  }

  /**
   * Constructs a Maxiphobic Heap from an iterable of elements and natural order comparator.
   * <p>
   * Time complexity: O(n)
   * @param iterable elements to insert in heap
   * @param <T> type of elements
   *
   * @return maxiphobic heap with elements in iterable
   */
  public static <T extends Comparable<? super T>> MaxiphobicHeapPractice<T> from(Iterable<T> iterable) {
    return from(Comparator.naturalOrder(), iterable);
  }

  /**
   * Creates a new {@code MaxiphobicHeapPractice} containing the same elements as the given {@code MaxiphobicHeapPractice}.
   * <p> Time complexity: O(n)
   */
  public static <T> MaxiphobicHeapPractice<T> copyOf(MaxiphobicHeapPractice<T> that) {
    return new MaxiphobicHeapPractice<>(that.comparator, copyOf(that.root));
  }

  // copies a tree
  private static <T> Node<T> copyOf(Node<T> node) {
    if(node == null){
      return null;
    } else {
      return new MaxiphobicHeapPractice.Node<>(node.element,node.weight,copyOf(node.left),copyOf(node.right));

    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public Comparator<T> comparator() {
    return comparator;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public boolean isEmpty() {
    return root == null;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public int size() {
    return weight(root);
  }

  private static int weight(Node<?> node) {
    return node == null ? 0 : node.weight;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void clear() {
    root = null;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public void insert(T element) {
    MaxiphobicHeapPractice.Node<T> singleton = new MaxiphobicHeapPractice.Node<>(element);
    root = merge(root,singleton);
  }

  /**
   * Merges two maxiphobic heaps.
   */
  private Node<T> merge(Node<T> node1, Node<T> node2) {
    if(node1 == null){
      return node2;
    }
    if(node2 == null){
      return node1;
    }

    // recursive case: both trees are non-empty
    // compare roots and if root of node1 is larger swap trees

    if(comparator.compare(node1.element,node2.element) > 0){
      MaxiphobicHeapPractice.Node<T> aux = node1;
      node1 = node2;
      node2 = aux;
    }

    // now node 1 is holding the minimum element at its root

    // update the weight
    node1.weight += node2.weight;

    MaxiphobicHeapPractice.Node<T> tree1 = node1.left;
    MaxiphobicHeapPractice.Node<T> tree2 = node1.right;
    MaxiphobicHeapPractice.Node<T> tree3 = node2;

    if(weight(tree1)<weight(tree2)){
      MaxiphobicHeapPractice.Node<T> temp = tree1;
      tree1 = tree2;
      tree2 = temp;
    }

    // we know weight of tree1 > weight of tree2

    // compare weights of tree1 and tree3 and swap tree3 if larger
    if(weight(tree1) < weight(tree3)){
      MaxiphobicHeapPractice.Node<T> temp = tree1;
      tree1 = tree3;
      tree3 = temp;
    }

    // We know weight(tree1) is the biggest out of the 3 trees
    node1.left = tree1;
    node1.right = merge(tree2,tree3);
    return node1;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   *
   * @throws <code>EmptyHeapException</code> if heap stores no element.
   */
  @Override
  public T minimum() {
    if(isEmpty()){
      throw new EmptyHeapException("minimum on empty heap");
    }
    return root.element;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   *
   * @throws <code>EmptyHeapException</code> if heap stores no element.
   */
  @Override
  public void deleteMinimum() {
    if(isEmpty()){
      throw new EmptyHeapException("deleteMinimum on empty heap");
    }
    root = merge(root.left,root.right);
  }

  /**
   * Returns representation of this heap as a String.
   */
  @Override
  public String toString() {
    String className = getClass().getSimpleName();
    StringBuilder sb = new StringBuilder();
    sb.append(className).append("(");
    toString(sb, root);
    sb.append(")");
    return sb.toString();
  }

  /**
   * Helper method for the toString method. It recursively builds a string representation of the heap.
   *
   * @param sb The StringBuilder to append to.
   * @param node The node to represent.
   */
  private static void toString(StringBuilder sb, Node<?> node) {
    if (node == null) {
      sb.append("null");
    } else {
      String className = node.getClass().getSimpleName();
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