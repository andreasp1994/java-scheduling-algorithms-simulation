import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * This class is responsible for running the simulation and managing the queues.
 * The simulation consists of a workload, a readyQueue, a terminatedProcessesList, and a runningProcess.
 * Additionally, the strategy design pattern is used to specify which scheduling strategy to use.
 * 
 * @author Antreas Pogiatzis
 *
 */
public class Simulation implements Process.ProcessStateChangeListener{

	private ArrayList<Process> workload;						// Workload of processes
	private ArrayList<Process> readyQueue;						// Ready Queue
	private ArrayList<Process> terminatedProcesses;				// List with all the terminated processes
	private Process runningProcess;								// The running process
	private ISchedulingAlgorithm schedulingAlgorithm;			// Scheduling Algorithm strategy
	
	/**
	 * Class constructor which takes a csv file name as a parameter.
	 * The csv file specified is read and the process workload is generated.
	 * @param csvFile Process workoad in csv file in format : PID,CBT,AAT
	 */
	public Simulation(String csvFile){
		ArrayList<Process> workload = new ArrayList<Process>();
        BufferedReader br = null;
        String line = "";
        
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] processData = line.split(",");
                Process process = new Process(Integer.valueOf(processData[0]), Double.valueOf(processData[1]), Double.valueOf(processData[2]));
                workload.add(process);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        this.workload = workload;
		this.readyQueue = new ArrayList<Process>();
		this.terminatedProcesses = new ArrayList<Process>();
		attachListener();
	}
	
	/**
	 * Class  constructor
	 * @param workload The process workload
	 */
	public Simulation(ArrayList<Process> workload){
		this.workload = workload;
		this.readyQueue = new ArrayList<Process>();
		this.terminatedProcesses = new ArrayList<Process>();
		attachListener();
	}
	
	/**
	 * Setter method  to dynamically set the scheduling algorithm to be used.
	 * @param schedulingAlgorithm
	 */
	public void setSchedulingAlgorithm(ISchedulingAlgorithm schedulingAlgorithm){
		this.schedulingAlgorithm = schedulingAlgorithm;
	}

	/**
	 * This method initiates the simulation. This is a more realistic simulation of the scheduling process since it
	 * simulates cpu cycles. t variable is used to measure the current cpu cycle and the loop continues to run until
	 * all the processes terminate.
	 * 
	 * The events executed in each cycle are in this order:
	 * 		a ) dispatchProcesses() - Find any processes that arrive on that cycle and dispatch them into the ready queue. 
	 * 								- Use algorithm.schedule() method to sort the queue each time a process arrives
	 * 		b ) updateRunningProcessState() - Check whether the currently running process is terminated or interrupted.
	 *		c ) runProcessIfIdle()	- if there is no process running, dequeue the first process from queue and run it.
	 *		d ) updateTimes() - Update running, waiting, and turnaround time of each process.
	 *						  - Processes in readyQueue get +1 at their waiting time and turnaround time
	 *						  - Running Process gets +1 at its running time and its turnaround time 
	 *
	 */
	public void simulate(){
		System.out.println(String.format("Simulation of %s started! Workload: %d", this.schedulingAlgorithm, this.workload.size()));
		int t = (int) workload.get(0).getAbsoluteArrivalTime();
		int ptotal = this.workload.size();
		
		//Simulation loop
		while(terminatedProcesses.size() != ptotal){
			dispatchProcesses(t);
			updateRunningProcessState();
			runProcessIfIdle();
			updateTimes();
			t++;
			
		}
	}
	
	/**
	 * This method attaches the simulation as an OnStateCahngeListener on
	 * each process.
	 */
	public void attachListener(){
		for(Process process : this.workload){
			process.setOnStateChangeListener(this);
		}
	}
	
	/**
	 * Find any process that arrive at time t and uses the scheduling algorithm implementation
	 * to enqueue them and sort them into the queue.
	 * @param t Current cpu cycle ( time ).
	 */
	public void dispatchProcesses(int t){
		while (!workload.isEmpty() && workload.get(0).getAbsoluteArrivalTime() == t){
			schedulingAlgorithm.enqueueProcess(readyQueue, workload.remove(0));
		}
		schedulingAlgorithm.schedule(readyQueue);
	}
	
	/**
	 * If there is no process running. Gets the first process in the ready queue and runs it
	 */
	public void runProcessIfIdle(){
		if (runningProcess == null && !readyQueue.isEmpty()) {
			runningProcess = readyQueue.remove(0);
		}
	}
	
	/**
	 * Update waiting time and running time of processes.
	 */
	public void updateTimes(){
		for(Process process : readyQueue){
			process.addWaitingTime();
		}
		if (runningProcess != null){
			runningProcess.addRunningTime();
		}
	}
	
	/**
	 * Check whether running process needs to be terminated or interrupted and notifies
	 * current ProcessStateChangeListener.
	 */
	public void updateRunningProcessState(){
		if(runningProcess != null) runningProcess.updateProcessState();
	}

	/**
	 * Callback which is called when a process is terminated.
	 * @param process Process which terminated
	 */
	public void onProcessTerminated(Process process) {
		terminatedProcesses.add(process);
		runningProcess = null;
		schedulingAlgorithm.schedule(readyQueue);
	}

	/**
	 * Callback which is called when a process is interrupted.
	 * @param process Process which is interrupted
	 */
	public void onProcessInterrupted(Process process) {
		schedulingAlgorithm.enqueueProcess(readyQueue, process);
		runningProcess = null;
		schedulingAlgorithm.schedule(readyQueue);
	}
	
	/**
	 * Prints metrics for simulation.
	 */
	public void printMetrics(){
		double totalWaitingTime = 0;
		double totalTurnaroundTime = 0;
		double averageWaitingTime = 0;
		double averageTurnaroundTime = 0;
		
		for(Process process : terminatedProcesses){
			totalWaitingTime += process.getWaitingTime();
			totalTurnaroundTime += process.getTurnaroundTime();
//			System.out.println(String.format("%d, %.1f, %.1f",process.getPID(), process.getWaitingTime(), process.getTurnaroundTime()));

		}
		averageWaitingTime = totalWaitingTime/terminatedProcesses.size();
		averageTurnaroundTime = totalTurnaroundTime/terminatedProcesses.size();
		
		System.out.println("====================");
		System.out.println(String.format("Average Waiting Time: %.2f", averageWaitingTime));
		System.out.println(String.format("Average Turnaround Time: %.2f\n",averageTurnaroundTime));
	}
	
	/**
	 * Writes a csv file with all the processes and their corresponding turnaround and waiting time
	  @param csvFile The name of the csvfile to be created.
	 */
	public void writeProcessesToCsv(String csvFile){
		FileWriter fileWriter = null;
		
		try{
			fileWriter = new FileWriter(csvFile);
			
			for(Process process : terminatedProcesses){
				fileWriter.append(String.format("%d, %.1f, %.1f",process.getPID(), process.getWaitingTime(), process.getTurnaroundTime()));

			}
		} catch	(Exception e){
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
