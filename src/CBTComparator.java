import java.util.Comparator;

public class CBTComparator implements Comparator<Process> {

	public int compare(Process arg0, Process arg1) {
		return (int) (arg0.getCpuBurstTime() - arg1.getCpuBurstTime());
	}

	

}
