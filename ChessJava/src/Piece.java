enum State {EMPTY,DARK,LIGHT};

public class Piece {

    private int position_x;
    private int position_y;
    private boolean isKing;

    private final State state;

    public Piece(State state, int x, int y) {
        this.state = state;
        position_x = x;
        position_y = y;
        isKing=false;
    }

    public int getPosition_y() {
        return position_y;
    }

    public void setPosition_y(int position_y) {
        this.position_y = position_y;
    }

    public int getPosition_x() {
        return position_x;
    }

    public void setPosition_x(int position_x) {
        this.position_x = position_x;
    }

    public State getState() {
        return state;
    }


    public boolean isKing() {
        return isKing;
    }

    public void makeKing() {
        isKing = !isKing;
    }
}
