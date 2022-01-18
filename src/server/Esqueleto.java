package server;

public class Esqueleto {
    private static Esqueleto uniqueInstance;
    private static Agenda agenda;
    
    private Esqueleto(){}

    public static synchronized Esqueleto getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Esqueleto();
            agenda = new Agenda();
        }
        return uniqueInstance;
    }
    
    byte[] addContato(byte[] args){
        return "";
    }
    byte[] listarTodos(byte[] args){
        return "";
    }
    byte[] procContato(byte[] args){
        return "";
    }
    byte[] editContato(byte[] args){
        return "";
    }
    byte[] rmContato(byte[] args){
        return "";
    }
    byte[] cleanAgenda(byte[] args){
        return "";
    }

}
