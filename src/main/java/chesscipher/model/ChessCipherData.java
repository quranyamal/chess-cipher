package main.java.chesscipher.model;

import main.java.chesscipher.model.ChessBoard;

public class ChessCipherData {
    public static final int BOARD_SIZE = 8;
    public static final int BLOCK_SIZE = BOARD_SIZE*BOARD_SIZE;
    private String dataStr;
    
    public int numBlock;
    private ChessBoard blocks[];    // chess board = data block
    
    public ChessCipherData(String data) {
        dataStr = data;
        numBlock = (data.length()/8) + (data.length()%8==0 ? 0 : 1);
        blocks = new ChessBoard[numBlock];
        initBlocks();
    }
    
    private void initBlocks() {
        for (int i=0; i<numBlock-1; i++) {
            String subStr = dataStr.substring(i*8, (i+1)*8);
            System.out.println("substring:" + subStr);
            blocks[i] = new ChessBoard(subStr);
        }
        initLastBlock();
    }
    
    private void initLastBlock() {
        String subStr = dataStr.substring((numBlock-1)*BOARD_SIZE);
        
        if (subStr.length()==8) {
            System.out.println("LAST BLOCK IS NOT PADDED");
        } else {
            System.out.println("LAST BLOCK IS PADDED WITH '%'");
            for (int i=subStr.length(); i<BOARD_SIZE; i++) {
                subStr = subStr + "%";
            }
        }
        blocks[numBlock-1] = new ChessBoard(subStr);
    }
    
    public String getDataStr() {
        return dataStr;
    }
    
    public void setDataStr(String str) {
        dataStr = str;
    }
    
    public ChessBoard getBlock(int i) {
        return blocks[i];
    }
    
    @Override
    public String toString() {
        return getDataStr();
    }
}
