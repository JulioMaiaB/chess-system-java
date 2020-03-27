package boardgame;

public abstract class Piece {
	
	
	protected Position position; // Não é a posição do Xadez. É uma posição simples de uma Matriz
	private Board board; // A peça conhece o tabuleiro onde ela está
	
	// Construtor: Só é passado o board (tabuleiro) no construtor pois a posição inicial da peça será NULA
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
	
	// Verifica se a peça está travada ou sem movimento
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
