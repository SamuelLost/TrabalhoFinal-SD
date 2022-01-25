package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.trabalhoFinal.protos.AgendaProto.Agenda;
import com.trabalhoFinal.protos.AgendaProto.Contato;
import com.trabalhoFinal.protos.AgendaProto.Contato.Telefone;
import com.trabalhoFinal.protos.AgendaProto.Contato.Endereco;
import com.trabalhoFinal.protos.AgendaProto.Contato.Email;

public class SistemaAgenda {
    private static final String FILENAME = "agenda.txt"; 
    
    /**
     * Método para ler o arquivo de entrada para Agenda.Builder
     * @param agenda: builder de Agenda para ler do arquivo
     * @return void
     * @throws IOException lançado pelo método mergeFrom
     */
	private void lerArquivoEntrada(Agenda.Builder agenda) {
		File f = new File(FILENAME);
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				System.out.println("IOException: " + e.getMessage());
			}
		}
		
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			System.out.println(FILENAME + ": File not found.  Creating a new file.");
		}
		// Lê do arquivo e salva em agenda
		try {
			agenda.mergeFrom(inputStream);
			inputStream.close();
		} catch (IOException e) {
			System.out.println("IOException server.AgendaEsqueleto: " + e.getMessage());
		}
	}
	
    /**
     * Método para escrever no arquivo de saída
     * @param agenda: builder de Agenda para escrever no arquivo
     * @return void
     * @throws IOException lançado pelo método writeTo
     */
	private void escreverArquivoSaida(Agenda.Builder agenda) {
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(FILENAME);
		} catch (FileNotFoundException e) {
			System.out.println(FILENAME + ": File not found.  Creating a new file.");
		}
		try {
			agenda.build().writeTo(outputStream);
			outputStream.close();
		} catch (IOException e) {
			System.out.println("IOException server.AgendaEsqueleto: " + e.getMessage());
		}
	}
	
    /**
     * Método para adicionar o contato. Cria uma agenda, lê o arquivo
     * de entrada e salva o conteudo do arquivo em agenda através do
     * mergeFrom() desserializada e fecha o arquivo de entrada.
     * 
     * Após isso, percorre contato por contato e verifica se existe
     * algum contato com o mesmo nome, caso existe não é possível
     * adicionar. Caso contrário, o contato é adicionado na agenda
     * e a agenda é escrita serializada no arquivo através de writeTo().
     * @param contato - Contato que será adicionado na agenda
     * @return boolean - true caso tenha adicionado, false caso contrário
     */
    public Boolean adicionarContato(Contato contato) {
    	Agenda.Builder agenda = Agenda.newBuilder();

    	// ler arquivo
    	lerArquivoEntrada(agenda);

		Boolean result = true;
		for (Contato _contato : agenda.getContatosList()) {
			if (contato.getNome().equals(_contato.getNome())) {
				result = false;
			}
		}
		
		// adiciona novo contato
    	if (result) {
    		agenda.addContatos(contato);
    	}
    	
    	// escrever arquivo
    	escreverArquivoSaida(agenda);

    	return result;
    }

    /**
     * Método para listar os contatos da Agenda
     * @return Agenda - agenda com os contatos
     */
    public Agenda listarContatos() {
    	Agenda.Builder agenda = Agenda.newBuilder();
    	
    	// ler arquivo
    	lerArquivoEntrada(agenda);

    	return agenda.build();
    }

    /**
     * Método para buscar contatos na agenda. A busca será feita pelo nome, pelos telefones, endereços e e-mails.
     * @param contato - contato com o nome que será buscado na agenda
     * @return agenda com os contatos que foram encontrados com a busca
     */
    public Agenda procurarContato(Contato contato) {
    	String busca = contato.getNome();
    	
    	Agenda.Builder agenda_retorno = Agenda.newBuilder();
    	
    	Agenda.Builder agenda = Agenda.newBuilder();
    	
    	// ler arquivo
    	lerArquivoEntrada(agenda);
		
		for (Contato _contato : agenda.getContatosList()) {
			boolean contain = false;
			if (_contato.getNome().contains(busca)) {
				contain = true;
			}
			for (Telefone tel : _contato.getTelefonesList()) {
				if (tel.getTelefone().contains(busca)) {
					contain = true;
				}
			}
			for (Endereco end : _contato.getEnderecosList()) {
				if (end.getEndereco().contains(busca)) {
					contain = true;
				}
			}
			for (Email end : _contato.getEmailsList()) {
				if (end.getEmail().contains(busca)) {
					contain = true;
				}
			}
			if (contain) {
				agenda_retorno.addContatos(_contato);
			}
		}
		
        return agenda_retorno.build();
    }

    /**
     * Método para remover um contato da agenda.
     * @param contato - objeto contato que será removido da agenda
     * @return booleano - true caso tenha dado certo remover, false caso contrário
     */
    public Boolean removerContato(Contato contato) {
		Agenda.Builder agenda = Agenda.newBuilder();
		
    	// ler arquivo
    	lerArquivoEntrada(agenda);

		int index = 0;
		for (Contato _contato : agenda.getContatosList()) {
			if (_contato.equals(contato)) {
				agenda.removeContatos(index);
				// escrever arquivo
				escreverArquivoSaida(agenda);
				return true;
			}
			index++;
		}
    	return false;
    }

    /**
     * Método para remover todos os contatos da agenda
     * @return booleano - true caso tenha dado certo limpar a agenda, false caso contrário
     */
    public Boolean limparAgenda() {
	   Agenda.Builder agenda = Agenda.newBuilder();
		
	   // ler arquivo
	   lerArquivoEntrada(agenda);
	
	   agenda.clearContatos();
	   
	   // escrever arquivo
	   escreverArquivoSaida(agenda);
		   
	   if (agenda.build().getContatosCount() == 0) return true;
	   
	   else return false;
	}

    /**
     * Método para editar um contato da agenda.
     * @param agendaAuxiliar - agenda com o contato na posição 0 sendo o antigo, e na posição 1 sendo o atual
     * @return booleano - true caso tenha dado certo editar, false caso contrário
     */    
	public Boolean editarContato(Agenda agendaAuxiliar) {
		Contato oldContato = agendaAuxiliar.getContatos(0);
		Contato newContato = agendaAuxiliar.getContatos(1);
		
		Agenda.Builder agenda = Agenda.newBuilder();
		// ler arquivo
		lerArquivoEntrada(agenda);

		int index = 0;
		for (Contato _contato : agenda.getContatosList()) {
			if (_contato.equals(oldContato)) {
				agenda.setContatos(index, newContato);
				break;
			}
			index++;
		}

		// escrever arquivo
		escreverArquivoSaida(agenda);

		return (index == agenda.getContatosCount() ? false : true);
	}
}
