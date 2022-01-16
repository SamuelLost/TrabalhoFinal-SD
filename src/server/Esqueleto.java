package server;

public class Esqueleto {
    private static Esqueleto uniqueInstance;

    private Esqueleto(){}

    public static synchronized Esqueleto getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new Esqueleto();
        return uniqueInstance;
    }



    String addContato(String args){
        return "";
    }
    String listarTodos(String args){
        return "";
    }
    String procContato(String args){
        return "";
    }
    String editContato(String args){
        return "";
    }
    String rmContato(String args){
        return "";
    }
    String cleanAgenda(String args){
        return "";
    }

}
