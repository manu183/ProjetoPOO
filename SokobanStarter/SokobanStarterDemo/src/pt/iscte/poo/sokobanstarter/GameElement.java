package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile{
	public boolean validMoves[][] = new boolean[GameEngine.GRID_HEIGHT][GameEngine.GRID_WIDTH];
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point2D getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void move() {}

	public void move(Direction direction) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isValidMove(Direction direction) {
		// TODO Auto-generated method stub
		
	}

}
