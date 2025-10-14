package org.uma.ed.datastructures.hashtable;

import org.uma.ed.datastructures.utils.toString.ToString;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An implementation of the {@link HashTable} interface using separate chaining.
 * <p>
 * In separate chaining, the hash table is an array of "chains" (typically linked lists).
 * Each key is hashed to an index in the array, which corresponds to a specific chain.
 * All keys that hash to the same index are stored in that chain's linked list.
 * <p>
 * This approach gracefully handles collisions. Deletion is simple as it involves standard
 * linked list node removal. The performance of operations depends on the length of the chains,
 * which is managed by keeping the load factor low through rehashing.
 *
 * @param <K> The type of keys stored in the hash table.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class SeparateChainingHashTable<K> implements HashTable<K> {

  /**
   * Internal class representing a node in a linked list (a chain).
   */
  private static final class Node<K> {
    K key;
    Node<K> next;

    Node(K key, Node<K> next) {
      this.key = key;
      this.next = next;
    }
  }

  private static final int DEFAULT_NUM_CHAINS = HashPrimes.primeGreaterThan(32);
  private static final double DEFAULT_MAX_LOAD_FACTOR = 5.0;

  private Node<K>[] table;      // The array of chains (linked lists).
  private int size;             // The total number of keys in the table.
  private final double maxLoadFactor; // The threshold for triggering a rehash.

  /*
   * INVARIANT:
   *  - `table` is an array of Node references, where each index represents a bucket.
   *  - `table[i]` is the head of a singly-linked list (a "chain") of all keys whose
   *    hash code maps to index `i`.
   *  - `size` is the total number of keys across all chains.
   *  - The load factor (size / table.length) should not exceed `maxLoadFactor`.
   */

  /**
   * Constructs an empty hash table with a specified number of chains and max load factor.
   * <p> Time complexity: O(numChains)
   */
  @SuppressWarnings("unchecked")
  public SeparateChainingHashTable(int numChains, double maxLoadFactor) {
    if (numChains <= 0) {
      throw new IllegalArgumentException("Initial number of chains must be greater than 0");
    }
    this.table = (Node<K>[]) new Node[numChains];
    this.size = 0;
    this.maxLoadFactor = maxLoadFactor;
  }

  /**
   * Constructs an empty hash table with default settings.
   * <p> Time complexity: O(default_chains)
   */
  public SeparateChainingHashTable() {
    this(DEFAULT_NUM_CHAINS, DEFAULT_MAX_LOAD_FACTOR);
  }

  /**
   * Creates an empty hash table with default settings.
   * <p> Time complexity: O(1) (amortized)
   */
  public static <K> SeparateChainingHashTable<K> empty() {
    return new SeparateChainingHashTable<>();
  }

  /**
   * Creates an empty hash table with an initial capacity sized for a given number of elements.
   * <p> Time complexity: O(capacity)
   */
  public static <K> SeparateChainingHashTable<K> withCapacity(int size) {
    if (size <= 0) {
      throw new IllegalArgumentException("Initial capacity must be greater than 0");
    }
    int initialChains = HashPrimes.primeGreaterThan((int) (size / DEFAULT_MAX_LOAD_FACTOR));
    return new SeparateChainingHashTable<>(initialChains, DEFAULT_MAX_LOAD_FACTOR);
  }

  /**
   * Creates a new hash table that is a structural copy of the given one.
   * <p> Time complexity: O(n + c), where n is the number of elements and c is the number of chains.
   */
  public static <K> SeparateChainingHashTable<K> copyOf(SeparateChainingHashTable<K> that) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Creates a new hash table containing the same elements as the given {@code HashTable}.
   * <p> Time complexity: Near O(n) on average, where n is the number of elements.
   */
  public static <K> SeparateChainingHashTable<K> copyOf(HashTable<K> that) { throw new UnsupportedOperationException("Not implemented yet"); }

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

  /**
   * Primary hash function to map a key to a chain index.
   */
  private int hash(K key) {
    return (key.hashCode() & 0x7fffffff) % table.length;
  }

  /**
   * Calculates the current load factor: expected number of keys per chain.
   */
  private double loadFactor() {
    return (double) size / table.length;
  }

  /**
   * A helper class that traverses a chain to find a key or its insertion point.
   * <p>
   * If found, {@code current} points to the node and {@code previous} to its predecessor
   * (or null if it's the first node).
   * If not found, {@code current} is null, and {@code previous} points to the last node
   * in the chain (or is null if the chain is empty).
   */
  private final class Finder {
    int index;
    Node<K> previous, current;

    Finder(K key) {
      index = hash(key);
      previous = null;
      current = table[index];

      while (current != null && !current.key.equals(key)) {
        previous = current;
        current = current.next;
      }
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: Near O(1) on average. Can be O(n) if rehashing occurs.
   */
  @Override
  public void insert(K key) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: Near O(1) on average.
   */
  @Override
  public K search(K key) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: Near O(1) on average.
   */
  @Override
  public boolean contains(K key) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: near O(1) on average.
   */
  @Override
  public void delete(K key) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(c) where c is the number of chains (to reset the table).
   */
  @Override
  public void clear() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Doubles the table size and re-inserts all keys into the new table.
   */
  @SuppressWarnings("unchecked")
  private void rehashing() {
    // compute new table size
    int newCapacity = HashPrimes.primeDoubleThan(table.length);

    Node<K>[] oldTable = table;

    // allocate new table
    table = (Node<K>[]) new Node[newCapacity];

    for (Node<K> chain : oldTable) {  // Iterate through all chains of the old table.
      Node<K> current = chain;
      while (current != null) { // Iterate through all nodes in the current chain.
        Node<K> node = current;
        current = current.next; // Advance for next iteration of while loop
        // insert node in new table
        int index = hash(node.key);
        node.next = table[index];
        table[index] = node;
      }
    }
  }

  @Override
  public Iterator<K> iterator() {
    return new SeparateChainingHashTableIterator();
  }

  /**
   * An iterator that traverses all keys in the hash table.
   * It moves from one chain to the next.
   */
  private final class SeparateChainingHashTableIterator implements Iterator<K> {
    private int tableIndex;
    private Node<K> currentNode;

    public SeparateChainingHashTableIterator() {
      this.tableIndex = 0;
      this.currentNode = null;
      findNext(); // Position iterator at the first element.
    }

    /**
     * Finds the next available node in the entire hash table.
     */
    private void findNext() {
      if (currentNode != null) {
        currentNode = currentNode.next;
      }
      while (currentNode == null && tableIndex < table.length) {
        currentNode = table[tableIndex];
        tableIndex++;
      }
    }

    @Override
    public boolean hasNext() {
      return currentNode != null;
    }

    @Override
    public K next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      K key = currentNode.key;
      findNext(); // Prepare for the next call to next().
      return key;
    }
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}