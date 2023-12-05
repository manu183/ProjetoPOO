package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public abstract class Interectable extends GameElement{

	protected Interectable(Point2D position, String name, int layer) {
		super(position, name, layer);
	}
	
	public  abstract void interact(GameElement gameElement, Point2D nextPosition);
}
