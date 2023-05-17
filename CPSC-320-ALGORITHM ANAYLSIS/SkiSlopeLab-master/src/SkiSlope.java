import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class SkiSlope {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SkiSlope controller = new SkiSlope();
		controller.go();
	}
	
	public void go() {
		Queue<Integer> data = getData();
		int [][] adjacencyMatrix = create2DArray(data);
		printMatrix(adjacencyMatrix);
		System.out.println();
		calculateRuns(adjacencyMatrix);
		printMatrix(adjacencyMatrix);
	}
	
	//Displays the Matrix for Development
	private void printMatrix(int[][] adjacencyMatrix) {
		// TODO Auto-generated method stub
		for(int i = 0; i < adjacencyMatrix.length; i++) {
			for(int j = 0; j < adjacencyMatrix.length; j++) {
				System.out.print(adjacencyMatrix[i][j]);
			}
			System.out.println();
		}
		
	}

	private void calculateRuns(int[][] adjacencyMatrix) {
		int number = 0;
		int readjust = 1;
		int length = adjacencyMatrix.length - 1;
		for(int i = adjacencyMatrix.length -1; i >= 0; i--) {
			System.out.println(number);
			for (int j = adjacencyMatrix.length -1; j >= 0; j--) {			
				number = number + adjacencyMatrix[i][j];
				if (adjacencyMatrix[i][j] == adjacencyMatrix[i][0]) {
					adjacencyMatrix[i][0] = number;
					number = 0;
				}
			}
			
			if (i - 1 < 0) {
				break;
			}
			number = (adjacencyMatrix[i][0] * adjacencyMatrix[i - 1][length]);
			if(readjust == 1) {
				length++;
				readjust--;
			}
			
			length--;
		}
		System.out.println("There are " + adjacencyMatrix[1][0] + " runs.");
	}
	

	private int[][] create2DArray(Queue<Integer> data) {
		// TODO Auto-generated method stub
		int junctions = data.poll() + 2;
		int legs = data.poll();
		int[][] adjacencyMatrix = new int[junctions][junctions];
		while (! data.isEmpty()) {
			adjacencyMatrix[data.poll()][data.poll()] ++;
		}
		return adjacencyMatrix;
	}

	
	/**
	 * Does the Necessary Methods to Aquire Data
	 * @return Queue <Integer> data
	 */
	public Queue<Integer> getData() {
		System.out.println("Please Select a File.");
		File file = getFile();
		Queue <Integer> data = readFile(file);
		return data;
		
	}
	
	/**
	 * Takes the File and Reads all of the Information and places it into a workable array.
	 * @param file
	 * @return
	 */
	private Queue<Integer> readFile(File file) {
		// TODO Auto-generated method stub
		Scanner scnr;
		try {
			scnr = new Scanner(file);
			Queue<Integer> data = new LinkedList<Integer>();
			while(scnr.hasNext() == true)
				data.add(scnr.nextInt());
			
			return data;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}

	/**
	 * Gets File From User With JFile Chooser
	 * @return
	 */
	private File getFile() {
		// TODO Auto-generated method stub
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
}
