package org.uma.ed.datastructures.priorityqueue;

import org.uma.ed.datastructures.heap.EmptyHeapException;
import org.uma.ed.datastructures.heap.MaxiphobicHeap;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of the {@link PriorityQueue} interface using a {@link MaxiphobicHeap}
 * as the underlying data structure.
 * <p>
 * This class serves as an adapter, delegating all priority queue operations to the
 * corresponding methods of a {@code MaxiphobicHeap} instance. This approach provides the
 * performance characteristics of a maxiphobic heap:
 * <ul>
 *     <li>{@code enqueue}: Amortized O(log n)</li>
 *     <li>{@code dequeue}: Amortized O(log n)</li>
 *     <li>{@code first}: O(1)</li>
 * </ul>
 *
 * @param <T> The type of elements held in this priority queue.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class MaxiphobicHeapPriorityQueue<T> extends AbstractPriorityQueue<T> implements PriorityQueue<T> {

  /**
   * The underlying {@code MaxiphobicHeap} instance.
   */
  private final MaxiphobicHeap<T> heap;

  /**
   * Private constructor to wrap an existing {@code MaxiphobicHeap}.
   */
  private MaxiphobicHeapPriorityQueue(MaxiphobicHeap<T> heap) {
    this.heap = heap;
  }

  /**
   * Constructs an empty {@code MaxiphobicHeapPriorityQueue} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public MaxiphobicHeapPriorityQueue(Comparator<T> comparator) {
    this(MaxiphobicHeap.empty(comparator));
  }

  /**
   * Creates an empty {@code MaxiphobicHeapPriorityQueue} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public static <T> MaxiphobicHeapPriorityQueue<T> empty(Comparator<T> comparator) {
    return new MaxiphobicHeapPriorityQueue<>(comparator);
  }

  /**
   * Creates an empty {@code MaxiphobicHeapPriorityQueue} with natural ordering.
   * <p> Time complexity: O(1)
   */
  public static <T extends Comparable<? super T>> MaxiphobicHeapPriorityQueue<T> empty() {
    return new MaxiphobicHeapPriorityQueue<T>(Comparator.naturalOrder());
  }

  /**
   * Creates a new {@code MaxiphobicHeapPriorityQueue} from the given elements.
   * <p> Time complexity: O(n), as it uses the efficient heapify algorithm.
   */
  @SafeVarargs
  public static <T> MaxiphobicHeapPriorityQueue<T> of(Comparator<T> comparator, T... elements) {
    return new MaxiphobicHeapPriorityQueue<>(MaxiphobicHeap.of(comparator, elements));
  }

  /**
   * Creates a new {@code MaxiphobicHeapPriorityQueue} from the given elements with natural ordering.
   * <p> Time complexity: O(n)
   */
  @SafeVarargs
  public static <T extends Comparable<? super T>> MaxiphobicHeapPriorityQueue<T> of(T... elements) {
    return of(Comparator.naturalOrder(), elements);
  }

  /**
   * Creates a new {@code MaxiphobicHeapPriorityQueue} from an iterable collection.
   * <p> Time complexity: O(n)
   */
  public static <T> MaxiphobicHeapPriorityQueue<T> from(Comparator<T> comparator, Iterable<T> iterable) {
    return new MaxiphobicHeapPriorityQueue<>(MaxiphobicHeap.from(comparator, iterable));
  }

  /**
   * Creates a new {@code MaxiphobicHeapPriorityQueue} from an iterable with natural ordering.
   * <p> Time complexity: O(n)
   */
  public static <T extends Comparable<? super T>> MaxiphobicHeapPriorityQueue<T> from(Iterable<T> iterable) {
    return from(Comparator.naturalOrder(), iterable);
  }

  /**
   * Creates a new {@code MaxiphobicHeapPriorityQueue} containing the same elements as the given queue.
   * <p> Time complexity: O(n)
   */
  public static <T> MaxiphobicHeapPriorityQueue<T> copyOf(MaxiphobicHeapPriorityQueue<T> queue) {
    return new MaxiphobicHeapPriorityQueue<>(MaxiphobicHeap.copyOf(queue.heap));
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public Comparator<T> comparator() { return heap.comparator();}

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public boolean isEmpty() { return heap.isEmpty();}

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void clear() { heap.clear();}

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public int size() { return heap.size();}

  /**
   * {@inheritDoc}
   * <p> Time complexity: Amortized O(log n)
   */
  @Override
  public void enqueue(T element) {
    heap.insert(element);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public T first() {
    if(heap.isEmpty()){
      throw new EmptyPriorityQueueException("first on empty queue");
    }
    return heap.minimum();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: Amortized O(log n)
   */
  @Override
  public void dequeue() {
    if(heap.isEmpty()){
      throw new EmptyPriorityQueueException("dequeue on empty queue");
    }
    heap.deleteMinimum();
  }

  /**
   * Provides an iterable that traverses the elements in priority order.
   * <p>
   * Note: This operation is expensive (O(n log n)) as it requires creating a
   * full copy of the heap and draining it.
   *
   * @return an {@code Iterable} for the elements of the priority queue.
   */
  @Override
  protected Iterable<T> elements() {
    return () -> new Iterator<>() {
      // Create a copy to avoid destroying the original heap during iteration.
      private final MaxiphobicHeap<T> copy = MaxiphobicHeap.copyOf(heap);

      @Override
      public boolean hasNext() {
        return !copy.isEmpty();
      }

      @Override
      public T next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        T element = copy.minimum();
        copy.deleteMinimum();
        return element;
      }
    };
  }
}