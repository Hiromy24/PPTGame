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

    public Client(UI actualUI) {
        userUI = actualUI;
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

            userUI.UpdateSearching(br.readLine());

            int rounds =0;
            while (rounds != 3){
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

                String enemyOption = br.readLine();
                userUI.UpdateSearching(enemyOption);
                String is_win = br.readLine();
                if (is_win.equals("WIN")) {
                    System.out.println("CLIENT: YOU WIN!");
                } else if (is_win.equals("LOSE")) {
                    System.out.println("CLIENT: YOU LOSE!");
                }else {
                    System.out.println("CLIENT: ITS A TIE!");
                }
                rounds++;
                sleep(5000);
                userUI.UpdateSearching("Siguiente Ronda!");
            }


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOption(String option) {
        synchronized (this) {
            this.option = option;
            notify();
        }
    }
}
