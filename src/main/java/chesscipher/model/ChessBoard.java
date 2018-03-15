package chesscipher.model;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class ChessBoard {
    private static final int SIZE = 8;
    private boolean[][] matrix;
    
    /* precondition: str length==SIZE */
    public ChessBoard(String str) {
        if (str.length()!=SIZE) System.out.println("STRLEN!=SIZE HARUSNYA EROR SIH");
        matrix = new boolean[SIZE][SIZE];
        setMatrix(str);
    }

    private void setMatrix(String str) {
        byte[] bytes = str.getBytes();
        StringBuilder binary = new StringBuilder();

        for (int row=0; row<SIZE; row++) {
           int val = bytes[row];
           for (int col = 0; col < 8; col++)
           {
            matrix[row][col] = (val & 128) != 0;
            binary.append((val & 128) == 0 ? 0 : 1);
            val <<= 1;
           }
           binary.append(' ');
        }
        System.out.println("'" + str + "' to binary: " + binary);
    }
    
    byte encodeBools(boolean[] bools){
      byte val = 0;
      for (boolean b: bools) {
        val <<= 1;
        if (b) val |= 1;
      }
      return val;
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
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
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
