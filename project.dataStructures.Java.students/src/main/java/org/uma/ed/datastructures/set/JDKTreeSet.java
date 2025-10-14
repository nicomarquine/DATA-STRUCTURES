package org.uma.ed.datastructures.set;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

/**
 * An implementation of the {@link SortedSet} interface that acts as an adapter for the
 * standard Java {@link java.util.TreeSet}.
 * <p>
 * This class delegates all of its operations to an internal {@code java.util.TreeSet}
 * instance. {@code java.util.TreeSet} is implemented using a Red-Black Tree, offering
 * guaranteed O(log n) time complexity for core operations like {@code insert},
 * {@code delete}, and {@code contains}.
 *
 * @param <T> The type of elements held in this sorted set.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class JDKTreeSet<T> extends AbstractSortedSet<T> implements SortedSet<T> {

  /**
   * The underlying JDK {@code TreeSet} to which all operations are delegated.
   */
  private final TreeSet<T> treeSet;

  /**
   * Private constructor to wrap an existing {@code TreeSet}.
   * @param treeSet The {@code TreeSet} to be used as the internal representation.
   */
  private JDKTreeSet(TreeSet<T> treeSet) {
    this.treeSet = treeSet;
  }

  /**
   * Constructs an empty {@code JDKTreeSet} with a specified comparator.
   * <p> Time complexity: O(1)
   * @param comparator the comparator that will be used to order this set.
   */
  public JDKTreeSet(Comparator<? super T> comparator) {
    this(new TreeSet<>(comparator));
  }

  /**
   * Creates an empty {@code JDKTreeSet} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public static <T> JDKTreeSet<T> empty(Comparator<? super T> comparator) {
    return new JDKTreeSet<>(comparator);
  }

  /**
   * Creates an empty {@code JDKTreeSet} with natural ordering.
   * <p> Time complexity: O(1)
   */
  public static <T extends Comparable<? super T>> JDKTreeSet<T> empty() {
    return new JDKTreeSet<>(Comparator.naturalOrder());
  }

  /**
   * Creates a new {@code JDKTreeSet} from the given elements, ordered by the specified comparator.
   * <p> Time complexity: O(n log n)
   */
  @SafeVarargs
  public static <T> JDKTreeSet<T> of(Comparator<? super T> comparator, T... elements) {
    JDKTreeSet<T> set = new JDKTreeSet<>(comparator);
    set.insert(elements);
    return set;
  }

  /**
   * Creates a new {@code JDKTreeSet} from the given elements with natural ordering.
   * <p> Time complexity: O(n log n)
   */
  @SafeVarargs
  public static <T extends Comparable<? super T>> JDKTreeSet<T> of(T... elements) {
    return of(Comparator.naturalOrder(), elements);
  }

  /**
   * Creates a new {@code JDKTreeSet} from an iterable, ordered by the specified comparator.
   * <p> Time complexity: O(n log n)
   */
  public static <T> JDKTreeSet<T> from(Comparator<? super T> comparator, Iterable<T> iterable) {
    JDKTreeSet<T> set = new JDKTreeSet<>(comparator);
    for (T element : iterable) {
      set.insert(element);
    }
    return set;
  }

  /**
   * Creates a new {@code JDKTreeSet} from an iterable with natural ordering.
   * <p> Time complexity: O(n log n)
   */
  public static <T extends Comparable<? super T>> JDKTreeSet<T> from(Iterable<T> iterable) {
    return from(Comparator.naturalOrder(), iterable);
  }

  /**
   * Creates a new {@code JDKTreeSet} that is a copy of the given one.
   * <p> This is an efficient O(n) operation.
   * <p> Time complexity: O(n)
   */
  @SuppressWarnings("unchecked")
  public static <T> JDKTreeSet<T> copyOf(JDKTreeSet<T> that) {
    // Uses the efficient TreeSet copy constructor.
    return new JDKTreeSet<>((TreeSet<T>) that.treeSet.clone());
  }

  /**
   * Creates a new {@code JDKTreeSet} from any {@code SortedSet}.
   * <p> This is an efficient O(n) operation as it leverages the sorted nature of the source.
   * <p> Time complexity: O(n)
   */
  public static <T> JDKTreeSet<T> copyOf(SortedSet<T> that) {
    if (that instanceof JDKTreeSet<T> jdkTreeSet) {
      return copyOf(jdkTreeSet);
    }
    // TreeSet can be efficiently constructed from any sorted collection.
    TreeSet<T> newTreeSet = new TreeSet<>(that.comparator());
    that.forEach(newTreeSet::add);
    return new JDKTreeSet<>(newTreeSet);
  }

  /**
   * {@inheritDoc}
   * <p> Note: `java.util.TreeSet.comparator()` returns null if it uses natural ordering.
   * This implementation guarantees to always return a valid Comparator instance.
   * <p> Time complexity: O(1)
   */
  @Override
  @SuppressWarnings("unchecked")
  public Comparator<T> comparator() {
    Comparator<? super T> cmp = treeSet.comparator();
    // Ensure we always return a non-null comparator, as per the SortedSet interface contract.
    return (cmp == null) ? (Comparator<T>) Comparator.naturalOrder() : (Comparator<T>) cmp;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public boolean isEmpty() {
    return treeSet.isEmpty();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public int size() {
    return treeSet.size();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public void insert(T element) {
    treeSet.add(element);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public boolean contains(T element) {
    return treeSet.contains(element);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public void delete(T element) {
    treeSet.remove(element);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void clear() {
    treeSet.clear();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public T minimum() {
    if (isEmpty()) {
      throw new NoSuchElementException("minimum on empty set");
    }
    return treeSet.first();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public T maximum() {
    if (isEmpty()) {
      throw new NoSuchElementException("maximum on empty set");
    }
    return treeSet.last();
  }

  /**
   * {@inheritDoc}
   * The iterator returns elements in ascending sorted order.
   */
  @Override
  public Iterator<T> iterator() {
    return treeSet.iterator();
  }
}