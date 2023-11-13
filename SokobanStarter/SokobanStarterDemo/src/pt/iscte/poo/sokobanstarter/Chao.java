package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Chao extends GameElement{

	private Point2D position;
	private static String imageName = "Chao";
	
	public Chao(Point2D position){
		super(position,imageName);
		this.position = position;
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
