package chess.webdto;

public class ChessGameTableDto {
    private final String current_turn_team;
    private final boolean is_playing;

    public ChessGameTableDto(String current_turn_team, boolean is_playing) {
        this.current_turn_team = current_turn_team;
        this.is_playing = is_playing;
    }

    public String getCurrentTurnTeam() {
        return current_turn_team;
    }

    public boolean getIsPlaying() {
        return is_playing;
    }
}
