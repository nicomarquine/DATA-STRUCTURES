/*============================================================================*/
/* Helper functions for LinkedList                                            */
/* Pepe Gallardo, 2025                                                        */
/*============================================================================*/

#ifndef HELPERS_H
#define HELPERS_H

#include <stddef.h>
#include "LinkedList.h"

struct X {
    int e;
    struct X* n;
};

struct Y {
    struct X* f;
    struct X* l;
    size_t s;
};

struct Y* _n(const int G[], size_t F);
void _p(char* H, size_t I, struct Y* A);
int _c(struct Y* F, struct Y* G);

#endif // HELPERS_H
