package chesscipher.controller;

import chesscipher.model.ChessBoard;
import chesscipher.model.ChessCipherData;
import chesscipher.model.ChessCipherKey;

public class ChessCipherDecryptor {

    public static void decrypt(ChessCipherData data, ChessCipherKey key) {
        key.resetRoundState();

        for (int i=0; i<data.numBlock; i++) {
            decryptBlock(data.getBlock(i), key.getSubKey());
        }

    }

    public static void decryptBlock(ChessBoard block, String subKey) {
        shiftBlockLeft(block, subKey);
        // todo
    }

    public static void shiftBlockLeft(ChessBoard block, String subKey) {
        for (int i=0; i<ChessBoard.SIZE; i++) {
            byte byt = block.getByte(i);
            byt--;
            block.setMatrixRow(i, byt);
        }
    }
    
}
