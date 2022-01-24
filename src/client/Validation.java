package client;

public class Validation {
	/**
     * Método estátio para realizar uma simples validação de números
     * de telefones. Caso o número de telefone tenha alguma caractére
     * diferente dos que são aceitos na string validos, então
     * o telefone é inválido.
     * @param telefone - String com o número
     * @return - Boolean: true caso seja válido, false caso contrário. 
     */
	public static boolean validationTelefone(String telefone) {
		String validos = "0123456789-.() ";
		for(int i = 0; i < telefone.length(); i++) {
			if(validos.indexOf(telefone.charAt(i)) == -1) {
				return false;
			}
		}
		return true;
	}
	
    /**
     * O método faz a validação de um email. Emails obrigatoriamente
     * devem conter um @ e .br ou .com. Essa características são observadas
     * no método a seguir, caso não sejam respeitadas então o email
     * não é válido.
     * @param email - String contendo o email
     * @return Boolean: true caso seja válido, false caso contrário.
     */
	public static boolean validationEmail(String email) {
		if(email.contains("@") && (email.contains(".br") || email.contains(".com")))
			return true;
		
		return false;
	}
}
