package org.uma.ed.demos.dictionary;

import org.uma.ed.datastructures.dictionary.AVLDictionary;
import org.uma.ed.datastructures.dictionary.Dictionary.Entry;
import org.uma.ed.datastructures.dictionary.SortedDictionary;

/**
 * A simple demonstration class for the {@link AVLDictionary} implementation.
 * <p>
 * This class illustrates the basic functionality of a sorted dictionary implemented
 * with an AVL tree, including:
 * <ul>
 *     <li>Creating a dictionary from a collection of key-value pairs using {@code of()}.</li>
 *     <li>Basic operations: {@code insert}, {@code delete}, {@code isDefinedAt}, {@code valueOf}.</li>
 *     <li>Accessing the minimum and maximum elements based on key order.</li>
 *     <li>Iterating through the dictionary's entries, which are yielded in sorted key order.</li>
 * </ul>
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class AVLDictionaryDemo {

  public static void main(String[] args) {

    System.out.println("--- Demo: Basic operations with AVLDictionary ---");

    // Create a sorted dictionary using the `of` factory method.
    // The entries will be automatically sorted by key (String's natural order).
    SortedDictionary<String, Integer> dictionary = AVLDictionary.of(
        Entry.of("five", 5),
        Entry.of("three", 3),
        Entry.of("two", 2),
        Entry.of("one", 1),
        Entry.of("zero", 0),
        Entry.of("four", 4)
    );

    System.out.println("Initial dictionary (sorted by key): " + dictionary);
    System.out.println("Size: " + dictionary.size());
    System.out.println();

    System.out.println("--- Querying the dictionary ---");
    System.out.println("Is key \"three\" defined? -> " + dictionary.isDefinedAt("three"));
    System.out.println("Is key \"six\" defined?   -> " + dictionary.isDefinedAt("six"));
    System.out.println("Value of key \"three\": " + dictionary.valueOf("three"));
    System.out.println();

    System.out.println("--- Modifying the dictionary ---");
    System.out.println("Deleting mapping for key \"three\"...");
    dictionary.delete("three");
    System.out.println("Dictionary after deletion: " + dictionary);
    System.out.println();

    System.out.println("--- Accessing ordered elements ---");
    System.out.println("Minimum entry (by key): " + dictionary.minimum());
    System.out.println("Maximum entry (by key): " + dictionary.maximum());
    System.out.println();

    System.out.println("--- Iterating through the dictionary entries (in key order) ---");
    for (Entry<String, Integer> entry : dictionary) {
      System.out.println("  -> " + entry);
    }
  }
}