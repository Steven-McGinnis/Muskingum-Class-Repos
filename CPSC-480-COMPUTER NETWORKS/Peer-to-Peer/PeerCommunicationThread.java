import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class PeerCommunicationThread extends Thread {
    private boolean isMaster = false;
    ArrayList<Peer> clients = new ArrayList<Peer>();
    ArrayList<Peer> masterPeerList = new ArrayList<Peer>();
    ArrayList<TODO> todoList = new ArrayList<TODO>();
    private ServerSocket serverSocket = null;
    private int port = 5555;
    private Socket masterSocket = null;
    private Socket targetPeer = null;

    public PeerCommunicationThread(boolean isMaster) {
        this.isMaster = isMaster;
    }

    /**
     * Creates a ServerSocket on the port specified by the user.
     * 
     * @param Sets the serverSocket to the ServerSocket created.
     */
    public void run() {
        if (this.isMaster) {
            masterSetup();
        } else {
            peerSetup();
        }
        System.out.println("Beginning Peer Operations");
        UIThread uiThread = new UIThread(this);yield();
        uiThread.start();
        runServer();
    }

    /**
     * Creates a ServerSocket on the port specified by the user.
     * 
     * @param Sets the serverSocket to the ServerSocket created.
     */
    private void peerSetup() {
        generatePort();
        createServerSocket();
        connectToMaster();
        sendToMaster("CONNECT " + this.port);
        String targetPeer = receiveFromMaster();
        closeMasterSocket();
        connectToTargetPeer(targetPeer);
        sendAttachMessage();
        String peerResponse = receiveFromPeer();
        addPeerToClients(peerResponse);
    }

    private void addPeerToClients(String peerResponse) {
        if(peerResponse.equalsIgnoreCase("ATTACHOK")){
            System.out.println("Peer Attached");
            Peer peer = new Peer();
            peer.setClient(targetPeer);
            peer.setIpAddress(targetPeer.getInetAddress().getHostAddress());
            peer.setPort(targetPeer.getPort());
            clients.add(peer);
        } else {
            System.out.println("Peer Not Attached");
            System.out.println("Peer Response: " + peerResponse);
            System.exit(-1);
        }
    }

    private void closeMasterSocket() {
        try {
            this.masterSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing master socket");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void sendAttachMessage() {
        try {
            PrintWriter out = new PrintWriter(targetPeer.getOutputStream(), true);                  // Create Output Stream
            out.println("ATTACH");                                                                          // Send ATTACH Message to Peer
            out.flush();                                                                                      // Flush Output Stream
        } catch (IOException e) {
            System.err.println("Error sending attach message");                                             // Print Error Message
            e.printStackTrace();                                                                              // Print Stack Trace
            System.exit(-1);                                                                                  // Exit Program
        }
    }
    
    /**
     * Creates a ServerSocket on the port specified by the user.
     * 
     * @param Sets the serverSocket to the ServerSocket created.
     */
    private void connectToTargetPeer(String targetPeer) {
        String[] targetPeerInfo = targetPeer.split(" ");                                                // Split the message into the command and the target peer
    
        if (targetPeerInfo.length != 2 || !targetPeerInfo[0].equalsIgnoreCase("CONNECT")) {     // If the message is not in the correct format
            System.err.println("Invalid target peer format");                                               // Print error message
            System.exit(-1);                                                                                  // Exit program
        }
    
        String[] ipAndPort = targetPeerInfo[1].split(":");                                              // Split the target peer into the IP address and port number
        if (ipAndPort.length != 2) {                                                                          // If the IP address and port number are not in the correct format
            System.err.println("Invalid IP and port format");                                               // Print error message
            System.exit(-1);                                                                                  // Exit program
        }
    
        String ipAddress = ipAndPort[0];                                                                      // Set the IP address
        int port = Integer.parseInt(ipAndPort[1]);                                                            // Set the port number

        try { 
            Socket targetPeerSocket = new Socket(ipAddress, port);                                            // Create Socket
            this.targetPeer = targetPeerSocket;
        } catch (IOException e) {                                                                             // If Error Connecting to Target Peer
            System.err.println("Could not connect to target peer");                                         // Print Error Message
            e.printStackTrace();                                                                              // Print Stack Trace
            System.exit(-1);                                                                                  // Exit Program
        }
    }
    

    /**
     * Creates a ServerSocket on the port specified by the user.
     * 
     * @param Sets the serverSocket to the ServerSocket created.
     * @return 
     */
    private String receiveFromMaster() {
        try { 
            BufferedReader in = new BufferedReader(new InputStreamReader(masterSocket.getInputStream())); // Create Input Stream
            String message = in.readLine();                                                               // Read Message
            if (message != null) {                                                                        // If Message Is Not Null
                return message;                                                                           // Return Message
            }
        } catch (IOException e) {                                                                         // If Error Receiving Message
            System.err.println("Error receiving message from master peer");                             // Print Error Message
            e.printStackTrace();                                                                          // Print Stack Trace
            System.exit(-1);                                                                              // Exit Program
        }
        return null;                                                                                      // Return Null
    }
    
    /**
     * Creates a ServerSocket on the port specified by the user.
     * @return String - The message received from the peer.
     */
    private String receiveFromPeer() {
        try { 
            BufferedReader in = new BufferedReader(new InputStreamReader(targetPeer.getInputStream()));   // Create Input Stream
            String message = in.readLine();                                                               // Read Message
            if (message != null) {                                                                        // If Message Is Not Null
                return message;                                                                           // Return Message
            }
        } catch (IOException e) {                                                                         // If Error Receiving Message
            System.err.println("Error receiving message from peer");                                    // Print Error Message
            e.printStackTrace();                                                                          // Print Stack Trace
            System.exit(-1);                                                                              // Exit Program
        }
        return null;                                                                                      // Return Null
    }
    

    /**
     * Connects to the master peer. Asks the user for the IP address and port number of the master peer.
     * 
     *  @param Sets the masterSocket to the socket created by the connection.
     */
    private void connectToMaster() {
        Scanner scnr = new Scanner(System.in);                                          // Create Scanner
        System.out.println("Enter the master peer IP address: ");                     // Ask User What Peer Role Is
        String ipAddress = scnr.nextLine();                                             // Read User Input with Scanner
        System.out.println("Enter the master peer port number: ");                    // Ask User What Peer Role Is
        int port = scnr.nextInt();                                                      // Read User Input with Scanner                                                                 // Close Scanner
        try {
            Socket masterSocket = new Socket(ipAddress, port);                          // Create Socket
            this.masterSocket = masterSocket;                                           // Set Master Socket
        } catch (IOException e) {
            System.err.println("Could not connect to master peer");                   // Print Error Message
            System.exit(-1);                                                            // Exit Program
        }
    }

    /**
     * Sends to the master peer upon connection Who you are and what port you are listening on.
     * 
     * @param message
     */
    private void sendToMaster(String message) {
        try {
            PrintWriter out = new PrintWriter(masterSocket.getOutputStream(), true);                 // Create Output Stream
            out.println(message);                                                                              // Send Message to Master Peer
        } catch (IOException e) {
            System.err.println("Error sending message to master peer");                                      // Print Error Message
            e.printStackTrace();                                                                               // Print Stack Trace
        }
    }

    /**
     * Run the Master Setup which involves creating a port and a server socket. And Prints the Information to the user
     */
    private void masterSetup() {
        generatePort(); // Generate Port
        createServerSocket(); // Start Server
        printConnectionInformation(); // Print Connection Information
    }

    /**
     * Generate a random port number between 1024 and 49151.
     *
     * @return void
     */
    private void generatePort() {
        Random rand = new Random(); // Create Random Object
        int min = 1024; // Set Minimum Port Number
        int max = 49151; // Set Maximum Port Number
        this.port = rand.nextInt((max - min) + 1) + min; // Generate Random Port Number
    }

    /**
     * Create a server socket on the specified port.
     *
     * @return void
     */
    private void createServerSocket() {
        try {
            serverSocket = new ServerSocket(this.port);                                     // Create Server Socket
            serverSocket.setSoTimeout(50);                                          // 50 ms timeout for no connection
            System.out.println("Listening on port: " + this.port);                          // Print Port Number
        } catch (IOException e) {                                                           // If Port Is Already In Use
            System.err.println("Could not listen on port: " + this.port);                   // Print Error Message
            System.exit(-1);                                                                // Exit Program
        }
    }

    /**
     * Print Connection Information for the user to see so they can make a connectin to the master peer.
     *
     * @return void
     */
    private void printConnectionInformation() {
        try {
            String ipAddress = InetAddress.getLocalHost().getHostAddress();             // Get Local IP Address
            System.out.println("Master peer IP address: " + ipAddress);                 // Print IP Address
            System.out.println("Master peer port number: " + this.port);                // Print Port Number
        } catch (UnknownHostException e) {                                              // If IP Address Cannot Be Found
            System.err.println("Error getting local IP address");                     // Print Error Message
            e.printStackTrace();                                                        // Print Stack Trace
        }
    }

    /**
     * Run the server. This method will run forever until the server is stopped.
     *
     * @return void
     */
    private void runServer() {
        do {
            Socket client = null;
            try {
                client = serverSocket.accept();
            } catch (SocketTimeoutException e) {
                // Do nothing, this just means no client tried to connect
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }

            if (client != null) {
                Peer peer = new Peer();
                peer.setClient(client);
                peer.setIpAddress(client.getInetAddress().getHostAddress());
                peer.setPort(client.getPort());
                clients.add(peer);
                if(isMaster){
                    masterPeerList.add(peer);
                }
            }

            int i = 0;
            while (i < clients.size()) {
                if (processClient(clients.get(i)))
                    i++;
            }
        } while (true);

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean processClient(Peer peer) {
        BufferedReader inFromClient = null; // Create Input Stream
        String input = null; // Create Input String
    
        try {
            inFromClient = new BufferedReader(new InputStreamReader(peer.getInputStream())); // Create Input Stream
    
            if (inFromClient.ready()) {                                                                                    // Check if the input stream is ready
                input = inFromClient.readLine();                                                                           // Read Input Stream
                if (input != null) {                                                                                       // If Input Stream Is Not Null
                    System.out.println("Received: " + input);                                                              // Print Input Stream
                    Scanner scanner = new Scanner(input);                                                                  // Create Scanner
                    String command = scanner.next();                                                                       // Read Command

                if (command.equalsIgnoreCase("CONNECT") && isMaster) {                                       // If Command Is CONNECT
                    int port = Integer.parseInt(scanner.next());                                                           // Read Port Number from peer
                    peer.setPort(port);                                                                                    // Set Port Number for Peer

                    if(masterPeerList.size() == 1){                                                                        // If There Is Only One Peer
                        PrintWriter outToClient = new PrintWriter(peer.getOutputStream(), true);                 // Create Output Stream
                        outToClient.println("CONNECT " + InetAddress.getLocalHost().getHostAddress() + ":" 
                                + this.port );                                                                             // Send CONNECT Message to Peer  

                        clients.remove(peer);                                                                              // Remove Peer From List
                    }

                    if (masterPeerList.size() > 1) {                                                                       // If There Is More Than One Peer
                        Random rand = new Random();                                                                        // Create Random Object
                        int randomIndex = rand.nextInt(masterPeerList.size() - 1);                                         // Generate Random Index
                        Peer randomExistingPeer = masterPeerList.get(randomIndex);
                        PrintWriter outToClient = new PrintWriter(peer.getOutputStream(), true);                 // Create Output Stream
                        outToClient.println("CONNECT " + randomExistingPeer.getIpAddress() + ":" + randomExistingPeer.getPort()); // Send CONNECT Message to Peer
                    }
                }

                if (command.equalsIgnoreCase("ATTACH")) {
                    PrintWriter outToClient = new PrintWriter(peer.getOutputStream(), true);                 // Create Output Stream
                    outToClient.println("ATTACHOK");
                }

                if (command.equalsIgnoreCase("UPDATE")) {
                    UUID msgID = UUID.fromString(scanner.next());
                    String listName = scanner.next();
                    createList(listName);
                }
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            clients.remove(peer);
            return false;
        }
        return true;
    }

    private void printClientList(){
        for (int i = 0; i < clients.size(); i++) {
            System.out.println("Client " + i + ": " + clients.get(i).getIpAddress() + ":" + clients.get(i).getPort());
        }
    }

    private void printMasterList(){
        for (int i = 0; i < masterPeerList.size(); i++) {
            System.out.println("Client " + i + ": " + masterPeerList.get(i).getIpAddress() + ":" + masterPeerList.get(i).getPort());
        }
    }

    public void createList(String listName) {
        TODO newList = new TODO(listName);
        UUID msgID = UUID.randomUUID();
        todoList.add(newList);
        for(int i = 0; i < clients.size(); i++){
            try (PrintWriter outToClient = new PrintWriter(clients.get(i).getOutputStream(), true)) {
                outToClient.println("UPDATE " + msgID + " " + listName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showLists() {
        for (int i = 0; i < todoList.size(); i++) {
            System.out.println("List " + i + ": " + todoList.get(i).getName() + " " + todoList.get(i).getId());
        }
    }
}
