package org.uma.ed.datastructures.dictionary;

import org.uma.ed.datastructures.hashtable.HashTable;
import org.uma.ed.datastructures.hashtable.LinearProbingHashTable;

import java.util.Iterator;

/**
 * An implementation of the {@link Dictionary} interface using a custom hash table
 * with linear probing for collision resolution.
 * <p>
 * This class maps unique keys to values. It provides constant-time performance O(1)
 * on average for basic operations ({@code insert}, {@code valueOf}, {@code delete}),
 * assuming the hash function disperses the elements properly among the buckets.
 * <p>
 * Note: Keys used with this dictionary must have a proper implementation of
 * {@link Object#equals(Object)} and {@link Object#hashCode()}.
 *
 * @param <K> The type of keys maintained by this dictionary.
 * @param <V> The type of mapped values.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class HashDictionary<K, V> extends AbstractDictionary<K, V> implements Dictionary<K, V> {

  /**
   * The underlying hash table that stores the dictionary entries.
   */
  private final HashTable<Entry<K, V>> hashTable;

  /**
   * Private constructor for internal use by factory methods.
   */
  private HashDictionary(LinearProbingHashTable<Entry<K, V>> hashTable) {
    this.hashTable = hashTable;
  }

  /**
   * Constructs an empty {@code HashDictionary} with default initial capacity and load factor.
   * <p> Time complexity: O(1)
   */
  public HashDictionary() {
    this(new LinearProbingHashTable<>());
  }

  /**
   * Constructs an empty {@code HashDictionary} with a specified initial capacity and load factor.
   * <p> Time complexity: O(1)
   * @param numCells Initial number of cells in table (should be a prime number).
   * @param maxLoadFactor Maximum load factor to tolerate. If exceeded, rehashing is performed automatically.
   *
   * @throws IllegalArgumentException if numChains is less than 1.
   */
  public HashDictionary(int numCells, double maxLoadFactor) {
    this(new LinearProbingHashTable<>(numCells, maxLoadFactor));
  }

  /**
   * Creates an empty {@code HashDictionary}.
   * <p> Time complexity: O(1)
   */
  public static <K, V> HashDictionary<K, V> empty() {
    return new HashDictionary<>();
  }

  /**
   * Creates an empty {@code HashDictionary} that can accommodate a specified number of elements
   * before needing to rehash.
   * <p> Time complexity: O(1)
   */
  public static <K, V> HashDictionary<K, V> withCapacity(int capacity) {
    return new HashDictionary<>(LinearProbingHashTable.withCapacity(capacity));
  }

  /**
   * Creates a new {@code HashDictionary} from the given entries.
   * <p> Time complexity: Near O(n) on average.
   */
  @SafeVarargs
  public static <K, V> HashDictionary<K, V> of(Entry<K, V>... entries) {
    HashDictionary<K, V> dictionary = withCapacity(entries.length);
    for (Entry<K, V> entry : entries) {
      dictionary.insert(entry);
    }
    return dictionary;
  }

  /**
   * Creates a new {@code HashDictionary} from an iterable of entries.
   * <p> Time complexity: Near O(n) on average.
   */
  public static <K, V> HashDictionary<K, V> from(Iterable<Entry<K, V>> iterable) {
    HashDictionary<K, V> dictionary = empty();
    for (Entry<K, V> entry : iterable) {
      dictionary.insert(entry);
    }
    return dictionary;
  }

  /**
   * Creates a new {@code HashDictionary} containing the same mappings as the given dictionary.
   * <p> Time complexity: O(n)
   */
  public static <K, V> HashDictionary<K, V> copyOf(HashDictionary<K, V> that) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Creates a new {@code HashDictionary} containing the same mappings as the given dictionary.
   * <p> Time complexity: Near O(n) on average.
   */
  public static <K, V> HashDictionary<K, V> copyOf(Dictionary<K, V> that) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public boolean isEmpty() { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public int size() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: Amortized O(1) on average.
   */
  @Override
  public void insert(Entry<K, V> entry) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1) on average.
   */
  @Override
  public V valueOf(K key) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1) on average.
   */
  @Override
  public boolean isDefinedAt(K key) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1) on average.
   */
  @Override
  public void delete(K key) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n) in the worst case.
   */
  @Override
  public void clear() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1) for creating the iterable.
   */
  @Override
  public Iterable<K> keys() {
    return () -> new KeyIterator(hashTable.iterator());
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1) for creating the iterable.
   */
  @Override
  public Iterable<V> values() {
    return () -> new ValueIterator(hashTable.iterator());
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1) for creating the iterable.
   */
  @Override
  public Iterable<Entry<K, V>> entries() {
    return hashTable;
  }

  @Override
  public Iterator<Entry<K, V>> iterator() {
    return entries().iterator();
  }

  // --- Private Iterator Helper Classes ---

  private abstract class EntryIterator<T> implements Iterator<T> {
    protected final Iterator<Entry<K, V>> iterator;

    EntryIterator(Iterator<Entry<K, V>> iterator) {
      this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
      return iterator.hasNext();
    }
  }

  private final class KeyIterator extends EntryIterator<K> {
    KeyIterator(Iterator<Entry<K, V>> iterator) {
      super(iterator);
    }

    @Override
    public K next() {
      return iterator.next().key();
    }
  }

  private final class ValueIterator extends EntryIterator<V> {
    ValueIterator(Iterator<Entry<K, V>> iterator) {
      super(iterator);
    }

    @Override
    public V next() {
      return iterator.next().value();
    }
  }
}