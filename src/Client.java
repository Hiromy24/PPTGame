import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client extends Thread {
    public static final String IP_SERVER = "localhost";
    public static final int PUERTO = 2024;
    private String option;
    private PrintWriter output;
    public String getOption() {
        return option;
    }

    public void run() {
        //region ClientPrint
        System.out.println("        CLIENT APP         ");
        System.out.println("-----------------------------------");
        //endregion
        InetSocketAddress connection = new InetSocketAddress(IP_SERVER, PUERTO);
        try(Socket socket = new Socket()) {
            //TODO: PSW
            socket.connect(connection);
            InputStreamReader input = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(input);

            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            output = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("CLIENT: Waiting response from server...");

            boolean is_win = bf.readLine().equals("WIN");
            if (is_win) {
                System.out.println("CLIENT: YOU WIN!");
            } else {
                System.out.println("CLIENT: YOU LOSE!");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendOption(String option) {
        this.option = option;
        output.println(option);
    }
}
