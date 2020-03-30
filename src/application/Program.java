package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>();
		
		
		while(!chessMatch.getCheckmate()) { // O programa roda enquanto o checkmate for igual a false
			try {		
				UI.clearScreen();
				UI.printMatch(chessMatch, captured);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source); //retorna os movimentos possiveis da peça {King, Rook}
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);//Exibe o tabuleiro com os movimentos da peça escolhida
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				
				if(capturedPiece != null) { // significa que alguma peça foi capturada
					captured.add(capturedPiece); // Adiciona na lista de peças capturadas
				}
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
		UI.clearScreen();
		UI.printMatch(chessMatch, captured);
	}

}
