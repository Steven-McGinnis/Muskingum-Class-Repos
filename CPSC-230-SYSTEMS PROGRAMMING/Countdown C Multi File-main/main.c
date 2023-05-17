#include <stdio.h>
#include "utility.h"

int main()
{
	int number;
	number = getNumber(1, 100);

	for (number; number >= 0; number--)
	{
		printf("%d", number);
		printf("\n");
	}
}
