import java.util.Scanner;

public class PeerToPeer {
    private boolean isMaster = false;

    /**
     * Main Method Begins the Program
     * 
     * @param args
     */
    public static void main(String[] args) {
        PeerToPeer p2p = new PeerToPeer(); // Create PeerToPeer Object
        p2p.go(p2p); // Run PeerToPeer
    }

    /**
     * Driver Code for PeerToPeer
     *
     * @return void
     */
    private void go(PeerToPeer p2p) {
        this.isMaster = p2p.getPeerRole(); // Get Peer Role From User
        PeerCommunicationThread commThread = new PeerCommunicationThread(this.isMaster);
        commThread.start();
    }

    /**
     * Get Peer Role From User
     *
     * @return boolean - True If Master Peer, False If Peer
     */
    private boolean getPeerRole() {
        Scanner scnr = new Scanner(System.in);                                      // Create Scanner
        System.out.println("Is this peer the master peer? (y/n)");                // Ask User What Peer Role Is
        String input = scnr.nextLine();                                             // Read User Input with Scanner
        if (input.equalsIgnoreCase("y")) {                            // If User Input Is Yes
            return true;                                                            // Return True
        } else if (input.equalsIgnoreCase("n")) {                     // If User Input Is No
            return false;                                                           // Return False
        } else {                                                                    // If User Input Is Invalid
            System.out.println("Invalid Input");                                  // Print Error Message
            return getPeerRole();                                                   // Recursively Call Method Until Valid Input Is Given
        }
    }

}
