package client;

public class StartClient {
    public static void main(String[] args) {
        ChatClient cc = new ChatClient("vps.gaxau.com", 50000);
        cc.run();
    }
}
