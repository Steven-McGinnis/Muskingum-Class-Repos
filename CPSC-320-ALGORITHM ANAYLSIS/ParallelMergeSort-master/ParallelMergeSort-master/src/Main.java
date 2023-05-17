import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {
	int numbers[];
	int arraySize = 2_000_000;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main controller = new Main();
		controller.go();
	}

	//Driver Code
	private void go(){
		ForkJoinPool pool = new ForkJoinPool();
		buildArray();
		pool.invoke(new ForkJoinMerge(numbers, 0, arraySize - 1));
		verifyArray();
	}
	
	//Constructs the Array Simple Method
	private void buildArray() {
		Random rand = new Random();
		this.numbers = new int[arraySize];
		for (int i = 0; i < numbers.length; i ++) {
			numbers[i] = rand.nextInt();
		}
	}
	
	//My Utility Method
	private void printArray() {
		for (int i = 0; i < numbers.length; i++) {
			System.out.println((i + 1) + ": " + numbers[i]);
		}
	}
	
	//Verification method
	private void verifyArray() {
		for(int i = 0; i < (numbers.length - 1); i++) {
			if(numbers[i] > numbers[i + 1]) {
				System.out.println("Array failed to sort properly.");
				return;
			}
		}
		System.out.println("Array Successfully sorted");
		return;
	}
}