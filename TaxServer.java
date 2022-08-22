import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;

public class TaxServer {

    public static void main(String[] args) {
        try (
                ServerSocket ss = new ServerSocket(4500);
                Socket s = ss.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);) {
            System.out.println("CONNECTION OPEN");
            String msg = "";
            while (!(msg = in.readLine()).equals("HELLO"))
                ; // Wait until hello is sent
            System.out.format("CLIENT: %s\n", msg);
            out.println("HI, HOW ARE YOU");
        } catch (Exception e) { // You should have some better exception handling
            e.printStackTrace();
        }
    }


   
}
