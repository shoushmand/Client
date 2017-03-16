package com.example.sahar.client;

import android.util.Log;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient implements Runnable{

    private String serverMessage;
    public static final String SERVERIP = "192.168.0.102"; //your computer IP address
    public static final int SERVERPORT = 5555;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;



    /**
     *  Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public TCPClient( ) {

    }


    public void stopClient(){
        mRun = false;
    }

    public void run() {

        mRun = true;

        try {
            //here you must put your computer's IP address.
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);

            Log.e("TCP Client", "C: Connecting...");

            //create a socket to make the connection with the server
            Socket socket = new Socket(serverAddr, SERVERPORT);

            try {

                //send the message to the server
                // out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                for (int i = 0; i < 20; i++){
                    out.write(i);
                // Flush the data from the stream to indicate end of message
                out.flush();
                }
                out.write(30);
                out.flush();
// Close the output stream
                out.close();

// Close the socket connection
                socket.close();

                Log.e("TCP Client", "C: Sent.");

                Log.e("TCP Client", "C: Done.");



            } catch (Exception e) {

                Log.e("TCP", "S: Error", e);

            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
            }

        } catch (Exception e) {

            Log.e("TCP", "C: Error", e);

        }

    }

    //Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
    //class at on asynckTask doInBackground
    public interface OnMessageReceived {
         void messageReceived(String message);
    }
}

