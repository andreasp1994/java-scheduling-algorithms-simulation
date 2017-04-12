import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class WorkloadGenerator {

	private Random random;
	
	//Gaussian constants
	private static final double CBT_MIN = 5;
	
	//Poisson Constants
	double L = 5;
	
	public WorkloadGenerator(){
		this.random = new Random(System.currentTimeMillis());
	}
	
	public ArrayList<Process> generateWorkload(double STD, double M, double L){
		int N = 1000;
		
		ArrayList<Process> processWorkload = new ArrayList<Process>(N);
		processWorkload.add(createInitialProcess(STD, M));
		
		for(int i = 1; i < N; i++){
			double CBT = randomGaussianValue(STD, M);
			CBT = Math.max(CBT_MIN, CBT);
			CBT = Math.ceil(CBT);
			double Dt = randomPoisson(L);
			double AAT = processWorkload.get(i-1).getAbsoluteArrivalTime() + Dt;
			
			Process process = new Process(i, CBT, AAT);
			processWorkload.add(process);
		}
		
		return processWorkload;
	}
	
	public ArrayList<Process> generateW1(){
		double M1 = 20;
		double STD1 = 3;
		return generateWorkload(STD1, M1, L);
	}
	
	public ArrayList<Process> generateW2(){
		double M2 = 60;
		double STD2 = 3;
		return generateWorkload(STD2, M2, L);
	}
	
	public static void printWorkload(ArrayList<ProcessEntry> processWorkload){
		for(ProcessEntry process : processWorkload){
			System.out.println(String.format("PID: %s \tCBT: %f \tAAT: %f", process.getPID(), process.getCpuBurstTime(), process.getAbsoluteArrivalTime()));
		}
	}
	
	public static void exportToCsv(ArrayList<Process> processWorkload, String filename ){
//		String FILE_HEADER = "pid,cbt,aat\n";

		FileWriter fileWriter = null;
		
		try{
			fileWriter = new FileWriter(filename);
			
//			fileWriter.append(FILE_HEADER);
			for(ProcessEntry process : processWorkload){
				fileWriter.append(((ProcessEntry)process).toString()); //casted to superclass
			}
			
		} catch	(Exception e){
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private Process createInitialProcess(double STD, double M){
		double CBT = randomGaussianValue(STD, M);
		CBT = Math.max(CBT_MIN, CBT);
		return new Process(0, Math.ceil(CBT), 0);
	}
	
	private double randomGaussianValue(double STD1, double M1){
		return random.nextGaussian() * STD1 + M1;
	}
	
	private double randomPoisson(double mean){
		int k = 0;
		double p = 1.0;
		double expLambda = Math.exp(-mean);
		do {
			k++;
			p *= random.nextDouble();
		} while (p >= expLambda);
		return k-1;
	}
}
