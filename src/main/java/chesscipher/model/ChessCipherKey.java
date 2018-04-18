package chesscipher.model;

import com.nullpointergames.boardgames.PieceColor;
import com.nullpointergames.boardgames.chess.CCPieceType;

import java.util.Arrays;
import java.util.Random;

public class ChessCipherKey {
    // nanti disesuaikan
    static final int MAX_ROUND = 3;
    static final int KEY_LENGTH = 24;
    static final int SUBKEY_LENGTH = 8;
    
    String key;
    int roundState;
    Random rng;
    boolean isWhiteTurn = false;
    
    public ChessCipherKey(String key_) {
        key = getAlignedKey(key_);
        setRNG(key);
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

    public void setRNG(String key){
        long seed = 0l;
        byte[] keyArray = key.getBytes();
        for(byte b:keyArray){
            seed+=b;
        }
        rng = new Random(seed);
    }

    public CCPieceType nextPiece(){
        isWhiteTurn = !isWhiteTurn;
        return CCPieceType.getCCPieceType((isWhiteTurn)? PieceColor.WHITE:PieceColor.BLACK,rng.nextInt(16));
    }

    public int nextDest(){
        return rng.nextInt(64);
    }
    
    public void resetRoundState() {
        roundState=0;
    }


    @Override
    public String toString() {
        return key;
    }
}
