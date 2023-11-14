package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public interface Movable {
	public void move(Direction direction);
	public boolean isValidMove(Point2D position, Direction direction);
}
