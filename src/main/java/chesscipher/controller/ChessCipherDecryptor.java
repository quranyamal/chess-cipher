package chesscipher.controller;

import chesscipher.model.ChessBoard;
import chesscipher.model.ChessCipherData;
import chesscipher.model.ChessCipherKey;
import chesscipher.model.ChessGlobVar;

import java.util.Arrays;

public class ChessCipherDecryptor {
    static int SIZE = ChessBoard.SIZE;

    public static void decrypt(ChessCipherData data, ChessCipherKey key) {
        key.resetRoundState();

        for (int i=0; i<data.numBlock; i++) {
            decryptBlock(data.getBlock(i), key.getSubKey());
        }

    }

    public static void decryptBlock(ChessBoard block, String subKey) {
        revertSBox(block);
        shiftLeft(block, subKey);
        // todo
    }


    public static void shiftLeft(ChessBoard block, String subKey) {
        for (int i=0; i<ChessBoard.SIZE; i++) {
            byte byt = block.getByte(i);
            byt--;
            block.setByte(i, byt);
        }
    }

    public static void revertKnightTourSubstitution(ChessBoard block) {
        System.out.println("revertKnightTourSubstitution");
        boolean[][] matrix = new boolean[SIZE][SIZE];
        for (int row=0; row<SIZE; row++) {
            for (int col=0; col<SIZE; col++) {
                int subtPos = Arrays.asList(ChessGlobVar.knightBox).indexOf((row*SIZE)+col+1);
                boolean b = block.getBoolAt(subtPos/8, subtPos%8);
                block.setBoolAt(row, col, b);
                System.out.println("subtPos="+subtPos+" fill ["+row+","+col+"] with value from ["+subtPos/8+","+subtPos%8+"]");
            }
        }
    }

    private static void revertSBox(ChessBoard block) {
        System.out.println("Revert SBox");
        for (int idx=0; idx<SIZE; idx++) {
            byte cipher = block.getByte(idx);
            //cipher++; // it is a must, dont know why
            Integer subtVal = Arrays.asList(ChessGlobVar.sBox).indexOf(cipher & 0xff);
            block.setByte(idx, subtVal.byteValue());
            System.out.println("replace "+(cipher & 0xff)+" with "+subtVal);
        }
    }

}
