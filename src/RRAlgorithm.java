import java.util.ArrayList;

public class RRAlgorithm implements ISchedulingAlgorithm{
	
	private double quantumTime;
	
	public RRAlgorithm(double quantumTime){
		this.quantumTime = quantumTime;
	}
	
	public void enqueueProcess(ArrayList<Process> readyQueue,
			Process process) {
		readyQueue.add(process);
	}
	
	/**
	 * For RR algorithm the scheduling just sets the preemption tim eof each process
	 * so it can get interrupted at the appropriate time.
	 * @param readyQueue Queue with ready processes
	 */
	public void schedule(ArrayList<Process> readyQueue) {
		for(Process process : readyQueue){
			if (process.getCpuBurstTime() > quantumTime) {
				process.setPreemptRunningTime(quantumTime);
			}
		}
	}

	@Override
	public String toString() {
		return String.format("Round Robin ( Q = %.0f ) Algorithm", this.quantumTime);
	}
	
}
