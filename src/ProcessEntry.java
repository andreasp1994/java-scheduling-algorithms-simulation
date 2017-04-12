/**
 * 
 * This class is used to define the process model with as minimum properties as possible.
 * 
 * @author Antreas Pogiatzis
 *
 */
public class ProcessEntry {
	
	private Integer PID;					// Process ID
	private double CpuBurstTime;			// Cpu Burst Time
	private double AbsoluteArrivalTime;		// Absolute Arrival Time
	
	public ProcessEntry(Integer PID, double CpuBurstTime, double AbsoluteArrivalTime){
		this.PID = PID;
		this.CpuBurstTime = CpuBurstTime;
		this.AbsoluteArrivalTime = AbsoluteArrivalTime;
	}

	public Integer getPID() {
		return PID;
	}

	public void setPID(Integer pID) {
		PID = pID;
	}

	public double getCpuBurstTime() {
		return CpuBurstTime;
	}

	public void setCpuBurstTime(double cpuBurstTime) {
		CpuBurstTime = cpuBurstTime;
	}

	public double getAbsoluteArrivalTime() {
		return AbsoluteArrivalTime;
	}

	public void setAbsoluteArrivalTime(double absoluteArrivalTime) {
		AbsoluteArrivalTime = absoluteArrivalTime;
	}

	@Override
	public String toString() {
		return String.format("%d,%d,%d\n", PID, (int) CpuBurstTime, (int) AbsoluteArrivalTime );
	}	
}
