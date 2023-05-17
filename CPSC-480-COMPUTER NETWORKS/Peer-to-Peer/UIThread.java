import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class UIThread extends Thread {

    public UIThread(PeerCommunicationThread peerCommunicationThread) {
        this.peerCommunicationThread = peerCommunicationThread;
    }

    private PeerCommunicationThread peerCommunicationThread;
    private Map<String, List<String>> todoLists = new HashMap<>();

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Create a list");
            System.out.println("2. Rename a list");
            System.out.println("3. Delete a list");
            System.out.println("4. Add a todo item to a list");
            System.out.println("5. Remove a todo item from a list");
            System.out.println("6. Show all lists");
            System.out.println("7. Quit");

            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    createList(scanner);
                    break;
                case "2":
                    //renameList(scanner);
                    break;
                case "3":
                    //deleteList(scanner);
                    break;
                case "4":
                    //addTodoItem(scanner);
                    break;
                case "5":
                    // removeTodoItem(scanner);
                    break;
                case "6":
                    showLists();
                    break;
                case "7":
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showLists() {
        peerCommunicationThread.showLists();
    }

    private void createList(Scanner scanner) {
        System.out.print("Enter the name of the new list: ");
        String listName = scanner.nextLine();
        peerCommunicationThread.createList(listName);
        System.out.println("List created: " + listName);
    }

    // Similarly, modify other methods to interact with the PeerCommunicationThread
}
