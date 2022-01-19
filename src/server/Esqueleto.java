package server;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.trabalhoFinal.protos.Contato;
import com.trabalhoFinal.protos.Message;

public class Esqueleto {
    private static Esqueleto uniqueInstance;
    private static Agenda agenda;
    
    private Esqueleto(){}

    public static synchronized Esqueleto getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Esqueleto();
            agenda = new Agenda();
        }
        return uniqueInstance;
    }
    
    byte[] addContato(ByteString args) throws InvalidProtocolBufferException{    	
    	Contato contato = Contato.parseFrom(args.toByteArray());
    	
    	Contato.Telefone[] telefone = new Contato.Telefone[contato.getTelefoneCount()];
    	for (int i = 0; i < contato.getTelefoneCount(); i++) {
    		telefone[i] = contato.getTelefone(i);
    	}
    	
    	Contato.Endereco[] endereco = new Contato.Endereco[contato.getEnderecoCount()];
    	for (int i = 0; i < contato.getEnderecoCount(); i++) {
    		endereco[i] = contato.getEndereco(i);
    	}
    	
    	Contato.Email[] emails = new Contato.Email[contato.getEmailsCount()];
    	for (int i = 0; i < contato.getEmailsCount(); i++) {
    		emails[i] = contato.getEmails(i);
    	}
    	
    	Boolean resp = agenda.addContato(contato.getNome(), telefone, endereco, emails);
    	
    	Message.Builder message = Message.newBuilder();
    	message.setType(1); //0: requisição - 1: resposta
    	message.setId(0);
    	message.setObjReference("");
    	message.setMethodId("");
    	message.setArgs(ByteString.copyFromUtf8(Boolean.toString(resp)));
    	
    	return message.build().toByteArray();
    }
    byte[] listarTodos(byte[] args){
    	return null;
    }
    byte[] procContato(byte[] args){
    	return null;
    }
    byte[] editContato(byte[] args){
    	return null;
    }
    byte[] rmContato(byte[] args){
    	return null;
    }
    byte[] cleanAgenda(byte[] args){
    	return null;
    }

}
