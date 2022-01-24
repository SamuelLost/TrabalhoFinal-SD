package views;

public class Tela {
    /**
     * Método para fazer apenas uma "limpeza" no terminal
     */
	public static void limpaTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
    /**
     * Método void apenas para imprimir o menu da aplicação
     */
	public static void printMenu() {
		System.out.println("\nDigite o n# da operação que deseja executar: ");
		System.out.println("1 - Adicionar Contato");
		System.out.println("2 - Listar Contatos");
		System.out.println("3 - Procurar Contato");
        System.out.println("4 - Editar Contato");
        System.out.println("5 - Remover Contato");
        System.out.println("6 - Limpar Agenda");
		System.out.println("0 - Sair\n");
	}
}
