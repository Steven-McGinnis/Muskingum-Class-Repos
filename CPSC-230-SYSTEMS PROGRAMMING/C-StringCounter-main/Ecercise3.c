#include <stdio.h>
#include <string.h>
int main()
{
	char string[51];
	int result;

	while (result != 0 ){
		printf("Please enter a Command or quit \n ");
		scanf("%s50", string);
		result = strcmp(string, "quit");
		if (result != 0)
		      printf("Length of Command is = %zu \n ", strlen(string));	
	}
	return 0;
}
