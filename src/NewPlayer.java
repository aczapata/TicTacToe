import java.util.Random;

import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.api.IPlayer;
import de.ovgu.dke.teaching.ml.tictactoe.api.IllegalMoveException;
import de.ovgu.dke.teaching.ml.tictactoe.game.Move;

/**
 * Some comments ...
 * 
 * @author Obed Rios, Jose Rodriguez, Andrea Zapata
 */
public class NewPlayer implements IPlayer {

	public String getName() {
		// TODO Auto-generated method stub
		return "Andrea";
	}

	public int[] randMove() {
		Random r = new Random();
		int x = r.nextInt(5);
		int y = r.nextInt(5);
		int z = r.nextInt(5);
		return new int[] { x, y, z };
	}

	public int[] smartMove(IBoard board) {
		
		Random r = new Random();
		int x = r.nextInt(5);
		int y = r.nextInt(5);
		int z = r.nextInt(5);
		return new int[] { x, y, z };
	}

	public int[] makeMove(IBoard board) {
		// TODO Auto-generated method stub

		// create a clone of the board that can be modified
		IBoard copy = board.clone();
		Move newMove= SmartBoard.smartMove(board, this);
		// do a move using the cloned board
		
			try {
				copy.makeMove(newMove);
			} catch (IllegalMoveException e) {
				// move was not allowed
				// e.printStackTrace();
			}
		// return your final decision for your next move
		return newMove.getPosition();
	}

	public void onMatchEnds(IBoard board) {
		return;
	}

}
