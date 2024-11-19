import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client extends Thread {
    public static final int PUERTO = 2024;
    public static final String IP_SERVER = "localhost";

    public void run() {
        System.out.println("        CLIENT APP         ");
        System.out.println("-----------------------------------");
        InetSocketAddress ip = new InetSocketAddress(IP_SERVER, PUERTO);
        Socket socket = new Socket();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        try {
//            String pwd = bf.readLine();
//            bf.read();
            socket.connect(ip);
            String opt = bf.readLine();
            InputStreamReader input = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(input);
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
}
