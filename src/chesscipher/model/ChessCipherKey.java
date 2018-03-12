package chesscipher.model;

public class ChessCipherKey {
    // nanti disesuaikan
    static final int MAX_ROUND = 8;
    static final int KEY_LENGTH = 64;
    static final int SUBKEY_LENGTH = 1;
    
    String key;
    int roundState;
    
    public ChessCipherKey(String key_) {
        key = getAlignedKey(key_);
        roundState = 0;
    }
    
    private String getAlignedKey(String key_) {
        while (key_.length()<KEY_LENGTH) {
                key_ = key_+key_;
        }
        return key_.substring(0, KEY_LENGTH);
    }
    
    public String getSubKey() {
        if (roundState==MAX_ROUND) resetRoundState();
        return key.substring(roundState++*SUBKEY_LENGTH, (roundState)*SUBKEY_LENGTH);
    }
    
    public void resetRoundState() {
        roundState=0;
    }
    
}
