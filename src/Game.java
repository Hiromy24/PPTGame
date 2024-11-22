// src/Game.java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Game extends Thread {
    private Socket socketToPlayer1;
    private InputStreamReader input1;
    private BufferedReader bf1;
    private PrintStream output1;

    private Socket socketToPlayer2;
    private InputStreamReader input2;
    private BufferedReader bf2;
    private PrintStream output2;

    private int player1Victories = 0;
    private int player2Victories = 0;

    private static final Map<String, String> winningConditions = new HashMap<>();
    static {
        winningConditions.put("Rock", "Scissors");
        winningConditions.put("Paper", "Rock");
        winningConditions.put("Scissors", "Paper");
    }

    public Game() {}

    @Override
    public void run() {
        try {
            while (player1Victories < 3 && player2Victories < 3) {
                String player1Choice = bf1.readLine();
                String player2Choice = bf2.readLine();

                if (player1Choice.equals(player2Choice)) {
                    output1.println("It's a tie!");
                    output2.println("It's a tie!");
                } else if (winningConditions.get(player1Choice).equals(player2Choice)) {
                    output1.println("WIN");
                    output2.println("LOSE");
                    player1Victories++;
                } else {
                    output1.println("LOSE");
                    output2.println("WIN");
                    player2Victories++;
                }
            }
            output1.println("Partida Finalizada!");
            output2.println("Partida Finalizada!");
            output1.flush();
            output2.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSocket(Socket socket) throws IOException {
        if (socketToPlayer1 == null) {
            socketToPlayer1 = socket;
            input1 = new InputStreamReader(socket.getInputStream());
            bf1 = new BufferedReader(input1);
            output1 = new PrintStream(socket.getOutputStream());
        } else {
            socketToPlayer2 = socket;
            input2 = new InputStreamReader(socket.getInputStream());
            bf2 = new BufferedReader(input2);
            output2 = new PrintStream(socket.getOutputStream());
            output1.println("Partida Encontrada!");
            output2.println("Partida Encontrada!");
            output1.flush();
            output2.flush();
        }
    }

    public void closeSockets() {
        try {
            if (socketToPlayer1 != null && !socketToPlayer1.isClosed()) {
                socketToPlayer1.close();
            }
            if (socketToPlayer2 != null && !socketToPlayer2.isClosed()) {
                socketToPlayer2.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}