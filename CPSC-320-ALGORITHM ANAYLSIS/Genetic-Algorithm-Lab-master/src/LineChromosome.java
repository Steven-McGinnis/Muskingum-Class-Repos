import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class LineChromosome implements Chromosome {
	private Random rand = new Random();
	private int numbits = 51;
	protected int[] bits;

	public LineChromosome() {
		bits = new int[numbits];
		randomize();
	}

	/**
	 * Randomizes the Bits in the Chromosome and if there is a Zero in Denominator
	 * Remakes it
	 */
	public void randomize() {
		String Denominator1 = "";
		String Denominator2 = "";
		String Denominator3 = "";
		while (Denominator1.equals("") || Denominator1.equals("00000000") || Denominator2.equals("")
				|| Denominator2.equals("00000000") || Denominator3.equals("") || Denominator3.equals("00000000")) {
			for (int i = 0; i < bits.length; i++)
				bits[i] = rand.nextInt(2);
			for (int j = 8; j <= 15; j++) {
				Denominator1 = Denominator1 + bits[j];
			}
			for (int j = 25; j <= 32; j++) {
				Denominator2 = Denominator2 + bits[j];
			}
			for (int j = 42; j <= 49; j++) {
				Denominator3 = Denominator3 + bits[j];
			}
		}
		return;
	}

	@Override
	public Chromosome crossover(Chromosome c) {
		// TODO Auto-generated method stub
		return crossover ((LineChromosome) c, new LineChromosome ());
	}

	public Chromosome crossover(LineChromosome other, LineChromosome newChromosome) {
		int first = rand.nextInt(bits.length);
		int second = rand.nextInt(bits.length);

		if (first > second) {
			int temp = first;
			first = second;
			second = temp;
		}

		for (int i = 0; i < first; i++)
			newChromosome.bits[i] = bits[i];

		for (int i = first; i < second; i++)
			newChromosome.bits[i] = other.bits[i];

		for (int i = second; i < bits.length; i++)
			newChromosome.bits[i] = bits[i];

		return newChromosome;
	}

	@Override
	public void mutate(double mutationRate) {
		// TODO Auto-generated method stub
        for (int i = 0; i < bits.length; i++)
        {
            if (rand.nextDouble() <= mutationRate)
            {
                if (bits[i] == 1)
                    bits[i] = 0;
                else
                    bits[i] = 1;
            }
        }
	}

	@Override
	public double getFitness() {
		// Constructors
		Main main = new Main();
		Queue<Integer> points = new LinkedList<Integer>();
		ArrayList<Double> PointsFinal = new ArrayList<Double>();
		ArrayList<Double>fitnesses = new ArrayList<Double>();


		
		// Gets and Converts Decimal from Array to a Single Usable Number
		String test = "";
		int start = 0;
		int stop = 7;
		for (int i = 0; i < 6; i++) {
			if (i == 1) {
				start = 8;
				stop = 15;
			} else if (i == 2) {
				start = 17;
				stop = 24;
			} else if (i == 3) {
				start = 25;
				stop = 32;
			} else if (i == 4) {
				start = 34;
				stop = 41;
			} else if (i == 5) {
				start = 42;
				stop = 49;
			}
			for (int j = start; j <= stop; j++) {
				test = test + bits[j];
			}
			points.add(convertBinaryToDecimal(Integer.parseInt(test)));
			test = "";
		}

		// Divide numerator and denominator apply negative if necessary
		int sign = 16;
		for (int i = 0; i < 3; i++) {
			double Numerator = points.poll();
			double Denominator = points.poll();
			double Point = Numerator / Denominator;

			if (bits[sign] == 1) {
				PointsFinal.add((Point * -1));
			} else {
				PointsFinal.add(Point);
			}
			sign = sign + 17;
		}

		//The points in Decimal Form
		double A = PointsFinal.get(0);
		double B = PointsFinal.get(1);
		double C = PointsFinal.get(2);
		
			
		//Do math on equation of a line using points x, and y with the points in Decimal Form
		//Add them to Array
		for (int i = 0; i < Main.cords.size(); i++) {
			Point target = Main.cords.get(i);
			double Fitness =  (A * target.x) + (B * target.y) + C;
			Fitness = 1 / (1 + Fitness);
			fitnesses.add(Fitness);
		}
		
		//Create the Final Fitness
		//Then take that and add each fitness to create a total then divide to get the mean
		double finalFitness = 0;
		for (int i = 0; i < fitnesses.size(); i ++) {
			finalFitness =  finalFitness + fitnesses.get(i);
		}

		finalFitness = finalFitness/fitnesses.size();
		finalFitness = Math.abs(finalFitness);
		return finalFitness;
	}

	public static int convertBinaryToDecimal(int num) {
		int decimalNumber = 0, i = 0;
		long remainder;

		while (num != 0) {
			remainder = num % 10;
			num /= 10;
			decimalNumber += remainder * Math.pow(2, i);
			++i;
		}
		return decimalNumber;
	}
}