package chess;

import boardgame.Board;
import boardgame.Piece;

// Essa classe é uma subclasse da classe Piece. Terá a COR e  também outros elementos.

public class ChessPiece extends Piece {
	
	// Cor da peça de Xadrez {BLACK, WHITE};
	private Color color; 
	
	public ChessPiece(Board board, Color color) {
		// A peça reconhece o tabuleiro onde ela está
		super(board); 
		this.color = color;
	}
	
	// Getters e Setters: O método setColor foi apagado para impedir que a cor de uma peça seja alterada.
	public Color getColor() {
		return color;
	}

}
