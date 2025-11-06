package org.uma.ed.datastructures.heap;

import java.util.Arrays;
import java.util.Comparator;

/**
 * An implementation of the {@link Heap} interface using a min-heap stored implicitly in a dynamic array.
 * <p>
 * A binary heap is a specialized tree-based data structure that satisfies two key properties:
 * <ol>
 *     <li><b>Shape Property:</b> It is a <strong>complete binary tree</strong>. This means that all levels
 *         of the tree are completely filled, except possibly the last level, which is
 *         filled from left to right. This regular structure is crucial, as it allows the
 *         tree to be stored efficiently in an array without explicit pointers.</li>
 *     <li><b>Heap Property:</b> The element in any given node is less than or equal to the
 *         elements in its children nodes (for a min-heap). This ensures the smallest
 *         element is always at the root.</li>
 * </ol>
 * Because a complete binary tree has a predictable, dense structure, the relationships
 * (parent, left child, right child) can be calculated directly from a node's index
 * in the array, eliminating the need for node objects and pointers.
 *
 * @param <T> Type of elements in heap.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class BinaryHeap<T> implements Heap<T> {
  /*
   * INVARIANT:
   * - The array elements[0..size-1] represents a heap-ordered, complete binary tree.
   * - The tree's root is at index 0.
   * - For any node at index i:
   *   - The parent is at index (i-1)/2.
   *   - The left child is at index 2*i+1.
   *   - The right child is at index 2*i+2.
   */

  /**
   * Default initial capacity for the heap.
   */
  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  /**
   * Index of the root element in the heap array.
   */
  private static final int ROOT_INDEX = 0;

  /**
   * Comparator used for ordering the elements in the heap.
   */
  private final Comparator<T> comparator;

  /**
   * Current number of elements in the heap.
   */
  private int size;

  /**
   * Array used to store the elements in the heap.
   */
  private T[] elements;

  /**
   * Private constructor for internal use by factory methods.
   */
  private BinaryHeap(Comparator<T> comparator, int size, T[] elements) {
    this.comparator = comparator;
    this.size = size;
    this.elements = elements;
  }

  /**
   * Creates an empty binary heap. Initial capacity is {@code initialCapacity} elements.
   * <p> Time complexity: O(1)
   *
   * @param comparator comparator for defining order of elements.
   * @param initialCapacity Initial capacity.
   * @throws IllegalArgumentException if initial capacity is less than 1.
   */
  @SuppressWarnings("unchecked")
  public BinaryHeap(Comparator<T> comparator, int initialCapacity) {
    if (initialCapacity <= 0) {
      throw new IllegalArgumentException("initial capacity must be greater than 0");
    }
    this.comparator = comparator;
    this.elements = (T[]) new Object[initialCapacity];
    this.size = 0;
  }

  /**
   * Creates an empty binary heap with provided comparator and default initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param comparator comparator for defining order of elements.
   */
  public BinaryHeap(Comparator<T> comparator) {
    this(comparator, DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Creates an empty binary heap with the provided comparator and default initial capacity.
   * <p> Time complexity: O(1)
   *
   * @param comparator The comparator used to order the elements in the heap.
   * @return A new empty binary heap.
   */
  public static <T> BinaryHeap<T> empty(Comparator<T> comparator) {
    return new BinaryHeap<>(comparator, DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Creates a binary heap with the given initial capacity and uses the natural order comparator.
   * <p> Time complexity: O(1)
   *
   * @param initialCapacity The initial capacity of the heap.
   * @return A new binary heap with the specified initial capacity.
   */
  public static <T extends Comparable<? super T>> BinaryHeap<T> withCapacity(int initialCapacity) {
    return new BinaryHeap<T>(Comparator.naturalOrder(), initialCapacity);
  }

  /**
   * Creates an empty binary heap with the natural order comparator and default initial capacity.
   * <p> Time complexity: O(1)
   *
   * @return A new empty binary heap.
   */
  public static <T extends Comparable<? super T>> BinaryHeap<T> empty() {
    return new BinaryHeap<T>(Comparator.naturalOrder(), DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Creates a binary heap with the given initial capacity and comparator.
   * <p> Time complexity: O(1)
   *
   * @param comparator The comparator used to order the elements in the heap.
   * @param initialCapacity The initial capacity of the heap.
   * @return A new binary heap with the specified initial capacity and comparator.
   */
  public static <T> BinaryHeap<T> withCapacity(Comparator<T> comparator, int initialCapacity) {
    return new BinaryHeap<>(comparator, initialCapacity);
  }

  /**
   * Creates a binary heap from given elements using the efficient O(n) heapify algorithm.
   * <p>
   * This method is more efficient than inserting elements one by one (which would be O(n log n)).
   * It works by placing all elements into the array and then calling {@code heapifyDown}
   * on all non-leaf nodes, from the last one up to the root.
   * <p> Time complexity: O(n)
   *
   * @param comparator comparator for defining order of elements.
   * @param elements elements to include in the heap.
   * @param <T> type of elements.
   * @return a binary heap containing the elements.
   */
  @SafeVarargs
  public static <T> BinaryHeap<T> of(Comparator<T> comparator, T... elements) {
    int size = elements.length;
    BinaryHeap<T> heap = BinaryHeap.withCapacity(comparator, size > 0 ? size : DEFAULT_INITIAL_CAPACITY);
    // Copy elements into the heap's internal array.
    System.arraycopy(elements, 0, heap.elements, 0, size);
    heap.size = size;
    // Heapify phase: Start from the parent of the last element and move up to the root.
    for (int i = size / 2 - 1; i >= 0; i--) {
      heap.heapifyDown(i);
    }
    return heap;
  }

  /**
   * Creates a binary heap from given elements with natural order, using the O(n) heapify algorithm.
   * <p> Time complexity: O(n)
   *
   * @param elements elements to include in the heap.
   * @param <T> type of elements.
   * @return a binary heap containing the elements.
   */
  @SafeVarargs
  public static <T extends Comparable<? super T>> BinaryHeap<T> of(T... elements) {
    return of(Comparator.naturalOrder(), elements);
  }

  /**
   * Creates a binary heap from an iterable collection using the O(n) heapify algorithm.
   * <p> Time complexity: O(n)
   *
   * @param comparator comparator for defining order of elements.
   * @param iterable elements to include in the heap.
   * @param <T> type of elements.
   * @return a binary heap containing the elements from the iterable.
   */
  public static <T> BinaryHeap<T> from(Comparator<T> comparator, Iterable<T> iterable) {
    // first count elements in iterable
    int size = 0;
    for (T _ : iterable) {
      size++;
    }

    // then create heap and copy elements
    BinaryHeap<T> heap = BinaryHeap.withCapacity(comparator, size > 0 ? size : DEFAULT_INITIAL_CAPACITY);
    int i = 0;
    for (T element : iterable) {
      heap.elements[i] = element;
      i++;
    }
    heap.size = size;

    // heapify down from last non-leaf to root
    for (int j = size / 2 - 1; j >= 0; j--) {
      heap.heapifyDown(j);
    }
    return heap;
  }

  /**
   * Creates a binary heap from an iterable with natural order, using the O(n) heapify algorithm.
   * <p> Time complexity: O(n)
   *
   * @param iterable elements to include in the heap.
   * @param <T> type of elements.
   * @return a binary heap containing the elements from the iterable.
   */
  public static <T extends Comparable<? super T>> BinaryHeap<T> from(Iterable<T> iterable) {
    return from(Comparator.naturalOrder(), iterable);
  }

  /**
   * Creates a new binary heap with the same elements and comparator as the given heap.
   * <p> Time complexity: O(n)
   *
   * @param that heap to copy.
   * @param <T> type of elements.
   * @return a new binary heap that is a copy of the given heap.
   */
  @SuppressWarnings("unchecked")
  public static <T> BinaryHeap<T> copyOf(BinaryHeap<T> that) {
    BinaryHeap<T> copy = new BinaryHeap<T>(that.comparator, that.size == 0 ? DEFAULT_INITIAL_CAPACITY : that.size);
    for(int i = 0; i < copy.size; i++) {
      copy.elements[i] = that.elements[i];
    }
    copy.size = that.size;
    return copy;
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
  public int size() {
    return size;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(n)
   */
  @Override
  public void clear() {
    for(int i = 0; i < size; i++) {
      elements[i] = null;
    }
    size = 0;
  }

  /**
   * Ensures the internal array has enough capacity for at least one more element.
   * If the array is full, its capacity is doubled.
   */
  private void ensureCapacity() {
    if (size == elements.length) {
      elements = Arrays.copyOf(elements, 2 * elements.length);
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: Amortized O(log n). A single insertion can be O(n) if resizing occurs.
   */
  @Override
  public void insert(T element) {
    ensureCapacity();
    //place new element at right in las level
    elements[size] = element;
    heapifyUp(size);
    size++;
  }

  // Helper method: Checks if element at index1 has higher priority (is smaller) than element at index2.
  private boolean lessThan(int index1, int index2) {
    return comparator.compare(elements[index1], elements[index2]) < 0;
  }

  // Helper method: Swaps two elements in the internal array.
  private void swap(int index1, int index2) {
    T temp = elements[index1];
    elements[index1] = elements[index2];
    elements[index2] = temp;
  }

  // Helper method: Checks if the node at the given index is the root.
  private static boolean isRoot(int index) {
    return index == ROOT_INDEX;
  }

  // Helper method: Computes the index of the parent of the node at the given index.
  private static int parent(int index) {
    return (index - 1) / 2;
  }

  // Helper method: Computes the index of the left child of the node at the given index.
  private static int leftChild(int index) {
    return 2 * index + 1;
  }

  // Helper method: Computes the index of the right child of the node at the given index.
  private static int rightChild(int index) {
    return 2 * index + 2;
  }

  // Helper method: Checks if an index corresponds to a valid node within the heap.
  private boolean isNode(int index) {
    return index < size;
  }

  // Helper method: Checks if the node at the given index has a left child.
  private boolean hasLeftChild(int index) {
    return leftChild(index) < size;
  }

  // Helper method: Checks if the node at the given index is a leaf.
  // In a complete tree, if there's no left child, there's no right child either.
  private boolean isLeaf(int index) {
    return !hasLeftChild(index);
  }

  /**
   * Restores the heap property by moving an element upwards from a given index
   * until it is in its correct position. Also known as "sift-up" or "bubble-up".
   *
   * @param index The index of the element to move up.
   */
  private void heapifyUp(int index) {
    while (isRoot(index)) {
      int indexParent = parent(index);
      if(lessThan(index, indexParent)) {
        swap(index, indexParent);
        index = indexParent;
      }else{
        break;
      }
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public T minimum() {
    if(isEmpty()){
      throw new EmptyHeapException("minimum on empty heap");
    }
    return elements[ROOT_INDEX];
  }

  /**
   * Restores the heap property by moving an element downwards from a given index
   * until it is in its correct position. Also known as "sift-down" or "bubble-down".
   *
   * @param index The index of the element to move down.
   */
  private void heapifyDown(int index) {
    while(!isLeaf(index)) {
      int indexLeftChild = leftChild(index);
      int indexMinElem = indexLeftChild;

      //does it also have a right child?
      int indexRightChild = rightChild(index);
      if (isNode(indexRightChild) && lessThan(indexRightChild, indexMinElem)) {
        indexMinElem = indexRightChild;
      }

      if(lessThan(indexMinElem, index)) {
        swap(index, indexMinElem);
        index = indexMinElem;
      } else {
        break;
      }
    }
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public void deleteMinimum() {
    //move rightmost element at last level to root
    elements[ROOT_INDEX] = elements[size - 1];
    elements[size - 1] = null;
    heapifyDown(ROOT_INDEX);
    size--;
  }

  /**
   * Returns a string representation of the heap, structured as a tree.
   * This is for debugging purposes and provides a visual representation of the heap structure.
   */
  @Override
  public String toString() {
    String className = getClass().getSimpleName();
    StringBuilder sb = new StringBuilder(className).append("(");
    toString(sb, ROOT_INDEX);
    sb.append(")");
    return sb.toString();
  }

  /**
   * Helper method for recursively building the string representation of the heap tree.
   *
   * @param sb The StringBuilder to append to.
   * @param index The index of the current node.
   */
  private void toString(StringBuilder sb, int index) {
    if (!isNode(index)) {
      sb.append("null");
    } else {
      sb.append("Node(");
      toString(sb, leftChild(index));
      sb.append(", ");
      sb.append(elements[index]);
      sb.append(", ");
      toString(sb, rightChild(index));
      sb.append(")");
    }
  }
}