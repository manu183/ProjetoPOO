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
		boolean containsBuraco=super.gameEngine.gameMap.containsOnPosition(new Buraco(nextPosition));
		System.out.println("Contains buraco:"+containsBuraco);
		if (containsBuraco) {
			if(alreadyExistsPalete(nextPosition)) {
				super.move(direction);
			}else {
				super.move(direction);
				super.setTransposable(true);
			}
		}else {
			if(!super.getTransposable()) {				
				super.move(direction);
			}
		}
//		System.out.println(super.gameEngine.gameMap);

	}


	private boolean alreadyExistsPalete(Point2D position) {
		if (super.gameEngine.gameMap.containsOnPosition(new Palete(position))) {
			return true;
		}
		return false;
	}

	public boolean isOnBuraco(Point2D position) {
		if (super.gameEngine.gameMap.containsOnPosition(new Buraco(position))) {
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
