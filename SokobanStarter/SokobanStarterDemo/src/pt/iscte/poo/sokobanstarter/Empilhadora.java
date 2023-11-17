package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends Movable {

	
	private static final String initialImageName = "Empilhadora_D";
	

	public Empilhadora(Point2D initialPosition) {
		super(initialPosition, initialImageName, 0);

	}
	
	
	
	@Override
	public void move(Direction direction) {
		if (direction == Direction.RIGHT) {
			super.setName("Empilhadora_R");
			
		} else if (direction ==Direction.LEFT) {
			super.setName("Empilhadora_L");
			
		} else if (direction == Direction.DOWN) {
			super.setName("Empilhadora_D");
			
		} else if (direction == Direction.UP) {
			super.setName("Empilhadora_U");
			
		}
		super.move(direction);
		
		
	}
	
	/*
	@Override
	public String getName() {
		return imageName;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 2;
	}
*/	
	
}