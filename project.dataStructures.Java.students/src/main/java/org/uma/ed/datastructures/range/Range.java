package org.uma.ed.datastructures.range;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents an arithmetic sequence of integers, commonly known as a range.
 * <p>
 * This class allows for the creation of sequences with a specified start, end, and step.
 * It implements {@link Iterable<Integer>}, enabling its use in enhanced for-loops.
 * The range is immutable.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public final class Range implements Iterable<Integer> {
  private final int from;
  private final int until;
  private final int step;

  /**
   * Constructs a new {@code Range}.
   *
   * @param from  The starting value of the sequence.
   * @param until The exclusive end value. The sequence will generate integers up to, but not including, this value.
   * @param step  The increment (or decrement) between consecutive integers in the sequence. Cannot be zero.
   * @throws IllegalArgumentException if {@code step} is zero.
   */
  private Range(int from, int until, int step) {
    if (step == 0) {
      throw new IllegalArgumentException("Step cannot be zero.");
    }
    this.from = from;
    this.until = until;
    this.step = step;
  }

  /**
   * Creates a range of integers from a starting value up to (but not including) an end value, with a step of 1.
   *
   * @param from The starting value (inclusive).
   * @param until The end value (exclusive).
   * @return a new {@code Range} instance.
   */
  public static Range exclusive(int from, int until) {
    return exclusive(from, until, 1);
  }

  /**
   * Creates a range of integers from a starting value up to (but not including) an end value, with a specified step.
   *
   * @param from The starting value (inclusive).
   * @param until The end value (exclusive).
   * @param step The step value. Can be positive or negative, but not zero.
   * @return a new {@code Range} instance.
   */
  public static Range exclusive(int from, int until, int step) {
    return new Range(from, until, step);
  }

  /**
   * Creates a range of integers from a starting value up to and including an end value, with a step of 1.
   *
   * @param from The starting value (inclusive).
   * @param to The end value (inclusive).
   * @return a new {@code Range} instance.
   */
  public static Range inclusive(int from, int to) {
    return inclusive(from, to, 1);
  }

  /**
   * Creates a range of integers from a starting value up to and including an end value, with a specified step.
   *
   * @param from The starting value (inclusive).
   * @param to The end value (inclusive).
   * @param step The step value. Can be positive or negative, but not zero.
   * @return a new {@code Range} instance.
   */
  public static Range inclusive(int from, int to, int step) {
    int until = to;
    if (step > 0) {
      until = to < Integer.MAX_VALUE ? to + 1 : to; // Handle potential overflow
    } else { // step < 0
      until = to > Integer.MIN_VALUE ? to - 1 : to; // Handle potential underflow
    }
    return new Range(from, until, step);
  }

  /**
   * Checks if a range defined by its parameters would be empty.
   *
   * @param start The starting value.
   * @param end   The exclusive end value.
   * @param step  The step value.
   * @return {@code true} if the range contains no elements, {@code false} otherwise.
   */
  private static boolean isEmpty(int start, int end, int step) {
    if (step > 0) {
      return start >= end;
    } else { // step < 0
      return start <= end;
    }
  }

  /**
   * Returns an iterator over the integers in this range.
   *
   * @return an {@code Iterator} for this range.
   */
  @Override
  public Iterator<Integer> iterator() {
    return new RangeIterator();
  }

  /**
   * An iterator for a {@code Range}.
   */
  private final class RangeIterator implements Iterator<Integer> {
    private int current;

    private RangeIterator() {
      this.current = from;
    }

    @Override
    public boolean hasNext() {
      return !isEmpty(current, until, step);
    }

    @Override
    public Integer next() {
      if (!hasNext()) {
        throw new NoSuchElementException("Range iterator has no more elements.");
      }
      int element = current;
      current += step;
      return element;
    }
  }

  /**
   * Checks if this range is empty (i.e., generates no integers).
   *
   * @return {@code true} if the range is empty, {@code false} otherwise.
   */
  public boolean isEmpty() {
    return isEmpty(from, until, step);
  }

  /**
   * Returns the number of elements in this range.
   * <p>
   * Warning: For very large ranges, this may overflow {@code int}.
   * The calculation is based on floating-point arithmetic to handle various step sizes correctly.
   *
   * @return the total number of integers in the sequence.
   */
  public int size() {
    if (isEmpty()) {
      return 0;
    }
    // Use long for intermediate calculations to prevent overflow with large ranges.
    long num = (long) until - from;
    long den = step;
    return (int) ((num + den + (den > 0 ? -1 : 1)) / den);
  }

  /**
   * Returns a string representation of the range.
   *
   * @return a string in the format {@code "Range(from, until, step)"}.
   */
  @Override
  public String toString() {
    return "Range(" + from + ", " + until + ", " + step + ")";
  }
}