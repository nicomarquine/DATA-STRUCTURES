package org.uma.ed.datastructures.stack;

import java.util.Arrays;

/**
 * An implementation of the {@link Stack} interface using a dynamic array.
 * <p>
 * This class provides a LIFO (Last-In-First-Out) stack of elements. The capacity of the
 * underlying array is automatically increased as needed, but it does not shrink.
 *
 * @param <T> The type of elements held in this stack.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class ArrayStack<T> extends AbstractStack<T> implements Stack<T> {

  /**
   * Default initial capacity for the stack if none is provided.
   */
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  /**
   * Array buffer to store the elements of the stack.
   */
  private T[] elements;

  /**
   * The number of elements in the stack. Also serves as the index for the next push.
   */
  private int size;

  /*
   * INVARIANT:
   *  - The size field holds the number of elements currently in the stack.
   *  - The elements are stored in the elements array from bottom to top, occupying indices 0 to size - 1.
   *  - The element at the top of the stack is located at elements[size - 1].
   *  - All array positions from size to elements.length - 1 are unused.
   */

  /**
   * Constructs an empty {@code ArrayStack} with a specified initial capacity.
   * <p>
   * The capacity is automatically increased when needed.
   * <p> Time complexity: O(1)
   *
   * @param initialCapacity the initial capacity of the underlying array.
   * @throws IllegalArgumentException if the specified initial capacity is not positive.
   */
  @SuppressWarnings("unchecked")
  public ArrayStack(int initialCapacity) {
    if (initialCapacity <= 0) {
      throw new IllegalArgumentException("Initial capacity must be greater than 0");
    }
    this.elements = (T[]) new Object[initialCapacity];
    this.size = 0;
  }

  /**
   * Constructs an empty {@code ArrayStack} with a default initial capacity.
   * <p> Time complexity: O(1)
   */
  public ArrayStack() {
    this(DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Creates an empty {@code ArrayStack} with a default initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param <T> the type of elements in the stack.
   * @return an empty {@code ArrayStack}.
   */
  public static <T> ArrayStack<T> empty() {
    return new ArrayStack<>();
  }

  /**
   * Creates an empty {@code ArrayStack} with a specified initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param <T>             the type of elements in the stack.
   * @param initialCapacity the initial capacity for the stack.
   * @return an empty {@code ArrayStack} with the given capacity.
   * @throws IllegalArgumentException if the initial capacity is not positive.
   */
  public static <T> ArrayStack<T> withCapacity(int initialCapacity) {
    return new ArrayStack<>(initialCapacity);
  }

  /**
   * Creates a new {@code ArrayStack} containing the given elements.
   * <p>
   * The elements are pushed onto the stack in the order they appear in the arguments.
   * Note that due to the LIFO (Last-In-First-Out) nature of a stack, the last
   * element in the argument list will be at the top of the stack and will be the first
   * element shown by {@code toString()}. For example, {@code ArrayStack.of(1, 2, 3)}
   * results in a stack represented as {@code "ArrayStack(3, 2, 1)"}.
   * <p> Time complexity: O(n), where n is the number of elements.
   *
   * @param <T>      the type of elements.
   * @param elements the elements to be stored in the stack.
   * @return a new {@code ArrayStack} with the given elements.
   */
  @SafeVarargs
  public static <T> ArrayStack<T> of(T... elements) {
    ArrayStack<T> stack = new ArrayStack<>(elements.length > 0 ? elements.length : DEFAULT_INITIAL_CAPACITY);
    for (T element : elements) {
      stack.push(element);
    }
    return stack;
  }

  /**
   * Creates a new {@code ArrayStack} from an iterable collection.
   * <p>
   * The elements are pushed onto the stack in the order they are returned by the iterable's iterator.
   * <p> Time complexity: O(n), where n is the number of elements.
   *
   * @param <T>      the type of elements.
   * @param iterable the iterable containing elements to be pushed onto the stack.
   * @return a new {@code ArrayStack} with the given elements.
   */
  public static <T> ArrayStack<T> from(Iterable<T> iterable) {
    ArrayStack<T> stack = new ArrayStack<>();
    for (T element : iterable) {
      stack.push(element);
    }
    return stack;
  }

  /**
   * Creates a new {@code ArrayStack} containing the same elements as the given stack.
   * <p>
   * This is an efficient O(n) operation that uses {@code System.arraycopy}.
   * <p> Time complexity: O(n)
   *
   * @param <T>  the type of elements.
   * @param that the {@code ArrayStack} to be copied.
   * @return a new {@code ArrayStack} with the same elements and order.
   */
  public static <T> ArrayStack<T> copyOf(ArrayStack<T> that) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Creates a new {@code ArrayStack} containing the same elements as the given stack.
   * <p>
   * Note: This operation temporarily modifies the source stack by popping all its
   * elements, but restores it to its original state before returning. The efficiency of this
   * method depends heavily on the implementation of the source stack.
   * <p> Time complexity: O(n * T_top + n * T_pop + n * T_push),
   * where T_... is the complexity of the source stack's operations.
   *
   * @param <T>  the type of elements.
   * @param that the generic {@code Stack} to be copied.
   * @return a new {@code ArrayStack} with the same elements and order.
   */
  public static <T> ArrayStack<T> copyOf(Stack<T> that) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public int size() { return size; }

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
   * {@inheritDoc}
   * <p> Time complexity: Amortized O(1). A single push may take O(n) if the
   * underlying array needs to be resized.
   */
  @Override
  public void push(T element) {
    ensureCapacity();
    elements[size] = element;
    size++;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public T top() {
    if(size <= 0){
      throw new EmptyStackException("top on empty stack");
    }
    return elements[size-1]; }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void pop() {
    if(size <=0){
      throw new EmptyStackException("pop on empty stack");
    }
    elements[size-1] = null;
    size--;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void clear() {
    for(int i = 0 ; i <size;i++){
      elements[i] = null;
    }
    size = 0;
  }

  /**
   * Provides an iterable that traverses the elements from the top of the stack to the bottom.
   *
   * @return an {@code Iterable} for the elements of the stack.
   */
  @Override
  protected Iterable<T> elements() {
    return () -> new java.util.Iterator<>() {
      private int current = size - 1;

      @Override
      public boolean hasNext() {
        return current >= 0;
      }

      @Override
      public T next() {
        if (!hasNext()) {
          throw new java.util.NoSuchElementException();
        }
        T element = elements[current];
        current--;
        return element;
      }
    };
  }
}