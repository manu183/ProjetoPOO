package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Caixote extends Movable {

	private static final String imageName = "Caixote";

	public Caixote(Point2D position) {
		super(position, imageName, 0);

	}

//	@Override
//	public void move(Direction direction) {
//		// TODO Auto-generated method stub
//		// Mudar a imagem da empilhadora de acordo com a sua direção
//		Point2D newPosition = super.getPosition().plus(direction.asVector());
//		if (newPosition.getX() >= 0 && newPosition.getX() < 10 && newPosition.getY() >= 0 && newPosition.getY() < 10
//				&& isValidMove(super.getPosition(), direction)) {
//			// TODO Acabar este método
//		}
//
//	}

}
