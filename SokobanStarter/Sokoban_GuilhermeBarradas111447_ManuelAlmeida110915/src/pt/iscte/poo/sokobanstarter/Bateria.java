package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Bateria extends Catchable {

	public static final String imageName = "Bateria";

	public Bateria(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);

	}

	@Override
	public void catchElement() {
		super.catchElement();
		super.gameEngine.bobcat.addBattery(10);
	}
}