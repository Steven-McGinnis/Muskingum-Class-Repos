#include <stdio.h>
#include <stdlib.h>
#include "linkedlist.h"

void verify (bool condition, char *message)
{
    if (condition)
    {
        printf ("%s\n", message);
        exit (0);
    }	
}

int main ()
{
    list_t *list;

    list = create_list ();

    verify (list == NULL, "create_list returned NULL");
    verify (list_size (list) != 0, "list_size returned non-zero size for a new list");

    list_append(list, 10);

    verify (list_size (list) != 1, "list_size did not return 1 after list_append on empty list");
    verify (list_find (list, 10) != 0, "list_find did not find 10 in list");
    verify (list_get (list, 0) != 10, "list_get did not get 10 as first item");

    verify (list_remove (list, 0) != true, "list_remove did not return true on valid index");

    verify (list_size (list) != 0, "list_size did not return 0 after list_remove");
    verify (list_find (list, 10) != -1, "list_find found 10 in list after list_remove");
    verify (list_get (list, 0) != -1, "list_get did not return error on invalid index");

    verify (list_insert (list, 10, 5) != false, "list_insert did not return error on invalid index");
    verify (list_remove (list, 5) != false, "list_remove did not return error on invalid index");  

    list_append (list, 10);
    list_append (list, 20);
    list_append (list, 30);
    list_append (list, 40);
    list_append (list, 50);
    list_append (list, 60);

    verify (list_get (list, 0) != 10, "list_get did not return 10 as first element");
    verify (list_get (list, 1) != 20, "list_get did not return 20 as second element");
    verify (list_get (list, 2) != 30, "list_get did not return 30 as third element");
    verify (list_get (list, 3) != 40, "list_get did not return 40 as fourth element");
    verify (list_get (list, 4) != 50, "list_get did not return 50 as fifth element");
    verify (list_get (list, 5) != 60, "list_get did not return 60 as sixth element");

    verify (list_insert (list, 55, 5) != true, "list_insert returned false on valid index");
    verify (list_insert (list, 35, 3) != true, "list_insert returned false on valid index");
    verify (list_insert (list, 5, 0) != true, "list_insert returned false on valid index");

    verify (list_get (list, 0) != 5, "list_get did not return 5 as first element");
    verify (list_get (list, 1) != 10, "list_get did not return 10 as second element");
    verify (list_get (list, 2) != 20, "list_get did not return 20 as third element");
    verify (list_get (list, 3) != 30, "list_get did not return 30 as fourth element");
    verify (list_get (list, 4) != 35, "list_get did not return 35 as fifth element");
    verify (list_get (list, 5) != 40, "list_get did not return 40 as sixth element");
    verify (list_get (list, 6) != 50, "list_get did not return 50 as seventh element");
    verify (list_get (list, 7) != 55, "list_get did not return 55 as eighth element");
    verify (list_get (list, 8) != 60, "list_get did not return 60 as ninth element");

    verify (list_find (list, 5) != 0, "list_find did not return 0 for 5");
    verify (list_find (list, 10) != 1, "list_find did not return 1 for 10");
    verify (list_find (list, 20) != 2, "list_find did not return 2 for 20");
    verify (list_find (list, 30) != 3, "list_find did not return 3 for 30");
    verify (list_find (list, 35) != 4, "list_find did not return 4 for 35");
    verify (list_find (list, 40) != 5, "list_find did not return 5 for 40");
    verify (list_find (list, 50) != 6, "list_find did not return 6 for 50");
    verify (list_find (list, 55) != 7, "list_find did not return 7 for 55");
    verify (list_find (list, 60) != 8, "list_find did not return 8 for 60");

    verify (list_remove (list, 0) != true, "list_remove returned false when removing first element");
    verify (list_get (list, 0) != 10, "list_get did not return 10 as first element after list_remove");
    verify (list_remove (list, 7) != true, "list_remove returned false when removing last element");
    verify (list_get (list, 6) != 55, "list_get did not return 55 as last element after list_remove");
    verify (list_remove (list, 3) != true, "list_remove returned false when removing middle element");
    verify (list_get (list, 3) != 40, "list_get did not return 40 after list_remove");

    free_list (list);
    printf ("All tests passed!\n");
}

