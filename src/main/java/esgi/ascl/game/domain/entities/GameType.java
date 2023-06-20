package esgi.ascl.game.domain.entities;

public enum GameType {
    POOL,
    THIRTY_SECOND_FINAL,
    SIXTEENTH_FINAL,
    QUARTER_FINAL,
    SEMI_FINAL,
    FINAL;

    GameType() {}

    public static GameType correctType(int nbTeams){
        return switch (nbTeams) {
            case 2 -> FINAL;
            case 4 -> SEMI_FINAL;
            case 8 -> QUARTER_FINAL;
            case 16 -> SIXTEENTH_FINAL;
            case 32 -> THIRTY_SECOND_FINAL;
            default -> POOL;
        };
    }
}
