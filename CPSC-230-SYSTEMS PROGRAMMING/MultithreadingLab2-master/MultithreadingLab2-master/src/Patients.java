import java.util.ArrayList;

public class Patients {
	private int numPatients;
	private ArrayList<Integer>patientTimes = new ArrayList<Integer>();
	private ArrayList<String>patientTools = new ArrayList<String>();
	public void setPatients(int numPatients) {
		this.numPatients = numPatients;
	}
	
	public int getNumPatients() {
		return numPatients;
	}
	
	public void addPatientTime(Integer time) {
		patientTimes.add(time);
	}
	
	public void fillTempArray(String temp) {
		patientTools.add(temp);
	}
	
	public void reducePatients() {
		numPatients --;
	}
	
	public int getPatientTimes() {
		int patientTime = patientTimes.get(0);
		patientTimes.remove(0);
		return patientTime;
	}
	
	public String getPatientTools() {
		String tools = patientTools.get(0);
		patientTools.remove(0);
		return tools;
	}
}
