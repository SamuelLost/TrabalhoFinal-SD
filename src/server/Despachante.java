package server;

import java.io.IOException;

import com.google.protobuf.ByteString;
import com.trabalhoFinal.protos.MessageProto.Message;

public class Despachante {
    private static Despachante uniqueInstance;
    private static Esqueleto esqueleto;
    
    private Despachante(){
    	esqueleto = Esqueleto.getInstance();
    }

    public static synchronized Despachante getInstance() {
        if (uniqueInstance == null)
        uniqueInstance = new Despachante();
        return uniqueInstance;
    }

    public ByteString invoke(Message request) throws IOException {
        switch (request.getMethodId()) {
            case "addContato":
                return esqueleto.addContato(request.getArgs());
            case "listarContatos":
                return esqueleto.listarTodos(request.getArgs());
            case "buscarContatos":
              return esqueleto.procContato(request.getArgs());
            case "editarContato":
                return esqueleto.editaContato(request.getArgs());
            case "removerContato":
              return esqueleto.removerContato(request.getArgs());
            case "cleanAgenda":
                return esqueleto.cleanAgenda(request.getArgs());
            default:
                return null;
        }
    }
}