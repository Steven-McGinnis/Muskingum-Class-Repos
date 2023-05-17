import java.util.concurrent.RecursiveAction;

public class  ForkJoinMerge extends RecursiveAction {

        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private int[] numbers;
        private int size;
        private int index;

        public ForkJoinMerge(final int[] numbers, int index, final int size) {
            this.numbers = numbers;
            this.index = index;
            this.size = size;
        }

    @Override
    protected void compute() {
	    if (index < size) {
	        int middle = index + (size - index) / 2;

	        ForkJoinMerge leftFork = new ForkJoinMerge(numbers, index, middle);
		    ForkJoinMerge rightFork = new ForkJoinMerge(numbers, middle + 1, size);
		    
		    invokeAll(leftFork, rightFork);
	          
	        merge(numbers, index, middle, size);
        }
    }
	
    void merge(int numbers[], int start, int mid, int end)
    {
        int arrayStartLocation2 = mid + 1;
     
        if (numbers[mid] <= numbers[arrayStartLocation2]) {
            return;
        }
     
        while (start <= mid && arrayStartLocation2 <= end) {
     
            if (numbers[start] <= numbers[arrayStartLocation2]) {
                start++;
            }
            else {
                int value = numbers[arrayStartLocation2];
                int index = arrayStartLocation2;
     
                while (index != start) {
                    numbers[index] = numbers[index - 1];
                    index--;
                }
                numbers[start] = value;
     
                start++;
                mid++;
                arrayStartLocation2++;
            }
        }
    }
}