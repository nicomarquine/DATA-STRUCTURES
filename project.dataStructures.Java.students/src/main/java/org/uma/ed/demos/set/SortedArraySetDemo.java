package org.uma.ed.demos.set;

import org.uma.ed.datastructures.set.SortedArraySet;
import org.uma.ed.datastructures.set.SortedSet;

import java.util.Comparator;
import java.util.Random;

/**
 * A demonstration class for the {@link SortedArraySet} implementation.
 * <p>
 * This demo illustrates:
 * <ul>
 *     <li>Creation of a sorted set with the default natural ordering.</li>
 *     <li>Creation of a sorted set using a custom {@code Comparator} (e.g., reverse order).</li>
 *     <li>Use of factory methods like {@code of()} for convenient instantiation.</li>
 *     <li>Set operations like {@code union} and {@code intersection} on sorted sets.</li>
 * </ul>
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class SortedArraySetDemo {

  public static void main(String[] args) {

    System.out.println("--- Demo 1: A SortedArraySet with natural ordering ---");
    SortedSet<Integer> set1 = SortedArraySet.empty();
    Random rnd = new Random();
    for (int i = 0; i < 20; i++) {
      set1.insert(rnd.nextInt(100));
    }
    System.out.println("Set 1 (random ints, natural order): " + set1);
    System.out.println();


    System.out.println("--- Demo 2: A SortedArraySet with a custom (reverse) comparator ---");
    Comparator<Integer> reverseComparator = Comparator.reverseOrder();
    SortedSet<Integer> set2 = SortedArraySet.empty(reverseComparator);
    for (int i = 0; i < 20; i++) {
      set2.insert(rnd.nextInt(50));
    }
    System.out.println("Set 2 (random ints, reverse order): " + set2);
    System.out.println();


    System.out.println("--- Demo 3: Creating sets with factory methods ---");
    // This set will also use the reverse order comparator.
    SortedSet<Integer> set3 = SortedArraySet.of(reverseComparator, 1, 7, 9, 0, 2, 5, 4, 1);
    System.out.println("Set 3 (`of` with reverse order): " + set3);
    System.out.println();

    // This set will use the default natural order comparator.
    SortedSet<Integer> set4 = SortedArraySet.of(1, 7, 9, 0, 2, 5, 4, 1);
    System.out.println("Set 4 (`of` with natural order): " + set4);
    System.out.println();


    System.out.println("--- Demo 4: Set operations (union and intersection) ---");
    // Note: union, intersection, etc., require the sets to have the same comparator instance.
    // We will use set2 and set3, which both use the reverseComparator instance.
    SortedSet<Integer> unionSet = SortedArraySet.union(set2, set3);
    System.out.println("Union of set 2 and set 3: " + unionSet);

    SortedSet<Integer> intersectionSet = SortedArraySet.intersection(set2, set3);
    System.out.println("Intersection of set 2 and set 3: " + intersectionSet);
    System.out.println();


    System.out.println("--- Demo 5: Copying a set and modifying it ---");
    SortedSet<Integer> set5 = SortedArraySet.copyOf(set4);
    System.out.println("Set 5 (a copy of set 4): " + set5);
    set5.delete(7);
    System.out.println("Set 5 after deleting 7: " + set5);
  }
}