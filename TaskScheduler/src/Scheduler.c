//
// Pepe Gallardo, Data Structures, UMA.
//

#include <assert.h>
#include <stdlib.h>
#include <stdio.h>

#include "Scheduler.h"
#include "test/unit/UnitTest.h"

struct Node *Scheduler_new()
{
  return NULL;
}

size_t Scheduler_size(const struct Node *p_last)
{
  if (p_last == NULL) {
        return 0;
    }
  struct Node* p_current = p_last -> p_next;
  size_t count = 0;
  do{
    count++;
    p_current = p_current -> p_next;
  }while(p_current != p_last -> p_next);
  return count;
}

void Scheduler_clear(struct Node **p_p_last)
{
  assert(p_p_last != NULL && "Scheduler_clear: p_p_last can not be NULL");
  if (p_p_last == NULL || *p_p_last == NULL) {
        return; // lista vacía o puntero nulo
    }
  struct Node* p_last = *p_p_last;
  struct Node* p_current = p_last -> p_next;
  struct Node* p_copy = NULL;
  while (p_current != p_last) {
        p_copy = p_current->p_next;
        free(p_current);
        p_current = p_copy;
    }

    // Liberamos el último nodo
    free(p_last);

    // Dejamos el puntero externo en NULL
    *p_p_last = NULL;
}

struct Task *Scheduler_first(const struct Node *p_last)
{
  assert(p_last != NULL && "Scheduler_first: task can not be NULL");
  struct Node* p_first = p_last -> p_next;

  return Task_copyOf(&p_first -> task);
}

void Scheduler_enqueue(struct Node **p_p_last, const struct Task *p_task)
{
    assert(p_p_last != NULL && "Scheduler_enqueue: p_p_last can not be NULL");
    assert(p_task != NULL && "Scheduler_enqueue: p_task can not be NULL");

    struct Node* p_node = Scheduler_new();

    // Si Scheduler_new() no asigna memoria, la creamos nosotros
    if (p_node == NULL) {
        p_node = malloc(sizeof(struct Node));
        assert(p_node != NULL && "Scheduler_enqueue: out of memory");
        p_node->p_next = NULL;
    }

    // Copiamos el contenido de la tarea al nuevo nodo
    p_node->task = *p_task;

    // Caso 1: la lista está vacía
    if (*p_p_last == NULL) {
        p_node->p_next = p_node;   // circularidad
        *p_p_last = p_node;
    } else {
        // Caso 2: la lista ya tiene elementos
        struct Node* p_last = *p_p_last;
        p_node->p_next = p_last->p_next; // nuevo apunta al primero
        p_last->p_next = p_node;         // último apunta al nuevo
        *p_p_last = p_node;              // el nuevo pasa a ser el último
    }
}

void Scheduler_dequeue(struct Node **p_p_last)
{
  assert(*p_p_last != NULL && "Scheduler_enqueue: p_p_last can not be NULL");

  struct Node* p_last = *p_p_last;

  if(p_last -> p_next == p_last){
    free(p_last);
    *p_p_last = NULL;
  }else{
    struct Node* p_first = p_last -> p_next;
    p_last -> p_next = p_first -> p_next;
    free(p_first);
    p_first = NULL;
  }
}

void Scheduler_print(const struct Node *p_last)
{
  // already implemented
  printf("Scheduler(");
  if (p_last != NULL)
  {
    struct Node *p_first = p_last->p_next;
    struct Node *p_current = p_first;
    do
    {
      printf("\n  ");
      Task_print(&(p_current->task));
      p_current = p_current->p_next;
      printf(p_current != p_first ? "," : "\n");
    } while (p_current != p_first);
  }
  printf(")");
}
