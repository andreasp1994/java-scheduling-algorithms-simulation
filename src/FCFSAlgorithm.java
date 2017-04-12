import java.util.ArrayList;

public class FCFSAlgorithm implements ISchedulingAlgorithm{

	public void schedule(ArrayList<Process> readyQueue) {
		// nothing to sort since the processes keep their natural 
		// arrival time sort.
	}

	public void enqueueProcess(ArrayList<Process> readyQueue,
			Process process) {
		readyQueue.add(process);
	}
	
	@Override
	public String toString() {
		return "FCFS Algorithm";
	}
}
