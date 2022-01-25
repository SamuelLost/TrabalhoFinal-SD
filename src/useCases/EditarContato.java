package useCases;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.trabalhoFinal.protos.AgendaProto.Contato;
import com.trabalhoFinal.protos.AgendaProto.Contato.Email;
import com.trabalhoFinal.protos.AgendaProto.Contato.Endereco;
import com.trabalhoFinal.protos.AgendaProto.Contato.Telefone;

import client.Proxy;
import client.Validation;
import exceptions.NullNameException;
import views.Tela;

public class EditarContato {
	Proxy proxy;
	BufferedReader stdin;

	public EditarContato(Proxy proxy, BufferedReader stdin) {
		this.proxy = proxy;
		this.stdin = stdin;
	}

	public void editarContato() throws NumberFormatException, IOException {
		
        //Lista dos contatos existentes
		List<Contato> listaContatos = proxy.listarContatos();
		
		int index = 1;
		String aux;
        //Percorrendo o contato
		for (Contato _contato : listaContatos) {
			System.out.println("Index: " + index);
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
		
		String indice = "";
		
		// abc
		while (true) {
			indice = stdin.readLine();
			boolean isNumeric =  indice.matches("[+-]?\\d*(\\.\\d+)?");
			if (!isNumeric) {
				System.out.println("Opção inválida! Digite o index do contato que deseja editar: ");
				continue;
			}
			index = Integer.parseInt(indice);
			if (1 <= index && index <= listaContatos.size()) {
				break;
			}
			System.out.println("Opção inválida! Digite o index do contato que deseja editar: ");
		}
		
		index--;
		
        //Criando o objeto telefone presente no .proto
        Telefone.Builder telefone = Telefone.newBuilder();
        //Criando o objeto Endereço presente no .proto
        Endereco.Builder endereco = Endereco.newBuilder();
        //Criando o objeto Email presente no .proto
        Email.Builder email = Email.newBuilder();
		
		Contato.Builder newContato = Contato.newBuilder(listaContatos.get(index));
		String type;

//        System.out.println("Deseja alterar o nome do contato? Se sim, digite um novo nome, se não dê ENTER:");
//        aux = stdin.readLine();
//
//        //Alterando o nome.
//        if (aux.length() != 0) newContato.setNome(aux);

        
		while (true) {
			System.out.println("Deseja adicionar um novo telefone ao contato? Se sim, digite um novo telefone, se não dê ENTER:");
			aux = stdin.readLine();
			
			if (aux.length() == 0) {
				break;
			}
			
    		while (!Validation.validationTelefone(aux)) {
    			System.out.println("Caracteres inválidos! Digite novamente:");
    			aux = stdin.readLine();
    		}
    		
            telefone.setTelefone(aux);
            
			System.out.println("Digite o tipo do telefone (1 - Mobile, 2 - Personal, 3 - Home, 4 - Work):");
			
			type = stdin.readLine();
			
			List<String> lista = new ArrayList<String>();
			lista.add("1"); lista.add("2"); lista.add("3"); lista.add("4");
			lista.add("Mobile"); lista.add("mobile");
			lista.add("Personal"); lista.add("personal");
			lista.add("Home"); lista.add("home");
			lista.add("Work"); lista.add("work");
			
			while (!lista.contains(type)) {
				System.out.println("Opção inválida! Digite o tipo do telefone (1 - Mobile, 2 - Personal, 3 - Home, 4 - Work):");
				type = stdin.readLine();
			}
			
			if (type.equals("1") || type.equals("Mobile") || type.equals("mobile")) telefone.setTypeValue(0);
			else if (type.equals("2") || type.equals("Personal") || type.equals("personal")) telefone.setTypeValue(1);
			else if (type.equals("3") || type.equals("Home") || type.equals("home")) telefone.setTypeValue(2);
			else if (type.equals("4") || type.equals("Work") || type.equals("work")) telefone.setTypeValue(3);
			
			newContato.addTelefones(telefone);
		}
		
		while (true) {
			index = 1;
			for (Telefone tele : newContato.getTelefonesList()) {
				System.out.println("("+ index +") " + tele.getTelefone() + " " + tele.getType());
				index++;
			}
			System.out.println("Deseja remover algum telefone? Se sim, digite um número, se não, dê ENTER:");
			
			boolean parou = false;
			
			while (true) {
				indice = stdin.readLine();
				if (indice.length() == 0) {
					parou = true;
					break;
				}
				boolean isNumeric =  indice.matches("[+-]?\\d*(\\.\\d+)?");
				if (!isNumeric) {
					System.out.println("Opção inválida! Digite o index do telefone que deseja remover: ");
					continue;
				}
				index = Integer.parseInt(indice);
				if (1 <= index && index <= newContato.getTelefonesList().size()) {
					break;
				}
				System.out.println("Opção inválida! Digite o index do telefone que deseja remover: ");
			}
			
			if (parou) break;
			
			index--;
			
			newContato.removeTelefones(index);
			Tela.limpaTela();
		}
		
		while (true) {
			System.out.println("Deseja adicionar um novo endereço ao contato? Se sim, digite um novo endereço, se não dê ENTER:");
			aux = stdin.readLine();
			
			if (aux.length() == 0) {
				break;
			}
    		
            endereco.setEndereco(aux);
            
            System.out.println("Digite o tipo do Endereço (1 - Home, 2 - Work):");
			
			List<String> lista = new ArrayList<String>();
			lista.add("1"); lista.add("2");
			lista.add("Home"); lista.add("home");
			lista.add("Work"); lista.add("work");
			
			type = stdin.readLine();
			
			while (!lista.contains(type)) {
				System.out.println("Opção inválida! Digite o tipo do Endereço (1 - Home, 2 - Work):");
				type = stdin.readLine();
			}
			
			if (type.equals("1") || type.equals("Home") || type.equals("home")) endereco.setTypeValue(2);
			else if (type.equals("2") || type.equals("Work") || type.equals("work")) endereco.setTypeValue(3);

            newContato.addEnderecos(endereco);
		}
		
		
		while (true) {
			index = 1;
			for (Endereco ende : newContato.getEnderecosList()) {
				System.out.println("("+ index +") " + ende.getEndereco() + " " + ende.getType());
				index++;
			}
			System.out.println("Deseja remover algum endereço? Se sim, digite um número, se não, dê ENTER:");
			
			boolean parou = false;
			
			while (true) {
				indice = stdin.readLine();
				if (indice.length() == 0) {
					parou = true;
					break;
				}
				boolean isNumeric =  indice.matches("[+-]?\\d*(\\.\\d+)?");
				if (!isNumeric) {
					System.out.println("Opção inválida! Digite o index do telefone que deseja remover: ");
					continue;
				}
				index = Integer.parseInt(indice);
				if (1 <= index && index <= newContato.getEnderecosList().size()) {
					break;
				}
				System.out.println("Opção inválida! Digite o index do endereço que deseja remover: ");
			}
			
			if (parou) break;
			
			index--;
			
			newContato.removeEnderecos(index);
			Tela.limpaTela();
		}
		
		while (true) {
			System.out.println("Deseja adicionar um novo e-mail ao contato? Se sim, digite um novo e-mail, se não dê ENTER:");
			aux = stdin.readLine();
			
			if (aux.length() == 0) {
				break;
			}
			
    		while (!Validation.validationEmail(aux)) {
    			System.out.println("Email inválido. Digite novamente: ");
    			aux = stdin.readLine();
    		}

    		email.setEmail(aux);
    		
			System.out.println("Digite o tipo do Email (1 - Personal, 2 - Work):");
			
			type = stdin.readLine();
			
			List<String> lista = new ArrayList<String>();
			lista.add("1"); lista.add("2");
			lista.add("Personal"); lista.add("personal");
			lista.add("Work"); lista.add("work");
			
			while (!lista.contains(type)) {
				System.out.println("Opção inválida! Digite o tipo do Email (1 - Personal, 2 - Work):");
				type = stdin.readLine();
			}

			if (type.equals("1") || type.equals("Personal") || type.equals("personal")) email.setTypeValue(1);
			else if (type.equals("2") || type.equals("Work") || type.equals("work")) email.setTypeValue(3);
			
            newContato.addEmails(email);
		}
		
		while (true) {
			index = 1;
			for (Email e_mail : newContato.getEmailsList()) {
				System.out.println("("+ index +") " + e_mail.getEmail() + " " + e_mail.getType());
				index++;
			}
			System.out.println("Deseja remover algum e-mail? Se sim, digite um número, se não, dê ENTER:");
			
			boolean parou = false;
			
			while (true) {
				indice = stdin.readLine();
				if (indice.length() == 0) {
					parou = true;
					break;
				}
				boolean isNumeric =  indice.matches("[+-]?\\d*(\\.\\d+)?");
				if (!isNumeric) {
					System.out.println("Opção inválida! Digite o index do e-mail que deseja remover: ");
					continue;
				}
				index = Integer.parseInt(indice);
				if (1 <= index && index <= newContato.getTelefonesList().size()) {
					break;
				}
				System.out.println("Opção inválida! Digite o index do e-mail que deseja remover: ");
			}
			
			if (parou) break;
			
			index--;
			
			newContato.removeEmails(index);
			Tela.limpaTela();
		}

		Boolean res = false;
		try {
			res = proxy.editarContato(newContato.build());
			if (res) {
				System.out.println("Contato editado com sucesso");
			} else {
				System.out.println("Sem sucesso. Contato não existe");
			}
		} catch (NullNameException e) {
			System.out.println(e.getMessage());
		}

	}
}
