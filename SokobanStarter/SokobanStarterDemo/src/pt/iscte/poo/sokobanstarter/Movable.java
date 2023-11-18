package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class Movable extends GameElement {

//	private Point2D position;
//	private String name;
//	private int layer;

	public Movable(Point2D position, String name, int layer) {
		super(position, name, layer);
//		this.position=position;

	}

	public static Movable createMovable(char key, Point2D position) {

		switch (key) {
		case 'C':
			return new Caixote(position);
		case 'E':
			return new Empilhadora(position);
		case 'P':
			return new Palete(position);
		default:
			throw new IllegalArgumentException("Unrecognized key '" + key + "' for creating Movable element");
		}
	}
	
	public static Movable createMovable(String name, Point2D position) {

		switch (name) {
		case Caixote.imageName:
			return new Caixote(position);
		case Empilhadora.initialImageName:
			return new Empilhadora(position);
		case Palete.imageName:
			return new Palete(position);
		default:
			throw new IllegalArgumentException("Unrecognized name " + "name" + " for creating Movable element");
		}
	}


	public void move(Direction direction) {
		System.out.println("Last position:" + this);
		
		
		
		//Verifica através da função isValidMove se o objeto se pode mover
		 if(isValidMove(super.getPosition(), direction)) {
			
			 //Calcular a nova posição
			Point2D newPosition = super.getPosition().plus(direction.asVector()); 
			 
			System.out.println("New Position:" + newPosition);
			// Atualizar o map do gameEngine
			super.gameEngine.gameMap.updateElementPosition(this, newPosition);
			
			super.setPosition(newPosition);
			
			// Verificar se realmente atualizou
			System.out.println("Verificação:" + this);
			System.out.println("Mexer!");
			System.out.println(gameEngine.gameMap.toString());
			System.out.println(gameEngine.getTile());
			System.out.println();
		}
	}

	
	protected boolean isValidMove(Point2D initialPosition, Direction direction) {
		// TODO Auto-generated method stub
		// Verifica se existe algum obstáculo na direção que pretende que seja uma
		// parede ou então dois caixotes seguidos
		Point2D nextPosition = calculateFinalPosition(initialPosition, direction);
		
		if (nextPosition.getX() < 0 && nextPosition.getX() >= 10 && nextPosition.getY() < 0 && nextPosition.getY() >= 10) {
			return false;
		}
		
		
		// Verifica se existe uma parede na posição seguinte
		if (super.gameEngine.gameMap.existsOnPosition(nextPosition, Parede.imageName)) {
			return false;
		}

		return true;

	}
	
	
	// Calcula a posição final de um movimento


	

	
	
	

	public Point2D calculateFinalPosition(Point2D initialPosition, Direction direction) {
		Point2D finalPosition = initialPosition.plus(direction.asVector());

		return finalPosition;
	}

}
