package org.uma.ed.datastructures.utils.toString;

import java.util.StringJoiner;

/**
 * Provides a utility method to generate a standardized string representation for collections.
 * <p>
 * The generated format is {@code ClassName(element1, element2, ...)}, which is consistent
 * across the entire data structures library. This class handles {@code null} elements
 * gracefully by representing them as the string "null".
 * <p>
 * This is a utility class and is not meant to be instantiated.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public final class ToString {

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private ToString() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Returns a standardized string representation of a collection.
   * <p>
   * This method takes a collection object (to derive the class name) and an iterable
   * (to access the elements). It formats them into a string like "ClassName(elem1, elem2, elem3)".
   *
   * @param <T>              the type of elements in the iterable.
   * @param collectionObject the collection instance from which the class name is extracted.
   * @param elements         an iterable providing the elements to include in the string.
   * @return a formatted string representation of the collection.
   */
  public static <T> String toString(Object collectionObject, Iterable<T> elements) {
    String className = collectionObject.getClass().getSimpleName();
    StringJoiner stringJoiner = new StringJoiner(", ", className + "(", ")");
    for (T element : elements) {
      // Use String.valueOf() to safely handle null elements without throwing a NullPointerException.
      stringJoiner.add(String.valueOf(element));
    }
    return stringJoiner.toString();
  }

  /**
   * Returns a standardized string representation of an iterable collection.
   * <p>
   * This is a convenience overload that uses the iterable itself as the object from which
   * to derive the class name.
   *
   * @param <T>      the type of elements in the iterable.
   * @param iterable the iterable collection to represent as a string.
   * @return a formatted string representation of the iterable.
   */
  public static <T> String toString(Iterable<T> iterable) {
    return toString(iterable, iterable);
  }
}