import java.util.ArrayList;
import java.util.Collections;


public class NPSJFAlgorithm implements ISchedulingAlgorithm{

	public void schedule(ArrayList<Process> readyQueue) {
		Collections.sort(readyQueue, new CBTComparator());
	}

	public void enqueueProcess(ArrayList<Process> readyQueue,
			Process process) {
		readyQueue.add(process);
	}
	
	@Override
	public String toString() {
		return "Non-Preemptive SJF Algorithm";
	}
}
