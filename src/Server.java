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
    private static Map<Socket, Integer> waitingPlayers = new HashMap<>();
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
                if (bf.readLine().equals("true")){
                    Game game =new Game();
                    String psw = bf.readLine();
                    gameFinder(game, player, Integer.parseInt(psw));
                }else{
                    waitingPlayers.put(player, Integer.valueOf(bf.readLine()));
                }
            }
        } catch (IOException e) {
            System.err.println("SERVER: Error starting server: " + e.getMessage());
        }
    }
    private static void gameFinder( Game game,Socket gameOwner, int psw){
        new Thread(() -> {
            try {
                int gamePsw = 0;
                if (psw != 1 ){
                    gamePsw = psw;
                    game.setGamePsw(psw);
                    gamePsw = game.getGamePsw();
                    game.addSocket(gameOwner);
                }

                while (game.playersConected() !=2) {
                    for (Map.Entry<Socket, Integer> entry : waitingPlayers.entrySet()) {
                        Socket player = entry.getKey();
                        InputStreamReader inputActual = new InputStreamReader(player.getInputStream());
                        BufferedReader bfActual = new BufferedReader(inputActual);
                        if (gamePsw == entry.getValue()) {
                            game.addSocket(player);
                            waitingPlayers.remove(player);
                        }
                    }
                }

                synchronized (games) {
                    games.add(game);
                    game.start();
                    gameNumber++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
       }).start();
    }

}