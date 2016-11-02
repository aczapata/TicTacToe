import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

	int n = 5;
	double[] w = new double[5];
	int[] x = new int[5];

	public NewPlayer() throws IOException {
		x[0] = 1;
		x[1] = 109;
		x[2] = 109;
		
		try {
			FileReader fr = new FileReader(new File("wvalues.txt"));
			BufferedReader br = new BufferedReader(fr);
			int t=0;
			for (String s : br.readLine().split(";")) {
				w[t]= Double.parseDouble(s);
				t++;
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			FileWriter fw = new FileWriter(new File("wvalues.txt"));
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("0.0;100.0;-100.0;0;0;");
			bw.close();
			fw.close();
			
			FileReader fr = new FileReader(new File("wvalues.txt"));
			BufferedReader br = new BufferedReader(fr);
			int t=0;
			for (String s : br.readLine().split(";")) {
				w[t]= Double.parseDouble(s);
				t++;
			}
			br.close();
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getName() {
		return "Andrea";
	}

	public Move smartMove(IBoard board) {

		double maxV = -100;
		Move maxM = null;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					if (board.getFieldValue(new int[] { i, j, k }) == null) {
						IBoard copy = board.clone();
						Move newMove = new Move(this, new int[] { i, j, k });

						try {
							copy.makeMove(newMove);
							int[] xt = x.clone();
							updateXvalues(newMove.getPosition(), xt, copy, true);
							double vt1 = calculateVFunction(xt);
							if (vt1 > maxV) {
								maxV = vt1;
								maxM = newMove;
							}

						} catch (IllegalMoveException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		System.out.println("Vt1= "+maxV);
		return maxM;
	}

	public int[] check(int i, int j, int dir, IBoard board) {
		int checkP = 0;
		int checkO = 0;
		switch (dir) {
		case 0:
			// x static, y static,
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { i, j, k }).equals(this))
						checkP++;
					else
						checkO++;
				} catch (NullPointerException e) {
				}
			}
			break;
		case 1:
			// x static, z static,
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { i, k, j }).equals(this))
						checkP++;
					else
						checkO++;
				} catch (NullPointerException e) {
				}
			}
			break;
		case 2:
			// y static, z static,
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { k, i, j }).equals(this))
						checkP++;
					else
						checkO++;
				} catch (NullPointerException e) {
				}
			}
			break;
		}

		return new int[] { checkP, checkO };
	}

	public int[] check(int i, int dir, IBoard board) {
		int checkP = 0;
		int checkO = 0;
		switch (dir) {
		case 0:
			// x=y , z static,
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { i, i, k }).equals(this))
						checkP++;
					else
						checkO++;
				} catch (NullPointerException e) {
				}
			}
			break;
		case 1:
			// x=z, y static,
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { i, k, i }).equals(this))
						checkP++;
					else
						checkO++;
				} catch (NullPointerException e) {
				}
			}
			break;
		case 2:
			// y=z, x static,
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { k, i, i }).equals(this))
						checkP++;
					else
						checkO++;
				} catch (NullPointerException e) {
				}
			}
			break;
		case 3:
			// x=y , z static,
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { i, n - (i + 1), k }).equals(this))
						checkP++;
					else
						checkO++;
				} catch (NullPointerException e) {
				}
			}
			break;
		case 4:
			// x=z, y static,
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { i, k, n - (i + 1) }).equals(this))
						checkP++;
					else
						checkO++;
				} catch (NullPointerException e) {
				}
			}
			break;
		case 5:
			// y=z, x static,
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { k, i, n - (i + 1) }).equals(this))
						checkP++;
					else
						checkO++;
				} catch (NullPointerException e) {
				}
			}
			break;
		}

		return new int[] { checkP, checkO };
	}

	public int[] check(int dir, IBoard board) {
		int checkP = 0;
		int checkO = 0;
		switch (dir) {
		case 0:
			// x=y=z
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { k, k, k }).equals(this))
						checkP++;
					else
						checkO++;
				} catch (NullPointerException e) {
				}
			}
			break;
		case 1:
			// x=y, z=n-(y+1),
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { k, k, n - (k + 1) }).equals(this))
						checkP++;
					else
						checkO++;
				} catch (NullPointerException e) {
				}
			}
			break;
		case 2:
			// x=z, y=n-(z+1),
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { k, n - (k + 1), k }).equals(this))
						checkP++;
					else
						checkO++;
				} catch (NullPointerException e) {
				}
			}
			break;
		case 3:
			// y=z, x=n-(z+1),
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { n - (k + 1), k, k }).equals(this))
						checkP++;
					else
						checkO++;
				} catch (NullPointerException e) {
				}
			}
			break;
		}

		return new int[] { checkP, checkO };
	}

	public Move randMove(IBoard board) {

		Random r = new Random();
		int x1, y1, z1;
		do {
			x1 = r.nextInt(5);
			y1 = r.nextInt(5);
			z1 = r.nextInt(5);
		} while (board.getFieldValue(new int[] { x1, y1, z1 }) != null);
		return new Move(this, new int[] { x1, y1, z1 });
	}

	public void updateXvalues(int[] lastMove, int x[], IBoard board, boolean myMove) {

		countY(x, check(lastMove[0], lastMove[1], 0, board), myMove);
		countY(x, check(lastMove[0], lastMove[2], 1, board), myMove);
		countY(x, check(lastMove[1], lastMove[2], 2, board), myMove);

		if (lastMove[0] == lastMove[1]) {
			countY(x, check(lastMove[0], 0, board), myMove);
		}
		if (lastMove[0] == lastMove[2]) {
			countY(x, check(lastMove[0], 1, board), myMove);
		}
		if (lastMove[1] == lastMove[2]) {
			countY(x, check(lastMove[1], 2, board), myMove);
		}
		if (lastMove[0] == (n - (lastMove[1] + 1))) {
			countY(x, check(lastMove[0], 3, board), myMove);
		}
		if (lastMove[0] == (n - (lastMove[2] + 1))) {
			countY(x, check(lastMove[0], 4, board), myMove);
		}
		if (lastMove[1] == (n - (lastMove[2] + 1))) {
			countY(x, check(lastMove[1], 5, board), myMove);
		}

		if (lastMove[0] == lastMove[1] && lastMove[0] == lastMove[2]) {
			countY(x, check(0, board), myMove);
		} else if (lastMove[0] == lastMove[1] && lastMove[2] == (n - (lastMove[1] + 1))) {
			countY(x, check(1, board), myMove);
		} else if (lastMove[0] == lastMove[2] && lastMove[1] == (n - (lastMove[2] + 1))) {
			countY(x, check(2, board), myMove);
		} else if (lastMove[1] == lastMove[2] && lastMove[0] == (n - (lastMove[2] + 1))) {
			countY(x, check(3, board), myMove);
		}
	}

	public int[] makeMove(IBoard board) {
		// TODO Auto-generated method stub

		// create a clone of the board that can be modified
		IBoard copy = board.clone();

		if (copy.getMoveHistory().size() > 0) {
			int[] lastMove = copy.getMoveHistory().get(copy.getMoveHistory().size() - 1).getPosition();
			updateXvalues(lastMove, x, board, false);
			for (int i = 0; i < x.length; i++) {

				System.out.println("x[" + i + "]=" + x[i]);

			}
			System.out.println("__________________");

		}
		Move newMove = smartMove(board);
		// do a move using the cloned board

		try {
			copy.makeMove(newMove);
		} catch (IllegalMoveException e) {
			// move was not allowed
			// e.printStackTrace();
		}

		updateXvalues(newMove.getPosition(), x, copy, true);
		for (int i = 0; i < x.length; i++) {

			System.out.println("x[" + i + "]=" + x[i]);

		}
		// return your final decision for your next move
		return newMove.getPosition();
	}

	public void countX(int[] c) {
		if (c[0] == 0 && c[1] == 0) {
			x[1]++;
			x[2]++;
		} else if (c[0] == 0) {
			x[2]++;
		} else if (c[1] == 0) {
			x[1]++;
		}
	}

	public void countY(int[] xt, int[] c, boolean myMove) {
		if (myMove && c[0] == 1 && c[1] >= 0) {
			xt[2]--;
		} else if (!myMove && c[1] == 1 && c[0] >= 0) {
			xt[1]--;
		}
	}
	/*
	 * public void updateX(IBoard board) { x = new int[5]; for (int i = 0; i <
	 * n; i++) { for (int j = 0; j < n; j++) { countX(check(i, j, 0, board));
	 * countX(check(i, j, 1, board)); countX(check(i, j, 2, board)); } }
	 * 
	 * // Diagonales por capa
	 * 
	 * for (int i = 0; i < n; i++) { countX(check(i, 0, board)); countX(check(i,
	 * 1, board)); countX(check(i, 2, board)); countX(check(i, 3, board));
	 * countX(check(i, 4, board)); countX(check(i, 5, board)); }
	 * 
	 * // Diagonales transversales countX(check(0, board)); countX(check(1,
	 * board)); countX(check(2, board)); countX(check(3, board));
	 * 
	 * }
	 */

	public double calculateVFunction(int xt[]) {

		double s = 0.0;
		double d1 = x[1]+x[2];
		for (int i = 0; i < xt.length; i++) {
			s += xt[i] * w[i];
		}
		//Asumimos que d1 es factor común
		if(d1>0) return s/d1;
		
		return s;
	}

	public void onMatchEnds(IBoard board) {
		double vt, vp;
		
		if (board.getWinner() == null) {
			vt = 0.0;
		} else if (board.getWinner().equals(this)) {
			vt = 100.0;
		} else {
			vt = -100.0;
		}
		// updateX(board);

		vp = calculateVFunction(x);
		System.out.println("vp=" + vp);
		System.out.println("vt=" + vt);
		double error = vt - vp;
		System.out.println("error=" + error);
		double lr = 0.05;
		
		
		try {
			File w_aux= new File("wvalues2.txt");
			File w_main= new File("wvalues.txt");
			FileWriter fw = new FileWriter(w_aux);
			BufferedWriter bw = new BufferedWriter(fw);
			
			FileReader fr = new FileReader(w_main);
			BufferedReader br = new BufferedReader(fr);
			
			
			String s = "";
			for (int i = 0; i < w.length; i++) {
				w[i] = w[i] + lr * error * x[i];
				s+= w[i]+";";
				System.out.println("x[" + i + "]=" + x[i]);
				System.out.println("w[" + i + "]=" + w[i]);
			}
			bw.write(s);
			bw.newLine();
			String s1= br.readLine();
			do{
				bw.write(s1);
				bw.newLine();
				
			}while((s1=br.readLine())!=null);
			br.close();
			bw.close();
			
			fw = new FileWriter(w_main);
			bw = new BufferedWriter(fw);
			
			fr = new FileReader(w_aux);
			br = new BufferedReader(fr);
			s1= br.readLine();
			do{
				
				bw.write(s1);
				bw.newLine();
				
			}while((s1=br.readLine())!=null);
			
			br.close();
			bw.close();
			w_aux.delete();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
