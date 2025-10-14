package org.uma.ed.datastructures.queue;

import java.util.ArrayDeque;

/**
 * An implementation of the {@link Queue} interface using {@link java.util.ArrayDeque} as
 * the underlying data structure.
 * <p>
 * This class serves as an adapter, mapping the {@code Queue} interface operations to the
 * corresponding methods of {@code ArrayDeque}. Using {@code ArrayDeque} is the recommended
 * approach for queue implementations in modern Java.
 *
 * @param <T> The type of elements held in this queue.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class JDKQueue<T> extends AbstractQueue<T> implements Queue<T> {

  /**
   * Default initial capacity for the queue if none is provided.
   */
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  /**
   * The underlying {@code ArrayDeque} instance storing the queue's elements.
   */
  private final ArrayDeque<T> elements;

  /**
   * Private constructor to wrap an existing {@code ArrayDeque}.
   * @param elements The {@code ArrayDeque} to be used as the internal representation.
   */
  private JDKQueue(ArrayDeque<T> elements) {
    this.elements = elements;
  }

  /**
   * Constructs an empty {@code JDKQueue} with a specified initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param initialCapacity the initial capacity hint for the queue.
   * @throws IllegalArgumentException if the specified initial capacity is negative.
   */
  public JDKQueue(int initialCapacity) {
    if (initialCapacity <= 0) {
      throw new IllegalArgumentException("Initial capacity must be greater than 0");
    }
    this.elements = new ArrayDeque<>(initialCapacity);
  }

  /**
   * Constructs an empty {@code JDKQueue} with a default initial capacity.
   * <p> Time complexity: O(1)
   */
  public JDKQueue() {
    this(DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Creates an empty {@code JDKQueue} with a default initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param <T> the type of elements in the queue.
   * @return an empty {@code JDKQueue}.
   */
  public static <T> JDKQueue<T> empty() {
    return new JDKQueue<>();
  }

  /**
   * Creates an empty {@code JDKQueue} with a specified initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param <T>             the type of elements in the queue.
   * @param initialCapacity the initial capacity hint for the queue.
   * @return an empty {@code JDKQueue} with the given capacity.
   */
  public static <T> JDKQueue<T> withCapacity(int initialCapacity) {
    return new JDKQueue<>(initialCapacity);
  }

  /**
   * Creates a new {@code JDKQueue} containing the given elements.
   * <p> Time complexity: O(n), where n is the number of elements.
   *
   * @param <T>      the type of elements.
   * @param elements the elements to be enqueued.
   * @return a new {@code JDKQueue} with the given elements.
   */
  @SafeVarargs
  public static <T> JDKQueue<T> of(T... elements) {
    JDKQueue<T> queue = new JDKQueue<>(elements.length > 0 ? elements.length : DEFAULT_INITIAL_CAPACITY);
    for (T element : elements) {
      queue.enqueue(element);
    }
    return queue;
  }

  /**
   * Creates a new {@code JDKQueue} from an iterable collection.
   * <p> Time complexity: O(n), where n is the number of elements.
   *
   * @param <T>      the type of elements.
   * @param iterable the iterable containing elements to be enqueued.
   * @return a new {@code JDKQueue} with the given elements.
   */
  public static <T> JDKQueue<T> from(Iterable<T> iterable) {
    JDKQueue<T> queue = new JDKQueue<>();
    for (T element : iterable) {
      queue.enqueue(element);
    }
    return queue;
  }

  /**
   * Creates a new {@code JDKQueue} containing the same elements as the given queue.
   * <p>
   * This is an efficient O(n) operation that uses the {@code ArrayDeque} copy constructor.
   * <p> Time complexity: O(n)
   *
   * @param <T>  the type of elements.
   * @param that the {@code JDKQueue} to be copied.
   * @return a new {@code JDKQueue} with the same elements and order.
   */
  public static <T> JDKQueue<T> copyOf(JDKQueue<T> that) {
    ArrayDeque<T> elements = new ArrayDeque<>(that.elements);
    return new JDKQueue<>(elements);
  }

  /**
   * Creates a new {@code JDKQueue} containing the same elements as the given queue.
   * <p>
   * Note: This operation temporarily modifies the source queue by dequeueing all its
   * elements, but restores it to its original state before returning. The efficiency of this
   * method depends heavily on the implementation of the source queue.
   * <p> Time complexity: O(n * T_first + n * T_enqueue + n * T_dequeue),
   * where T_... is the complexity of the source queue's operations.
   *
   * @param <T>  the type of elements.
   * @param that the generic {@code Queue} to be copied.
   * @return a new {@code JDKQueue} with the same elements and order.
   */
  public static <T> JDKQueue<T> copyOf(Queue<T> that) {
    if (that instanceof JDKQueue<T> jdkQueue) {
      // Use the more efficient specialized version if possible.
      return copyOf(jdkQueue);
    }
    ArrayDeque<T> deque = new ArrayDeque<>(!that.isEmpty() ? that.size() : DEFAULT_INITIAL_CAPACITY);
    while (!that.isEmpty()) {
      deque.addLast(that.first()); // `addLast`, preserving FIFO order.
      that.dequeue();
    }

    // Restore the original contents of `that`.
    for (T element : deque) {
      that.enqueue(element);
    }

    return new JDKQueue<>(deque);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public boolean isEmpty() {
    return elements.isEmpty();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public int size() {
    return elements.size();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: Amortized O(1)
   */
  @Override
  public void enqueue(T element) {
    elements.addLast(element);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public T first() {
    if (isEmpty()) {
      throw new EmptyQueueException("first on empty queue");
    }
    return elements.peekFirst();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void dequeue() {
    if (isEmpty()) {
      throw new EmptyQueueException("dequeue on empty queue");
    }
    elements.pollFirst();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void clear() {
    elements.clear();
  }

  /**
   * Provides an iterable that traverses the elements from the front to the rear of the queue.
   *
   * @return an {@code Iterable} for the elements of the queue.
   */
  @Override
  protected Iterable<T> elements() {
    return this.elements;
  }
}