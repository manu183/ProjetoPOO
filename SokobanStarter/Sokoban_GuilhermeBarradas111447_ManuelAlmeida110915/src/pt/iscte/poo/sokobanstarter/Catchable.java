package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public abstract class Catchable extends GameElement {
	public Catchable(Point2D position, String name, int layer) {
		super(position, name, layer);
	}
	
	// O método define que por defeito, quando um Catchable é "apanhado" o mesmo desaparece
	public void catchElement() {
		super.removeElement(); //Remove esta ParedeRachada o gameMap, e consequentemente da GUI
	}

}