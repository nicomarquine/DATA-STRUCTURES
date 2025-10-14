package org.uma.ed.datastructures.list;

import java.util.Objects;

/**
 * Represents a List, an ordered collection of elements (also known as a sequence).
 * <p>
 * This interface allows duplicate elements. Each element in a list has an index, which
 * is a non-negative integer representing its position (0 for the first element, 1 for
 * the second, and so on).
 * <p>
 * The user of this interface has precise control over where in the list each element is
 * inserted. They can access elements by their integer index and search for elements in the list.
 *
 * @param <T> The type of elements held in this list.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public interface List<T> extends Iterable<T> {

  /**
   * Checks if this list is empty.
   *
   * @return {@code true} if this list contains no elements, {@code false} otherwise.
   */
  boolean isEmpty();

  /**
   * Returns the number of elements in this list.
   *
   * @return the total number of elements in the list.
   */
  int size();

  /**
   * Inserts the specified element at the specified position in this list.
   * <p>
   * Shifts the element currently at that position (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * @param index   the index at which the specified element is to be inserted.
   * @param element the element to be inserted.
   * @throws ListException if the index is out of range ({@code index < 0 || index > size()}).
   */
  void insert(int index, T element);

  /**
   * Appends the specified element to the end of this list.
   *
   * @param element the element to be appended to this list.
   */
  void append(T element);

  /**
   * Inserts the specified element at the beginning of this list.
   * <p>
   * Shifts the element currently at position 0 (if any) and any subsequent
   * elements to the right (adds one to their indices).
   *
   * @param element the element to be inserted at the beginning of this list.
   */
  void prepend(T element);

  /**
   * Appends all of the elements in the specified varargs array to the end of this list,
   * in the order that they are specified.
   *
   * @param elements the elements to be appended to this list.
   */

  @SuppressWarnings("unchecked")
  default void append(T... elements) {
    for (T element : elements) {
      append(element);
    }
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index the index of the element to return.
   * @return the element at the specified position in the list.
   * @throws ListException if the index is out of range ({@code index < 0 || index >= size()}).
   */
  T get(int index);

  /**
   * Replaces the element at the specified position in this list with the specified element.
   *
   * @param index   the index of the element to replace.
   * @param element the element to be stored at the specified position.
   * @throws ListException if the index is out of range ({@code index < 0 || index >= size()}).
   */
  void set(int index, T element);

  /**
   * Removes the element at the specified position in this list.
   * <p>
   * Shifts any subsequent elements to the left (subtracts one from their indices).
   *
   * @param index the index of the element to be removed.
   * @throws ListException if the index is out of range ({@code index < 0 || index >= size()}).
   */
  void delete(int index);

  /**
   * Returns {@code true} if this list contains the specified element.
   * <p>
   * More formally, returns {@code true} if and only if this list contains at least one
   * element {@code e} such that {@code Objects.equals(element, e)}.
   *
   * @param element the element whose presence in this list is to be tested.
   * @return {@code true} if this list contains the specified element.
   */
  default boolean contains(T element) {
    for (T elem : this) {
      if (Objects.equals(elem, element)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Removes all of the elements from this list, leaving it empty.
   */
  void clear();
}