package org.uma.ed.datastructures.stack;

/**
 * An implementation of the {@link Stack} interface using a singly-linked list of nodes.
 * <p>
 * This class provides a LIFO (Last-In-First-Out) stack of elements. The top of the stack
 * corresponds to the head of the linked list, which makes push and pop operations highly
 * efficient. The stack's capacity is dynamic and grows as elements are added.
 *
 * @param <T> The type of elements held in this stack.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class LinkedStack<T> extends AbstractStack<T> implements Stack<T> {

  /**
   * Internal class representing a node in the linked structure.
   * @param <E> The type of the element stored in the node.
   */
  private static final class Node<E> {
    E element;
    Node<E> next;

    Node(E element, Node<E> next) {
      this.element = element;
      this.next = next;
    }
  }

  /**
   * Reference to the node at the top of the stack. It is {@code null} if the stack is empty.
   */
  private Node<T> top;

  /**
   * The number of elements in the stack.
   */
  private int size;

  /*
   * INVARIANT:
   *  - The size field holds the number of elements currently in the stack.
   *  - If the stack is empty, top is null.
   *  - If the stack is not empty, top references the first node (the top element).
   *  - Each node's next field points to the node below it in the stack, with the
   *    bottom-most node's next field being null.
   */

  /**
   * Constructs an empty {@code LinkedStack}.
   * <p> Time complexity: O(1)
   */
  public LinkedStack() {
    this.top = null;
    this.size = 0;
  }

  /**
   * Creates an empty {@code LinkedStack}.
   * <p> Time complexity: O(1)
   *
   * @param <T> the type of elements in the stack.
   * @return an empty {@code LinkedStack}.
   */
  public static <T> LinkedStack<T> empty() {
    return new LinkedStack<>();
  }

  /**
   * Creates a new {@code LinkedStack} containing the given elements.
   * <p>
   * The elements are pushed onto the stack in the order they appear in the arguments.
   * Note that due to the LIFO (Last-In-First-Out) nature of a stack, the last
   * element in the argument list will be at the top of the stack and will be the first
   * element shown by {@code toString()}. For example, {@code LinkedStack.of(1, 2, 3)}
   * results in a stack represented as {@code "LinkedStack(3, 2, 1)"}.
   * <p> Time complexity: O(n), where n is the number of elements.
   *
   * @param <T>      the type of elements.
   * @param elements the elements to be stored in the stack.
   * @return a new {@code LinkedStack} with the given elements.
   */
  @SafeVarargs
  public static <T> LinkedStack<T> of(T... elements) {
    LinkedStack<T> stack = new LinkedStack<>();
    for (T element : elements) {
      stack.push(element);
    }
    return stack;
  }

  /**
   * Creates a new {@code LinkedStack} from an iterable collection.
   * <p>
   * The elements are pushed onto the stack in the order they are returned by the iterable's iterator.
   * <p> Time complexity: O(n), where n is the number of elements.
   *
   * @param <T>      the type of elements.
   * @param iterable the iterable containing elements to be pushed onto the stack.
   * @return a new {@code LinkedStack} with the given elements.
   */
  public static <T> LinkedStack<T> from(Iterable<T> iterable) {
    LinkedStack<T> stack = new LinkedStack<>();
    for (T element : iterable) {
      stack.push(element);
    }
    return stack;
  }

  /**
   * Creates a new {@code LinkedStack} containing the same elements as the given stack.
   * <p>
   * This is an efficient O(n) operation that iterates through the source stack's nodes directly.
   * <p> Time complexity: O(n)
   *
   * @param <T>  the type of elements.
   * @param that the {@code LinkedStack} to be copied.
   * @return a new {@code LinkedStack} with the same elements and order.
   */
  public static <T> LinkedStack<T> copyOf(LinkedStack<T> that) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Creates a new {@code LinkedStack} containing the same elements as the given stack.
   * <p>
   * Note: This operation temporarily modifies the source stack by popping all its
   * elements, but restores it to its original state before returning. The efficiency of this
   * method depends heavily on the implementation of the source stack.
   * <p> Time complexity: O(n * T_top + n * T_pop + n * T_push),
   * where T_... is the complexity of the source stack's operations.
   *
   * @param <T>  the type of elements.
   * @param that the generic {@code Stack} to be copied.
   * @return a new {@code LinkedStack} with the same elements and order.
   */
  public static <T> LinkedStack<T> copyOf(Stack<T> that) { throw new UnsupportedOperationException("Not implemented yet"); }

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
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public T top() {
    if(size <= 0){
      throw new EmptyStackException("top on empty stack");
    }
    return top.element;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void pop() {
    if(size <= 0){
      throw new EmptyStackException("pop on empty stack");
    }
    top = top.next;
    size--;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void push(T element) {
    Node<T> node = new Node<>(element,top);
    top = node;
    size++;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void clear() {
    top = null;
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
      private Node<T> current = top;

      @Override
      public boolean hasNext() {
        return current != null;
      }

      @Override
      public T next() {
        if (!hasNext()) {
          throw new java.util.NoSuchElementException();
        }
        T element = current.element;
        current = current.next;
        return element;
      }
    };
  }
}