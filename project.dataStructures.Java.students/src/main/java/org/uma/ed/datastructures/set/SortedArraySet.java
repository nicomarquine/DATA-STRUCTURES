package org.uma.ed.datastructures.set;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of the {@link SortedSet} interface using a sorted dynamic array.
 * <p>
 * This class maintains its elements in ascending order, as defined by a comparator.
 * This ordering allows for logarithmic time complexity O(log n) for the {@code contains}
 * operation. However, the {@code insert} and {@code delete} operations require shifting
 * elements, resulting in a linear time complexity O(n).
 *
 * @param <T> The type of elements held in this sorted set.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class SortedArraySet<T> extends AbstractSortedSet<T> implements SortedSet<T> {

  /**
   * Default initial capacity if none is provided.
   */
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  /**
   * Comparator defining the order of elements in this set.
   */
  private final Comparator<T> comparator;

  /**
   * The array buffer where elements are stored.
   */
  private T[] elements;

  /**
   * The number of elements in this set.
   */
  private int size;

  /*
   * INVARIANT:
   *  - The size field holds the number of elements currently in the set.
   *  - The elements are stored in elements[0...size-1] and are always sorted
   *    according to the comparator.
   *  - The array contains no duplicate elements as per the comparator.
   *  - elements.length is the capacity of the array, which is always >= size.
   */

  /**
   * Constructs an empty {@code SortedArraySet} with a specified comparator and initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param comparator      the comparator that will be used to order the set.
   * @param initialCapacity the initial capacity of the underlying array.
   * @throws IllegalArgumentException if the initial capacity is not positive.
   */
  @SuppressWarnings("unchecked")
  public SortedArraySet(Comparator<T> comparator, int initialCapacity) {
    if (initialCapacity <= 0) {
      throw new IllegalArgumentException("Initial capacity must be greater than 0");
    }
    this.comparator = comparator;
    this.elements = (T[]) new Object[initialCapacity];
    this.size = 0;
  }

  /**
   * Constructs an empty {@code SortedArraySet} with a specified comparator and default capacity.
   * <p> Time complexity: O(1)
   *
   * @param comparator the comparator that will be used to order the set.
   */
  public SortedArraySet(Comparator<T> comparator) {
    this(comparator, DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Creates an empty {@code SortedArraySet} with a specified comparator and initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param <T>             the type of elements.
   * @param comparator      the comparator to use for ordering.
   * @param initialCapacity the initial capacity of the set.
   * @return an empty {@code SortedArraySet}.
   */
  public static <T> SortedArraySet<T> withCapacity(Comparator<T> comparator, int initialCapacity) {
    return new SortedArraySet<>(comparator, initialCapacity);
  }

  /**
   * Creates an empty {@code SortedArraySet} with a specified initial capacity and natural ordering.
   * <p> Time complexity: O(1)
   *
   * @param <T>             the type of elements.
   * @param initialCapacity the initial capacity of the set.
   * @return an empty {@code SortedArraySet}.
   */
  public static <T extends Comparable<? super T>> SortedArraySet<T> withCapacity(int initialCapacity) {
    return new SortedArraySet<T>(Comparator.naturalOrder(), initialCapacity);
  }

  /**
   * Creates an empty {@code SortedArraySet} with a specified comparator and default capacity.
   * <p> Time complexity: O(1)
   *
   * @param <T>        the type of elements.
   * @param comparator the comparator to use for ordering.
   * @return an empty {@code SortedArraySet}.
   */
  public static <T> SortedArraySet<T> empty(Comparator<T> comparator) {
    return new SortedArraySet<>(comparator);
  }

  /**
   * Creates an empty {@code SortedArraySet} with natural ordering and default capacity.
   * <p> Time complexity: O(1)
   *
   * @param <T> the type of elements.
   * @return an empty {@code SortedArraySet}.
   */
  public static <T extends Comparable<? super T>> SortedArraySet<T> empty() {
    return new SortedArraySet<T>(Comparator.naturalOrder());
  }

  /**
   * Creates a new {@code SortedArraySet} from the given elements, ordered by the specified comparator.
   * <p> Time complexity: O(n^2) in the worst case, due to repeated insertions.
   *
   * @param <T>        the type of elements.
   * @param comparator the comparator to use for ordering.
   * @param elements   the elements to include in the set.
   * @return a new {@code SortedArraySet} containing the elements.
   */
  @SafeVarargs
  public static <T> SortedArraySet<T> of(Comparator<T> comparator, T... elements) {
    SortedArraySet<T> set = new SortedArraySet<>(comparator, elements.length > 0 ? elements.length : DEFAULT_INITIAL_CAPACITY);
    for (T element : elements) {
      set.insert(element);
    }
    return set;
  }

  /**
   * Creates a new {@code SortedArraySet} from the given elements, ordered by their natural ordering.
   * <p> Time complexity: O(n^2) in the worst case, due to repeated insertions.
   *
   * @param <T>      the type of elements.
   * @param elements the elements to include in the set.
   * @return a new {@code SortedArraySet} containing the elements.
   */
  @SafeVarargs
  public static <T extends Comparable<? super T>> SortedArraySet<T> of(T... elements) {
    return of(Comparator.naturalOrder(), elements);
  }

  /**
   * Creates a new {@code SortedArraySet} from an iterable, ordered by the specified comparator.
   * <p> Time complexity: O(n^2) in the worst case, due to repeated insertions.
   *
   * @param <T>        the type of elements.
   * @param comparator the comparator to use for ordering.
   * @param iterable   an iterable containing the elements for the set.
   * @return a new {@code SortedArraySet} containing the elements.
   */
  public static <T> SortedArraySet<T> from(Comparator<T> comparator, Iterable<T> iterable) {
    SortedArraySet<T> set = new SortedArraySet<>(comparator);
    for (T element : iterable) {
      set.insert(element);
    }
    return set;
  }

  /**
   * Creates a new {@code SortedArraySet} from an iterable, ordered by their natural ordering.
   * <p> Time complexity: O(n^2) in the worst case, due to repeated insertions.
   *
   * @param <T>      the type of elements.
   * @param iterable an iterable containing the elements for the set.
   * @return a new {@code SortedArraySet} containing the elements.
   */
  public static <T extends Comparable<? super T>> SortedArraySet<T> from(Iterable<T> iterable) {
    return from(Comparator.naturalOrder(), iterable);
  }

  /**
   * Creates a new {@code SortedArraySet} containing the same elements as the given sorted set.
   * <p> This is an efficient O(n) operation as it leverages the sorted nature of the source set.
   * <p> Time complexity: O(n)
   *
   * @param <T> the type of elements.
   * @param that the sorted set to be copied.
   * @return a new {@code SortedArraySet} with the same elements.
   */
  public static <T> SortedArraySet<T> copyOf(SortedSet<T> that) {
    SortedArraySet<T> copy = new SortedArraySet<>(that.comparator(),that.size() == 0?DEFAULT_INITIAL_CAPACITY:that.size());
    for(T element : that){
      copy.append(element);
    }
    return copy;

  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void clear() {
    for(int i = 0 ; i<size;i++){
      elements[i] = null;
    }
    size = 0;
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
  public boolean isEmpty() { return size == 0; }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public int size() { return size; }

  /**
   * Ensures the array has enough capacity for at least one more element.
   */
  private void ensureCapacity() {
    if (size == elements.length) {
      elements = Arrays.copyOf(elements, elements.length * 2);
    }
  }

  /**
   * A helper class that uses binary search to find an element's position.
   * <p>
   * If the element is found, {@code found} is true and {@code index} is its position.
   * If not found, {@code found} is false and {@code index} is the position where it
   * should be inserted to maintain order.
   */
  private final class Finder {
    boolean found;
    int index;

    Finder(T element) {
      // Standard binary search algorithm
      int left = 0;
      int right = size - 1;
      int mid;
      while (left <= right) {
        mid = left + (right - left) / 2; // Avoids potential overflow
        int cmp = comparator.compare(element, elements[mid]);
        if (cmp == 0) {
          this.found = true;
          this.index = mid;
          return;
        } else if (cmp > 0) {
          left = mid + 1;
        } else {
          right = mid - 1;
        }
      }
      this.found = false;
      this.index = left;
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n), due to the potential need to shift elements.
   * The search itself is O(log n).
   */
  @Override
  public void insert(T element) {
    Finder find = new Finder(element);
    if(find.found){
      // element found at finder.index;overwrite
      elements[find.index] = element;

    } else{
      // element not found; insert at finder.index to keep order
      ensureCapacity();
      //shift right
      for(int i = size; i > find.index;i--){
        elements[i] = elements[i-1];
      }
      elements[find.index] = element;
      size++;
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public boolean contains(T element) {
    Finder find = new Finder(element);
    return find.found;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n), due to the potential need to shift elements.
   * The search itself is O(log n).
   */
  @Override
  public void delete(T element) {
    Finder finder = new Finder(element);
    if(finder.found){
      for(int i = finder.index ; i < size-1;i++){
        elements[i] = elements[i + 1];
      }
      elements[size-1] = null;
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
      throw new NoSuchElementException("No elements found");
    }
    return elements[0];
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public T maximum() {
    if(isEmpty()){
      throw new NoSuchElementException("No elements found");
    }
    return elements[size-1];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<T> iterator() {
    return new SortedArraySetIterator();
  }

  /**
   * An iterator for this {@code SortedArraySet}.
   */
  private final class SortedArraySetIterator implements Iterator<T> {
    private int currentIndex = 0;

    @Override
    public boolean hasNext() { return currentIndex<size; }

    @Override
    public T next() {
      if(!hasNext()){
        throw new NoSuchElementException("No more elements");
      }
      T element = elements[currentIndex];
      currentIndex++;
      return element;}
  }

  /**
   * Appends an element to the end of the set's internal array.
   * <p>
   * PRECONDITION: This method assumes the element being appended is greater than all
   * existing elements in the set, thus maintaining the sorted order. It is an
   * efficient O(1) amortized operation used for optimized set construction.
   *
   * @param element The element to append.
   */
  private void append(T element) {
    assert size == 0 || comparator.compare(element, elements[size - 1]) > 0;
    ensureCapacity();
    elements[size] = element;
    size++;
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
  public static <T> SortedArraySet<T> difference(SortedSet<T> set1, SortedSet<T> set2) {
    checkSameComparator(set1, set2, "difference");
    throw new UnsupportedOperationException("Not implemented yet");
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n + m)
   */
  public static <T> SortedArraySet<T> intersection(SortedSet<T> set1, SortedSet<T> set2) {
    checkSameComparator(set1, set2, "intersection");
    Iterator<T> it1 = set1.iterator();
    Iterator<T> it2 = set2.iterator();

    T element1 = nextOrNull(it1);
    T element2 = nextOrNull(it2);

    int capacity = Math.min(set1.size(),set2.size());
    if(capacity == 0){
      capacity = DEFAULT_INITIAL_CAPACITY;
    }
    SortedArraySet<T> i= new SortedArraySet<>(set1.comparator(),capacity);

    while(element1 != null || element2 != null){ // while we have at least one element to add
      int cmp = i.comparator.compare(element1,element2);
      if(cmp<0){
        element1 = nextOrNull(it1);
      } else if(cmp > 0){
        element2 = nextOrNull(it2);
      } else{
        i.append(element1);
        element1 = nextOrNull(it1);
        element2 = nextOrNull(it2);

      }
    }

    return i;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n + m)
   */
  public static <T> SortedArraySet<T> union(SortedSet<T> set1, SortedSet<T> set2) {
    checkSameComparator(set1, set2, "union");
    Iterator<T> it1 = set1.iterator();
    Iterator<T> it2 = set2.iterator();

    T element1 = nextOrNull(it1);
    T element2 = nextOrNull(it2);
    int capacity = set1.size() + set2.size();
    if(capacity == 0){
      capacity = DEFAULT_INITIAL_CAPACITY;
    }

    SortedArraySet<T> u = new SortedArraySet<>(set1.comparator(),capacity);

    while(element1 != null || element2 != null){ // while we have at least one element to add
      if(element1 == null){
        // No more elements from first set
        u.append(element2);
        element2 = nextOrNull(it2);
      } else if(element2 == null){
        // No more elements from second set
        u.append(element1);
        element1 = nextOrNull(it1);

      } else{
        // at least one element from eac set, compare them

        int cmp = u.comparator.compare(element1,element2);
        if(cmp == 0){
          // repeated elements
          u.append(element1);
          element1 = nextOrNull(it1);
          element2 = nextOrNull(it2);
        } else if( cmp < 0){
          // element1 comes before element2
          u.append(element1);
          element1 = nextOrNull(it1);
        } else{
          // element2 comes before element1
          u.append(element2);
          element2 = nextOrNull(it2);
        }
      }
    }
    return u;
  }
}