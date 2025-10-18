//
// Pepe Gallardo, Data Structures, UMA.
//

#include <assert.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#include "Task.h"
#include "test/unit/UnitTest.h"

struct Task *Task_new(unsigned int id, const char name[], unsigned int quantum)
{
  assert(name != NULL && "Task_new : name is null");
  assert((strlen(name)) <= 20 && "Task_new; name is too long");
  assert(quantum > 0 && "Task_new: quantum is not greater than 0");

  struct Task* p_task = malloc(sizeof(struct Task));
  assert(p_task != NULL && "Task_new: out of memory");

  p_task -> id = id;
  strcpy(p_task ->name,name);
  p_task -> quantum = quantum;

  return p_task;
}

void Task_free(struct Task **p_p_task)
{
  assert(p_p_task != NULL && "p_p_task is null");
  assert(*p_p_task != NULL && "Cannot be a null pointer");
  free(*p_p_task);
  *p_p_task = NULL;
}

struct Task *Task_copyOf(const struct Task *p_task)
{
  assert(p_task != NULL && "Task_new: out of memory");
  return(Task_new(p_task ->  id,p_task ->  name,p_task ->  quantum));
}

void Task_print(const struct Task *p_task)
{
  // already implemented
  assert(p_task != NULL && "Task_print: NULL p_task");
  printf("Task(ID: %u, Name: %s, Quantum: %u)", p_task->id, p_task->name, p_task->quantum);
}
