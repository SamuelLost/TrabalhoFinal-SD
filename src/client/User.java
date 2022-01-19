package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.trabalhoFinal.protos.Contato;
import com.trabalhoFinal.protos.Contato.Endereco;
import com.trabalhoFinal.protos.Contato.Telefone;
import com.trabalhoFinal.protos.Contato.Email;

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
		case 1:
            Contato.Builder contato = Contato.newBuilder();
            
            System.out.println("Digite o nome do Contato:");
            contato.setNome(stdin.readLine());
            
            System.out.println("Digite o telefone do Contato:");
            Telefone.Builder telefone = Telefone.newBuilder();
            telefone.setTelefone(stdin.readLine());
            contato.addTelefone(telefone);
            
            System.out.println("Digite o endereço do Contato:");
            Endereco.Builder endereco = Endereco.newBuilder();
            endereco.setEndereco(stdin.readLine());
            contato.addEndereco(endereco);
            
            System.out.println("Digite o email do Contato:");
            Email.Builder email = Email.newBuilder();
            email.setEmail(stdin.readLine());
            contato.addEmails(email);
            
			Boolean retorno = proxy.addContato(contato.build());
			
			if (retorno) {
				System.out.println("Contato cadastrado com sucesso");
			} else {
				System.out.println("Contato não cadastrado");
			}
			
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