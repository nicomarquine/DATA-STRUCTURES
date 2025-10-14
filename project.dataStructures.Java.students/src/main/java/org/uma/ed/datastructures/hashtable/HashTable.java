package org.uma.ed.datastructures.hashtable;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An interface for a Hash Table data structure that stores unique keys.
 * <p>
 * A hash table provides efficient key lookup, insertion, and deletion, typically in
 * average-case constant time, O(1). Its performance relies heavily on the keys having
 * a well-defined and properly implemented {@code equals()} and {@code hashCode()} methods.
 * <p>
 * In particular, the {@code equals}-{@code hashCode} contract requires that both methods are defined such that:
 * <ul>
 *     <li>If {@code k1.equals(k2)}, then {@code k1.hashCode() == k2.hashCode()}.</li>
 *     <li>Keys that are equal are treated as the same key within the hash table.</li>
 * </ul>
 * The iteration order over the keys is not guaranteed and may appear random.
 *
 * @param <K> The type of keys stored in the hash table.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public interface HashTable<K> extends Iterable<K> {

  /**
   * Checks if this hash table is empty.
   * <p> Time complexity: O(1)
   *
   * @return {@code true} if this hash table contains no keys, {@code false} otherwise.
   */
  boolean isEmpty();

  /**
   * Returns the number of keys in this hash table.
   * <p> Time complexity: O(1)
   *
   * @return the number of keys in the hash table.
   */
  int size();

  /**
   * Inserts the specified key into the hash table.
   * <p>
   * If the hash table already contains a key that is equal to the new key,
   * the existing key is replaced by the new one.
   * <p> Time complexity: Near O(1) on average, O(n) in the worst case.
   *
   * @param key the key to be inserted.
   */
  void insert(K key);

  /**
   * Searches for a given key in the hash table.
   *
   * @param key the key to search for.
   * @return the key found in the table that is equal to the search key;
   *         returns {@code null} if no such key is found.
   * <p> Time complexity: Near O(1) on average, O(n) in the worst case.
   */
  K search(K key);

  /**
   * Returns {@code true} if this hash table contains the specified key.
   * <p> Time complexity: Near O(1) on average, O(n) in the worst case.
   *
   * @param key the key whose presence in this table is to be tested.
   * @return {@code true} if this table contains the specified key.
   */
  boolean contains(K key);

  /**
   * Removes the specified key from this hash table if it is present.
   * <p>
   * If the key is not found, the hash table remains unchanged.
   * <p> Time complexity: Near O(1) on average, O(n) in the worst case.
   *
   * @param key the key to be removed.
   */
  void delete(K key);

  /**
   * Removes all keys from this hash table, leaving it empty.
   * <p> Time complexity: O(n), where n is the capacity of the hash table.
   */
  void clear();
}