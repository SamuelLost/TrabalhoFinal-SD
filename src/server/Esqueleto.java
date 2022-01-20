package server;

import java.io.IOException;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.trabalhoFinal.protos.AgendaProto.Agenda;
import com.trabalhoFinal.protos.AgendaProto.Contato;

public class Esqueleto {
    private static Esqueleto uniqueInstance;
    private static SistemaAgenda agenda;
    
    private Esqueleto(){}

    public static synchronized Esqueleto getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Esqueleto();
            agenda = new SistemaAgenda();
        }
        return uniqueInstance;
    }
    
    ByteString addContato(ByteString args) throws IOException {
    	Contato contato = Contato.parseFrom(args.toByteArray());

    	Boolean response = agenda.addContato(contato);

    	return ByteString.copyFrom(response.toString().getBytes());
    }
    ByteString listarTodos(ByteString args) throws IOException {
    	Agenda agenda_response = agenda.listarContato();

    	return ByteString.copyFrom(agenda_response.toByteArray());
    }
    ByteString procContato(ByteString args) throws IOException{
    	Agenda agenda_response = agenda.buscarContato(new String(args.toByteArray()));

    	return ByteString.copyFrom(agenda_response.toByteArray());
    }
    byte[] editContato(ByteString args){
    	return null;
    }
    byte[] rmContato(ByteString args){
    	return null;
    }
    byte[] cleanAgenda(ByteString args){
    	return null;
    }

}
