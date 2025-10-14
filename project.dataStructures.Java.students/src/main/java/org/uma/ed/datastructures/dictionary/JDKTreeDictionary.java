package org.uma.ed.datastructures.dictionary;

import java.util.*;

/**
 * An implementation of the {@link SortedDictionary} interface that acts as an adapter for the
 * standard Java {@link java.util.TreeMap}.
 * <p>
 * This class delegates all of its operations to an internal {@code java.util.TreeMap}
 * instance. {@code java.util.TreeMap} is implemented using a Red-Black Tree, offering
 * guaranteed O(log n) time complexity for core operations like {@code insert},
 * {@code valueOf}, and {@code delete}.
 *
 * @param <K> The type of keys maintained by this dictionary.
 * @param <V> The type of mapped values.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class JDKTreeDictionary<K, V> extends AbstractSortedDictionary<K, V> implements SortedDictionary<K, V> {

  /**
   * The underlying JDK {@code TreeMap} to which all operations are delegated.
   */
  private final TreeMap<K, V> treeMap;

  /**
   * Private constructor to wrap an existing {@code TreeMap}.
   * @param treeMap The {@code TreeMap} to be used as the internal representation.
   */
  private JDKTreeDictionary(TreeMap<K, V> treeMap) {
    this.treeMap = treeMap;
  }

  /**
   * Constructs an empty {@code JDKTreeDictionary} with a specified comparator.
   * <p> Time complexity: O(1)
   * @param comparator the comparator that will be used to order the keys in this dictionary.
   */
  public JDKTreeDictionary(Comparator<? super K> comparator) {
    this(new TreeMap<>(comparator));
  }

  /**
   * Creates an empty {@code JDKTreeDictionary} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public static <K, V> JDKTreeDictionary<K, V> empty(Comparator<? super K> comparator) {
    return new JDKTreeDictionary<>(comparator);
  }

  /**
   * Creates an empty {@code JDKTreeDictionary} with natural key ordering.
   * <p> Time complexity: O(1)
   */
  public static <K extends Comparable<? super K>, V> JDKTreeDictionary<K, V> empty() {
    return new JDKTreeDictionary<>(Comparator.naturalOrder());
  }

  /**
   * Creates a new {@code JDKTreeDictionary} from the given entries, ordered by the specified comparator.
   * <p> Time complexity: O(n log n)
   */
  @SafeVarargs
  public static <K, V> JDKTreeDictionary<K, V> of(Comparator<? super K> comparator, Entry<K, V>... entries) {
    JDKTreeDictionary<K, V> dict = new JDKTreeDictionary<>(comparator);
    for (Entry<K, V> entry : entries) {
      dict.insert(entry);
    }
    return dict;
  }

  /**
   * Creates a new {@code JDKTreeDictionary} from the given entries with natural key ordering.
   * <p> Time complexity: O(n log n)
   */
  @SafeVarargs
  public static <K extends Comparable<? super K>, V> JDKTreeDictionary<K, V> of(Entry<K, V>... entries) {
    return of(Comparator.naturalOrder(), entries);
  }

  /**
   * Creates a new {@code JDKTreeDictionary} from an iterable of entries, ordered by the specified comparator.
   * <p> Time complexity: O(n log n)
   */
  public static <K, V> JDKTreeDictionary<K, V> from(Comparator<? super K> comparator, Iterable<Entry<K, V>> iterable) {
    JDKTreeDictionary<K, V> dict = new JDKTreeDictionary<>(comparator);
    for (Entry<K, V> entry : iterable) {
      dict.insert(entry);
    }
    return dict;
  }

  /**
   * Creates a new {@code JDKTreeDictionary} from an iterable of entries with natural key ordering.
   * <p> Time complexity: O(n log n)
   */
  public static <K extends Comparable<? super K>, V> JDKTreeDictionary<K, V> from(Iterable<Entry<K, V>> iterable) {
    return from(Comparator.naturalOrder(), iterable);
  }

  /**
   * Creates a new {@code JDKTreeDictionary} that is a copy of the given one.
   * <p> This is an efficient O(n) operation.
   * <p> Time complexity: O(n)
   */
  public static <K, V> JDKTreeDictionary<K, V> copyOf(JDKTreeDictionary<K, V> that) {
    return new JDKTreeDictionary<>(new TreeMap<>(that.treeMap));
  }

  /**
   * Creates a new {@code JDKTreeDictionary} from any {@code SortedDictionary}.
   * <p> This is an efficient O(n) operation as it leverages the sorted nature of the source.
   * <p> Time complexity: O(n)
   */
  public static <K, V> JDKTreeDictionary<K, V> copyOf(SortedDictionary<K, V> that) {
    if (that instanceof JDKTreeDictionary<K, V> jdkTreeDict) {
      return copyOf(jdkTreeDict);
    }
    TreeMap<K, V> newTreeMap = new TreeMap<>(that.comparator());
    for (Entry<K, V> entry : that.entries()) {
      newTreeMap.put(entry.key(), entry.value());
    }
    return new JDKTreeDictionary<>(newTreeMap);
  }

  /**
   * {@inheritDoc}
   * <p> Note: `java.util.TreeMap.comparator()` returns null if it uses natural ordering.
   * This implementation guarantees to always return a valid Comparator instance.
   * <p> Time complexity: O(1)
   */
  @Override
  @SuppressWarnings("unchecked")
  public Comparator<K> comparator() {
    Comparator<? super K> cmp = treeMap.comparator();
    return (cmp == null) ? (Comparator<K>) Comparator.naturalOrder() : (Comparator<K>) cmp;
  }

  @Override
  public boolean isEmpty() {
    return treeMap.isEmpty();
  }

  @Override
  public int size() {
    return treeMap.size();
  }

  @Override
  public void insert(Entry<K, V> entry) {
    treeMap.put(entry.key(), entry.value());
  }

  @Override
  public V valueOf(K key) {
    return treeMap.get(key);
  }

  @Override
  public boolean isDefinedAt(K key) {
    return treeMap.containsKey(key);
  }

  @Override
  public void delete(K key) {
    treeMap.remove(key);
  }

  @Override
  public void clear() {
    treeMap.clear();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public Entry<K, V> minimum() {
    if (treeMap.isEmpty()) {
      throw new NoSuchElementException("minimum on empty dictionary");
    }
    Map.Entry<K, V> jdkEntry = treeMap.firstEntry();
    return Entry.of(jdkEntry.getKey(), jdkEntry.getValue());
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public Entry<K, V> maximum() {
    if (treeMap.isEmpty()) {
      throw new NoSuchElementException("maximum on empty dictionary");
    }
    Map.Entry<K, V> jdkEntry = treeMap.lastEntry();
    return Entry.of(jdkEntry.getKey(), jdkEntry.getValue());
  }

  @Override
  public Iterable<K> keys() {
    return treeMap.keySet();
  }

  @Override
  public Iterable<V> values() {
    return treeMap.values();
  }

  @Override
  public Iterable<Entry<K, V>> entries() {
    return this; // This class is iterable over its entries.
  }

  /**
   * Returns an iterator over the entries in this dictionary, in ascending key order.
   * <p>
   * This method adapts the iterator from {@code java.util.TreeMap.entrySet()} to an
   * iterator over this library's {@code Dictionary.Entry} type.
   *
   * @return an iterator over the entries in key-sorted order.
   */
  @Override
  public Iterator<Entry<K, V>> iterator() {
    Iterator<Map.Entry<K, V>> jdkIterator = treeMap.entrySet().iterator();

    // Return an adapter that converts JDK Map.Entry to our Dictionary.Entry on the fly.
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