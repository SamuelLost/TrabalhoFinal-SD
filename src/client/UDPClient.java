package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient {
    private DatagramSocket socket;
    private InetAddress aHost;
    private int serverPort;
    private DatagramPacket request;
    private DatagramPacket reply;

    public UDPClient() {
        try {
            socket = new DatagramSocket();
            aHost = InetAddress.getByName("localhost");
            serverPort = 6789; 
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    public void sendResquest(byte[] msg) {
        request = new DatagramPacket(msg, msg.length, aHost, serverPort);
        try {
            socket.send(request);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public byte[] getResponse() {
        byte[] buffer = new byte[1000];
        reply = new DatagramPacket(buffer, buffer.length);
		try {
            socket.receive(reply);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reply.getData();
    }

    public void close() {
        socket.close();
    }
}