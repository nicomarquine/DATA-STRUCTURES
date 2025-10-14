package org.uma.ed.demos.set;

import org.uma.ed.datastructures.range.Range;
import org.uma.ed.datastructures.set.AVLSet;
import org.uma.ed.datastructures.set.SortedSet;

import java.util.Random;

/**
 * A demonstration class for the {@link AVLSet} implementation.
 * <p>
 * This class illustrates the basic functionality of a set implemented with an AVL tree,
 * including:
 * <ul>
 *     <li>Creating a set with duplicate elements and observing that only unique elements are stored.</li>
 *     <li>Basic operations: {@code insert}, {@code delete}, {@code contains}, {@code minimum}, {@code maximum}.</li>
 *     <li>Creating a set from an {@code Iterable} like {@code Range}.</li>
 *     <li>Populating a set with random data.</li>
 * </ul>
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class AVLSetDemo {

  public static void main(String[] args) {

    System.out.println("--- Demo 1: Basic AVLSet operations ---");
    // The `of` factory method handles duplicates automatically.
    SortedSet<Integer> set1 = AVLSet.of(5, 3, 2, 1, 2, 0, 4, 3, 5, 1, 8);
    System.out.println("Set 1 created from `of(5, 3, 2, 1, 2, 0, 4, 3, 5, 1, 8)`:");
    System.out.println("-> " + set1);
    System.out.println("Size: " + set1.size());
    System.out.println("Contains 3? " + set1.contains(3));
    System.out.println("Contains 6? " + set1.contains(6));
    System.out.println();

    System.out.println("Deleting elements 3 and 5...");
    set1.delete(3);
    set1.delete(5);
    System.out.println("Set 1 after deletions: " + set1);
    System.out.println("Minimum element: " + set1.minimum());
    System.out.println("Maximum element: " + set1.maximum());
    System.out.println();

    System.out.println("Iterating through the final state of Set 1:");
    for (Integer x : set1) {
      System.out.print(x + " ");
    }
    System.out.println("\n");

    System.out.println("Clearing the set...");
    set1.clear();
    System.out.println("Set 1 after clearing: " + set1);
    System.out.println();


    System.out.println("--- Demo 2: Creating an AVLSet from a Range ---");
    SortedSet<Integer> set2 = AVLSet.from(Range.inclusive(0, 5));
    System.out.println("Set 2 created from Range.inclusive(0, 5):");
    System.out.println("-> " + set2);
    System.out.println();


    System.out.println("--- Demo 3: Creating an AVLSet with random elements ---");
    SortedSet<Integer> set3 = AVLSet.empty();
    Random rnd = new Random();
    for (int i = 0; i < 20; i++) {
      set3.insert(rnd.nextInt(100));
    }
    System.out.println("Set 3 with 20 random integers between 0 and 99:");
    System.out.println("-> " + set3);
  }
}