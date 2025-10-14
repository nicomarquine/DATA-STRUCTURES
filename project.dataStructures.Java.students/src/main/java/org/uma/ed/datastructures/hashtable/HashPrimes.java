package org.uma.ed.datastructures.hashtable;

import java.util.Arrays;

/**
 * A utility class for providing prime numbers to be used as capacities for hash tables.
 * <p>
 * Using a prime number as the size of a hash table's underlying array is a common
 * heuristic that helps to ensure a better, more uniform distribution of keys, especially
 * when the hash function is simple (like the common {@code hashCode() % array.length}).
 * It reduces the likelihood of collisions that can arise from patterns in the input data's
 * hash codes.
 * <p>
 * This class provides a pre-computed list of prime numbers and methods to efficiently
 * find a suitable prime for a given capacity requirement. It is a utility class and
 * is not meant to be instantiated.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public final class HashPrimes {

  /**
   * Private constructor to prevent instantiation of this utility class.
   */
  private HashPrimes() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * A pre-computed list of prime numbers, suitable for hash table capacities.
   * Each prime is roughly double the previous one.
   */
  private static final int[] PRIMES = {5, 11, 23, 53, 107, 223, 449, 907, 1823, 3659, 7321, 14653, 29311,
                                       58631, 117269, 234539, 469099, 938207, 1876417, 3752839, 7505681,
                                       15011389, 30022781, 60045577, 120091177, 240182359, 480364727,
                                       960729461, 1921458943};

  /**
   * Finds the smallest prime number from the pre-computed list that is greater than {@code 2 * n}.
   * <p>
   * This is a common strategy used during rehashing, where a table's capacity is
   * typically doubled to maintain a low load factor. This method ensures the new
   * capacity is a prime number slightly larger than double the previous size.
   *
   * @param n a number used to calculate the lower bound.
   * @return the smallest pre-computed prime that is {@code > 2 * n}.
   */
  public static int primeDoubleThan(int n) {
    // Find a prime greater than (2*n - 1), which is equivalent to finding a prime >= 2*n.
    return primeGreaterThan(2 * n - 1);
  }

  /**
   * Finds the smallest prime number from the pre-computed list that is strictly greater than {@code n}.
   * <p>
   * This method uses binary search on the sorted list of primes for efficient lookup.
   *
   * @param n the lower bound (exclusive) for the prime number.
   * @return the smallest pre-computed prime that is {@code > n}.
   * @throws RuntimeException if {@code n} is too large and no suitable prime exists in the list.
   */
  public static int primeGreaterThan(int n) {
    // Use binary search to find the insertion point for n + 1.
    int index = Arrays.binarySearch(PRIMES, n + 1);

    int insertionPoint;
    if (index >= 0) {
      // A prime equal to n + 1 was found. This is the correct prime.
      insertionPoint = index;
    } else {
      // No prime equal to n + 1 was found. The formula -(index + 1) gives the
      // index where n + 1 would be inserted. This is the index of the first
      // prime greater than n.
      insertionPoint = -(index + 1);
    }

    if (insertionPoint == PRIMES.length) {
      // This occurs if n is greater than or equal to the largest prime in our list.
      throw new RuntimeException("HashPrime.primeGreaterThan: argument " + n + " is too large for the pre-computed prime list.");
    } else {
      return PRIMES[insertionPoint];
    }
  }
}