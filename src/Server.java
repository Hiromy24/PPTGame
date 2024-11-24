import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    public static final int PUERTO = 2024;
    private static final List<Game> games = new ArrayList<>();
    private static int gameNumber = 0;
    private static Map<Integer, Queue<Socket>> waitingPlayers = new HashMap<>();
    public static void main(String[] args) {
        System.out.println("            SERVER APP            ");
        System.out.println("----------------------------------");
        InetSocketAddress sa = new InetSocketAddress(PUERTO);

        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(sa);
            while (true) {

                Socket player = serverSocket.accept();
                InputStreamReader input = new InputStreamReader(player.getInputStream());
                BufferedReader bf = new BufferedReader(input);
                new Thread(() -> {
                    int psw = 0;
                    try {
                        psw = Integer.parseInt(bf.readLine());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    synchronized (waitingPlayers) {
                        waitingPlayers.putIfAbsent(psw, new LinkedList<>());
                        waitingPlayers.get(psw).add(player);
                    }
                    if (waitingPlayers.get(psw).size() >= 2){
                        Socket player1 = waitingPlayers.get(psw).poll();
                        Socket player2 = waitingPlayers.get(psw).poll();
                        Game game = new Game();
                        try {
                            game.addSocket(player1);
                            game.addSocket(player2);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        synchronized (games) {
                            games.add(game);
                            game.start();
                            gameNumber++;
                        }
                    }
                }).start();

            }
        } catch (IOException e) {
            System.err.println("SERVER: Error starting server: " + e.getMessage());
        }
    }


}