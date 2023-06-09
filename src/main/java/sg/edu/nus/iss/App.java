package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class App 
{
    public static void main( String[] args ) throws NumberFormatException, UnknownHostException, IOException
    {
        String serverHost = args[0];
        String serverPort = args[1];

        //establish connection to server - slide 8
        // server must be started first
        Socket socket = new Socket(serverHost, Integer.parseInt(serverPort));

        //setup console input from keyboard
        // variable to save keyboard inputs
        //variable to save msgReceived
        Console con = System.console();
        String keyboardInput = "";
        String msgReceived = "";



        //similar to server - slide 9
        try(InputStream is = socket.getInputStream()){
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            try(OutputStream os = socket.getOutputStream()){
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);

                //while loop here
                while(!keyboardInput.equals("close")){
                    keyboardInput = con.readLine("Enter a command: ");

                    //send message acrsos through the communication tunnel
                    dos.writeUTF(keyboardInput);
                    dos.flush();

                    //receive message from server(response) and process it
                    msgReceived = dis.readUTF();
                    System.out.println(msgReceived);
                }



                // closes the output stream in reverse order

                dos.close();
                bos.close();
                os.close();
                

            }catch (EOFException ex){

            }
            // closes the input stream in reverse order
            dis.close();
            bis.close();
            is.close();
            socket.close();
        }catch (EOFException ex){
            ex.printStackTrace();
        }
        
      
 
    }
}
