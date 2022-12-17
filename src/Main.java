public class Main {
    public static void main(String[] args) {
        DatabaseNode dbn = new DatabaseNode(
                "9997",
                "localhost",
                1,
                2
        );

        dbn.startNode();
    }
}