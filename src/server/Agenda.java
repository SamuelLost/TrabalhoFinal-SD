package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Agenda {    
    
    Agenda() {}
    	
    public Boolean addContato(String nome, com.trabalhoFinal.protos.Contato.Telefone[] telefone, com.trabalhoFinal.protos.Contato.Endereco[] endereco, com.trabalhoFinal.protos.Contato.Email[] emails) {
    	com.trabalhoFinal.protos.Agenda.Builder agenda = com.trabalhoFinal.protos.Agenda.newBuilder();
    	try {
    		FileInputStream input = new FileInputStream("agenda.txt");
    		try {
	    	  agenda.mergeFrom(input);
	        } finally {
	          try { input.close(); } catch (Throwable ignore) {}
	        }
		} catch (FileNotFoundException e) {
			System.out.println("agenda.txt: File not found. Creating a new file.");
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
    	
    	com.trabalhoFinal.protos.Contato.Builder contato = com.trabalhoFinal.protos.Contato.newBuilder(); 
    	contato.setNome(nome);
    	
    	for (int i = 0; i < telefone.length; i++)  {
    		com.trabalhoFinal.protos.Contato.Telefone.Builder builder_telefone = com.trabalhoFinal.protos.Contato.Telefone.newBuilder();
    		builder_telefone.setTelefone(telefone[i].getTelefone());
    		builder_telefone.setType(com.trabalhoFinal.protos.Contato.Type.HOME);
    		contato.addTelefone(builder_telefone.build());
    	}
    	
    	for (int i = 0; i < endereco.length; i++)  {
    		com.trabalhoFinal.protos.Contato.Endereco.Builder builder_endereco = com.trabalhoFinal.protos.Contato.Endereco.newBuilder();
    		builder_endereco.setEndereco(endereco[i].getEndereco());
    		builder_endereco.setType(com.trabalhoFinal.protos.Contato.Type.HOME);
    		contato.addEndereco(builder_endereco.build());
    	}

    	for (int i = 0; i < emails.length; i++)  {
    		com.trabalhoFinal.protos.Contato.Email.Builder builder_email = com.trabalhoFinal.protos.Contato.Email.newBuilder();
    		builder_email.setEmail(emails[i].getEmail());
    		contato.addEmails(builder_email.build());
    	}

    	agenda.addContato(contato.build());
    	
    	try {
			FileOutputStream output = new FileOutputStream("agenda.txt");
			agenda.build().writeTo(output);
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
    	
    	return true;
    }

//    public void listarContato() {
//        for (Contato contato : agenda) {
//            System.out.println(contato.getNome());
//            System.out.println(contato.getFone());
//            System.out.println(contato.getEndereco());
//            System.out.println(contato.getEmail());
//        }
//    }
//
//    public Boolean procContato(String busca) {
//        for (Contato contato : agenda) {
//            if (contato.getNome() == busca) {
//                System.out.println(contato.getNome());
//                System.out.println(contato.getFone());
//                System.out.println(contato.getEndereco());
//                System.out.println(contato.getEmail());
//                return true;
//            }
//        }
//        return false;
//
//    }
//
//    // public Boolean ediContato(Contato contato, ){return false;}
//    public Boolean rmContato(String nome) {
//        for (Contato contato : agenda) {
//            if (contato.getNome() == nome) {
//                agenda.remove(contato);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void cleanAgenda() {
//        agenda.clear();
//    }

}
