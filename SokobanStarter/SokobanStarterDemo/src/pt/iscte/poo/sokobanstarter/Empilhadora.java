package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement implements Movable {

	private Point2D position;
	private String imageName;

	public Empilhadora(Point2D initialPosition) {
		super(initialPosition, "Empilhadora", 0);
		imageName = "Empilhadora_D";
		position = initialPosition;

	}

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

	public void move(Direction direction) {
		if (direction.equals(Direction.RIGHT)) {
			imageName = "Empilhadora_R";
		} else if (direction.equals(Direction.LEFT)) {
			imageName = "Empilhadora_L";
		} else if (direction.equals(Direction.DOWN)) {
			imageName = "Empilhadora_D";
		} else if (direction.equals(Direction.UP)) {
			imageName = "Empilhadora_U";

		}
		Point2D newPosition = position.plus(direction.asVector());
		if (newPosition.getX() >= 0 && newPosition.getX() < 10 && newPosition.getY() >= 0 && newPosition.getY() < 10
				&& isValidMove(position, direction)) {
			position = newPosition;
		}

	}
}