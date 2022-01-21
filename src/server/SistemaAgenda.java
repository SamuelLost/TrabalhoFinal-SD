package server;

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
    
	private FileInputStream lerArquivoEntrada() throws FileNotFoundException {
		FileInputStream inputStream = new FileInputStream(FILENAME);
    	return inputStream;
	}
	
	private FileOutputStream lerArquivoSaida() throws FileNotFoundException {
		FileOutputStream outputStream = new FileOutputStream(FILENAME);
    	return outputStream;
	}
	
    public Boolean addContato(Contato contato) throws IOException {
    	Agenda.Builder agenda = Agenda.newBuilder();

    	// ler arquivo
    	FileInputStream inputStream = lerArquivoEntrada();

		// Lê do arquivo e salva em agenda
		agenda.mergeFrom(inputStream);

		// fechar arquivo
		inputStream.close();

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
    	
    	// ler arquivo
    	FileOutputStream outputStream = lerArquivoSaida();

		// Lê da agenda e salva no arquivo
		agenda.build().writeTo(outputStream);

		// fechar arquivo
		outputStream.close();
		
    	return result;
    }

    public Agenda listarContato() throws IOException {
    	Agenda.Builder agenda = Agenda.newBuilder();
    	
    	// ler arquivo
    	FileInputStream inputStream = lerArquivoEntrada();

		// Lê do arquivo e salva em agenda
		agenda.mergeFrom(inputStream);

		// fechar arquivo
		inputStream.close();
		
    	return agenda.build();
    }

    public Agenda buscarContato(String busca) throws IOException {
    	Agenda.Builder agenda_retorno = Agenda.newBuilder();
    	
    	Agenda.Builder agenda = Agenda.newBuilder();
    	
    	// ler arquivo
    	FileInputStream inputStream = lerArquivoEntrada();

		// Lê do arquivo e salva em agenda
		agenda.mergeFrom(inputStream);

		// fechar arquivo
		inputStream.close();
		
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

    public Boolean removerContato(String nome) throws IOException {
    	Agenda.Builder agenda_retorno = Agenda.newBuilder();
    	
    	Agenda.Builder agenda = Agenda.newBuilder();
    	
    	// ler arquivo
    	FileInputStream inputStream = lerArquivoEntrada();

		// Lê do arquivo e salva em agenda
		agenda.mergeFrom(inputStream);

		// fechar arquivo
		inputStream.close();
		
		for (Contato contato : agenda.getContatosList()) {
			if (!contato.getNome().equals(nome)) {
				agenda_retorno.addContatos(contato);
			}
		}
		
    	// ler arquivo
    	FileOutputStream outputStream = lerArquivoSaida();

		// Lê da agenda e salva no arquivo
    	agenda_retorno.build().writeTo(outputStream);

		// fechar arquivo
		outputStream.close();
    	
    	return (agenda.build().getContatosCount() == agenda_retorno.build().getContatosCount() ? false : true);
    }

//    public void cleanAgenda() {
//        agenda.clear();
//    }

}
