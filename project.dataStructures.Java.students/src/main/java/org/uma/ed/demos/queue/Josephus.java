package org.uma.ed.demos.queue;

import org.uma.ed.datastructures.queue.ArrayQueue;
import org.uma.ed.datastructures.queue.Queue;

/**
 * A simulation of the classic Flavius Josephus problem using a {@link Queue}.
 * <p>
 * In this problem, N prisoners are standing in a circle, and every M-th prisoner is
 * eliminated until only one remains. This program determines the sequence of eliminations
 * and identifies the final survivor.
 * <p>
 * The circular arrangement and the process of "skipping" prisoners are naturally
 * modeled by dequeueing an element from the front of a queue and re-enqueueing it
 * at the rear.
 *
 * @author Pablo López, Data Structures, Grado en Informática. UMA.
 */
public class Josephus {

  public static void main(String[] args) {
    // --- Input Validation ---
    if (args.length < 2) {
      System.err.println("Usage: java Josephus <num_prisoners> <prisoners_to_skip>");
      System.err.println("Example: java Josephus 10 2");
      return;
    }

    int numPrisoners;
    int prisonersToSkip;
    try {
      numPrisoners = Integer.parseInt(args[0]);
      prisonersToSkip = Integer.parseInt(args[1]);
      if (numPrisoners <= 0 || prisonersToSkip < 0) {
        throw new NumberFormatException();
      }
    } catch (NumberFormatException e) {
      System.err.println("Error: Arguments must be positive integers (prisoners) and non-negative (skip).");
      return;
    }

    // --- Simulation ---
    System.out.printf("Simulating Josephus problem with %d prisoners (numbered 0 to %d) and skipping %d prisoners...%n%n",
        numPrisoners, numPrisoners - 1, prisonersToSkip);

    // A queue to hold the prisoners still in the circle.
    Queue<Integer> prisoners = ArrayQueue.withCapacity(numPrisoners);
    for (int i = 0; i < numPrisoners; i++) {
      prisoners.enqueue(i); // Prisoners are numbered 0 to N-1
    }

    // A queue to record the order of elimination.
    Queue<Integer> killed = ArrayQueue.withCapacity(numPrisoners - 1);

    while (prisoners.size() > 1) {
      // 1. Skip prisoners by rotating the queue.
      //    We dequeue an element from the front and enqueue it at the back.
      for (int i = 0; i < prisonersToSkip; i++) {
        int prisonerToMove = prisoners.first();
        prisoners.dequeue();
        prisoners.enqueue(prisonerToMove);
      }

      // 2. The prisoner at the front is now the one to be killed.
      int toBeKilled = prisoners.first();
      prisoners.dequeue();
      killed.enqueue(toBeKilled);
    }

    // --- Output Results ---
    System.out.println("Prisoners killed (in order): " + killed);
    if (!prisoners.isEmpty()) {
      System.out.println("The survivor is prisoner: " + prisoners.first());
    } else {
      // This case should not happen if numPrisoners > 0.
      System.out.println("There are no survivors.");
    }
  }
}