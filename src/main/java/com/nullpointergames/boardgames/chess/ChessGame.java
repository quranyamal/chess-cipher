package com.nullpointergames.boardgames.chess;

import static com.nullpointergames.boardgames.PieceColor.BLACK;
import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static com.nullpointergames.boardgames.chess.PieceType.BISHOP;
import static com.nullpointergames.boardgames.chess.PieceType.KING;
import static com.nullpointergames.boardgames.chess.PieceType.KNIGHT;
import static com.nullpointergames.boardgames.chess.PieceType.PAWN;
import static com.nullpointergames.boardgames.chess.PieceType.QUEEN;
import static com.nullpointergames.boardgames.chess.PieceType.ROOK;
import static com.nullpointergames.boardgames.utils.MessageUtils.CHECK;
import static com.nullpointergames.boardgames.utils.MessageUtils.CHECKMATE;
import static com.nullpointergames.boardgames.utils.MessageUtils.CHOOSE_YOUR_PIECE;
import static com.nullpointergames.boardgames.utils.MessageUtils.GAME_OVER;
import static com.nullpointergames.boardgames.utils.MessageUtils.IT_IS_NOT_YOUR_TURN;
import static com.nullpointergames.boardgames.utils.MessageUtils.YOU_LOST;
import static com.nullpointergames.boardgames.utils.MessageUtils.YOU_WON;
import static com.nullpointergames.boardgames.utils.MessageUtils.getMessage;
import static java.lang.String.format;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chesscipher.controller.util.SwapEntry;
import chesscipher.model.ChessBoard;
import com.nullpointergames.boardgames.*;
import com.nullpointergames.boardgames.chess.exceptions.PromotionException;
import com.nullpointergames.boardgames.chess.rules.PawnRule;


public class ChessGame {
	public final Map<CCPieceType,Position> positionMap = new HashMap<>(32);

	private final Board board;
	private final PieceColor myColor;
	private PieceColor turn = WHITE;
	private boolean isOver;
	private PieceColor winner;

	public ChessGame(PieceColor myColor) {
		this(myColor, new Board(myColor != WHITE));
		putPieces();
	}
	
	private ChessGame(PieceColor myColor, Board board) {
		this.myColor = myColor;
		this.board = board;
	}

	public Board getBoard() {
		return board;
	}

	public void move(CCPieceType pieceType, int dest) throws PromotionException {
		final Position from = positionMap.get(pieceType);
		final Position to = new Position(dest);
		Rule rule = RuleFactory.getRule(board,from);
		List<Position> possibleMoves = rule.possibleMoves();
		if(possibleMoves.isEmpty()){
			moveWithoutVerification(from,from);
			return;
		}

		Position minPos = possibleMoves.get(0);
		double minDist = to.getDistance(minPos);
		for(int i=1;i<possibleMoves.size();i++){
			double dist = to.getDistance(possibleMoves.get(i));
			if(dist<minDist){
				minDist = dist;
				minPos = possibleMoves.get(i);
			}
		}

		move(from,minPos);
	}

	public void move(CCPieceType pieceType, final Position to) throws PromotionException {
		final Position from = positionMap.get(pieceType);
		move(from,to);
		positionMap.get(pieceType).set(to.col(),to.row());
	}

	public void move(final Position from, final Position to) throws PromotionException {
		if(isOver) {
			String result = winner == myColor ? getMessage(YOU_WON) : getMessage(YOU_LOST);
			throw new RuntimeException(format("%s. %s", getMessage(GAME_OVER), result));
		}
		
		if(board.getPieceColor(from) != myColor)
			throw new RuntimeException(getMessage(CHOOSE_YOUR_PIECE, getMessage(myColor.name())));
		
		if(board.getPieceColor(from) != turn)
			throw new RuntimeException(getMessage(IT_IS_NOT_YOUR_TURN));
		
		move(board, from, to);
		nextTurn();
	}

	public SwapEntry getSwapEntry(CCPieceType pieceType, int dest, ChessBoard board) throws PromotionException {
		Position validDest = moveWithoutVerification(pieceType,dest);
		final Position from = positionMap.get(pieceType);
		return new SwapEntry(from,validDest);
	}

	public void moveWithoutVerification(CCPieceType pieceType, int dest, ChessBoard board) throws PromotionException {
		Position validDest = moveWithoutVerification(pieceType,dest);
		final Position from = positionMap.get(pieceType);
		boolean temp = board.matrix[from.row()-1][from.col()-'a'];
		board.matrix[from.row()-1][from.col()-'a'] = board.matrix[validDest.row()-1][validDest.col()-'a'];
		board.matrix[validDest.row()-1][validDest.col()-'a'] = temp;
	}

	public Position moveWithoutVerification(CCPieceType pieceType, int dest) throws PromotionException {
		final Position from = positionMap.get(pieceType);
		final Position to = new Position(dest);
		Rule rule = RuleFactory.getRule(board,from);
		List<Position> possibleMoves = rule.possibleMoves();
		if(possibleMoves.isEmpty()){
			moveWithoutVerification(from,from);
			return from;
		}

		Position minPos = possibleMoves.get(0);
		double minDist = to.getDistance(minPos);
		for(int i=1;i<possibleMoves.size();i++){
			double dist = to.getDistance(possibleMoves.get(i));
			if(dist<minDist){
				minDist = dist;
				minPos = possibleMoves.get(i);
			}
		}

		moveWithoutVerification(from,minPos);
		return minPos;
	}

	public void moveWithoutVerification(CCPieceType pieceType, final Position to){
		final Position from = positionMap.get(pieceType);
		moveWithoutVerification(from,to);
	}
	
	public void moveWithoutVerification(final Position from, final Position to) {
		try {
			RuleFactory.getRule(board, from).moveWithoutVerification(to);
		} catch (PromotionException e) {}
		
		nextTurn();
	}
	
	public Block find(Position position) {
		return board.find(position);
	}
	
	public List<Position> getPossibleMoves(Position from) {
		return RuleFactory.getRule(board, from).possibleMoves();
	}
	
	public void promoteTo(PieceType pieceType) {
		PawnRule pawnRule = new PawnRule(board, null);
		pawnRule.promoteTo(board, pieceType);
	}

	public PieceColor getTurn() {
		return turn;
	}
	
	public void verifyCheckAndCheckmate() {
		if(isCheckmate()) {
			isOver = true;
			winner = turn == WHITE ? BLACK : WHITE;
			String result = winner == myColor ? getMessage(YOU_WON) : getMessage(YOU_LOST);
			throw new RuntimeException(format("%s. %s", getMessage(CHECKMATE), result));
		}
		
		if(isCheck())
			throw new RuntimeException(getMessage(CHECK));
	}

	public ChessGame clone() {
		ChessGame chessGame = new ChessGame(myColor, board.clone());
		chessGame.isOver = new Boolean(isOver);
		chessGame.winner = winner;
		chessGame.turn = turn == WHITE ? WHITE : BLACK;
		
		return chessGame;
	}
	
	private void move(Board board, Position from, Position to) throws PromotionException {
		RuleFactory.getRule(board, from).move(to);
	}
	
	private void putPiece(PieceType type, PieceColor color, char col, int row) {
		board.put(new Piece(type, color), new Position(col, row));		
	}

	private final void nextTurn() {
		turn = turn.equals(WHITE) ? BLACK : WHITE;
	}

	private boolean isCheckmate() {
		for(Block b : board) {
			Piece piece = board.getPiece(b.position());
			if(piece.color() != turn)
				continue;
			
			List<Position> possibleMoves = RuleFactory.getRule(board, b.position()).possibleMoves();
			if(!possibleMoves.isEmpty())
				return false;
		}
		
		return true;
	}

	private boolean isCheck() {
		for(Block b : board)
			for(Position position : RuleFactory.getRule(board, b.position()).possibleMoves())
				if(board.getPieceType(position) == KING)
					return true;
			
		return false;
	}
	
	private void putPieces() {
		positionMap.put(CCPieceType.WHITE_ROOK1, new Position('a',1));
		putPiece(ROOK, WHITE, 'a', 1);
		positionMap.put(CCPieceType.WHITE_KNIGHT1, new Position('b',1));
		putPiece(KNIGHT, WHITE, 'b', 1);
		positionMap.put(CCPieceType.WHITE_BISHOP1, new Position('c',1));
		putPiece(BISHOP, WHITE, 'c', 1);
		positionMap.put(CCPieceType.WHITE_QUEEN, new Position('d',1));
		putPiece(QUEEN, WHITE, 'd', 1);
		positionMap.put(CCPieceType.WHITE_KING, new Position('e',1));
		putPiece(KING, WHITE, 'e', 1);
		positionMap.put(CCPieceType.WHITE_BISHOP2, new Position('f',1));
		putPiece(BISHOP, WHITE, 'f', 1);
		positionMap.put(CCPieceType.WHITE_KNIGHT2, new Position('g',1));
		putPiece(KNIGHT, WHITE, 'g', 1);
		positionMap.put(CCPieceType.WHITE_ROOK2, new Position('h',1));
		putPiece(ROOK, WHITE, 'h', 1);

		positionMap.put(CCPieceType.WHITE_PAWN1, new Position('a',2));
		putPiece(PAWN, WHITE, 'a', 2);
		positionMap.put(CCPieceType.WHITE_PAWN2, new Position('b',2));
		putPiece(PAWN, WHITE, 'b', 2);
		positionMap.put(CCPieceType.WHITE_PAWN3, new Position('c',2));
		putPiece(PAWN, WHITE, 'c', 2);
		positionMap.put(CCPieceType.WHITE_PAWN4, new Position('d',2));
		putPiece(PAWN, WHITE, 'd', 2);
		positionMap.put(CCPieceType.WHITE_PAWN5, new Position('e',2));
		putPiece(PAWN, WHITE, 'e', 2);
		positionMap.put(CCPieceType.WHITE_PAWN6, new Position('f',2));
		putPiece(PAWN, WHITE, 'f', 2);
		positionMap.put(CCPieceType.WHITE_PAWN7, new Position('g',2));
		putPiece(PAWN, WHITE, 'g', 2);
		positionMap.put(CCPieceType.WHITE_PAWN8, new Position('h',2));
		putPiece(PAWN, WHITE, 'h', 2);


		positionMap.put(CCPieceType.BLACK_ROOK1, new Position('a',8));
		putPiece(ROOK, BLACK, 'a', 8);
		positionMap.put(CCPieceType.BLACK_KNIGHT1, new Position('b',8));
		putPiece(KNIGHT, BLACK, 'b', 8);
		positionMap.put(CCPieceType.BLACK_BISHOP1, new Position('c',8));
		putPiece(BISHOP, BLACK, 'c', 8);
		positionMap.put(CCPieceType.BLACK_QUEEN, new Position('d',8));
		putPiece(QUEEN, BLACK, 'd', 8);
		positionMap.put(CCPieceType.BLACK_KING, new Position('e',8));
		putPiece(KING, BLACK, 'e', 8);
		positionMap.put(CCPieceType.BLACK_BISHOP2, new Position('f',8));
		putPiece(BISHOP, BLACK, 'f', 8);
		positionMap.put(CCPieceType.BLACK_KNIGHT2, new Position('g',8));
		putPiece(KNIGHT, BLACK, 'g', 8);
		positionMap.put(CCPieceType.BLACK_ROOK2, new Position('h',8));
		putPiece(ROOK, BLACK, 'h', 8);

		positionMap.put(CCPieceType.BLACK_PAWN1, new Position('a',7));
		putPiece(PAWN, BLACK, 'a', 7);
		positionMap.put(CCPieceType.BLACK_PAWN2, new Position('b',7));
		putPiece(PAWN, BLACK, 'b', 7);
		positionMap.put(CCPieceType.BLACK_PAWN3, new Position('c',7));
		putPiece(PAWN, BLACK, 'c', 7);
		positionMap.put(CCPieceType.BLACK_PAWN4, new Position('d',7));
		putPiece(PAWN, BLACK, 'd', 7);
		positionMap.put(CCPieceType.BLACK_PAWN5, new Position('e',7));
		putPiece(PAWN, BLACK, 'e', 7);
		positionMap.put(CCPieceType.BLACK_PAWN6, new Position('f',7));
		putPiece(PAWN, BLACK, 'f', 7);
		positionMap.put(CCPieceType.BLACK_PAWN7, new Position('g',7));
		putPiece(PAWN, BLACK, 'g', 7);
		positionMap.put(CCPieceType.BLACK_PAWN8, new Position('h',7));
		putPiece(PAWN, BLACK, 'h', 7);
	}
}
