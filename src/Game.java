import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Game extends Thread {
    private Socket socketToPlayer1;
    private Socket socketToPlayer2;
    private static final Map<String, String> winningConditions = new HashMap<>();

    static {
        winningConditions.put("Rock", "Scissors");
        winningConditions.put("Paper", "Rock");
        winningConditions.put("Scissors", "Paper");
    }

    public Game(Socket socketToPlayer1, Socket socketToPlayer2) {
        this.socketToPlayer1 = socketToPlayer1;
        this.socketToPlayer2 = socketToPlayer2;
    }

    @Override
    public void run() {
        try {
            InputStreamReader input1 = new InputStreamReader(socketToPlayer1.getInputStream());
            BufferedReader bf1 = new BufferedReader(input1);
            PrintStream output1 = new PrintStream(socketToPlayer1.getOutputStream());

            InputStreamReader input2 = new InputStreamReader(socketToPlayer2.getInputStream());
            BufferedReader bf2 = new BufferedReader(input2);
            PrintStream output2 = new PrintStream(socketToPlayer2.getOutputStream());

            String player1Choice = bf1.readLine();
            String player2Choice = bf2.readLine();

            output1.println("Player 1: " + player1Choice);
            output2.println("Player 2: " + player2Choice);

            if (player1Choice.equals(player2Choice)) {
                System.out.println("It's a tie!");
            } else if (winningConditions.get(player1Choice).equals(player2Choice)) {
                System.out.println("Player 1 wins");
            } else {
                System.out.println("Player 2 wins");
            }

            socketToPlayer1.close();
            socketToPlayer2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}