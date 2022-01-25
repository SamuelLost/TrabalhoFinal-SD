package useCases;

import java.io.BufferedReader;

import client.Proxy;

public class LimparAgenda {
	private Proxy proxy;

	public LimparAgenda(Proxy proxy, BufferedReader stdin) {
		this.proxy = proxy;
	}

    //Método para limpar a agenda, e consultar o resutado
	public void limparAgenda() {
		Boolean result = proxy.limparAgenda();
		if (result) {
			System.out.println("Agenda limpa com sucesso");
		} else {
			System.out.println("Agenda já está vazia");
		}
	}
}
