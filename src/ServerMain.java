import models.Server;

public class ServerMain {
    public static void main(String[] args) {
        Server server = new Server(2023);
        server.start();
    }
}
