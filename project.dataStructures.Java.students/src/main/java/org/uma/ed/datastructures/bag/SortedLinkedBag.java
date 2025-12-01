package org.uma.ed.datastructures.bag;

import org.uma.ed.datastructures.heap.BinaryHeap;

import javax.imageio.plugins.tiff.TIFFTagSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of the {@link SortedBag} interface using a sorted, singly-linked list.
 * <p>
 * This implementation stores unique elements in sorted nodes, with each node maintaining a
 * count of the element's occurrences. This design allows for duplicates while keeping the
 * underlying structure sorted.
 * <p>
 * Performance characteristics are typical for a linked list: operations that require
 * finding an element ({@code insert}, {@code delete}, {@code occurrences}) have a linear
 * time complexity O(n). However, accessing the minimum and maximum elements is O(1)
 * due to the maintenance of `first` and `last` pointers.
 *
 * @param <T> The type of elements held in this sorted bag.
 *
 * @author Pablo López, Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class SortedLinkedBag<T> extends AbstractSortedBag<T> implements SortedBag<T> {

  /**
   * Internal class representing a node in the sorted linked structure.
   * Each node stores a unique element, its number of occurrences, and a link to the next node.
   */
  private static final class Node<E> {
    E element;
    int occurrences;
    Node<E> next;

    Node(E element, int occurrences, Node<E> next) {
      this.element = element;
      this.occurrences = occurrences;
      this.next = next;
    }
  }

  /*
   * INVARIANT:
   *  - The linked structure starting at `first` maintains nodes in ascending order of their `element`.
   *  - Each node contains a unique element; no two nodes store the same element.
   *  - Each node's `occurrences` count is always greater than zero.
   *  - `first` points to the first node (smallest element) or is null if the bag is empty.
   *  - `last` points to the last node (largest element) or is null if the bag is empty.
   *  - `size` stores the total number of elements in the bag (the sum of all occurrences).
   */

  private final Comparator<T> comparator;
  private Node<T> first;
  private Node<T> last;
  private int size;

  /**
   * Constructs an empty {@code SortedLinkedBag} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public SortedLinkedBag(Comparator<T> comparator) {
    this.comparator = comparator;
    this.first = null;
    this.last = null;
    this.size = 0;
  }

  /**
   * Creates an empty {@code SortedLinkedBag} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public static <T> SortedLinkedBag<T> empty(Comparator<T> comparator) {
    return new SortedLinkedBag<>(comparator);
  }

  /**
   * Creates an empty {@code SortedLinkedBag} with natural ordering.
   * <p> Time complexity: O(1)
   */
  public static <T extends Comparable<? super T>> SortedLinkedBag<T> empty() {
    return new SortedLinkedBag<T>(Comparator.naturalOrder());
  }

  /**
   * Creates a new {@code SortedLinkedBag} from the given elements.
   * <p> Time complexity: O(n^2) in the worst case, due to repeated linear-time insertions.
   */
  @SafeVarargs
  public static <T> SortedLinkedBag<T> of(Comparator<T> comparator, T... elements) {
    SortedLinkedBag<T> bag = new SortedLinkedBag<>(comparator);
    bag.insert(elements);
    return bag;
  }

  /**
   * Creates a new {@code SortedLinkedBag} from the given elements with natural ordering.
   * <p> Time complexity: O(n^2)
   */
  @SafeVarargs
  public static <T extends Comparable<? super T>> SortedLinkedBag<T> of(T... elements) {
    return of(Comparator.naturalOrder(), elements);
  }

  /**
   * Creates a new {@code SortedLinkedBag} from an iterable.
   * <p> Time complexity: O(n^2)
   */
  public static <T> SortedLinkedBag<T> from(Comparator<T> comparator, Iterable<T> iterable) {
    SortedLinkedBag<T> bag = new SortedLinkedBag<>(comparator);
    for (T element : iterable) {
      bag.insert(element);
    }
    return bag;
  }

  /**
   * Creates a new {@code SortedLinkedBag} from an iterable with natural ordering.
   * <p> Time complexity: O(n^2)
   */
  public static <T extends Comparable<? super T>> SortedLinkedBag<T> from(Iterable<T> iterable) {
    return from(Comparator.naturalOrder(), iterable);
  }

  /**
   * Creates a new {@code SortedLinkedBag} containing the same elements as the given sorted bag.
   * <p>
   * This is an efficient O(n) operation that leverages the sorted nature of the source bag.
   * <p> Time complexity: O(n)
   */
  public static <T> SortedLinkedBag<T> copyOf(SortedBag<T> that) {
    SortedLinkedBag<T> copy = new SortedLinkedBag<>(that.comparator());
    // Since `that` is sorted, we can use the efficient append operation.
    for (T element : that) {
      copy.append(element);
    }
    return copy;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public Comparator<T> comparator() {
    return this.comparator;
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
   * A helper class that encapsulates the logic for finding an element (or its
   * insertion point) within the sorted linked list in a single traversal.
   * <p>
   * Upon instantiation with an element, it traverses the list and sets its public
   * fields (`found`, `previous`, `current`) to reflect the result of the search.
   * This avoids redundant list traversals in methods like {@code insert}, {@code delete},
   * and {@code occurrences}.
   * <p>
   * The state of the Finder's fields after a search for {@code element} is as follows:
   * <ul>
   *     <li><b>If the element is found:</b>
   *         <ul>
   *             <li>{@code found} is {@code true}.</li>
   *             <li>{@code current} points to the node containing the element.</li>
   *             <li>{@code previous} points to the node immediately before {@code current},
   *                 or is {@code null} if {@code current} is the first node in the list.</li>
   *         </ul>
   *     </li>
   *     <li><b>If the element is not found:</b>
   *         <ul>
   *             <li>{@code found} is {@code false}.</li>
   *             <li>{@code current} points to the node that would immediately follow the
   *                 new element's position if it were to be inserted. This will be the
   *                 first node in the list with an element greater than the one searched for.
   *                 It can be {@code null} if the insertion point is at the end of the list.</li>
   *             <li>{@code previous} points to the node that would precede the new element's
   *                 position. It can be {@code null} if the insertion point is at the
   *                 beginning of the list.</li>
   *         </ul>
   *     </li>
   * </ul>
   */
  private final class Finder {
    boolean found;
    Node<T> previous, current;

    Finder(T element) {
      previous = null;
      current = first;

      int cmp = 0;
      while (current != null && (cmp = comparator.compare(element, current.element)) > 0) {
        previous = current;
        current = current.next;
      }
      found = (current != null && cmp == 0);
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void insert(T element) {
    if(isEmpty()){
      Node<T> node = new Node<>(element, 1, null);
      first = node;
      last = node;
      size = 1;
    }else {
      Finder finder = new Finder(element);
      if (!finder.found) {
        if (finder.previous == null) {
          Node<T> node = new Node<>(element, 1, first);
          first = node;
        } else if (finder.current == null) {
          Node<T> node = new Node<>(element, 1, null);
          last.next = node;
          last = node;
        } else {
          Node<T> node = new Node<>(element, 1, finder.current);
          finder.previous.next = node;
        }
        size++;
      } else {
        finder.current.occurrences++;
        size++;
      }
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void delete(T element) {
    if(isEmpty()){
      throw new NoSuchElementException("delete on empty bag");
    }

    Finder finder = new Finder(element);
    if(finder.found){
      if(finder.current.occurrences > 1){
        finder.current.occurrences--;
        size--;
      }else{
        if (finder.previous == null) {
          first = first.next;
          if (first == null) last = null; // la lista queda vacía
        } else if (finder.current == last) {
          last = finder.previous;
        } else {
          finder.previous.next = finder.current.next;
        }
        size--;
      }
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public int occurrences(T element) {
    Finder finder = new Finder(element);
    return finder.found ? finder.current.occurrences : 0;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public T minimum() {
    if(isEmpty()){
      throw new NoSuchElementException("minimum on empty bag");
    }

    return first.element;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public T maximum() {
    if(isEmpty()){
      throw new NoSuchElementException("minimum on empty bag");
    }

    return last.element;
  }

  @Override
  public Iterator<T> iterator() {
    return new BagIterator();
  }

  /**
   * An iterator for this bag that yields each element as many times as its occurrence count.
   */
  private final class BagIterator implements Iterator<T> {
    private Node<T> currentNode;
    private int occurrencesLeft;

    BagIterator() {
      this.currentNode = first;
      if (this.currentNode != null) {
        this.occurrencesLeft = this.currentNode.occurrences;
      }
    }

    @Override
    public boolean hasNext() {
      return currentNode != null;
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException("No more elements in BagIterator");
      }
      T elem = currentNode.element;
      occurrencesLeft--;

      // si aún quedan ocurrencias del mismo elemento
      if (occurrencesLeft > 0) {
        return elem;
      }

      // si NO quedan ocurrencias, avanzar al siguiente nodo
      currentNode = currentNode.next;

      // si no hemos terminado, cargar ocurrencias del siguiente nodo
      if (currentNode != null) {
        occurrencesLeft = currentNode.occurrences;
      }

      return elem;
    }
  }

  /**
   * Appends an element to the end of the bag.
   * PRECONDITION: The element must be >= the last element in the bag.
   */
  private void append(T element) {
    assert first == null || comparator.compare(element, last.element) >= 0;

    if (isEmpty()) {
      first = new Node<>(element, 1, null);
      last = first;
    } else if (comparator.compare(element, last.element) == 0) {
      last.occurrences++;
    } else { // element > last.element
      Node<T> newNode = new Node<>(element, 1, null);
      last.next = newNode;
      last = newNode;
    }
    size++;
  }

  // --- Set-like Operations ---

  /**
   * Modifies this bag to be the union with the given bag. Inefficient for general bags.
   */
  public void union(Bag<T> bag) {
    for (T x : bag) {
      this.insert(x);
    }
  }

  /**
   * Efficiently modifies this bag to be the union with another {@code SortedLinkedBag}.
   */
  public void union(SortedLinkedBag<T> that) {
    Node<T> p = this.first;
    Node<T> q = that.first;
    Node<T> prev = null;

    while(p != null && q != null){
      int cmp = comparator.compare(p.element, q.element);

      if(cmp < 0){
        prev = p;
        p = p.next;
      }else if (cmp > 0){
        // q < p → insertar nodo de q antes de p
        Node<T> newNode = new Node<>(q.element, q.occurrences, p);
        if (prev == null) {
          first = newNode;
        } else {
          prev.next = newNode;
        }
        prev = newNode;

        size += q.occurrences;
        q = q.next;
      }else {
        // p == q → sumar ocurrencias
        p.occurrences += q.occurrences;
        size += q.occurrences;

        prev = p;
        p = p.next;
        q = q.next;
      }
    }
    // Si quedan elementos en that → añadirlos al final
    while (q != null) {
      Node<T> newNode = new Node<>(q.element, q.occurrences, null);
      if (prev == null) {
        first = newNode;
      } else {
        prev.next = newNode;
      }
      prev = newNode;
      last = newNode;

      size += q.occurrences;
      q = q.next;
    }
  }

  /**
   * Modifies this bag to be the intersection with the given bag. Inefficient.
   */
  public void intersection(Bag<T> bag) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Efficiently modifies this bag to be the intersection with another {@code SortedLinkedBag}.
   */
  public void intersection(SortedLinkedBag<T> that) {
    Node<T> p = this.first;
    Node<T> q = that.first;

    Node<T> newFirst = null;
    Node<T> newLast = null;
    int newSize = 0;

    while (p != null && q != null) {
      int cmp = comparator.compare(p.element, q.element);

      if (cmp < 0) {
        p = p.next;
      } else if (cmp > 0) {
        q = q.next;
      } else {
        // mismo elemento
        int occ = Math.min(p.occurrences, q.occurrences);
        if (occ > 0) {
          Node<T> node = new Node<>(p.element, occ, null);
          if (newFirst == null) newFirst = node;
          else newLast.next = node;
          newLast = node;
          newSize += occ;
        }
        p = p.next;
        q = q.next;
      }
    }

    // sustituimos this por la nueva lista
    this.first = newFirst;
    this.last = newLast;
    this.size = newSize;
  }

  /**
   * Modifies this bag to be the difference with the given bag. Inefficient.
   */
  public void difference(Bag<T> bag) { throw new UnsupportedOperationException("Not implemented yet"); }

  /**
   * Efficiently modifies this bag to be the difference with another {@code SortedLinkedBag}.
   */
  public void difference(SortedLinkedBag<T> that) {Node<T> p = this.first;
    Node<T> q = that.first;

    Node<T> newFirst = null;
    Node<T> newLast = null;
    int newSize = 0;

    while (p != null) {
      int cmp = (q == null ? -1 : comparator.compare(p.element, q.element));

      if (q == null || cmp < 0) {
        // elemento solo en this → copiar entero
        Node<T> node = new Node<>(p.element, p.occurrences, null);
        if (newFirst == null) newFirst = node;
        else newLast.next = node;
        newLast = node;
        newSize += p.occurrences;

        p = p.next;
      } else if (cmp > 0) {
        // avanzar en that
        q = q.next;
      } else {
        // mismo elemento → restar ocurrencias
        int occ = p.occurrences - q.occurrences;

        if (occ > 0) {
          Node<T> node = new Node<>(p.element, occ, null);
          if (newFirst == null) newFirst = node;
          else newLast.next = node;
          newLast = node;
          newSize += occ;
        }

        p = p.next;
        q = q.next;
      }
    }

    this.first = newFirst;
    this.last = newLast;
    this.size = newSize;}
}