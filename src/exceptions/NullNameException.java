package exceptions;

public class NullNameException extends RuntimeException {
	public NullNameException() {
		super("Nome não pode estar vazio");
	}
}
