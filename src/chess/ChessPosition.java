package chess;

import boardgame.Position;

// Classe que determina as posição na classe de Xadrez. Exemplo: a1, d5, h4, etc.
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
	
	// Getters e Setters: os métodos setters foram apagados
	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	// Converte a posição do xadrez (ChessPosition) para a posição normal (Position).
	protected Position toPosition() {
		// Foi utilizado o conceito de UNICODE para fazer as comparações entre variaveis do tipo char.
		return new Position(8 - row, column - 'a');
	}
	
	// Converte a posição normal (Position) para a posição do xadrez (ChessPosition).
	protected static ChessPosition fromPosition(Position position) {
		// OBS: Não entendi
		return new ChessPosition((char)('a' - position.getColumn()), 8 - position.getRow());
	}
	
	// Foi necessário utilizar um macete utilizando uma concatenação de String vazia "" para o compilador entender que se trata de uma string
	@Override
	public String toString() {
		return "" + column + row;
	}
}
