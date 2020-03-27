package boardgame;

public abstract class Piece {
	
	
	protected Position position; // N�o � a posi��o do Xadez. � uma posi��o simples de uma Matriz
	private Board board; // A pe�a conhece o tabuleiro onde ela est�
	
	// Construtor: S� � passado o board (tabuleiro) no construtor pois a posi��o inicial da pe�a ser� NULA
	public Piece(Board board) { 
		this.board = board;
		this.position = null;
	}

	protected Board getBoard() {
		return board;
	}
	
	public abstract boolean[][] possibleMoves();
	
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	// Verifica se a pe�a est� travada ou sem movimento
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
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
