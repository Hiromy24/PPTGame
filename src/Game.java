import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Game extends Thread {
    //region Socket1
    private Socket socketToPlayer1;
    private InputStreamReader input1;
    private BufferedReader bf1;
    private PrintStream output1;
    //endregion
    //region Socket2
    private Socket socketToPlayer2;
    private InputStreamReader input2;
    private BufferedReader bf2;
    private PrintStream output2;
    //endregion
    //WinConditions
    private static final Map<String, String> winningConditions = new HashMap<>();
    static {
        winningConditions.put("Rock", "Scissors");
        winningConditions.put("Paper", "Rock");
        winningConditions.put("Scissors", "Paper");
    }

    public Game() {

    }

    public Game(Socket socketToPlayer1, Socket socketToPlayer2) {
        this.socketToPlayer1 = socketToPlayer1;
        this.socketToPlayer2 = socketToPlayer2;
    }

    @Override
    public void run() {
        try {

            int rounds = 0;
            while (rounds != 3){
                String player1Choice = bf1.readLine();
                String player2Choice = bf2.readLine();

            if (player1Choice.equals(player2Choice)) {
                output1.println("It's a tie!");
                output2.println("It's a tie!");
            } else if (winningConditions.get(player1Choice).equals(player2Choice)) {
                output1.println("WIN");
                output2.println("LOSE");
            } else {
                output1.println("LOSE");
                output2.println("WIN");
            }
                output1.flush();
                output2.flush();
                rounds++;
            }
            socketToPlayer1.close();
            socketToPlayer2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSocket(Socket socket) throws IOException {
        if (socketToPlayer1 == null){
            socketToPlayer1 = socket;
            input1 = new InputStreamReader(socketToPlayer1.getInputStream());
            bf1 = new BufferedReader(input1);
            output1 = new PrintStream(socketToPlayer1.getOutputStream());
        }else {
            socketToPlayer2 = socket;
            input2 = new InputStreamReader(socketToPlayer2.getInputStream());
            bf2 = new BufferedReader(input2);
            output2 = new PrintStream(socketToPlayer2.getOutputStream());
            output1.println("Partida Encontrada!");
            output2.println("Partida Encontrada!");
            output1.flush();
            output2.flush();
        }
    }
}