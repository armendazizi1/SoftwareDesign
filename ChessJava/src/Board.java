public class Board {
    private static final int rows = 8;
    private static final int columns =8;

    private Piece [][] board;

    public Board(){
        board = new Piece[rows][columns];
    }

    public Piece [][] getBoard(){
        return board;
    }

    // Initializes the board in the default initial configuration.
    public void initializeBoard(){
        for(int i = 0; i < rows; i++){
            for(int j= 0; j < columns; j++){
               if (i < 3) {
                   if (i == 1){
                       if (j % 2 == 0){
                           board[i][j]= new Piece(State.LIGHT,i,j);
                       }
                       else board[i][j]=new Piece(State.EMPTY,i,j);
                   }
                   else {
                       if (j % 2 == 0){
                           board[i][j]= new Piece(State.EMPTY,i,j);
                       }
                       else board[i][j]= new Piece(State.LIGHT,i,j);
                   }
                    }
               else if (i > 4) {
                   if (i == 6){
                       if (j % 2 == 0){
                           board[i][j]=new Piece(State.EMPTY,i,j);
                       }
                       else board[i][j]=new Piece(State.DARK,i,j);
                   }
                   else {
                       if (j % 2 == 0){
                           board[i][j]=new Piece(State.DARK,i,j);
                       }
                       else board[i][j]=new Piece(State.EMPTY,i,j);
                   }
               }
               else board[i][j]=new Piece(State.EMPTY,i,j);
            }
        }
    }

    public void printBoard(){
        System.out.println( "   0   1   2   3   4   5   6   7");
        System.out.println(" _________________________________");
        for(int i = 0; i < rows; i++){
            System.out.print(" |");
            for(int j= 0; j < columns; j++){
                Piece piece = board[i][j];
                switch (piece.getState()){
                    case EMPTY:
                        if ((j != 0)) {
                            System.out.print("  | ");
                        } else {
                            System.out.print("   | ");
                        }
                        break;
                    case LIGHT:
                        if ((j != 0)) {
                            System.out.print("◎ | ");
                        } else {
                            System.out.print(" ◎ | ");
                        }
                        break;
                    case DARK:
                        if ((j != 0)) {
                            System.out.print("◉ | ");
                        } else {
                            System.out.print(" ◉ | ");
                        }
                        break;
                }
            }
            System.out.println("\n |___|___|___|___|___|___|___|___|");
        }


        System.out.println( "   0   1   2   3   4   5   6   7");
    }

    // missing 'validateMove()' method, that checks whether a move is valid, and whether during that
    // move we have made a capture.
    public boolean movePiece(Piece p, int new_x, int new_y){

       // mark the old position of the piece that is moving as EMPTY
       board[p.getPosition_x()][p.getPosition_y()] = new Piece(State.EMPTY,p.getPosition_x(),p.getPosition_y());

       // place the piece that we want to move to the new position.
       board[new_x][new_y]= p;
       p.setPosition_x(new_x);
       p.setPosition_y(new_y);

       // the return statement would depend on the result of validateMove(), which will also be a boolean.
       return true;
    }

}
