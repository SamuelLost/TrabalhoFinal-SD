package server;

import com.trabalhoFinal.protos.Message;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer {
    DatagramSocket aSocket;
    Despachante despachante;
	public static void main(String args[]) {
		aSocket = null;
		try {
			aSocket = new DatagramSocket(6789);
			byte[] buffer = new byte[1000];
			while (true) {
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);

                Connection c = new Connection(aSocket);

				DatagramPacket reply = new DatagramPacket(request.getData(), 
										request.getLength(), request.getAddress(), request.getPort());
				aSocket.send(reply);
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
    public String getRequest() {
        String request = "";
        try {
            request = dataIn.readUTF();
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
        return request;
    }

    public void sendResponse(String request) {
        try {
            String response = despachante.invoke(request);
            dataOut.writeUTF(response);
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }
    public byte[] empacotaResposta(Message message, byte[] args) {
        return null;
    }
}