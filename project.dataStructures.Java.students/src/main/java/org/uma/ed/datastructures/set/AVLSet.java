package org.uma.ed.datastructures.set;

import org.uma.ed.datastructures.searchtree.AVL;
import org.uma.ed.datastructures.searchtree.SearchTree;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of the {@link SortedSet} interface using a balanced {@link AVL} tree.
 * <p>
 * This class serves as an adapter, delegating all set operations to an underlying
 * {@code AVL} tree instance. This design leverages the AVL tree's self-balancing
 * property to guarantee logarithmic time complexity O(log n) for core operations like
 * {@code insert}, {@code delete}, and {@code contains}.
 *
 * @param <T> The type of elements held in this sorted set.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class AVLSet<T> extends AbstractSortedSet<T> implements SortedSet<T> {

  /**
   * The underlying AVL tree that stores the set's elements.
   */
  private final SearchTree<T> avlTree;

  /**
   * Private constructor to wrap an existing {@code AVL} tree.
   */
  private AVLSet(AVL<T> avlTree) {
    this.avlTree = avlTree;
  }

  /**
   * Constructs an empty {@code AVLSet} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public AVLSet(Comparator<T> comparator) {
    this(AVL.empty(comparator));
  }

  /**
   * Creates an empty {@code AVLSet} with a specified comparator.
   * <p> Time complexity: O(1)
   */
  public static <T> AVLSet<T> empty(Comparator<T> comparator) {
    return new AVLSet<>(comparator);
  }

  /**
   * Creates an empty {@code AVLSet} with natural ordering.
   * <p> Time complexity: O(1)
   */
  public static <T extends Comparable<? super T>> AVLSet<T> empty() {
    return AVLSet.<T>empty(Comparator.naturalOrder());
  }

  /**
   * Creates a new {@code AVLSet} from the given elements, ordered by the specified comparator.
   * <p> Time complexity: O(m log n), where m is the number of elements to insert and n is the final size.
   */
  @SafeVarargs
  public static <T> AVLSet<T> of(Comparator<T> comparator, T... elements) {
    AVLSet<T> avlSet = new AVLSet<>(comparator);
    avlSet.insert(elements);
    return avlSet;
  }

  /**
   * Creates a new {@code AVLSet} from the given elements with natural ordering.
   * <p> Time complexity: O(m log n)
   */
  @SafeVarargs
  public static <T extends Comparable<? super T>> AVLSet<T> of(T... elements) {
    return of(Comparator.naturalOrder(), elements);
  }

  /**
   * Creates a new {@code AVLSet} from an iterable, ordered by the specified comparator.
   * <p> Time complexity: O(m log n)
   */
  public static <T> AVLSet<T> from(Comparator<T> comparator, Iterable<T> iterable) {
    AVLSet<T> avlSet = new AVLSet<>(comparator);
    for (T element : iterable) {
      avlSet.insert(element);
    }
    return avlSet;
  }

  /**
   * Creates a new {@code AVLSet} from an iterable with natural ordering.
   * <p> Time complexity: O(m log n)
   */
  public static <T extends Comparable<? super T>> AVLSet<T> from(Iterable<T> iterable) {
    return from(Comparator.naturalOrder(), iterable);
  }

  /**
   * Creates a new {@code AVLSet} that is a copy of the given one.
   * <p> This is an efficient O(n) operation as it copies the tree structure directly.
   * <p> Time complexity: O(n)
   */
  public static <T> AVLSet<T> copyOf(AVLSet<T> that) {
    return new AVLSet<>(AVL.copyOf(that.avlTree));
  }

  /**
   * Creates a new {@code AVLSet} from any {@code SortedSet}.
   * <p> Time complexity: O(n log n) due to repeated insertions.
   */
  public static <T> AVLSet<T> copyOf(SortedSet<T> that) {
    AVLSet<T> copy = new AVLSet<>(that.comparator());
    for(T element : that){
      copy.insert(element);
    }
    return copy;
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public Comparator<T> comparator() {
    return avlTree.comparator();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public boolean isEmpty() {
    return avlTree.isEmpty();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public int size() {
    return avlTree.size();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public void insert(T element) {
    avlTree.insert(element);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public boolean contains(T element) {
    return avlTree.contains(element);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public void delete(T element) {
    avlTree.delete(element);
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(1)
   */
  @Override
  public void clear() {
    avlTree.clear();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public T minimum() {
    if(isEmpty()){
      throw new NoSuchElementException("minimum on empty set");
    }
    return avlTree.minimum();
  }

  /**
   * {@inheritDoc}
   * <p> Time complexity: O(log n)
   */
  @Override
  public T maximum() {
    if(isEmpty()){
      throw new NoSuchElementException("minimum on empty set");
    }
    return avlTree.maximum();
  }

  /**
   * {@inheritDoc}
   * The iterator returns elements in ascending sorted order.
   */
  @Override
  public Iterator<T> iterator() {
    return avlTree.inOrder().iterator();
  }
}