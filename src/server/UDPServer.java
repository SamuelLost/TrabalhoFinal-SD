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
		despachante = Despachante.getInstance();
		aSocket = null;
		try {
			aSocket = new DatagramSocket(6789);
			while (true) {
                byte[] req = getRequest();
        		
                Message message = desempacotaRequisicao(req, request.getLength());

//                System.out.println(message.getId());
//                System.out.println(message.getMethodId());
//                System.out.println(message.getArgs());
//                System.out.println(message.getType());
//                System.out.println(message.getObjReference());
                
                byte[] response = despachante.invoke(message);
                
        		byte[] aux2 = new byte[response.length];
        		for (int i = 0; i < response.length; i++) {
        			aux2[i] = response[i];
        		}
                
                Message message_resp = Message.parseFrom(aux2);

//	            System.out.println(message_resp.getId());
//	            System.out.println(message_resp.getMethodId());
//	            System.out.println(message_resp.getArgs());
//	            System.out.println(message_resp.getType());
//	            System.out.println(message_resp.getObjReference());
                
	            byte[] resposta = empacotaResposta(message_resp.getArgs(), message_resp.getId());

                sendResponse(resposta);
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
            
            reply = new DatagramPacket(response, response.length, request.getAddress(), request.getPort());
			aSocket.send(reply);
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }
    public static byte[] empacotaResposta(ByteString resultado, int requestId) {
    	Message.Builder message_resp = Message.newBuilder();
    	message_resp.setType(1); //0: requisição - 1: resposta
    	message_resp.setId(requestId);
    	message_resp.setObjReference("");
    	message_resp.setMethodId("");
    	message_resp.setArgs(resultado);
        return message_resp.build().toByteArray();
    }
    public static Message desempacotaRequisicao(byte[] request, int len) throws InvalidProtocolBufferException {
    	Message message = Message.parseFrom(request);
        return message;
    }
    
}