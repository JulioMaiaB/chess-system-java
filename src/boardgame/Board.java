package boardgame;

public class Board {
	
	private int rows;    		// Quantidade de linhas do tabuleiro
	private int columns;	 	// Quantidade de colunas do tabuleiro
	private Piece[][] pieces;	// Possui uma matriz de pe�as
	
	
	public Board(int rows, int columns) {
		// Condi��o de programa��o defensiva e tratamento de erro
		if (rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: there must be at leat 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns]; // A Matriz ser� gerada na quantidade de linhas e colunas do tabuleiro
	}
	
	/* Getters e Setters: 
	   As PE�AS n�o possuem Getters e Setters pois h� m�todos na classes Board que retornam uma pe�a dado uma linha e uma coluna ou
	   uma posi��o
	   OBS: A classe n�o retornar� a matriz inteira, somente uma pe�a por vez.*/
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	// Este m�todo substitui um m�todo GET
	public Piece piece(int row, int column) { 
		// Programa��o Defensiva: verifica se o resultado for igual a falso para lan�ar a exce��o
		if(!positionExists(row, column)) {
			throw new BoardException("Position not on the board");
		}
		/* Retorna apenas a PE�A que estiver na posi��o da matriz;
		   � puxado de um FOR na classe ChessMatch. Em seguida � feito o Downcasting de Piece para ChessPiece;*/
		return pieces[row][column]; 
	}
	
	// Este m�todo substitui um m�todo GET 
	public Piece piece(Position position) {
		// Programa��o Defensiva: verifica se o resultado for igual a falso para lan�ar a exce��o
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	// Recebe uma pe�a e uma posi��o e � respons�vel por colocar a pe�a na posi��o certa no tabuleiro.
	public void placePiece(Piece piece, Position position) {
		// Antes de colocar uma pe�a na posi��o, � preciso verificar se ja existe uma pe�a nessa posi��o
		if (thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position " + position);
		}
		/* Vai na matriz de pe�as do TABULEIRO e atribui a pe�a � posi��o.
		   L�-se: Na matriz de pe�as do tabuleiro e na posi��o recebida pelo m�todo, recebe uma pe�a. */
		pieces[position.getRow()][position.getColumn()] = piece;
		// A posi��o da pe�a � acess�vel diretamente por estar no mesmo pacote de TABULEIRO (boardgame).
		piece.position = position;
	}
	
	/* - M�todo auxiliar que recebe uma linha e uma coluna;
	   - Este m�todo foi criado, porque em algum momento da classe, vai ter um momento que ser� mais f�cil testar pela linha e pela coluna
	     do que pela posi��o; */
	private boolean positionExists(int row, int column) {
		// OBS: Eu sei se a posi��o existe se a mesma est� dentro do tabuleiro
		return row >= 0 && row < rows && column >= 0 && column < columns; // true or false
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		// Antes faz uma valida��o. Se a posi��o n�o existir, o programa para e lan�a uma exce��o
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null; // Se for diferente de nulo, ent�o tem uma pe�a nesta posi��o
	}
}
