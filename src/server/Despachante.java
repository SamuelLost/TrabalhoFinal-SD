package server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.google.protobuf.ByteString;
import com.trabalhoFinal.protos.MessageProto.Message;

public class Despachante {
    private static Despachante uniqueInstance;

    /**
     * Construtor vazio para permitir o uso de objetos apenas através do getInstance
     */
    private Despachante() {}

    /**
     * Método para poder pegar uma instancia de Despachante de
     * cada vez e sincronizado.
     * @return - uma instância de Despachante
     */
    public static synchronized Despachante getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new Despachante();
        return uniqueInstance;
    }

    /**
     * Método invoke para invocar o métodos de AgendaEsqueleto de acordo com a 
     * requisição empacotada que é passada como parâmetro.
     * 
     * O método faz uso da API de reflexão do Java para selecionar
     * o método de forma dinâmica, facilitando a remoção e inserção
     * de novos métodos no sitema sem precisar modificar o método invoke.
     * 
     * @param request - objeto Message com a requisição empacotada
     * @return ByteString com resposta
     */
    public ByteString invoke(Message request) {
    	Class<?> objRef = null;
    	Method method = null;
    	ByteString resposta = null;
    	
    	try {
            //Faz a busca do objeto a ser instanciado, concatenando o nome do pacote e nome
            // do objeto a ser usado.
			objRef = Class.forName("server." + request.getObjReference() + "Esqueleto"); 
	
            //Pegando o nome do método
	    	String methodName = request.getMethodId();
	    	
	    	System.out.println("Executando: " + methodName);

            //O método que contém dentro do objeto referenciado, através
            //do método getMethod() passando o nome do método e tipo do argumento.
			method = objRef.getMethod(methodName, ByteString.class);
			
            //Recebendo a resposta em ByteString.
            //Chama o método invoke da Classe Method passando a nova instância do objeto
            //referenciado e os argumentos para o método.
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