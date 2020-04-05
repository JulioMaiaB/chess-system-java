package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

// Essa classe é uma subclasse da classe Piece. Terá a COR e  também outros elementos.

public abstract class ChessPiece extends Piece {
	
	private Color color; //{Black, White}
	private int moveCount; // Contagem dos movimentos da peça
	
	public ChessPiece(Board board, Color color) {
		super(board); 
		this.color = color;
	}
	
	// Getters e Setters: O método setColor foi apagado para impedir que a cor de uma peça seja alterada.
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
		ChessPiece p = (ChessPiece)getBoard().piece(position); //Retorna a peça na posição Position
		return p != null && p.getColor() != color;
	}
	
}
