package org.uma.ed.datastructures.stack;

/**
 * Represents a Stack, a Last-In-First-Out (LIFO) data structure.
 * <p>
 * This interface defines the standard operations for a stack, including pushing an element
 * onto the top, popping the top element, and inspecting the top element without removing it.
 * Implementations should follow a LIFO discipline.
 *
 * @param <T> The type of elements held in this stack.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public interface Stack<T> {

  /**
   * Checks if this stack is empty.
   *
   * @return {@code true} if this stack contains no elements, {@code false} otherwise.
   */
  boolean isEmpty();

  /**
   * Returns the number of elements in this stack.
   *
   * @return the total number of elements in the stack.
   */
  int size();

  /**
   * Pushes an element onto the top of this stack.
   *
   * @param element the element to be pushed onto the stack.
   */
  void push(T element);

  /**
   * Retrieves, but does not remove, the element at the top of this stack.
   *
   * @return the element at the top of the stack.
   * @throws EmptyStackException if the stack is empty.
   */
  T top();

  /**
   * Removes the element at the top of this stack.
   *
   * @throws EmptyStackException if the stack is empty.
   */
  void pop();

  /**
   * Removes all of the elements from this stack, leaving it empty.
   */
  void clear();
}