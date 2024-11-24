import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PUERTO = 2024;

    public static void main(String[] args) {
        System.out.println("            SERVER APP            ");
        System.out.println("----------------------------------");
        InetSocketAddress sa = new InetSocketAddress(PUERTO);

        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(sa);
            while (true) {
                System.out.println("SERVER: Waiting for request on port " + PUERTO);
                Socket socketToPlayer1 = serverSocket.accept();
                System.out.println("SERVER: Player 1 connected");

                Socket socketToPlayer2 = serverSocket.accept();
                System.out.println("SERVER: Player 2 connected");

                Game game = new Game(socketToPlayer1, socketToPlayer2);
                game.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}