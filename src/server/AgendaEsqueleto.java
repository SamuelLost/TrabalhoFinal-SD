package server;

import java.io.IOException;

import com.google.protobuf.ByteString;
import com.trabalhoFinal.protos.AgendaProto.Agenda;
import com.trabalhoFinal.protos.AgendaProto.Contato;

public class AgendaEsqueleto {
    private static AgendaEsqueleto uniqueInstance;
    private static SistemaAgenda agenda;

    public static synchronized AgendaEsqueleto getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new AgendaEsqueleto();
            agenda = new SistemaAgenda();
        }
        return uniqueInstance;
    }
    
    public ByteString addContato(ByteString args) throws IOException {

    	Contato contato = Contato.parseFrom(args.toByteArray());

    	Boolean response = agenda.addContato(contato);

    	return ByteString.copyFrom(response.toString().getBytes());
    }
    public ByteString listarContatos(ByteString args) throws IOException {
    	Agenda agenda_response = agenda.listarContato();

    	return ByteString.copyFrom(agenda_response.toByteArray());
    }
    public ByteString buscarContatos(ByteString args) throws IOException{
    	Agenda agenda_response = agenda.buscarContato(new String(args.toByteArray()));

    	return ByteString.copyFrom(agenda_response.toByteArray());
    }
    public ByteString editarContato(ByteString args) throws IOException {
        Contato contato = Contato.parseFrom(args.toByteArray());

        Boolean response = agenda.editContato(contato);

        return ByteString.copyFrom(response.toString().getBytes());
    }
    public ByteString removerContato(ByteString args) throws IOException{
    	Boolean response = agenda.removerContato(new String(args.toByteArray()));
    	
    	return ByteString.copyFrom(response.toString().getBytes());
    }
    public ByteString cleanAgenda(ByteString args) throws IOException {
        Boolean response = agenda.cleanAgenda();
        return ByteString.copyFrom(response.toString().getBytes());
    }

}
