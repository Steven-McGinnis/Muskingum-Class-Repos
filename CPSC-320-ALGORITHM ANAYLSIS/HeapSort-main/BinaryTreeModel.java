import java.util.ArrayList;
import java.util.Scanner;

public class BinaryTreeModel {
		private int Length;
	private ArrayList <Integer> SortedTree = new ArrayList<Integer>();
	
	/**
	 * Gets you the Length when polled
	 * @return Length
	 */
	public int getLength() {
		return Length;
	}

	/**
	 * Sets the Length of the Binary Tree
	 * @param length
	 */
	public void setLength(int length) {
		Length = length;
	}
	

	/**
	 * Makes a Binary Tree Array from a file
	 * @param input
	 * @return binaryTree
	 */
	public ArrayList<Integer> makeTree (Scanner input) {
		// TODO Auto-generated method stub
		ArrayList<Integer> binaryTree = new ArrayList<Integer>();
		while(input.hasNext()) {
			binaryTree.add(input.nextInt());
		}
		setLength(binaryTree.size());
		return binaryTree;
	}
	
	/**
	 * 
	 * @return SortedTree
	 */
	public ArrayList<Integer> getSortedTree() {
		return SortedTree;
	}

	/**
	 * Sorts the Various Nodes in the Binary Tree
	 * @param binaryTree
	 * @param N Length
	 * @return binaryTree
	 */
	public ArrayList<Integer> makeHeap (ArrayList<Integer> binaryTree, int N) {
		for (int i = N / 2 - 1; i>= 0; i--) {
			int largest = i;
			int leftChild = 2 * i + 1;
			int rightChild = 2 * i + 2;
				
			if(leftChild < N && binaryTree.get(leftChild) > binaryTree.get(largest))
				largest = leftChild;
	
			if(rightChild < N && binaryTree.get(rightChild) > binaryTree.get(largest))
				largest = rightChild;
		
			if(largest != i) {
				int swap = binaryTree.get(i);
				int swap2 = binaryTree.get(largest);
				binaryTree.set(i, swap2);
				binaryTree.set(largest, swap);
			}	
		}
		return binaryTree;
	}
	
	/**
	 * HeapSorts the top Root and adds it to the SortedTree then Reduces the Length on Binary
	 * @param binaryTree
	 * @return binaryTree
	 */
	public ArrayList<Integer> heapSort (ArrayList<Integer> binaryTree) {
		SortedTree.add(binaryTree.get(0));
		binaryTree.remove(0);
		setLength(binaryTree.size());
		return binaryTree;
	}
}

