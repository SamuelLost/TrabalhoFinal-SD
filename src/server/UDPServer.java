package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.google.protobuf.InvalidProtocolBufferException;
import com.trabalhoFinal.protos.Message;

public class UDPServer {
    private static DatagramSocket aSocket;
    private static Despachante despachante;
    private static byte[] buffer = new byte[1000];
    private static DatagramPacket request;
    private static DatagramPacket reply;
	public static void main(String args[]) {
		aSocket = null;
		try {
			aSocket = new DatagramSocket(6789);
			while (true) {
                String request = new String(getRequest());
                sendResponse(request);
				
			}
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (aSocket != null)
				aSocket.close();
		}
	}
    public static byte[] getRequest() throws IOException {
        request = new DatagramPacket(buffer, buffer.length);
		aSocket.receive(request);
        return request.getData();
    }

    public static void sendResponse(String requisicao) {
        try {
            //String response = despachante.invoke(requisicao);
            reply = new DatagramPacket(requisicao.getBytes(), 
										request.getLength(), request.getAddress(), request.getPort());
			aSocket.send(reply);
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }
    public byte[] empacotaResposta(Message message, byte[] args) {
        return null;
    }

    
}