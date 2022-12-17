import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final DatabaseNode node;

    /**
     * This class handles multithreaded communication between client and a node and between other nodes
     * @param node node to handle communication on
     */
    public ClientHandler(DatabaseNode node){
        this.node = node;
    }

    @Override
    public void run() {
        try{

            PrintWriter output = new PrintWriter(this.node.getNodeSocket().getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(this.node.getNodeSocket().getInputStream()));

            String inputLine;


            while((inputLine = input.readLine()) != null){

                System.out.println("Message recieved: ");
                System.out.println(inputLine);
                System.out.println();

                String[] inputValues = inputLine.split(" ");

                if (inputValues[0].equals("get-value")) {
                    sendMessage(output, getValue(Integer.parseInt(inputValues[1])));
                }

                if(inputValues[0].equals("new-record")){
                    String[] pair = inputValues[1].split(":");
                    this.node.setKey(Integer.parseInt(pair[0]));
                    this.node.setValue(Integer.parseInt(pair[1]));

                    System.out.println("The value has been set to <" + this.node.getKey() + ">:<" + this.node.getValue() +">");

                    sendMessage(output, "OK");

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getValue(int key){
        if(key == this.node.getKey()){
            System.out.println("The key is correct!");
            return String.valueOf(this.node.getValue());
        }else{
            return "ERROR: The key is not correct!";
        }
    }

    private void sendMessage(PrintWriter output, String message){
        System.out.println("Sending response...");
        System.out.println();
        output.println(message);
        System.out.println("Response sent");
    }
}
