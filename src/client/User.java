package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.trabalhoFinal.protos.Contato;

import java.io.IOException;

public class User {
    Proxy proxy;
    
    public User(){
        proxy = new Proxy();
    }

    public int selecionaOperacao() throws IOException {

		int operacao = 0;

		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		String opt = null;
		do {
			opt = stdin.readLine();
		} while (opt.equals("\n") || opt.equals("") || opt.isEmpty());
		operacao = Integer.parseInt(opt);

		switch (operacao) {
		case 1: //Adicionar Contato
            Contato.Builder contato = Contato.newBuilder();
            System.out.println("Digite o nome do Contato:");
            //contato.setNome(stdin.readLine());
            System.out.println("Digite o numero:");
            //contato.setTelefone();
            System.out.println("Digite o endereço:");
            System.out.println("Digite o Email:");
			// Interagir com o usuario via stdin.readLine() para setar
			// argumentos de entada
			// ex:
			// System.out.println("Digite seu nome: ");
			// person.setName(stdin.readLine());

			// Por fim, chamar metodo do proxy correspondente à operação
			// escolhida
			// proxy.addPerson(person.build());

			break;

		case 2:

			break;

		case 3:
			break;

		case 0:
			proxy.finaliza();
			break;

		default:
			System.out.println("Operação invalida, tente outra.");
			break;
		}
		return operacao;
    }    
    public void printMenu() {
		System.out.println("\nDigite o n# da operação que deseja executar: ");
		System.out.println("1 - Adicionar Contato");
		System.out.println("2 - Listar Contatos");
		System.out.println("3 - Procurar Contato");
        System.out.println("4 - Editar Contato");
        System.out.println("5 - Limpar Agenda");
		System.out.println("0 - Sair\n");
	}
    public static void main(String[] args) {
        User bookClient = new User();
		int operacao = -1;
		do {
			bookClient.printMenu();
			try {
				operacao = bookClient.selecionaOperacao();
			} catch (IOException ex) {
				System.out.println("Escolha uma das operações pelo número");
			}
		} while (operacao != 0);
    }
}