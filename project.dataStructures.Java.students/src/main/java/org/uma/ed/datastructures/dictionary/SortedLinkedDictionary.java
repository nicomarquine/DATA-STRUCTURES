package org.uma.ed.datastructures.dictionary;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of the {@link SortedDictionary} interface using a sorted, singly-linked list.
 * <p>
 * This class maintains its entries in a linked list, sorted in ascending order of their keys
 * as defined by a comparator.
 * <p>
 * While simple to understand, this approach has performance limitations. Operations that
 * require finding a key ({@code insert}, {@code valueOf}, {@code isDefinedAt}, {@code delete})
 * have a linear time complexity O(n). Operations at the head of the list ({@code minimum}) are O(1).
 *
 * @param <K> The type of keys maintained by this dictionary.
 * @param <V> The type of mapped values.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class SortedLinkedDictionary<K, V> extends AbstractSortedDictionary<K, V> implements SortedDictionary<K, V> {

  /**
   * Internal class representing a node in the linked structure.
   */
  private static final class Node<K, V> {
    Entry<K, V> entry;
    Node<K, V> next;

    Node(Entry<K, V> entry, Node<K, V> next) {
      this.entry = entry;
      this.next = next;
    }
  }

  /*
   * INVARIANT:
   *  - The linked list starting at `first` holds the dictionary's entries.
   *  - The entries are sorted in ascending order of their keys, according to the `comparator`.
   *  - The list contains no duplicate keys.
   *  - `first` and `last` are null if and only if the dictionary is empty (`size == 0`).
   *  - If not empty, `first` points to the first node and `last` points to the last node.
   */

  private Node<K, V> first, last;
  private int size;
  private final Comparator<K> comparator;

  /**
   * Constructs an empty {@code SortedLinkedDictionary} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public SortedLinkedDictionary(Comparator<K> comparator) {
    this.first = null;
    this.last = null;
    this.size = 0;
    this.comparator = comparator;
  }

  /**
   * Creates an empty {@code SortedLinkedDictionary} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public static <K, V> SortedLinkedDictionary<K, V> empty(Comparator<K> comparator) {
    return new SortedLinkedDictionary<>(comparator);
  }

  /**
   * Creates an empty {@code SortedLinkedDictionary} with natural key ordering.
   * <p> Time complexity: O(1)
   */
  public static <K extends Comparable<? super K>, V> SortedLinkedDictionary<K, V> empty() {
    return new SortedLinkedDictionary<K, V>(Comparator.naturalOrder());
  }

  /**
   * Creates a new {@code SortedLinkedDictionary} from the given entries.
   * <p> Time complexity: O(n^2) in the worst case, due to repeated linear-time insertions.
   */
  @SafeVarargs
  public static <K, V> SortedLinkedDictionary<K, V> of(Comparator<K> comparator, Entry<K, V>... entries) {
    SortedLinkedDictionary<K, V> dictionary = new SortedLinkedDictionary<>(comparator);
    for (Entry<K, V> entry : entries) {
      dictionary.insert(entry);
    }
    return dictionary;
  }

  /**
   * Creates a new {@code SortedLinkedDictionary} from the given entries with natural key ordering.
   * <p> Time complexity: O(n^2) in the worst case.
   */
  @SafeVarargs
  public static <K extends Comparable<? super K>, V> SortedLinkedDictionary<K, V> of(Entry<K, V>... entries) {
    return of(Comparator.naturalOrder(), entries);
  }

  /**
   * Creates a new {@code SortedLinkedDictionary} from an iterable of entries.
   * <p> Time complexity: O(n^2) in the worst case.
   */
  public static <K, V> SortedLinkedDictionary<K, V> from(Comparator<K> comparator, Iterable<Entry<K, V>> entries) {
    SortedLinkedDictionary<K, V> dictionary = new SortedLinkedDictionary<>(comparator);
    for (Entry<K, V> entry : entries) {
      dictionary.insert(entry);
    }
    return dictionary;
  }

  /**
   * Creates a new {@code SortedLinkedDictionary} from an iterable of entries with natural key ordering.
   * <p> Time complexity: O(n^2) in the worst case.
   */
  public static <K extends Comparable<? super K>, V> SortedLinkedDictionary<K, V> from(Iterable<Entry<K, V>> entries) {
    return from(Comparator.naturalOrder(), entries);
  }

  /**
   * Creates a new {@code SortedLinkedDictionary} containing the same mappings as the given sorted dictionary.
   * <p>This is an efficient O(n) operation that leverages the sorted nature of the source.
   * <p> Time complexity: O(n)
   */
  public static <K, V> SortedLinkedDictionary<K, V> copyOf(SortedDictionary<K, V> that) { throw new UnsupportedOperationException("Not implemented yet"); }

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
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public Comparator<K> comparator() {
    return comparator;
  }

  /**
   * A helper class that traverses the list to find the correct position for an entry's key.
   */
  private final class Finder {
    Node<K, V> previous, current;
    boolean found;

    Finder(K key) {
      previous = null;
      current = first;
      int cmp = 0;
      while (current != null && (cmp = comparator.compare(key, current.entry.key())) > 0) {
        previous = current;
        current = current.next;
      }
      found = (current != null && cmp == 0);
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void insert(Entry<K, V> entry) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public V valueOf(K key) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public boolean isDefinedAt(K key) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void delete(K key) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void clear() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public Entry<K, V> minimum() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public Entry<K, V> maximum() { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public Iterator<Entry<K, V>> iterator() {
    return new EntriesIterator();
  }

  @Override
  public Iterable<K> keys() { return KeyIterator::new; }
  @Override
  public Iterable<V> values() { return ValueIterator::new; }
  @Override
  public Iterable<Entry<K, V>> entries() { return this; }

  private abstract class NodeIterator<T> implements Iterator<T> {
    protected Node<K, V> current = first;
    @Override
    public boolean hasNext() { return current != null; }
  }
  private final class KeyIterator extends NodeIterator<K> {
    @Override
    public K next() {
      if (!hasNext()) throw new NoSuchElementException();
      K key = current.entry.key();
      current = current.next;
      return key;
    }
  }
  private final class ValueIterator extends NodeIterator<V> {
    @Override
    public V next() {
      if (!hasNext()) throw new NoSuchElementException();
      V value = current.entry.value();
      current = current.next;
      return value;
    }
  }
  private final class EntriesIterator extends NodeIterator<Entry<K, V>> {
    @Override
    public Entry<K, V> next() {
      if (!hasNext()) throw new NoSuchElementException();
      Entry<K, V> entry = current.entry;
      current = current.next;
      return entry;
    }
  }

  /**
   * Appends an entry to the end of the list.
   * PRECONDITION: The key of the entry must be greater than the key of the last current entry.
   */
  private void append(Entry<K, V> entry) {
    assert isEmpty() || comparator.compare(entry.key(), last.entry.key()) > 0;
    Node<K, V> newNode = new Node<>(entry, null);
    if (isEmpty()) {
      first = newNode;
    } else {
      last.next = newNode;
    }
    last = newNode;
    size++;
  }
}