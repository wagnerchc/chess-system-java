package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{
	
	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}
	
	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}
	
	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		int row = position.getRow();
		int column = position.getColumn();
		
		p.setValues(row - 1, column);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(row + 1, column);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(row, column - 1);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(row, column + 1);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(row + 1, column + 1);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(row - 1, column - 1);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		p.setValues(row + 1, column - 1);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(row - 1, column + 1);
		
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//SpecialMoveCastling
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			//Special Move King Side Rook
			Position positionOneKingSide = new Position(position.getRow(), position.getColumn() + 1);
			Position positionTwoKingSide = new Position(position.getRow(), position.getColumn() + 2);
			Position RookSideKing = new Position(position.getRow(), position.getColumn() + 3);
			if (testRookCastling(RookSideKing) && getBoard().piece(positionOneKingSide) == null && getBoard().piece(positionTwoKingSide) == null) {
				mat[position.getRow()][position.getColumn() + 2] = true;
			}
			//Special Move Queen Side Rook
			Position positionOneQueenSide = new Position(position.getRow(), position.getColumn() - 1);
			Position positionTwoQueenSide = new Position(position.getRow(), position.getColumn() - 2);
			Position positionThreeQueenSide = new Position(position.getRow(), position.getColumn() - 2);
			Position RookSideQueen = new Position(position.getRow(), position.getColumn() - 4);
			if (testRookCastling(RookSideQueen) && getBoard().piece(positionOneQueenSide) == null && getBoard().piece(positionTwoQueenSide) == null && getBoard().piece(positionThreeQueenSide) == null) {
				mat[position.getRow()][position.getColumn() - 2] = true;
			}
		}
		
		return mat;
	}
}