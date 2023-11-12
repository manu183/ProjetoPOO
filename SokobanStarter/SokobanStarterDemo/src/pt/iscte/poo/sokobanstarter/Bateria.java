package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Bateria extends GameElement{
	private Point2D Point2D;
	private String imageName;
	
	public Bateria(Point2D Point2D){
		this.Point2D = Point2D;
		imageName = "Bateria";
	}
	
	@Override
	public String getName() {
		return imageName;
	}

	@Override
	public Point2D getPosition() {
		return Point2D;
	}

	@Override
	public int getLayer() {
		return 0;
	}
}
