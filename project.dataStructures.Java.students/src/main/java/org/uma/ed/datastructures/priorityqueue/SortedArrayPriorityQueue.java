package org.uma.ed.datastructures.priorityqueue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of the {@link PriorityQueue} interface using a sorted dynamic array.
 * <p>
 * This implementation maintains its elements in non-ascending order of priority.
 * The element with the highest priority (the smallest value) is kept at the end of the array
 * to allow for constant-time O(1) {@code first()} and {@code dequeue()} operations.
 * <p>
 * The {@code enqueue(T element)} operation has a linear time complexity O(n), as it may
 * require shifting elements to maintain the sorted order.
 *
 * @param <T> The type of elements held in this priority queue.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class SortedArrayPriorityQueue<T> extends AbstractPriorityQueue<T> implements PriorityQueue<T> {

  /**
   * Default initial capacity for the underlying array.
   */
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  /**
   * Comparator provided by the user to define the priority order of elements.
   */
  private final Comparator<T> comparator;

  /**
   * Comparator used for maintaining the sorted order in reversed priority.
   * This is the reverse of the comparator provided by the user.
   */
  private final Comparator<T> reversedComparator;
  private T[] elements;
  private int size;

  /*
   * INVARIANT:
   *  - The elements are stored in `elements[0...size-1]`.
   *  - The array is sorted in NON-ASCENDING (descending) order of priority.
   *    (i.e., from lowest priority to highest priority).
   *  - The element with the highest priority (minimum value) is at `elements[size - 1]`.
   */

  /**
   * Constructs an empty {@code SortedArrayPriorityQueue} with a specified comparator and initial capacity.
   * <p>Time complexity: O(1)</p>
   */
  @SuppressWarnings("unchecked")
  public SortedArrayPriorityQueue(Comparator<T> comparator, int initialCapacity) {
    if (initialCapacity <= 0) {
      throw new IllegalArgumentException("Initial capacity must be greater than 0");
    }
    this.comparator = comparator;
    this.reversedComparator = comparator.reversed();
    this.elements = (T[]) new Object[initialCapacity];
    this.size = 0;
  }

  /**
   * Constructs an empty {@code SortedArrayPriorityQueue} with a specified comparator.
   * <p>Time complexity: O(1)</p>
   */
  public SortedArrayPriorityQueue(Comparator<T> comparator) {
    this(comparator, DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Creates an empty {@code SortedArrayPriorityQueue} with natural ordering.
   * <p>Time complexity: O(1)</p>
   */
  public static <T extends Comparable<? super T>> SortedArrayPriorityQueue<T> empty() {
    return new SortedArrayPriorityQueue<T>(Comparator.naturalOrder());
  }

  /**
   * Creates an empty {@code SortedArrayPriorityQueue} with a specified comparator.
   * <p>Time complexity: O(1)</p>
   */
  public static <T> SortedArrayPriorityQueue<T> empty(Comparator<T> comparator) {
    return new SortedArrayPriorityQueue<>(comparator);
  }

  /**
   * Creates a new {@code SortedArrayPriorityQueue} from the given elements.
   * <p>Time complexity: O(m^2) in the worst case due to repeated linear-time insertions.</p>
   */
  @SafeVarargs
  public static <T> SortedArrayPriorityQueue<T> of(Comparator<T> comparator, T... elements) {
    SortedArrayPriorityQueue<T> pq = new SortedArrayPriorityQueue<>(comparator);
    for (T element : elements) {
      pq.enqueue(element);
    }
    return pq;
  }

  /**
   * Creates a new {@code SortedArrayPriorityQueue} from the given elements with natural ordering.
   * <p>Time complexity: O(m^2) in the worst case.</p>
   */
  @SafeVarargs
  public static <T extends Comparable<? super T>> SortedArrayPriorityQueue<T> of(T... elements) {
    return of(Comparator.naturalOrder(), elements);
  }

  /**
   * Creates a new {@code SortedArrayPriorityQueue} from an iterable.
   * <p>Time complexity: O(n log n) due to sorting, which is more efficient than O(n^2).</p>
   */
  public static <T> SortedArrayPriorityQueue<T> from(Comparator<T> comparator, Iterable<T> iterable) {
    // More efficient O(n log n) construction by adding all and then sorting.
    java.util.ArrayList<T> tempList = new java.util.ArrayList<>();
    iterable.forEach(tempList::add);

    tempList.sort(comparator.reversed());

    SortedArrayPriorityQueue<T> pq = new SortedArrayPriorityQueue<>(comparator, !tempList.isEmpty() ? tempList.size() : DEFAULT_INITIAL_CAPACITY);
    if (!tempList.isEmpty()) {
      for (int i = 0; i < tempList.size(); i++) {
        pq.elements[i] = tempList.get(i);
      }
      pq.size = tempList.size();
    }
    return pq;
  }

  /**
   * Creates a new {@code SortedArrayPriorityQueue} from an iterable with natural ordering.
   * <p>Time complexity: O(n log n).</p>
   */
  public static <T extends Comparable<? super T>> SortedArrayPriorityQueue<T> from(Iterable<T> iterable) {
    return from(Comparator.naturalOrder(), iterable);
  }

  /**
   * Creates a new {@code SortedArrayPriorityQueue} that is a copy of the given one.
   * <p>Time complexity: O(n)</p>
   */
  public static <T> SortedArrayPriorityQueue<T> copyOf(SortedArrayPriorityQueue<T> queue) {
    SortedArrayPriorityQueue<T> copy = new SortedArrayPriorityQueue<>(queue.comparator, queue.elements.length);
    copy.size = queue.size;
    System.arraycopy(queue.elements, 0, copy.elements, 0, queue.size);
    return copy;
  }

  @Override
  public Comparator<T> comparator() {
    return comparator;
  }

  @Override
  public boolean isEmpty() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public int size() {
    return size;
  }

  private void ensureCapacity() {
    if (size == elements.length) {
      elements = Arrays.copyOf(elements, size * 2);
    }
  }

  /**
   * {@inheritDoc}
   * This operation uses binary search to find the insertion point (O(log n)) but requires
   * shifting elements (O(n)), making the total complexity O(n).
   * <p>Time complexity: O(n)</p>
   */
  @Override
  public void enqueue(T element) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  /**
   * {@inheritDoc}
   * The highest priority element is at the end of the array.
   * <p>Time complexity: O(1)</p>
   */
  @Override
  public T first() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  /**
   * {@inheritDoc}
   * The highest priority element is at the end of the array.
   * <p>Time complexity: O(1)</p>
   */
  @Override
  public void dequeue() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  /**
   * Provides an iterable that traverses the elements in priority order (from highest to lowest).
   */
  @Override
  protected Iterable<T> elements() {
    return () -> new Iterator<>() {
      private int currentIndex = size - 1; // Start from the end (highest priority)

      @Override
      public boolean hasNext() {
        return currentIndex >= 0;
      }

      @Override
      public T next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        return elements[currentIndex--];
      }
    };
  }
}