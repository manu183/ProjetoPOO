package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.utils.Point2D;

public class Bateria extends GameElement{
	private Point2D position;
	private String imageName;
	
	public Bateria(Point2D position){
		super(position,"Bateria");
		this.position = position;
		imageName = "Bateria";

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
