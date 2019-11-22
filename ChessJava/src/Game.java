public class Game {
    public static void main(String args[]){
        Board board = new Board();

        board.initializeBoard();
        board.printBoard();

        Piece [][] pieces = board.getBoard();
        Piece piece =pieces[5][4];

        System.out.println("-----------------------------------");

        board.movePiece(piece,4,5);
        board.printBoard();


    }
}
