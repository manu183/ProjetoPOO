package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Palete extends Movable {

	private static final String imageName = "Palete";

	public Palete(Point2D position) {
		super(position, imageName, 0);

	}
	
	//Método para mover os objetos Palete
	@Override
	protected void move(Direction direction) {
		Point2D nextPosition = super.calculateFinalPosition(getPosition(), direction);
		boolean containsBuraco = super.gameEngine.gameMap.exists(new Buraco(nextPosition));//Verifica se existe um buraco na próxima posição

		if (!super.getTransposable()) {
			if (containsBuraco) {
				if (!alreadyExistsPalete(nextPosition)) {
					super.setTransposable(true);
				}
			}
		}
		super.move(direction);//Invoca o método move da classe Movable

	}
	
	//Método que verifica se existe um objeto Palete numa certa posição
	private boolean alreadyExistsPalete(Point2D position) {
		return super.gameEngine.gameMap.exists(new Palete(position));
	}

}