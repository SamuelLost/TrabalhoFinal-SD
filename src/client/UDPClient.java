package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient {
    protected DatagramSocket socket;
    private InetAddress aHost;
    private int serverPort;
    private DatagramPacket request;
    private DatagramPacket reply;

    public UDPClient() {
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(2000);
            aHost = InetAddress.getByName("localhost");
            serverPort = 6789; 
        } catch (SocketException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (UnknownHostException e) {
        	System.err.println("Error: " + e.getMessage());
        }
    }

    public void sendResquest(byte[] msg) {
        request = new DatagramPacket(msg, msg.length, aHost, serverPort);
        try {
            socket.send(request);
        } catch (IOException e) {
        	System.err.println("Error: " + e.getMessage());
        }
    }

    public byte[] getResponse() throws IOException {
        byte[] buffer = new byte[1024];
        reply = new DatagramPacket(buffer, buffer.length);
        socket.receive(reply);
        
		byte[] aux = new byte[reply.getLength()];
		for (int i = 0; i < reply.getLength(); i++) {
			aux[i] = reply.getData()[i];
		}
        return aux;
    }

    public void close() {
        socket.close();
    }
}