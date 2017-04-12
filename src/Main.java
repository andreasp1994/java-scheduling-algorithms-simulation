/**
 * This is a simulation of various OS scheduling algorithms.
 * Use this class to run the simulation.
 *  
 * @author Antreas Pogiatis
 *
 */
public class Main {

	public static void main(String[] args){	
		
		// NOTE: Statistics are printed in standard output. However by uncommenting the last line
		// 		 of each simulation code, the results will be  printed in a csv file as well.
		
		/**
		 * Workload 1
		 */
		
		//Example of simulating FCFS algorithm
		Simulation simulation = new Simulation("WorkLoad_1.csv");
		simulation.setSchedulingAlgorithm(new FCFSAlgorithm());
		simulation.simulate();
		simulation.printMetrics();
//		simulation.writeProcessesToCsv("W1_FCFS_Stats.csv");
		
		//Example of simulating Non-Preemptive SJF algorithm
		simulation = new Simulation("WorkLoad_1.csv");
		simulation.setSchedulingAlgorithm(new NPSJFAlgorithm());
		simulation.simulate();
		simulation.printMetrics();
//		simulation.writeProcessesToCsv("W1_NPSJF_Stats.csv");
		
		//Example of simulating RR Algorithm with Q = 5
		simulation = new Simulation("WorkLoad_1.csv");
		simulation.setSchedulingAlgorithm(new RRAlgorithm(5));
		simulation.simulate();
		simulation.printMetrics();
//		simulation.writeProcessesToCsv("W1_RRQ5_Stats.csv");
		
		//Example of simulating RR Algorithm with Q = 15
		simulation = new Simulation("WorkLoad_1.csv");
		simulation.setSchedulingAlgorithm(new RRAlgorithm(15));
		simulation.simulate();
		simulation.printMetrics();
//		simulation.writeProcessesToCsv("W1_RRQ15_Stats.csv");
		
		//Example of simulating RR Algorithm with Q = 40
		simulation = new Simulation("WorkLoad_1.csv");
		simulation.setSchedulingAlgorithm(new RRAlgorithm(40));
		simulation.simulate();
		simulation.printMetrics();
//		simulation.writeProcessesToCsv("W1_RRQ40_Stats.csv");
		
		/**
		 * Workload 2
		 */
		
		//Example of simulating FCFS algorithm
		simulation = new Simulation("WorkLoad_2.csv");
		simulation.setSchedulingAlgorithm(new FCFSAlgorithm());
		simulation.simulate();
		simulation.printMetrics();
//		simulation.writeProcessesToCsv("W2_FCFS_Stats.csv");
		
		//Example of simulating Non-Preemptive SJF algorithm
		simulation = new Simulation("WorkLoad_2.csv");
		simulation.setSchedulingAlgorithm(new NPSJFAlgorithm());
		simulation.simulate();
		simulation.printMetrics();
//		simulation.writeProcessesToCsv("W2_NPSJF_Stats.csv");
	
		//Example of simulating RR Algorithm with Q = 5
		simulation = new Simulation("WorkLoad_2.csv");
		simulation.setSchedulingAlgorithm(new RRAlgorithm(5));
		simulation.simulate();
		simulation.printMetrics();
//		simulation.writeProcessesToCsv("W2_RRQ5_Stats.csv");

		//Example of simulating RR Algorithm with Q = 15
		simulation = new Simulation("WorkLoad_2.csv");
		simulation.setSchedulingAlgorithm(new RRAlgorithm(15));
		simulation.simulate();
		simulation.printMetrics();
//		simulation.writeProcessesToCsv("W2_RRQ15_Stats.csv");

		//Example of simulating RR Algorithm with Q = 40
		simulation = new Simulation("WorkLoad_2.csv");
		simulation.setSchedulingAlgorithm(new RRAlgorithm(40));
		simulation.simulate();
		simulation.printMetrics();
//		simulation.writeProcessesToCsv("W2_RRQ40_Stats.csv");

	}	
}
