package boardgame;

public class Board {
	
	private int rows;    		// Quantidade de linhas do tabuleiro
	private int columns;	 	// Quantidade de colunas do tabuleiro
	private Piece[][] pieces;	// Possui uma matriz de peças
	
	
	public Board(int rows, int columns) {
		// Condição de programação defensiva e tratamento de erro
		if (rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: there must be at leat 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns]; // A Matriz será gerada na quantidade de linhas e colunas do tabuleiro
	}
	
	/* Getters e Setters: 
	   As PEÇAS não possuem Getters e Setters pois há métodos na classes Board que retornam uma peça dado uma linha e uma coluna ou
	   uma posição
	   OBS: A classe não retornará a matriz inteira, somente uma peça por vez.*/
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	// Este método substitui um método GET
	public Piece piece(int row, int column) { 
		// Programação Defensiva: verifica se o resultado for igual a falso para lançar a exceção
		if(!positionExists(row, column)) {
			throw new BoardException("Position not on the board");
		}
		/* Retorna apenas a PEÇA que estiver na posição da matriz;
		   É puxado de um FOR na classe ChessMatch. Em seguida é feito o Downcasting de Piece para ChessPiece;*/
		return pieces[row][column]; 
	}
	
	// Este método substitui um método GET 
	public Piece piece(Position position) {
		// Programação Defensiva: verifica se o resultado for igual a falso para lançar a exceção
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	// Recebe uma peça e uma posição e é responsável por colocar a peça na posição certa no tabuleiro.
	public void placePiece(Piece piece, Position position) {
		// Antes de colocar uma peça na posição, é preciso verificar se ja existe uma peça nessa posição
		if (thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position " + position);
		}
		/* Vai na matriz de peças do TABULEIRO e atribui a peça à posição.
		   Lê-se: Na matriz de peças do tabuleiro e na posição recebida pelo método, recebe uma peça. */
		pieces[position.getRow()][position.getColumn()] = piece;
		// A posição da peça é acessível diretamente por estar no mesmo pacote de TABULEIRO (boardgame).
		piece.position = position;
	}
	
	/* - Método auxiliar que recebe uma linha e uma coluna;
	   - Este método foi criado, porque em algum momento da classe, vai ter um momento que será mais fácil testar pela linha e pela coluna
	     do que pela posição; */
	private boolean positionExists(int row, int column) {
		// OBS: Eu sei se a posição existe se a mesma está dentro do tabuleiro
		return row >= 0 && row < rows && column >= 0 && column < columns; // true or false
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		// Antes faz uma validação. Se a posição não existir, o programa para e lança uma exceção
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null; // Se for diferente de nulo, então tem uma peça nesta posição
	}
}
