package org.uma.ed.demos.priorityQueue;

import org.uma.ed.datastructures.priorityqueue.PriorityQueue;
import org.uma.ed.datastructures.priorityqueue.SortedArrayPriorityQueue;

import java.util.Comparator;

class Person{
    public int ID;
    private String name;
    private int age;

    public Person(int ID, String name, int age) {
        this.ID = ID;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person(" + ID + ", " + name + ", " + age + ")";
    }
}

public class SortedArrayPriorityQueueDemo {


    public static void main(String[] args) {
        Comparator<Person> comparator1 = Comparator.comparingInt(Person person -> person.ID);
        Comparator<Person> comparator2 = Comparator.comparingInt(Person person -> person.age);

        PriorityQueue<Person> pq1 = new SortedArrayPriorityQueue<Person>(comparator1, 32);

        Person p1 = new Person(1, "Jhon", 32);
        Person p2 = new Person(7, "Peter", 34);
        Person p3 = new Person(2, "Jhon", 32);

        pq1.enqueue(p1);
        pq1.enqueue(p2);
        pq1.enqueue(p3);

        System.out.println(pq1);
    }
}
