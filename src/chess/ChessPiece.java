package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

// Essa classe é uma subclasse da classe Piece. Terá a COR e  também outros elementos.

public abstract class ChessPiece extends Piece {
	
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
	
	// Verifica se possui uma peça oponente no tabuleiro
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		// Verifica se tem uma pela e se ela é diferente da cor da peça atual
		return p != null && p.getColor() != color;
	}
	
}
