import java.util.ArrayList;
import java.util.Random;

public class Population {
	private ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
	private double mutationRate = 0;
	private int population;

	/**
	 * Constructor
	 */
	public Population() {
	}

	/**
	 * Adds the Sent Chromosome to the Array for the Population
	 * 
	 * @param c Chromosome
	 */
	public void addChromosome(Chromosome c) {
		chromosomes.add(c);
	}

	/**
	 * Runs through the list of Chromosomes and returns the most fit one
	 * 
	 * @return mostFit Chromosome
	 */
	public Chromosome evaluate() {
		Chromosome mostFit = null;
		for (Chromosome c : chromosomes) {
			if (mostFit == null || c.getFitness() < mostFit.getFitness())
				mostFit = c;
		}
		return mostFit;
	}

	public void breed() {
		// Create Values and constructors
		Random rand = new Random();
		population = chromosomes.size(); // Sets the population size to use to compare during the refilling of the
											// population
		ArrayList<Double> Tickets = new ArrayList<Double>();// Holds Each Chromosomes Ticket Value without needed to
															// apply it to the Chromosome class
		double TicketTotal = 00.00; // The Ticket Total that will be used as the maximum random value.
		Chromosome leastFit = null; // The Leastfit Chromosome to use to generate tickets
		Chromosome parentChromosome1 = null; // Parent 1 to breed
		Chromosome parentChromosome2 = null; // Parent 2 to breed

		// Get Least fit Chromosome
		for (Chromosome c : chromosomes) {
			if (leastFit == null || c.getFitness() < leastFit.getFitness() && c.getFitness() > 0) {
				leastFit = c;
			}
		}
		
		// Generate Tickets total from least Chromosome
		for (Chromosome c : chromosomes) {
			double tickets = (c.getFitness() / leastFit.getFitness());
			tickets = Math.round(tickets); 
			TicketTotal = TicketTotal + tickets;
			Tickets.add(tickets);
		}

		// Creates a replacement population
		ArrayList<Chromosome> chromosomes1 = new ArrayList<Chromosome>();

		// While new population isnt the same size as the old population do this
		while (chromosomes1.size() != population) {

			// Generates Random parent numbers from the tickets avoiding 0's
			double parentticket1 = 0.0;// This is the random ticket value for parent 1
			double parentticket2 = 0.0;// This is the random ticket value for parent 2
			while (parentticket1 <= 0.0 && parentticket2 <= 0.0) {
				parentticket1 = rand.nextInt((int) TicketTotal);// Randomize the value and check for negatives or 0
				parentticket2 = rand.nextInt((int) TicketTotal);// Randomize the value and check for negatives or 0
			}

			// These values are the base size that incriment as tickets are added to it
			double ticketTarget = 0.0;
			double ticketTarget2 = 0.0;

			// For Parent 1
			// while the ticketTarget is less than the parent ticket add to the ticket
			// target and incriment i which will call the correct chromosome in the array.
			for (int i = 0; i < Tickets.size(); i++) {
				if (ticketTarget < parentticket1 && parentticket1 > (Tickets.get(i) + ticketTarget)) {
					ticketTarget = ticketTarget + Tickets.get(i);
				} else {
					parentChromosome1 = chromosomes.get(i);
					break;
				}
			}

			// For Parent 2
			// while the ticketTarget2 is less than the parent ticket add to the ticket
			// target and incriment i which will call the correct chromosome in the array.
			for (int i = 0; i < Tickets.size(); i++) {
				if (ticketTarget2 < parentticket2 && parentticket2 > (Tickets.get(i) + ticketTarget2)) {
					ticketTarget2 = ticketTarget2 + Tickets.get(i);
				} else {
					parentChromosome2 = chromosomes.get(2);
					break;
				}
			}


			// Breed Parents
			Chromosome newChild = parentChromosome1.crossover(parentChromosome2);

			// Mutate the New Child if Necessary
			newChild.mutate(mutationRate);
			// Add the Child to the new Chromosome array
			chromosomes1.add(newChild);
		}
		// Replace the old Chromosome
		chromosomes = chromosomes1;

	}

	public void setMutationRate(double mutationrate) {
		this.mutationRate = mutationrate;
	}
}