import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client extends Thread {
    public static final String IP_SERVER = "10.34.125.247";
    public static final int PUERTO = 2024;
    private PrintWriter output;
    private String option;
    private final UI userUI;
    private boolean is_finished = false;
    private int win = 0;

    public Client(UI actualUI) {
        userUI = actualUI;
    }

    public void run() {
        //region ClientPrint
        System.out.println("        CLIENT APP         ");
        System.out.println("-----------------------------------");
        //endregion
        InetSocketAddress connection = new InetSocketAddress(IP_SERVER, PUERTO);
        try {
            Socket socket = new Socket();
            socket.connect(connection);
            InputStreamReader input = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(input);

            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            output = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("CLIENT: Waiting response from server...");

            userUI.UpdateSearching(br.readLine());
            while (!is_finished && win < 3) {
                synchronized (this) {
                    while (option == null) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    output.println(option.trim());
                    option = null;
                }

                String is_win = br.readLine();
                switch (is_win) {
                    case "WIN" -> {
                        System.out.println("CLIENT: YOU WIN!");
                        win++;
                    }
                    case "LOSE" -> System.out.println("CLIENT: YOU LOSE!");
                    case "Partida Finalizada!" -> {
                        System.out.println("CLIENT: GAME FINISHED!");
                        is_finished = true;
                        resetGame();
                        socket.close();
                    }
                    default -> System.out.println("CLIENT: ITS A TIE!");
                }
            }
            userUI.UpdateSearching("Partida Finalizada!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOption(String option) {
        synchronized (this) {
            this.option = option;
            notify();
        }
    }

    private void resetGame() {
        is_finished = false;
        option = null;
    }
}
