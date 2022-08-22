import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TaxClient {
    public static void main(String[] args) {
        try (
                Socket s = new Socket("127.0.0.1", 4500);
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);) {
            out.println("HELLO");
            System.out.println(in.readLine());
        } catch (Exception e) { // You should have some better exception handling
            e.printStackTrace();
        }
    }
}
