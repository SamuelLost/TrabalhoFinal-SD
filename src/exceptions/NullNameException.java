package exceptions;

public class NullNameException extends RuntimeException {
	public NullNameException() {
		super("O nome do contato não pode estar vazio");
	}
}
