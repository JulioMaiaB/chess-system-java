package boardgame;

public abstract class Piece {
	
	// Não é a posição do Xadez e por isso é PROTECTED. É uma posição simples de uma Matriz
	protected Position position; 
	// A peça conhece o tabuleiro onde ela está
	private Board board; 
	
	// Construtor: Só é passado o board (tabuleiro) no construtor pois a posição inicial da peça será NULA
	public Piece(Board board) { 
		this.board = board;
		this.position = null;
	}

	/* - Não há um setBoard para não permitir que o tabuleiro seja alterado;
	   - Será um getter do tipo PROTECTED para apenas subclasses e classes do mesmo pacote terem acesso; */
	protected Board getBoard() {
		return board;
	}
	
	public abstract boolean[][] possibleMoves();
	
	
	// Método concreto que utiliza um método abstrato - chamado de hook methods
	public boolean posibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	
	// Verifica se a peça está travada sou sem movimento
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
