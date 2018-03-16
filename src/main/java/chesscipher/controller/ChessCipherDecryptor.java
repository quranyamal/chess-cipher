package chesscipher.controller;

import chesscipher.controller.util.SwapEntry;
import chesscipher.model.ChessBoard;
import chesscipher.model.ChessCipherData;
import chesscipher.model.ChessCipherKey;
import com.nullpointergames.boardgames.PieceColor;
import com.nullpointergames.boardgames.chess.ChessGame;
import com.nullpointergames.boardgames.chess.exceptions.PromotionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChessCipherDecryptor extends ChessCipherBase{
    static List<SwapEntry> swapEntries = new ArrayList<>();

    public static void decrypt(ChessCipherData data, ChessCipherKey key) {
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
            decryptBlock(data.getBlock(i),key);
//            decryptWithDiffusion(cloneList.subList(0,i),data.getBlock(i));
        }
    }

    public static void decryptBlock(ChessBoard block, ChessCipherKey key) {
        chessGame = new ChessGame(PieceColor.WHITE);
        String subKey = key.getSubKey();
//        shiftBlockLeft(block, subKey);

        chessPermutation(block,key);
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

    public static void shiftBlockLeft(ChessBoard block, String subKey) {
        for (int i=0; i<ChessBoard.SIZE; i++) {
            byte byt = block.getByte(i);
            byt--;
            block.setMatrixRow(i, byt);
        }
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
