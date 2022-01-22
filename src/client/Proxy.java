package client;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.List;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.trabalhoFinal.protos.MessageProto.Message;
import com.trabalhoFinal.protos.AgendaProto.Agenda;
import com.trabalhoFinal.protos.AgendaProto.Contato;

public class Proxy {
	final int MAX_ENVIOS = 10;
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
    	byte[] resp = doOperation("Agenda", "listarContatos", ByteString.copyFrom("".getBytes()));

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

        ByteString args = ByteString.copyFrom(contato.toByteArray());

        byte[] resp = doOperation("Agenda", "editarContato", args);

        ByteString byteString = ByteString.copyFrom(resp);

        Boolean response = Boolean.valueOf(new String(byteString.toByteArray()));

        return response;
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
    public Boolean limparAgenda() throws InvalidProtocolBufferException {
        ByteString args = ByteString.copyFrom(("remove").getBytes());
        byte[] resp = doOperation("Agenda", "cleanAgenda", args);

        ByteString byteString = ByteString.copyFrom(resp);

        Boolean response = Boolean.valueOf(new String(byteString.toByteArray()));

        return response;
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

    public static Message desempacotaMensagem(byte[] resposta) {
    	Message message = null;
		try {
			message = Message.parseFrom(resposta);
		} catch (InvalidProtocolBufferException e) {
			System.out.println("InvalidProtocolBufferException " + e.getMessage());
		}
        return message;
    }

	public byte[] doOperation(String objectRef, String methodId, ByteString args) {
        try {
        	byte[] bytes_request = empacotaMensagem(objectRef, methodId, args);
            
            client.sendResquest(bytes_request);
            
            Message response_message = null;
            byte[] bytes_response = null;
            
            int qtd_envios = 1;
            
            while (qtd_envios <= MAX_ENVIOS) {
            	client.socket.setSoTimeout(1500);
            	try {
            		bytes_response = client.getResponse();
            		response_message = desempacotaMensagem(bytes_response);
            		
            		if (response_message.getId() == (requestId - 1)) {
            			return response_message.getArgs().toByteArray();
            		}
            	} catch (SocketTimeoutException e) {
					System.out.println("SocketTimeoutException " + e.getMessage());
					client.sendResquest(bytes_request);
					qtd_envios++;
            	} catch (IOException e) {
            		System.out.println("IOException " + e.getMessage());
				}
            }

        } catch(SocketException e) {
        	System.out.println("SocketException " + e.getMessage());
        }
        
        return "Servidor morreu".getBytes();
	}
}
