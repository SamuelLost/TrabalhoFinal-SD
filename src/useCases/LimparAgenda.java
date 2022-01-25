package useCases;

import java.io.BufferedReader;

import com.google.protobuf.InvalidProtocolBufferException;

import client.Proxy;

public class LimparAgenda {
	private Proxy proxy;

	public LimparAgenda(Proxy proxy, BufferedReader stdin) {
		this.proxy = proxy;
	}

	public void limparAgenda() throws InvalidProtocolBufferException {
		Boolean result = proxy.limparAgenda();
		if (result) {
			System.out.println("Agenda limpa com sucesso");
		} else {
			System.out.println("Agenda já está vazia");
		}
	}
}
