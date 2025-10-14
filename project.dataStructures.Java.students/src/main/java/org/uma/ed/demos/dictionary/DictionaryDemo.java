package org.uma.ed.demos.dictionary;

import org.uma.ed.datastructures.dictionary.AVLDictionary;
import org.uma.ed.datastructures.dictionary.Dictionary;
import org.uma.ed.datastructures.dictionary.Dictionary.Entry;
import org.uma.ed.datastructures.dictionary.HashDictionary;
import org.uma.ed.datastructures.dictionary.SortedDictionary;
import org.uma.ed.datastructures.dictionary.SortedLinkedDictionary;
import org.uma.ed.datastructures.list.JDKArrayList;
import org.uma.ed.datastructures.list.List;

/**
 * A demonstration class for various {@link Dictionary} implementations.
 * <p>
 * This demo highlights the concept of equality between different dictionary implementations.
 * It shows that two dictionaries are considered equal if they contain the exact same set of
 * key-value mappings, regardless of their internal data structure (e.g., balanced tree,
 * linked list, or hash table) or the order of their elements.
 * <p>
 * This illustrates a core principle of interface-based design: objects are judged
 * by their logical content, not their concrete class.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class DictionaryDemo {

  public static void main(String[] args) {

    // Define a common set of entries, including duplicates.
    // The factory methods will handle the duplicates, keeping the last inserted value for each key.
    List<Entry<Integer, String>> entries = JDKArrayList.of(
        Entry.of(20, "twenty"), Entry.of(5, "five"), Entry.of(3, "three"), Entry.of(2, "two"),
        Entry.of(1, "one"), Entry.of(2, "two_updated"), Entry.of(0, "zero"),
        Entry.of(4, "four"), Entry.of(3, "three_updated"), Entry.of(5, "five_updated"),
        Entry.of(100, "one_hundred"), Entry.of(50, "fifty")
    );

    System.out.println("--- Creating dictionaries with different implementations ---");

    // 1. AVLDictionary: A sorted dictionary based on a balanced binary search tree.
    SortedDictionary<Integer, String> avlDictionary = AVLDictionary.from(entries);
    System.out.println("AVLDictionary (sorted by key):");
    System.out.println("-> " + avlDictionary);
    System.out.println("Value for key 3: " + avlDictionary.valueOf(3));
    System.out.println();

    // 2. SortedLinkedDictionary: A sorted dictionary based on a linked list.
    SortedDictionary<Integer, String> sortedLinkedDictionary = SortedLinkedDictionary.from(entries);
    System.out.println("SortedLinkedDictionary (sorted by key):");
    System.out.println("-> " + sortedLinkedDictionary);
    System.out.println("Value for key 3: " + sortedLinkedDictionary.valueOf(3));
    System.out.println();

    // 3. HashDictionary: An unordered dictionary based on a hash table.
    Dictionary<Integer, String> hashDictionary = HashDictionary.from(entries);
    System.out.println("HashDictionary (unordered):");
    System.out.println("-> " + hashDictionary);
    System.out.println("Value for key 3: " + hashDictionary.valueOf(3));
    System.out.println();


    System.out.println("--- Equality Checks ---");
    System.out.println("Despite different implementations and internal ordering, all three dictionaries");
    System.out.println("contain the same set of key-value mappings, so they should be equal.");
    System.out.println();

    System.out.printf("Is AVLDictionary equal to SortedLinkedDictionary? -> %b%n",
        avlDictionary.equals(sortedLinkedDictionary));

    System.out.printf("Is AVLDictionary equal to HashDictionary? -> %b%n",
        avlDictionary.equals(hashDictionary));

    System.out.printf("Is SortedLinkedDictionary equal to HashDictionary? -> %b%n",
        sortedLinkedDictionary.equals(hashDictionary));
  }
}