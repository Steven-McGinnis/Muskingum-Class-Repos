import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String request;
    private Map<String, String> headers; //Trying a Hash Map This time lol
    int httpPort = 443;

    public HttpRequest(Socket clientSocket) {
        headers = new HashMap<>();
        try {
            BufferedReader read = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            request = read.readLine();
            String headerStuff;
            //Get all the headers
            while((headerStuff = read.readLine()) != null && headerStuff.length() != 0) {
                String[] header = headerStuff.split(": ");
                headers.put(header[0], header[1]);
            }
        } catch (IOException e) {
            System.out.println("Error reading request from client: " + e.getMessage());
        }
    }

    public HttpResponse send() {
        HttpResponse resp = null;
    
        // Check if the request is a GET request
        if (!headers.containsKey("Host")) {
            System.out.println("Error no Host?");
            return null;
        }
    
        // Grab the host name
        String whosHosting = headers.get("Host");
    
        // Try to connect to the host to proxy
        try (Socket serverSocket = new Socket(whosHosting, httpPort)) {
            PrintWriter outToServer = new PrintWriter(serverSocket.getOutputStream());
            // Send the request
            outToServer.println(request);
            // Send all the headers
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                outToServer.println(entry.getKey() + ": " + entry.getValue());
            }
            // Add a blank line to the end of the request
            outToServer.println();
            // Flush the output stream
            outToServer.flush();
            // Create a Response object with the server socket
            resp = new HttpResponse(serverSocket);
        } catch (IOException e) {
            System.out.println("Error connecting to host: " + e.getMessage());
        }
        return resp; // Add this return statement
    }
    
}
