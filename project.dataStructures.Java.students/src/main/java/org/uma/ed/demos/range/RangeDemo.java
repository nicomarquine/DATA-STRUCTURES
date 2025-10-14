package org.uma.ed.demos.range;

import org.uma.ed.datastructures.range.Range;

/**
 * A demonstration class for the {@link Range} utility.
 * <p>
 * This class illustrates the various ways to create and iterate over a {@code Range},
 * including:
 * <ul>
 *     <li>Exclusive and inclusive ranges with positive steps.</li>
 *     <li>Ranges with negative steps (counting down).</li>
 *     <li>Empty ranges that produce no output.</li>
 *     <li>Ranges with a step of zero (which are disallowed and will throw an exception).</li>
 * </ul>
 */
public class RangeDemo {

  public static void main(String[] args) {
    System.out.println("--- Exclusive Range (1 to 10, step 2) ---");
    // Expected: 1, 3, 5, 7, 9
    for (Integer x : Range.exclusive(1, 11, 2)) {
      System.out.print(x + " ");
    }
    System.out.println("\n");

    System.out.println("--- Inclusive Range (0 to 10, step 2) ---");
    // Expected: 0, 2, 4, 6, 8, 10
    for (Integer x : Range.inclusive(0, 10, 2)) {
      System.out.print(x + " ");
    }
    System.out.println("\n");

    System.out.println("--- Exclusive Range with default step (1 to 9) ---");
    // Expected: 1, 2, 3, 4, 5, 6, 7, 8, 9
    for (Integer x : Range.exclusive(1, 10)) {
      System.out.print(x + " ");
    }
    System.out.println("\n");

    System.out.println("--- Empty Range (positive step, from >= until) ---");
    // Expected: (no output)
    System.out.print("Output: [");
    for (Integer x : Range.exclusive(10, 0, 1)) {
      System.out.print(x + " ");
    }
    System.out.println("]\n");


    System.out.println("--- Countdown Range (10 down to 1, exclusive) ---");
    // Expected: 10, 9, 8, 7, 6, 5, 4, 3, 2, 1
    for (Integer x : Range.exclusive(10, 0, -1)) {
      System.out.print(x + " ");
    }
    System.out.println("\n");

    System.out.println("--- Empty Countdown Range (from <= until) ---");
    // Expected: (no output)
    System.out.print("Output: [");
    for (Integer x : Range.exclusive(10, 20, -1)) {
      System.out.print(x + " ");
    }
    System.out.println("]\n");

    System.out.println("--- Testing size() method ---");
    System.out.println("Size of Range.inclusive(1, 5): " + Range.inclusive(1, 5).size()); // Expected: 5
    System.out.println("Size of Range.exclusive(1, 10, 2): " + Range.exclusive(1, 10, 2).size()); // Expected: 5
    System.out.println("Size of Range.exclusive(10, 0, -1): " + Range.exclusive(10, 0, -1).size()); // Expected: 10
    System.out.println("Size of an empty range: " + Range.exclusive(10, 0, 1).size()); // Expected: 0
    System.out.println();

    System.out.println("--- Testing disallowed zero step ---");
    try {
      // This is expected to throw an IllegalArgumentException
      Range.inclusive(1, 5, 0).iterator().hasNext();
      System.out.println("Error: Exception was not thrown for step = 0.");
    } catch (IllegalArgumentException e) {
      System.out.println("Successfully caught expected exception: " + e.getMessage());
    }
  }
}