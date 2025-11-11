package org.uma.ed.datastructures.set;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of the {@link SortedSet} interface using a sorted, singly-linked list.
 * <p>
 * This class maintains its elements in ascending order, as defined by a comparator.
 * Due to the nature of a linked list, operations that require finding an element
 * ({@code insert}, {@code delete}, {@code contains}) have a linear time complexity O(n).
 * Operations at the head of the list ({@code minimum}) are O(1).
 *
 * @param <T> The type of elements held in this sorted set.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class SortedLinkedSet<T> extends AbstractSortedSet<T> implements SortedSet<T> {

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
   *  - The `size` field holds the number of elements in the set.
   *  - The linked list starting at `first` contains the elements in ascending order
   *    according to the `comparator`.
   *  - The list contains no duplicate elements as per the `comparator`.
   *  - If the set is empty, `first` is null.
   */

  /**
   * Comparator defining the order of elements in this set.
   */
  private final Comparator<T> comparator;

  /**
   * Reference to the first node in the linked list.
   */
  private Node<T> first;

  /**
   * The number of elements in this set.
   */
  private int size;

  /**
   * Private constructor for internal use by the {@code SortedLinkedSetBuilder}.
   */
  private SortedLinkedSet(SortedLinkedSetBuilder<T> builder) {
    this.first = builder.first;
    this.size = builder.size;
    this.comparator = builder.comparator;
  }

  /**
   * Constructs an empty {@code SortedLinkedSet} with a specified comparator.
   * <p> Time complexity: O(1)
   *
   * @param comparator the comparator that will be used to order the set.
   */
  public SortedLinkedSet(Comparator<T> comparator) {
    this.comparator = comparator;
    this.first = null;
    this.size = 0;
  }

  /**
   * Creates an empty {@code SortedLinkedSet} with a specified comparator.
   * <p> Time complexity: O(1)
   *
   * @param <T>        the type of elements.
   * @param comparator the comparator to use for ordering.
   * @return an empty {@code SortedLinkedSet}.
   */
  public static <T> SortedLinkedSet<T> empty(Comparator<T> comparator) {
    return new SortedLinkedSet<>(comparator);
  }

  /**
   * Creates an empty {@code SortedLinkedSet} with natural ordering.
   * <p> Time complexity: O(1)
   *
   * @param <T> the type of elements.
   * @return an empty {@code SortedLinkedSet}.
   */
  public static <T extends Comparable<? super T>> SortedLinkedSet<T> empty() {
    return new SortedLinkedSet<T>(Comparator.naturalOrder());
  }

  /**
   * Creates a new {@code SortedLinkedSet} from the given elements, ordered by the specified comparator.
   * <p> Time complexity: O(n^2) in the worst case, due to repeated linear-time insertions.
   *
   * @param <T>        the type of elements.
   * @param comparator the comparator to use for ordering.
   * @param elements   the elements to include in the set.
   * @return a new {@code SortedLinkedSet} containing the elements.
   */
  @SafeVarargs
  public static <T> SortedLinkedSet<T> of(Comparator<T> comparator, T... elements) {
    SortedLinkedSet<T> set = new SortedLinkedSet<>(comparator);
    for (T element : elements) {
      set.insert(element);
    }
    return set;
  }

  /**
   * Creates a new {@code SortedLinkedSet} from the given elements, ordered by their natural ordering.
   * <p> Time complexity: O(n^2) in the worst case, due to repeated linear-time insertions.
   *
   * @param <T>      the type of elements.
   * @param elements the elements to include in the set.
   * @return a new {@code SortedLinkedSet} containing the elements.
   */
  @SafeVarargs
  public static <T extends Comparable<? super T>> SortedLinkedSet<T> of(T... elements) {
    return of(Comparator.naturalOrder(), elements);
  }

  /**
   * Creates a new {@code SortedLinkedSet} from an iterable, ordered by the specified comparator.
   * <p> Time complexity: O(n^2) in the worst case, due to repeated linear-time insertions.
   *
   * @param <T>        the type of elements.
   * @param comparator the comparator to use for ordering.
   * @param iterable   an iterable containing the elements for the set.
   * @return a new {@code SortedLinkedSet} containing the elements.
   */
  public static <T> SortedLinkedSet<T> from(Comparator<T> comparator, Iterable<T> iterable) {
    SortedLinkedSet<T> set = new SortedLinkedSet<>(comparator);
    for (T element : iterable) {
      set.insert(element);
    }
    return set;
  }

  /**
   * Creates a new {@code SortedLinkedSet} from an iterable, ordered by their natural ordering.
   * <p> Time complexity: O(n^2) in the worst case, due to repeated linear-time insertions.
   *
   * @param <T>      the type of elements.
   * @param iterable an iterable containing the elements for the set.
   * @return a new {@code SortedLinkedSet} containing the elements.
   */
  public static <T extends Comparable<? super T>> SortedLinkedSet<T> from(Iterable<T> iterable) {
    return from(Comparator.naturalOrder(), iterable);
  }

  /**
   * Creates a new {@code SortedLinkedSet} containing the same elements as the given sorted set.
   * <p> This is an efficient O(n) operation that leverages the sorted nature of the source set.
   * <p> Time complexity: O(n)
   *
   * @param <T> the type of elements.
   * @param that the sorted set to be copied.
   * @return a new {@code SortedLinkedSet} with the same elements.
   */
  public static <T> SortedLinkedSet<T> copyOf(SortedSet<T> that) {
    SortedLinkedSetBuilder<T> copy = new SortedLinkedSetBuilder<>(that.comparator());
    for(T element : that){
      copy.append(element);
    }
    return copy.toSortedLinkedSet();
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
  public Comparator<T> comparator() {
    return this.comparator;
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
  public int size() {
    return size;
  }

  /**
   * A helper class that traverses the list to find the correct position for an element.
   * <p>
   * If found, {@code found} is true, {@code current} points to the node, and {@code previous} to its predecessor.
   * If not found, {@code found} is false, and {@code previous} and {@code current} point to the nodes
   * that would surround the element if it were inserted.
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
    Finder finder = new Finder(element);

    if(finder.found) {
      //element in the set, replace it
      finder.current.element = element;
    }else{
      //element not in the set
      Node<T> node = new Node<>(element, finder.current);

      if(finder.previous == null) {
        //insert at first position
        first = node;
      }else{
        finder.previous.next = node;
      }
      size++;
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public boolean contains(T element) {
    Finder finder = new Finder(element);

    return finder.found;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void delete(T element) {
    Finder finder = new Finder(element);

    if (finder.found) {
      //found. remove it
      if(finder.previous == null) {
        //elemnt to remove is the first one
        first = finder.current.next;
      }else{
        //the element to remove is not the first one
        finder.previous.next = finder.current.next;
      }
      size--;
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public T minimum() {
    if(isEmpty()){
      throw new NoSuchElementException("minimum on empty set");
    }
    return first.element;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public T maximum() {
    if(isEmpty()){
      throw new NoSuchElementException("maximum on empty set");
    }
    Node<T> current = first;
    while(current.next != null){
      current = current.next;
    }

    return current.element;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<T> iterator() {
    return new SortedLinkedSetIterator();
  }

  /**
   * An iterator for this {@code SortedLinkedSet}.
   */
  private final class SortedLinkedSetIterator implements Iterator<T> {
    private Node<T> current = first;

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public T next() {
      if(!hasNext()){
        throw new NoSuchElementException("no more elements");
      }
      T element = current.element;
      current = current.next;
      return element;
    }
  }

  /**
   * A builder class for efficiently constructing a {@code SortedLinkedSet}.
   * <p>
   * This builder is optimized for creating a set from an already sorted sequence of elements.
   * It uses an O(1) append operation, allowing for O(n) total construction time from a sorted source.
   */
  private static final class SortedLinkedSetBuilder<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;
    private final Comparator<T> comparator;

    SortedLinkedSetBuilder(Comparator<T> comparator) {
      this.first = null;
      this.last = null;
      this.size = 0;
      this.comparator = comparator;
    }

    /**
     * Appends an element to the end of the structure being built.
     * PRECONDITION: The element must be strictly greater than the last element added.
     */
    void append(T element) {
      assert last == null || comparator.compare(element, last.element) > 0;

      Node<T> newNode = new Node<>(element, null);
      if (last == null) { // List is empty
        first = newNode;
      } else {
        last.next = newNode;
      }
      last = newNode;
      size++;
    }

    /**
     * Finalizes the build process and returns the constructed {@code SortedLinkedSet}.
     */
    SortedLinkedSet<T> toSortedLinkedSet() {
      return new SortedLinkedSet<>(this);
    }
  }

  /**
   * A helper method to safely retrieve the next element from an iterator, or null if exhausted.
   */
  private static <T> T nextOrNull(Iterator<T> iterator) {
    return iterator.hasNext() ? iterator.next() : null;
  }

  /**
   * Helper method to check for comparator consistency.
   */
  private static void checkSameComparator(SortedSet<?> set1, SortedSet<?> set2, String operation) {
    if (set1.comparator() != set2.comparator()) {
      throw new IllegalArgumentException(operation + ": both sorted sets must use the same comparator instance");
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n + m)
   */
  public static <T> SortedLinkedSet<T> union(SortedSet<T> set1, SortedSet<T> set2) {
    checkSameComparator(set1, set2, "union");
    SortedLinkedSetBuilder<T> builder = new SortedLinkedSetBuilder<>(set1.comparator());

    Iterator<T> it1 = set1.iterator();
    Iterator<T> it2 = set2.iterator();
    T elem1 = nextOrNull(it1);
    T elem2 = nextOrNull(it2);

    while (elem1 != null || elem2 != null) {
      if (elem1 == null) {
        builder.append(elem2);
        elem2 = nextOrNull(it2);
      } else if (elem2 == null) {
        builder.append(elem1);
        elem1 = nextOrNull(it1);
      } else {
        int cmp = set1.comparator().compare(elem1, elem2);
        if (cmp < 0) {
          builder.append(elem1);
          elem1 = nextOrNull(it1);
        } else if (cmp > 0) {
          builder.append(elem2);
          elem2 = nextOrNull(it2);
        } else { // they are equal
          builder.append(elem1);
          elem1 = nextOrNull(it1);
          elem2 = nextOrNull(it2);
        }
      }
    }
    return builder.toSortedLinkedSet();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n + m)
   */
  public static <T> SortedLinkedSet<T> intersection(SortedSet<T> set1, SortedSet<T> set2) {
    checkSameComparator(set1, set2, "intersection");
    SortedLinkedSetBuilder<T> builder = new SortedLinkedSetBuilder<>(set1.comparator());

    Iterator<T> it1 = set1.iterator();
    Iterator<T> it2 = set2.iterator();
    T elem1 = nextOrNull(it1);
    T elem2 = nextOrNull(it2);

    while (elem1 != null && elem2 != null) {
      int cmp = set1.comparator().compare(elem1, elem2);
      if (cmp < 0) {
        elem1 = nextOrNull(it1);
      } else if (cmp > 0) {
        elem2 = nextOrNull(it2);
      } else { // they are equal
        builder.append(elem1);
        elem1 = nextOrNull(it1);
        elem2 = nextOrNull(it2);
      }
    }
    return builder.toSortedLinkedSet();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n + m)
   */
  public static <T> SortedLinkedSet<T> difference(SortedSet<T> set1, SortedSet<T> set2) {
    checkSameComparator(set1, set2, "difference");
    SortedLinkedSetBuilder<T> builder = new SortedLinkedSetBuilder<>(set1.comparator());

    Iterator<T> it1 = set1.iterator();
    Iterator<T> it2 = set2.iterator();
    T elem1 = nextOrNull(it1);
    T elem2 = nextOrNull(it2);

    while (elem1 != null) {
      if (elem2 == null || set1.comparator().compare(elem1, elem2) < 0) {
        builder.append(elem1);
        elem1 = nextOrNull(it1);
      } else if (set1.comparator().compare(elem1, elem2) > 0) {
        elem2 = nextOrNull(it2);
      } else { // elem1 is equal to elem2
        elem1 = nextOrNull(it1);
        elem2 = nextOrNull(it2);
      }
    }
    return builder.toSortedLinkedSet();
  }
}