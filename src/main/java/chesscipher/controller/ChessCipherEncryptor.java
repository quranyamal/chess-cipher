package chesscipher.controller;

import chesscipher.model.ChessBoard;
import chesscipher.model.ChessCipherData;
import chesscipher.model.ChessCipherKey;

public class ChessCipherEncryptor {
    
    public static void encrypt(ChessCipherData data, ChessCipherKey key) {
        key.resetRoundState();

        for (int i=0; i<data.numBlock; i++) {
            encryptBlock(data.getBlock(i), key.getSubKey());
        }
    }


    public static void encryptBlock(ChessBoard block, String subKey) {
        shiftBlockRight(block, subKey);
        // todo
    }

    public static void shiftBlockRight(ChessBoard block, String subKey) {
        for (int i=0; i<ChessBoard.SIZE; i++) {
            byte byt = block.getByte(i);
            byt++;
            block.setMatrixRow(i, byt);
        }
    }
    
}
