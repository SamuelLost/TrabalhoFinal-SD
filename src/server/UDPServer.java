package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Map;
import java.util.TreeMap;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.trabalhoFinal.protos.MessageProto.Message;

public class UDPServer {
	private static final Integer PORT = 6789;
	
    private static DatagramSocket aSocket;
    private static Despachante despachante;
    private static byte[] buffer = new byte[1024];
    private static DatagramPacket request;
    private static DatagramPacket reply;
    private static Map<Integer, byte[]> mapa;

    public static void main(String args[]) {
        mapa = new TreeMap<Integer, byte[]>(); // Estrutura de dados
        despachante = Despachante.getInstance();
        aSocket = null;
        try {
            aSocket = new DatagramSocket(PORT);
            while (true) {
                System.out.println("Servidor rodando...");

                // Recebe a requisição
                byte[] bytes_request = getRequest();

                // Desempacota a requisição
                Message message_request = desempacotaRequisicao(bytes_request);

                // Verifica se a requisição já foi tratada e se já tem resposta
                if (mapa.containsKey(message_request.getId())) {
                    // Envia a resposta empacotada
                    sendResponse(mapa.get(message_request.getId()), request.getAddress(), request.getPort());
                } else {
                    // Resolve a requisição e salva a resposta
                    ByteString byteString_response = despachante.invoke(message_request);

                    // Empacota a resposta
                    byte[] bytes_packed_response = empacotaResposta(message_request, byteString_response);

                    // Envia a resposta empacotada
                    sendResponse(bytes_packed_response, request.getAddress(), request.getPort());

                    // Coloca a requisição dentro do mapa com a o id e resposta
                    mapa.put(message_request.getId(), bytes_packed_response);
                }

            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } finally {
            if (aSocket != null)
                aSocket.close();
        }
    }

    /**
     * Método para pegar a requisição enviada pelo cliente
     * @return - byte[]: requisição
     */
    public static byte[] getRequest() {
        request = new DatagramPacket(buffer, buffer.length);
        
        try {
			aSocket.receive(request);
		} catch (IOException e) {
			System.out.println("IOException server.UDPServer: " + e.getMessage());
		}

        //Removendo o lixo
        byte[] aux = new byte[request.getLength()];
        for (int i = 0; i < request.getLength(); i++) {
            aux[i] = request.getData()[i];
        }

        return aux;
    }

    /**
     * Método para enviar uma resposta ao cliente
     * @param response - conteúdo a ser enviado
     */
    public static void sendResponse(byte[] response, InetAddress clientHost, int clientPort) {
        try {
            reply = new DatagramPacket(response, response.length, clientHost, clientPort);
            aSocket.send(reply);
        } catch (IOException e) {
            System.out.println("IOException server.UDPServer: " + e.getMessage());
        }
    }

    /**
     * Método para empacotar e serializar a resposta.
     * @param message - objeto Message
     * @param args - resposta a ser empacotada
     * @return - a resposta empacotada e serializada
     */
    public static byte[] empacotaResposta(Message message, ByteString args) {
        Message.Builder message_response = Message.newBuilder()
		    .setType(1)
		    .setId(message.getId())
		    .setObjReference(message.getObjReference())
		    .setMethodId(message.getMethodId())
		    .setArgs(args);
        return message_response.build().toByteArray();
    }

    /**
     * Desserializa a requisição com o método parseFrom()
     * @param request - requisição serializada
     * @return a requisição empacotada em Message
     */
    public static Message desempacotaRequisicao(byte[] request) {
        Message message = null;
		try {
			message = Message.parseFrom(request);
		} catch (InvalidProtocolBufferException e) {
			System.out.println("InvalidProtocolBufferException server.UDPServer: " + e.getMessage());
		}
        return message;
    }

}