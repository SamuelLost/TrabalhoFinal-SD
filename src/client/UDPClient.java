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

    /**
     * Construtor da classe
     */
    public UDPClient() {
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(1500);
            aHost = InetAddress.getByName("localhost");
            serverPort = 6789; 
        } catch (SocketException e) {
            System.err.println("SocketException-UDPClient.java: " + e.getMessage());
        } catch (UnknownHostException e) {
        	System.err.println("UnknownHostException-UDPClient.java: " + e.getMessage());
        }
    }

    /**
     * Método para enviar a requisição
     * @param msg - a requisição empacota e serializada em byte[]
     */
    public void sendResquest(byte[] msg) {
        request = new DatagramPacket(msg, msg.length, aHost, serverPort);
        try {
            socket.send(request);
        } catch (IOException e) {
        	System.err.println("IOExcepiton-UDPClient.java: " + e.getMessage());
        }
    }

    /**
     * Método para pegar a reposta enviada do servidor
     * @return - byte[]: a resposta serializada e empacotada
     * @throws IOException
     */
    public byte[] getResponse() throws IOException {
        byte[] buffer = new byte[1024];
        reply = new DatagramPacket(buffer, buffer.length);
        socket.receive(reply);
        
        //Removendo o lixo da resposta de acordo com o tamanho
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