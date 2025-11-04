package org.uma.ed.datastructures.tree;

import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;
import org.uma.ed.datastructures.queue.JDKQueue;
import org.uma.ed.datastructures.queue.Queue;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * A utility class providing static methods for common operations on general (multi-way) trees.
 * <p>
 * A tree is represented by its root {@link Node}. An empty tree is represented by a
 * {@code null} reference. Each node contains an element and a list of its children.
 * The methods in this class are typically recursive and demonstrate classic tree algorithms.
 * <p>
 * This is a utility class and is not meant to be instantiated.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public final class Tree {

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private Tree() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Represents a node in a general tree. Each node contains an element and a
   * list of references to its children nodes.
   *
   * @param <E> the type of the element stored in the node.
   */
  public static final class Node<E> {
    private final E element;
    private final List<Node<E>> children;

    /**
     * Creates a new leaf node with a given element and no children.
     *
     * @param element the element to be stored in the node.
     */
    public Node(E element) {
      this.element = element;
      this.children = JDKArrayList.empty();
    }

    /**
     * Factory method to create a new node with a variable number of children.
     *
     * @param <T>      the type of the element.
     * @param element  the element to be stored in the node.
     * @param children a varargs array of child nodes.
     * @return a new {@code Node} with the given element and children.
     */
    @SafeVarargs
    public static <T> Node<T> of(T element, Node<T>... children) {
      Node<T> node = new Node<>(element);
      for (Node<T> child : children) {
        node.children.append(child);
      }
      return node;
    }
  }

  /**
   * Computes the number of nodes in a tree.
   *
   * @param <T>  the type of the element.
   * @param root the root node of the tree. An empty tree is represented by {@code null}.
   * @return the total number of nodes in the tree.
   */
  public static <T> int size(Node<T> root) {
    if(root == null){
      //empty tree
      return 0;
    } else{
      //non empty tree
      int sz = 1; // count element at root
      for(Node<T> child :root.children){
        sz += size(child);
      }
      return sz;
    }
  }

  /**
   * Computes the height of a tree.
   * <p>
   * The height of an empty tree is defined as 0. The height of a non-empty tree is
   * 1 plus the maximum height among all its children's subtrees.
   *
   * @param <T>  the type of elements in the tree.
   * @param root the root node of the tree.
   * @return the height of the tree.
   */
  public static <T> int height(Node<T> root) {
    if(root == null){
      //empty tree
      return 0;
    } else{
      int maxH = 0;
      for(Node<T> child: root.children){
        maxH += Math.max(height(child),maxH);
      }
      return 1 - maxH;
    }
  }

  /**
   * Computes the sum of all elements in a tree of integers.
   *
   * @param root the root node of the tree.
   * @return the sum of all integer elements in the tree.
   */
  public static int sum(Node<Integer> root) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Finds the maximum element in a tree.
   *
   * @param <T>        the type of elements in the tree.
   * @param root       the root node of the tree.
   * @param comparator the {@code Comparator} used to compare elements.
   * @return the maximum element in the tree.
   * @throws NoSuchElementException if the tree is empty.
   */
  public static <T> T maximum(Node<T> root, Comparator<T> comparator) {
    if(root == null){
      throw new NoSuchElementException("No maximum on empty tree");
    } else{
      T maxElement = root.element;
      for(Node<T> child: root.children){
        T maxChild = maximum(child,comparator);
        if(comparator.compare(maxChild,maxElement)>0){
          maxElement = maxChild;
        }
      }
      return maxElement;

    }
  }

  /**
   * Counts the number of occurrences of a specific element in a tree.
   *
   * @param <T>     the type of elements in the tree.
   * @param root    the root node of the tree.
   * @param element the element to count.
   * @return the number of times the element appears in the tree.
   */
  public static <T> int count(Node<T> root, T element) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Returns a list containing all the leaf elements of a tree.
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

  private static <T> void collectLeaves(Node<T> node, List<T> leavesList) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Performs a preorder traversal of a tree.
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

  private static <T> void buildPreorder(Node<T> node, List<T> traversal) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Performs a postorder traversal of a tree.
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
   * Performs a breadth-first (level-order) traversal of a tree.
   *
   * @param <T>  the type of elements in the tree.
   * @param root the root node of the tree.
   * @return a {@code List} containing the elements in breadth-first sequence.
   */
  public static <T> List<T> breadthFirst(Node<T> root) { throw new UnsupportedOperationException("Not implemented yet"); }
}