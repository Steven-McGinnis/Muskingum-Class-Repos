import java.util.ArrayList;

public class Equipment {
	private ArrayList<String> Tools = new ArrayList<>();
	private ArrayList<Integer> ToolAmount = new ArrayList<>();

	public void addTool(String tool) {
		Tools.add(tool);
	}

	public void addToolAmount(int toolAmount) {
		ToolAmount.add(toolAmount);
	}

	public void printTools() {
		for (int i = 0; i < Tools.size(); i++) {
			System.out.println("Tool: " + Tools.get(i) + " " + ToolAmount.get(i));
		}
	}

	public int checkTool(String string) {
		// TODO Auto-generated method stub
		for (int i = 0; i < Tools.size(); i++) {
			if (string.equals(Tools.get(i))) {
				return i;
			}
		}
		return 0;
	}

	public boolean isAvailable(int number) {
		if (ToolAmount.get(number) <= 0) {
			return false;
		} else if (ToolAmount.get(number) > 0) {
			return true;
		}
		return false;
	}

	public void reserveTool(int i) {
		// TODO Auto-generated method stub
		int tool = ToolAmount.get(i);
		tool--;
		ToolAmount.set(i, tool);
	}

	public void returnTool(int i) {
		// TODO Auto-generated method stub
		int tool = ToolAmount.get(i);
		tool++;
		ToolAmount.set(i, tool);
	}

	public void printInfo() {
		for (int i = 0; i < Tools.size(); i++) {
			System.out.println("Tool: " + Tools.get(i) + " Amount: " + ToolAmount.get(i));
		}
	}
}
