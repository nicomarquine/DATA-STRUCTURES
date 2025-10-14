package org.uma.ed.datastructures.dictionary;

import java.util.Comparator;

/**
 * Represents a Dictionary, a data structure that maps unique keys to values.
 * <p>
 * A dictionary cannot contain duplicate keys; each key can map to at most one value.
 * The uniqueness of keys is determined by their {@code equals} method, unless the
 * dictionary is a {@link SortedDictionary}, in which case the comparator is used.
 * <p>
 * This interface is the main entry point for the dictionary framework in this library.
 *
 * @param <K> The type of keys maintained by this dictionary.
 * @param <V> The type of mapped values.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public interface Dictionary<K, V> extends Iterable<Dictionary.Entry<K, V>> {

  /**
   * Represents a key-value pair, which is the fundamental element of a dictionary.
   * <p>
   * An entry is defined primarily by its key; {@code equals} and {@code hashCode}
   * are based solely on the key component.
   *
   * @param <K> The type of the key.
   * @param <V> The type of the value.
   * @param key   The key for this entry. Must not be null.
   * @param value The value associated with the key. May be null.
   */
  record Entry<K, V>(K key, V value) {

    /**
     * Canonical constructor for an Entry.
     *
     * @throws IllegalArgumentException if the key is null.
     */
    public Entry {
      if (key == null) {
        throw new IllegalArgumentException("Key cannot be null");
      }
    }

    /**
     * Factory method to create a new {@code Entry} with a given key and value.
     *
     * @return A new {@code Entry} instance.
     */
    public static <K, V> Entry<K, V> of(K key, V value) {
      return new Entry<>(key, value);
    }

    /**
     * Factory method to create a new {@code Entry} with a given key and a null value.
     * <p>
     * This is useful for search or delete operations where only the key is needed.
     *
     * @return A new {@code Entry} with the given key.
     */
    public static <K, V> Entry<K, V> withKey(K key) {
      return new Entry<>(key, null);
    }

    /**
     * Compares this entry with another object for equality.
     * <p>
     * Two entries are considered equal if their keys are equal. The value component
     * is not considered in this comparison.
     *
     * @param obj The object to compare with this entry.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
      return this == obj || (obj instanceof Entry<?, ?> other && this.key.equals(other.key));
    }

    /**
     * Returns the hash code for this entry.
     * <p>
     * The hash code is based solely on the key to be consistent with {@code equals}.
     *
     * @return The hash code of the key.
     */
    @Override
    public int hashCode() {
      return key.hashCode();
    }

    /**
     * Returns a string representation of this entry.
     */
    @Override
    public String toString() {
      return "Entry(" + key + ", " + value + ")";
    }

    /**
     * Returns a comparator that compares {@code Entry} objects based on their keys,
     * using a provided key comparator.
     * <p>
     * This is a crucial utility for implementing sorted dictionaries.
     *
     * @param keyComparator The comparator to use for comparing the keys.
     * @return A comparator for {@code Entry} objects that delegates to the key comparator.
     */
    public static <K, V> Comparator<Entry<K, V>> onKeyComparator(Comparator<K> keyComparator) {
      return (entry1, entry2) -> keyComparator.compare(entry1.key, entry2.key);
    }
  }

  /**
   * Checks if this dictionary is empty.
   *
   * @return {@code true} if this dictionary contains no key-value mappings.
   */
  boolean isEmpty();

  /**
   * Returns the number of key-value mappings in this dictionary.
   *
   * @return the number of entries in the dictionary.
   */
  int size();

  /**
   * Associates the specified value with the specified key in this dictionary.
   * <p>
   * If the dictionary previously contained a mapping for the key, the old value is
   * replaced by the specified value.
   *
   * @param entry The key-value mapping to be inserted.
   */
  void insert(Entry<K, V> entry);

  /**
   * A convenience method to associate a key with a value.
   *
   * @param key   the key with which the specified value is to be associated.
   * @param value the value to be associated with the specified key.
   */
  default void insert(K key, V value) {
    insert(Entry.of(key, value));
  }

  /**
   * Returns the value to which the specified key is mapped, or {@code null} if this
   * dictionary contains no mapping for the key.
   *
   * @param key the key whose associated value is to be returned.
   * @return the value for the given key, or {@code null} if the key is not found.
   */
  V valueOf(K key);

  /**
   * Returns the value to which the specified key is mapped, or a default value if
   * the dictionary contains no mapping for the key.
   *
   * @param key          the key whose associated value is to be returned.
   * @param defaultValue the default value to return if no mapping is found.
   * @return the value for the key, or the default value.
   * todo: if associated value can be null, this method cannot distinguish between "no mapping" and "mapping to null"
   */
  default V valueOfOrDefault(K key, V defaultValue) {
    V value = valueOf(key);
    return value == null ? defaultValue : value;
  }

  /**
   * Returns {@code true} if this dictionary contains a mapping for the specified key.
   *
   * @param key the key whose presence in this dictionary is to be tested.
   * @return {@code true} if a mapping for the key exists.
   */
  boolean isDefinedAt(K key);

  /**
   * Removes the mapping for a key from this dictionary if it is present.
   *
   * @param key the key whose mapping is to be removed from the dictionary.
   */
  void delete(K key);

  /**
   * Removes all of the mappings from this dictionary, leaving it empty.
   */
  void clear();

  /**
   * Returns an {@code Iterable} view of the keys contained in this dictionary.
   *
   * @return an iterable view of the keys.
   */
  Iterable<K> keys();

  /**
   * Returns an {@code Iterable} view of the values contained in this dictionary.
   *
   * @return an iterable view of the values.
   */
  Iterable<V> values();

  /**
   * Returns an {@code Iterable} view of the mappings (key-value pairs) contained
   * in this dictionary.
   *
   * @return an iterable view of the entries.
   */
  Iterable<Entry<K, V>> entries();
}