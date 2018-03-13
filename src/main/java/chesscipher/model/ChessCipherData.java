package chesscipher.model;

public class ChessCipherData {
    private String dataStr;
    
    public int numBlock;
    private CCBoard blocks[];    // chess board = data block
    
    public ChessCipherData(String data) {
        dataStr = data;
        numBlock = 99; // todo
        blocks = new CCBoard[numBlock];
        initBlocks();
    }
    
    private void initBlocks() {
        for (int i=0; i<numBlock-1; i++) {
            // todo
        }
        initLastBlock();
    }
    
    private void initLastBlock() {
        // todo
    }
    
    public String getDataStr() {
        return dataStr;
    }
    
    public void setDataStr(String str) {
        dataStr = str;
    }
    
    public CCBoard getBlock(int i) {
        return blocks[i];
    }
    
    @Override
    public String toString() {
        return getDataStr();
    }
}
