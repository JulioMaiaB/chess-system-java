package boardgame;

public class BoardException extends RuntimeException{ // RuntimeException é uma exceção opcional de ser tratada
	private static final long serialVersionUID = 1L;
	
	// Construtor que recebe a mensagem "msg"
	public BoardException(String msg) {
		super(msg); // Passa a mensagem para a classe mãe (RuntimeException)
	}
	
}
