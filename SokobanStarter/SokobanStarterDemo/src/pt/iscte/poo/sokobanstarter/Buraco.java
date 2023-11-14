package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Buraco extends GameElement{
	private Point2D Point2D;
	private String imageName;
	
	public Buraco(Point2D Point2D){
		this.Point2D = Point2D;
		imageName = "Buraco";
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
