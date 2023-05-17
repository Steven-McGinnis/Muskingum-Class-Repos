import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;

public class BinaryTreeModelTest {

	@Test
	public void testMakeTree() {
		//Setup 
		BinaryTreeModel model = new BinaryTreeModel();
		HeapSortController controller = new HeapSortController();
		PrintWriter output = null;
		try {
			output = new PrintWriter("file.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		output.println(12);
		output.println(9);
		output.println(32);
		output.println(62);
		output.println(72);
		output.println(86);
		output.println(42);
		output.close();
		File file = new File("file.txt");
		Scanner scnr = controller.scanner(file);
		//Method Execution
		ArrayList<Integer>BinaryTree = model.makeTree(scnr);
		//Verification
		System.out.println(BinaryTree);
		if(! BinaryTree.toString().equals("[12, 9, 32, 62, 72, 86, 42]"))
		fail("The File Created and Scanned did not print properly.");
	}

	@Test
	public void testMakeHeap() {
		//Setup
		BinaryTreeModel model = new BinaryTreeModel();
		ArrayList<Integer>binaryTree = new ArrayList<Integer>();
		binaryTree.add(12);
		binaryTree.add(9);
		binaryTree.add(35);
		binaryTree.add(1);
		binaryTree.add(38);
		binaryTree.add(6);
		binaryTree.add(8);
		binaryTree.add(16);
		binaryTree.add(4);
		//Method Execution
		binaryTree = model.makeHeap(binaryTree, 3);	
		//Verification
		if(! binaryTree.toString().equals("[35, 9, 12, 1, 38, 6, 8, 16, 4]"))
		fail("The String after Heapsorting Was not 16 9 12");
	}

	@Test
	public void testHeapSort() {
		//Setup
		BinaryTreeModel model = new BinaryTreeModel();
		ArrayList<Integer>binaryTree = new ArrayList<Integer>();
		binaryTree.add(12);
		binaryTree.add(9);
		binaryTree.add(35);
		
		//Method Execution
		binaryTree = model.heapSort(binaryTree);
		
		//Verification
		if(binaryTree.get(0) != 9)
		fail("The Binary Tree Did Not Get Reduced");
		
		if(model.getSortedTree().get(0) != 12) 
		fail("The Sorted Tree did not get the top of the binary tree");
	}

}
