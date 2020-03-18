package boardgame;

public class Piece {
	
	protected Position position; // "N�o � a posi��o do Xadez e por isso � PROTECTED. � uma posi��o simples de uma Matriz"
	private Board board; // "A pe�a conhece o tabuleiro onde ela est�" 
	
	
	// Construtor: S� � passado o board (tabuleiro) no construtor pois a posi��o inicial da pe�a ser� NULA
	public Piece(Board board) { 
		this.board = board;
		this.position = null;
	}

	// Getter: 
	// N�o h� um setBoard para n�o permitir que o tabuleiro seja alterado;
	// Ser� um getter do tipo PROTECTED para apenas subclasses e classes do mesmo pacote terem acesso;
	protected Board getBoard() {
		return board;
	}

	
}
