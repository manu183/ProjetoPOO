package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class ParedeRachada implements ImageTile{
	private Point2D Point2D;
	private String imageName;
	
	public ParedeRachada(Point2D Point2D){
		this.Point2D = Point2D;
		imageName = "ParedeRachada";
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
