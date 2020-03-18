package boardgame;

public class Board {
	
	private int rows;    		// Quantidade de linhas do tabuleiro
	private int columns;	 	// Quantidade de colunas do tabuleiro
	private Piece[][] pieces;	// Possui uma matriz de peças
	
	
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns]; // A Matriz será gerada na quantidade de linhas e colunas do tabuleiro
	}
	
	// Getters e Setters: 
	// As PEÇAS não possuem Getters e Setters pois há métodos na classes Board que retornam uma peça dado uma linha e uma coluna ou
	// uma posição
	// OBS: A classe não retornará a matriz inteira, somente uma peça por vez.
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
	
	
	
}
