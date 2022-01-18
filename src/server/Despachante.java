package server;

import com.google.protobuf.InvalidProtocolBufferException;
import com.trabalhoFinal.protos.Message;

public class Despachante {
    private static Despachante uniqueInstance;

    private Despachante(){}

    public static synchronized Despachante getInstance() {
        if (uniqueInstance == null)
        uniqueInstance = new Despachante();
        return uniqueInstance;
    }

    //Não sei como prosseguir
    public byte[] invoke(byte[] request) {
        byte[] aux = desempacotaServidor(request);
        String req = new String(aux);
        String[] buff = req.split("");
        Esqueleto esqueleto = Esqueleto.getInstance();
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

    public byte[] desempacotaServidor(byte[] seila) {
        try {
            Message a = Message.parseFrom(seila);
            
            String out = String.valueOf(a.getType()) + String.valueOf(a.getId()) + a.getObjReference()
                + a.getMethodId() + a.getArgs().toString();
            return out.getBytes();
        } catch (InvalidProtocolBufferException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}