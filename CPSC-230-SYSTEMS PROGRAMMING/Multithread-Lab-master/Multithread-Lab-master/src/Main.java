import java.util.Random;

public class Main {
	int total = 0;
	int threadTotal;
	private int numIntegers = 10;
	int array[];
	 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main controller = new Main();
		controller.go();
	}

	private void go() {
		// TODO Auto-generated method stub
		generate();
		printArray();
		printArrayWithoutThreads();
		thread();
	}

	private void thread() {
		// TODO Auto-generated method stub
		int numThreads = 10;

		for (int i = 0; i < numThreads; i++) {
			MyThread thread = new MyThread(i, array, numIntegers);
			thread.start();
		}

	}


	private void printArrayWithoutThreads() {
		// TODO Auto-generated method stub
		for (int i = 0; i < array.length; i++) {
			total = total + array[i];
		}
		System.out.println("The Total should be " + total);
		
	}

	private void generate() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		this.array = new int[numIntegers];
		for (int i = 0; i < array.length; i++) {
			array[i] = rand.nextInt(101);
		}
	}

	private void printArray() {
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
	}
}
