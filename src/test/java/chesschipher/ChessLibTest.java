package chesschipher;

import chesscipher.model.CCBoard;
import chesscipher.model.ChessCipherKey;
import com.nullpointergames.boardgames.Board;
import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.PieceColor;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.chess.CCPieceType;
import com.nullpointergames.boardgames.chess.ChessGame;
import com.nullpointergames.boardgames.chess.PieceType;
import com.nullpointergames.boardgames.chess.exceptions.PromotionException;
import com.nullpointergames.boardgames.chess.rules.PawnRule;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

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
    public void testIdx2Pos(){
        System.out.println(new Position(63));
    }

    @Test
    public void testGame(){
        ChessCipherKey cipherKey = new ChessCipherKey("percobaan");
        ChessGame chessGame = new ChessGame(PieceColor.WHITE);
        System.out.println(chessGame.getBoard());
        System.out.println(chessGame.getTurn());
        try {
            chessGame.move(CCPieceType.WHITE_PAWN2, new Position('b',4));
        } catch (PromotionException e) {
            e.printStackTrace();
        }
        System.out.println(chessGame.getBoard());
        chessGame.moveWithoutVerification(CCPieceType.BLACK_PAWN2, new Position('b',5));
        System.out.println(pos2String(chessGame.positionMap.get(CCPieceType.WHITE_PAWN2)));
        try {
            chessGame.move(CCPieceType.WHITE_PAWN2, 32);
        } catch (PromotionException e) {
            e.printStackTrace();
        }
        System.out.println(chessGame.getBoard());
        System.out.println((chessGame.positionMap.get(CCPieceType.WHITE_PAWN2)));
        chessGame.moveWithoutVerification(CCPieceType.BLACK_KNIGHT1, new Position('a',6));
        System.out.println(chessGame.getBoard());
    }

    @Test
    public void testPieceId(){
        assertEquals(CCPieceType.BLACK_PAWN1,CCPieceType.getCCPieceType(PieceColor.WHITE,16));
    }

    @Test
    public void testRNG(){
        ChessCipherKey cck = new ChessCipherKey("halohal1");
        ChessGame chessGame = new ChessGame(PieceColor.WHITE);
        System.out.println(chessGame.getBoard());
        for(int i=0;i<100;i++){
            System.out.println(chessGame.getTurn());
            try {
                chessGame.moveWithoutVerification(cck.nextPiece(),cck.nextDest());
            } catch (PromotionException e) {
                e.printStackTrace();
            }
            System.out.println(chessGame.getBoard());
        }
    }

    public String pos2String(Position p){
        return String.format("%c,%d",p.col(),p.row());
    }
}
