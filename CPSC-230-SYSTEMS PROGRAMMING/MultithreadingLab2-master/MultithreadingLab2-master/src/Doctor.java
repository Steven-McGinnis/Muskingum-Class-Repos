import java.util.ArrayList;

public class Doctor extends Thread {
	private Equipment eq;
	private Patients patient;

	public Doctor(Equipment eq, Patients patient) {
		this.eq = eq;
		this.patient = patient;
	}

	public void run() {
		while (patient.getNumPatients() > 0) {
			ArrayList<Integer> ToolsIndex = new ArrayList<Integer>();
			long patientTime;
			String[] patientTools;

			synchronized (patient) {

				if (patient.getNumPatients() <= 0) {
					return;
				}
				// Reduce the Total Number of Patients in the Queue
				patient.reducePatients();

				// PatientTime for the Wait
				patientTime = patient.getPatientTimes();

				// Collects the String of Tools
				patientTools = breakupTools(patient.getPatientTools());
			}

			synchronized (eq) {
				// TODO Get the index for each tool
				for (int i = 0; i < patientTools.length; i++) {
					ToolsIndex.add(eq.checkTool(patientTools[i]));
				}

				// TODO Identify if all tools are Available
				boolean allToolsAvailable = false;
				while (allToolsAvailable == false) {
					for (int i = 0; i < ToolsIndex.size(); i++) {
						if (eq.isAvailable(ToolsIndex.get(i)) == true) {
							allToolsAvailable = true;
						}
					}
				}

				// Reserve Equipment
				for (int i = 0; i < ToolsIndex.size(); i++) {
					eq.reserveTool(i);
				}
			}

			// Time out
			boolean TimePassed = false;
			long start = System.currentTimeMillis();
			long totalTimePassed = 0;
			while (TimePassed == false) {
				try {
					sleep(patientTime);
					long stop = System.currentTimeMillis();
					long time = stop - start;
					totalTimePassed = totalTimePassed + time;
					if (totalTimePassed > patientTime) {
						TimePassed = true;
					}
				} catch (InterruptedException e) {
				}

			}

			// Release all EquipMent and yeet the prisoner
			for (int i = 0; i < ToolsIndex.size(); i++) {
				eq.returnTool(i);
			}
			System.out.println("Patient Complete");

		}
	}

	public String[] breakupTools(String tools1) {
		String[] tools = tools1.split(" ");
		return tools;

	}
}
