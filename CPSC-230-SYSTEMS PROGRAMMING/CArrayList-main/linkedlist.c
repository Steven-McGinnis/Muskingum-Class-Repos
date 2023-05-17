#include <stdio.h>
#include <stdlib.h>
#include "linkedlist.h"

struct list_t
{
    struct node_t *first;
    struct node_t *last;
    int size;
};

struct node_t
{
    struct node_t *next;
    int data;
};

typedef struct node_t node_t;

list_t *create_list ()
{
    list_t *list = malloc (sizeof(list_t));

    list->first = NULL;
    list->last = NULL;
    list->size = 0;

    return list;
}

void free_list (list_t *list)
{
	   node_t * tmp = malloc(sizeof(node_t));

	   while (list->first != NULL)
	    {
	       tmp = list->first;
	       list->first = list->first->next;
	       free(tmp);
	    }
}

int list_size (list_t *list)
{
    int size;
    size = list->size;
	return size;
}

void list_append (list_t *list, int item)
{
	     node_t* new_node = malloc(sizeof(node_t));
	     node_t* last = malloc(sizeof(node_t));
	     last = list->first;


	    new_node->data  = item;

	    new_node->next = NULL;


	    if (list->first == NULL)
	    {
	       list->first = new_node;
	       list->size++;
	       return;
	    }


	    while (last->next != NULL)
	        last = last->next;


	    last->next = new_node;
	    list->size++;
	    return;
}

bool list_insert (list_t *list, int item, int index)
{
	if(index > (list->size +1)){
		return false;
	}

	node_t *newNode = malloc(sizeof(node_t));
	newNode->data = item;
	node_t *temp = list->first;

	if (index == 0){
		  newNode->next = list->first;
		  list->first = newNode;
		  list->size++;
		return true;
	}


	for(int i= 0 ; i < (index - 1); i++) {
	  if(temp->next != NULL) {
	    temp = temp->next;
	  }
	}
	newNode->next = temp->next;
	temp->next = newNode;
	list->size++;

	return true;
}

bool list_remove (list_t *list, int index)
{
    node_t * temp = list->first;

    if (temp == NULL)
    {
    	return false;
    }

    if (index == 0) {


        list->first = temp->next;


        free(temp);
        list->size--;
        return true;
    }

    for (int i = 0; temp != NULL && i < index - 1; i++)
        temp = temp->next;


    if (temp == NULL || temp->next == NULL)
        return false;

    node_t * next = temp->next->next;

    free(temp->next);

    temp->next = next;
    list->size--;
    return true;
}

int list_find (list_t *list, int item)
{
	node_t * current = list->first;
	int count;
	count = 0;
	while(current != NULL)
	{
		if (current->data == item)
			return count;
		count++;
		current = current->next;
	}
    return -1;
}

int list_get (list_t *list, int index)
{
    node_t *current = list->first;
    int count = 0;
    while (current != NULL) {
        if (count == index)
            return (current->data);
        count++;
        current = current->next;
    }
	return -1;
}

void list_Print (list_t * list){

	node_t * current = list->first;
	while (current != NULL) {
		printf("%d\n", current->data);
		current = current->next;
	}
}
