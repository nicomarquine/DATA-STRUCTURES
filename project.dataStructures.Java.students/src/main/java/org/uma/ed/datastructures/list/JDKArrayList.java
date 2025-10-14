package org.uma.ed.datastructures.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * An implementation of the {@link List} interface that acts as an adapter for the
 * standard Java {@link java.util.ArrayList}.
 * <p>
 * This class delegates all of its operations to an internal {@code java.util.ArrayList}
 * instance. It serves as an example of the Adapter design pattern and allows Java's
 * native ArrayList to be used seamlessly within this data structures library.
 *
 * @param <T> The type of elements held in this list.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class JDKArrayList<T> extends AbstractList<T> implements List<T> {

  /**
   * Default initial capacity for the list if none is provided.
   */
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  /**
   * The underlying JDK {@code ArrayList} to which all operations are delegated.
   */
  private final java.util.ArrayList<T> list;

  /**
   * Private constructor to wrap an existing {@code java.util.ArrayList}.
   * @param list The {@code ArrayList} to be used as the internal representation.
   */
  private JDKArrayList(java.util.ArrayList<T> list) {
    this.list = list;
  }

  /**
   * Constructs an empty {@code JDKArrayList} with a specified initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param initialCapacity the initial capacity of the list.
   * @throws IllegalArgumentException if the specified initial capacity is negative.
   */
  public JDKArrayList(int initialCapacity) {
    if (initialCapacity <= 0) {
      throw new IllegalArgumentException("Initial capacity must be greater than 0");
    }
    this.list = new ArrayList<>(initialCapacity);
  }

  /**
   * Constructs an empty {@code JDKArrayList} with a default initial capacity.
   * <p> Time complexity: O(1)
   */
  public JDKArrayList() {
    this(DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Creates an empty {@code JDKArrayList} with a default initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param <T> the type of elements in the list.
   * @return an empty {@code JDKArrayList}.
   */
  public static <T> JDKArrayList<T> empty() {
    return new JDKArrayList<>();
  }

  /**
   * Creates an empty {@code JDKArrayList} with a specified initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param <T>             the type of elements in the list.
   * @param initialCapacity the initial capacity of the list.
   * @return an empty {@code JDKArrayList} with the given capacity.
   * @throws IllegalArgumentException if the initial capacity is negative.
   */
  public static <T> JDKArrayList<T> withCapacity(int initialCapacity) {
    return new JDKArrayList<>(initialCapacity);
  }

  /**
   * Creates a new {@code JDKArrayList} containing the given elements.
   * <p> Time complexity: O(n), where n is the number of elements.
   *
   * @param <T>      the type of elements.
   * @param elements the elements to be stored in the list.
   * @return a new {@code JDKArrayList} with the given elements.
   */
  @SafeVarargs
  public static <T> JDKArrayList<T> of(T... elements) {
    java.util.ArrayList<T> jdkList = new java.util.ArrayList<>(elements.length > 0 ? elements.length : DEFAULT_INITIAL_CAPACITY);
    Collections.addAll(jdkList, elements);
    return new JDKArrayList<>(jdkList);
  }

  /**
   * Creates a new {@code JDKArrayList} from an iterable collection.
   * <p> Time complexity: O(n), where n is the number of elements.
   *
   * @param <T>      the type of elements.
   * @param iterable the iterable containing elements for the list.
   * @return a new {@code JDKArrayList} with the given elements.
   */
  public static <T> JDKArrayList<T> from(Iterable<T> iterable) {
    java.util.ArrayList<T> jdkList = new java.util.ArrayList<>();
    for (T element : iterable) {
      jdkList.add(element);
    }
    return new JDKArrayList<>(jdkList);
  }

  /**
   * Creates a new {@code JDKArrayList} containing the same elements as the given list.
   * <p> Time complexity: O(n)
   *
   * @param <T>  the type of elements.
   * @param that the {@code JDKArrayList} to be copied.
   * @return a new {@code JDKArrayList} with the same elements and order.
   */
  public static <T> JDKArrayList<T> copyOf(JDKArrayList<T> that) {
    return new JDKArrayList<>(new java.util.ArrayList<>(that.list));
  }

  /**
   * Creates a new {@code JDKArrayList} containing the same elements as the given list.
   * <p> Time complexity: O(n)
   *
   * @param <T>  the type of elements.
   * @param that the generic {@code List} to be copied.
   * @return a new {@code JDKArrayList} with the same elements and order.
   */
  public static <T> JDKArrayList<T> copyOf(List<T> that) {
    if (that instanceof JDKArrayList<T> jdkArrayList) {
      // Use the more efficient specialized version if possible.
      return copyOf(jdkArrayList);
    }
    java.util.ArrayList<T> jdkList = new java.util.ArrayList<>(!that.isEmpty() ? that.size() : DEFAULT_INITIAL_CAPACITY);
    for (T element : that) {
      jdkList.add(element);
    }
    return new JDKArrayList<>(jdkList);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void clear() {
    list.clear();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public int size() {
    return list.size();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void insert(int index, T element) {
    try {
      list.add(index, element);
    } catch (IndexOutOfBoundsException e) {
      throw new ListException(e.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void delete(int index) {
    try {
      list.remove(index);
    } catch (IndexOutOfBoundsException e) {
      throw new ListException(e.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public T get(int index) {
    try {
      return list.get(index);
    } catch (IndexOutOfBoundsException e) {
      throw new ListException(e.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void set(int index, T element) {
    try {
      list.set(index, element);
    } catch (IndexOutOfBoundsException e) {
      throw new ListException(e.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: Amortized O(1)
   */
  @Override
  public void append(T element) {
    list.add(element);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void prepend(T element) {
    list.addFirst(element); // Requires Java 21+
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<T> iterator() {
    return list.iterator();
  }
}