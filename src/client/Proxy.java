package client;

import com.google.protobuf.Message;
import com.trabalhoFinal.protos.Contato;

public class Proxy {
    public Contato addContato(String name, String adress, String email) {
				
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

    public byte[] empacotaMensagem(String objectRef, String method, byte[] args) {
        return null;
    }

    public Message desempacotaMessagem(byte[] resposta) {
        return null;
    }

	public byte[] doOperation(String objectRef, String method, byte[] args) {
		return null;
	}
}
