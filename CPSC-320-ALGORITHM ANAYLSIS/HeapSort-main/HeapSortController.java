import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class HeapSortController {

	/**
	 * Main Method
	 * @param args
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
		HeapSortController controller = new HeapSortController();
		controller.go();

	}

	/**
	 * Driver Code for the Program
	 */
	private void go() {
		// TODO Auto-generated method stub
		TextViewController view = new TextViewController();
		BinaryTreeModel model = new BinaryTreeModel();
		File file = getFile();
		Scanner scnr = scanner(file);
		ArrayList<Integer> binaryTree = model.makeTree(scnr);

		//While the Binary Tree Has Roots Heap and Pop
		while (! binaryTree.isEmpty()){
			binaryTree = model.makeHeap(binaryTree, model.getLength());
			model.heapSort(binaryTree);
		}

		view.printTree(model.getSortedTree());

	}

	/**
	 * Creates a scanner that is surrounded with a try catch block
	 * @param file
	 * @return scnr
	 */
	public Scanner scanner(File file) {
		Scanner scnr = null;
		// TODO Auto-generated method stub
		try {
			scnr = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scnr;
	}

	/**
	 * Asks for a file using JFileChooser
	 * @return File
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

