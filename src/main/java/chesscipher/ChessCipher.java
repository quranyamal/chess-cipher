package chesscipher;

import chesscipher.controller.ChessCipherDecryptor;
import chesscipher.controller.ChessCipherEncryptor;
import chesscipher.model.ChessCipherData;
import chesscipher.model.ChessCipherKey;

public class ChessCipher {
    private ChessCipherData data;
    private ChessCipherKey key;
    
    public void setData(String dataStr) {
        data = new ChessCipherData(dataStr);
    }
    
    public void setKey(String keyStr) {
        key = new ChessCipherKey(keyStr);
    }
    
    public ChessCipherData getData() {
        return data;
    }
    
    public ChessCipherKey getKey() {
        return key;
    }
    
    public void encrypt() {
        ChessCipherEncryptor.encrypt(data, key);
    }
    
    public void decrypt() {
        ChessCipherDecryptor.decrypt(data, key);
    }
    
}
