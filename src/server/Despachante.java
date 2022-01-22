package server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.google.protobuf.ByteString;
import com.trabalhoFinal.protos.MessageProto.Message;

public class Despachante {
    private static Despachante uniqueInstance;
    private static AgendaEsqueleto esqueleto;
    
    private Despachante(){
    	esqueleto = AgendaEsqueleto.getInstance();
    }

    public static synchronized Despachante getInstance() {
        if (uniqueInstance == null)
        uniqueInstance = new Despachante();
        return uniqueInstance;
    }

    public ByteString invoke(Message request) {
    	Class<?> objRef = null;
    	Method method = null;
    	ByteString resposta = null;
    	
    	try {
			objRef = Class.forName("server." + request.getObjReference() + "Esqueleto"); 
	
	    	String methodName = request.getMethodId();
	    	
	    	System.out.println("Executando: " + methodName);

			method = objRef.getMethod(methodName, ByteString.class);
			
			resposta = (ByteString) (method.invoke(objRef.getDeclaredConstructor().newInstance(), request.getArgs()));
		} catch (NoSuchMethodException e) {
			System.out.println("NoSuchMethodException: " + e.getMessage());
		} catch (SecurityException e) {
			System.out.println("SecurityException: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException" + e.getMessage());
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException: " + e.getMessage());
		} catch (InvocationTargetException e) {
			System.out.println("InvocationTargetException: " + e.getMessage());
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: " + e.getMessage());
		}
    	
    	return resposta;
    }
}