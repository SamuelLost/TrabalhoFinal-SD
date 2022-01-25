package exceptions;

@SuppressWarnings("serial") //Ignorando uma warning de serialização usada para gravar o objeto
// em outro local
public class NullNameException extends RuntimeException {
	public NullNameException() {
		super("O nome do contato não pode estar vazio");
	}
}
