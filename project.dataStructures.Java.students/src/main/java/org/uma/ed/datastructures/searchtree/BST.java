package org.uma.ed.datastructures.searchtree;

import org.uma.ed.datastructures.either.Either;
import org.uma.ed.datastructures.stack.JDKStack;
import org.uma.ed.datastructures.stack.Stack;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of the {@link SearchTree} interface using an unbalanced
 * binary search tree.
 * <p>
 * This implementation stores keys in a sorted manner, but does not perform any
 * balancing operations. As a result, the performance of most operations (like
 * {@code insert}, {@code search}, {@code delete}) is O(log n) on average for randomly
 * inserted data, but can degrade to O(n) in the worst case (e.g., when keys are
 * inserted in sorted or reverse-sorted order).
 *
 * @param <K> The type of keys maintained by this search tree.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class BST<K> implements SearchTree<K> {

  /**
   * Internal class representing a node in the binary search tree.
   */
  private static final class Node<K> {
    K key;
    Node<K> left, right;

    Node(K key) {
      this.key = key;
      this.left = null;
      this.right = null;
    }
  }

  /*
   * INVARIANT:
   *  - The `root` points to the root of the tree, or is null if the tree is empty.
   *  - For any given node `n`, all keys in its left subtree are smaller than `n.key`.
   *  - For any given node `n`, all keys in its right subtree are greater than `n.key`.
   *  - There are no duplicate keys in the tree.
   *  - `size` is the total number of nodes in the tree.
   */

  private final Comparator<K> comparator;
  private Node<K> root;
  private int size;

  /**
   * Private constructor for internal use.
   */
  private BST(Comparator<K> comparator, Node<K> root, int size) {
    this.comparator = comparator;
    this.root = root;
    this.size = size;
  }

  /**
   * Constructs an empty {@code BST} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public BST(Comparator<K> comparator) {
    this(comparator, null, 0);
  }

  /**
   * Creates an empty {@code BST} with natural ordering.
   * <p> Time complexity: O(1)
   */
  public static <K extends Comparable<? super K>> BST<K> empty() {
    return new BST<K>(Comparator.naturalOrder());
  }

  /**
   * Creates an empty {@code BST} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public static <K> BST<K> empty(Comparator<K> comparator) {
    return new BST<>(comparator);
  }

  /**
   * Creates a new {@code BST} that is a copy of the given search tree.
   * <p>
   * Note: Using preorder traversal for copying helps to create a more balanced
   * tree than an inorder traversal, though it does not guarantee balance.
   * <p> Time complexity: O(n^2) in the worst case, due to repeated insertions.
   */
  public static <K> BST<K> copyOf(SearchTree<K> that) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Creates a new {@code BST} that is an exact structural copy of the given {@code BST}.
   * <p> Time complexity: O(n), where n is the number of nodes.
   */
  public static <K> BST<K> copyOf(BST<K> that) {
    return new BST<>(that.comparator, copyOf(that.root), that.size);
  }

  private static <K> Node<K> copyOf(Node<K> node) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1).
   */
  @Override
  public void clear() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1).
   */
  @Override
  public Comparator<K> comparator() {
    return comparator;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1).
   */
  @Override
  public boolean isEmpty() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1).
   */
  @Override
  public int size() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n) in the worst case.
   */
  @Override
  public int height() {
    return height(root);
  }

  private static int height(Node<?> node) {
    return node == null ? 0 : 1 + Math.max(height(node.left), height(node.right));
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n) on average, O(n) in the worst case.
   */
  @Override
  public void insert(K key) {
    root = insert(root, key);
  }

  private Node<K> insert(Node<K> node, K key) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n) on average, O(n) in the worst case.
   */
  @Override
  public K search(K key) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n) on average, O(n) in the worst case.
   */
  @Override
  public boolean contains(K key) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n) on average, O(n) in the worst case.
   */
  @Override
  public void delete(K key) {
    root = delete(root, key);
  }

  // returns modified tree
  private Node<K> delete(Node<K> node, K key) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Precondition: node is a non-empty tree. Removes minimum key from tree rooted at node. Before deletion, key is saved
   * into temp node. Returns modified tree (without min key).
   */
  private static <K> Node<K> split(Node<K> node, Node<K> temp) {
    if (node.left == null) {
      // min node found, so copy min key
      temp.key = node.key;
      return node.right; // remove node
    } else {
      // remove min from left subtree
      node.left = split(node.left, temp);
      return node;
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n) on average, O(n) in the worst case.
   */
  @Override
  public K minimum() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n) on average, O(n) in the worst case.
   */
  @Override
  public K maximum() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n) on average, O(n) in the worst case.
   */
  @Override
  public void deleteMinimum() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n) on average, O(n) in the worst case.
   */
  @Override
  public void deleteMaximum() { throw new UnsupportedOperationException("Not implemented yet"); }

  // An abstract base for traversal iterators
  private abstract class Traversal implements Iterator<K> {
    final Stack<Either<Node<K>, Node<K>>> stack = JDKStack.empty();

    public Traversal() {
      if (root != null) {
        pushTree(root);
      }
    }

    abstract void pushTree(Node<K> node);

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public K next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Either<Node<K>, Node<K>> either = stack.top();
      stack.pop();
      while (either.isRight()) {
        Node<K> node = either.right();
        pushTree(node);
        either = stack.top();
        stack.pop();
      }
      return either.left().key;
    }
  }

  private final class InOrderIterator extends Traversal {
    void pushTree(Node<K> node) {
      if (node.right != null) stack.push(Either.right(node.right));
      stack.push(Either.left(node));
      if (node.left != null) stack.push(Either.right(node.left));
    }
  }

  private final class PreOrderIterator extends Traversal {
    void pushTree(Node<K> node) {
      if (node.right != null) stack.push(Either.right(node.right));
      if (node.left != null) stack.push(Either.right(node.left));
      stack.push(Either.left(node));
    }
  }

  private final class PostOrderIterator extends Traversal {
    void pushTree(Node<K> node) {
      stack.push(Either.left(node));
      if (node.right != null) stack.push(Either.right(node.right));
      if (node.left != null) stack.push(Either.right(node.left));
    }
  }

  @Override
  public Iterable<K> inOrder() { return InOrderIterator::new; }

  @Override
  public Iterable<K> preOrder() { return PreOrderIterator::new; }

  @Override
  public Iterable<K> postOrder() { return PostOrderIterator::new; }

  /**
   * Returns a string representation of the tree structure for debugging.
   */
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
      sb.append("Node(");
      toString(sb, node.left);
      sb.append(", ");
      sb.append(node.key);
      sb.append(", ");
      toString(sb, node.right);
      sb.append(")");
    }
  }
}