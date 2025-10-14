package org.uma.ed.datastructures.bag;

import org.uma.ed.datastructures.hashtable.HashTable;
import org.uma.ed.datastructures.hashtable.LinearProbingHashTable;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of the {@link Bag} interface using a hash table.
 * <p>
 * This bag stores unique elements as keys in a hash table, with each key associated
 * with a count of its occurrences. This approach provides near constant-time performance
 * O(1) on average for basic operations like {@code insert}, {@code delete}, and {@code occurrences}.
 * <p>
 * This implementation does not maintain any specific order for its elements. The iteration
 * order is determined by the hash table and may appear random.
 * <p>
 * Note: Elements stored in this bag must have a proper implementation of
 * {@code equals()} and {@code hashCode()}.
 *
 * @param <T> The type of elements in the bag.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class HashBag<T> extends AbstractBag<T> implements Bag<T> {

  /**
   * An internal record to store an element and its occurrence count.
   * The hash table's equality and hash code are based on the element only.
   */
  private record Pair<E>(E element, int occurrences) {

    static <E> Pair<E> of(E element, int occurrences) {
      return new Pair<>(element, occurrences);
    }

    static <E> Pair<E> withElement(E element) {
      return new Pair<>(element, 0); // Occurrences can be a dummy value for searches.
    }

    @Override
    public boolean equals(Object obj) {
      // Equality is based only on the element, not the occurrence count.
      return obj instanceof Pair<?> that && this.element.equals(that.element);
    }

    @Override
    public int hashCode() {
      // Hash code is based only on the element.
      return element.hashCode();
    }
  }

  private final HashTable<Pair<T>> hashTable;

  /**
   * Private constructor for internal use by factory methods.
   */
  private HashBag(LinearProbingHashTable<Pair<T>> hashTable) {
    this.hashTable = hashTable;
  }

  /**
   * Constructs an empty {@code HashBag} with default capacity and load factor.
   * <p> Time complexity: O(1)
   */
  public HashBag() {
    this(LinearProbingHashTable.empty());
  }

  /**
   * Constructs an empty {@code HashBag} with a specified initial capacity and load factor.
   * <p> Time complexity: O(1)
   */
  public HashBag(int numCells, double maxLoadFactor) {
    this(new LinearProbingHashTable<>(numCells, maxLoadFactor));
  }

  /**
   * Creates an empty {@code HashBag}.
   * <p> Time complexity: O(1)
   */
  public static <T> HashBag<T> empty() {
    return new HashBag<>();
  }

  /**
   * Creates an empty {@code HashBag} with enough initial capacity to hold the
   * specified number of unique elements without needing to rehash.
   * <p> Time complexity: O(1)
   */
  public static <T> HashBag<T> withCapacity(int capacity) {
    return new HashBag<>(LinearProbingHashTable.withCapacity(capacity));
  }

  /**
   * Creates a new {@code HashBag} from the given elements.
   * <p> Time complexity: Near O(m) on average, where m is the number of elements.
   */
  @SafeVarargs
  public static <T> HashBag<T> of(T... elements) {
    // Capacity hint can improve performance by reducing rehashes.
    HashBag<T> hashBag = withCapacity(elements.length);
    hashBag.insert(elements);
    return hashBag;
  }

  /**
   * Creates a new {@code HashBag} from an iterable.
   * <p> Time complexity: Near O(m) on average, where m is the number of elements.
   */
  public static <T> HashBag<T> from(Iterable<T> iterable) {
    HashBag<T> hashBag = new HashBag<>();
    for (T element : iterable) {
      hashBag.insert(element);
    }
    return hashBag;
  }

  /**
   * Creates a new {@code HashBag} that is a copy of the given one.
   * <p> Time complexity: O(n), where n is the number of unique elements.
   */
  public static <T> HashBag<T> copyOf(HashBag<T> that) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Creates a new {@code HashBag} from any {@code Bag}.
   * <p> Time complexity: Near O(m) on average, where m is the total number of elements.
   */
  public static <T> HashBag<T> copyOf(Bag<T> that) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1).
   */
  @Override
  public boolean isEmpty() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n), where n is the number of unique elements.
   */
  @Override
  public int size() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: Near O(1) on average.
   */
  @Override
  public void insert(T element) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: Near O(1) on average.
   */
  @Override
  public void delete(T element) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n), where n is the number of unique elements.
   */
  @Override
  public void clear() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: Near O(1) on average.
   */
  @Override
  public int occurrences(T element) { throw new UnsupportedOperationException("Not implemented yet"); }

  @Override
  public Iterator<T> iterator() {
    return new HashBagIterator();
  }

  /**
   * An iterator that traverses the elements of the bag, yielding each element
   * according to its number of occurrences.
   */
  private final class HashBagIterator implements Iterator<T> {
    private final Iterator<Pair<T>> tableIterator;
    private T currentElement;
    private int occurrencesLeft;

    public HashBagIterator() {
      this.tableIterator = hashTable.iterator();
      this.occurrencesLeft = 0;
      advanceToNextElement();
    }

    private void advanceToNextElement() {
      while (tableIterator.hasNext()) {
        Pair<T> nextPair = tableIterator.next();
        if (nextPair.occurrences() > 0) {
          currentElement = nextPair.element();
          occurrencesLeft = nextPair.occurrences();
          return;
        }
      }
      currentElement = null;
      occurrencesLeft = 0;
    }

    @Override
    public boolean hasNext() { throw new UnsupportedOperationException("Not implemented yet"); }

    @Override
    public T next() { throw new UnsupportedOperationException("Not implemented yet"); }
  }
}