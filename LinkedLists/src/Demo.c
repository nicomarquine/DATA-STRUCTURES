//
// Pepe Gallardo, Data Structures, UMA.
//

#include <stdio.h>

#include "LinkedList.h"

int runDemo(void) {
  #define SIZE 8
  int elements[SIZE] = {10, 50, 30, 10, 40, 80, 70, 90};

  struct LinkedList* p_list1 = LinkedList_new();
  for (size_t i = 0; i < SIZE; i++) {
    LinkedList_append(p_list1, elements[i]);
  }

  printf("p_list1: ");
  LinkedList_print(p_list1);

  printf("\nget element at index 0 from p_list1: %d", LinkedList_get(p_list1, 0));
  printf("\nget element at index 4 from p_list1: %d", LinkedList_get(p_list1, 4));

  printf("\nset element at index 2 from p_list1 to 100");
  LinkedList_set(p_list1, 2, 100);
  printf("\nlist1: ");
  LinkedList_print(p_list1);

  printf("\nremove element at index 2 from p_list1");
  LinkedList_delete(p_list1, 2);
  printf("\nlist1: ");
  LinkedList_print(p_list1);

  printf("\nremove element at index 0 from p_list1");
  LinkedList_delete(p_list1, 0);
  printf("\nlist1: ");
  LinkedList_print(p_list1);

  printf("\ninsert 200 at index 2 in p_list1");
  LinkedList_insert(p_list1, 2, 200);
  printf("\nlist1: ");
  LinkedList_print(p_list1);

  printf("\nprepend 300 to p_list1");
  LinkedList_prepend(p_list1, 300);
  printf("\nlist1: ");
  LinkedList_print(p_list1);

  printf("\nappend 400 to p_list1");
  LinkedList_append(p_list1, 400);
  printf("\nlist1: ");
  LinkedList_print(p_list1);

  printf("\ncopy p_list1 to p_list2");
  struct LinkedList* p_list2 = LinkedList_copyOf(p_list1);
  printf("\nlist2: ");
  LinkedList_print(p_list2);

  printf("\nremove element at index 1 from p_list2");
  LinkedList_delete(p_list2, 1);
  printf("\nlist1: ");
  LinkedList_print(p_list1);
  printf("\nlist2: ");
  LinkedList_print(p_list2);

  printf("\nclear p_list1");
  LinkedList_clear(p_list1);
  printf("\nlist1: ");
  LinkedList_print(p_list1);
  printf("\nlist2: ");
  LinkedList_print(p_list2);
  printf("\n");

  LinkedList_free(&p_list1);
  LinkedList_free(&p_list2);
  printf("done!\n");

  return 0;
}
