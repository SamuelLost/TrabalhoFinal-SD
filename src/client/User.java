package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.trabalhoFinal.protos.MessageProto.Message;
import com.trabalhoFinal.protos.AgendaProto.Contato;
import com.trabalhoFinal.protos.AgendaProto.Contato.*;

import java.io.IOException;

public class User {
    Proxy proxy;
    
    public User(){
        proxy = new Proxy();
    }

    public String selecionaOperacao() throws IOException {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		String opt = null;
		do {
			opt = stdin.readLine();
		} while (opt.equals("\n") || opt.equals("") || opt.isEmpty());

		switch (opt) {
		case "1":
		{
            Contato.Builder contato = Contato.newBuilder();
            
            System.out.println("Digite o nome do Contato:");
            contato.setNome(stdin.readLine());
            
            System.out.println("Digite o telefone do Contato:");
            Telefone.Builder telefone = Telefone.newBuilder();
            telefone.setTelefone(stdin.readLine());
            contato.addTelefones(telefone);
            
            System.out.println("Digite o endereço do Contato:");
            Endereco.Builder endereco = Endereco.newBuilder();
            endereco.setEndereco(stdin.readLine());
            contato.addEnderecos(endereco);
            
            System.out.println("Digite o email do Contato:");
            Email.Builder email = Email.newBuilder();
            email.setEmail(stdin.readLine());
            contato.addEmails(email);
            
			if (proxy.addContato(contato.build())) {
				System.out.println("Contato cadastrado com sucesso");
			} else {
				System.out.println("Sem sucesso. Contato já existe");
			}
			
			break;
		}
		case "2":
		{
			List<Contato> listaContatos = proxy.listarTodos();
			for (Contato _contato : listaContatos) {
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
			}
		}
			break;

		case "3":
		{
			System.out.println("Digite sua busca");
			String busca = stdin.readLine();
			
			List<Contato> listaContatos = proxy.procContato(busca);
			
			System.out.println("Quantidade: " + listaContatos.size());
			
			for (Contato _contato : listaContatos) {
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
			}
		}
			break;
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
        System.out.println("5 - Limpar Agenda");
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
			}
		} while (!operacao.equals("exit"));
    }
}