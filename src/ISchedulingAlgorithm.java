import java.util.ArrayList;

/**
 * Interface which defines the scheduling algorithm.
 * 
 * @author Antreas Pogiatzis
 *
 */
public interface ISchedulingAlgorithm {
	
	/**
	 * Adds a process into the readyQueue
	 * @param readyQueue
	 * @param process
	 */
	public void enqueueProcess(ArrayList<Process> readyQueue, Process process);
	
	/**
	 * Sorts the ready queue appropriately.
	 * @param readyQueue
	 */
	public void schedule(ArrayList<Process> readyQueue);

}
