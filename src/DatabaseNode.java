import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DatabaseNode {
    private final String NODE_TCP_PORT;
    private final String NODE_TCP_ADDRESS;
    private final String PREV_NODE_TCP_PORT;
    private final String PREV_NOTE_TCP_ADDRESS;
    private final int key;
    private final int value;
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

            System.out.println("New node created \n Listening for connections...");
            nodeSocket = nodeServerSocket.accept();

            System.out.println();
            System.out.println("Connected!");

            manageInput();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void manageInput() throws IOException {
        PrintWriter output = new PrintWriter(nodeSocket.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(nodeSocket.getInputStream()));

        String inputLine;


        while((inputLine = input.readLine()) != null){

            System.out.println("Message recieved: ");
            System.out.println(inputLine);
            System.out.println();

            String[] inputValues = inputLine.split(" ");

            if (inputValues[0].equals("get-value")) {
                sendMessage(output, getValue(Integer.parseInt(inputValues[1])));
            }
        }
    }

    private String getValue(int key){
        if(key == this.key){
            System.out.println("The key is correct!");
            return String.valueOf(this.value);
        }else{
            return "ERROR: The key is not correct!";
        }
    }

    private void sendMessage(PrintWriter output, String message){
        System.out.println("Sending response...");
        System.out.println();
        output.println(message);
        System.out.println("Message sent!");
    }

}
