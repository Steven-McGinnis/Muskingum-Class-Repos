import java.util.ArrayList;

public class TextViewController {

	/**
	 * Takes the Sorted Tree and Prints it Out
	 * @param sortedTree
	 */
	public void printTree(ArrayList<Integer> sortedTree) {
		for (int i = 0; i < sortedTree.size(); i++) {
			System.out.print(sortedTree.get(i) + " ");
		}
	}
}
