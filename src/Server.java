import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final int PUERTO = 2024;
    private static final List<Game> games = new ArrayList<>();
    private static int gameNumber = 0;

    public static void main(String[] args) {
        System.out.println("            SERVER APP            ");
        System.out.println("----------------------------------");
        InetSocketAddress sa = new InetSocketAddress(PUERTO);

        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(sa);
            while (true) {
                Game game = new Game();
                System.out.println("SERVER: Waiting for request on port " + PUERTO);
                try {
                    Socket socketToPlayer1 = serverSocket.accept();
                    System.out.println("SERVER: Player 1 connected");
                    game.addSocket(socketToPlayer1);

                    Socket socketToPlayer2 = serverSocket.accept();
                    System.out.println("SERVER: Player 2 connected");
                    game.addSocket(socketToPlayer2);

                    synchronized (games) {
                        games.add(game);
                        game.start();
                        gameNumber++;
                    }
                } catch (IOException e) {
                    System.err.println("SERVER: Error handling player connection: " + e.getMessage());
                    game.closeSockets();
                }
            }
        } catch (IOException e) {
            System.err.println("SERVER: Error starting server: " + e.getMessage());
        }
    }
}