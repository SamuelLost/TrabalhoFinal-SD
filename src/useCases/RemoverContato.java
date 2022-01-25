package useCases;

import java.io.BufferedReader;
import java.io.IOException;

import com.trabalhoFinal.protos.AgendaProto.Contato;

import client.Proxy;

public class RemoverContato {
	private Proxy proxy;
	private BufferedReader stdin;

	public RemoverContato(Proxy proxy, BufferedReader stdin) {
		this.proxy = proxy;
		this.stdin = stdin;
	}
	
	public void removerContato() throws IOException {
		System.out.println("Digite o nome do contato: ");

		String nome = stdin.readLine();

		Contato.Builder contato = Contato.newBuilder();
		contato.setNome(nome);
		
		Boolean result = proxy.removerContato(contato.build());
		if (result) {
			System.out.println("Contato removido com sucesso");
		} else {
			System.out.println("Contato não removido");
		}
	}
}
