//
// Pepe Gallardo, Data Structures, UMA.
//

#include <stdio.h>

#include "Scheduler.h"

int runDemo(void) {
  struct Node* p_last = Scheduler_new();

  // Enqueue a heap allocated task
  struct Task* p_task1 = Task_new(1, "Task1", 2);
  Scheduler_enqueue(&p_last, p_task1);
  Task_free(&p_task1);
  
  // Enqueue another heap allocated task
  struct Task* p_task2 = Task_new(2, "Task2", 1);
  Scheduler_enqueue(&p_last, p_task2);
  Task_free(&p_task2);
  
  // Enqueue a stack allocated task
  struct Task task3 = {.id=3, .name="Task3", .quantum=3};
  Scheduler_enqueue(&p_last, &task3);

  printf("Initial Scheduler:\n");
  Scheduler_print(p_last);

  printf("\n\nFirst Task:\n");
  struct Task* p_task = Scheduler_first(p_last);
  Task_print(p_task);
  Task_free(&p_task);

  printf("\n\nAfter Dequeue:\n");
  Scheduler_dequeue(&p_last);
  Scheduler_print(p_last);

  struct Task task4 = {.id=4, .name="Task4", .quantum=4};
  Scheduler_enqueue(&p_last, &task4);
  printf("\n\nAfter Enqueue:\n");
  Scheduler_print(p_last);

  Scheduler_clear(&p_last);
  printf("\n\nAfter Clearing Scheduler:\n");
  Scheduler_print(p_last);
  printf("\n");

  return 0;
}