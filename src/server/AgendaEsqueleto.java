package server;

import java.io.IOException;

import com.google.protobuf.ByteString;
import com.trabalhoFinal.protos.AgendaProto.Agenda;
import com.trabalhoFinal.protos.AgendaProto.Contato;

public class AgendaEsqueleto {
    private static AgendaEsqueleto uniqueInstance;
    private static SistemaAgenda agenda;

    //Singleton
    public static synchronized AgendaEsqueleto getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new AgendaEsqueleto();
            agenda = new SistemaAgenda();
        }
        return uniqueInstance;
    }
    
    /**
     * Esqueleto do método addContato(), faz a desserialização do argumento
     * e chama o método correspondente na classe SistemaAgenda. Obtém a resposta,
     * transforma em ByteString.
     * @param args - ByteString: argumentos
     * @return - ByteString: resposta
     * @throws IOException - gerada pelos métodos addContato()
     */
    public ByteString addContato(ByteString args) throws IOException {

    	Contato contato = Contato.parseFrom(args.toByteArray());

    	Boolean response = agenda.addContato(contato);

    	return ByteString.copyFrom(response.toString().getBytes());
    }
    
    /**
     * Esqueleto do método listarContatos() e chama o método correspondente na classe SistemaAgenda. 
     * Obtém a resposta, transforma em ByteString.
     * @param args - ByteString: argumentos
     * @return - ByteString: resposta
     * @throws IOException - gerada pelo método listarContatos()
     */
    public ByteString listarContatos(ByteString args) throws IOException {
    	Agenda agenda_response = agenda.listarContato();

    	return ByteString.copyFrom(agenda_response.toByteArray());
    }

    /**
     * Esqueleto do método buscarContatos() e chama o método correspondente na classe SistemaAgenda. 
     * Obtém a resposta, transforma em ByteString.
     * @param args - ByteString: argumentos 
     * @return - ByteString: resposta
     * @throws IOException - gerada pelo método buscarContatos()
     */
    public ByteString buscarContatos(ByteString args) throws IOException{
    	Agenda agenda_response = agenda.buscarContato(new String(args.toByteArray()));

    	return ByteString.copyFrom(agenda_response.toByteArray());
    }

    /**
     * Esqueleto do método editarContato() e chama o método correspondente na classe SistemaAgenda. 
     * Obtém a resposta, transforma em ByteString.
     * @param args - ByteString: argumentos 
     * @return - ByteString: resposta
     * @throws IOException - gerada pelo método editContato()
     */
    public ByteString editarContato(ByteString args) throws IOException {
        //Desserializa
        Contato contato = Contato.parseFrom(args.toByteArray());

        Boolean response = agenda.editContato(contato);

        return ByteString.copyFrom(response.toString().getBytes());
    }

    /**
     * Esqueleto do método removerContato() e chama o método correspondente na classe SistemaAgenda. 
     * Obtém a resposta, transforma em ByteString.
     * @param args - ByteString: argumentos 
     * @return - ByteString: resposta
     * @throws IOException - gerada pelo método removerContato()
     */
    public ByteString removerContato(ByteString args) throws IOException{
    	Boolean response = agenda.removerContato(new String(args.toByteArray()));
    	
    	return ByteString.copyFrom(response.toString().getBytes());
    }

    /**
     * Esqueleto do método cleanAgenda() e chama o método correspondente na classe SistemaAgenda. 
     * Obtém a resposta, transforma em ByteString.
     * @param args - ByteString: argumentos 
     * @return - ByteString: resposta
     * @throws IOException - gerada pelo método cleanAgenda()
     */
    public ByteString cleanAgenda(ByteString args) throws IOException {
        Boolean response = agenda.cleanAgenda();
        return ByteString.copyFrom(response.toString().getBytes());
    }

}
