package server;

import java.io.IOException;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.trabalhoFinal.protos.AgendaProto.Agenda;
import com.trabalhoFinal.protos.AgendaProto.Contato;

public class AgendaEsqueleto {
    private SistemaAgenda agenda;

    public AgendaEsqueleto() {
    	agenda = new SistemaAgenda();
    }
    
    /**
     * Esqueleto do método addContato(), faz a desserialização do argumento
     * e chama o método correspondente na classe SistemaAgenda. Obtém a resposta,
     * transforma em ByteString.
     * @param args - ByteString: argumentos
     * @return - ByteString: resposta
     * @throws IOException - gerada pelos métodos addContato()
     */
    public ByteString adicionarContato(ByteString args) {
    	Contato contato = null;
		try {
			contato = Contato.parseFrom(args.toByteArray());
		} catch (InvalidProtocolBufferException e) {
			System.out.println("InvalidProtocolBufferException server.AgendaEsqueleto: " + e.getMessage());
		}

    	Boolean response = null;
		response = agenda.adicionarContato(contato);

    	return ByteString.copyFrom(response.toString().getBytes());
    }
    
    /**
     * Esqueleto do método listarContatos() e chama o método correspondente na classe SistemaAgenda. 
     * Obtém a resposta, transforma em ByteString.
     * @param args - ByteString: argumentos
     * @return - ByteString: resposta
     * @throws IOException - gerada pelo método listarContatos()
     */
    public ByteString listarContatos(ByteString args) {
    	Agenda agenda_response = null;
    	
		agenda_response = agenda.listarContatos();

    	return ByteString.copyFrom(agenda_response.toByteArray());
    }

    /**
     * Esqueleto do método buscarContatos() e chama o método correspondente na classe SistemaAgenda. 
     * Obtém a resposta, transforma em ByteString.
     * @param args - ByteString: argumentos 
     * @return ByteString: resposta
     * @throws IOException - gerada pelo método buscarContatos()
     */
    public ByteString procurarContatos(ByteString args) {
    	Contato contato = null;
		try {
			contato = Contato.parseFrom(args.toByteArray());
		} catch (InvalidProtocolBufferException e) {
			System.out.println("InvalidProtocolBufferException server.AgendaEsqueleto: " + e.getMessage());
		}
    	
    	Agenda agenda_response = agenda.procurarContato(contato);

    	return ByteString.copyFrom(agenda_response.toByteArray());
    }

    /**
     * Esqueleto do método editarContato() e chama o método correspondente na classe SistemaAgenda. 
     * Obtém a resposta, transforma em ByteString.
     * @param args - ByteString: argumentos 
     * @return ByteString: resposta
     * @throws IOException - gerada pelo método editContato()
     */
    public ByteString editarContato(ByteString args) {
        //Desserializa
        Agenda agendaAuxiliar = null;
		try {
			agendaAuxiliar = Agenda.parseFrom(args.toByteArray());
		} catch (InvalidProtocolBufferException e) {
			System.out.println("InvalidProtocolBufferException server.AgendaEsqueleto: " + e.getMessage());
		}

        Boolean response = agenda.editarContato(agendaAuxiliar);

        return ByteString.copyFrom(response.toString().getBytes());
    }

    /**
     * Esqueleto do método removerContato() e chama o método correspondente na classe SistemaAgenda. 
     * Obtém a resposta, transforma em ByteString.
     * @param args - ByteString: argumentos 
     * @return - ByteString: resposta
     * @throws IOException - gerada pelo método removerContato()
     */
    public ByteString removerContato(ByteString args) {
    	Contato contato = null;
		try {
			contato = Contato.parseFrom(args.toByteArray());
		} catch (InvalidProtocolBufferException e) {
			System.out.println("InvalidProtocolBufferException server.AgendaEsqueleto: " + e.getMessage());
		}

    	Boolean response = agenda.removerContato(contato);
    	
    	return ByteString.copyFrom(response.toString().getBytes());
    }

    /**
     * Esqueleto do método cleanAgenda() e chama o método correspondente na classe SistemaAgenda. 
     * Obtém a resposta, transforma em ByteString.
     * @param args - ByteString: argumentos 
     * @return - ByteString: resposta
     * @throws IOException - gerada pelo método cleanAgenda()
     */
    public ByteString limparAgenda(ByteString args) {
        Boolean response = agenda.limparAgenda();
        return ByteString.copyFrom(response.toString().getBytes());
    }

}
