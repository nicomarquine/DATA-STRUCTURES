package org.uma.ed.demos.list;

import org.uma.ed.datastructures.list.ArrayList;
import org.uma.ed.datastructures.list.LinkedList;
import org.uma.ed.datastructures.list.List;

/**
 * A demonstration class for various {@link List} implementations.
 * <p>
 * This demo highlights a core concept of interface-based programming: different
 * implementations of the same interface can be treated as equivalent if their
 * logical state is the same.
 * <p>
 * It illustrates:
 * <ul>
 *     <li>Building an {@code ArrayList} and a {@code LinkedList} with the exact same sequence of operations.</li>
 *     <li>Using factory methods like {@code of()} for convenient list creation.</li>
 *     <li>The behavior of the {@code equals()} method, which confirms that two lists are equal
 *         if they contain the same elements in the same order, regardless of their
 *         underlying implementation class (e.g., array vs. linked nodes).</li>
 * </ul>
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class ListDemo {
  public static void main(String[] args) {

    System.out.println("--- Demo 1: Building an ArrayList ---");
    List<Integer> arrayList = ArrayList.empty();
    arrayList.append(1);
    arrayList.append(2);
    arrayList.prepend(3);
    arrayList.insert(1, 4);
    System.out.println("ArrayList state: " + arrayList);
    System.out.println();


    System.out.println("--- Demo 2: Building a LinkedList with the same operations ---");
    List<Integer> linkedList = LinkedList.empty();
    linkedList.append(1);
    linkedList.append(2);
    linkedList.prepend(3);
    linkedList.insert(1, 4);
    System.out.println("LinkedList state: " + linkedList);
    System.out.println();


    System.out.println("--- Demo 3: Creating a list with a factory method ---");
    List<Integer> listFromFactory = LinkedList.of(3, 4, 1, 2);
    System.out.println("List from `of(3, 4, 1, 2)`: " + listFromFactory);
    System.out.println();


    System.out.println("--- Equality Checks: Comparing lists with different implementations ---");
    System.out.println("Although their internal structures are different (array vs nodes),");
    System.out.println("the lists are considered equal because their content and order are identical.");
    System.out.println();
    System.out.printf("Is arrayList equal to linkedList? -> %b%n", arrayList.equals(linkedList));
    System.out.printf("Is arrayList equal to listFromFactory? -> %b%n", arrayList.equals(listFromFactory));
    System.out.printf("Is linkedList equal to listFromFactory? -> %b%n", linkedList.equals(listFromFactory));
  }
}