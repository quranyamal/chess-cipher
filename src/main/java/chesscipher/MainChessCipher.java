package chesscipher;

import chesscipher.model.ChessCipherData;
import chesscipher.model.ChessCipherKey;

public class MainChessCipher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String plainText = "COBA DATANYA STRING DULU";
        ChessCipher chessCipher = new ChessCipher();
        
        chessCipher.setData(plainText);
        chessCipher.setKey("AABC");
        
        chessCipher.encrypt();
        String cipherText = chessCipher.getData().toString();
        
        chessCipher.decrypt();
        String decryptedText = chessCipher.getData().toString();
        
        System.out.println("plain : " + plainText);
        System.out.println("cipher: "+ cipherText);
        System.out.println("revrsd: "+ decryptedText);
        
    }
    
}
