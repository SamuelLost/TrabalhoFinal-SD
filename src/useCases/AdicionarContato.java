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

public class AdicionarContato {
	private Proxy proxy;
	private BufferedReader stdin;

	public AdicionarContato(Proxy proxy, BufferedReader stdin) {
		this.proxy = proxy;
		this.stdin = stdin;
	}
	
    /**
     * Método que faz a interação com o usário para adioconar o contato
     * @throws IOException - readLine()
     */
	public void adicionaContato() throws IOException {
        //Criando o objeto Contato presente no .proto
        Contato.Builder contato = Contato.newBuilder();
        //Criando o objeto telefone presente no .proto
        Telefone.Builder telefone = Telefone.newBuilder();
        //Criando o objeto Endereço presente no .proto
        Endereco.Builder endereco = Endereco.newBuilder();
        //Criando o objeto Email presente no .proto
        Email.Builder email = Email.newBuilder();
        
		String type = "";
        
        System.out.println("Digite o nome do contato: ");
        contato.setNome(stdin.readLine()); //setando o nome do contato

        //Digitando 1 ou mais telefones para o contato
        while (true) {
        	System.out.println("Digite um telefone ou dê ENTER para continuar o cadastro:");
        	String num = stdin.readLine();
        	if (num.length() == 0) break;
        	else {
                //Fazendo a validação de um número de telefone
        		while (!Validation.validationTelefone(num)) {
        			System.out.println("Caracteres inválidos! Digite novamente:");
        			num = stdin.readLine();
        		}
                telefone.setTelefone(num);
                
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
    			
                contato.addTelefones(telefone);
        	}
        }

        //Digitando 1 ou mais endereço para o contato
        while (true) {
        	System.out.println("Digite um endereço ou dê ENTER para continuar o cadastro:");
        	String end = stdin.readLine();
        	if(end.length() == 0) break;
        	else {
        		endereco.setEndereco(end);
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

                contato.addEnderecos(endereco);
        	}
        }

        //Fazendo a leitura de um ou mais emails
        while(true) {
        	System.out.println("Digite um e-mail ou dê ENTER para continuar o cadastro:");
        	String email_in = stdin.readLine();
        	if(email_in.length() == 0) break;
        	else {
                //Validando o email
        		while (!Validation.validationEmail(email_in)) {
        			System.out.println("Email inválido. Digite novamente: ");
        			email_in = stdin.readLine();
        		}

        		email.setEmail(email_in);
        		
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
    			
                contato.addEmails(email);
        	}
        	
        }
        
        // Verificando se o contato foi adicionado com sucesso
        
        Boolean res = false;
        try {
        	res = proxy.adicionarContato(contato.build());
			if (res) {
				System.out.println("Contato cadastrado com sucesso");
			} else {
				System.out.println("Sem sucesso. Contato já existe");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
