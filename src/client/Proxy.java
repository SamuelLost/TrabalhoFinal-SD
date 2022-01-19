package client;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.trabalhoFinal.protos.*;

public class Proxy {
    UDPClient client;
    int id = 0;
    public Proxy() {
        client = new UDPClient();
    }
    public Boolean addContato(Contato contato) throws InvalidProtocolBufferException {
        ByteString args = ByteString.copyFrom(contato.toByteArray());

        byte[] resp = doOperation("Agenda", "addContato", args);
        
        ByteString bs = ByteString.copyFrom(resp);
        
        Boolean ret = Boolean.valueOf(new String(bs.toByteArray()));
        
        return ret;
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

    public static Message desempacotaMessagem(byte[] resposta) throws InvalidProtocolBufferException {
    	Message message = Message.parseFrom(resposta);
        return message;
    }

	public byte[] doOperation(String objectRef, String method, ByteString args) throws InvalidProtocolBufferException {
        byte[] requestEmpac = empacotaMensagem(objectRef, method, args);
        
        client.sendResquest(requestEmpac);

        byte[] response = client.getResponse();
        
        Message message = desempacotaMessagem(response);
        
        System.out.println("Get response: " + response.length);
        
        return message.getArgs().toByteArray();
	}
}
