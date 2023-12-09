package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Vazio extends GameElement {

	private static final String imageName = "Vazio";

	public Vazio(Point2D position) {
		super(position, imageName, 0);
	}
}