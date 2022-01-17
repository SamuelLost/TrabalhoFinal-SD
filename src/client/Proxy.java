package client;

import com.google.protobuf.ByteString;
import com.trabalhoFinal.protos.*;
public class Proxy {
    UDPClient client;
    int id = 0;
    public Proxy() {
        client = new UDPClient();
    }
    public Contato addContato(String name, String adress, String email) {
        String aux = name + "," + adress + "," + email;
        byte[] args = aux.getBytes();
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

    //Tentativa de empacotar
    public byte[] empacotaMensagem(String objectRef, String method, byte[] args) {
        Message.Builder a = Message.newBuilder();
        a.setType(0); //0: requisição - 1: response
        a.setId(id++);
        a.setObjReference(objectRef);
        a.setMethodId(method);
        a.setArgs(ByteString.copyFrom(args));
        //Falta serializar
        return null;
    }

    public Message desempacotaMessagem(byte[] resposta) {
        return null;
    }

    //Falta desempacotar e ver o retorno
	public byte[] doOperation(String objectRef, String method, byte[] args) {
        byte[] requestEmpac = empacotaMensagem(objectRef, method, args);
        client.sendResquest(requestEmpac);
        client.getResponse();
		return null;
	}
}
