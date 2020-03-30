package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		
		while(true) {
			try {		
				UI.clearScreen();
				UI.printMatch(chessMatch);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source); //retorna os movimentos possiveis da peça {King, Rook}
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);//Exibe o tabuleiro com os movimentos da peça escolhida
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				// O  vai ser feito com essa peça capturada ? Cenas do próximo capítulo
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
			}
			catch(ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine(); // Usado para apertar ENTER quando o programa parar
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine(); // Usado para apertar ENTER quando o programa parar
			}
		}
	}

}
