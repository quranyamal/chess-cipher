package chesschipher;

import chesscipher.model.CCBoard;
import com.nullpointergames.boardgames.Board;
import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.PieceColor;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.chess.ChessGame;
import com.nullpointergames.boardgames.chess.PieceType;
import com.nullpointergames.boardgames.chess.exceptions.PromotionException;
import com.nullpointergames.boardgames.chess.rules.PawnRule;
import org.junit.Test;

import java.util.List;

public class ChessLibTest {
    @Test
    public void xaa(){
        Board b = new Board(false);
        Piece pawn1 = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece pawn2 = new Piece(PieceType.QUEEN, PieceColor.BLACK);
        Position pawn1pos = new Position('b',6);
        Position pawn2pos = new Position('c',7);
        b.put(pawn1,pawn1pos);
        b.put(pawn2,pawn2pos);
        System.out.println(b);
        PawnRule pawnRule = new PawnRule(b,pawn1pos);
        List<Position> positionList = pawnRule.possibleMovesWithoutCheckVerification();
        for(Position p :positionList){
            System.out.println(pos2String(p));
        }
    }

    @Test
    public void testInitCCBoard(){
        CCBoard b = new CCBoard(true);
        b.initializeBoard();
        System.out.println(b);
    }

    @Test
    public void testGame(){
        ChessGame chessGame = new ChessGame(PieceColor.WHITE);
        System.out.println(chessGame.getBoard());
        System.out.println(chessGame.getTurn());
        try {
            chessGame.move(new Position('b',2),new Position('b',4));
        } catch (PromotionException e) {
            e.printStackTrace();
        }
        System.out.println(chessGame.getBoard());
        System.out.println(chessGame.getTurn());
        List<Position> positions = chessGame.getPossibleMoves(new Position('b',7));
        for(Position p:positions){
            System.out.println(pos2String(p));
        }
        System.out.println(chessGame.getBoard());
    }

    public String pos2String(Position p){
        return String.format("%c,%d",p.col(),p.row());
    }
}