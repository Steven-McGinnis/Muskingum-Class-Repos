import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Peer {
    private Socket client;                              // The connection to the other peer
    private int port;                                   // The port the peer is listening on
    private String ipAddress;                           // The IP address of the peer
    private boolean ready;                              // Indicates if the peer is ready to handle messages

    public Peer() {
        this.ready = false;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void close() throws IOException {
        if (client != null) {
            client.close();
        }
    }

    public InputStream getInputStream() throws IOException {
        return client.getInputStream();
    }

    
    public OutputStream getOutputStream() throws IOException {
        return client.getOutputStream();
    }

}
