//
// Pepe Gallardo, Data Structures, UMA.
//

#include <stdio.h>

#include "ArrayList.h"

int runDemo(void) {
  #define SIZE 8
  int elements[SIZE] = {10, 50, 30, 10, 40, 80, 70, 90};

  struct ArrayList* p_list1 = ArrayList_new(2);
  for (size_t i = 0; i < SIZE; i++) {
    ArrayList_append(p_list1, elements[i]);
  }

  printf("p_list1: ");
  ArrayList_print(p_list1);

  printf("\nget element at index 0 from p_list1: %d", ArrayList_get(p_list1, 0));
  printf("\nget element at index 4 from p_list1: %d", ArrayList_get(p_list1, 4));

  printf("\nset element at index 2 from p_list1 to 100");
  ArrayList_set(p_list1, 2, 100);
  printf("\np_list1: ");
  ArrayList_print(p_list1);

  printf("\nremove element at index 2 from p_list1");
  ArrayList_delete(p_list1, 2);
  printf("\np_list1: ");
  ArrayList_print(p_list1);
  printf("\nremove element at index 0 from p_list1");
  ArrayList_delete(p_list1, 0);
  printf("\np_list1: ");
  ArrayList_print(p_list1);

  printf("\ninsert 200 at index 2 in p_list1");
  ArrayList_insert(p_list1, 2, 200);
  printf("\np_list1: ");
  ArrayList_print(p_list1);

  printf("\nprepend 300 to p_list1");
  ArrayList_prepend(p_list1, 300);
  printf("\np_list1: ");
  ArrayList_print(p_list1);

  printf("\nappend 400 to p_list1");
  ArrayList_append(p_list1, 400);
  printf("\np_list1: ");
  ArrayList_print(p_list1);

  printf("\ncopy p_list1 to p_list2");
  struct ArrayList* p_list2 = ArrayList_copyOf(p_list1);
  printf("\np_list2: ");
  ArrayList_print(p_list2);

  printf("\nremove element at index 1 from p_list2");
  ArrayList_delete(p_list2, 1);
  printf("\np_list1: ");
  ArrayList_print(p_list1);
  printf("\np_list2: ");
  ArrayList_print(p_list2);

  printf("\nclear p_list1");
  ArrayList_clear(p_list1);
  printf("\np_list1: ");
  ArrayList_print(p_list1);
  printf("\np_list2: ");
  ArrayList_print(p_list2);
  printf("\n");

  ArrayList_free(&p_list1);
  ArrayList_free(&p_list2);
  
  return 0;
}
