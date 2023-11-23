package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends GameElement {

	public static final String imageName = "ParedeRachada";

	public ParedeRachada(Point2D position) {
		super(position, imageName, 0);
	}
	
	public void removeElement() {
		super.gameEngine.gameMap.removeElement(this);
	}

	public void breakElement() {
		removeElement();
	}

}
