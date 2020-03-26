package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

// Essa classe é o coração do sistema de Xadrez. Nesta classe terão todas as regras do jogo de xadrez

public class ChessMatch {

	// Partida de xadrez precisa ter um tabuleiro
	private Board board; 
	
	public ChessMatch() {
		// Importante entender: quem precisar saber a dimensão do jogo de xadrez é a classe ChessMatch
		board = new Board(8,8); 
		initialSetup();
	}
	
	/* - Método que retornará uma matriz de peças de xadrez correspondentes a esta partida (ChessMatch);
	   - Para a aplicação (application), será liberado as peças do tipo ChessPiece (e não Piece) pois é um desenvolvimento em camadas;
	   - Sendo assim, será necessário liberar para o programa uma matriz do tipo ChessPiece, da camada de Xadrez (chess) e não do tabuleiro
		 (board) 
		 OBS: LIBERA PARA A APLICAÇÃO UMA PEÇA CHESSPIECE*/
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
	
	// Move uma peça de uma origem para um desitno
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		// Cria duas variáveis para pegar a posição do xadrez e converter para a posição da matriz através do toPosition().
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		// Valida se na posição de origem tem uma peça, caso não exista lança uma exceção
		validateSourcePosition(source);
		Piece capturedPiece = makeMove(source, target);//makeMove é responsável por realizar o movimento da peça
		return (ChessPiece) capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		// Colocar o valor null na posição da matriz de peças da classe board
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}
	
	// Método auxiliar para validar se a posição inicial tem uma peça
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	// Método vai receber as coordenadas do xadrez
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		// Converte posição de xadrez para posição da matriz 
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	// Método responsável por iniciar a partida de xadrez colocando as peças no tabuleiro
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
 
}
