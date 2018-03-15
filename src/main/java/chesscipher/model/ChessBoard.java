package chesscipher.model;

public class ChessBoard {
    public static final int SIZE = 8;
    public boolean[][] matrix; // !=0 -> true, 0->false

    /* precondition: str length==SIZE */
    private boolean[][] matrix;

    public ChessBoard(boolean[][] mtr) {
        matrix = mtr;
    }

    public ChessBoard(String str) {
        if (str.length()!=SIZE) System.out.println("STRLEN!=SIZE HARUSNYA EROR SIH");
        matrix = new boolean[SIZE][SIZE];
        setMatrix(str);
    }

    private void setMatrix(String str) {
        byte[] bytes = str.getBytes();
        StringBuilder binary = new StringBuilder();

        for (int row=0; row<SIZE; row++) {
           setByte(row, bytes[row]);
           binary.append(' ');
        }
        System.out.println("'" + str + "' to binary: " + binary);
    }

    public boolean getBoolAt(int row, int col) {
        return matrix[row][col];
    }

    public void setBoolAt(int row, int col, boolean b) {
        matrix[row][col] = b;
    }

    byte encodeBools(boolean[] bools){
      byte val = 0;
      for (boolean b: bools) {
        val <<= 1;
        if (b) val |= 1;
      }
      return val;
    }

    public byte getByte(int idx) {
        return encodeBools(matrix[idx]);
    }

    public void setByte(int idx, byte bytes) {
        int val = bytes;
        for (int col = 0; col < 8; col++) {
            matrix[idx][col] = (val & 128) != 0;
            val <<= 1;
        }
    }

    @Override
    public String toString() {
        char[] chars = new char[8];
        for (int i=0; i<SIZE; i++) {
            chars[i] = (char) encodeBools(matrix[i]);
        }
        return String.valueOf(chars);
    }
    
    public void printBoard() {
        System.out.println("Board:");
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                System.out.print(matrix[i][j]?1 + " " : 0 + " ");
                System.out.print(((matrix[i][j])?1:0) + " ");
            }
            System.out.println();
        }
    }

    public void printBytes() {
        System.out.println("Bytes: ");
        for (int i=0; i<SIZE; i++) {
            System.out.println(getByte(i) + " ");
        }
    }

    public static void main(String args[]) {
        ChessBoard board1 = new ChessBoard("ABCDEFGH");
        ChessBoard board2 = new ChessBoard("AABBCCDD");
        
        System.out.println("board 1");
        board1.printBoard();

        System.out.println("board 2");
        board2.printBoard();
    }
}
