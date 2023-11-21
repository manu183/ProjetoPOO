package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Bateria extends GameElement {

	public static final String imageName = "Bateria";

	public Bateria(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);

	}
}
