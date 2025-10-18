//
// Pepe Gallardo, Data Structures, UMA.
//

#ifndef SCHEDULER_H
#define SCHEDULER_H

#include <stddef.h>

#include "Task.h"


struct Node {
  struct Task task;    // Task to be executed
  struct Node* p_next; // Pointer to next node in circular list
};

struct Node* Scheduler_new(); // Initialize an empty scheduler
size_t Scheduler_size(const struct Node* p_last); // Return the number of tasks in the scheduler
void Scheduler_clear(struct Node** p_p_last); // Remove all tasks from the scheduler making it empty
struct Task* Scheduler_first(const struct Node* p_last); // Return a heap allocated copy of the first task in the scheduler
void Scheduler_enqueue(struct Node** p_p_last, const struct Task* p_task); // Add a a copy of the task at the end of the queue
void Scheduler_dequeue(struct Node** p_p_last); // Remove the first task from the scheduler
void Scheduler_print(const struct Node* p_last); // Print all tasks in the scheduler
#endif
