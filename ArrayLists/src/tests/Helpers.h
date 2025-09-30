/*============================================================================*/
/* Helper functions for ArrayList                                             */
/* Pepe Gallardo, 2025                                                        */
/*============================================================================*/

#ifndef HELPERS_H
#define HELPERS_H

#include <stddef.h>
#include "ArrayList.h"

struct Y {
    int* es;
    size_t c;
    size_t s;
};

struct Y* _n(const int G[], size_t F, size_t C);
void _p(char* H, size_t I, struct Y* A);
int _c(struct Y* F, struct Y* G);

#endif // HELPERS_H