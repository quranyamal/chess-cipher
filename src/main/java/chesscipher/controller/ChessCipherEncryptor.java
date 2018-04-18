package chesscipher.controller;

import chesscipher.ChessCipher;
import chesscipher.model.ChessBoard;
import chesscipher.model.ChessCipherData;
import chesscipher.model.ChessCipherKey;
import com.nullpointergames.boardgames.PieceColor;
import com.nullpointergames.boardgames.chess.ChessGame;
import com.nullpointergames.boardgames.chess.exceptions.PromotionException;
import chesscipher.model.ChessGlobVar;

import java.util.Arrays;

import java.util.List;

public class ChessCipherEncryptor extends ChessCipherBase{
    private static int SIZE = ChessBoard.SIZE;

    public static void encryptOld(ChessCipherData data, ChessCipherKey key) {
        key.resetRoundState();

        encryptBlock(data.getBlock(0),key);
        seedRandBlock(data.getBlock(0));
        encryptedList.add(data.getBlock(0));

        for (int i=1; i<data.numBlock; i++) {
//            encryptBlock(data.getBlock(i),key);
            encryptWithDiffusion(encryptedList,data.getBlock(i));
            encryptedList.add(data.getBlock(i));
        }
    }

    public static void encrypt(ChessCipherData data, ChessCipherKey key) {
        key.resetRoundState();

        System.out.println("num block="+data.numBlock);
        System.out.println("encrypting...");

        for (int i=0; i<data.numBlock; i++) {
            encryptBlock(data.getBlock(i),key);
        }
    }

    public static void encryptBlock(ChessBoard block, ChessCipherKey key) {
        System.out.println("encrypting block");

        chessGame = new ChessGame(PieceColor.WHITE);
        String subKey = key.getSubKey();

        //chessPermutation(block,key);
        vigenereShift(block, subKey);
        //applySBox(block);
        //applyKnightTourSubstitution(block);
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

    public static void shiftRight(ChessBoard block) {
        for (int i=0; i<ChessBoard.SIZE; i++) {
            byte byt = block.getByte(i);
            byt++;
            block.setByte(i, byt);
        }
    }

    public static void vigenereShift(ChessBoard block, String subKey) {
        System.out.println("vigenere shift with subkey: " + subKey);
        char[] chars = subKey.toCharArray();
        for (int i=0; i<ChessBoard.SIZE; i++) {
            byte byt = block.getByte(i);
            byt += ((int) chars[i] - 64) % 255;
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
        System.out.print("bytes before: ");
        block.printBytes();
        for (int idx=0; idx<SIZE; idx++) {
            byte plain = block.getByte(idx);
            Integer subtVal = ChessGlobVar.sBox[plain & 0xff];
            block.setByte(idx, subtVal.byteValue());
            System.out.println("replace "+plain+" with "+subtVal);
        }
        System.out.print("bytes after: ");
        block.printBytes();
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
