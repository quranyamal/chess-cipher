package main.java.chesscipher.controller;

import main.java.chesscipher.model.ChessCipherData;
import main.java.chesscipher.model.ChessCipherKey;

public class ChessCipherEncryptor {
    
    public static void encrypt(ChessCipherData data, ChessCipherKey key) {
        key.resetRoundState();
        
        // todo. contoh:
        shiftBytesRight(data, key);
        // (seharusnya per block)
    }
    
    public static void shiftBytesRight(ChessCipherData data, ChessCipherKey key) {
        char dataChars[] = data.getDataStr().toCharArray();
        for (int i=0; i<dataChars.length; i++) {
            int numShift = (int) key.getSubKey().toCharArray()[0] - 64;
            dataChars[i] = (char) ((int)dataChars[i] + numShift);
        }
        data.setDataStr(String.valueOf(dataChars));
    }
    
}
