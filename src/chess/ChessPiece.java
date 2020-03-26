package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

// Essa classe � uma subclasse da classe Piece. Ter� a COR e  tamb�m outros elementos.

public abstract class ChessPiece extends Piece {
	
	// Cor da pe�a de Xadrez {BLACK, WHITE};
	private Color color; 
	
	public ChessPiece(Board board, Color color) {
		// A pe�a reconhece o tabuleiro onde ela est�
		super(board); 
		this.color = color;
	}
	
	// Getters e Setters: O m�todo setColor foi apagado para impedir que a cor de uma pe�a seja alterada.
	public Color getColor() {
		return color;
	}
	
	// Verifica se possui uma pe�a oponente no tabuleiro
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		// Verifica se tem uma pela e se ela � diferente da cor da pe�a atual
		return p != null && p.getColor() != color;
	}
	
}
