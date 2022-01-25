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
     * @return void
     * @throws IOException lançado pelo método mergeFrom
     */
	private void lerArquivoEntrada(Agenda.Builder agenda) throws IOException {
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
		agenda.mergeFrom(inputStream);
		
		inputStream.close();
	}
	
    /**
     * Método para ler arquivo de saída
     * @return void
     * @throws IOException lançado pelo método writeTo
     */
	private void escreverArquivoSaida(Agenda.Builder agenda) throws IOException {
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(FILENAME);
		} catch (FileNotFoundException e) {
			System.out.println(FILENAME + ": File not found.  Creating a new file.");
		}
		agenda.build().writeTo(outputStream);
		
		outputStream.close();
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
     * @param contato
     * @return
     * @throws IOExceptionlerArquivoEntrada
     */
    public Boolean adicionarContato(Contato contato) throws IOException {
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
     * 
     * @return
     * @throws IOException
     */
    public Agenda listarContatos() throws IOException {
    	Agenda.Builder agenda = Agenda.newBuilder();
    	
    	// ler arquivo
    	lerArquivoEntrada(agenda);

    	return agenda.build();
    }

    public Agenda procurarContato(Contato contato) throws IOException {
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

    public Boolean removerContato(Contato contato) throws IOException {
    	String nome = contato.getNome();
    	
		Agenda.Builder agenda = Agenda.newBuilder();
		
    	// ler arquivo
    	lerArquivoEntrada(agenda);

		int index = 0;
		for (Contato _contato : agenda.getContatosList()) {
			if (_contato.getNome().equals(nome)) {
				agenda.removeContatos(index);
				// escrever arquivo
				escreverArquivoSaida(agenda);
				return true;
			}
			index++;
		}
    	return false;
    }

    public Boolean limparAgenda() throws IOException {
	   Agenda.Builder agenda = Agenda.newBuilder();
		
	   // ler arquivo
	   lerArquivoEntrada(agenda);
	
	   agenda.clearContatos();
	   
	   // escrever arquivo
	   escreverArquivoSaida(agenda);
		   
	   if (agenda.build().getContatosCount() == 0) return true;
	   
	   else return false;
	}

	public Boolean editarContato(Contato contato) throws IOException {
		Agenda.Builder agenda = Agenda.newBuilder();
		// ler arquivo
		lerArquivoEntrada(agenda);

		int index = 0;
		for (Contato _contato : agenda.getContatosList()) {
			if (contato.getNome().equals(_contato.getNome())) {
				agenda.setContatos(index, contato);
				break;
			}
			index++;
		}

		// escrever arquivo
		escreverArquivoSaida(agenda);

		return true;
	}
}
