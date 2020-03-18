package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

// Essa classe é o coração do sistema de Xadrez. Nesta classe terão todas as regras do jogo de xadrez

public class ChessMatch {

	private Board board; // Partida de xadrez precisa ter um tabuleiro
	
	public ChessMatch() {
		board = new Board(8,8); // Importante entender: quem precisar saber a dimensão do jogo de xadrez é a classe ChessMatch
		initialSetup();
	}
	
	// - Método que retornará uma matriz de peças de xadrez correspondentes a esta partida (ChessMatch);
	// - Para a aplicação (application), será liberado as peças do tipo ChessPiece (e não Piece) pois é um desenvolvimento em camadas;
	// - Por isso, será necessário liberar para o programa uma matriz do tipo ChessPiece, da camada de Xadrez (chess) e não do tabuleiro
	// (board) - LIBERAR PARA A APLICAÇÃO UMA PEÇA CHESSPIECE
	public ChessPiece[][] getPieces() {
		// Quantidade de linhas e colunas do tabuleiro é o tamanho da matriz
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()]; 
		// Percorre a Matriz da classe Board e retorna as de peças do tabuleiro do tipo ChessMatch
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	// Método responsável por iniciar a partida de xadrez colocando as peças no tabuleiro
	private void initialSetup() {
		board.placePiece(new King(board, Color.WHITE), new Position(7, 4));
		board.placePiece(new King(board, Color.BLACK), new Position(0, 4));
		board.placePiece(new Rook(board, Color.WHITE), new Position(7, 0));
	}
	
}
