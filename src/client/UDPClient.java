package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient {
	private final Integer TIMEOUT = 1500;
	private final Integer PORT = 6789;
	private final String IP = "127.0.0.1";
	
	
    private DatagramSocket socket;
    private InetAddress aHost;
    private int serverPort;
    private DatagramPacket request;
    private DatagramPacket reply;

    /**
     * Construtor da classe
     */
    public UDPClient() {
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(TIMEOUT);
            aHost = InetAddress.getByName(IP);
            serverPort = PORT; 
        } catch (SocketException e) {
            System.err.println("SocketException client.UDPClient: " + e.getMessage());
        } catch (UnknownHostException e) {
        	System.err.println("UnknownHostException client.UDPClient: " + e.getMessage());
        }
    }

    /**
     * Método para enviar a requisição
     * @param msg - a requisição empacota e serializada em byte[]
     */
    public void sendResquest(byte[] message) {
        request = new DatagramPacket(message, message.length, aHost, serverPort);
        try {
            socket.send(request);
        } catch (IOException e) {
        	System.err.println("IOException client.UDPClient: " + e.getMessage());
        }
    }

    /**
     * Método para pegar a reposta enviada do servidor
     * @return byte[] - a resposta serializada e empacotada
     * @throws IOException
     */
    public byte[] getResponse() throws IOException {
        byte[] bufferEntrada = new byte[1024];

        reply = new DatagramPacket(bufferEntrada, bufferEntrada.length);

		socket.receive(reply);
        
        //Removendo o lixo da resposta
		byte[] aux = new byte[reply.getLength()];
		for (int i = 0; i < reply.getLength(); i++) {
			aux[i] = reply.getData()[i];
		}
        return aux;
    }

    //Fechando o cliente
    public void close() {
        socket.close();
    }
}