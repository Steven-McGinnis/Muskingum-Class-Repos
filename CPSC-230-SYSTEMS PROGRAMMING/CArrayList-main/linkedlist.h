#ifndef LIST_H
#define LIST_H

#include <stdbool.h>

typedef struct list_t list_t;

list_t *create_list ();
void free_list (list_t *list);
int list_size (list_t *list);
void list_append (list_t *list, int item);
bool list_remove (list_t *list, int index);
int list_find (list_t *list, int item);
int list_get (list_t *list, int index);
bool list_insert (list_t *list, int item, int index);

#endif

