package boardgame;

public class BoardException extends RuntimeException{ // RuntimeException � uma exce��o opcinal de ser tratada
	private static final long serialVersionUID = 1L;
	
	// Construtor que recebe a mensagem "msg"
	public BoardException(String msg) {
		super(msg); // Passa a mensagem para a classe m�e (RuntimeException)
	}
	
}
