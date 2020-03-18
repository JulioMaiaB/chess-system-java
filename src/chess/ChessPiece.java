package chess;

import boardgame.Board;
import boardgame.Piece;

public class ChessPiece extends Piece {

	// Essa classe é uma subclasse da classe Piece. Terá a COR e outros elementos também
	
	private Color color; // Cor da peça de Xadrez {BLACK, WHITE};
	
	public ChessPiece(Board board, Color color) {
		super(board); // A peça reconhece o tabuleiro onde ela está
		this.color = color;
	}
	
	// Getters e Setters: O método setColor foi apagado para impedir que a cor de uma peça seja alterada.
	public Color getColor() {
		return color;
	}

}
