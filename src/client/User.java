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
    Proxy proxy; // Classe Proxy
	Scanner scanner = new Scanner(System.in); //Leitura do teclado
	String input; //String para entrada
    
    public User(){
        proxy = new Proxy(); //Instanciando o objeto Proxy
    }

    /**
     * Método para selecionar a operação que vai ser realizada
     * @return string com a operação realizada
     * @throws IOException - gerada se acontecer o erro na entrada
     * @throws InterruptedException - gerada caso a thread seja interrompida
     */
    public String selecionaOperacao() throws IOException, InterruptedException {
        //Entrada pelo teclado
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String opt = null;
		do {
			opt = stdin.readLine(); //Escolhendo a opção do menu
		} while (opt.equals("\n") || opt.equals("") || opt.isEmpty());
		
		switch (opt) {
		case "1": //addContato
		{
			Tela.limpaTela();

            //Criando o objeto Contato presente no .proto
            Contato.Builder contato = Contato.newBuilder();
			String type;
            
            System.out.println("Digite o nome do Contato:");
            contato.setNome(stdin.readLine()); //setando o nome do contato
            
            System.out.println("Digite o telefone do Contato:");
            //Criando o objeto telefone presente no .proto
            Telefone.Builder telefone = Telefone.newBuilder();

            //Digitando 1 ou mais telefones para o contato
            while(true) {
            	String num = stdin.readLine();
            	if(num.length() == 0) break;
            	else {
                    //Fazendo a validação de um número de telefone
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
            //Criando o objeto Endereço presente no .proto
            Endereco.Builder endereco = Endereco.newBuilder();
            //Digitando 1 ou mais endereço para o contato
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
            //Criando o objeto Email presente no .proto
            Email.Builder email = Email.newBuilder();
            //Fazendo a leitura de um ou mais emails
            while(true) {
            	String email_in = stdin.readLine();
            	if(email_in.length() == 0) break;
            	else {
                    //Validando o email
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
            
            // Verificando se o contato foi adicionado com sucesso
			if (proxy.addContato(contato.build())) {
				System.out.println("Contato cadastrado com sucesso");
			} else {
				System.out.println("Sem sucesso. Contato já existe");
			}
			System.out.println("Aperte Enter para voltar ao menu...");
			input = scanner.nextLine();

			Tela.limpaTela();
            opt = "addContato";
			break;
		}
		case "2": //listarContatos
		{
			Tela.limpaTela();
            //Lista com os contatos existentes
			List<Contato> listaContatos = proxy.listarTodos();
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
			System.out.println("Aperte Enter para voltar ao menu...");
			input = scanner.nextLine();

			Tela.limpaTela();
            opt = "listarContatos";
			break;
		}


		case "3": //procContatos
		{
			Tela.limpaTela();
			System.out.println("Digite sua busca: ");
			String busca = stdin.readLine();
            //Lista com os contatos que contém a string de busca
			List<Contato> listaContatos = proxy.procContato(busca);

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
			}
			System.out.println("Aperte Enter para voltar ao menu...");
			input = scanner.nextLine();

			Tela.limpaTela();
            opt = "procContato";
			break;
		}
			case "4": //editContato
			{
				Tela.limpaTela();
                //Lista dos contatos existentes
				List<Contato> listaContatos = proxy.listarTodos();
				int index=0;
				String aux;
                //Percorrendo o contato
				for (Contato _contato : listaContatos) {
					System.out.println("Index:" + index);
					System.out.println("Nome: " + _contato.getNome());
					System.out.println("Telefones: ");
                    //Percorrendo os telefones do contato
					for (Telefone tel : _contato.getTelefonesList()) {
						System.out.println("- " + tel.getTelefone() + " " + tel.getType());
					}
					System.out.println("Endereços: ");
                    //Percorrendo os endereços do contato
					for (Endereco end : _contato.getEnderecosList()) {
						System.out.println("- " + end.getEndereco() + " " + end.getType());
					}
					System.out.println("E-mails: ");
                    //Percorrendo os emails do contato
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

                System.out.println("Deseja alterar o Telefone do Contato? Senão aperte ENTER:");
                aux = stdin.readLine();
                //Alterando o nome.
                if(aux.length() == 0) contato.setNome(listaContatos.get(index).getNome());
				else contato.setNome(aux);

				// contato.setNome(listaContatos.get(index).getNome()); - original
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
                opt = "editContato";
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
            opt = "rmContato";
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
                opt = "limparAgenda";
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