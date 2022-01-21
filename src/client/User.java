package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.trabalhoFinal.protos.AgendaProto.Contato;
import com.trabalhoFinal.protos.AgendaProto.Contato.*;

import java.io.IOException;
import java.util.Scanner;

public class User {
    Proxy proxy;
	Scanner scanner = new Scanner(System.in);
	String input;
    
    public User(){
        proxy = new Proxy();
    }

    public String selecionaOperacao() throws IOException, InterruptedException {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		String opt = null;
		do {
			opt = stdin.readLine();
		} while (opt.equals("\n") || opt.equals("") || opt.isEmpty());

		switch (opt) {
		case "1": //addContato
		{
            Contato.Builder contato = Contato.newBuilder();
			String type;
            
            System.out.println("Digite o nome do Contato:");
            contato.setNome(stdin.readLine());
            
            System.out.println("Digite o telefone do Contato:");
            Telefone.Builder telefone = Telefone.newBuilder();
            telefone.setTelefone(stdin.readLine());
			System.out.println("Digite o tipo do telefone(Mobile, Personal, Home, Work):");
			type = stdin.readLine();
			if (type.equals("Mobile") || type.equals("mobile")) telefone.setTypeValue(0);
			else if (type.equals("Personal") || type.equals("personal")) telefone.setTypeValue(1);
			else if (type.equals("Home") || type.equals("home")) telefone.setTypeValue(2);
			else if (type.equals("Work") || type.equals("work")) telefone.setTypeValue(3);
            contato.addTelefones(telefone);
            
            System.out.println("Digite o endereço do Contato:");
            Endereco.Builder endereco = Endereco.newBuilder();
            endereco.setEndereco(stdin.readLine());
			System.out.println("Digite o tipo do Endereço(Mobile, Personal, Home, Work):");
			type = stdin.readLine();
			if (type.equals("Mobile") || type.equals("mobile")) endereco.setTypeValue(0);
			else if (type.equals("Personal") || type.equals("personal")) endereco.setTypeValue(1);
			else if (type.equals("Home") || type.equals("home")) endereco.setTypeValue(2);
			else if (type.equals("Work") || type.equals("work")) endereco.setTypeValue(3);
            contato.addEnderecos(endereco);
            
            System.out.println("Digite o email do Contato:");
            Email.Builder email = Email.newBuilder();
            email.setEmail(stdin.readLine());
			System.out.println("Digite o tipo do Email(Mobile, Personal, Home, Work):");
			type = stdin.readLine();
			if (type.equals("Mobile") || type.equals("mobile")) email.setTypeValue(0);
			else if (type.equals("Personal") || type.equals("personal")) email.setTypeValue(1);
			else if (type.equals("Home") || type.equals("home")) email.setTypeValue(2);
			else if (type.equals("Work") || type.equals("work")) email.setTypeValue(3);
            contato.addEmails(email);
            
			if (proxy.addContato(contato.build())) {
				System.out.println("Contato cadastrado com sucesso");
			} else {
				System.out.println("Sem sucesso. Contato já existe");
			}
			System.out.println("Aperte Enter para voltar ao menu...");
			input = scanner.nextLine();

			System.out.print("\033[H\033[2J");//Runtime.getRuntime().exec("clear");
			System.out.flush();
			break;
		}
		case "2": //listarContatos
		{
			List<Contato> listaContatos = proxy.listarTodos();
			for (Contato _contato : listaContatos) {
				System.out.println("Nome: " + _contato.getNome());
				System.out.println("Telefones: ");
				for (Telefone tel : _contato.getTelefonesList()) {
					System.out.println("- " + tel.getTelefone() + " Tipo:" + tel.getType());
				}
				System.out.println("Endereços: ");
				for (Endereco end : _contato.getEnderecosList()) {
					System.out.println("- " + end.getEndereco() + " Tipo:" + end.getType());
				}
				System.out.println("E-mails: ");
				for (Email end : _contato.getEmailsList()) {
					System.out.println("- " + end.getEmail() + " Tipo:" + end.getType());
				}
				System.out.println("------------------------------------------");
			}
			System.out.println("Aperte Enter para voltar ao menu...");
			input = scanner.nextLine();

			System.out.print("\033[H\033[2J");//Runtime.getRuntime().exec("clear");
			System.out.flush();

			break;
		}


		case "3": //procContatos
		{
			System.out.println("Digite sua busca");
			String busca = stdin.readLine();

			List<Contato> listaContatos = proxy.procContato(busca);

			System.out.println("Quantidade: " + listaContatos.size());

			for (Contato _contato : listaContatos) {
				System.out.println("Nome: " + _contato.getNome());
				System.out.println("Telefones: ");
				for (Telefone tel : _contato.getTelefonesList()) {
					System.out.println("- " + tel.getTelefone() + " Tipo:" + tel.getType());
				}
				System.out.println("Endereços: ");
				for (Endereco end : _contato.getEnderecosList()) {
					System.out.println("- " + end.getEndereco() + " Tipo:" + end.getType());
				}
				System.out.println("E-mails: ");
				for (Email end : _contato.getEmailsList()) {
					System.out.println("- " + end.getEmail() + " Tipo:" + end.getType());
				}
			}
			System.out.println("Aperte Enter para voltar ao menu...");
			input = scanner.nextLine();

			System.out.print("\033[H\033[2J");//Runtime.getRuntime().exec("clear");
			System.out.flush();
			break;
		}
			/*case "4": //editContato
			{
				List<Contato> listaContatos = proxy.listarTodos();
				int index=0;
				String aux;
				for (Contato _contato : listaContatos) {
					System.out.println("Index:" + index);
					System.out.println("Nome: " + _contato.getNome());
					System.out.println("Telefones: ");
					for (Telefone tel : _contato.getTelefonesList()) {
						System.out.println("- " + tel.getTelefone() + " " + tel.getType());
					}
					System.out.println("Endereços: ");
					for (Endereco end : _contato.getEnderecosList()) {
						System.out.println("- " + end.getEndereco() + " " + end.getType());
					}
					System.out.println("E-mails: ");
					for (Email end : _contato.getEmailsList()) {
						System.out.println("- " + end.getEmail() + " " + end.getType());
					}
					index++;
					System.out.println("------------------------------------------");
				}
				System.out.println("Digite o index do numero que deseja editar:");
				index = Integer.parseInt(stdin.readLine());

				Contato.Builder contato = Contato.newBuilder();
				String type;

				System.out.println("Deseja alterar o nome do Contato? Senão aperte ENTER:");
				aux = stdin.readLine();
				if (aux.equals(""))contato.setNome(listaContatos.get(index).getNome());
				else contato.setNome(aux);
				System.out.println("Deseja alterar o Telefone do Contato? Senão aperte ENTER:");
				aux = stdin.readLine();
				Telefone.Builder telefone = Telefone.newBuilder();
				if (aux.equals(""))telefone.setTelefone(listaContatos.get(index).getTelefones(0).getTelefone());
				else telefone.setTelefone(aux );
				System.out.println("Dseja editar o tipo do telefone(Mobile, Personal, Home, Work)? Senão aperte ENTER:");
				type = stdin.readLine();
				if(type.equals("")) telefone.setTypeValue(listaContatos.get(index).getTelefones(0).getTypeValue());
				else {
					if (type.equals("Mobile") || type.equals("mobile")) telefone.setTypeValue(0);
					else if (type.equals("Personal") || type.equals("personal")) telefone.setTypeValue(1);
					else if (type.equals("Home") || type.equals("home")) telefone.setTypeValue(2);
					else if (type.equals("Work") || type.equals("work")) telefone.setTypeValue(3);
				}
				contato.addTelefones(telefone);

				System.out.println("Deseja alterar o Endereço do Contato? Senão aperte ENTER:");
				aux = stdin.readLine();
				Endereco.Builder endereco = Endereco.newBuilder();
				if (aux.equals(""))endereco.setEndereco(listaContatos.get(index).getEnderecos(0).getEndereco());
				else endereco.setEndereco(aux );
				System.out.println("Dseja editar o tipo do Endereço(Mobile, Personal, Home, Work)? Senão aperte ENTER:");
				type = stdin.readLine();
				if(type.equals("")) endereco.setTypeValue(listaContatos.get(index).getEnderecos(0).getTypeValue());
				else {
					if (type.equals("Mobile") || type.equals("mobile")) endereco.setTypeValue(0);
					else if (type.equals("Personal") || type.equals("personal")) endereco.setTypeValue(1);
					else if (type.equals("Home") || type.equals("home")) endereco.setTypeValue(2);
					else if (type.equals("Work") || type.equals("work")) endereco.setTypeValue(3);
				}
				contato.addEnderecos(endereco);

				System.out.println("Deseja alterar o Email do Contato? Senão aperte ENTER:");
				aux = stdin.readLine();
				Email.Builder email = Email.newBuilder();
				if (aux.equals(""))email.setEmail(listaContatos.get(index).getEmails(0).getEmail());
				else email.setEmail(aux);
				System.out.println("Dseja editar o tipo do Email(Mobile, Personal, Home, Work)? Senão aperte ENTER:");
				type = stdin.readLine();
				if(type.equals("")) email.setTypeValue(listaContatos.get(index).getEmails(0).getTypeValue());
				else {
					if (type.equals("Mobile") || type.equals("mobile")) email.setTypeValue(0);
					else if (type.equals("Personal") || type.equals("personal")) email.setTypeValue(1);
					else if (type.equals("Home") || type.equals("home")) email.setTypeValue(2);
					else if (type.equals("Work") || type.equals("work")) email.setTypeValue(3);
				}

				contato.addEmails(email);
				if (proxy.editarContato(contato.build())) {
					System.out.println("Contato cadastrado com sucesso");
				} else {
					System.out.println("Sem sucesso. Contato já existe");
				}

				System.out.println("Aperte Enter para voltar ao menu...");
				input = scanner.nextLine();

				System.out.print("\033[H\033[2J");//Runtime.getRuntime().exec("clear");
				System.out.flush();

				break;



			}*/

		case "5": //rmContato
		{
			System.out.println("Digite o nome para remover");
			String nome = stdin.readLine();
			Boolean result = proxy.removerContato(nome);
			if (result) {
				System.out.println("Contato removido com sucesso");
			} else {
				System.out.println("Contato não removido");
			}
			System.out.println("Aperte Enter para voltar ao menu...");
			input = scanner.nextLine();

			System.out.print("\033[H\033[2J");//Runtime.getRuntime().exec("clear");
			System.out.flush();
			break;
		}

		case "6": //limparAgenda
			{
				Boolean result = proxy.limparAgenda();
				if (result) {
					System.out.println("Agenda limpa com sucesso");
				} else {
					System.out.println("Agenda já limpa");
				}
				System.out.println("Aperte Enter para voltar ao menu...");
				input = scanner.nextLine();

				System.out.print("\033[H\033[2J");//Runtime.getRuntime().exec("clear");
				System.out.flush();
				break;
			}

		case "0":
			System.out.println("Finalizando aplicação");
			proxy.finaliza();
			opt = "exit";
			break;

		default:
			System.out.println("Operação invalida, tente novamente");
			break;
		}
		return opt;
    }    
    public void printMenu() {
		System.out.println("\nDigite o n# da operação que deseja executar: ");
		System.out.println("1 - Adicionar Contato");
		System.out.println("2 - Listar Contatos");
		System.out.println("3 - Procurar Contato");
        System.out.println("4 - Editar Contato");
        System.out.println("5 - Remover Contato");
        System.out.println("6 - Limpar Agenda");
		System.out.println("0 - Sair\n");
	}
    public static void main(String[] args) {
        User bookClient = new User();
		String operacao = "exit";
		do {
			bookClient.printMenu();
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