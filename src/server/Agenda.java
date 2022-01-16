package server;

import java.util.Vector;


public class Agenda {
    Vector <Contato> agenda;

    public Boolean addContato(String nome , String telefone, String endereco, String email){
        if(agenda.add(new Contato(nome, telefone, endereco, email))) return true;
        else return null;
    }
    public void listarContato(){
        for (Contato contato : agenda){
            System.out.println(contato.getnome());
            System.out.println(contato.getfone());
            System.out.println(contato.getendereco());
            System.out.println(contato.getemail());
        }
    }
    public Boolean procContato(String busca){
        for(Contato contato : agenda){
            if(contato.getnome()==busca){
                System.out.println(contato.getnome());
                System.out.println(contato.getfone());
                System.out.println(contato.getendereco());
                System.out.println(contato.getemail());
                return true;
            }
        }
        return false;
        
    }
    //public Boolean ediContato(Contato contato, ){return false;}
    public Boolean rmContato(String nome){
        for(Contato contato : agenda){
            if(contato.getnome()==nome){
                agenda.remove(contato);
                return true;
            }
        }
        return false;
    }
    public void cleanAgenda(){
        agenda.clear();
    }


}
