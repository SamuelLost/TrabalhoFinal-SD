package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Map;
import java.util.TreeMap;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.trabalhoFinal.protos.MessageProto.Message;

public class UDPServer {
    private static DatagramSocket aSocket;
    private static Despachante despachante;
    private static byte[] buffer = new byte[1024];
    private static DatagramPacket request;
    private static DatagramPacket reply;
    private static Map<Integer, byte[]> mapa;

	public static void main(String args[]) {
		mapa = new TreeMap<Integer, byte[]>();
		despachante = Despachante.getInstance();
		aSocket = null;
		try {
			aSocket = new DatagramSocket(6789);
			while (true) {
				System.out.println("Servidor rodando...");
				
				// Recebe a requisição
                byte[] bytes_request = getRequest();
        		
                // Desempacota a requisição
                Message message_request = desempacotaRequisicao(bytes_request);

                if (mapa.containsKey(message_request.getId())) {
    	            // Envia a resposta empacotada
                    sendResponse(mapa.get(message_request.getId()));

                    continue;
                }
                
                // Resolve a requisição e salva a resposta
                ByteString byteString_response = despachante.invoke(message_request);

                // Empacota a resposta
	            byte[] bytes_packed_response = empacotaResposta(message_request, byteString_response);	            
	            
	            // Envia a resposta empacotada
                sendResponse(bytes_packed_response);
                
                mapa.put(message_request.getId(), bytes_packed_response);
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

    public static byte[] empacotaResposta(Message message, ByteString args) {
    	Message.Builder message_response = Message.newBuilder();
    	
    	message_response.setType(1); // 1 -> response
    	
    	message_response.setId(message.getId());
    	
    	message_response.setObjReference(message.getObjReference());
    	
    	message_response.setMethodId(message.getMethodId());
    	
    	message_response.setArgs(args);
    	
        return message_response.build().toByteArray();
    }

    public static Message desempacotaRequisicao(byte[] request) throws InvalidProtocolBufferException {
    	Message message = Message.parseFrom(request);
        return message;
    }
    
}