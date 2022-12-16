import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DatabaseNode {
    private final String NODE_TCP_PORT;
    private String NODE_TCP_ADDRESS;
    private String PREV_NODE_TCP_PORT;
    private String PREV_NOTE_TCP_ADDRESS;
    private int key;
    private int value;
    private ServerSocket nodeServerSocket;
    private Socket nodeSocket;
    private static int nodeID = 0;
    public DatabaseNode(String NODE_TCP_PORT, String NODE_TCP_ADDRESS, String PREV_NODE_TCP_PORT, String PREV_NOTE_TCP_ADDRESS, int key, int value) {
        this.NODE_TCP_PORT = NODE_TCP_PORT;
        this.NODE_TCP_ADDRESS = NODE_TCP_ADDRESS;
        this.PREV_NODE_TCP_PORT = PREV_NODE_TCP_PORT;
        this.PREV_NOTE_TCP_ADDRESS = PREV_NOTE_TCP_ADDRESS;
        this.key = key;
        this.value = value;
        nodeID += 1;
    }

    public void startNode(){
        try {
            System.out.println("Starting new node on port " + NODE_TCP_PORT + "...");
            nodeServerSocket = new ServerSocket(Integer.parseInt(NODE_TCP_PORT));

            PrintWriter output = new PrintWriter(nodeSocket.getOutputStream());
            BufferedReader input = new BufferedReader(new InputStreamReader(nodeSocket.getInputStream()));

            System.out.println("New node created \n Listening for connections...");
            nodeSocket = nodeServerSocket.accept();

            System.out.println("Connected!");
            String inputLine, outputLine;

            while((inputLine = input.readLine()) != null){
                System.out.println(inputLine);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
