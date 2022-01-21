package client;

import java.util.List;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.trabalhoFinal.protos.MessageProto.Message;
import com.trabalhoFinal.protos.AgendaProto.Agenda;
import com.trabalhoFinal.protos.AgendaProto.Contato;

public class Proxy {
    UDPClient client;
    int requestId;

    public Proxy() {
        client = new UDPClient();
        requestId = 0;
    }
    
    // retorna true se adicionar, false se já existir contato com o mesmo nome
    public Boolean addContato(Contato contato) throws InvalidProtocolBufferException {
        ByteString args = ByteString.copyFrom(contato.toByteArray());

        byte[] resp = doOperation("Agenda", "addContato", args);
        
        ByteString byteString = ByteString.copyFrom(resp);
        
        Boolean response = Boolean.valueOf(new String(byteString.toByteArray()));
        
        return response;
    }

    // retorna lista com os contatos
    public List<Contato> listarTodos() throws InvalidProtocolBufferException {
    	byte[] resp = doOperation("Agenda", "listarContatos", ByteString.copyFrom("vazio".getBytes()));

    	ByteString byteString = ByteString.copyFrom(resp);
    	
    	Agenda agenda_response = Agenda.parseFrom(byteString);
    	
    	List<Contato> listaContatos = agenda_response.getContatosList(); 
    	
        return listaContatos;
    }

    // retorna lista com os contatos que se encaixam na busca
    public List<Contato> procContato(String busca) throws InvalidProtocolBufferException {
    	byte[] resp = doOperation("Agenda", "buscarContatos", ByteString.copyFrom(busca.getBytes()));

    	ByteString byteString = ByteString.copyFrom(resp);
    	
    	Agenda agenda_response = Agenda.parseFrom(byteString);
    	
    	List<Contato> listaContatos = agenda_response.getContatosList(); 
    	
        return listaContatos;
    }

    // retorna true se conseguir editar, falso caso o contato não exista
    public Boolean editarContato(Contato contato) throws InvalidProtocolBufferException {
        return true;
    }

    // retorna true caso consiga remover o contato, falto caso o contato não exista
    public Boolean removerContato(String nome) throws InvalidProtocolBufferException {
        ByteString args = ByteString.copyFrom(nome.getBytes());

        byte[] resp = doOperation("Agenda", "removerContato", args);
        
        ByteString byteString = ByteString.copyFrom(resp);
        
        Boolean response = Boolean.valueOf(new String(byteString.toByteArray()));
        
        return response;
    }

    // limpar os contatos, não retorna nada
    public void limparAgenda() {

    }

    // finaliza o cliente udp
    public void finaliza() {
    	client.close();
    }

    public byte[] empacotaMensagem(String objectRef, String method, ByteString args) {
        Message.Builder message = Message.newBuilder();
        message.setType(0);  // 0 -> request

        message.setId(requestId++);
        
        message.setObjReference(objectRef);
        
        message.setMethodId(method);
        
        message.setArgs(args);
        
        return message.build().toByteArray();
    }

    public static Message desempacotaMessagem(byte[] resposta) throws InvalidProtocolBufferException {
    	Message message = Message.parseFrom(resposta);
        return message;
    }

	public byte[] doOperation(String objectRef, String methodId, ByteString args) throws InvalidProtocolBufferException {
        byte[] bytes_request = empacotaMensagem(objectRef, methodId, args);
        
        client.sendResquest(bytes_request);

        byte[] bytes_response = client.getResponse();
        
        Message response_message = desempacotaMessagem(bytes_response);
        
        return response_message.getArgs().toByteArray();
	}
}
