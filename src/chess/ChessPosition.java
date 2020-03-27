package chess;

import boardgame.Position;

// Classe que determina as posi��o na classe de Xadrez. Exemplo: a1, d5, h4, etc.
public class ChessPosition {
	
	private char column;
	private int row;
	
	public ChessPosition(char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8 ) {
			throw new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8.");
		}
		this.column = column;
		this.row = row;
	}
	
	// Getters e Setters: os m�todos setters foram apagados
	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	// Converte a posi��o do xadrez (ChessPosition) para a posi��o normal (Position).
	protected Position toPosition() {
		// Foi utilizado o conceito de UNICODE para fazer as compara��es entre variaveis do tipo char.
		return new Position(8 - row, column - 'a');
	}
	
	// Converte a posi��o normal (Position) para a posi��o do xadrez (ChessPosition).
	protected static ChessPosition fromPosition(Position position) {
		// OBS: N�o entendi
		return new ChessPosition((char)('a' - position.getColumn()), 8 - position.getRow());
	}
	
	// Foi necess�rio utilizar um macete utilizando uma concatena��o de String vazia "" para o compilador entender que se trata de uma string
	@Override
	public String toString() {
		return "" + column + row;
	}
}
