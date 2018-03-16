package chesscipher.controller;

import chesscipher.model.ChessBoard;
import chesscipher.model.ChessCipherKey;
import com.nullpointergames.boardgames.PieceColor;
import com.nullpointergames.boardgames.chess.ChessGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class ChessCipherBase {
    static final int MOVE_LIMIT = 100;
    static ChessGame chessGame;
    static List<ChessBoard> encryptedList = new ArrayList<>();
    static Random random = null;

    public static void seedRandBlock(ChessBoard board){
        long seed = 0;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                int val = (board.matrix[i][j])?1:0;
                seed+=val<<(i*8)+j;
            }
        }
        random = new Random(seed);
    }

    public static ChessCipherKey getRandKey(List<ChessBoard> encrypted){
//        for(ChessBoard board: encrypted){
//            board.printBoard();
//        }
        int keyLength = 64;
        char[] chars = new char[keyLength/8];
        int idx=0;
        int c=0;
        for(int i=0;i<keyLength;i++){
            int blockIdx = random.nextInt(encrypted.size());
            ChessBoard chessBoard = encrypted.get(blockIdx);
            int row = random.nextInt(8);
            int col = random.nextInt(8);
            int val = (chessBoard.matrix[row][col])?1:0;
            System.out.print(String.format("%d(%d,%d)",blockIdx,row,col));
            chars[idx] += val<<((c)%8);
            if(++c>=8){
                idx=0;
                c=0;
            }
        }
        return new ChessCipherKey(Arrays.toString(chars));
    }
}
