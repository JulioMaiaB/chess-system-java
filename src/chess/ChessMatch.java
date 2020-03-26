package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

// Essa classe � o cora��o do sistema de Xadrez. Nesta classe ter�o todas as regras do jogo de xadrez

public class ChessMatch {

	// Partida de xadrez precisa ter um tabuleiro
	private Board board; 
	
	public ChessMatch() {
		// Importante entender: quem precisar saber a dimens�o do jogo de xadrez � a classe ChessMatch
		board = new Board(8,8); 
		initialSetup();
	}
	
	/* - M�todo que retornar� uma matriz de pe�as de xadrez correspondentes a esta partida (ChessMatch);
	   - Para a aplica��o (application), ser� liberado as pe�as do tipo ChessPiece (e n�o Piece) pois � um desenvolvimento em camadas;
	   - Sendo assim, ser� necess�rio liberar para o programa uma matriz do tipo ChessPiece, da camada de Xadrez (chess) e n�o do tabuleiro
		 (board) 
		 OBS: LIBERA PARA A APLICA��O UMA PE�A CHESSPIECE*/
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
	
	// Move uma pe�a de uma origem para um desitno
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		// Cria duas vari�veis para pegar a posi��o do xadrez e converter para a posi��o da matriz atrav�s do toPosition().
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		// Valida se na posi��o de origem tem uma pe�a, caso n�o exista lan�a uma exce��o
		validateSourcePosition(source);
		Piece capturedPiece = makeMove(source, target);//makeMove � respons�vel por realizar o movimento da pe�a
		return (ChessPiece) capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		// Colocar o valor null na posi��o da matriz de pe�as da classe board
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}
	
	// M�todo auxiliar para validar se a posi��o inicial tem uma pe�a
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if(!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	// M�todo vai receber as coordenadas do xadrez
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		// Converte posi��o de xadrez para posi��o da matriz 
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	// M�todo respons�vel por iniciar a partida de xadrez colocando as pe�as no tabuleiro
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
