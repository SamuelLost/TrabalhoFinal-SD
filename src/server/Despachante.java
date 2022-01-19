package server;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.trabalhoFinal.protos.Message;
import com.trabalhoFinal.protos.Contato;

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

    public byte[] invoke(Message request) throws InvalidProtocolBufferException {
    	String objReference = request.getObjReference();
    	String methodName = request.getMethodId();
    	int type = request.getType();
    	ByteString args = request.getArgs();

        switch (methodName) {
            case "addContato":
                return esqueleto.addContato(args);
            case "rm":
//                return esqueleto.rmContato();
            case "listar":
//                return esqueleto.listarTodos(args);
            case "rmtodos":
//                return esqueleto.cleanAgenda(args);
            case "procurar":
//                return esqueleto.procContato(args);    
            default:
                return null;
        }
    }
}