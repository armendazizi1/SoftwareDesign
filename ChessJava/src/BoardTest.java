import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    Board board = new Board();
    Piece [][] pieces = board.getBoard();

    @Before
   public void init() {
        board.initializeBoard();
        board.printBoard();
    }

    @Test
    public  void testBoard() {
        // assert the initial configuration has the right pieces
        // at the right places.
       assertEquals(pieces[0][1].getState(),State.LIGHT);
       assertEquals(pieces[0][2].getState(),State.EMPTY);
       assertEquals(pieces[0][3].getState(),State.LIGHT);

       assertEquals(pieces[3][1].getState(),State.EMPTY);
       assertEquals(pieces[3][2].getState(),State.EMPTY);
       assertEquals(pieces[3][3].getState(),State.EMPTY);

       assertEquals(pieces[5][0].getState(),State.DARK);
       assertEquals(pieces[5][1].getState(),State.EMPTY);
       assertEquals(pieces[5][2].getState(),State.DARK);


    }


    @Test
    public  void movePiece() {

        Piece piece =pieces[0][1];
        board.movePiece(piece,1,1);
        board.printBoard();

        System.out.println("-----------------------------------");

        String position = piece.getPosition_x() + "--" + piece.getPosition_y();
        assertEquals(position,"1--1");

    }

    @Test
    public void movePieceAndCapture() {

        // Set board in a configuration where a capture is possible:
        Piece pieceBlack =pieces[2][3];
        board.movePiece(pieceBlack,3,3);
        Piece pieceWhite =pieces[5][2];
        board.movePiece(pieceWhite,4,2);
        board.printBoard();

        // perform a hard-coded capture
        board.movePiece(pieceWhite,2,4);
        Piece empty = new Piece(State.EMPTY,3,3);
        pieces[3][3]=empty;
        board.printBoard();

        assertEquals(pieces[3][3],empty);

    }


    @Test
    public void invalidMove() {
        Piece pieceBlack =pieces[2][3];
        // This will fail since the validateMove() is not implemented, and all the moves are
        // valid.
        assertEquals(board.movePiece(pieceBlack,2,4),false);

    }

    @Test
    public void kingMove() {
        Piece pieceBlack =pieces[2][3];
        board.movePiece(pieceBlack,3,4);
        pieceBlack.makeKing();

        // the king is able to move backwards
        board.movePiece(pieceBlack,2,3);

        assertEquals(pieceBlack.getPosition_x(),2);
        assertEquals(pieceBlack.getPosition_y(),3);
    }



}