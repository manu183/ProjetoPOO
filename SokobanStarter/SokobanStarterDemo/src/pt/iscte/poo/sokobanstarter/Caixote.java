package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Caixote extends Movable {

	public static final String imageName = "Caixote";

	public Caixote(Point2D position) {
		super(position, imageName, 0);

	}

	@Override
	public boolean isValidMove(Point2D initialPosition, Direction direction) {
		//Verifica a implementação feita no Movable, que vê se existe alguma parede para onde o objeto deseija deslocar
		if (!super.isValidMove(initialPosition, direction))
			return false;
		
		//Verifica se existe um caixote seguido de um caixote
		if (super.gameEngine.gameMap.existsOnPosition(super.calculateFinalPosition(initialPosition, direction),
				imageName)) 
			return false;
		

		return false;
	}
	
	

}
