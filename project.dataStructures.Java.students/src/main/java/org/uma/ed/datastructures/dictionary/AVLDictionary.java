package org.uma.ed.datastructures.dictionary;

import org.uma.ed.datastructures.searchtree.AVL;
import org.uma.ed.datastructures.searchtree.SearchTree;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of the {@link SortedDictionary} interface using an AVL tree.
 * <p>
 * This class maps unique keys to values, maintaining the keys in sorted order.
 * The underlying AVL tree ensures that the dictionary remains balanced, providing
 * guaranteed O(log n) time complexity for {@code insert}, {@code delete}, {@code valueOf},
 * and {@code isDefinedAt} operations.
 *
 * @param <K> The type of keys maintained by this dictionary.
 * @param <V> The type of mapped values.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class AVLDictionary<K, V> extends AbstractSortedDictionary<K, V> implements SortedDictionary<K, V> {

  private final Comparator<K> comparator;
  private final SearchTree<Entry<K, V>> avlTree;

  /**
   * Private constructor for internal use.
   */
  private AVLDictionary(Comparator<K> comparator, SearchTree<Entry<K, V>> avlTree) {
    this.comparator = comparator;
    this.avlTree = avlTree;
  }

  /**
   * Constructs an empty {@code AVLDictionary}, ordered by the specified comparator.
   * <p> Time complexity: O(1)
   */
  public AVLDictionary(Comparator<K> comparator) {
    this(comparator, new AVL<>(Entry.onKeyComparator(comparator)));
  }

  /**
   * Creates an empty {@code AVLDictionary}, ordered by the specified comparator.
   * <p> Time complexity: O(1)
   */
  public static <K, V> AVLDictionary<K, V> empty(Comparator<K> comparator) {
    return new AVLDictionary<>(comparator);
  }

  /**
   * Creates an empty {@code AVLDictionary}, ordered by the natural ordering of its keys.
   * <p> Time complexity: O(1)
   */
  public static <K extends Comparable<? super K>, V> AVLDictionary<K, V> empty() {
    return new AVLDictionary<K, V>(Comparator.naturalOrder());
  }

  /**
   * Creates a new {@code AVLDictionary} from the given entries.
   * <p> Time complexity: O(n log n)
   */
  @SafeVarargs
  public static <K, V> AVLDictionary<K, V> of(Comparator<K> comparator, Entry<K, V>... entries) {
    AVLDictionary<K, V> dictionary = new AVLDictionary<>(comparator);
    for (Entry<K, V> entry : entries) {
      dictionary.insert(entry);
    }
    return dictionary;
  }

  /**
   * Creates a new {@code AVLDictionary} from the given entries with natural key ordering.
   * <p> Time complexity: O(n log n)
   */
  @SafeVarargs
  public static <K extends Comparable<? super K>, V> AVLDictionary<K, V> of(Entry<K, V>... entries) {
    return of(Comparator.naturalOrder(), entries);
  }

  /**
   * Creates a new {@code AVLDictionary} from an iterable of entries.
   * <p> Time complexity: O(n log n)
   */
  public static <K, V> AVLDictionary<K, V> from(Comparator<K> comparator, Iterable<Entry<K, V>> iterable) {
    AVLDictionary<K, V> dictionary = new AVLDictionary<>(comparator);
    for (Entry<K, V> entry : iterable) {
      dictionary.insert(entry);
    }
    return dictionary;
  }

  /**
   * Creates a new {@code AVLDictionary} from an iterable of entries with natural key ordering.
   * <p> Time complexity: O(n log n)
   */
  public static <K extends Comparable<? super K>, V> AVLDictionary<K, V> from(Iterable<Entry<K, V>> entries) {
    return from(Comparator.naturalOrder(), entries);
  }

  /**
   * Creates a new {@code AVLDictionary} containing the same mappings as the given dictionary.
   * <p> This is an efficient O(n) operation using the underlying tree's copy mechanism.
   * <p> Time complexity: O(n)
   */
  public static <K, V> AVLDictionary<K, V> copyOf(AVLDictionary<K, V> that) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Creates a new {@code AVLDictionary} containing the same mappings as the given sorted dictionary.
   * <p> Time complexity: O(n log n) due to repeated insertions.
   */
  public static <K, V> AVLDictionary<K, V> copyOf(SortedDictionary<K, V> that) { throw new UnsupportedOperationException("Not implemented yet"); }

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
  public Comparator<K> comparator() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public void insert(Entry<K, V> entry) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public V valueOf(K key) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public boolean isDefinedAt(K key) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
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
   * <p> Time complexity: O(log n)
   */
  @Override
  public Entry<K, V> minimum() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public Entry<K, V> maximum() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n) for the full iteration.
   */
  @Override
  public Iterable<K> keys() {
    return () -> new KeyIterator(avlTree.inOrder().iterator());
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n) for the full iteration.
   */
  @Override
  public Iterable<V> values() {
    return () -> new ValueIterator(avlTree.inOrder().iterator());
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n) for the full iteration.
   */
  @Override
  public Iterable<Entry<K, V>> entries() { throw new UnsupportedOperationException("Not implemented yet"); }

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