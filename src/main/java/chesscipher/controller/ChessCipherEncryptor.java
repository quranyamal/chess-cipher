package chesscipher.controller;

import chesscipher.ChessCipher;
import chesscipher.model.ChessBoard;
import chesscipher.model.ChessCipherData;
import chesscipher.model.ChessCipherKey;
import com.nullpointergames.boardgames.PieceColor;
import com.nullpointergames.boardgames.chess.ChessGame;
import com.nullpointergames.boardgames.chess.exceptions.PromotionException;

import java.util.List;

public class ChessCipherEncryptor extends ChessCipherBase{

    public static void encrypt(ChessCipherData data, ChessCipherKey key) {
        key.resetRoundState();

        encryptBlock(data.getBlock(0),key);
        seedRandBlock(data.getBlock(0));
        encryptedList.add(data.getBlock(0));

        for (int i=1; i<data.numBlock; i++) {
            encryptBlock(data.getBlock(i),key);
//            encryptWithDiffusion(encryptedList,data.getBlock(i));
            encryptedList.add(data.getBlock(i));
        }
    }


    public static void encryptBlock(ChessBoard block, ChessCipherKey key) {
        chessGame = new ChessGame(PieceColor.WHITE);
        String subKey = key.getSubKey();
//        shiftBlockRight(block, subKey);

        chessPermutation(block,key);
        // todo
    }

    public static void encryptWithDiffusion(List<ChessBoard> encrypted, ChessBoard currBlock){
        for(ChessBoard board: encrypted){
            board.printBoard();
        }
        ChessCipherKey newKey = getRandKey(encrypted);
        System.out.println(newKey);
        encryptBlock(currBlock,newKey);
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
