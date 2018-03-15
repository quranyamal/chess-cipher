package chesscipher.controller.util;

import com.nullpointergames.boardgames.Position;

public class SwapEntry {
    public Position from;
    public Position to;

    public SwapEntry(Position from, Position to) {
        this.from = from;
        this.to = to;
    }
}
