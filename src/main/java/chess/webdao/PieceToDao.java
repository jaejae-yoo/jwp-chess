package chess.webdao;

import chess.domain.piece.*;

import java.util.HashMap;
import java.util.Map;

public class PieceToDao {
    private static final Map<Piece, String> pieceFormat = new HashMap<>();

    static {
        pieceFormat.put(new Pawn(1), "Pawn");
        pieceFormat.put(new Pawn(-1), "Pawn");
        pieceFormat.put(new Rook(), "Rook");
        pieceFormat.put(new Knight(), "Knight");
        pieceFormat.put(new Bishop(), "Bishop");
        pieceFormat.put(new Queen(), "Queen");
        pieceFormat.put(new King(), "King");
    }

    private PieceToDao() {
    }

    public static String convert(final Piece piece) {
        return pieceFormat.get(piece);
    }
}
