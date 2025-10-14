package org.uma.ed.datastructures.stack;

import java.util.ArrayDeque;
import java.util.Iterator;

/**
 * An implementation of the {@link Stack} interface using {@link java.util.ArrayDeque} as
 * the underlying data structure.
 * <p>
 * This class serves as an adapter, mapping the {@code Stack} interface operations to the
 * corresponding methods of {@code ArrayDeque}. Using {@code ArrayDeque} is the recommended
 * approach for stack implementations in modern Java, in preference to the legacy
 * {@code java.util.Stack} class.
 *
 * @param <T> The type of elements held in this stack.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class JDKStack<T> extends AbstractStack<T> implements Stack<T> {

  /**
   * Default initial capacity for the stack if none is provided.
   */
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  /**
   * The underlying {@code ArrayDeque} instance storing the stack's elements.
   */
  private final ArrayDeque<T> elements;

  /**
   * Private constructor to wrap an existing {@code ArrayDeque}.
   * @param elements The {@code ArrayDeque} to be used as the internal representation.
   */
  private JDKStack(ArrayDeque<T> elements) {
    this.elements = elements;
  }

  /**
   * Constructs an empty {@code JDKStack} with a specified initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param initialCapacity the initial capacity of the stack.
   * @throws IllegalArgumentException if the specified initial capacity is not positive.
   */
  public JDKStack(int initialCapacity) {
    if (initialCapacity <= 0) {
      throw new IllegalArgumentException("Initial capacity must be greater than 0");
    }
    this.elements = new ArrayDeque<>(initialCapacity);
  }

  /**
   * Constructs an empty {@code JDKStack} with a default initial capacity.
   * <p> Time complexity: O(1)
   */
  public JDKStack() {
    this(DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Creates an empty {@code JDKStack} with a default initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param <T> the type of elements in the stack.
   * @return an empty {@code JDKStack}.
   */
  public static <T> JDKStack<T> empty() {
    return new JDKStack<>();
  }

  /**
   * Creates an empty {@code JDKStack} with a specified initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param <T>             the type of elements in the stack.
   * @param initialCapacity the initial capacity for the stack.
   * @return an empty {@code JDKStack} with the given capacity.
   * @throws IllegalArgumentException if the initial capacity is not positive.
   */
  public static <T> JDKStack<T> withCapacity(int initialCapacity) {
    return new JDKStack<>(initialCapacity);
  }

  /**
   * Creates a new {@code JDKStack} containing the given elements.
   * <p>
   * The elements are pushed onto the stack in the order they appear in the arguments.
   * Note that due to the LIFO (Last-In-First-Out) nature of a stack, the last
   * element in the argument list will be at the top of the stack and will be the first
   * element shown by {@code toString()}. For example, {@code JDKStack.of(1, 2, 3)}
   * results in a stack represented as {@code "JDKStack(3, 2, 1)"}.
   * <p> Time complexity: O(n), where n is the number of elements.
   *
   * @param <T>      the type of elements.
   * @param elements the elements to be stored in the stack.
   * @return a new {@code JDKStack} with the given elements.
   */
  @SafeVarargs
  public static <T> JDKStack<T> of(T... elements) {
    ArrayDeque<T> deque = new ArrayDeque<>(elements.length > 0 ? elements.length : DEFAULT_INITIAL_CAPACITY);
    for (T element : elements) {
      deque.push(element);
    }
    return new JDKStack<>(deque);
  }

  /**
   * Creates a new {@code JDKStack} from an iterable collection.
   * <p>
   * The elements are pushed onto the stack in the order they are returned by the iterable's iterator.
   * <p> Time complexity: O(n), where n is the number of elements.
   *
   * @param <T>      the type of elements.
   * @param iterable the iterable containing elements to be pushed onto the stack.
   * @return a new {@code JDKStack} with the given elements.
   */
  public static <T> JDKStack<T> from(Iterable<T> iterable) {
    ArrayDeque<T> deque = new ArrayDeque<>();
    for (T element : iterable) {
      deque.push(element);
    }
    return new JDKStack<>(deque);
  }

  /**
   * Creates a new {@code JDKStack} containing the same elements as the given stack.
   * <p>
   * This is an efficient O(n) operation that uses the {@code ArrayDeque} copy constructor.
   * <p> Time complexity: O(n)
   *
   * @param <T>  the type of elements.
   * @param that the {@code JDKStack} to be copied.
   * @return a new {@code JDKStack} with the same elements and order.
   */
  public static <T> JDKStack<T> copyOf(JDKStack<T> that) {
    ArrayDeque<T> deque = new ArrayDeque<>(that.elements);
    return new JDKStack<>(deque);
  }

  /**
   * Creates a new {@code JDKStack} containing the same elements as the given stack.
   * <p>
   * Note: This operation temporarily modifies the source stack by popping all its
   * elements, but restores it to its original state before returning. The efficiency of this
   * method depends heavily on the implementation of the source stack.
   * <p> Time complexity: O(n * T_top + n * T_pop + n * T_push),
   * where T_... is the complexity of the source stack's operations.
   *
   * @param <T>  the type of elements.
   * @param that the generic {@code Stack} to be copied.
   * @return a new {@code JDKStack} with the same elements and order.
   */
  public static <T> JDKStack<T> copyOf(Stack<T> that) {
    if (that instanceof JDKStack<T> jdkStack) {
      // use specialized version for JDKStack
      return copyOf(jdkStack);
    }
    ArrayDeque<T> deque = new ArrayDeque<>(!that.isEmpty() ? that.size() : DEFAULT_INITIAL_CAPACITY);
    while (!that.isEmpty()) {
      deque.addLast(that.top()); // Add to the end, creating a reversed-order copy
      that.pop();
    }
    // Restore original contents of that using descending iterator
    Iterator<T> iterator = deque.descendingIterator();
    while (iterator.hasNext()) {
      that.push(iterator.next());
    }
    return new JDKStack<>(deque);
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
  public void push(T element) {
    elements.addFirst(element);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public T top() {
    if (isEmpty()) {
      throw new EmptyStackException("top on empty stack");
    }
    return elements.peekFirst();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void pop() {
    if (isEmpty()) {
      throw new EmptyStackException("pop on empty stack");
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
   * Provides an iterable that traverses the elements from the top of the stack to the bottom.
   *
   * @return an {@code Iterable} for the elements of the stack.
   */
  @Override
  protected Iterable<T> elements() {
    return this.elements;
  }
}