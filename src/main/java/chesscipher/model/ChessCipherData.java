package chesscipher.model;

import java.util.Arrays;

public class ChessCipherData {
    public static final int BOARD_SIZE = 8;
    public static final int BLOCK_SIZE = BOARD_SIZE*BOARD_SIZE;
    private String rawDataStr;
    private String tmpData; // isinya concat blok2 data yang sedang dienkripsi/dekripsi
    private byte bytes[];

    public int numBlock;
    public ChessBoard blocks[];    // chess board = data block
    
    public ChessCipherData(String data) {
        rawDataStr = data;
        numBlock = (data.length()/8) + (data.length()%8==0 ? 0 : 1);
        blocks = new ChessBoard[numBlock];
        bytes = new byte[8];
        tmpData = "";
        initBlocks(data);
    }

    public ChessCipherData(byte[] data) {
        bytes = data;
        numBlock = (data.length/8) + (data.length%8==0 ? 0 : 1);
        blocks = new ChessBoard[numBlock];
        bytes = new byte[8];
        initBlocks(data);
    }

    private void initBytes(byte[] bytes_) {
        for (int i=0; i<8; i++) {
            bytes[i] = bytes_[i];
        }
    }

    private void initBlocksFromBytes(byte[] bytes) {
        for (int i=0; i<numBlock; i++) {
            byte[] sub8Bytes = Arrays.copyOfRange(bytes,i*BOARD_SIZE, (i+1)*BOARD_SIZE);
            blocks[i] = new ChessBoard(sub8Bytes);

        }
        //initLastBlockFromBytes();
    }

    private void initBlocks(String data) {
        for (int i=0; i<numBlock-1; i++) {
            String subStr = rawDataStr.substring(i*8, (i+1)*8);
            System.out.println("substring:" + subStr);
            blocks[i] = new ChessBoard(subStr);
            
            tmpData = tmpData + blocks[i].toString();
            
        }
        initLastBlock(data);
    }

    private void initBlocks(byte[] data) {
        for (int i=0; i<numBlock-1; i++) {
            byte[] subData = Arrays.copyOfRange(data,i*8, (i+1)*8);
            System.out.println("subdata:" + subData.toString());
            blocks[i] = new ChessBoard(subData);
        }
        byte[] subData = Arrays.copyOfRange(data,(numBlock-1)*8, numBlock*8);
        initLastBlock(subData);
    }

    private void initLastBlock(String data) {
        String subStr = rawDataStr.substring((numBlock-1)*BOARD_SIZE);
        
        if (subStr.length()==8) {
            System.out.println("LAST BLOCK IS NOT PADDED");
        } else {
            System.out.println("LAST BLOCK IS PADDED");
            for (int i=subStr.length(); i<BOARD_SIZE; i++) {
                subStr = subStr + (char)0;
            }
        }
        blocks[numBlock-1] = new ChessBoard(subStr);
        tmpData += blocks[numBlock-1];
    }

    private void initLastBlock(byte[] data) {
        if (data.length==8) {
            System.out.println("LAST BLOCK IS NOT PADDED");
        } else {
            System.out.println("LAST BLOCK IS PADDED");
            for (int i=data.length; i<BOARD_SIZE; i++) {
                data[i] = 0;
            }
        }
        blocks[numBlock-1] = new ChessBoard(data);
    }

    public String getDataStr() {
        return rawDataStr;
    }
    
    public void setDataStr(String str) {
        rawDataStr = str;
    }


    public ChessBoard getBlock(int i) {
        return blocks[i];
    }

    @Override
    public String toString() {
        String strFromBlock = "";
        for (int i=0; i<numBlock; i++) {
            strFromBlock += blocks[i].toString();
        }
        return strFromBlock;
    }

    public byte[] getBytesData() {
        if (numBlock==0) {
            return null;
        } else {
            int numBytes = numBlock*BOARD_SIZE;
            byte bytes[] = new byte[numBytes];

            for (int i=0; i<numBlock; i++) {
                for (int j=0; j<BOARD_SIZE; j++) {
                    bytes[i*BOARD_SIZE + j] = blocks[i].getByte(j);
                }
            }
            return bytes;
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ChessCipherData clone = new ChessCipherData(this.toString());
        for(int i=0;i<clone.blocks.length;i++){
            ChessBoard block = clone.blocks[i];
            for(int row=0;row<block.matrix.length;row++){
                System.arraycopy(this.blocks[i].matrix[row], 0, block.matrix[row], 0, block.matrix[row].length);
            }
        }
        return clone;
    }

    public String getTmpData() {
        return tmpData;
    }
}
