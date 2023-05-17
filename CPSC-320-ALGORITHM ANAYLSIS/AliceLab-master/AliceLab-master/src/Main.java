import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JFileChooser;

public class Main {
	private int numBoards = 0;
	private int currentBoard = 0;
	private int startLocationX = 0;
	private int startLocationY = 0;
	private ArrayList<Board> boards = new ArrayList<Board>();
	private Stack<Object> moves = new Stack<Object>();;

	public static void main(String[] args) {
		Main controller = new Main();
		controller.go();
	}

	/**
	 * Primary Driver Code for Lab
	 */
	private void go() {
		getData();
		for (int i = 0; i < numBoards; i++) {
			int result = calculateRuns();
			outputResult(result);
			currentBoard++;
		}
	}

	/**
	 * Higher order Function that runs the BackTracking Requirements
	 * 
	 * @return
	 */
	private int calculateRuns() {
		// Create a Queue to hold the piece moves.
		Stack<Object> currentMoves = new Stack<Object>();
		// Get current board
		Board current = boards.get(currentBoard);
		// Finds which piece we are to start with
		getStartLocation(current);
		// Starts solving the board.
		int result = solve(current, startLocationX, startLocationY, currentMoves);
		// Returns the Integer Letting the Program Know which Result Occured
		return result;
	}

	/**
	 * Recursive BackTracking Algorithm that goes through each peace to add to a
	 * movement Stack
	 * 
	 * @param current
	 * @param nextX
	 * @param nextY
	 * @param currentMoves
	 * @return
	 */
	private int solve(Board current, int nextX, int nextY, Stack<Object> currentMoves) {
		// If this piece is the only piece left and were in the top row
		switchPiece(current, nextX, nextY);
		if (isLastPiece(current) == true && isTopRow(current) == true) {
			this.moves = currentMoves;
			return 1;
		}
		// If there is one piece left and its not on the top row show no condition
		else if (isLastPiece(current) == true && isTopRow(current) == false) {
			return 2;
		}
		Piece piece = current.getPiece(nextX, nextY);
		currentMoves.add(piece);
		current.setPiece(nextX, nextY, null);
		ArrayList<Point> nodes = getNodes(current, piece, nextX, nextY);

		for (int i = 0; i < nodes.size(); i++) {
			if (solve(current, nodes.get(i).x, nodes.get(i).y, currentMoves) == 1) {
				currentMoves.add(current.getPiece(nodes.get(i).x, nodes.get(i).y));
				this.moves = currentMoves;
				return 1;
			}
		}

		replacePiece(current, nextX, nextY, currentMoves);
		return 2;
	}

	/**
	 * Finds the Various Pieces that the current piece can capture and adds the
	 * points to a point array
	 * 
	 * @param current
	 * @param piece
	 * @param nextX
	 * @param nextY
	 * @return
	 */

	private ArrayList<Point> getNodes(Board current, Piece piece, int nextX, int nextY) {
		ArrayList<Point> peacePoints = new ArrayList<Point>();
		for (int i = 0; i < current.getHeight(); i++) {
			for (int j = 0; j < current.getWidth(); j++) {
				if (piece.isValidMove(nextX, nextY, i, j) == true) {
					if (current.getPiece(i, j) != null) {
						Point point = new Point(i, j);
						peacePoints.add(point);
					}
				}
			}
		}
		return peacePoints;
	}

	/**
	 * Reports true if the piece it reads is in the top row. This output is only
	 * relevant if there is a single piece remaining.
	 * 
	 * @param current
	 * @return
	 */
	private boolean isTopRow(Board current) {
		for (int i = 0; i < current.getHeight(); i++) {
			for (int j = 0; j < current.getWidth(); j++) {
				if (current.getPiece(i, j) != null && i == 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Counts number of Pieces on the Board and Returns True if the last piece is
	 * the only one.
	 * 
	 * @param current
	 * @return
	 */
	private boolean isLastPiece(Board current) {
		int totalPieces = 0;
		for (int i = 0; i < current.getHeight(); i++) {
			for (int j = 0; j < current.getWidth(); j++) {
				if (current.getPiece(i, j) != null) {
					totalPieces++;
				}
			}
		}
		if (totalPieces != 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Replaces the Stacked Piece from the Stack as it was not a valid Piece
	 * 
	 * @param current
	 * @param nextX
	 * @param nextY
	 * @param currentMoves
	 */
	private void replacePiece(Board current, int nextX, int nextY, Stack<Object> currentMoves) {
		// TODO Auto-generated method stub
		Piece piece = (Piece) currentMoves.pop();
		switch (((Piece) piece).getSymbol()) {
		case " ":
			break;
		case "p2":
			current.setPiece(nextX, nextY, new Pawn(PieceColor.White));
			break;
		case "R2":
			current.setPiece(nextX, nextY, new Rook(PieceColor.White));
			break;
		case "N2":
			current.setPiece(nextX, nextY, new Knight(PieceColor.White));
			break;
		case "B2":
			current.setPiece(nextX, nextY, new Bishop(PieceColor.White));
			break;
		case "Q2":
			current.setPiece(nextX, nextY, new Queen(PieceColor.White));
			break;
		case "K2":
			current.setPiece(nextX, nextY, new King(PieceColor.White));
			break;
		}
	}

	
	/**
	 * Switches the pieces with the color black in order to correctly calculate
	 * moves Without this there will be problems with the movement
	 * 
	 * @param current
	 * @param nextX
	 * @param nextY
	 */
	private void switchPiece(Board current, int nextX, int nextY) {
		Piece piece = current.getPiece(nextX, nextY);
		switch (piece.getSymbol()) {
		case " ":
			break;
		case "p1":
			current.setPiece(nextX, nextY, new Pawn(PieceColor.Black));
			break;
		case "R1":
			current.setPiece(nextX, nextY, new Rook(PieceColor.Black));
			break;
		case "N1":
			current.setPiece(nextX, nextY, new Knight(PieceColor.Black));
			break;
		case "B1":
			current.setPiece(nextX, nextY, new Bishop(PieceColor.Black));
			break;
		case "Q1":
			current.setPiece(nextX, nextY, new Queen(PieceColor.Black));
			break;
		case "K1":
			current.setPiece(nextX, nextY, new King(PieceColor.Black));
			break;
		}

	}

	
	/**
	 * Checks the Board Position for the Starting Piece
	 * 
	 * @param current
	 */
	private void getStartLocation(Board current) {
		for (int i = 7; i < current.getHeight(); i++) {
			for (int j = 0; j < current.getWidth(); j++) {
				if (current.getPiece(i, j) != null) {
					startLocationX = i;
					startLocationY = j;
				}
			}
		}
	}

	/**
	 * Outputs the Result of the Board to the Correct Answer
	 * 
	 * @param result
	 */
	private void outputResult(int result) {
		// TODO Auto-generated method stub
		if (result == 1) {
			String moves = convertMoves();
			System.out.println("Board " + (currentBoard + 1) + ": Alice should capture in this order: " + moves);
		}
		if (result == 2) {
			System.out.println("Board " + (currentBoard + 1) + ": Alice is stuck!");
		}
	}

	/**
	 * Converts the Moves in the Stack to a String for the Output Result
	 * 
	 * @return
	 */
	private String convertMoves() {
		String result = "";
		for (int i = 0; i < moves.size(); i++) {
			if (moves.get(i) == null) {

			} else {
				Piece piece = (Piece) moves.get(i);
				switch (((Piece) piece).getSymbol()) {
				case " ":
					break;
				case "p2":
					result += "P";
					break;
				case "R2":
					result += "R";
					break;
				case "N2":
					result += "N";
					break;
				case "B2":
					result += "B";
					break;
				case "Q2":
					result += "Q";
					break;
				case "K2":
					result += "K";
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Driver Code for Reading the File
	 * 
	 * @return Queue <Integer> data
	 */
	private void getData() {
		File file = getFile();
		readFile(file);
	}

	/**
	 * Reads the File Setting up the board as Needed Then Creates Each Board and
	 * Sends it to the ArrayList
	 * 
	 * @param file
	 */
	private void readFile(File file) {
		Scanner scnr;
		try {
			scnr = new Scanner(file);

			numBoards = scnr.nextInt();

			for (int k = 0; k < numBoards; k++) {
				Board board = new Board();
				for (int i = 0; i <= 8; i++) {
					String line = scnr.nextLine();
					for (int j = 0; j < line.length(); j++) {
						char c = line.charAt(j);
						switch (c) {

						case '-':
							break;
						case 'P':
							board.setPiece((i - 1), j, new Pawn(PieceColor.White));
							break;
						case 'R':
							board.setPiece((i - 1), j, new Rook(PieceColor.White));
							break;
						case 'N':
							board.setPiece((i - 1), j, new Knight(PieceColor.White));
							break;
						case 'B':
							board.setPiece((i - 1), j, new Bishop(PieceColor.White));
							break;
						case 'Q':
							board.setPiece((i - 1), j, new Queen(PieceColor.White));
							break;
						case 'K':
							board.setPiece((i - 1), j, new King(PieceColor.White));
							break;
						}
					}
				}
				boards.add(board);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return;
	}

	/**
	 * Gets File From User With JFile Chooser
	 * 
	 * @return
	 */
	private File getFile() {
		System.out.println("Please Select a File.");
		{
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				return file;
			} else if (returnVal == JFileChooser.CANCEL_OPTION) {
				System.out.println("No File Selected");
				System.exit(0);
			}
			return null;
		}
	}
}