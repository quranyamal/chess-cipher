package chesscipher;

import chesscipher.model.ChessCipherData;
import chesscipher.model.ChessCipherKey;

public class MainChessCipher {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        
        encryptDecryptTest1();
        
    }

    static void encryptDecryptTest1() {
        ChessCipher cipher = new ChessCipher();
        String plainText = "COBA DATANYA STRING DULU!!";
        //String plainText = "ABCD0000";
        cipher.setData(plainText);
        cipher.setKey("AABC");

        cipher.getData().getBlock(0).printBoard();
        cipher.getData().getBlock(0).printBytes();

        cipher.encrypt();
        String cipherText = cipher.getData().toString();

        cipher.setKey("AABC");
        cipher.getData().getBlock(0).printBoard();
        cipher.getData().getBlock(0).printBytes();

        cipher.decrypt();
        String decryptedText = cipher.getData().toString();

        System.out.println();
        System.out.println("plain : " + plainText);
        System.out.println("cipher: "+ cipherText);
        System.out.println("revrsd: "+ decryptedText);
        
        System.out.println();
        System.out.println("tmpData:" + cipher.getData().getTmpData());
    }
    
}
