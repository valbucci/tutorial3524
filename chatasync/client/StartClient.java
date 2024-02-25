package client;

public class StartClient {
    public static void main(String[] args) {
        ChatClient cc = new ChatClient("localhost", 50000);
        cc.run();
    }
}
