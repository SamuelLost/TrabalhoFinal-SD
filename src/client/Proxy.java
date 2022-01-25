package client;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import exceptions.NullNameException;

import com.trabalhoFinal.protos.AgendaProto.Agenda;
import com.trabalhoFinal.protos.AgendaProto.Contato;
import com.trabalhoFinal.protos.MessageProto.Message;

public class Proxy {	
	private int requestId = 0;
    private UDPClient client;

    /**
     * Construtor da classe
     */
    public Proxy() {
        client = new UDPClient();
    }
    
    /**
     * Método para adicionar um contato na agenda. Chama o método doOperation que envia os argumentos
     * e monta o Message para ser enviado. Recupera a resposta na forma de booleano.
     * @param contato - objeto Contato para ser adicionado
     * @return Boolean - true se adicionar, false se já existir contato com o mesmo nome
     * @throws NullNameException Nome não pode ser vazio
     */
    public Boolean adicionarContato(Contato contato) throws NullNameException {
    	if (contato.getNome().isEmpty()) {
    		throw new NullNameException();
    	}
    	
        //Transformando em ByteString o objeto contato serializado em byte[]
        ByteString args = contato.toByteString();

        //Obtendo a resposta
        byte[] resp = doOperation("Agenda", "adicionarContato", args);
        
        //Transformando a resposta em ByteString
        ByteString byteString = ByteString.copyFrom(resp);
        
        //Transformando em booleano
        Boolean response = Boolean.valueOf(new String(byteString.toByteArray()));
        
        return response;
    }

    /**
     * Método para listar os contatos da Agenda. Chama o método doOperation que envia os argumentos
     * e monta o Message para ser enviado. Recupera a resposta na forma de lista de contatos.
     * @return List - lista com os contatos da agenda
     */
    public List<Contato> listarContatos() {
        //Obtendo a resposta
    	byte[] resp = doOperation("Agenda", "listarContatos", ByteString.copyFrom("listarContatos".getBytes()));

        //Transformando a resposta em ByteString
    	ByteString byteString = ByteString.copyFrom(resp);
    	
        //Transformando a resposta em uma agenda de contatos
    	Agenda agenda_response = null;
		try {
			agenda_response = Agenda.parseFrom(byteString);
		} catch (InvalidProtocolBufferException e) {
			System.out.println("InvalidProtocolBufferException - client.Proxy: " + e.getMessage());
		}
    	
        //Obtendo a lista de contatos presente na agenda_response
    	List<Contato> listaContatos = agenda_response.getContatosList(); 
    	
        return listaContatos;
    }

    /**
     * Método para buscar contatos na Agenda. Chama o método doOperation que envia os argumentos
     * e monta o Message para ser enviado. Recupera a resposta na forma de lista de contatos.
     * @param contato - contato com o nome a ser buscado na agenda
     * @return List - com os contatos que se encaixam na busca
     */
    public List<Contato> procurarContatos(Contato contato) {
        //Transformando em ByteString o objeto contato serializado em byte[]
        ByteString args = contato.toByteString();
    	
        byte[] resp = doOperation("Agenda", "procurarContatos", args);

    	ByteString byteString = ByteString.copyFrom(resp);
    	
    	Agenda agenda_response = null;
		try {
			agenda_response = Agenda.parseFrom(byteString);
		} catch (InvalidProtocolBufferException e) {
			System.out.println("InvalidProtocolBufferException - client.Proxy: " + e.getMessage());
		}
    	
    	List<Contato> listaContatos = agenda_response.getContatosList(); 
    	
        return listaContatos;
    }
    
    /**
     * Método para editar um contato da Agenda. Chama o método doOperation que envia os argumentos
     * e monta o Message para ser enviado. Recupera a resposta na forma de booleano.
     * @param agendaAuxiliar - agenda com o contato na posição 0 como sendo o contato anterior, e na posição 1 como sendo o novo
     * @return Boolean - true caso a edição tenha sido realizada com sucesso, false caso contrário
     * @throws NullNameException Nome não pode ser vazio
     */
    public Boolean editarContato(Agenda agendaAuxiliar) throws NullNameException {
    	if (agendaAuxiliar.getContatos(1).getNome().isEmpty()) {
    		throw new NullNameException();
    	}
    	
        ByteString args = agendaAuxiliar.toByteString();

        byte[] resp = doOperation("Agenda", "editarContato", args);

        ByteString byteString = ByteString.copyFrom(resp);

        Boolean response = Boolean.valueOf(new String(byteString.toByteArray()));

        return response;
    }

    /**
     * Método para remover um contato da Agenda. Chama o método doOperation que envia os argumentos
     * e monta o Message para ser enviado. Recupera a resposta na forma de booleano.
     * @param contato - objeto do contato que será removido da agenda 
     * @return Boolean - true caso tenha sido removido, false caso contrário
     * @throws NullNameException Nome não pode ser vazio
     */
    public Boolean removerContato(Contato contato) {
        ByteString args = contato.toByteString();

        byte[] resp = doOperation("Agenda", "removerContato", args);
        
        ByteString byteString = ByteString.copyFrom(resp);
        
        Boolean response = Boolean.valueOf(new String(byteString.toByteArray()));
        
        return response;
    }

    /**
     * Método para remover todos os contatos da Agenda. Chama o método doOperation que envia os argumentos
     * e monta o Message para ser enviado. Recupera a resposta na forma de booleano.
     * @return Boolean - true caso dê certo limpar a agenda, fase caso contrário
     */
    public Boolean limparAgenda() {
        ByteString args = ByteString.copyFrom(("limparAgenda").getBytes());
        
        byte[] resp = doOperation("Agenda", "limparAgenda", args);

        ByteString byteString = ByteString.copyFrom(resp);

        Boolean response = Boolean.valueOf(new String(byteString.toByteArray()));

        return response;
    }

    // finaliza o cliente udp
    public void finaliza() {
    	client.close();
    }

    /**
     * Método que faz o empacotamento da requisição. Cria um objeto
     * Message do proto e vai setando seus atributos como o tipo 0 para
     * requisição, o id da requisição, o nome do Objeto referenciado, 
     * o nome do método e os argumentos. Após isso, é serializado com
     * toByteArray().
     * @param objectRef: A referência para o objeto
     * @param method: o nome do método
     * @param args: os argumentos para o método
     * @return byte[]: a requisição empacotada e serializada.
     */
    public byte[] empacotaMenssagem(String objectRef, String method, ByteString args) {
        Message.Builder message = Message.newBuilder()
	    		.setType(0)
	    		.setId(requestId++)
	    		.setObjReference(objectRef)
	    		.setMethodId(method)
	    		.setArgs(args);
        return message.build().toByteArray();
    }

    /**
     * O método faz a desserialização da resposta empacotada. 
     * A desserialização ocorre pelo método parseFrom() que recebe
     * o byte[] como argumento e retorna o objeto que estava serializado.
     * @param resposta: a resposta serializada
     * @return retorna a resposta empacotada
     */
    public static Message desempacotaMenssagem(byte[] resposta) {
    	Message message = null;
		try {
			message = Message.parseFrom(resposta);
		} catch (InvalidProtocolBufferException e) {
			System.out.println("InvalidProtocolBufferException client.Proxy: " + e.getMessage());
		}
        return message;
    }

    /**
     * O método realiza a montagem da requisição, faz o empacotamento da requisição
     * e faz o envio para o servidor. Após enviar a requisição caso a resposta não seja
     * recebida em menos de 1500ms é feita uma retransmissão. São feitas no máximo
     * 10 retransmissões, caso nenhuma tenha obtido a resposta, paramos de enviar a requisição.
     * @param objectRef: referência do objeto para montar a requisição
     * @param methodId: nome do método para montar a requisição
     * @param args: argumentos em ByteString 
     * @return a resposta em byte[]
     */
	public byte[] doOperation(String objectRef, String methodId, ByteString args) {
    	byte[] bytes_request = empacotaMenssagem(objectRef, methodId, args);
        
        while (true) {
        	client.sendResquest(bytes_request);
        	try {
        		Message response_message = desempacotaMenssagem(client.getResponse());
        		if (response_message.getId() == (requestId - 1)) {
        			return response_message.getArgs().toByteArray();
        		}
        	} catch (SocketTimeoutException e) {
				System.out.println("SocketTimeoutException " + e.getMessage());
				System.out.println("Timeout exceeded");
        	} catch (IOException e) {
        		System.out.println("IOException " + e.getMessage());
			}
        }
	}
}
