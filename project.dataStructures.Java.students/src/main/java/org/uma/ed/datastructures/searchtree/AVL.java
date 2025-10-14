package org.uma.ed.datastructures.searchtree;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import org.uma.ed.datastructures.either.Either;
import org.uma.ed.datastructures.stack.JDKStack;
import org.uma.ed.datastructures.stack.Stack;

/**
 * An implementation of the {@link SearchTree} interface using a balanced AVL tree.
 * <p>
 * An AVL tree is a self-balancing binary search tree. It maintains its balance by ensuring
 * that for any node, the heights of its two child subtrees differ by at most one. This
 * property guarantees that operations like {@code insert}, {@code search}, and {@code delete}
 * have a worst-case time complexity of O(log n).
 * <p>
 * The balance is maintained through tree rotations (single and double) performed after
 * insertions or deletions that violate the height property.
 *
 * @param <K> Type of keys.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class AVL<K> implements SearchTree<K> {

  /**
   * Internal class representing a node in the AVL tree.
   * Each node stores a key, its height, and references to its children.
   */
  private static final class Node<K> {
    K key;
    int height;
    Node<K> left, right;

    Node(K key) {
      this.key = key;
      this.height = 1;
      this.left = null;
      this.right = null;
    }

    /**
     * Returns the height of a subtree rooted at the given node.
     * The height of a null (empty) subtree is defined as 0.
     */
    static int height(Node<?> node) {
      return node == null ? 0 : node.height;
    }

    /**
     * Recalculates and sets the height of this node.
     * The height is 1 plus the maximum height of its two children.
     */
    void setHeight() {
      height = 1 + Math.max(height(left), height(right));
    }

    /**
     * Calculates the balance factor of a node (height of left subtree minus height of right subtree).
     * A positive value means the node is left-heavy, a negative value means it is right-heavy.
     * For a valid AVL tree, this value must be in the range [-1, 1].
     */
    static int balance(Node<?> node) {
      return node == null ? 0 : height(node.left) - height(node.right);
    }

    /*
     * INVARIANT:
     * - Standard Binary Search Tree property holds (left child < parent < right child).
     * - No duplicate keys.
     * - For every node, the absolute difference between the heights of its left and right
     *   subtrees (the balance factor) is at most 1.
     */

    /**
     * A utility method to check if all keys in a subtree satisfy a given predicate.
     */
    static <K> boolean all(Predicate<K> p, Node<K> node) {
      if (node == null) {
        return true;
      } else {
        return (p.test(node.key) && all(p, node.left) && all(p, node.right));
      }
    }

    /**
     * A utility method to verify if a tree rooted at this node is a valid AVL tree.
     * Used for testing and debugging.
     */
    static <K> boolean isAVL(final Node<K> node, Comparator<K> comparator) {
      if (node == null) {
        return true;
      } else {
        return (Math.abs(balance(node)) <= 1)
            && all(k -> comparator.compare(k, node.key) < 0, node.left)
            && all(k -> comparator.compare(k, node.key) > 0, node.right)
            && isAVL(node.left, comparator) && isAVL(node.right, comparator);
      }
    }

    /**
     * Performs a single right rotation on this node.
     * @return The new root of the rotated subtree.
     */
    Node<K> rightRotated() {
      Node<K> left = this.left;
      this.left = left.right;
      this.setHeight();
      left.right = this;
      left.setHeight();
      return left;
    }

    /**
     * Performs a single left rotation on this node.
     * @return The new root of the rotated subtree.
     */
    Node<K> leftRotated() {
      Node<K> right = this.right;
      this.right = right.left;
      this.setHeight();
      right.left = this;
      right.setHeight();
      return right;
    }

    /**
     * Restores the AVL balance property for this node if it is violated.
     * This method checks the balance factor and applies the necessary single or double rotations.
     * @return The root of the balanced subtree.
     */
    Node<K> balanced() {
      int balance = balance(this);
      Node<K> balanced;
      if (balance > 1) { // Left-heavy
        if (balance(left) < 0) { // Left-Right case (double rotation)
          left = left.leftRotated();
        }
        // Left-Left case (single rotation)
        balanced = this.rightRotated();
      } else if (balance < -1) { // Right-heavy
        if (balance(right) > 0) { // Right-Left case (double rotation)
          right = right.rightRotated();
        }
        // Right-Right case (single rotation)
        balanced = this.leftRotated();
      } else { // Already balanced
        balanced = this;
        balanced.setHeight(); // Height might still need update
      }
      return balanced;
    }
  }

  private final Comparator<K> comparator;
  private Node<K> root;
  private int size;

  /**
   * Private constructor for internal use by factory and copy methods.
   */
  private AVL(Comparator<K> comparator, Node<K> root, int size) {
    this.comparator = comparator;
    this.root = root;
    this.size = size;
  }

  /**
   * Constructs an empty {@code AVL} tree with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public AVL(Comparator<K> comparator) {
    this(comparator, null, 0);
  }

  /**
   * Creates an empty {@code AVL} tree with natural ordering.
   * <p> Time complexity: O(1)
   */
  public static <K extends Comparable<? super K>> AVL<K> empty() {
    return new AVL<K>(Comparator.naturalOrder());
  }

  /**
   * Creates an empty {@code AVL} tree with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public static <K> AVL<K> empty(Comparator<K> comparator) {
    return new AVL<>(comparator);
  }

  /**
   * Creates a new {@code AVL} tree from a generic {@code SearchTree}.
   * <p> Time complexity: O(n log n), due to repeated insertions.
   */
  public static <K> AVL<K> copyOf(SearchTree<K> that) {
    if (that instanceof AVL<K> avl) {
      return copyOf(avl);
    }
    AVL<K> copy = new AVL<>(that.comparator());
    for (K key : that.preOrder()) {
      copy.insert(key);
    }
    return copy;
  }

  /**
   * Creates a new {@code AVL} tree that is an exact structural copy of the given {@code AVL}.
   * <p> Time complexity: O(n)
   */
  public static <K> AVL<K> copyOf(AVL<K> that) {
    return new AVL<>(that.comparator, copyOf(that.root), that.size);
  }

  private static <K> Node<K> copyOf(Node<K> node) {
    if (node == null) return null;
    Node<K> copy = new Node<>(node.key);
    copy.height = node.height;
    copy.left = copyOf(node.left);
    copy.right = copyOf(node.right);
    return copy;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1).
   */
  @Override
  public void clear() {
    root = null;
    size = 0;
  }

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
  public boolean isEmpty() {
    return root == null;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1).
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1).
   */
  @Override
  public int height() {
    return Node.height(root);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public void insert(K key) { root = insert(root, key); }

  /**
   * Internal recursive method to insert a key and rebalance the tree on the way up.
   */
  private Node<K> insert(Node<K> node, K key) {
    if (node == null) {
      size++;
      return new Node<>(key);
    }
    int cmp = comparator.compare(key, node.key);
    if (cmp < 0) {
      node.left = insert(node.left, key);
    } else if (cmp > 0) {
      node.right = insert(node.right, key);
    } else {
      node.key = key; // Replace existing key
    }
    return node.balanced(); // Rebalance and update height
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public K search(K key) {
    Node<K> node = root;
    while (node != null) {
      int cmp = comparator.compare(key, node.key);
      if (cmp < 0) {
        node = node.left;
      } else if (cmp > 0) {
        node = node.right;
      } else {
        return node.key; // Found
      }
    }
    return null; // Not found
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public boolean contains(K key) {
    return search(key) != null;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public void delete(K key) { root = delete(root, key); }

  /**
   * Internal recursive method to delete a key and rebalance the tree on the way up.
   */
  private Node<K> delete(Node<K> node, K key) {
    if (node == null) {
      // Key not found. Nothing to delete.
    } else {
      int cmp = comparator.compare(key, node.key);
      if (cmp < 0) {
        node.left = delete(node.left, key);
      } else if (cmp > 0) {
        node.right = delete(node.right, key);
      } else {
        // Node to delete found
        node = deleteNode(node);
      }
    }
    // If node became null, there's nothing to balance.
    return (node == null) ? null : node.balanced();
  }

  /**
   * Handles the actual deletion of a node once it's found.
   * @param node The node to delete.
   * @return The node that replaces the deleted node in the tree structure.
   */
  private Node<K> deleteNode(Node<K> node) {
    if (node.left == null) {
      node = node.right;
    } else if (node.right == null) {
      node = node.left;
    } else {
      // Node has two children: replace with inorder successor.
      node.right = split(node.right, node);
    }
    size--;
    return node;
  }

  /**
   * Helper for deletion: finds and removes the minimum key in a subtree.
   * The key of the minimum node is copied into `temp`, and the node itself is removed.
   * @param node The root of the subtree to search within.
   * @param temp A node whose key will be overwritten with the minimum key found.
   * @return The modified subtree after removing the minimum node.
   */
  private static <K> Node<K> split(Node<K> node, Node<K> temp) {
    if (node.left == null) {
      temp.key = node.key; // Copy the minimum key
      return node.right;   // Remove the minimum node
    } else {
      node.left = split(node.left, temp);
      return node.balanced();
    }
  }

  /**
   * A utility method to check if the tree rooted at this instance is a valid AVL tree.
   * Primarily for testing and debugging.
   */
  public boolean isAVL() {
    return Node.isAVL(root, comparator);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public K minimum() {
    if (root == null) {
      throw new EmptySearchTreeException("minimum on empty tree");
    }
    Node<K> node = root;
    while (node.left != null) {
      node = node.left;
    }
    return node.key;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public K maximum() {
    if (root == null) {
      throw new EmptySearchTreeException("maximum on empty tree");
    }
    Node<K> node = root;
    while (node.right != null) {
      node = node.right;
    }
    return node.key;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public void deleteMinimum() {
    if (isEmpty()) {
      throw new EmptySearchTreeException("deleteMinimum on empty tree");
    }
    root = deleteMinimum(root);
    size--;
  }

  private static <K> Node<K> deleteMinimum(Node<K> node) {
    if (node.left == null) {
      return node.right;
    }
    node.left = deleteMinimum(node.left);
    return node.balanced();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public void deleteMaximum() {
    if (isEmpty()) {
      throw new EmptySearchTreeException("deleteMaximum on empty tree");
    }
    root = deleteMaximum(root);
    size--;
  }

  private static <K> Node<K> deleteMaximum(Node<K> node) {
    if (node.right == null) {
      return node.left;
    }
    node.right = deleteMaximum(node.right);
    return node.balanced();
  }

  // Almost an iterator on keys in tree
  private abstract class Traversal implements Iterator<K> {
    Stack<Either<Node<K>, Node<K>>> stack = JDKStack.empty();

    public Traversal() {
      if (root != null) {
        pushTree(root);
      }
    }

    abstract void pushTree(Node<K> node);

    public boolean hasNext() {
      return !stack.isEmpty();
    }

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
      // in reverse order, cause stack is LIFO
      if (node.right != null) {
        stack.push(Either.right(node.right));
      }
      stack.push(Either.left(node));
      if (node.left != null) {
        stack.push(Either.right(node.left));
      }
    }
  }

  private final class PreOrderIterator extends Traversal {
    void pushTree(Node<K> node) {
      // in reverse order, cause stack is LIFO
      if (node.right != null) {
        stack.push(Either.right(node.right));
      }
      if (node.left != null) {
        stack.push(Either.right(node.left));
      }
      stack.push(Either.left(node));
    }
  }

  private final class PostOrderIterator extends Traversal {
    void pushTree(Node<K> node) {
      // in reverse order, cause stack is LIFO
      stack.push(Either.left(node));
      if (node.right != null) {
        stack.push(Either.right(node.right));
      }
      if (node.left != null) {
        stack.push(Either.right(node.left));
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterable<K> inOrder() {
    return InOrderIterator::new;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterable<K> preOrder() {
    return PreOrderIterator::new;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterable<K> postOrder() {
    return PostOrderIterator::new;
  }

  /**
   * Returns representation of this search tree as a String.
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
      String className = node.getClass().getSimpleName();
      sb.append(className).append("(");
      toString(sb, node.left);
      sb.append(", ");
      sb.append(node.key);
      sb.append(", ");
      toString(sb, node.right);
      sb.append(")");
    }
  }
}
