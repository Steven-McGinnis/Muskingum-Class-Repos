#include <stdio.h>
#include "utility.h"


int getNumber(int min, int max)
{
	int number;

	printf("Please Enter a Number. \n");
	scanf("%d", &number);
	if (number < min || number > max)
	{
		getNumber(min, max);
	}
	else
	{
		return number;
	}

}
