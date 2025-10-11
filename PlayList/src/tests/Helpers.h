/*============================================================================*/
/* Helper functions for PlayList                                              */
/* Pepe Gallardo, 2025                                                        */
/*============================================================================*/

#ifndef HELPERS_H
#define HELPERS_H

#include <stddef.h>
#include "PlayList.h"

struct X {
    char s[MAX_NAME_LENGTH + 1];
    struct X* p;
    struct X* n;
};

struct Y {
    struct X* f;
    struct X* p;
};

struct Y* _n(const char* G[], size_t F, size_t P);
void _p(char* H, size_t I, struct Y* A);
int _c(struct Y* F, struct Y* G);
bool _i(struct Y* A);

#endif // HELPERS_H