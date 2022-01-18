package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.trabalhoFinal.protos.Message;

public class UDPServer {
    private static DatagramSocket aSocket;
    private static Despachante despachante;
    private static byte[] buffer = new byte[1024];
    private static DatagramPacket request;
    private static DatagramPacket reply;
	public static void main(String args[]) {
		aSocket = null;
		try {
			aSocket = new DatagramSocket(6789);
			while (true) {
                byte[] req = getRequest();
                Message message = desempacotaRequisicao(req);
                byte[] resp = empacotaResposta();
                sendResponse(resp);
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
		byte[] aux = new byte[request.getLength()];
		for (int i = 0; i < request.getLength(); i++) {
			aux[i] = request.getData()[i];
		}
		return aux;
    }
    public static void sendResponse(byte[] response) {
        try {
            byte[] response = despachante.invoke(requisicao);
            reply = new DatagramPacket(requisicao.getBytes(), 
										request.getLength(), request.getAddress(), request.getPort());
			aSocket.send(reply);
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }
    public static byte[] empacotaResposta(Message message, byte[] args) {
    	
    }
    public static Message desempacotaRequisicao(byte[] request) {
    	Message message = null;
		try {
			message = Message.parseFrom(request);
		} catch (InvalidProtocolBufferException e) {
			System.out.println("Error: " + e.getMessage());
		}
        return message;
    }
    
}