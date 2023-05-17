import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class Main extends Thread{

	
	static int num_patients = 0;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main controller = new Main();
		controller.go();
	}

	private static void startDoctors(Equipment eq, Patients patient) {
		int numDoctors = 4;
		for (int i = 0; i < numDoctors; i ++) {
			Doctor doctor = new Doctor(eq, patient);
			doctor.start();
			
			long Start = System.currentTimeMillis();
			try {
				doctor.join();
				System.out.println("All Patients Complete");
				long Stop = System.currentTimeMillis();
				System.out.println(Stop - Start);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void go() {
		File file = getFile();
		Equipment eq = new Equipment();
		Patients patient = new Patients();
		readFile(file, eq, patient);
		startDoctors(eq, patient);
		
		
		
	}

	private File getFile() {
		System.out.println("Please Select a File.");
		{
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				return file;
			} else if (returnVal == JFileChooser.CANCEL_OPTION) {
				System.out.println("No File Selected");
				System.exit(0);
			}
			return null;
		}
	}
	
	private void readFile(File file, Equipment eq, Patients patient) {
		Scanner scnr;
		try {
			scnr = new Scanner(file);
			int num_equipment = scnr.nextInt();
			//Get Tools
			for (int i = 0; i < num_equipment; i ++) {
				String tool = scnr.next();
				int amount = scnr.nextInt();
				eq.addTool(tool);
				eq.addToolAmount(amount);
			}
			
			patient.setPatients(scnr.nextInt());	
			
			for (int i = 0; i < patient.getNumPatients(); i++) {
				int time = scnr.nextInt();
				patient.addPatientTime(time);
				String junk = scnr.nextLine();
				String needed = scnr.nextLine();
				patient.fillTempArray(needed);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return;
	}
}
