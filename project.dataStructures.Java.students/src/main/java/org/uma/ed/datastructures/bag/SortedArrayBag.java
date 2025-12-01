package org.uma.ed.datastructures.bag;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of the {@link SortedBag} interface using a sorted dynamic array of bins.
 * <p>
 * Each "bin" stores a unique element and a count of its occurrences. The array of bins
 * is kept sorted according to the element's natural order or a provided comparator.
 * <p>
 * This structure provides logarithmic time complexity O(log n) for search operations
 * like {@code occurrences} and {@code contains}. However, {@code insert} and {@code delete}
 * operations require shifting elements in the array, resulting in linear time complexity O(n).
 * Accessing the minimum and maximum elements is an efficient O(1) operation.
 *
 * @param <T> The type of elements held in this sorted bag.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class SortedArrayBag<T> extends AbstractSortedBag<T> implements SortedBag<T> {

  /**
   * Default initial capacity for the underlying array.
   */
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  /**
   * An internal record to store a unique element and its number of occurrences.
   */
  private record Bin<E>(E element, int occurrences) {

    static <E> Bin<E> of(E element, int occurrences) {
      return new Bin<>(element, occurrences);
    }

    /**
     * A factory for creating a "search key" bin. The occurrence count is irrelevant for searching.
     */
    static <E> Bin<E> withElement(E element) {
      return new Bin<>(element, 0); // Occurrences can be a dummy value.
    }

    /**
     * Returns a comparator that compares {@code Bin} objects based on their element component.
     */
    static <E> Comparator<Bin<E>> byElement(Comparator<E> elementComparator) {
      return (b1, b2) -> elementComparator.compare(b1.element, b2.element);
    }
  }

  private final Comparator<T> comparator;
  private Bin<T>[] elements;
  private int bins; // Number of bins in use
  private int size; // Total number of elements including duplicates

  /**
   * Constructs an empty {@code SortedArrayBag} with a specified comparator and initial capacity.
   * <p>Time complexity: O(1)</p>
   */
  @SuppressWarnings("unchecked")
  public SortedArrayBag(Comparator<T> comparator, int initialCapacity) {
    if (initialCapacity <= 0) {
      throw new IllegalArgumentException("Initial capacity must be greater than 0");
    }
    this.comparator = comparator;
    this.elements = (Bin<T>[]) new Bin[initialCapacity];
    this.bins = 0;
    this.size = 0;
  }

  /**
   * Constructs an empty {@code SortedArrayBag} with a specified comparator.
   * <p>Time complexity: O(1)</p>
   */
  public SortedArrayBag(Comparator<T> comparator) {
    this(comparator, DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Creates an empty {@code SortedArrayBag} with natural ordering.
   * <p>Time complexity: O(1)</p>
   */
  public static <T extends Comparable<? super T>> SortedArrayBag<T> empty() {
    return new SortedArrayBag<T>(Comparator.naturalOrder());
  }

  /**
   * Creates an empty {@code SortedArrayBag} with a specified comparator.
   * <p>Time complexity: O(1)</p>
   */
  public static <T> SortedArrayBag<T> empty(Comparator<T> comparator) {
    return new SortedArrayBag<>(comparator);
  }

  /**
   * Creates a new {@code SortedArrayBag} from the given elements.
   * <p>Time complexity: O(m^2) in the worst case due to repeated linear-time insertions.</p>
   */
  @SafeVarargs
  public static <T> SortedArrayBag<T> of(Comparator<T> comparator, T... elements) {
    SortedArrayBag<T> bag = new SortedArrayBag<>(comparator);
    bag.insert(elements);
    return bag;
  }

  /**
   * Creates a new {@code SortedArrayBag} from the given elements with natural ordering.
   * <p>Time complexity: O(m^2) in the worst case.</p>
   */
  @SafeVarargs
  public static <T extends Comparable<? super T>> SortedArrayBag<T> of(T... elements) {
    return of(Comparator.naturalOrder(), elements);
  }

  /**
   * Creates a new {@code SortedArrayBag} from an iterable.
   * <p>Time complexity: O(m^2) in the worst case.</p>
   */
  public static <T> SortedArrayBag<T> from(Comparator<T> comparator, Iterable<T> iterable) {
    SortedArrayBag<T> bag = new SortedArrayBag<>(comparator);
    for (T element : iterable) {
      bag.insert(element);
    }
    return bag;
  }

  /**
   * Creates a new {@code SortedArrayBag} from an iterable with natural ordering.
   * <p>Time complexity: O(m^2) in the worst case.</p>
   */
  public static <T extends Comparable<? super T>> SortedArrayBag<T> from(Iterable<T> iterable) {
    return from(Comparator.naturalOrder(), iterable);
  }

  /**
   * Creates a new {@code SortedArrayBag} from any {@code SortedBag}.
   * This is an efficient O(m) operation that leverages the sorted nature of the source.
   * <p>Time complexity: O(m), where m is the total number of elements in the source bag.</p>
   */
  public static <T> SortedArrayBag<T> copyOf(SortedBag<T> that) {
    if (that == null) {
      throw new IllegalArgumentException("Bag cannot be null");
    }

    SortedArrayBag<T> copy = new SortedArrayBag<>(that.comparator());

    T previous = null;
    int occurrences = 0;

    for (T elem : that) {
      if (previous == null) {
        previous = elem;
        occurrences = 1;
      } else if (copy.comparator.compare(elem, previous) == 0) {
        occurrences++;
      } else {
        // cerrar el bin anterior
        copy.ensureCapacity();
        copy.elements[copy.bins++] = new Bin<>(previous, occurrences);
        previous = elem;
        occurrences = 1;
      }
    }

    // cerrar último bin si hay elementos
    if (previous != null) {
      copy.ensureCapacity();
      copy.elements[copy.bins++] = new Bin<>(previous, occurrences);
    }

    copy.size = that.size();

    return copy;
  }

  @Override
  public Comparator<T> comparator() {
    return comparator;
  }

  @Override
  public boolean isEmpty() {
    return bins == 0;
  }

  @Override
  public int size() {
    return size;
  }

  /**
   * A helper class that uses binary search to find a bin's position.
   */
  private class Finder {
    final int index;
    final boolean found;

    Finder(T element) {
      // Arrays.binarySearch returns the index if found, or -(insertion point) - 1 if not found
      int index = Arrays.binarySearch(elements, 0, bins,
          Bin.withElement(element),
          Bin.byElement(comparator)
      );
      this.found = index >= 0;
      this.index = this.found ? index : -(index + 1);
    }
  }

  private void ensureCapacity() {
    if (bins == elements.length) {
      elements = Arrays.copyOf(elements, elements.length * 2);
    }
  }

  @Override
  public int occurrences(T element) {
    Finder finder = new Finder(element);

    return finder.found ? elements[finder.index].occurrences : 0;
  }

  @Override
  public void insert(T element) {
    Finder find = new Finder(element);
    if(isEmpty()){
      elements[0] = new Bin<>(element, 1);
      size++;
      bins++;
    }else{
      if(find.found){
        elements[find.index] = new Bin<>(element, elements[find.index].occurrences + 1);
        size++;
      }else{
        ensureCapacity();
        int insertPos = find.index; // posición donde debe ir
        // desplazar hacia la derecha: USAR bins (no size)
        for (int j = bins; j > insertPos; j--) {
          elements[j] = elements[j - 1];
        }

        elements[insertPos] = new Bin<>(element,1);
        size++;
        bins++;
      }
    }
  }

  @Override
  public void delete(T element) {
    if(isEmpty()){
      throw new NoSuchElementException("delete on empty bag");
    }else{
      Finder find = new Finder(element);
      if(find.found){
        if(elements[find.index].occurrences > 1){
          elements[find.index] = new Bin<>(element, elements[find.index].occurrences - 1);
          size--;
        }else{
          for(int i = find.index; i < size; i++){
            elements[i] = elements[i+1];
          }
          size--;
          bins--;
        }
      }
    }
  }

  @Override
  public void clear() {
    if(isEmpty()){
      throw new NoSuchElementException("clear on empty bag");
    }

    for(int i = 0; i < elements.length; i++){
      elements[i] = null;
    }
    size = 0;
    bins = 0;
  }

  @Override
  public T minimum() {
    if(isEmpty()){
      throw new NoSuchElementException("minimum on empty bag");
    }
    return elements[0].element;
  }

  @Override
  public T maximum() {
    if(isEmpty()){
      throw new NoSuchElementException("minimum on empty bag");
    }
    return elements[bins - 1].element;
  }

  @Override
  public Iterator<T> iterator() {
    return new BagIterator();
  }

  private final class BagIterator implements Iterator<T> {
    private int binIndex;
    private int occurrencesLeft;

    BagIterator() {
      this.binIndex = 0;
      if (bins > 0) {
        this.occurrencesLeft = elements[0].occurrences();
      } else {
        this.occurrencesLeft = 0;
      }
    }

    @Override
    public boolean hasNext() {
      return binIndex < bins;
    }

    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException("No more elements in BagIterator");
      }

      // elemento actual
      T elem = elements[binIndex].element();

      // consumir una ocurrencia
      occurrencesLeft--;

      // si se han agotado las ocurrencias de este bin...
      if (occurrencesLeft == 0) {
        binIndex++;  // pasar al siguiente bin

        // si no hemos acabado, cargar sus ocurrencias
        if (binIndex < bins) {
          occurrencesLeft = elements[binIndex].occurrences();
        }
      }

      return elem;
    }
  }
}