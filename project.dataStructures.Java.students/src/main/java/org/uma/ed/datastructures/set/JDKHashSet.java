package org.uma.ed.datastructures.set;

import java.util.HashSet;
import java.util.Iterator;

/**
 * An implementation of the {@link Set} interface that acts as an adapter for the
 * standard Java {@link java.util.HashSet}.
 * <p>
 * This class delegates all of its operations to an internal {@code java.util.HashSet}
 * instance. {@code java.util.HashSet} is implemented using a {@code HashMap} and offers
 * constant-time performance on average for basic operations.
 * <p>
 * Note that elements should properly implement {@link Object#equals(Object)} and
 * {@link Object#hashCode()}.
 *
 * @param <T> The type of elements held in this set.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class JDKHashSet<T> extends AbstractSet<T> implements Set<T> {

  private final HashSet<T> hashSet;

  /**
   * Private constructor to wrap an existing {@code HashSet}.
   */
  private JDKHashSet(HashSet<T> hashSet) {
    this.hashSet = hashSet;
  }

  /**
   * Constructs an empty {@code JDKHashSet} with a specified initial capacity and load factor.
   * <p> Time complexity: O(1)
   */
  public JDKHashSet(int initialCapacity, float loadFactor) {
    this.hashSet = new HashSet<>(initialCapacity, loadFactor);
  }

  /**
   * Constructs an empty {@code JDKHashSet} with default initial capacity and load factor.
   * <p> Time complexity: O(1)
   */
  public JDKHashSet() {
    this.hashSet = new HashSet<>();
  }

  /**
   * Creates an empty {@code JDKHashSet} with a default initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param <T> the type of elements in the set.
   * @return an empty {@code JDKHashSet}.
   */
  public static <T> JDKHashSet<T> empty() {
    return new JDKHashSet<>();
  }

  /**
   * Creates an empty {@code JDKHashSet} that can accommodate a specified number of elements
   * before needing to rehash.
   * <p> Time complexity: O(1)
   *
   * @param <T>      the type of elements.
   * @param capacity the desired initial capacity.
   * @return an empty {@code JDKHashSet} with the given capacity.
   */
  public static <T> JDKHashSet<T> withCapacity(int capacity) {
    return new JDKHashSet<>(new HashSet<>(capacity));
  }

  /**
   * Creates a new {@code JDKHashSet} with the provided elements.
   * <p> Time complexity: Near O(n) on average.
   */
  @SafeVarargs
  public static <T> JDKHashSet<T> of(T... elements) {
    JDKHashSet<T> set = withCapacity(elements.length);
    set.insert(elements);
    return set;
  }

  /**
   * Creates a new {@code JDKHashSet} from an iterable of elements.
   * <p> Time complexity: Near O(n) on average.
   */
  public static <T> JDKHashSet<T> from(Iterable<T> iterable) {
    JDKHashSet<T> set = empty();
    for (T element : iterable) {
      set.insert(element);
    }
    return set;
  }

  /**
   * Returns a new {@code JDKHashSet} with the same elements as the argument.
   * <p> Time complexity: O(n)
   */
  @SuppressWarnings("unchecked")
  public static <T> JDKHashSet<T> copyOf(JDKHashSet<T> that) {
    return new JDKHashSet<>((HashSet<T>) that.hashSet.clone());
  }

  /**
   * Returns a new {@code JDKHashSet} with the same elements as the argument.
   * <p> Time complexity: Near O(n) on average.
   */
  public static <T> JDKHashSet<T> copyOf(Set<T> that) {
    if (that instanceof JDKHashSet<T> hashSet) {
      return copyOf(hashSet);
    }
    JDKHashSet<T> copy = withCapacity(that.size());
    for (T element : that) {
      copy.insert(element);
    }
    return copy;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public boolean isEmpty() {
    return hashSet.isEmpty();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public int size() {
    return hashSet.size();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: Amortized O(1) on average.
   */
  @Override
  public void insert(T element) {
    // For Sets, `add` will replace if the element is already present,
    // which matches our defined contract.
    hashSet.add(element);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1) on average.
   */
  @Override
  public boolean contains(T element) {
    return hashSet.contains(element);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1) on average.
   */
  @Override
  public void delete(T element) {
    hashSet.remove(element);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n) in the worst case.
   */
  @Override
  public void clear() {
    hashSet.clear();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1) for creating the iterator.
   */
  @Override
  public Iterator<T> iterator() {
    return hashSet.iterator();
  }
}