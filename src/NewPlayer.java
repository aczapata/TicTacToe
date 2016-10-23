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
	public int[] randMove(){
		Random r= new Random();
		int x=r.nextInt(5);
		int y=r.nextInt(5);
		int z=r.nextInt(5);
		return new int[]{x,y,z};
	}
	
	public int[] smartMove(IBoard board){
		int x1=0,x2=0;
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				boolean containsX = false;
				boolean containsO = false; 
				for(int k=0;k<5;k++){
					IPlayer p= board.getFieldValue(new int[]{i,j,k});
					if (p!=null){
						if (this.getName().equals(p.getName())){
							containsX = true; 
						}else{
							containsO = true;						
						}
					}
				}
				
				if (containsX&&!containsO){
					x1++;
				}else if (!containsX&&containsO){
					x2++;					
				}else if (!containsX&&!containsO){
					x1++;
					x2++;
				}
			}
		}
		System.out.println("X1= " + x1);
		System.out.println("X2= " + x2);
		Random r= new Random();
		int x=r.nextInt(5);
		int y=r.nextInt(5);
		int z=r.nextInt(5);
		return new int[]{x,y,z};
	}
	
	public int[] makeMove(IBoard board) {
		// TODO Auto-generated method stub

		// create a clone of the board that can be modified
		IBoard copy = board.clone();
		double d = 0;
		Boolean legalMove=false;
		Move newMove = null;
		// do a move using the cloned board
		do{
			newMove = new Move(this, smartMove(copy));
		try {
			copy.makeMove(newMove);
			legalMove= true;
		} catch (IllegalMoveException e) {
			// move was not allowed
			//e.printStackTrace();
		}
		}while(!legalMove);
		// return your final decision for your next move
		return newMove.getPosition();
	}

	public void onMatchEnds(IBoard board) {
		return;
	}

}
