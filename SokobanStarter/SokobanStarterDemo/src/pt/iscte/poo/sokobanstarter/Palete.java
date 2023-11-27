package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Palete extends Movable {

	public static final String imageName = "Palete";

	public Palete(Point2D position) {
		super(position, imageName, 0);

	}

	public void move(Direction direction) {

		Point2D nextPosition = super.calculateFinalPosition(getPosition(), direction);
		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(nextPosition);

		if(isValidMove(getPosition(),direction)) {
			for (GameElement actual : elements) {
				if (actual instanceof Buraco) {
					this.setTransposable(true);
				}
			}
			super.move(direction);
		}

	}
	
	protected boolean isValidMove(Point2D initialPosition, Direction direction) {
		if(super.gameEngine.gameMap.existsOnPosition(initialPosition, Buraco.imageName) || !super.isValidMove(initialPosition, direction)) {
			return false;
		}
		return true;

	}
	
	
}
