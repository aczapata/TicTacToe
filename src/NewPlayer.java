import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.api.IMove;
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
	double[] w = new double[8];
	int[] x = new int[8];
	ArrayList<int[]> lx = new ArrayList<>();
	String name = "Andrea ";

	public NewPlayer() throws IOException {
		getValues();
	}

	public String getName() {
		return this.name;
	}

	public void getValues() throws IOException {
		x[0] = 1;
		// Total number of 2-groups
		x[1] = 0;
		x[2] = 0;
		// Total number of 3-groups
		x[3] = 0;
		x[4] = 0;
		// Total number of 4-groups
		x[5] = 0;
		x[6] = 0;
		// Total number of 5-groups
		x[7] = 0;
		
		try {
			FileReader fr = new FileReader(new File("wvalues.txt"));
			BufferedReader br = new BufferedReader(fr);
			int t = 0;
			for (String s : br.readLine().split(";")) {
				w[t] = Double.parseDouble(s);
				t++;
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			FileWriter fw = new FileWriter(new File("wvalues.txt"));
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("0.0;1.25;-1.25;5.0;-5.0;20.0;-80.0;80.0;");
			bw.close();
			fw.close();

			FileReader fr = new FileReader(new File("wvalues.txt"));
			BufferedReader br = new BufferedReader(fr);
			int t = 0;
			for (String s : br.readLine().split(";")) {
				w[t] = Double.parseDouble(s);
				t++;
			}
			br.close();
			
			FileWriter x_fw = new FileWriter(new File("xvalues.txt"));
			BufferedWriter x_bw = new BufferedWriter(x_fw);
			
			x_bw.newLine();
			x_bw.close();
			x_fw.close();

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
					if (board.getFieldValue(new int[] { k, k, i }).equals(this))
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
					if (board.getFieldValue(new int[] { k, i, k }).equals(this))
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
					if (board.getFieldValue(new int[] { i, k, k }).equals(this))
						checkP++;
					else
						checkO++;
				} catch (NullPointerException e) {
				}
			}
			break;
		case 3:
			// x=n-(y+1) , z static,
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { k, n - (k + 1), i }).equals(this))
						checkP++;
					else
						checkO++;
				} catch (NullPointerException e) {
				}
			}
			break;
		case 4:
			// x=n-(z+1), y static,
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { k, i, n - (k + 1) }).equals(this))
						checkP++;
					else
						checkO++;
				} catch (NullPointerException e) {
				}
			}
			break;
		case 5:
			// y=n-(z+1), x static
			for (int k = 0; k < n; k++) {
				try {
					if (board.getFieldValue(new int[] { i, k, n - (k + 1) }).equals(this))
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

	public void countY(int[] xt, int[] c, boolean myMove) {
		if (myMove) {
			if (c[1] == 0) {
				if (c[0] == 2) {
					xt[1]++;
				} else if (c[0] == 3) {
					xt[3]++;
					xt[1]--;
				} else if (c[0] == 4) {
					xt[5]++;
					xt[3]--;
				} else if (c[0] == 5) {
					xt[7]++;
					xt[5]--;
				}
			} else {
				if (c[0] == 1) {
					if (c[1] == 2) {
						xt[2]--;
					} else if (c[1] == 3) {
						xt[4]--;
					} else if (c[1] == 4) {
						xt[6]--;
					}
				}
			}
		} else {
			if (c[0] == 0) {
				if (c[1] == 2) {
					xt[2]++;
				} else if (c[1] == 3) {
					xt[4]++;
					xt[2]--;
				} else if (c[1] == 4) {
					xt[6]++;
					xt[4]--;
				} else if (c[1] == 5) {
					xt[8]++;
					xt[6]--;
				}
			} else {
				if (c[1] == 1) {
					if (c[0] == 2) {
						xt[1]--;
					} else if (c[0] == 3) {
						xt[3]--;
					} else if (c[0] == 4) {
						xt[5]--;
					}
				}
			}
		}

	}

	public void updateXvalues(int[] lastMove, int x[], IBoard board, boolean myMove) {

		countY(x, check(lastMove[0], lastMove[1], 0, board), myMove);
		countY(x, check(lastMove[0], lastMove[2], 1, board), myMove);
		countY(x, check(lastMove[1], lastMove[2], 2, board), myMove);

		if (lastMove[0] == lastMove[1]) {
			countY(x, check(lastMove[2], 0, board), myMove);
		}
		if (lastMove[0] == lastMove[2]) {
			countY(x, check(lastMove[1], 1, board), myMove);
		}
		if (lastMove[1] == lastMove[2]) {
			countY(x, check(lastMove[0], 2, board), myMove);
		}
		if (lastMove[0] == (n - (lastMove[1] + 1))) {
			countY(x, check(lastMove[2], 3, board), myMove);
		}
		if (lastMove[0] == (n - (lastMove[2] + 1))) {
			countY(x, check(lastMove[1], 4, board), myMove);
		}
		if (lastMove[1] == (n - (lastMove[2] + 1))) {
			countY(x, check(lastMove[0], 5, board), myMove);
		}
		if (lastMove[0] == lastMove[1] && lastMove[0] == lastMove[2]) {
			countY(x, check(0, board), myMove);
		} 
		if (lastMove[0] == lastMove[1] && lastMove[2] == (n - (lastMove[1] + 1))) {
			countY(x, check(1, board), myMove);
		} 
		if (lastMove[0] == lastMove[2] && lastMove[1] == (n - (lastMove[2] + 1))) {
			countY(x, check(2, board), myMove);
		} 
		if (lastMove[1] == lastMove[2] && lastMove[0] == (n - (lastMove[2] + 1))) {
			countY(x, check(3, board), myMove);
		}
	}

	public double calculateVFunction(int xt[]) {

		double s = 0.0;

		for (int i = 0; i < xt.length; i++) {
			s += xt[i] * w[i];
		}

		return s;
	}

	public Move smartMove(IBoard board) {

		double maxV = 0;
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
							// System.out.println(printV(newMove.getPosition())
							// + ";" + vt1);
							if (maxM == null) {
								maxV = vt1;
								maxM = newMove;
								// System.out.println(printV(newMove.getPosition())+";"+vt1);
							} else {
								if (vt1 > maxV) {
									maxV = vt1;
									maxM = newMove;
									//System.out.println(printV(newMove.getPosition()) + ";" + vt1);
								}
							}

						} catch (IllegalMoveException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		return maxM;
	}

	public void fileUpdate(File file, String s) throws IOException {
		try{
		File aux = new File("file_aux"+Math.random()+".txt");
		
		FileWriter fw = new FileWriter(aux);
		BufferedWriter bw = new BufferedWriter(fw);

		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		bw.write(s);
		bw.newLine();
		
		String s1 = br.readLine();
		do {
			bw.write(s1);
			bw.newLine();

		} while ((s1 = br.readLine()) != null);
		
		br.close();
		fr.close();
		
		bw.close();
		fw.close();
		

		FileWriter fw2 = new FileWriter(file);
		BufferedWriter bw2 = new BufferedWriter(fw2);

		FileReader fr2 = new FileReader(aux);
		BufferedReader br2 = new BufferedReader(fr2);
		
		s1 = br2.readLine();
		do {

			bw2.write(s1);
			bw2.newLine();

		} while ((s1 = br2.readLine()) != null);

		br2.close();
		bw2.close();
		
		fw2.close();
		fr2.close();
		
		aux.delete();}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public int[] makeMove(IBoard board) {
		// TODO Auto-generated method stub

		// create a clone of the board that can be modified
		IBoard copy = board.clone();

		if (copy.getMoveHistory().size() > 0) {
			int[] lastMove = copy.getMoveHistory().get(copy.getMoveHistory().size() - 1).getPosition();
			updateXvalues(lastMove, x, board, false);
			int[] xt = x.clone();
			lx.add(xt);
			//System.out.println(printV(xt));
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
		int[] xt = x.clone();
		lx.add(xt);
		//System.out.println(printV(xt));
		// return your final decision for your next move
		return newMove.getPosition();
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

		//for (int k = 1; k < lx.size(); k += 2) {

			vp = calculateVFunction(x);
			double error = vt - vp;
			double lr = 0.1;

			try {
				
				String s = "", sx = "";
				for (int i = 0; i < w.length; i++) {
					w[i] = w[i] + lr * error * x[i];
					s += w[i] + ";";
					sx += x[i] + ";";
				}
								
				File w_main = new File("wvalues.txt");
				fileUpdate(w_main, s);
				
				File x_main = new File("xvalues.txt");
				fileUpdate(x_main, sx+";"+vt);
				
				getValues();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}
	}

}
