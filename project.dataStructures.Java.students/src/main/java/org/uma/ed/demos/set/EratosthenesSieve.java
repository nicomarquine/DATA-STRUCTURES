package org.uma.ed.demos.set;

import org.uma.ed.datastructures.range.Range;
import org.uma.ed.datastructures.set.AVLSet;
import org.uma.ed.datastructures.set.SortedSet;

/**
 * An implementation of the Sieve of Eratosthenes algorithm to find prime numbers,
 * demonstrating the use of a {@link SortedSet}.
 * <p>
 * The algorithm works as follows:
 * <ol>
 *     <li>Start with a set of candidate numbers, initially all integers from 2 to a given limit {@code n}.</li>
 *     <li>Take the smallest candidate number, {@code p}. This number is prime.</li>
 *     <li>Remove {@code p} and all of its multiples from the set of candidates.</li>
 *     <li>Repeat the process with the new smallest candidate, until the candidate set is empty.</li>
 * </ol>
 * This implementation uses a {@code SortedSet} to efficiently store and retrieve the
 * smallest candidate at each step.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class EratosthenesSieve {

  /**
   * Computes all prime numbers up to a given limit using the Sieve of Eratosthenes.
   *
   * @param n the upper bound (inclusive) for finding prime numbers. Must be >= 2.
   * @return a {@code SortedSet} containing all prime numbers from 2 to {@code n}.
   */
  public static SortedSet<Integer> primesUpTo(int n) {
    if (n < 2) {
      // There are no primes less than 2.
      return AVLSet.empty();
    }

    // 1. Initialize the set of candidates with all integers from 2 to n.
    SortedSet<Integer> candidates = AVLSet.from(Range.inclusive(2, n));
    SortedSet<Integer> primes = AVLSet.empty();

    while (!candidates.isEmpty()) {
      // 2. The smallest number remaining in the candidates set is the next prime.
      int prime = candidates.minimum();
      primes.insert(prime);

      // 3. Remove this prime and all of its multiples from the candidates.
      //    We can start removing from `prime` itself.
      for (int multiple = prime; multiple <= n && multiple > 0; multiple += prime) {
        // The check `multiple > 0` prevents infinite loops in case of integer overflow.
        candidates.delete(multiple);
      }
    }
    return primes;
  }

  public static void main(String[] args) {
    final int LIMIT = 100_000;
    System.out.println("Calculating prime numbers up to " + LIMIT + " using the Sieve of Eratosthenes...");

    long startTime = System.currentTimeMillis();
    SortedSet<Integer> primes = primesUpTo(LIMIT);
    long endTime = System.currentTimeMillis();

    System.out.println("Found " + primes.size() + " prime numbers up to " + LIMIT + ".");
    System.out.println("Primes: " + primes); // Uncomment to see the full list

    long duration = endTime - startTime;
    System.out.printf("Calculation took %d milliseconds.%n", duration);
  }
}