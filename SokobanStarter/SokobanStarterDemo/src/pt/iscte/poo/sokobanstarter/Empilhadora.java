package pt.iscte.poo.sokobanstarter;

import java.util.Random;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement{

	private Point2D position;
	private String imageName;
	
	public Empilhadora(Point2D initialPosition){
		super(initialPosition,"Empilhadora");
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
	
	@Override
	public void move(Direction direction) {
		
		// Gera uma direcao aleatoria para o movimento
		//Direction[] possibleDirections = Direction.values();
		//Random randomizer = new Random();
		//int randomNumber = randomizer.nextInt(possibleDirections.length);
		//Direction randomDirection = possibleDirections[randomNumber];
		
		// Move segundo a direcao gerada, mas so' se estiver dentro dos limites
		//Point2D newPosition = position.plus(randomDirection.asVector());
		
		//Mudar a imagem da empilhadora de acordo com a sua direção
		if(direction.equals(Direction.RIGHT)) {
			imageName="Empilhadora_R";
		}else if(direction.equals(Direction.LEFT)) {
			imageName="Empilhadora_L";
		}else if(direction.equals(Direction.DOWN)) {
			imageName="Empilhadora_D";
		}else if(direction.equals(Direction.UP)) {
			imageName="Empilhadora_U";
			
		}
		Point2D newPosition = position.plus(direction.asVector());
		if (newPosition.getX()>=0 && newPosition.getX()<10 && 
			newPosition.getY()>=0 && newPosition.getY()<10 &&
			isValidMove(position, direction)){
			//Atualiza o hashmap do gameEngine
			super.move(direction,newPosition);
			position = newPosition;
		}
	}
}