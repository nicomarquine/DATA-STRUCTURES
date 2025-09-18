//
// Pepe Gallardo, Data Structures, UMA.
//

#include <stdio.h>

#include "LinkedStructure.h"

int main(void) {
  #define SIZE 8
  int elements[SIZE] = {10, 50, 30, 10, 40, 80, 70, 90};

  struct Node* p_first1 = LinkedStructure_new();
  for (size_t i = 0; i < SIZE; i++) {
    LinkedStructure_append(&p_first1, elements[i]);
  }

  printf("p_first1: ");
  LinkedStructure_print(p_first1);

  printf("\nget element at index 0 from p_first1: %d", LinkedStructure_get(p_first1, 0));
  printf("\nget element at index 4 from p_first1: %d", LinkedStructure_get(p_first1, 4));

  printf("\nset element at index 2 from p_first1 to 100");
  LinkedStructure_set(p_first1, 2, 100);
  printf("\np_first1: ");
  LinkedStructure_print(p_first1);

  printf("\nremove element at index 2 from p_first1");
  LinkedStructure_delete(&p_first1, 2);
  printf("\np_first1: ");
  LinkedStructure_print(p_first1);

  printf("\nremove element at index 0 from p_first1");
  LinkedStructure_delete(&p_first1, 0);
  printf("\np_first1: ");
  LinkedStructure_print(p_first1);

  printf("\ninsert 200 at index 2 in p_first1");
  LinkedStructure_insert(&p_first1, 2, 200);
  printf("\np_first1: ");
  LinkedStructure_print(p_first1);

  printf("\nprepend 300 to p_first1");
  LinkedStructure_prepend(&p_first1, 300);
  printf("\np_first1: ");
  LinkedStructure_print(p_first1);

  printf("\nappend 400 to p_first1");
  LinkedStructure_append(&p_first1, 400);
  printf("\np_first1: ");
  LinkedStructure_print(p_first1);

  printf("\ncopy p_first1 to p_first2");
  struct Node* p_first2 = LinkedStructure_copyOf(p_first1);
  printf("\np_first2: ");
  LinkedStructure_print(p_first2);

  printf("\nremove element at index 1 from p_first2");
  LinkedStructure_delete(&p_first2, 1);
  printf("\np_first1: ");
  LinkedStructure_print(p_first1);
  printf("\np_first2: ");
  LinkedStructure_print(p_first2);

  printf("\nclear p_first1 and p_first2");
  LinkedStructure_clear(&p_first1);
  LinkedStructure_clear(&p_first2);
  printf("\np_first1: ");
  LinkedStructure_print(p_first1);
  printf("\np_first2: ");
  LinkedStructure_print(p_first2);
  printf("\ndone!\n");
}
