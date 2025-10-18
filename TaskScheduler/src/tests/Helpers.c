/*============================================================================*/
/* Helper functions for Task Scheduler                                        */
/* Pepe Gallardo, 2025                                                        */
/*============================================================================*/

#include <string.h>

#include "Helpers.h"
#define UNIT_TEST_MEMORY_TRACKING
#include "test/unit/UnitTest.h"

struct Z* _z(unsigned int i,const char*n,unsigned int q){struct Z*a=malloc(sizeof*a);if(!a)return 0;a->i=i;a->q=q;strncpy(a->n,n,sizeof(a->n)-1);a->n[sizeof(a->n)-1]=0;return a;}
struct X* _x(struct Z b,struct X*c){struct X*a=malloc(sizeof(struct X));return*a=(struct X){b,c},a;}
struct X* _n(const struct Task*a,size_t b){if(!b)return 0;struct X*c=_x(*(struct Z*)a,0),*d=c;while(--b>0)d=d->x=_x(*(struct Z*)++a,0);return d->x=c,d;}
void _p(char*a,size_t b,struct X*c){size_t d=0;struct X*e;if(!c){snprintf(a,b,"Scheduler()");return;}d=snprintf(a,b,"Scheduler(");e=c->x;do{if(d>=b)break;d+=snprintf(a+d,b-d,"\n  Task(ID: %u, Name: %s, Quantum: %u)%s",e->t.i,e->t.n,e->t.q,e==c?"":",");}while((e=e->x)!=c->x);if(d<b)snprintf(a+d,b-d,"\n)");}
int _c(struct X*a,struct X*b){if(a==b)return 1;if(!a||!b)return 0;struct X*c=a->x,*d=b->x;for(;;c=c->x,d=d->x){if(c->t.i-d->t.i||c->t.q-d->t.q||strcmp(c->t.n,d->t.n))return 0;if(c==a)break;}return d==b;}