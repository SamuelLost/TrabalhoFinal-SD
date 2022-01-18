package server;

public class Contato {
    private String nome;
    private String fone;
    private String endereco;
    private String email;
    public Contato(String nome, String fone, String email, String endereco){
        this.nome = nome;
        this.fone = fone;
        this.endereco = endereco;
        this.email = email;
    }
    public String getNome(){
        return nome;
    }
    public String getFone(){
        return fone;
    }
    public String getEndereco(){
        return endereco;
    }
    public String getEmail(){
        return email;
    }
    /*public boolean validar(String number){
        String valid = "0123456789()-.";
        for(int i=0 char c = valid.charAt(i); ;){
            if(valid.find(c) == string::npos)
                return false;
        }        
        return true;
    }*/
}
