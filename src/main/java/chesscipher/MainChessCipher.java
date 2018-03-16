package chesscipher;

import chesscipher.model.ChessCipherData;
import chesscipher.model.ChessCipherKey;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MainChessCipher {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        
        encryptDecryptTest1();
        
    }

    static void encryptDecryptTest1() {
        ChessCipher cipher = new ChessCipher();
        String plainText = "I like him not, nor stands it safe with us To let his madness range. Therefore prepare you; I your commission will forthwith dispatch, And he to England shall along with you: The terms of our estate: i.e., my position as king. ...more  The terms of our estate may not endure Hazard so near's as doth hourly grow / Out of his brows: i.e., dangers that threaten me so nearly, which grow every hour from his (mad) moods. ...more   Hazard so near's as doth hourly grow Out of his brows. ";
        cipher.setData(plainText);
        cipher.setKey("KURAKURA");
        
        cipher.encrypt();
        String cipherText = cipher.getData().toString();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("E:\\kuliah\\if42\\kriptografi\\tts\\cipher.txt"))) {

            String content = cipherText;

            bw.write(content);

            // no need to close it.
            //bw.close();

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        }

        cipher.setKey("KURAKURA");
//        cipher.getData().blocks[0].matrix[1][3] = !cipher.getData().blocks[0].matrix[1][3];
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
