package org.uma.ed.datastructures.searchtree;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a Search Tree, a data structure that stores a sorted collection of unique keys.
 * <p>
 * This interface defines the contract for a tree-based map-like structure where keys are
 * kept in a sorted order. The sorting is determined either by the keys' natural ordering
 * (if they implement {@link Comparable}) or by a {@link Comparator} provided at creation time.
 * <p>
 * Uniqueness of keys is enforced based on the ordering criterion: two keys {@code k1} and
 * {@code k2} are considered equal if {@code comparator.compare(k1, k2) == 0}.
 *
 * @param <K> The type of the keys maintained by this search tree.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public interface SearchTree<K> {

  /**
   * Returns the comparator used to order the keys in this tree.
   *
   * @return the comparator for this tree.
   */
  Comparator<K> comparator();

  /**
   * Checks if this search tree is empty.
   *
   * @return {@code true} if this tree contains no keys, {@code false} otherwise.
   */
  boolean isEmpty();

  /**
   * Returns the number of keys in this search tree.
   *
   * @return the number of keys in the tree.
   */
  int size();

  /**
   * Returns the height of this search tree.
   * <p>
   * The height of an empty tree is 0.
   *
   * @return the height of the tree.
   */
  int height();

  /**
   * Inserts the specified key into the search tree.
   * <p>
   * If the tree already contains a key that is considered equal (according to the comparator),
   * the existing key is replaced by the new one.
   *
   * @param key the key to be inserted.
   */
  void insert(K key);

  /**
   * Searches for a given key in the search tree.
   *
   * @param key the key to search for.
   * @return the key found in the tree that is equal to the search key, or {@code null} if no such key is found.
   */
  K search(K key);

  /**
   * Returns {@code true} if this search tree contains the specified key.
   *
   * @param key the key whose presence in this tree is to be tested.
   * @return {@code true} if this tree contains the specified key.
   */
  boolean contains(K key);

  /**
   * Removes the specified key from this search tree if it is present.
   *
   * @param key the key to be removed.
   */
  void delete(K key);

  /**
   * Removes all keys from this search tree, leaving it empty.
   */
  void clear();

  /**
   * Returns the smallest key in this search tree.
   *
   * @return the smallest key.
   * @throws EmptySearchTreeException if the tree is empty.
   */
  K minimum();

  /**
   * Returns the largest key in this search tree.
   *
   * @return the largest key.
   * @throws EmptySearchTreeException if the tree is empty.
   */
  K maximum();

  /**
   * Removes the smallest key from this search tree.
   *
   * @throws EmptySearchTreeException if the tree is empty.
   */
  void deleteMinimum();

  /**
   * Removes the largest key from this search tree.
   *
   * @throws EmptySearchTreeException if the tree is empty.
   */
  void deleteMaximum();

  /**
   * Provides an {@code Iterable} to traverse the keys of the tree in in-order.
   * <p>
   * For a binary search tree, this traversal yields the keys in ascending sorted order.
   *
   * @return an {@code Iterable} for the keys in in-order sequence.
   */
  Iterable<K> inOrder();

  /**
   * Provides an {@code Iterable} to traverse the keys of the tree in post-order.
   *
   * @return an {@code Iterable} for the keys in post-order sequence.
   */
  Iterable<K> postOrder();

  /**
   * Provides an {@code Iterable} to traverse the keys of the tree in pre-order.
   *
   * @return an {@code Iterable} for the keys in pre-order sequence.
   */
  Iterable<K> preOrder();
}