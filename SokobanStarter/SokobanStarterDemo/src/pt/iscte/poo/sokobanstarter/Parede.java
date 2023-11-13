package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Parede extends GameElement{
	
	private Point2D position;
	private static String imageName="Parede";
	
	public Parede(Point2D initialPosition){
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

}
