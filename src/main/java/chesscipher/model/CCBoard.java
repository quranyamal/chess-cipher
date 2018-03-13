package chesscipher.model;

import com.nullpointergames.boardgames.Board;
import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.PieceColor;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.chess.PieceType;

import java.util.ArrayList;
import java.util.List;

// todo
public class CCBoard extends Board {
    private static final int whitePawnsRow = 2;
    private static final int blackPawnsRow = 7;
    private static final int whitePieceRow = 1;
    private static final int blackPieceRow = 8;


    public CCBoard(boolean rotate) {
        super(rotate);
    }

    public void initializeBoard(){
        initializeColor(true);
        initializeColor(false);
    }

    public void initializeColor(boolean isWhite){
        PieceColor pieceColor = (isWhite)?PieceColor.WHITE:PieceColor.BLACK;
        List<Piece> pawns = new ArrayList<>(8);
        for(int i=0;i<8;i++){
            pawns.add(i,new Piece(PieceType.PAWN, pieceColor));
        }
        for(int i=0;i<8;i++){
            this.put(pawns.get(i),new Position((char)('a'+i),(isWhite)?whitePawnsRow:blackPawnsRow));
        }

        int pieceRow = (isWhite)?whitePieceRow:blackPieceRow;
        this.put(new Piece(PieceType.ROOK,pieceColor),new Position('a',pieceRow));
        this.put(new Piece(PieceType.ROOK,pieceColor),new Position('h',pieceRow));
        this.put(new Piece(PieceType.KNIGHT,pieceColor),new Position('b',pieceRow));
        this.put(new Piece(PieceType.KNIGHT,pieceColor),new Position('g',pieceRow));
        this.put(new Piece(PieceType.BISHOP,pieceColor),new Position('c',pieceRow));
        this.put(new Piece(PieceType.BISHOP,pieceColor),new Position('f',pieceRow));
        this.put(new Piece(PieceType.QUEEN,pieceColor),new Position('d',pieceRow));
        this.put(new Piece(PieceType.KING,pieceColor),new Position('e',pieceRow));
    }
}
