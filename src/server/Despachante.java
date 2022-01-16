package server;

import com.trabalhoFinal.protos.Message;
//import com.google.protobuf.Message;

public class Despachante {
  private static Despachante uniqueInstance;

  private Despachante(){}

  public static synchronized Despachante getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new Despachante();
    return uniqueInstance;
  }

  public byte[] invoke(Message request) {
    String[] buff = request.split(",");
    Esqueleto esqueleto = new Esqueleto();
    //String response = "";
    switch (buff[0]) {
        case "add":
            return esqueleto.addContato();
        case "rm":
            return esqueleto.rmContato();
        case "listar":
            return esqueleto.listarTodos(args);
        case "rmtodos":
            return esqueleto.cleanAgenda(args);
        case "procurar":
            return esqueleto.procContato(args);    
        default:
            return null;
    }
  }
}