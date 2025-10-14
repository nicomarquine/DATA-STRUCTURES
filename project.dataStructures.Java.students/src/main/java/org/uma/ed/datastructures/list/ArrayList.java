package org.uma.ed.datastructures.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A resizable-array implementation of the {@link List} interface.
 * <p>
 * This class provides a dynamic array that grows as more elements are added.
 * It offers constant time performance for size, isEmpty, get, and set operations (amortized O(1)
 * for append), but O(n) for operations that require shifting elements, such as insert and
 * delete at arbitrary positions.
 *
 * @param <T> The type of elements held in this list.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class ArrayList<T> extends AbstractList<T> implements List<T> {

  /**
   * Default initial capacity for the list if none is provided.
   */
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  /**
   * The array buffer into which the elements of the ArrayList are stored.
   */
  private T[] elements;

  /**
   * The number of elements contained in this ArrayList.
   */
  private int size;

  /*
   * INVARIANT:
   *  - `size` holds the number of elements in the list.
   *  - The elements of the list are stored in `elements[0...size-1]`.
   *  - `elements.length` is the capacity of the array, which is always >= `size`.
   */

  /**
   * Constructs an empty {@code ArrayList} with a specified initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param initialCapacity the initial capacity of the underlying array.
   * @throws IllegalArgumentException if the specified initial capacity is not positive.
   */
  @SuppressWarnings("unchecked")
  public ArrayList(int initialCapacity) {
    if (initialCapacity <= 0) {
      throw new IllegalArgumentException("Initial capacity must be greater than 0");
    }
    this.elements = (T[]) new Object[initialCapacity];
    this.size = 0;
  }

  /**
   * Constructs an empty {@code ArrayList} with a default initial capacity.
   * <p> Time complexity: O(1)
   */
  public ArrayList() {
    this(DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Creates an empty {@code ArrayList} with a default initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param <T> the type of elements in the list.
   * @return an empty {@code ArrayList}.
   */
  public static <T> ArrayList<T> empty() {
    return new ArrayList<>();
  }

  /**
   * Creates an empty {@code ArrayList} with a specified initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param <T>             the type of elements in the list.
   * @param initialCapacity the initial capacity for the list.
   * @return an empty {@code ArrayList} with the given capacity.
   * @throws IllegalArgumentException if the initial capacity is not positive.
   */
  public static <T> ArrayList<T> withCapacity(int initialCapacity) {
    return new ArrayList<>(initialCapacity);
  }

  /**
   * Creates a new {@code ArrayList} containing the given elements.
   * <p> Time complexity: O(n), where n is the number of elements.
   *
   * @param <T>      the type of elements.
   * @param elements the elements to be stored in the list.
   * @return a new {@code ArrayList} with the given elements.
   */
  @SafeVarargs
  public static <T> ArrayList<T> of(T... elements) {
    ArrayList<T> list = new ArrayList<>(elements.length > 0 ? elements.length : DEFAULT_INITIAL_CAPACITY);
    for (T element : elements) {
      list.elements[list.size] = element;
      list.size++;
    }
    return list;
  }

  /**
   * Creates a new {@code ArrayList} from an iterable collection.
   * <p> Time complexity: O(n), where n is the number of elements.
   *
   * @param <T>      the type of elements.
   * @param iterable the iterable containing elements for the list.
   * @return a new {@code ArrayList} with the given elements.
   */
  public static <T> ArrayList<T> from(Iterable<T> iterable) {
    ArrayList<T> list = new ArrayList<>();
    for (T element : iterable) {
      list.append(element);
    }
    return list;
  }

  /**
   * Creates a new {@code ArrayList} containing the same elements as the given list.
   * <p> Time complexity: O(n)
   *
   * @param <T>  the type of elements.
   * @param that the {@code ArrayList} to be copied.
   * @return a new {@code ArrayList} with the same elements and order.
   */
  public static <T> ArrayList<T> copyOf(ArrayList<T> that) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Creates a new {@code ArrayList} containing the same elements as the given list.
   * <p> Time complexity: O(n)
   *
   * @param <T>  the type of elements.
   * @param that the generic {@code List} to be copied.
   * @return a new {@code ArrayList} with the same elements and order.
   */
  public static <T> ArrayList<T> copyOf(List<T> that) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void clear() { throw new UnsupportedOperationException("Not implemented yet"); }

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
   * Ensures that the capacity is sufficient to add one more element.
   * If the array is full, it is resized to double its current capacity.
   */
  private void ensureCapacity() {
    if (size >= elements.length) {
      elements = Arrays.copyOf(elements, 2 * elements.length);
    }
  }

  /**
   * Checks if the given index is valid for an access or modification operation.
   * @param index the index to check.
   * @throws ListException if the index is out of range ({@code index < 0 || index >= size()}).
   */
  private void validateIndex(int index) {
    if (index < 0 || index >= size) {
      throw new ListException("Invalid index " + index + " for a list of size " + size);
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void insert(int index, T element) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void delete(int index) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public T get(int index) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void set(int index, T element) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: Amortized O(1).
   */
  @Override
  public void append(T element) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void prepend(T element) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<T> iterator() {
    return new ArrayListIterator();
  }

  /**
   * An iterator for this {@code ArrayList}.
   */
  private final class ArrayListIterator implements Iterator<T> {
    private int currentIndex;

    public ArrayListIterator() {
      this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
      return currentIndex < size;
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException("Iterator has no more elements");
      }
      T element = elements[currentIndex];
      currentIndex++;
      return element;
    }
  }
}