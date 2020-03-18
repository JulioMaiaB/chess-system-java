package boardgame;

public class Piece {
	
	protected Position position; // "Não é a posição do Xadez e por isso é PROTECTED. É uma posição simples de uma Matriz"
	private Board board; // "A peça conhece o tabuleiro onde ela está" 
	
	
	// Construtor: Só é passado o board (tabuleiro) no construtor pois a posição inicial da peça será NULA
	public Piece(Board board) { 
		this.board = board;
		this.position = null;
	}

	// Getter: 
	// Não há um setBoard para não permitir que o tabuleiro seja alterado;
	// Será um getter do tipo PROTECTED para apenas subclasses e classes do mesmo pacote terem acesso;
	protected Board getBoard() {
		return board;
	}

	
}
