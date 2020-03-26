package boardgame;

public abstract class Piece {
	
	// N�o � a posi��o do Xadez e por isso � PROTECTED. � uma posi��o simples de uma Matriz
	protected Position position; 
	// A pe�a conhece o tabuleiro onde ela est�
	private Board board; 
	
	// Construtor: S� � passado o board (tabuleiro) no construtor pois a posi��o inicial da pe�a ser� NULA
	public Piece(Board board) { 
		this.board = board;
		this.position = null;
	}

	/* - N�o h� um setBoard para n�o permitir que o tabuleiro seja alterado;
	   - Ser� um getter do tipo PROTECTED para apenas subclasses e classes do mesmo pacote terem acesso; */
	protected Board getBoard() {
		return board;
	}
	
	// M�todo abstrato que futuramente ser� chamado por um m�todo concreto desta classe
	public abstract boolean[][] possibleMoves();
	
	
	// M�todo concreto que utiliza um m�todo abstrato - tamb�m de chamado de Hook Methods
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	// Verifica se a pe�a est� travada ou sem movimento
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves(); // Por enquanto, sempre retorna falso
		for(int i = 0; i < mat.length; i++) {
			for(int j = 0; j < mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
