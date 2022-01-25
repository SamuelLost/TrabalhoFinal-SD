package useCases;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.trabalhoFinal.protos.AgendaProto.Contato;
import com.trabalhoFinal.protos.AgendaProto.Contato.Email;
import com.trabalhoFinal.protos.AgendaProto.Contato.Endereco;
import com.trabalhoFinal.protos.AgendaProto.Contato.Telefone;

import client.Proxy;

public class ProcurarContatos {
	private Proxy proxy;
	private BufferedReader stdin;

	public ProcurarContatos(Proxy proxy, BufferedReader stdin) {
		this.proxy = proxy;
		this.stdin = stdin;
	}
	
    /**
     * Interação para procurar um contato. Recebe o retorno e imprime na tela
     * @throws IOException - readLine()
     */
	public void procurarContatos() throws IOException {
		System.out.println("Digite sua busca: ");
		
		String busca = stdin.readLine();
		
		Contato.Builder contato = Contato.newBuilder();
		contato.setNome(busca);
		
        //Lista com os contatos que contém a string de busca
		List<Contato> listaContatos = proxy.procurarContatos(contato.build());

		System.out.println("Quantidade: " + listaContatos.size());

        //Percorrendo contato
		for (Contato _contato : listaContatos) {
			System.out.println("Nome: " + _contato.getNome());
			System.out.println("Telefones: ");
            //Percorrendo os telefones do contato
			for (Telefone tel : _contato.getTelefonesList()) {
				System.out.println("- " + tel.getTelefone() + " Tipo:" + tel.getType());
			}
			System.out.println("Endereços: ");
            //Percorrendo os endereços do contato
			for (Endereco end : _contato.getEnderecosList()) {
				System.out.println("- " + end.getEndereco() + " Tipo:" + end.getType());
			}
			System.out.println("E-mails: ");
            //Percorrendo os emails do contato
			for (Email end : _contato.getEmailsList()) {
				System.out.println("- " + end.getEmail() + " Tipo:" + end.getType());
			}
			System.out.println("------------------------------------------");
		}
	}
}
