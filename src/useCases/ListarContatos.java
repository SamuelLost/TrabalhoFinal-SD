package useCases;

import java.io.BufferedReader;
import java.util.List;

import com.trabalhoFinal.protos.AgendaProto.Contato;
import com.trabalhoFinal.protos.AgendaProto.Contato.Email;
import com.trabalhoFinal.protos.AgendaProto.Contato.Endereco;
import com.trabalhoFinal.protos.AgendaProto.Contato.Telefone;

import client.Proxy;

public class ListarContatos {
	private Proxy proxy;

	public ListarContatos(Proxy proxy, BufferedReader stdin) {
		this.proxy = proxy;
	}
	
    /**
     * Lista e imprime todos os contatos da agenda
     */
	public void listarContatos() {
        //Lista com os contatos existentes
		List<Contato> listaContatos = proxy.listarContatos();
        //Percorrendo os contatos
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
