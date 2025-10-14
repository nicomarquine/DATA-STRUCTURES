package org.uma.ed.datastructures.tree;

import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;
import org.uma.ed.datastructures.queue.JDKQueue;
import org.uma.ed.datastructures.queue.Queue;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * A utility class providing static methods for common operations on binary trees.
 * <p>
 * A binary tree is represented by its root {@link Node}. An empty tree is represented by a
 * {@code null} reference. The methods in this class are typically recursive and demonstrate
 * classic tree traversal and processing algorithms.
 * <p>
 * This is a utility class and is not meant to be instantiated.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public final class BinaryTree {

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private BinaryTree() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Represents a node in a binary tree. Each node contains an element and references
   * to its left and right children.
   *
   * @param <E> the type of the element stored in the node.
   */
  public static final class Node<E> {
    private final E element;
    private final Node<E> left;
    private final Node<E> right;

    /**
     * Creates a new leaf node with a given element and no children.
     *
     * @param element the element to be stored in the node.
     */
    public Node(E element) {
      this(element, null, null);
    }

    /**
     * Creates a new node with a given element and references to left and right children.
     *
     * @param element the element to be stored in the node.
     * @param left    the left child node.
     * @param right   the right child node.
     */
    public Node(E element, Node<E> left, Node<E> right) {
      this.element = element;
      this.left = left;
      this.right = right;
    }

    /**
     * Factory method to create a new leaf node.
     *
     * @param <T>     the type of the element.
     * @param element the element to be stored in the node.
     * @return a new leaf {@code Node} with the given element.
     */
    public static <T> Node<T> of(T element) {
      return new Node<>(element);
    }

    /**
     * Factory method to create a new node with children.
     *
     * @param <T>     the type of the element.
     * @param element the element to be stored in the node.
     * @param left    the left child node.
     * @param right   the right child node.
     * @return a new {@code Node} with the given element and children.
     */
    public static <T> Node<T> of(T element, Node<T> left, Node<T> right) {
      return new Node<>(element, left, right);
    }
  }

  /**
   * Computes the number of nodes in a binary tree.
   *
   * @param <T>  the type of elements in the tree.
   * @param root the root node of the tree. An empty tree is represented by {@code null}.
   * @return the total number of nodes in the tree.
   */
  public static <T> int size(Node<T> root) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  /**
   * Computes the height of a binary tree.
   * <p>
   * The height of an empty tree is defined as 0. The height of a non-empty tree is
   * 1 plus the maximum height of its left and right subtrees.
   *
   * @param <T>  the type of elements in the tree.
   * @param root the root node of the tree.
   * @return the height of the tree.
   */
  public static <T> int height(Node<T> root) {throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Computes the sum of all elements in a binary tree of integers.
   *
   * @param root the root node of the tree.
   * @return the sum of all integer elements in the tree.
   */
  public static int sum(Node<Integer> root) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Finds the maximum element in a binary tree.
   *
   * @param <T>        the type of elements in the tree.
   * @param root       the root node of the tree.
   * @param comparator the {@code Comparator} used to compare elements.
   * @return the maximum element in the tree.
   * @throws NoSuchElementException if the tree is empty.
   */
  public static <T> T maximum(Node<T> root, Comparator<T> comparator) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Counts the number of occurrences of a specific element in a binary tree.
   *
   * @param <T>     the type of elements in the tree.
   * @param root    the root node of the tree.
   * @param element the element to count.
   * @return the number of times the element appears in the tree.
   */
  public static <T> int count(Node<T> root, T element) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Returns a list containing all the leaf elements of a binary tree.
   *
   * @param <T>  the type of elements in the tree.
   * @param root the root node of the tree.
   * @return a {@code List} of the leaf elements.
   */
  public static <T> List<T> leaves(Node<T> root) {
    List<T> leavesList = JDKArrayList.empty();
    collectLeaves(root, leavesList);
    return leavesList;
  }

  /**
   * Helper method to recursively collect leaf elements.
   *
   * @param node       the current node being visited.
   * @param leavesList the list where leaf elements are collected.
   */
  private static <T> void collectLeaves(Node<T> node, List<T> leavesList) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Performs a preorder traversal of a binary tree.
   *
   * @param <T>  the type of elements in the tree.
   * @param root the root node of the tree.
   * @return a {@code List} containing the elements in preorder sequence.
   */
  public static <T> List<T> preorder(Node<T> root) {
    List<T> traversal = JDKArrayList.empty();
    buildPreorder(root, traversal);
    return traversal;
  }

  private static <T> void buildPreorder(Node<T> node, List<T> traversal) {throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Performs a postorder traversal of a binary tree.
   *
   * @param <T>  the type of elements in the tree.
   * @param root the root node of the tree.
   * @return a {@code List} containing the elements in postorder sequence.
   */
  public static <T> List<T> postorder(Node<T> root) {
    List<T> traversal = JDKArrayList.empty();
    buildPostorder(root, traversal);
    return traversal;
  }

  private static <T> void buildPostorder(Node<T> node, List<T> traversal) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Performs an inorder traversal of a binary tree.
   *
   * @param <T>  the type of elements in the tree.
   * @param root the root node of the tree.
   * @return a {@code List} containing the elements in inorder sequence.
   */
  public static <T> List<T> inorder(Node<T> root) {
    List<T> traversal = JDKArrayList.empty();
    buildInorder(root, traversal);
    return traversal;
  }

  private static <T> void buildInorder(Node<T> node, List<T> traversal) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Performs a breadth-first (level-order) traversal of a binary tree.
   *
   * @param <T>  the type of elements in the tree.
   * @param root the root node of the tree.
   * @return a {@code List} containing the elements in breadth-first sequence.
   */
  public static <T> List<T> breadthFirst(Node<T> root) { throw new UnsupportedOperationException("Not implemented yet"); }
}