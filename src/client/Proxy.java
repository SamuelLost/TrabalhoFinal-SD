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
    private UDPClient client;
    private int requestId;

    /**
     * Construtor da classe
     */
    public Proxy() {
        client = new UDPClient();
        requestId = 0;
    }
    
    /**
     * O método realiza a formação da requisição e obtém a resposta através do
     * método doOperation(). Após obter a resposta faz uma série de transformações
     * e retorna o resultado
     * @param contato - objeto Contato do .proto
     * @return Boolean: true se adicionar, false se já existir contato com o mesmo nome
     * @throws InvalidProtocolBufferException - exceção gerada pela API do Google ao serializar
     * e transformar em em ByteString
     */
    public Boolean addContato(Contato contato) throws InvalidProtocolBufferException {
        //Transformando em ByteString o objeto contato serializado em byte[]
        ByteString args = ByteString.copyFrom(contato.toByteArray());

        //Obtendo a resposta
        byte[] resp = doOperation("Agenda", "addContato", args);
        
        //Transformando a resposta em ByteString
        ByteString byteString = ByteString.copyFrom(resp);
        
        //Transformando em booleano
        Boolean response = Boolean.valueOf(new String(byteString.toByteArray()));
        
        return response;
    }

    /**
     * O método realiza a formação da requisição e obtém a resposta através do
     * método doOperation(). Faz a transformação da resposta para ByteString,
     * transforma para uma agenda e com a agenda é possível obter a uma lista
     * de contatos.
     * @return List<Contato> com os contatos
     * @throws InvalidProtocolBufferException - exceção gerada pela API do Google ao serializar
     * e transformar em em ByteString
     */
    public List<Contato> listarTodos() throws InvalidProtocolBufferException {

        //Obtendo a resposta
    	byte[] resp = doOperation("Agenda", "listarContatos", ByteString.copyFrom("".getBytes()));

        //Transformando a resposta em ByteString
    	ByteString byteString = ByteString.copyFrom(resp);
    	
        //Transformando a resposta em uma agenda de contatos
    	Agenda agenda_response = Agenda.parseFrom(byteString);
    	
        //Obtendo a lista de contatos presente na agenda_response
    	List<Contato> listaContatos = agenda_response.getContatosList(); 
    	
        return listaContatos;
    }

    /**
     * Monta a requisição e obtém a resposta atrável de doOperation().
     * @param busca - String para ser feita a busca
     * @return - List<Contato> com os contatos que se encaixam na busca
     * @throws InvalidProtocolBufferException - exceção gerada pela API do Google ao serializar
     * e transformar em em ByteString
     */
    public List<Contato> procContato(String busca) throws InvalidProtocolBufferException {
        byte[] resp = doOperation("Agenda", "buscarContatos", ByteString.copyFrom(busca.getBytes()));

    	ByteString byteString = ByteString.copyFrom(resp);
    	
    	Agenda agenda_response = Agenda.parseFrom(byteString);
    	
    	List<Contato> listaContatos = agenda_response.getContatosList(); 
    	
        return listaContatos;
    }

    // retorna true se conseguir editar, falso caso o contato não exista
    /**
     * Monta a requisição e obtém a resposta atrável de doOperation(). 
     * @param contato - objeto Contato do .proto
     * @return Boolean: true caso a edição tenha sido realiza, false caso
     * contrário.
     * @throws InvalidProtocolBufferException - exceção gerada pela API do Google ao serializar
     * e transformar em em ByteString
     */
    public Boolean editarContato(Contato contato) throws InvalidProtocolBufferException {

        ByteString args = ByteString.copyFrom(contato.toByteArray());

        byte[] resp = doOperation("Agenda", "editarContato", args);

        ByteString byteString = ByteString.copyFrom(resp);

        Boolean response = Boolean.valueOf(new String(byteString.toByteArray()));

        return response;
    }

    /**
     * Monta a requisição e obtém a resposta atrável de doOperation().
     * @param nome - String com o nome do Contato
     * @return Boolean: true caso tenha sido removido, false caso contrário
     * @throws InvalidProtocolBufferException - exceção gerada pela API do Google ao serializar
     * e transformar em em ByteString
     */
    public Boolean removerContato(String nome) throws InvalidProtocolBufferException {
        ByteString args = ByteString.copyFrom(nome.getBytes());

        byte[] resp = doOperation("Agenda", "removerContato", args);
        
        ByteString byteString = ByteString.copyFrom(resp);
        
        Boolean response = Boolean.valueOf(new String(byteString.toByteArray()));
        
        return response;
    }

    // limpar os contatos, não retorna nada
    /**
     * Monta a requisição e obtém a resposta atrável de doOperation().
     * @return Boolean: true caso tenha sido limpa, false caso contrário
     * @throws InvalidProtocolBufferException - exceção gerada pela API do Google ao serializar
     * e transformar em em ByteString
     */
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

    /**
     * Método que faz o empacotamento da requisição. Cria um objeto
     * Message do proto e vai setando seus atributos como o tipo 0 para
     * requisição, o id da requisição, o nome do Objeto referenciado, 
     * o nome do método e os argumentos. Após isso, é serializado com
     * toByteArray().
     * @param objectRef - A referência para o objeto
     * @param method - o nome do método
     * @param args - os argumentos para o método
     * @return - byte[]: a requisição empacota e serializada.
     */
    public byte[] empacotaMensagem(String objectRef, String method, ByteString args) {
        Message.Builder message = Message.newBuilder();
        message.setType(0);  // 0 -> request

        message.setId(requestId++);
        
        message.setObjReference(objectRef);
        
        message.setMethodId(method);
        
        message.setArgs(args);
        
        return message.build().toByteArray();
    }

    /**
     * O método faz a deserialização da resposta empacotada. 
     * A deserialização ocorre pelo método parseFrom() que recebe
     * o byte[] como argumento e retorna o objeto que estava serializado.
     * @param resposta - a resposta serializada
     * @return retorna a resposta empacota
     */
    public static Message desempacotaMensagem(byte[] resposta) {
    	Message message = null;
		try {
			message = Message.parseFrom(resposta);
		} catch (InvalidProtocolBufferException e) {
			System.out.println("InvalidProtocolBufferException " + e.getMessage());
		}
        return message;
    }

    /**
     * O método realiza a montagem da requisição, faz o empacotamento da requisição
     * e faz o envio para o servidor. Após enviar a requisição seta um time de 1.5 segundos
     * caso a resposta não seja enviada a tempo é feita uma retransmissão. São feitas no máximo
     * 10 retransmissões, caso nenhuma tenha obtido a resposta, provavelmente o servidor
     * esteja morto. 
     * @param objectRef - referência do objeto para montar a requisição
     * @param methodId - nome do método para montar a requisição
     * @param args - argumentos em ByteString 
     * @return - a resposta em byte[]
     */
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
