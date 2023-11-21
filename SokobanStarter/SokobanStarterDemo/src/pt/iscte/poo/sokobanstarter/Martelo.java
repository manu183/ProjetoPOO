package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Martelo extends GameElement {

	public static final String imageName = "Martelo";

	public Martelo(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);
	}

}
