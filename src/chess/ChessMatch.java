package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

// Essa classe � o cora��o do sistema de Xadrez. Nesta classe ter�o todas as regras do jogo de xadrez

public class ChessMatch {

	private Board board; // Partida de xadrez precisa ter um tabuleiro
	
	public ChessMatch() {
		board = new Board(8,8); // Importante entender: quem precisar saber a dimens�o do jogo de xadrez � a classe ChessMatch
		initialSetup();
	}
	
	// - M�todo que retornar� uma matriz de pe�as de xadrez correspondentes a esta partida (ChessMatch);
	// - Para a aplica��o (application), ser� liberado as pe�as do tipo ChessPiece (e n�o Piece) pois � um desenvolvimento em camadas;
	// - Por isso, ser� necess�rio liberar para o programa uma matriz do tipo ChessPiece, da camada de Xadrez (chess) e n�o do tabuleiro
	// (board) - LIBERAR PARA A APLICA��O UMA PE�A CHESSPIECE
	public ChessPiece[][] getPieces() {
		// Quantidade de linhas e colunas do tabuleiro � o tamanho da matriz
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()]; 
		// Percorre a Matriz da classe Board e retorna as de pe�as do tabuleiro do tipo ChessMatch
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	// M�todo respons�vel por iniciar a partida de xadrez colocando as pe�as no tabuleiro
	private void initialSetup() {
		board.placePiece(new King(board, Color.WHITE), new Position(7, 4));
		board.placePiece(new King(board, Color.BLACK), new Position(0, 4));
		board.placePiece(new Rook(board, Color.WHITE), new Position(7, 0));
	}
	
}
