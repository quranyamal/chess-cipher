package chesscipher.controller;

import chesscipher.model.ChessBoard;
import chesscipher.model.ChessCipherData;
import chesscipher.model.ChessCipherKey;
import com.nullpointergames.boardgames.PieceColor;
import com.nullpointergames.boardgames.chess.ChessGame;
import com.nullpointergames.boardgames.chess.exceptions.PromotionException;
import chesscipher.model.ChessGlobVar;

import java.util.Arrays;

public class ChessCipherEncryptor extends ChessCipherBase{

    public static void encrypt(ChessCipherData data, ChessCipherKey key) {
        key.resetRoundState();

        for (int i=0; i<data.numBlock; i++) {
            encryptBlock(data.getBlock(i), key);
        }
    }


    public static void encryptBlock(ChessBoard block, ChessCipherKey key) {
        chessGame = new ChessGame(PieceColor.WHITE);
        String subKey = key.getSubKey();
//        shiftBlockRight(block, subKey);

        chessPermutation(block,key);
        // todo
    }

    public static void shiftRight(ChessBoard block, String subKey) {
        for (int i=0; i<ChessBoard.SIZE; i++) {
            byte byt = block.getByte(i);
            byt++;
            block.setByte(i, byt);
        }
    }

    public static void applyKnightTourSubstitution(ChessBoard block) {
        System.out.println("Apply knight tour");
        boolean[][] matrix = new boolean[SIZE][SIZE];
        for (int row=0; row<SIZE; row++) {
            for (int col=0; col<SIZE; col++) {
                int subtPos = Arrays.asList(ChessGlobVar.knightBox).indexOf((row*SIZE)+col+1);
                boolean b = block.getBoolAt(row, col);
                block.setBoolAt(subtPos/8, subtPos%8, b);
                System.out.println("subtPos="+subtPos+" fill ["+subtPos/8+","+subtPos%8+"] with value from ["+row+","+col+"]");
            }
        }
    }

    public static void applySBox(ChessBoard block) {
        System.out.println("Apply SBox");
        for (int idx=0; idx<SIZE; idx++) {
            byte plain = block.getByte(idx);
            Integer subtVal = ChessGlobVar.sBox[(int)plain];
            block.setByte(idx, subtVal.byteValue());
            System.out.println("replace "+plain+" with "+subtVal);
        }
    }




    private static void chessPermutation(ChessBoard block, ChessCipherKey key){
        for(int i=0;i<MOVE_LIMIT;i++){
            try {
                chessGame.moveWithoutVerification(key.nextPiece(),key.nextDest(),block);
            } catch (PromotionException e) {
                e.printStackTrace();
            }
        }
    }
}
