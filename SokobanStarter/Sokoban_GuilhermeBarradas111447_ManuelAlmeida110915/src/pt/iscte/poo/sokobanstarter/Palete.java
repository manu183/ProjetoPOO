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
		boolean containsBuraco = super.gameEngine.gameMap.exists(new Buraco(nextPosition));

		if (!super.getTransposable()){
			if (containsBuraco) {
				if (!alreadyExistsPalete(nextPosition)) {
					super.setTransposable(true);
				}
			}
		}
		super.move(direction);

	}

	private boolean alreadyExistsPalete(Point2D position) {
		return super.gameEngine.gameMap.exists(new Palete(position));
	}

	public boolean isOnBuraco(Point2D position) {
		return super.gameEngine.gameMap.exists(new Buraco(position));
	}

}