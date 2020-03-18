package chess;

import boardgame.Board;
import boardgame.Piece;

public class ChessPiece extends Piece {

	// Essa classe � uma subclasse da classe Piece. Ter� a COR e outros elementos tamb�m
	
	private Color color; // Cor da pe�a de Xadrez {BLACK, WHITE};
	
	public ChessPiece(Board board, Color color) {
		super(board); // A pe�a reconhece o tabuleiro onde ela est�
		this.color = color;
	}
	
	// Getters e Setters: O m�todo setColor foi apagado para impedir que a cor de uma pe�a seja alterada.
	public Color getColor() {
		return color;
	}

}
