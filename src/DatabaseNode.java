import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabaseNode {
    private final String NODE_TCP_PORT;
    private final String NODE_TCP_ADDRESS;
    private int key;
    private int value;
    private ServerSocket nodeServerSocket;
    private Socket nodeSocket;
    private static int nodeID = 0;

    /**
     * Database node representation
     * @param NODE_TCP_PORT tcp port of this node
     * @param NODE_TCP_ADDRESS tcp addess of this node
     * @param key key to unlock the value
     * @param value value saved by the node
     */
    public DatabaseNode(String NODE_TCP_PORT, String NODE_TCP_ADDRESS, int key, int value) {
        this.NODE_TCP_PORT = NODE_TCP_PORT;
        this.NODE_TCP_ADDRESS = NODE_TCP_ADDRESS;
        this.key = key;
        this.value = value;
        nodeID += 1;
    }

    /**
     * Runs the node
     */
    public void startNode(){
        try {
            System.out.println("Starting new node on port " + NODE_TCP_PORT + "...");
            nodeServerSocket = new ServerSocket(Integer.parseInt(NODE_TCP_PORT));

            System.out.println("New node created \n Listening for connections...");
            nodeSocket = nodeServerSocket.accept();

            System.out.println();
            System.out.println("Connected!");

            ClientHandler handler = new ClientHandler(this);

            new Thread(handler).start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Socket getNodeSocket() {
        return nodeSocket;
    }
}
