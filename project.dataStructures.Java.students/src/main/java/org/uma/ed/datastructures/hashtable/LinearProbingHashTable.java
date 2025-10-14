package org.uma.ed.datastructures.hashtable;

import org.uma.ed.datastructures.utils.toString.ToString;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An implementation of the {@link HashTable} interface using open addressing with linear probing.
 * <p>
 * In linear probing, when a hash collision occurs (i.e., the target cell for a key is
 * already occupied by a different key), the algorithm probes the next cell in the array
 * sequentially (wrapping around if necessary) until an empty cell is found.
 * <p>
 * This implementation automatically resizes and rehashes its contents when the load factor
 * (the ratio of size to capacity) exceeds a specified maximum. Deletion is handled carefully
 * to ensure that probing chains are not broken.
 *
 * @param <K> The type of keys stored in the hash table.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class LinearProbingHashTable<K> implements HashTable<K> {

  private static final int DEFAULT_NUM_CELLS = HashPrimes.primeGreaterThan(32);
  private static final double DEFAULT_MAX_LOAD_FACTOR = 0.5;

  private K[] keys;           // The array of cells storing the keys.
  private int size;           // The number of keys currently in the table.
  private final double maxLoadFactor; // The threshold for triggering a rehash.

  /*
   * INVARIANT:
   *  - `size` is the number of non-null elements in the `keys` array.
   *  - The `keys` array contains the stored elements. `null` indicates an empty cell.
   *  - The load factor (`size / keys.length`) should ideally be kept <= `maxLoadFactor`.
   *  - All keys belonging to the same probing sequence form a contiguous block (a "cluster").
   *    A `null` cell marks the end of a cluster.
   */

  /**
   * Constructs an empty hash table with a specified initial capacity and max load factor.
   * <p> Time complexity: O(capacity)
   */
  @SuppressWarnings("unchecked")
  public LinearProbingHashTable(int numCells, double maxLoadFactor) {
    if (numCells <= 0) {
      throw new IllegalArgumentException("Initial number of cells must be greater than 0");
    }
    this.keys = (K[]) new Object[numCells];
    this.size = 0;
    this.maxLoadFactor = maxLoadFactor;
  }

  /**
   * Constructs an empty hash table with default capacity and load factor.
   * <p> Time complexity: O(default_capacity)
   */
  public LinearProbingHashTable() {
    this(DEFAULT_NUM_CELLS, DEFAULT_MAX_LOAD_FACTOR);
  }

  /**
   * Creates an empty hash table with default settings.
   * <p> Time complexity: O(1) (amortized)
   */
  public static <K> LinearProbingHashTable<K> empty() {
    return new LinearProbingHashTable<>();
  }

  /**
   * Creates an empty hash table with an initial capacity sized for a given number of elements.
   * <p> Time complexity: O(capacity)
   */
  public static <K> LinearProbingHashTable<K> withCapacity(int size) {
    if (size <= 0) {
      throw new IllegalArgumentException("Initial capacity must be greater than 0");
    }
    int initialCapacity = HashPrimes.primeGreaterThan((int) (size / DEFAULT_MAX_LOAD_FACTOR));
    return new LinearProbingHashTable<>(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);
  }

  /**
   * Creates a new hash table that is a structural copy of the given one.
   * <p> Time complexity: O(n), where n is the capacity of the source table.
   */
  public static <K> LinearProbingHashTable<K> copyOf(LinearProbingHashTable<K> that) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Creates a new hash table containing the same elements as the given {@code HashTable}.
   * <p> Time complexity: Near O(n) on average, where n is the number of elements.
   */
  public static <K> LinearProbingHashTable<K> copyOf(HashTable<K> that) { throw new UnsupportedOperationException("Not implemented yet"); }

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
   * Primary hash function to map a key to an initial cell index.
   */
  private int hash(K key) {
    // Mask with 0x7fffffff to ensure positivity, then take modulo.
    return (key.hashCode() & 0x7fffffff) % keys.length;
  }

  /**
   * Calculates the current load factor: percentage of occupied cells.
   */
  private double loadFactor() {
    return (double) size / keys.length;
  }

  /**
   * Computes the next index in the probing sequence (with wrap-around).
   */
  private int advance(int index) {
    return (index + 1) % keys.length;
  }

  /**
   * Finds the index for a given key.
   * @return The index where the key is located, or the first empty cell in its probe sequence if not found.
   */
  private int searchIndex(K key) {
    int index = hash(key);
    while (keys[index] != null && !keys[index].equals(key)) {
      index = advance(index);
    }
    return index;
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
   * <p> Time complexity: near O(1)
   */
  @Override
  public void delete(K key) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n), where n is the capacity.
   */
  @Override
  public void clear() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Doubles the table size to the next prime number and re-inserts all keys.
   * This is a costly O(n) operation performed to maintain a low load factor.
   */
  @SuppressWarnings("unchecked")
  private void rehashing() {
    K[] oldKeys = this.keys;

    int newCapacity = HashPrimes.primeDoubleThan(oldKeys.length);
    this.keys = (K[]) new Object[newCapacity];

    // Reinsert elements in new table
    for (K oldKey : oldKeys) {
      if (oldKey != null) {
        int newIndex = searchIndex(oldKey); // search for new index in new table
        keys[newIndex] = oldKey; // insert oldKey in new table
      }
    }
  }

  @Override
  public Iterator<K> iterator() {
    return new LinearProbingHashTableIterator();
  }

  /**
   * An iterator that traverses the non-null keys in the internal array.
   */
  private final class LinearProbingHashTableIterator implements Iterator<K> {
    private int yieldedCount;  // Number of keys yielded so far
    private int currentIndex;  // Current index in the keys array

    public LinearProbingHashTableIterator() {
      yieldedCount = 0;
      currentIndex = -1; // so that after first increment it becomes 0
    }

    @Override
    public boolean hasNext() {
      return yieldedCount < size;
    }

    @Override
    public K next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      // Find the next non-null element.
      do {
        currentIndex++;
      } while (keys[currentIndex] == null);

      yieldedCount++;
      return keys[currentIndex];
    }
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}