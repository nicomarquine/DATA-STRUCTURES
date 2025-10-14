package org.uma.ed.demos.set;

import org.uma.ed.datastructures.set.AVLSet;
import org.uma.ed.datastructures.set.SortedSet;
import java.util.Comparator;

/**
 * A simple record to represent a person with an ID, name, and age.
 * <p>
 * This record demonstrates how to implement {@code equals}, {@code hashCode}, and
 * {@code Comparable} based on a unique business key (in this case, the {@code id}).
 * This ensures that two {@code Person} objects are considered "equal" if and only if
 * they have the same ID, regardless of their other attributes like name or age.
 *
 * @param id   The unique identifier for the person. This is the key for comparisons.
 * @param name The name of the person.
 * @param age  The age of the person.
 */
record Person(int id, String name, int age) implements Comparable<Person> {

  /**
   * Factory method to create a new {@code Person} instance.
   *
   * @param id   the unique identifier.
   * @param name the person's name.
   * @param age  the person's age.
   * @return a new {@code Person} instance.
   */
  public static Person of(int id, String name, int age) {
    return new Person(id, name, age);
  }

  /**
   * A comparator that sorts persons by age in ascending order.
   */
  public static final Comparator<Person> BY_AGE_COMPARATOR = Comparator.comparingInt(Person::age);

  /**
   * A comparator that sorts persons by name in lexicographical order.
   */
  public static final Comparator<Person> BY_NAME_COMPARATOR = Comparator.comparing(Person::name);

  /**
   * Defines the natural order for {@code Person} objects, which is based solely on their ID.
   * <p>
   * This is consistent with the {@code equals} method.
   *
   * @param other the person to be compared.
   * @return a negative integer, zero, or a positive integer as this person's ID
   *         is less than, equal to, or greater than the other person's ID.
   */
  @Override
  public int compareTo(Person other) {
    return Integer.compare(this.id, other.id);
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   * <p>
   * Two {@code Person} objects are considered equal if they have the same ID.
   *
   * @param object the reference object with which to compare.
   * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
   */
  @Override
  public boolean equals(Object object) {
    return this == object || (object instanceof Person other && this.id == other.id);
  }

  /**
   * Returns a hash code value for the person.
   * <p>
   * The hash code is based solely on the ID to be consistent with {@code equals}.
   *
   * @return a hash code value for this object.
   */
  @Override
  public int hashCode() {
    return Integer.hashCode(id);
  }
}

/**
 * A demonstration of how to use a {@link SortedSet} to store and "update" custom objects.
 * <p>
 * This demo illustrates a key feature of the library's {@code Set.insert} method: when
 * an element is inserted that is "equal" to an element already in the set (according to
 * the set's comparator or the element's natural order), the old element is replaced
 * by the new one. This allows for updating an element's attributes (like a person's age)
 * while preserving its identity within the set.
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class PersonAVLSetDemo {
  public static void main(String[] args) {
    Person p1 = Person.of(10, "Alice", 20);
    Person p2 = Person.of(12, "Bob", 30);
    Person p3 = Person.of(30, "Charlie", 40);
    Person p4 = Person.of(24, "David", 50);

    // A SortedSet using the natural order of Person (by ID).
    SortedSet<Person> setById = AVLSet.of(p1, p2, p3, p4);

    System.out.println("--- Set sorted by ID (natural order) ---");
    System.out.println("Initial set: " + setById);
    System.out.println("Maximum element (by ID): " + setById.maximum());
    System.out.println();

    System.out.println("--- Updating an element in the set ---");
    System.out.println("Original person with ID 30: " + p3);
    // Create a new Person object with the same ID but updated age.
    Person p5_updated = Person.of(30, "Charlie", 41);
    System.out.println("New person object with same ID: " + p5_updated);

    // Because p5_updated.compareTo(p3) == 0, the insert operation will replace p3 with p5_updated.
    setById.insert(p5_updated);
    System.out.println("Set after inserting the updated person: " + setById);
    System.out.println();

    System.out.println("--- Set sorted by a custom comparator (by age) ---");
    SortedSet<Person> setByAge = new AVLSet<>(Person.BY_AGE_COMPARATOR);
    setByAge.insert(p1);
    setByAge.insert(p2);
    setByAge.insert(p3);
    setByAge.insert(p4);
    System.out.println("Set sorted by age: " + setByAge);
    System.out.println("Maximum element (by age): " + setByAge.maximum());
  }
}