package chesscipher.controller;

import chesscipher.controller.util.SwapEntry;
import chesscipher.model.ChessBoard;
import chesscipher.model.ChessCipherData;
import chesscipher.model.ChessCipherKey;
import com.nullpointergames.boardgames.PieceColor;
import com.nullpointergames.boardgames.chess.ChessGame;
import com.nullpointergames.boardgames.chess.exceptions.PromotionException;
import chesscipher.model.ChessGlobVar;

import java.util.Arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChessCipherDecryptor extends ChessCipherBase{
    static List<SwapEntry> swapEntries = new ArrayList<>();
    static int SIZE = ChessBoard.SIZE;

    public static void decryptOld(ChessCipherData data, ChessCipherKey key) {
        System.out.println("=======");
        key.resetRoundState();

        ChessCipherData cloneData = null;
        try {
            cloneData = (ChessCipherData) data.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        List<ChessBoard> cloneList = Arrays.asList(cloneData.blocks);
        seedRandBlock(cloneList.get(0));

        decryptBlock(data.getBlock(0),key);

        for (int i=1; i<data.numBlock; i++) {
//            decryptBlock(data.getBlock(i),key);
            decryptWithDiffusion(cloneList.subList(0,i),data.getBlock(i));
        }
    }

    public static void decrypt(ChessCipherData data, ChessCipherKey key) {
        System.out.println("====decrypting===");
        key.resetRoundState();

        for (int i=0; i<data.numBlock; i++) {
            decryptBlock(data.getBlock(i),key);
        }
    }

    public static void decryptBlock(ChessBoard block, ChessCipherKey key) {
        System.out.println("decrypting block");
        chessGame = new ChessGame(PieceColor.WHITE);
        String subKey = key.getSubKey();
//        shiftBlockLeft(block, subKey);

        //revertSBox(block);
        shiftLeft(block, subKey);
        //chessPermutation(block,key);
        // todo
    }

public static void decryptWithDiffusion(List<ChessBoard> encrypted, ChessBoard currBlock){
        for(ChessBoard board: encrypted){
            board.printBoard();
        }
        ChessCipherKey newKey = getRandKey(encrypted);
        System.out.println(newKey);
        decryptBlock(currBlock,newKey);
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
        System.out.print("bytes before: ");
        block.printBytes();
        for (int idx=0; idx<SIZE; idx++) {
            byte cipher = block.getByte(idx);
            //cipher++; // it is a must, dont know why
            Integer subtVal = Arrays.asList(ChessGlobVar.sBox).indexOf(cipher & 0xff);
            block.setByte(idx, subtVal.byteValue());
            System.out.println("replace "+(cipher & 0xff)+" with "+subtVal);
        }
        System.out.print("bytes after: ");
        block.printBytes();
    }

    private static void chessPermutation(ChessBoard block, ChessCipherKey key){
        swapEntries.clear();
        for(int i=0;i<MOVE_LIMIT;i++){
            try {
                swapEntries.add(i,chessGame.getSwapEntry(key.nextPiece(),key.nextDest(),block));
            } catch (PromotionException e) {
                e.printStackTrace();
            }
        }

        for(int i=swapEntries.size()-1;i>=0;i--){
            SwapEntry swapEntry = swapEntries.get(i);
            boolean temp = block.matrix[swapEntry.from.row()-1][swapEntry.from.col()-'a'];
            block.matrix[swapEntry.from.row()-1][swapEntry.from.col()-'a'] = block.matrix[swapEntry.to.row()-1][swapEntry.to.col()-'a'];
            block.matrix[swapEntry.to.row()-1][swapEntry.to.col()-'a'] = temp;
        }
    }
}
