package org.uma.ed.datastructures.dictionary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Dictionaries (aka finite maps) associating different keys to values implemented using
 * JDK's {@link java.util.HashMap}.
 * <p>
 * This class serves as an adapter over Java's standard HashMap, allowing it to be used
 * within this data structures library. It correctly handles {@code null} values.
 * Note that keys should properly implement {@link Object#equals(Object)} and
 * {@link Object#hashCode()}.
 *
 * @param <K> Type of keys.
 * @param <V> Type of values.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class JDKHashDictionary<K, V> extends AbstractDictionary<K, V> implements Dictionary<K, V> {

  private final HashMap<K, V> hashMap;

  /**
   * Private constructor to wrap an existing {@code HashMap}.
   * @param hashMap The {@code HashMap} to be used as the internal representation.
   *
   * <p> Time complexity: O(1)
   */
  private JDKHashDictionary(HashMap<K, V> hashMap) {
    this.hashMap = hashMap;
  }

  /**
   * Creates an empty {@code JDKHashDictionary}.
   * <p> Time complexity: O(1)
   */
  public JDKHashDictionary() {
    this(new HashMap<>());
  }

  /**
   * Creates an empty {@code JDKHashDictionary} with a specified initial capacity and load factor.
   * <p> Time complexity: O(1)
   *
   * @param initialCapacity the initial capacity.
   * @param loadFactor      the load factor.
   * @throws IllegalArgumentException if the initial capacity or load factor are invalid.
   */
  public JDKHashDictionary(int initialCapacity, double loadFactor) {
    this(new HashMap<>(initialCapacity, (float)loadFactor));
  }

  /**
   * Constructs an empty {@code JDKHashDictionary}.
   * <p> Time complexity: O(1)
   */
  public static <K, V> JDKHashDictionary<K, V> empty() {
    return new JDKHashDictionary<>();
  }

  /**
   * Creates a new empty {@code JDKHashDictionary} that can accommodate a specified number of
   * elements before needing to rehash.
   * <p> Time complexity: O(1)
   *
   * @param capacity the desired initial capacity.
   * @return a new {@code JDKHashDictionary} with the given capacity.
   */
  public static <K, V> JDKHashDictionary<K, V> withCapacity(int capacity) {
    return new JDKHashDictionary<>(new HashMap<>(capacity));
  }

  /**
   * Returns a new {@code JDKHashDictionary} with the provided entries.
   * <p> Time complexity: Near O(n) on average.
   */
  @SafeVarargs
  public static <K, V> JDKHashDictionary<K, V> of(Entry<K, V>... entries) {
    JDKHashDictionary<K, V> dictionary = entries.length > 0 ? withCapacity(entries.length) : empty();
    for (Entry<K, V> entry : entries) {
      dictionary.insert(entry);
    }
    return dictionary;
  }

  /**
   * Returns a new {@code JDKHashDictionary} from an iterable of entries.
   * <p> Time complexity: Near O(n) on average.
   */
  public static <K, V> JDKHashDictionary<K, V> from(Iterable<Entry<K, V>> iterable) {
    JDKHashDictionary<K, V> dictionary = empty();
    for (Entry<K, V> entry : iterable) {
      dictionary.insert(entry);
    }
    return dictionary;
  }

  /**
   * Returns a new {@code JDKHashDictionary} with the same associations as the argument.
   * <p> Time complexity: O(n)
   */
  @SuppressWarnings("unchecked")
  public static <K, V> JDKHashDictionary<K, V> copyOf(JDKHashDictionary<K, V> that) {
    return new JDKHashDictionary<>((HashMap<K, V>) that.hashMap.clone());
  }

  /**
   * Returns a new {@code JDKHashDictionary} with the same associations as the argument.
   * <p> Time complexity: Near O(n) on average.
   */
  public static <K, V> JDKHashDictionary<K, V> copyOf(Dictionary<K, V> that) {
    if (that instanceof JDKHashDictionary<K, V> hashDictionary) {
      return copyOf(hashDictionary);
    }
    JDKHashDictionary<K, V> copy = withCapacity(that.size());
    for (Entry<K, V> entry : that.entries()) {
      copy.insert(entry.key(), entry.value());
    }
    return copy;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public boolean isEmpty() {
    return hashMap.isEmpty();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public int size() {
    return hashMap.size();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: Amortized O(1) on average.
   */
  @Override
  public void insert(K key, V value) {
    hashMap.put(key, value);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: Amortized O(1) on average.
   */
  @Override
  public void insert(Entry<K, V> entry) {
    hashMap.put(entry.key(), entry.value());
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1) on average.
   */
  @Override
  public V valueOf(K key) {
    return hashMap.get(key);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1) on average.
   */
  @Override
  public boolean isDefinedAt(K key) {
    return hashMap.containsKey(key);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1) on average.
   */
  @Override
  public void delete(K key) {
    hashMap.remove(key);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n) in the worst case.
   */
  @Override
  public void clear() {
    hashMap.clear();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1) for creating the iterable.
   */
  @Override
  public Iterable<K> keys() {
    return hashMap.keySet();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1) for creating the iterable.
   */
  @Override
  public Iterable<V> values() {
    return hashMap.values();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1) for creating the iterable.
   */
  @Override
  public Iterable<Entry<K, V>> entries() {
    return this; // This class is already iterable over its entries.
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1) for creating the iterator.
   */
  @Override
  public Iterator<Entry<K, V>> iterator() {
    Iterator<Map.Entry<K, V>> jdkIterator = hashMap.entrySet().iterator();
    // Adapt the JDK's Map.Entry iterator to our Dictionary.Entry iterator
    return new Iterator<>() {
      @Override
      public boolean hasNext() {
        return jdkIterator.hasNext();
      }

      @Override
      public Entry<K, V> next() {
        Map.Entry<K, V> jdkEntry = jdkIterator.next();
        return Entry.of(jdkEntry.getKey(), jdkEntry.getValue());
      }
    };
  }
}