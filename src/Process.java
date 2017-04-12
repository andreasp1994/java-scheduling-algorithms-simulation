/**
 * 
 * This class is used to extend the definition of ProcessEntry with some additionaly properties
 * such us waiting time, running time, turnaround time and lastly some helper properties to enable
 * interrupts.
 * 
 * @author Antreas Pogiatzis
 *
 */
public class Process extends ProcessEntry {
	private double waitingTime;
	private double runningTime;
	private double turnaroundTime;
	
	private double preemptRunningTime;		// The time that the process is allowed to run continuously
	private double continuousRunningTime;	// The time that the process is running continuously 
	private double remainingCpuBurstTime;	// The remaining cpu burst time of the process
	
	// This is a listener which is used to notify the simulation that
	// the process has changes state.
	private ProcessStateChangeListener listener;
	
	public Process(Integer PID, double CpuBurstTime,
			double AbsoluteArrivalTime) {
		super(PID, CpuBurstTime, AbsoluteArrivalTime);
		this.waitingTime = 0;
		this.runningTime = 0;
		this.turnaroundTime = 0;
		this.continuousRunningTime = 0;
		this.preemptRunningTime = 0;
		this.remainingCpuBurstTime = CpuBurstTime;
	}
	
	public double getTurnaroundTime() {
		return turnaroundTime;
	}

	public double getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(double waitingTime) {
		this.waitingTime = waitingTime;
	}

	public double getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(double runningTime) {
		this.runningTime = runningTime;
	}

	public double getContinuousRunningTime() {
		return continuousRunningTime;
	}

	public void setContinuousRunningTime(double continuousRunningTime) {
		this.continuousRunningTime = continuousRunningTime;
	}

	public double getRemainingCpuBurstTime() {
		return remainingCpuBurstTime;
	}

	public void setRemainingCpuBurstTime(double remainingCpuBurstTime) {
		this.remainingCpuBurstTime = remainingCpuBurstTime;
	}

	public double getPreemptRunningTime() {
		return preemptRunningTime;
	}

	public void setPreemptRunningTime(double preemptRunningTime) {
		this.preemptRunningTime = preemptRunningTime;
	}
	
	public void updateProcessState(){
		if(runningTime == this.getCpuBurstTime()){
			listener.onProcessTerminated(this);
		} else if (continuousRunningTime == this.preemptRunningTime){
			this.setRemainingCpuBurstTime(this.getRemainingCpuBurstTime() - continuousRunningTime);
			continuousRunningTime = 0;
			listener.onProcessInterrupted(this);
		}
	}

	public void addRunningTime(){
		runningTime++;
		turnaroundTime++;
		continuousRunningTime++;
	}
	
	public void addWaitingTime(){
		turnaroundTime++;
		waitingTime++;
	}
	
	public void setOnStateChangeListener(ProcessStateChangeListener listener){
		this.listener = listener;
	}
	
	//State listener interface
	//Used to issue state changed events to the scheduler
	public interface ProcessStateChangeListener{
		
		void onProcessTerminated(Process process);
		
		void onProcessInterrupted(Process process);
	}
}
