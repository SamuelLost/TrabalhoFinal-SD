package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.trabalhoFinal.protos.AgendaProto.Contato;
import com.trabalhoFinal.protos.AgendaProto.Contato.*;

import views.Tela;

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
			Tela.limpaTela();
            Contato.Builder contato = Contato.newBuilder();
			String type;
            
            System.out.println("Digite o nome do Contato:");
            contato.setNome(stdin.readLine());
            
            System.out.println("Digite o telefone do Contato:");
            Telefone.Builder telefone = Telefone.newBuilder();
            while(true) {
            	String num = stdin.readLine();
            	if(num.length() == 0) break;
            	else {
            		if(Validation.validationTelefone(num)) telefone.setTelefone(num);
                    else {
                    	System.out.println("Caracteres inválidos. Digite novamente:");
                    	telefone.setTelefone(stdin.readLine());
                    }
        			System.out.println("Digite o tipo do telefone (1 - Mobile, 2 - Personal, 3 - Home, 4 - Work):");
        			type = stdin.readLine();
        			if (type.equals("1") || type.equals("Mobile") || type.equals("mobile")) telefone.setTypeValue(0);
        			else if (type.equals("2") || type.equals("Personal") || type.equals("personal")) telefone.setTypeValue(1);
        			else if (type.equals("3") || type.equals("Home") || type.equals("home")) telefone.setTypeValue(2);
        			else if (type.equals("4") || type.equals("Work") || type.equals("work")) telefone.setTypeValue(3);
                    contato.addTelefones(telefone);
            	}
                System.out.println("Digite outro telefone do Contato ou ENTER para o próximo:");
            }
            
            
            System.out.println("Digite o endereço do Contato:");
            Endereco.Builder endereco = Endereco.newBuilder();
            while(true) {
            	String end = stdin.readLine();
            	if(end.length() == 0) break;
            	else {
            		endereco.setEndereco(end);
        			System.out.println("Digite o tipo do Endereço (1 - Home, 2 - Work):");
        			type = stdin.readLine();
        			if (type.equals("1") || type.equals("Home") || type.equals("home")) endereco.setTypeValue(1);
        			else if (type.equals("2") || type.equals("Work") || type.equals("work")) endereco.setTypeValue(3);
                    contato.addEnderecos(endereco);
            	}
            	System.out.println("Digite outro endereço do Contato ou ENTER para o próximo:");
            }
            
            System.out.println("Digite o email do Contato:");
            Email.Builder email = Email.newBuilder();
            while(true) {
            	String email_in = stdin.readLine();
            	if(email_in.length() == 0) break;
            	else {
            		if(Validation.validationEmail(email_in)) email.setEmail(email_in);
                    else {
                    	System.out.println("Email inválido. Digite novamente:");
                    	email.setEmail(stdin.readLine());
                    }
                    
        			System.out.println("Digite o tipo do Email (1 - Personal, 2 - Work):");
        			type = stdin.readLine();
        			if (type.equals("1") || type.equals("Personal") || type.equals("personal")) email.setTypeValue(1);
        			else if (type.equals("1") || type.equals("Work") || type.equals("work")) email.setTypeValue(3);
                    contato.addEmails(email);
            	}
            	System.out.println("Digite outro email do Contato ou ENTER para o próximo:");
            }
            
			if (proxy.addContato(contato.build())) {
				System.out.println("Contato cadastrado com sucesso");
			} else {
				System.out.println("Sem sucesso. Contato já existe");
			}
			System.out.println("Aperte Enter para voltar ao menu...");
			input = scanner.nextLine();

			Tela.limpaTela();
			break;
		}
		case "2": //listarContatos
		{
			Tela.limpaTela();
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

			Tela.limpaTela();

			break;
		}


		case "3": //procContatos
		{
			Tela.limpaTela();
			System.out.println("Digite sua busca: ");
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

			Tela.limpaTela();
			break;
		}
			case "4": //editContato
			{
				Tela.limpaTela();
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
				System.out.println("Digite o index do contato que deseja editar: ");
				index = Integer.parseInt(stdin.readLine());
				Contato.Builder contato = Contato.newBuilder();
				String type;

				contato.setNome(listaContatos.get(index).getNome());
				System.out.println("Deseja alterar o Telefone do Contato? Senão aperte ENTER:");
				aux = stdin.readLine();
				Telefone.Builder telefone = Telefone.newBuilder();
				if (aux.equals("")) telefone.setTelefone(listaContatos.get(index).getTelefones(0).getTelefone());
				else {
					if(Validation.validationTelefone(aux)) telefone.setTelefone(aux);
					else {
						System.out.println("Caracteres inválidos. Digite novamente:");
		            	telefone.setTelefone(stdin.readLine());
					}
				
				}
				System.out.println("Deseja editar o tipo do telefone (1 - Mobile, 2 - Personal, 3 - Home, 4 - Work)? Senão aperte ENTER:  ");
				type = stdin.readLine();
				if(type.equals("")) telefone.setTypeValue(listaContatos.get(index).getTelefones(0).getTypeValue());
				else {
					if (type.equals("1") || type.equals("Mobile") || type.equals("mobile")) telefone.setTypeValue(0);
					else if (type.equals("2") || type.equals("Personal") || type.equals("personal")) telefone.setTypeValue(1);
					else if (type.equals("3") || type.equals("Home") || type.equals("home")) telefone.setTypeValue(2);
					else if (type.equals("4") || type.equals("Work") || type.equals("work")) telefone.setTypeValue(3);
				}
				contato.addTelefones(telefone);

				System.out.println("Deseja alterar o endereço do Contato? Senão aperte ENTER:");
				aux = stdin.readLine();
				Endereco.Builder endereco = Endereco.newBuilder();
				if (aux.equals(""))endereco.setEndereco(listaContatos.get(index).getEnderecos(0).getEndereco());
				else endereco.setEndereco(aux );
				System.out.println("Dseja editar o tipo do Endereço (1 - Home, 2 - Work)? Senão aperte ENTER:");
				type = stdin.readLine();
				if(type.equals("")) endereco.setTypeValue(listaContatos.get(index).getEnderecos(0).getTypeValue());
				else {
					if (type.equals("1") || type.equals("Personal") || type.equals("personal")) endereco.setTypeValue(1);
					else if (type.equals("2") || type.equals("Work") || type.equals("work")) endereco.setTypeValue(3);
				}
				contato.addEnderecos(endereco);

				System.out.println("Deseja alterar o Email do Contato? Senão aperte ENTER:");
				aux = stdin.readLine();
				Email.Builder email = Email.newBuilder();
				if (aux.equals(""))email.setEmail(listaContatos.get(index).getEmails(0).getEmail());
				else {
					if(Validation.validationEmail(aux)) email.setEmail(aux);
		            else {
		            	System.out.println("Email inválido. Digite novamente:");
		            	email.setEmail(stdin.readLine());
		            }
				}
				System.out.println("Dseja editar o tipo do Email(1 - Personal, 2 - Work)? Senão aperte ENTER:");
				type = stdin.readLine();
				if(type.equals("")) email.setTypeValue(listaContatos.get(index).getEmails(0).getTypeValue());
				else {
					if (type.equals("1") || type.equals("Personal") || type.equals("personal")) email.setTypeValue(1);
					else if (type.equals("1") || type.equals("Work") || type.equals("work")) email.setTypeValue(3);
				}
				contato.addEmails(email);

				if (proxy.editarContato(contato.build())) {
					System.out.println("Contato editado com sucesso");
				} else {
					System.out.println("Sem sucesso. Contato não existe");
				}

				System.out.println("Aperte Enter para voltar ao menu...");
				input = scanner.nextLine();

				Tela.limpaTela();

				break;
			}

		case "5": //rmContato
		{
			Tela.limpaTela();
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

			Tela.limpaTela();
			break;
		}

		case "6": //limparAgenda
			{
				Tela.limpaTela();
				Boolean result = proxy.limparAgenda();
				if (result) {
					System.out.println("Agenda limpa com sucesso");
				} else {
					System.out.println("Agenda já limpa");
				}
				System.out.println("Aperte Enter para voltar ao menu...");
				input = scanner.nextLine();

				Tela.limpaTela();
				break;
			}

		case "0":
			Tela.limpaTela();
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