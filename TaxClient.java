import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

/*  
    /============================\
    |  SENG4500 Assignment 1     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */

public class TaxClient {
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in); // reading from console: system.in

        String host = "127.0.0.1"; // initital default host address :127.0.0.1
        int port = 4500; // initital default port 4500

        System.out.println("CLIENT: current host:127.0.0.1 , port:4500 ");
        System.out.print(" Do you want to over-ride the host and port ? (Y/N)");
        String overideChoice = reader.nextLine();
        if (overideChoice.toUpperCase().equals("Y")) {
            System.out.print("host address:");
            host = reader.nextLine();
            System.out.print("port:");
            port = Integer.parseInt(reader.nextLine());
        }

        String msg = ""; // variable to hold Server's response
        // ask user to input command that needs to send to the Server
        System.out.print("CLIENT:");
        String clientInput = reader.nextLine() + "\n";

        // to make connection with TaxServer, Client must enter TAX (command) to connect
        // with the server
        while (!clientInput.toUpperCase().equals("TAX" + "\n")) {
            System.out.println("Invalid Command input,Enter'TAX' connection to the server");
            System.out.println("Enter'EXIT' to quit TaxClient");
            clientInput = reader.nextLine() + "\n";

            if (clientInput.toUpperCase().equals("EXIT" + "\n")) {
                System.exit(0);
                // client close all the connection
                reader.close();

            }
        }

        try (
                // initial Socket Connection with host:127.0.0.1 and port 4500
                Socket socket = new Socket(host, port);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);) {

            // convert into ACSII and send to server
            out.println(toAscii(clientInput));

            // convert the msg from ASCII to string
            while ((msg = asciiToString(in.readLine())) != null) {

                System.out.format("SERVER: %s\n", msg); // display server msg on console

                // Revice Command--------------------------------------------------------------

                if (msg.equals("TAX: OK" + "\n")) {

                    // ask client to input the next operation
                    System.out.print("CLIENT:");

                    String inputString = reader.nextLine() + "\n";

                    // if Client enter STORE
                    if (inputString.toUpperCase().equals("STORE" + "\n")) {
                        String start_incomeString = reader.nextLine() + "\n";
                        String end_incomeString = reader.nextLine() + "\n";
                        String base_taxString = reader.nextLine() + "\n";
                        String tax_per_dollaString = reader.nextLine() + "\n";

                        // convert into ACSII and send to server
                        out.println(toAscii(inputString + start_incomeString + end_incomeString + base_taxString
                                + tax_per_dollaString));

                    } else{
                        out.println(toAscii(inputString));
                    }

                    

                }

                
                else if (msg.equals("STORE: OK" + "\n")) {

                    // ask client to input the next operation
                    System.out.print("CLIENT:");
                    // Client enter next operation command
                    String inputString = reader.nextLine() + "\n";

                    if (inputString.toUpperCase().equals("STORE" + "\n")) {
                        String start_incomeString = reader.nextLine() + "\n";
                        String end_incomeString = reader.nextLine() + "\n";
                        String base_taxString = reader.nextLine() + "\n";
                        String tax_per_dollaString = reader.nextLine() + "\n";

                        // convert into ACSII and send to server
                        out.println(toAscii(inputString + start_incomeString + end_incomeString + base_taxString
                                + tax_per_dollaString));

                    } else {
                        out.println(toAscii(inputString)); // convert into ACSII and send to server
                    }
                }

                else if (msg.contains("QUERY: OK" + "\n")) {
                    // ask client to input the next operation
                    System.out.print("CLIENT:");
                    // Client enter next operation command
                    String inputString = reader.nextLine() + "\n";
                    if (inputString.toUpperCase().equals("STORE" + "\n")) {
                        String start_incomeString = reader.nextLine() + "\n";
                        String end_incomeString = reader.nextLine() + "\n";
                        String base_taxString = reader.nextLine() + "\n";
                        String tax_per_dollaString = reader.nextLine() + "\n";

                        // convert into ACSII and send to server
                        out.println(toAscii(inputString + start_incomeString + end_incomeString + base_taxString
                                + tax_per_dollaString));

                    } else {
                        out.println(toAscii(inputString)); // convert into ACSII and send to server
                    }
                }
 
                else if (msg.matches("BYE: OK" + "\n")) {
                    
                    // client close all the connection
                    reader.close();
                    socket.close();
                    in.close();
                    out.close();
                    System.out.println("TaxClient closedown.");
                    
                    // exit the application
                    System.exit(0);

                }

                else if (msg.matches("END:OK")) {
                    
                    // client close all the connection
                    reader.close();
                    socket.close();
                    in.close();
                    out.close();
                    System.out.println("TaxClient closedown.");
                    
                    // exit the application
                    System.exit(0);
                    
                }


                else {

                    // ask client to input the next operation
                    System.out.print("CLIENT:");
                    // Client enter next operation command
                    String inputString = reader.nextLine() + "\n";
                    if (inputString.toUpperCase().equals("STORE" + "\n")) {
                        String start_incomeString = reader.nextLine() + "\n";
                        String end_incomeString = reader.nextLine() + "\n";
                        String base_taxString = reader.nextLine() + "\n";
                        String tax_per_dollaString = reader.nextLine() + "\n";

                        // convert into ACSII and send to server
                        out.println(toAscii(inputString + start_incomeString + end_incomeString + base_taxString
                                + tax_per_dollaString));                              

                    } else{
                         out.println(toAscii(inputString)); // convert into ACSII and send to server

                         
                    }
                       
                    
                }

            }

            

        } catch (Exception e) { // You should have some better exception handling
            e.printStackTrace();
            
        }

    }

    // ================================================================
    public TaxClient() {

    }



    // this method is convert the string to Ascii then return integer
    public static List toAscii(String convertString) {

        // convert the string into uppercase then into byte[]
        byte[] bytes = convertString.toUpperCase().getBytes(StandardCharsets.US_ASCII);
        List<Integer> result = new ArrayList<>();
        for (byte aByte : bytes) {
            int ascii = (int) aByte; // byte -> int
            result.add(ascii);
        }

        return result; // retrun the result of ASCII

    }

    public static String asciiToString(String msg) {

        // first clear "[" and "]"
        msg = msg.replace("[", "");
        msg = msg.replace("]", "");

        // converting string to List<Integer>
        List<Integer> msgIntList = new ArrayList<>();
        for (String i : msg.split(", ")) {

            int msgInt = Integer.parseInt(i);
            msgIntList.add(msgInt);
        }

        String str = "";

        for (int i : msgIntList) {
            str += Character.toString((char) i);

        }

        return str; // return the final converted string
    }

}
