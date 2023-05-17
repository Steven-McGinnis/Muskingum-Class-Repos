import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ProxyServer {
    int port = 5555;
    public static void main(String[] args) {
        ProxyServer ps = new ProxyServer();                                // Create a ProxyServer object
        ps.go();                                                           // Start the server
    }

    private void go() { 
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Proxy server listening on port " + port);   // Print a message to let the user know the server is running

            while (true) {                                                  // Loop forever, listening for and handling incoming connections
                Socket clientSocket = serverSocket.accept();                // Wait for a connection from a client
                handleRequest(clientSocket);                                // Handle the request from the client
            }
        } catch (IOException e) {
            System.err.println("Error on port: " + port);                   // If we encounter an IOException, print an error message
            System.exit(-1);                                                // Exit the program
        }
    }

    /**
     * Handle a request from a client
     * @param clientSocket
     */
    private static void handleRequest(Socket clientSocket) {
        try {
            HttpRequest request = new HttpRequest(clientSocket); // Create the HttpRequest object
            HttpResponse response = request.send(); // Send the request and get a response back
            if (response != null) { // Check if the response is not null
                response.send(clientSocket); // Send the response to the client
            }
            clientSocket.close(); // Close the socket
        } catch (IOException e) { // If we encounter an IOException, print an error message
            System.err.println("Error handling request: " + e.getMessage()); // Print an error message
        }
    }
    
}
