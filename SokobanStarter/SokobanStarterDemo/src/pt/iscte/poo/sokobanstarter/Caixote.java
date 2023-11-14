package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Caixote extends GameElement {

	private Point2D position;
	private static String imageName="Caixote";
	
	public Caixote(Point2D initialPosition){
		super(initialPosition,imageName);
		this.position = initialPosition;
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
		return 0;
	}
	
	@Override
	public void move(Direction direction) {
		
		//Mudar a imagem da empilhadora de acordo com a sua direção
		Point2D newPosition = position.plus(direction.asVector());
		if (newPosition.getX()>=0 && newPosition.getX()<10 && 
			newPosition.getY()>=0 && newPosition.getY()<10 &&
			isValidMove(position, direction)){
			super.gameEngine.moveImageTile(position, newPosition, this);
			position = newPosition;
		}
	}

}
