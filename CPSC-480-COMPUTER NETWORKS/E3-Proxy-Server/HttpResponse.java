import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private String statusLine;
    private Map<String, String> headers;
    private byte[] body;

    public HttpResponse(Socket serverSocket) {
        headers = new HashMap<>();
        try {
            DataInputStream dataInStream = new DataInputStream(serverSocket.getInputStream());
            statusLine = dataInStream.readLine();
            String headerLine;
            // Get all the headers
            while (!(headerLine = dataInStream.readLine()).equals("")) {
                String[] header = headerLine.split(": ");
                headers.put(header[0], header[1]);
            }
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            body = new byte[contentLength];
            dataInStream.readFully(body);
        } catch (IOException e) {
            System.err.println("Error reading response: " + e.getMessage());
        }
    }

    public void send(Socket clientSocket) {
        try {
            DataOutputStream dataOutStream = new DataOutputStream(clientSocket.getOutputStream());
            // Send the status line
            dataOutStream.writeBytes(statusLine + "\r\n");
            // Send all the headers
            for (String headerName : headers.keySet()) {
                dataOutStream.writeBytes(headerName + ": " + headers.get(headerName) + "\r\n");
            }
            // Add a blank line to the end of the headers
            dataOutStream.writeBytes("\r\n");
            // Send the body
            dataOutStream.write(body);
            // Flush the output stream
            dataOutStream.flush();
        } catch (IOException e) {
            System.err.println("Error sending response: " + e.getMessage());
        }
    }
}
