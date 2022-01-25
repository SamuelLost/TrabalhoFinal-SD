package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import useCases.AdicionarContato;
import useCases.EditarContato;
import useCases.LimparAgenda;
import useCases.ListarContatos;
import useCases.ProcurarContatos;
import useCases.RemoverContato;

import views.Tela;

import java.io.IOException;
import java.util.Scanner;

public class User {
	// Classe Proxy que disponibiliza os métodos
    Proxy proxy;
	
    // Métodos da agenda
	AdicionarContato adicionarContato;
	ListarContatos listarContato;
	ProcurarContatos procurarContatos;
	EditarContato editarContato;
	RemoverContato removerContato;
	LimparAgenda limparAgenda;
	
	// Leitura do teclado
	BufferedReader stdin;
    
    public User() {
    	stdin = new BufferedReader(new InputStreamReader(System.in));
    	
        proxy = new Proxy(); //Instanciando o objeto Proxy
        
        adicionarContato = new AdicionarContato(proxy, stdin);
        listarContato = new ListarContatos(proxy, stdin);
        procurarContatos = new ProcurarContatos(proxy, stdin);
        editarContato = new EditarContato(proxy, stdin);
        removerContato = new RemoverContato(proxy, stdin);
        limparAgenda = new LimparAgenda(proxy, stdin);
    }

    /**
     * Método para selecionar a operação que vai ser realizada
     * @return string com a operação realizada
     * @throws IOException - gerada se acontecer o erro na entrada
     * @throws InterruptedException - gerada caso a thread seja interrompida
     */
    public String selecionaOperacao() throws IOException, InterruptedException {		
		String opt = null;
		do {
			opt = stdin.readLine(); //Escolhendo a opção do menu
		} while (opt.equals("\n") || opt.equals("") || opt.isEmpty());

		Tela.limpaTela();
		
		switch (opt) {
			case "1":
				opt = "adicionarContato";
				adicionarContato.adicionaContato();
				break;
			case "2":
				opt = "listarContatos";
				listarContato.listarContatos();
				break;
			case "3":
				opt = "procurarContato";
				procurarContatos.procurarContatos();
				break;
			case "4":
				opt = "editarContato";
				editarContato.editarContato();
				break;
			case "5":
				opt = "removerContato";
				removerContato.removerContato();
				break;
			case "6":
				opt = "limparAgenda";
				limparAgenda.limparAgenda();
				break;
			case "0":
				opt = "exit";
				proxy.finaliza();
				System.out.println("Aplicação finalizada");
				return opt;
			default:
				System.out.println("Operação inválida. Tente novamente");
				return opt;
		}

		System.out.println("Aperte Enter para voltar ao menu...");
		stdin.readLine();
		Tela.limpaTela();

		return opt;
    }

    public static void main(String[] args) {
        User bookClient = new User();
		String operacao = "exit";
		do {
			Tela.printMenu();
			try {
				operacao = bookClient.selecionaOperacao();
			} catch (IOException ex) {
				System.out.println("Escolha uma das operações pelo número");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!operacao.equals("exit"));
    }
}