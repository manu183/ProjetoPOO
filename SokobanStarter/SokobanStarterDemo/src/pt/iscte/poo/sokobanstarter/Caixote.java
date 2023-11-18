package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Caixote extends Movable {

	public static final String imageName = "Caixote";

	public Caixote(Point2D position) {
		super(position, imageName, 0);

	}
	
	@Override
	public void move(Direction direction) {
		
		if(isValidMove(getPosition(),direction)) {
			Point2D newPosition=super.calculateFinalPosition(getPosition(), direction);
			super.gameEngine.gameMap.updateElementPosition(this, newPosition);
			System.out.println("Move caixote!!!");
			super.gameEngine.sycronizeTileList();
			
		}else {

			System.out.println("It is not possible to move Caixote to that position!");
		}
		System.out.println();
		System.out.println();
		System.out.println(super.gameEngine.gameMap);
	}

	@Override
	public boolean isValidMove(Point2D initialPosition, Direction direction) {
		//Verifica a implementação feita no Movable, que vê se existe alguma parede para onde o objeto deseija deslocar
		boolean res = false;
		
		if (super.isValidMove(initialPosition, direction)) {
//			System.out.println("Invalid caixote move1!");
			res=true;
		}
		
		//Verifica se existe um caixote seguido de um caixote
		if (super.gameEngine.gameMap.existsOnPosition(super.calculateFinalPosition(initialPosition, direction),
				imageName)) {
//			System.out.println("Invalid caixote move2!");
			res=false;
		}
		
		System.out.println("A função isValidMove do Caixote devolve:"+res);
		return res;
	}
	
	
	

}
