package application;

import chess.ChessPiece;


public class UI {
	
	// M�todo que recebe a matriz pe�as da partida (ChessMatch)
	public static void printBoard(ChessPiece[][] pieces) {
		for (int i = 0; i < pieces.length; i++ ) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
		
	}
	
	// M�todo auxiliar para imprimir uma pe�a
	private static void printPiece(ChessPiece piece) {
		if (piece == null) {
			System.out.print("-");
		}
		else {
			System.out.println(piece);
		}
		System.out.print(" ");
	}
	
}
