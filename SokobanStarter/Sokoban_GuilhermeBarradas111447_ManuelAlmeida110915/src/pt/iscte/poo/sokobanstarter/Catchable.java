package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public abstract class Catchable extends GameElement {
	public Catchable(Point2D position, String name, int layer) {
		super(position, name, layer);

	}	
	
	public void catchElement() {
		removeElement();
	}

}
