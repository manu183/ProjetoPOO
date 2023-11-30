package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Palete extends Movable {

	public static final String imageName = "Palete";

	public Palete(Point2D position) {
		super(position, imageName, 0);

	}

	@Override
	public void move(Direction direction) {

		Point2D nextPosition = super.calculateFinalPosition(getPosition(), direction);
		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(nextPosition);
		
//		System.out.println(this);
//		System.out.println("isValidMove:"+isValidMove(nextPosition));
		if (isValidMove(nextPosition)) {
			if(super.gameEngine.gameMap.containsOnPosition(new Buraco(nextPosition))) {
				if(!alreadyExistsPalete(nextPosition)) {
					super.setTransposable(true);
				}else {
					super.setTransposable(false);
				}
			}
			super.move(direction);
			System.out.println("MOVEEEEEEEEEE");
		}

	}

//	@Override
//	protected boolean isValidMove(Point2D finalPosition) {
//		if(!super.isValidMove(finalPosition) || (isOnBuraco(super.getPosition()) && !alreadyExistsPalete(finalPosition))){
//			System.out.println("Palete nextPosition:"+finalPosition);
//			return false;
//		}
//		return true;
//
//	}
	
	private boolean alreadyExistsPalete(Point2D position) {
		if(super.gameEngine.gameMap.containsOnPosition(new Palete(position))) {
			return true;
		}
		return false;
	}
	
	public boolean isOnBuraco(Point2D position) {
		if(super.gameEngine.gameMap.containsOnPosition(new Buraco(position))) {
			return true;
		}
		return false;
	}
//	@Override
//	protected boolean isValidMove(Point2D initialPosition, Direction direction) {
//		if(super.gameEngine.gameMap.existsOnPosition(initialPosition, Buraco.imageName) || !super.isValidMove(initialPosition, direction)) {
//			return false;
//		}
//		return true;
//
//	}

}
