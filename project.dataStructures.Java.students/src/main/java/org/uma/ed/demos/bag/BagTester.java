package org.uma.ed.demos.bag;

import org.uma.ed.datastructures.bag.*;

/**
 * A simple test driver for the {@link Bag} ADT to visually inspect its behavior.
 * <p>
 * This program performs a sequence of operations on a {@code Bag} instance to test its
 * core functionalities: insertion, deletion, and counting occurrences. The state of the bag
 * and the count of vowels are printed after each major step to allow for manual verification.
 * <p>
 * To test a different {@code Bag} implementation, simply change the instantiation line
 * in the {@code main} method.
 *
 * @author Pablo López, Data Structures, Grado en Informática. UMA.
 */
public class BagTester {

  public static void main(String[] args) {

    // --- Configuration: Choose the Bag implementation and text to test ---
    Bag<Character> bag = SortedLinkedBag.empty();
    System.out.println("Testing implementation: " + bag.getClass().getSimpleName());
    // Other options to test:
    // Bag<Character> bag = SortedLinkedBag.empty();
    // Bag<Character> bag = AVLBag.empty();
    // Bag<Character> bag = BSTBag.empty();
    // Bag<Character> bag = HashBag.empty();

    String cervantes = "En un lugar de la Mancha, de cuyo nombre no quiero acordarme...";
    String dickens = "It was the best of times, it was the worst of times...";

    String text = cervantes;
    System.out.println("Using text snippet: \"" + text + "\"\n");

    System.out.println("--- Step 0: Initial State ---");
    System.out.println("Is bag empty? " + bag.isEmpty());
    System.out.println(bag);
    printVowelOccurrences(bag);
    System.out.println("--------------------------------\n");

    // Step 1: Feed the bag with the text characters
    System.out.println("--- Step 1: Inserting all characters from the text ---");
    for (int i = 0; i < text.length(); i++) {
      bag.insert(text.charAt(i));
    }
    System.out.println(bag);
    printVowelOccurrences(bag);
    System.out.println("--------------------------------\n");

    // Step 2: Delete some specific characters
    System.out.println("--- Step 2: Deleting single occurrences of some characters ---");
    String toBeDeleted = "e Muy aaa cuccu monk java ghci duEee";
    System.out.println("Characters to delete: \"" + toBeDeleted + "\"");
    for (int i = 0; i < toBeDeleted.length(); i++) {
      bag.delete(toBeDeleted.charAt(i));
    }
    System.out.println(bag);
    printVowelOccurrences(bag);
    System.out.println("--------------------------------\n");

    // Step 3: Remove all non-letter characters
    System.out.println("--- Step 3: Removing all occurrences of non-letter characters ---");
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      if (!Character.isLetter(c)) {
        removeAll(bag, c);
      }
    }
    System.out.println(bag);
    printVowelOccurrences(bag);
    System.out.println("--------------------------------\n");

    // Step 4: Remove all occurrences of some specific letters
    System.out.println("--- Step 4: Removing all occurrences of letters 'z', 'y', 'w', 'v', 'u' ---");
    String toBeNuked = "zywvu";
    for (int i = 0; i < toBeNuked.length(); i++) {
      removeAll(bag, toBeNuked.charAt(i));
    }
    System.out.println(bag);
    printVowelOccurrences(bag);
    System.out.println("--------------------------------\n");

    // Step 5: Remove all remaining vowels
    System.out.println("--- Step 5: Removing all remaining vowels (a, e, i, o, u) ---");
    removeVowels(bag);
    System.out.println(bag);
    printVowelOccurrences(bag);
    System.out.println("--------------------------------\n");

    System.out.println("--- Final State ---");
    System.out.println("Is bag empty? " + bag.isEmpty());
  }

  /**
   * Prints the number of occurrences of each vowel (a, e, i, o, u) in the given bag.
   * @param bag The bag to inspect.
   */
  public static void printVowelOccurrences(Bag<Character> bag) {
    final String VOWELS = "aeiou";
    System.out.print("Vowel counts: ");
    for (int i = 0; i < VOWELS.length(); i++) {
      char vowel = VOWELS.charAt(i);
      System.out.printf("'%c': %d  ", vowel, bag.occurrences(vowel));
    }
    System.out.println();
  }

  /**
   * Removes all occurrences of a specified element from a bag.
   * @param bag The bag to modify.
   * @param element The element to remove completely.
   */
  public static <T> void removeAll(Bag<T> bag, T element) {
    while (bag.contains(element)) {
      bag.delete(element);
    }
  }

  /**
   * Removes all vowels (both lowercase and uppercase) from a bag of characters.
   * @param bag The bag to modify.
   */
  public static void removeVowels(Bag<Character> bag) {
    final String VOWELS = "aeiou";
    for (int i = 0; i < VOWELS.length(); i++) {
      char vowel = VOWELS.charAt(i);
      removeAll(bag, vowel);
      removeAll(bag, Character.toUpperCase(vowel));
    }
  }
}