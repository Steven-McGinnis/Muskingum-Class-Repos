import java.awt.Point;
import java.util.LinkedList;

public class Main {
	public static LinkedList<Point> cords = new LinkedList<Point>();
	
	public static void main(String[] args) {
		Population pop = new Population();
		Main main = new Main();
		
		main.setCords(new Point(1, 1));
		main.setCords(new Point(2, 2));
		main.setCords(new Point(3, 3));
		main.setCords(new Point(4, 4));
		
		
		for (int i = 0; i < 10; i++) {	
			LineChromosome line = new LineChromosome();
			pop.addChromosome(line);
		}
			
		int generation = 0;
		while (true) {
			if (pop.evaluate().getFitness() >= 0.999 || generation == 100000) {
				break;
			} else {
				pop.breed();
			}
			generation++;
		}
		
		if (generation == 100000) {
			System.out.println("Solution not found at generation " + generation + " Final Evaluation was "
					+ pop.evaluate().getFitness());
		} else {
			System.out.println("Solution found at generation " + generation + " " + pop.evaluate().getFitness());
		}
	}

	public static LinkedList<Point> getCords() {
		return cords;
	}

	public void setCords(Point point) {
		cords.add(point);
	}
}