package org.uma.ed.demos.bag;

import org.uma.ed.datastructures.bag.SortedLinkedBag;

/**
 * A simple test class to demonstrate the set-like operations (union, intersection,
 * and difference) specifically implemented for {@link SortedLinkedBag}.
 * <p>
 * This demo creates two sorted bags and then applies each of the three specialized
 * operations, printing the result to the console for visual verification. These methods
 * are efficient (O(n+m)) because they take advantage of the fact that both bags are sorted.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class SortedLinkedBagExtraTests {

  public static void main(String[] args) {

    // --- Setup: Create two SortedLinkedBags ---
    SortedLinkedBag<Integer> bag1 = SortedLinkedBag.of(1, 1, 2, 1, 3, 7, 5, 5, 5, 10, 11);
    SortedLinkedBag<Integer> bag2 = SortedLinkedBag.of(1, 1, 2, 1, 0, 3, 7, 5, 5, 9, 8, 15, 3);

    System.out.println("--- Initial Bags ---");
    System.out.println("Bag 1: " + bag1);
    System.out.println("Bag 2: " + bag2);
    System.out.println("--------------------\n");


    System.out.println("--- Union Operation (bag1 U bag2) ---");
    // The union contains all elements, with occurrences summed for common elements.
    SortedLinkedBag<Integer> unionResult = SortedLinkedBag.copyOf(bag1);
    unionResult.union(bag2); // Modifies unionResult in-place
    System.out.println("Result: " + unionResult);
    System.out.println();


    System.out.println("--- Intersection Operation (bag1 ∩ bag2) ---");
    // The intersection contains only common elements, with the minimum of their occurrences.
    SortedLinkedBag<Integer> intersectionResult = SortedLinkedBag.copyOf(bag1);
    intersectionResult.intersection(bag2); // Modifies intersectionResult in-place
    System.out.println("Result: " + intersectionResult);
    System.out.println();


    System.out.println("--- Difference Operation (bag1 - bag2) ---");
    // The difference contains elements from bag1, with occurrences reduced by their count in bag2.
    SortedLinkedBag<Integer> differenceResult = SortedLinkedBag.copyOf(bag1);
    differenceResult.difference(bag2); // Modifies differenceResult in-place
    System.out.println("Result: " + differenceResult);
  }
}