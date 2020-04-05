package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

// Essa classe � uma subclasse da classe Piece. Ter� a COR e  tamb�m outros elementos.

public abstract class ChessPiece extends Piece {
	
	private Color color; //{Black, White}
	private int moveCount; // Contagem dos movimentos da pe�a
	
	public ChessPiece(Board board, Color color) {
		super(board); 
		this.color = color;
	}
	
	// Getters e Setters: O m�todo setColor foi apagado para impedir que a cor de uma pe�a seja alterada.
	public Color getColor() {
		return color;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public void increaseMoveCount() {
		moveCount++;
	}
	
	public void decreaseMoveCount() {
		moveCount--;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position); //Retorna a pe�a na posi��o Position
		return p != null && p.getColor() != color;
	}
	
}
