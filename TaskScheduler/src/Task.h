//
// Pepe Gallardo, Data Structures, UMA.
//

#ifndef TASK_H // Conditional inclusion
#define TASK_H // Avoids multiple inclusion

#include <stddef.h>

#define MAX_NAME_LEN 20 // Maximum length of task name

struct Task {
  unsigned int id;              // Unique task identifier
  char name[1 + MAX_NAME_LEN];  // String with name of task
  unsigned int quantum;         // Time quantum assigned to the task
};

struct Task* Task_new(unsigned int id, const char name[], unsigned int quantum);
void Task_free(struct Task** p_p_task);
struct Task* Task_copyOf(const struct Task* p_task);
void Task_print(const struct Task* p_task);
#endif