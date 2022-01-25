package useCases;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.trabalhoFinal.protos.AgendaProto.Contato;
import com.trabalhoFinal.protos.AgendaProto.Contato.Email;
import com.trabalhoFinal.protos.AgendaProto.Contato.Endereco;
import com.trabalhoFinal.protos.AgendaProto.Contato.Telefone;

import client.Proxy;

public class RemoverContato {
	private Proxy proxy;
	private BufferedReader stdin;

	public RemoverContato(Proxy proxy, BufferedReader stdin) {
		this.proxy = proxy;
		this.stdin = stdin;
	}
	
    /**
     * Interação para remover um contato. Recebe o resultado e imprime na tela.
     * @throws IOException readLine()
     */
	public void removerContato() throws IOException {
        //Lista dos contatos existentes
		List<Contato> listaContatos = proxy.listarContatos();
		
		int index = 1;
        //Percorrendo o contato
		for (Contato _contato : listaContatos) {
			System.out.println("Index: " + index);
			System.out.println("Nome: " + _contato.getNome());
			System.out.println("Telefones: ");
            //Percorrendo os telefones do contato
			for (Telefone tel : _contato.getTelefonesList()) {
				System.out.println("- " + tel.getTelefone() + " " + tel.getType());
			}
			System.out.println("Endereços: ");
            //Percorrendo os endereços do contato
			for (Endereco end : _contato.getEnderecosList()) {
				System.out.println("- " + end.getEndereco() + " " + end.getType());
			}
			System.out.println("E-mails: ");
            //Percorrendo os emails do contato
			for (Email end : _contato.getEmailsList()) {
				System.out.println("- " + end.getEmail() + " " + end.getType());
			}
			index++;
			System.out.println("------------------------------------------");
		}
		
		System.out.println("Digite o index do contato que deseja remover: ");
		
		String indice = "";
		
		int indexContato = 1;

		while (true) {
			indice = stdin.readLine();
			boolean isNumeric =  indice.matches("[+-]?\\d*(\\.\\d+)?");
			if (!isNumeric || indice.isEmpty()) {
				System.out.println("Opção inválida! Digite o index do contato que deseja remover: ");
				continue;
			}
			indexContato = Integer.parseInt(indice);
			if (1 <= indexContato && indexContato <= listaContatos.size()) {
				break;
			}
			System.out.println("Opção inválida! Digite o index do contato que deseja remover: ");
		}
		
		indexContato--;
		
		Boolean result = proxy.removerContato(listaContatos.get(indexContato));

		if (result) {
			System.out.println("Contato removido com sucesso");
		} else {
			System.out.println("Contato não removido");
		}
	}
}
