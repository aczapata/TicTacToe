import java.util.List;
import java.util.Random;

import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.api.IMove;
import de.ovgu.dke.teaching.ml.tictactoe.api.IPlayer;
import de.ovgu.dke.teaching.ml.tictactoe.api.IllegalMoveException;
import de.ovgu.dke.teaching.ml.tictactoe.game.Move;

public class SmartBoard {

	int posX, posO;
	static int n = 5;

	public void updateValues(IBoard board) {
		List<IMove> moves = board.getMoveHistory();

	}

	public static Move smartMove(IBoard board, IPlayer player) {
		int x = 0, y = 0, z = 0;
		if (board.getMoveHistory().size() > 0) {
			IMove lastMoveOP = board.getMoveHistory().get(board.getMoveHistory().size() - 1);
			int x1 = check(lastMoveOP.getPosition()[0], lastMoveOP.getPosition()[1], 0, player, board);
			int x2 = check(lastMoveOP.getPosition()[0], lastMoveOP.getPosition()[2], 1, player, board);
			int x3 = check(lastMoveOP.getPosition()[1], lastMoveOP.getPosition()[2], 2, player, board);

			System.out.println("Oponent: X1= " + x1 + " X2= " + x2 + " X3= " + x3);
			
			IMove lastMoveME = board.getMoveHistory().get(board.getMoveHistory().size() - 2);
			int x4 = check(lastMoveME.getPosition()[0], lastMoveME.getPosition()[1], 0, player, board);
			int x5 = check(lastMoveME.getPosition()[0], lastMoveME.getPosition()[2], 1, player, board);
			int x6 = check(lastMoveME.getPosition()[1], lastMoveME.getPosition()[2], 2, player, board);
			
			System.out.println("Me: X1= " + x4 + " X2= " + x5 + " X3= " + x6);
					
			Random r = new Random();
			do {
				if (x4>0){
					x= lastMoveME.getPosition()[0];
					y = r.nextInt(5);
					z = r.nextInt(5);
				}else if (x5 >0){
					y= lastMoveME.getPosition()[0];
					x = r.nextInt(5);
					z = r.nextInt(5);
				}else if (x6 >0){
					z= lastMoveME.getPosition()[0];
					x = r.nextInt(5);
					y = r.nextInt(5);
				}else{
				x = r.nextInt(5);
				y = r.nextInt(5);
				z = r.nextInt(5);}
			} while (board.getFieldValue(new int[] { x, y, z }) != null);
			return new Move(player, new int[] { x, y, z });
		}else{
		
		
			
		Random r = new Random();
		do {
			x = r.nextInt(5);
			y = r.nextInt(5);
			z = r.nextInt(5);
		} while (board.getFieldValue(new int[] { x, y, z }) != null);}
		return new Move(player, new int[] { x, y, z });
	}

	public static int check(int i, int j, int dir, IPlayer player1, IBoard board) {
		int checkP = 0;
		switch (dir) {
		case 0:
			// x static, y static,
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { i, j, k }).equals(player1))
						checkP++;
				} catch (NullPointerException e) {
				}
			}

		case 1:
			// x static, z static,
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { i, k, j }).equals(player1))
						checkP++;
				} catch (NullPointerException e) {
				}
			}
		case 2:
			// y static, z static,
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { k, i, j }).equals(player1))
						checkP++;
				} catch (NullPointerException e) {
				}
			}
		}

		return checkP;
	}

}
