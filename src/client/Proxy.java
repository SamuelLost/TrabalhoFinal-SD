package client;

import java.util.Arrays;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.trabalhoFinal.protos.*;
public class Proxy {
    UDPClient client;
    int id = 0;
    public Proxy() {
        client = new UDPClient();
    }
    public Contato addContato(String name, String adress, String email) {
        String aux = name + "," + adress + "," + email;
        ByteString args = ByteString.copyFromUtf8(aux);
        doOperation("Contato", "addContato", args);
        return null;
    }

    public Contato listarTodos() {
        return null;
    }

    public Contato procContato(String busca) {
        return null;
    }

    public boolean editContato(String name, String adress, String telefone, String email) {
        return true;
    }

    public String rmContato(String name) {
        return "";
    }

    public void cleanAgenda() {

    }

    public void finaliza() {

    }

    public byte[] empacotaMensagem(String objectRef, String method, ByteString args) {
        Message.Builder a = Message.newBuilder();
        a.setType(0); //0: requisição - 1: resposta
        a.setId(id++);
        a.setObjReference(objectRef);
        a.setMethodId(method);
        a.setArgs(args);
        return a.build().toByteArray();
    }

    public static byte[] desempacotaMessagem(byte[] resposta) {
    	Message message = null;
        try {
            message = Message.parseFrom(resposta);
        } catch (InvalidProtocolBufferException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return message.toByteArray();
    }

	public byte[] doOperation(String objectRef, String method, ByteString args) {
        byte[] requestEmpac = empacotaMensagem(objectRef, method, args);
        client.sendResquest(requestEmpac);
        return desempacotaMessagem(client.getResponse());
	}
}
