package client;

public class Validation {
	
	public static boolean validationTelefone(String telefone) {
		String validos = "0123456789-.() ";
		for(int i = 0; i < telefone.length(); i++) {
			if(validos.indexOf(telefone.charAt(i)) == -1) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean validationEmail(String email) {
		if(email.contains("@") && (email.contains(".br") || email.contains(".com")))
			return true;
		
		return false;
	}
}
