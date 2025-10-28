package org.uma.ed.datastructures.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A singly-linked list implementation of the {@link List} interface.
 * <p>
 * This implementation uses a chain of nodes, each containing an element and a reference
 * to the next node. It maintains references to both the first (head) and last (tail)
 * nodes, which allows for constant-time O(1) performance for {@code append}, {@code prepend},
 * and operations on an empty list.
 * <p>
 * Operations that require traversing the list, such as {@code get}, {@code set}, {@code insert},
 * and {@code delete} at an arbitrary index, have a time complexity of O(n).
 *
 * @param <T> The type of elements held in this list.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class LinkedList<T> extends AbstractList<T> implements List<T> {

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
   *  - The size field holds the number of elements in the list.
   *  - If the list is empty (size == 0), both first and last are null.
   *  - If the list is not empty:
   *      - first references the first node in the sequence.
   *      - last references the last node in the sequence.
   *      - Each node contains a reference to the next node or null if it is the last node.
   */

  /**
   * Reference to the first node in the list (the head).
   */
  private Node<T> first;

  /**
   * Reference to the last node in the list (the tail).
   */
  private Node<T> last;

  /**
   * The number of elements in the list.
   */
  private int size;

  /**
   * Constructs an empty {@code LinkedList}.
   * <p> Time complexity: O(1)
   */
  public LinkedList() {
    this.first = null;
    this.last = null;
    this.size = 0;
  }

  /**
   * Creates an empty {@code LinkedList}.
   * <p> Time complexity: O(1)
   *
   * @param <T> the type of elements in the list.
   * @return an empty {@code LinkedList}.
   */
  public static <T> LinkedList<T> empty() {
    return new LinkedList<>();
  }

  /**
   * Creates a new {@code LinkedList} from an iterable collection.
   * <p> Time complexity: O(n), where n is the number of elements.
   *
   * @param <T>      the type of elements.
   * @param iterable the iterable containing elements for the list.
   * @return a new {@code LinkedList} with the given elements.
   */
  public static <T> LinkedList<T> from(Iterable<T> iterable) {
    LinkedList<T> list = new LinkedList<>();
    for (T element : iterable) {
      list.append(element);
    }
    return list;
  }

  /**
   * Creates a new {@code LinkedList} containing the given elements.
   * <p> Time complexity: O(n), where n is the number of elements.
   *
   * @param <T>      the type of elements.
   * @param elements the elements to be stored in the list.
   * @return a new {@code LinkedList} with the given elements.
   */
  @SafeVarargs
  public static <T> LinkedList<T> of(T... elements) {
    LinkedList<T> list = new LinkedList<>();
    if (elements.length > 0) {
      // Handle first element to initialize the list
      list.first = new Node<>(elements[0], null);
      Node<T> tail = list.first;

      // Handle remaining elements
      for (int i = 1; i < elements.length; i++) {
        tail.next = new Node<>(elements[i], null);
        tail = tail.next;
      }
      list.last = tail;
      list.size = elements.length;
    }
    return list;
  }

  /**
   * Creates a new {@code LinkedList} containing the same elements as the given list.
   * <p> Time complexity: O(n)
   *
   * @param <T>  the type of elements.
   * @param that the generic {@code List} to be copied.
   * @return a new {@code LinkedList} with the same elements and order.
   */
  public static <T> LinkedList<T> copyOf(List<T> that) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void clear() {
    first = null;
    last = null;
    size = 0;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public boolean isEmpty() { return size == 0; }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public int size() { return size; }

  /**
   * Checks if the given index is valid for an access, modification, or deletion operation.
   * @param index the index to check.
   * @throws ListException if the index is out of range ({@code index < 0 || index >= size()}).
   */
  private void checkIndex(int index) {
    if (index < 0 || index >= size) {
      throw new ListException("Invalid index " + index + " for a list of size " + size);
    }
  }

  /**
   * Returns the node at the specified index. This is an O(n) operation.
   * @param index the index of the node to retrieve.
   * @return the node at the specified index.
   */
  private Node<T> nodeAtIndex(int index) {
    // Precondition: index is assumed to be valid [0, size-1]
    Node<T> current;
    if (index == size - 1) {
      // Optimization for the last element
      current = last;
    } else {
      current = first;
      for (int i = 0; i < index; i++) {
        current = current.next;
      }
    }
    return current;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void insert(int index, T element) {
    if(index == 0){
      prepend(element);
    } else if(index == size){
      append(element);
    } else{
      checkIndex(index);
      Node<T> prev = nodeAtIndex(index-1);
      Node<T> node = new Node<>(element,prev.next);
      prev.next = node;
      size++;
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void delete(int index) {
    checkIndex(index);
    if(index == 0){
      first = first.next;
      if(first == null){
        last = null;
      }
    } else if (index == size-1){
      Node<T> prev = nodeAtIndex(index-1);
      prev.next = null;
      last = prev;
    } else{
      Node<T> prev = nodeAtIndex(index-1);
      prev.next = prev.next.next;
    }
    size--;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public T get(int index) {
    checkIndex(index);
    return nodeAtIndex(index).element;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void set(int index, T element) {
    checkIndex(index);
    nodeAtIndex(index).element = element;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void append(T element) {
    Node<T> node = new Node<>(element,null);
    if(isEmpty()){
      first = node;
    } else {
      last.next = node;
    }
    last = node;
    size++;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void prepend(T element) {
    Node<T> node = new Node<>(element,first);
    first = node;
    if(size == 0){
      last = node;
    }
    size++;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<T> iterator() {
    return new LinkedListIterator();
  }

  /**
   * An iterator for this {@code LinkedList}.
   */
  private final class LinkedListIterator implements Iterator<T> {
    private Node<T> current;

    public LinkedListIterator() {
      this.current = first;
    }

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException("Iterator has no more elements");
      }
      T element = current.element;
      current = current.next;
      return element;
    }
  }
}