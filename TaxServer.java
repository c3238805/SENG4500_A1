import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.net.ServerSocket;
/*  
    /============================\
    |  SENG4500 Assignment 1     | 
    |  Name : Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */
//Introduction of class:

public class TaxServer {

    static TaxServer ts; // initial the TaxServer
    private LinkedList<TaxNode> taxScale;

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in); // reading from console: system.in

        int port = 4500; // initital default port 4500

        System.out.println("SERVER: current port:4500 ");
        System.out.print(" Do you want to over-ride the port ? (Y/N)");
        String overideChoice = reader.nextLine();

        String response = "";

        if (overideChoice.toUpperCase().equals("Y")) {

            System.out.print("port:");
            port = Integer.parseInt(reader.nextLine());
        }

        // initial a new TaxServer class
        ts = new TaxServer();

        // while not recived a shut down command, server stay alive

        while (true) {

            try (
                    // first initial a serverSocket using port 4500
                    ServerSocket serversocket = new ServerSocket(port);
                    Socket socket = serversocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);) {
                System.out.println("CONNECTION OPEN"); // notice when connection is ready
                String msg = ""; // variable to hold Client's response

                // convert the msg from ASCII to string
                while ((msg = asciiToString(in.readLine())) != null) {

                    System.out.format("CLIENT: %s\n", msg); // display client msg on console
                    
                    // Revice Command--------------------------------------------------------------
                    if (msg.equals("TAX" + "\n")) {

                        // print Server response msg in console
                        System.out.print("SERVER:" + "TAX: OK" + "\n");

                        // convert into ACSII and send to server
                        out.println(toAscii("TAX: OK" + "\n"));
                    }

                    else if (msg.contains("STORE" + "\n")) {
                        // first check if the Size of LinkedList<TaxNode> taxScale >=10
                        if (ts.taxScale.getSize() >= 10) {
                            response = "STORE: Fail (sufficient to store up to ten income ranges, Maximum ranges reached.)";
                            // print Server response msg in console
                            System.out.print("SERVER:" + response + "\n");

                            // convert into ACSII and send to server
                            out.println(toAscii("STORE:" + response + "\n"));

                        } else {

                            // first filter the data
                            String[] msg_array = msg.split("\n");

                            if (msg_array[2].isEmpty()) {
                                msg_array[2] = "-1";
                            }
                            // start_income , end_income, base_tax , tax_per_dollar
                            TaxNode newRange = new TaxNode(
                                    Integer.parseInt(msg_array[1]), Integer.parseInt(msg_array[2]),
                                    Integer.parseInt(msg_array[3]), Integer.parseInt(msg_array[4]));

                            // insert the TaxNode inorder
                            ts.taxScale.insertInOrder(newRange);

                            // send STORE: OK response back to client
                            response = "STORE: OK";
                            // print Server response msg in console
                            System.out.print("SERVER:" + response + "\n");

                            // convert into ACSII and send to server
                            out.println(toAscii(response + "\n"));

                        }

                    }

                    else if (msg.contains("QUERY" + "\n")) {

                        Iterator<TaxNode> listAllRange = ts.taxScale.iterator();
                        String queryOut = "";
                        while (listAllRange.hasNext()) {

                            TaxNode temp = listAllRange.next();
                            String emptyString = temp.getEnd_income().toString();
                            if (temp.getEnd_income().equals(-1)) {
                                emptyString = "~";
                            }

                            queryOut += temp.getStart_income() + "  " + emptyString + "  " + temp.getBase_tax()
                                    + "  " + temp.getTax_per_dollar() + "  " + "\n";

                        }

                        // send QUERY: OK response back to client
                        response = queryOut + "QUERY: OK";
                        // print Server response msg in console
                        System.out.print("SERVER:" + response + "\n");

                        // convert into ACSII and send to server
                        out.println(toAscii(response + "\n"));

                    }

                    else if (msg.contains("BYE" + "\n")) {
                        // send response msg: BYE: OK to Client

                        response = "BYE: OK";
                        // print Server response msg in console
                        System.out.print("SERVER:" + response + "\n");

                        // convert into ACSII and send to server
                        out.println(toAscii(response + "\n"));

                        break;

                    }

                    else if (msg.contains("END" + "\n")) {

                        response = "END:OK";
                        // print Server response msg in console
                        System.out.print("SERVER:" + response + "\n");

                        // convert into ACSII and send to server
                        out.println(toAscii(response + "\n"));

                        break;

                    }


                    else {

                        //check msg to see if its valid
                        if(checkInteger(msg)){
                            msg = msg.replace("\n", "");
                            // run the calculator method and get response string 
                            response= calculator(Integer.parseInt(msg));

                            System.out.print("SERVER:" + response + "\n"); 
                            // convert into ACSII and send to server
                            out.println(toAscii(response + "\n"));

                            

                        }else {

                            // when recive a undefined command from client site, response with I dont Know msg
                            response = "I DON'T KNOW " + msg;
                            System.out.print("SERVER:" + response + "\n");
                            // convert into ACSII and send to server
                            out.println(toAscii(response + "\n"));
                            

                        }


                    }



                }

                // when break out of the while (in.readline()) loop


                if (response.contains("END:OK")) {
                    // Server close all the connection
                    reader.close();
                    serversocket.close();
                    socket.close();
                    in.close();
                    out.close();
                    System.out.println("TaxServer Shutdown.");
                    // exit the application
                    System.exit(0);
                }

            } catch (IOException e) { // You should have some better exception handling
                e.printStackTrace();

            }

        }
    }

    // ================================================================
    public TaxServer() {

        // when TaxServer class is initial, initial a LinkedList to hold the tax income
        // range and payable
        this.taxScale = new LinkedList<TaxNode>();
        // initial first TaxNode
        TaxNode initialFirstNode = new TaxNode(0, 0, 0, 0);
        // add to tasScale LinkedList
        this.taxScale.add(initialFirstNode);
    }

    // the TaxServer contain a calculator
    public static String calculator(int clientIncome){

        String answer = "";

        double tax = 0;    //initial a variable to hold tax value

        boolean foundRange =false;

        // loop the entire List
        Iterator<TaxNode> listAllRange = ts.taxScale.iterator();

        while (listAllRange.hasNext()) {

            TaxNode temp = listAllRange.next();
            if((temp.getStart_income() <= clientIncome)&&(temp.getEnd_income() >= clientIncome)){

                // conver into cent
                float cent = (float) (temp.getTax_per_dollar() * 0.01);
                tax = temp.getBase_tax() + (cent * (clientIncome-temp.getStart_income()));
               
                foundRange = true;

                break;
            }

        }

        if(foundRange){

            answer = "TAX IS " + String.valueOf(tax);

        }else {

            answer = "I DON'T KNOW "+ clientIncome; 
        }

        return answer;
    }

    // integer validator (this is to check wherethere the client entered an integer)
    public static Boolean checkInteger (String input){

        Boolean flag = true;
        for (int a = 0; a < input.length(); a++) {
            if (a == 0 && input.charAt(a) == '-')
                continue;
            if (!Character.isDigit(input.charAt(a)) && input.charAt(a) != 10)   // when i=10, indicate "\n"
                flag = false;
        }


        return flag;

    }

    // this method is convert the string to Ascii then return integer
    public static List toAscii(String convertString) {

        // convert the string into byte[]
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
