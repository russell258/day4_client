package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class App 
{
    public static void main( String[] args ) throws UnknownHostException, IOException
    {
        String portNumber = args[0];
        String input = "";
        Console con = System.console();
        Socket socket = new Socket("localhost",Integer.parseInt(portNumber));

        //send command to server write then read
        try (OutputStream os = socket.getOutputStream()){
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);
            
            try (InputStream is = socket.getInputStream()){
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            //while loop to send command
            while (!input.equals("close")){
                input = con.readLine("Enter a command: ");
                dos.writeUTF(input);
                dos.flush();
                
                if (!input.equals("close")){
                    String msgReceived = dis.readUTF();
                    System.out.println(msgReceived);
                }

            }


            dis.close();
            }

            dos.close();
        }
        socket.close();

    }
}
