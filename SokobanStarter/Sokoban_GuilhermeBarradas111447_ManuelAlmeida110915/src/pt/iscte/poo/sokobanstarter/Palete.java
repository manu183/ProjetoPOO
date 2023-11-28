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
		
		System.out.println(this);
		System.out.println("isValidMove:"+isValidMove(getPosition(), direction));
		if (isValidMove(getPosition(), direction)) {
			for (GameElement actual : elements) {
				if (actual instanceof Buraco) {
					if(!alreadyExistsPalete(nextPosition)) {
						this.setTransposable(true);
						System.out.println("isTransposable is now true");
					}
				}
			}
			System.out.println("MOVEEEEEEEEEE");
			super.move(direction);
		}

	}

	@Override
	protected boolean isValidMove(Point2D initialPosition, Direction direction) {
		Point2D nextPosition = super.calculateFinalPosition(getPosition(), direction);
		if(!super.isValidMove(initialPosition, direction) || (isOnBuraco(initialPosition) && !alreadyExistsPalete(nextPosition))){
			System.out.println("Palete nextPosition:"+nextPosition);
			return false;
		}
		return true;

	}
	
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
