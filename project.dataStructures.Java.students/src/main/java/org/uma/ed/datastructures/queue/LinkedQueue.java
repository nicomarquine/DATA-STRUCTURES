package org.uma.ed.datastructures.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of the {@link Queue} interface using a singly-linked list of nodes.
 * <p>
 * This class provides a FIFO (First-In-First-Out) queue by maintaining references
 * to both the first (head) and last (tail) nodes of the list. This design allows for
 * constant-time O(1) {@code enqueue} and {@code dequeue} operations.
 *
 * @param <T> The type of elements held in this queue.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class LinkedQueue<T> extends AbstractQueue<T> implements Queue<T> {

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

  /*
   * INVARIANT:
   *  - The `size` field holds the number of elements currently in the queue.
   *  - If the queue is empty (`size == 0`), both `first` and `last` are null.
   *  - If the queue is not empty:
   *      - `first` references the node at the front of the queue.
   *      - `last` references the node at the rear of the queue.
   *      - The `next` reference of the `last` node is always null.
   */

  /**
   * Reference to the first node in the queue (the head).
   */
  private Node<T> first;

  /**
   * Reference to the last node in the queue (the tail).
   */
  private Node<T> last;

  /**
   * The number of elements in the queue.
   */
  private int size;

  /**
   * Constructs an empty {@code LinkedQueue}.
   * <p> Time complexity: O(1)
   */
  public LinkedQueue() {
    this.first = null;
    this.last = null;
    this.size = 0;
  }

  /**
   * Creates an empty {@code LinkedQueue}.
   * <p> Time complexity: O(1)
   *
   * @param <T> the type of elements in the queue.
   * @return an empty {@code LinkedQueue}.
   */
  public static <T> LinkedQueue<T> empty() {
    return new LinkedQueue<>();
  }

  /**
   * Creates a new {@code LinkedQueue} containing the given elements.
   * <p> Time complexity: O(n), where n is the number of elements.
   *
   * @param <T>      the type of elements.
   * @param elements the elements to be enqueued.
   * @return a new {@code LinkedQueue} with the given elements.
   */
  @SafeVarargs
  public static <T> LinkedQueue<T> of(T... elements) {
    LinkedQueue<T> queue = new LinkedQueue<>();
    for (T element : elements) {
      queue.enqueue(element);
    }
    return queue;
  }

  /**
   * Creates a new {@code LinkedQueue} from an iterable collection.
   * <p> Time complexity: O(n), where n is the number of elements.
   *
   * @param <T>      the type of elements.
   * @param iterable the iterable containing elements to be enqueued.
   * @return a new {@code LinkedQueue} with the given elements.
   */
  public static <T> LinkedQueue<T> from(Iterable<T> iterable) {
    LinkedQueue<T> queue = new LinkedQueue<>();
    for (T element : iterable) {
      queue.enqueue(element);
    }
    return queue;
  }

  /**
   * Creates a new {@code LinkedQueue} containing the same elements as the given queue.
   * <p> Time complexity: O(n)
   *
   * @param <T>  the type of elements.
   * @param that the {@code LinkedQueue} to be copied.
   * @return a new {@code LinkedQueue} with the same elements and order.
   */
  public static <T> LinkedQueue<T> copyOf(LinkedQueue<T> that) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Creates a new {@code LinkedQueue} containing the same elements as the given queue.
   * <p>
   * Note: This operation temporarily modifies the source queue by dequeueing all its
   * elements, but restores it to its original state before returning. The efficiency of this
   * method depends heavily on the implementation of the source queue.
   * <p> Time complexity: O(n * T_first + n * T_enqueue + n * T_dequeue),
   * where T_... is the complexity of the source queue's operations.
   *
   * @param <T>  the type of elements.
   * @param that the generic {@code Queue} to be copied.
   * @return a new {@code LinkedQueue} with the same elements and order.
   */
  public static <T> LinkedQueue<T> copyOf(Queue<T> that) { throw new UnsupportedOperationException("Not implemented yet"); }

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
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public T first() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void dequeue() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void enqueue(T element) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void clear() { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Provides an iterable that traverses the elements from the front to the rear of the queue.
   *
   * @return an {@code Iterable} for the elements of the queue.
   */
  @Override
  protected Iterable<T> elements() {
    return () -> new Iterator<>() {
      private Node<T> current = first;

      @Override
      public boolean hasNext() {
        return current != null;
      }

      @Override
      public T next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        T element = current.element;
        current = current.next;
        return element;
      }
    };
  }
}