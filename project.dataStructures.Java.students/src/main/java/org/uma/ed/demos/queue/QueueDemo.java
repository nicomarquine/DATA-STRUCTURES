package org.uma.ed.demos.queue;

import org.uma.ed.datastructures.queue.ArrayQueue;
import org.uma.ed.datastructures.queue.LinkedQueue;
import org.uma.ed.datastructures.queue.Queue;
import org.uma.ed.datastructures.range.Range;

/**
 * A demonstration class for various {@link Queue} implementations.
 * <p>
 * This demo illustrates:
 * <ul>
 *     <li>Basic queue operations (enqueue, dequeue, first) on different implementations.</li>
 *     <li>Creation of queues using factory methods like {@code of()} and {@code from()}.</li>
 *     <li>The behavior of the {@code equals()} method, showing that two queues are equal if they
 *         contain the same elements in the same FIFO order, regardless of their underlying implementation.</li>
 * </ul>
 *
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 */
public class QueueDemo {

  public static void main(String[] args) {
    System.out.println("--- Demo 1: Basic operations with LinkedQueue ---");
    Queue<Integer> q1 = LinkedQueue.empty();
    q1.enqueue(0);
    q1.enqueue(1);
    q1.enqueue(2);
    q1.enqueue(3);
    System.out.println("Queue after enqueuing 0, 1, 2, 3: " + q1);

    int firstElement = q1.first();
    System.out.println("First element is: " + firstElement);
    q1.dequeue();
    System.out.println("Queue after one dequeue: " + q1);
    System.out.println();


    System.out.println("--- Demo 2: Comparing different implementations ---");
    // q2 is an ArrayQueue with the same logical content as q1 after the dequeue.
    Queue<Integer> q2 = ArrayQueue.empty();
    q2.enqueue(1);
    q2.enqueue(2);
    q2.enqueue(3);
    System.out.println("An ArrayQueue with elements 1, 2, 3 enqueued: " + q2);
    System.out.println();

    // q3 is created using the `of` factory method.
    Queue<Integer> q3 = ArrayQueue.of(1, 2, 3);
    System.out.println("An ArrayQueue created with `of(1, 2, 3)`: " + q3);
    System.out.println();

    System.out.println("--- Equality Checks ---");
    System.out.println("Is q1 (LinkedQueue) equal to q2 (ArrayQueue)? -> " + q1.equals(q2));
    System.out.println("Is q1 (LinkedQueue) equal to q3 (ArrayQueue)? -> " + q1.equals(q3));
    System.out.println("Is q2 (ArrayQueue) equal to q3 (ArrayQueue)? -> " + q2.equals(q3));
    System.out.println();


    System.out.println("--- Demo 3: Creating a queue from a Range iterable ---");
    // Elements from the range [0, 2, 4, 6, 8, 10] will be enqueued.
    Queue<Integer> q4 = ArrayQueue.from(Range.inclusive(0, 10, 2));
    System.out.println("Queue created from a Range(0 to 10, step 2): " + q4);
  }
}