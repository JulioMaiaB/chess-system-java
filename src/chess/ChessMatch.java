package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn; // Contagem de turno da partida
	private Color currentPlayer; // Indica se a vez de jogar � da cor branca ou preta
	private Board board; // Partida de xadrez precisa ter um tabuleiro
	private boolean check; // Por padr�o � false
	private boolean checkMate; // Por padr�o � false
	private ChessPiece enPassantVulnerable; // Recebe a peça que está vulnerável à en passant
	private ChessPiece promoted; // Verifica se um peão foi promovido
	
	// Lista para controlar as pe�as que est�o no tabuleiro e as pe�as que foram capturadas
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		// Importante entender: quem precisar saber a dimens�o do jogo de xadrez � a
		// classe ChessMatch
		board = new Board(8, 8);
		initialSetup();
		this.turn = 1;
		this.currentPlayer = Color.WHITE;
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckmate() {
		return checkMate;
	}
	
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
	}
	
	/*
	 * - Método que retornará uma matriz de peças de xadrez correspondentes a esta
	 * partida (ChessMatch); - Para a aplicação (application), será liberado as
	 * peças do tipo ChessPiece (e não Piece) pois é um desenvolvimento em camadas;
	 * - Sendo assim, será necessário liberar para o programa uma matriz do tipo
	 * ChessPiece, da camada de Xadrez (chess) e não do tabuleiro (board) OBS:
	 * LIBERA PARA A APLICAÇÃO UMA PEÇA CHESSPIECE
	 */
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

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		if (board.piece(source) instanceof King) {
			validateCastlingMove(source, target); // Valida se pode fazer o roque
		}

		Piece capturedPiece = makeMove(source, target);			
		
		if (testCheck(currentPlayer)) { // Se coloca em uma posição a qual é um poss�vel movimento de uma peça adversária
			undoMove(source, target, capturedPiece);
			throw new ChessException("You cant't put yourself in check");
		}
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target); // Guarda a peça que fez o movimento
		
		// #specialmove promotion 
		promoted = null; // joga na variável promoted o valor nulo para assegurar ques está sendo feito um novo teste
		if (movedPiece instanceof Pawn) {
			if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7	)){
				promoted = (ChessPiece)board.piece(target);
				promoted = replacePromotedPiece("Q");
			}
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		} else {
			nextTurn();
		}
		
		// #specialmove en passant
		// Verifica se a peça que se moveu é vulnerável ao en passant
		if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2) || target.getRow() == source.getRow() + 2) { 
			enPassantVulnerable = movedPiece;
		}
		else {
			enPassantVulnerable = null;
		}
		
		return (ChessPiece) capturedPiece; // Retorna a peça capturada para adicionar na lista de peças capturadas
	}

	public ChessPiece replacePromotedPiece(String type) { // Método para retornar a nova peça desejada
		if (promoted == null) {
			throw new IllegalStateException("There is no piece to be promoted");
		}
		// Utilizado o .equals() para fazer a comparação pois String é do tipo classe e não do tipo primitivo
		if (!type.equals("B") && !type.contentEquals("N") && !type.equals("R") && !type.equals("Q")) {
			return promoted; // Caso tudo dẽ errado, retorna a peça que ja está na variável promoted
		}
		
		Position pos = promoted.getChessPosition().toPosition(); // Posição da peça promovida
		Piece p = board.removePiece(pos); // Remove a peça que estava lá anteriormente
		piecesOnTheBoard.remove(p); // remove a peça capturada da lista de peças do tabuleiro
		
		ChessPiece newPiece = newPiece(type, promoted.getColor()); // Instancia uma nova peça
		board.placePiece(newPiece, pos); // Coloca a peça instanciada no tabuleiro
		piecesOnTheBoard.add(newPiece); // Adiciona a peça na lista de peças do tabuleiro
		
		return newPiece;
	}
	
	private ChessPiece newPiece (String type, Color color) { // Método auxiliar para instanciar uma nova peça
		if (type.equals("B")) return new Bishop(board, color);
		if (type.equals("N")) return new Knight(board, color);
		if (type.equals("Q")) return new Queen(board, color);
		return new Rook(board, color);
	}
	
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target); // o "p" deste parâmetro é do tipo Piece, então é feito o UPCASTING de forma automática

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		// #specialmove castling kingside rook
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}

		// #specialmove castling queenside rook
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		// #specialmove en passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == null) { // Significa que o peão andou na diagonal
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn()); // Posição abaixo do peão que moveu
				}
				else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn()); // Posição abaixo do peão que moveu
				}
				capturedPiece = board.removePiece(pawnPosition); // Remove do tabuleiro o peão que foi capturado
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}

		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target); // Remove a pe�a que foi colocada na posi��o de destino
		p.decreaseMoveCount();
		board.placePiece(p, source); // Coloca a pe�a de volta a sua posi��o de destino

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}

		// #specialmove castling kingside rook
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}

		// #specialmove castling queenside rook
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		
		// #specialmove en passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) { // Significa que o peão andou na diagonal
				ChessPiece pawn = (ChessPiece)board.removePiece(target);
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) { // se a branca que capturou, tenho que devolver a peça preta para a posição correta
					 pawnPosition = new Position(3, target.getColumn());
				}
				else { // se a preta que capturou, tenho que devolver a peça branca para a posição corretaa
					pawnPosition = new Position(4, target.getColumn());
				}
				board.placePiece(pawn, pawnPosition);
			}
		}
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) { // Downcasting de Piece para ChessPiece para usar o getColor()
			throw new ChessException("The chosen piece is not yours!");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}

	private void nextTurn() {
		turn++; // Aumenta os turnos
		currentPlayer = (getCurrentPlayer() == Color.WHITE) ? Color.BLACK : Color.WHITE; // Operador condicional tern�rio
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	
	private void validateCastlingMove(Position source, Position target) {
		if (!canCastling(source, target)) {
			throw new ChessException("You cannot castling if your king pass in a square that is dominated by an opponent piece");
		}
	}

	private boolean canCastling(Position source, Position target) {
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(currentPlayer)).collect(Collectors.toList());
		
		Position p1 = new Position(source.getRow(), source.getColumn() + 1);
		Position p2 = new Position(source.getRow(), source.getColumn() - 1);

		for (Piece p : opponentPieces) {
			if (target.getColumn() == source.getColumn() + 2) {
				if (p.possibleMove(p1)) {
					return false;
				}
			} 
			else if (target.getColumn() == source.getColumn() - 2) {
				if (p.possibleMove(p2)) {
					return false;
				}
			}
		}
		return true;
	}

	private Color opponent(Color color) { // Dado uma cor, retorna o oponente desta cor
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece king(Color color) { // Localiza o rei de uma determinada cor
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p; // Retorna o rei da cor localizada
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}

	private boolean testCheck(Color color) { // Teste se o rei desta cor est� em check
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) { // Se n�o estiver em check
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getRows(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece) p).getChessPosition().toPosition(); // Pega a posi��o da pe�a
						Position target = new Position(i, j); // Instancia a posi��o de destino
						Piece capturedPiece = makeMove(source, target); // Faz o movimento da pe�a
						boolean testCheck = testCheck(color); // Testa se o rei ainda est� em cheque mesmo ap�s o movimento
						undoMove(source, target, capturedPiece); // Desfaz o movimento ap�s a checagem
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	// ChessPiece piece = new Rook(board, Color.WHITE));

	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK, this));
		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}

}
