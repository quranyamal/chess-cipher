package chesscipher.controller;

import chesscipher.model.ChessCipherData;
import chesscipher.model.ChessCipherKey;

public class ChessCipherDecryptor {

    public static void decrypt(ChessCipherData data, ChessCipherKey key) {
        key.resetRoundState();        
        
        // todo. contoh:
        shiftBytesLeft(data, key);
        // (seharusnya per block)
    }
 
    public static void shiftBytesLeft(ChessCipherData data, ChessCipherKey key) {
        char dataChars[] = data.getDataStr().toCharArray();
        for (int i=0; i<dataChars.length; i++) {
            int numShift = (int) key.getSubKey().toCharArray()[0] - 64;
            dataChars[i] = (char) ((int)dataChars[i] - numShift);
        }
        data.setDataStr(String.valueOf(dataChars));
    }
    
}
