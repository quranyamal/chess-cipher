package chesscipher.controller;

import chesscipher.model.ChessBoard;
import chesscipher.model.ChessCipherData;
import chesscipher.model.ChessCipherKey;
import com.nullpointergames.boardgames.PieceColor;
import com.nullpointergames.boardgames.chess.ChessGame;
import com.nullpointergames.boardgames.chess.exceptions.PromotionException;

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

    public static void shiftBlockRight(ChessBoard block, String subKey) {
        for (int i=0; i<ChessBoard.SIZE; i++) {
            byte byt = block.getByte(i);
            byt++;
            block.setMatrixRow(i, byt);
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
