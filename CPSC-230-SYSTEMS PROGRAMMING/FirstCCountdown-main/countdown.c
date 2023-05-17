#include <stdio.h>

int main()
{
	int number;

	printf("Please Enter a Number.\n");
	scanf("%d", &number);

	for (number; number >= 0; number--){
		printf( "%d", number, "\n");
		printf("\n");	
	}


	return 0;
}
