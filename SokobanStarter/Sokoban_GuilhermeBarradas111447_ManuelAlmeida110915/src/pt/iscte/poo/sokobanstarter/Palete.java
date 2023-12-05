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
		boolean containsBuraco = super.gameEngine.gameMap.containsOnPosition(new Buraco(nextPosition));
		System.out.println("Contains buraco:" + containsBuraco);

		if(super.getTransposable()) {
			System.out.println("Esta palete não se deve mexer mais");
		}else {
			System.out.println("Esta palete deve-se mexer");
			if(containsBuraco) {
				if(!alreadyExistsPalete(nextPosition)) {
					super.setTransposable(true);
				}
			}
			super.gameEngine.gameMap.updateElementPosition(this, nextPosition);
		}

		
	}

	private boolean alreadyExistsPalete(Point2D position) {
		return super.gameEngine.gameMap.containsOnPosition(new Palete(position));
	}

	public boolean isOnBuraco(Point2D position) {
		return super.gameEngine.gameMap.containsOnPosition(new Buraco(position));
	}

}
