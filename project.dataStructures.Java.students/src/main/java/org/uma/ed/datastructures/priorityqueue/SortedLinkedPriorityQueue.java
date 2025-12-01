package org.uma.ed.datastructures.priorityqueue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of the {@link PriorityQueue} interface using a sorted, singly-linked list.
 * <p>
 * This implementation maintains its elements in a linked list, sorted in non-decreasing
 * order of priority (from highest priority/smallest element at the head to lowest priority
 * at the tail).
 * <p>
 * While simple to understand, this approach has performance limitations. Operations that
 * only access the head of the list ({@code first}, {@code dequeue}) are O(1), but the
 * {@code enqueue} operation requires a linear scan to find the correct insertion point,
 * resulting in O(n) time complexity.
 *
 * @param <T> The type of elements held in this priority queue.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class SortedLinkedPriorityQueue<T> extends AbstractPriorityQueue<T> implements PriorityQueue<T> {

  /**
   * Internal class representing a node in the linked structure.
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
   *  - The linked list starting at `first` holds the elements in non-decreasing order
   *    according to the `comparator`.
   *  - The element at the head of the list (`first`) is the minimum element (highest priority).
   *  - `size` holds the number of elements in the queue.
   */

  /**
   * The comparator defining the priority order of elements.
   */
  private final Comparator<T> comparator;

  /**
   * Reference to the first node in the linked list (the head).
   * It is {@code null} if the queue is empty.
   */
  private Node<T> first;

  /**
   * The number of elements in the priority queue.
   */
  private int size;

  /**
   * Private constructor for internal use by factory methods.
   */
  private SortedLinkedPriorityQueue(Comparator<T> comparator, Node<T> first, int size) {
    this.comparator = comparator;
    this.first = first;
    this.size = size;
  }

  /**
   * Constructs an empty {@code LinkedPriorityQueue} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public SortedLinkedPriorityQueue(Comparator<T> comparator) {
    this(comparator, null, 0);
  }

  /**
   * Creates an empty {@code LinkedPriorityQueue} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public static <T> SortedLinkedPriorityQueue<T> empty(Comparator<T> comparator) {
    return new SortedLinkedPriorityQueue<>(comparator);
  }

  /**
   * Creates an empty {@code LinkedPriorityQueue} with natural ordering.
   * <p> Time complexity: O(1)
   */
  public static <T extends Comparable<? super T>> SortedLinkedPriorityQueue<T> empty() {
    return new SortedLinkedPriorityQueue<T>(Comparator.naturalOrder());
  }

  /**
   * Creates a new {@code LinkedPriorityQueue} from the given elements.
   * <p> Time complexity: O(n^2) in the worst case, due to repeated linear-time insertions.
   */
  @SafeVarargs
  public static <T> SortedLinkedPriorityQueue<T> of(Comparator<T> comparator, T... elements) {
    SortedLinkedPriorityQueue<T> queue = new SortedLinkedPriorityQueue<>(comparator);
    for (T elem : elements) {
      queue.enqueue(elem);
    }
    return queue;
  }

  /**
   * Creates a new {@code LinkedPriorityQueue} from the given elements with natural ordering.
   * <p> Time complexity: O(n^2) in the worst case.
   */
  @SafeVarargs
  public static <T extends Comparable<? super T>> SortedLinkedPriorityQueue<T> of(T... elements) {
    return of(Comparator.naturalOrder(), elements);
  }

  /**
   * Creates a new {@code LinkedPriorityQueue} from an iterable collection.
   * <p> Time complexity: O(n^2) in the worst case.
   */
  public static <T> SortedLinkedPriorityQueue<T> from(Comparator<T> comparator, Iterable<T> iterable) {
    SortedLinkedPriorityQueue<T> queue = new SortedLinkedPriorityQueue<>(comparator);
    for (T elem : iterable) {
      queue.enqueue(elem);
    }
    return queue;
  }

  /**
   * Creates a new {@code LinkedPriorityQueue} from an iterable with natural ordering.
   * <p> Time complexity: O(n^2) in the worst case.
   */
  public static <T extends Comparable<? super T>> SortedLinkedPriorityQueue<T> from(Iterable<T> iterable) {
    return from(Comparator.naturalOrder(), iterable);
  }

  /**
   * Creates a new {@code LinkedPriorityQueue} containing the same elements as the given queue.
   * <p> Time complexity: O(n)
   */
  public static <T> SortedLinkedPriorityQueue<T> copyOf(SortedLinkedPriorityQueue<T> queue) {
    return new SortedLinkedPriorityQueue<>(queue.comparator, copyNodes(queue.first), queue.size);
  }

  // helper to copy nodes
  private static <T> Node<T> copyNodes(Node<T> node) {
    if (node == null) {
      return null;
    }
    Node<T> newHead = new Node<>(node.element, null);
    Node<T> currentSrc = node.next;
    Node<T> currentDest = newHead;

    while (currentSrc != null) {
      currentDest.next = new Node<>(currentSrc.element, null);
      currentDest = currentDest.next;
      currentSrc = currentSrc.next;
    }
    return newHead;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public Comparator<T> comparator() {
    return comparator;
  }

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
  public void clear() {
    first = null;
    size = 0;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public T first() {
    if(isEmpty()){
      throw new EmptyPriorityQueueException("first on empty queue");
    }
    return first.element;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void dequeue() {
    if (isEmpty()) {
      throw new EmptyPriorityQueueException("dequeue on empty queue");
    }
    first = first.next;
    size--;
  }

  /**
   * {@inheritDoc}
   * <p> This operation requires a linear scan to find the correct position
   * to insert the new element while maintaining the sorted order.
   * <p> Time complexity: O(n)
   */
  @Override
  public void enqueue(T element) {
    Node<T> newNode = new Node<>(element, null);

    // Caso base: lista vacía
    if (first == null) {
      first = newNode;
      size++;
      return;
    }

    Node<T> prev = null;
    Node<T> curr = first;

    // Recorremos mientras curr.element <= element
    // Esto mantiene orden ascendente y permite duplicados correctamente
    while (curr != null && comparator.compare(curr.element, element) <= 0) {
      prev = curr;
      curr = curr.next;
    }

    // Insertar al principio (nuevo mínimo)
    if (prev == null) {
      newNode.next = first;
      first = newNode;
    }
    // Insertar en medio o al final
    else {
      prev.next = newNode;
      newNode.next = curr;
    }

    size++;
  }

  /**
   * Provides an iterable that traverses the elements in priority order (front to rear).
   *
   * @return an {@code Iterable} for the elements of the priority queue.
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