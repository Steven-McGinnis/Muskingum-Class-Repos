public interface Chromosome {
	public Chromosome crossover(Chromosome c);
	public void mutate (double mutationRate);
	public double getFitness ();
}