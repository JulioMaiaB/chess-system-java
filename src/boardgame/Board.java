package boardgame;

public class Board {
	
	private int rows;    		// Quantidade de linhas do tabuleiro
	private int columns;	 	// Quantidade de colunas do tabuleiro
	private Piece[][] pieces;	// Possui uma matriz de pe�as
	
	
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns]; // A Matriz ser� gerada na quantidade de linhas e colunas do tabuleiro
	}
	
	// Getters e Setters: 
	// As PE�AS n�o possuem Getters e Setters pois h� m�todos na classes Board que retornam uma pe�a dado uma linha e uma coluna ou
	// uma posi��o
	// OBS: A classe n�o retornar� a matriz inteira, somente uma pe�a por vez.
	public int getRows() {
		return rows;
	}


	public void setRows(int rows) {
		this.rows = rows;
	}


	public int getColumns() {
		return columns;
	}


	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public Piece piece(int row, int column) { 
		// Retorna apenas a PE�A que estiver na posi��o da matriz;
		// � puxado de um FOR na classe ChessMatch. Em seguida � feito o Downcasting de Piece para ChessPiece;
		return pieces[row][column]; 
	}
	
	public Piece piece(Position position) {
		return pieces[position.getRow()][position.getColumn()];
	}
	
	// Recebe uma pe�a e uma posi��o. Respons�vel por colocar a pe�a na posi��o do tabuleiro.
	public void placePiece(Piece piece, Position position) {
		// Vai na matriz de pe�as do TABULEIRO e atribui a pe�a � posi��o
		// L�-se: Na matriz de pe�as do tabuleiro, na posi��o recebida pelo m�todo, recebe uma pe�a.
		pieces[position.getRow()][position.getColumn()] = piece;
		// A posi��o da pe�a � acess�vel diretamente por estar no mesmo pacote de TABULEIRO(boardgame)
		piece.position = position;
	}
	
}
