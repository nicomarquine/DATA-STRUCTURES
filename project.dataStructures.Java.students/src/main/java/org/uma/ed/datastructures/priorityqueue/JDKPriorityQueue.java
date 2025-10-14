package org.uma.ed.datastructures.priorityqueue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.util.Comparator.naturalOrder;

/**
 * An implementation of the {@link PriorityQueue} interface that acts as an adapter for
 * the standard Java {@link java.util.PriorityQueue}.
 * <p>
 * This class delegates all of its operations to an internal {@code java.util.PriorityQueue}
 * instance. Java's {@code PriorityQueue} is implemented using a binary heap, providing
 * O(log n) time complexity for {@code enqueue} (offer) and {@code dequeue} (poll) operations,
 * and O(1) for {@code first} (peek).
 * <p>
 * This class serves as a bridge, allowing the highly-optimized JDK implementation to be
 * used seamlessly within this data structures library.
 *
 * @param <T> The type of elements held in this priority queue.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class JDKPriorityQueue<T> extends AbstractPriorityQueue<T> implements PriorityQueue<T> {

  /**
   * Default initial capacity for the heap.
   */
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  /**
   * The underlying JDK {@code PriorityQueue} to which all operations are delegated.
   */
  private final java.util.PriorityQueue<T> priorityQueue;

  /**
   * Private constructor to wrap an existing {@code java.util.PriorityQueue}.
   * @param priorityQueue The {@code java.util.PriorityQueue} to be used as the internal representation.
   */
  private JDKPriorityQueue(java.util.PriorityQueue<T> priorityQueue) {
    this.priorityQueue = priorityQueue;
  }

  /**
   * Constructor to create a {@code JDKPriorityQueue} with a specified comparator and initial capacity.
   */
  public JDKPriorityQueue(Comparator<T> comparator, int initialCapacity) {
    if (initialCapacity <= 0) {
      throw new IllegalArgumentException("Initial capacity must be greater than 0");
    }
    this.priorityQueue = new java.util.PriorityQueue<>(initialCapacity, comparator);
  }

  /**
   * Constructs an empty {@code JDKPriorityQueue} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public JDKPriorityQueue(Comparator<T> comparator) {
    this(comparator, DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Creates an empty {@code JDKPriorityQueue} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public static <T> JDKPriorityQueue<T> empty(Comparator<T> comparator) {
    return new JDKPriorityQueue<>(comparator);
  }

  /**
   * Creates an empty {@code JDKPriorityQueue} with natural ordering.
   * <p> Time complexity: O(1)
   */
  public static <T extends Comparable<? super T>> JDKPriorityQueue<T> empty() {
    return new JDKPriorityQueue<T>(naturalOrder());
  }

  /**
   * Creates an empty {@code JDKPriorityQueue} with a specified comparator and initial capacity.
   * <p> Time complexity: O(1)
   */
  public static <T> JDKPriorityQueue<T> withCapacity(Comparator<T> comparator, int initialCapacity) {
    return new JDKPriorityQueue<>(comparator, initialCapacity);
  }

  /**
   * Creates an empty {@code JDKPriorityQueue} with natural ordering and a specified initial capacity.
   * <p> Time complexity: O(1)
   */
  public static <T extends Comparable<? super T>> JDKPriorityQueue<T> withCapacity(int initialCapacity) {
    return  new JDKPriorityQueue<T>(naturalOrder(), initialCapacity);
  }

  /**
   * Creates a new {@code JDKPriorityQueue} from the given elements.
   * <p> Time complexity: O(n log n), due to repeated insertions.
   */
  @SafeVarargs
  public static <T> JDKPriorityQueue<T> of(Comparator<T> comparator, T... elements) {
    JDKPriorityQueue<T> queue = new JDKPriorityQueue<>(comparator, elements.length > 0 ? elements.length : DEFAULT_INITIAL_CAPACITY);
    for (T elem : elements) {
      queue.enqueue(elem);
    }
    return queue;
  }

  /**
   * Creates a new {@code JDKPriorityQueue} from the given elements with natural ordering.
   * <p> Time complexity: O(n log n)
   */
  @SafeVarargs
  public static <T extends Comparable<? super T>> JDKPriorityQueue<T> of(T... elements) {
    return of(naturalOrder(), elements);
  }

  /**
   * Creates a new {@code JDKPriorityQueue} from an iterable collection.
   * <p> Time complexity: O(n log n)
   */
  public static <T> JDKPriorityQueue<T> from(Comparator<T> comparator, Iterable<T> iterable) {
    JDKPriorityQueue<T> queue = new JDKPriorityQueue<>(comparator);
    for (T elem : iterable) {
      queue.enqueue(elem);
    }
    return queue;
  }

  /**
   * Creates a new {@code JDKPriorityQueue} from an iterable with natural ordering.
   * <p> Time complexity: O(n log n)
   */
  public static <T extends Comparable<? super T>> JDKPriorityQueue<T> from(Iterable<T> iterable) {
    return from(naturalOrder(), iterable);
  }

  /**
   * Creates a new {@code JDKPriorityQueue} containing the same elements as the given queue.
   * <p> Time complexity: O(n)
   */
  public static <T> JDKPriorityQueue<T> copyOf(JDKPriorityQueue<T> queue) {
    return new JDKPriorityQueue<>(new java.util.PriorityQueue<>(queue.priorityQueue));
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  @SuppressWarnings("unchecked")
  public Comparator<T> comparator() {
    Comparator<? super T> cmp = priorityQueue.comparator();
    return (cmp != null) ? (Comparator<T>) cmp
                         : (Comparator<T>) Comparator.naturalOrder();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public boolean isEmpty() {
    return priorityQueue.isEmpty();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void clear() {
    priorityQueue.clear();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public int size() {
    return priorityQueue.size();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public void enqueue(T element) {
    priorityQueue.offer(element);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public T first() {
    if (isEmpty()) {
      throw new EmptyPriorityQueueException("first on empty priority queue");
    }
    return priorityQueue.peek();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public void dequeue() {
    if (isEmpty()) {
      throw new EmptyPriorityQueueException("dequeue on empty priority queue");
    }
    // `poll()` is the correct method to remove the head.
    priorityQueue.poll();
  }

  /**
   * Provides an iterable that traverses the elements in priority order.
   * <p>
   * Note: This operation is expensive (O(n log n)) as it requires creating a
   * full copy of the priority queue and draining it.
   *
   * @return an {@code Iterable} for the elements of the priority queue.
   */
  @Override
  protected Iterable<T> elements() {
    return () -> new Iterator<>() {
      // Create a copy to avoid destroying the original queue during iteration.
      private final java.util.PriorityQueue<T> copy = new java.util.PriorityQueue<>(priorityQueue);

      @Override
      public boolean hasNext() {
        return !copy.isEmpty();
      }

      @Override
      public T next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        // poll() retrieves and removes the head (minimum element).
        return copy.poll();
      }
    };
  }
}