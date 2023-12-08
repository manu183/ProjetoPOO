package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Bateria extends GameElement implements Catchable {

	public static final String imageName = "Bateria";

	public Bateria(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);

	}

	@Override
	public void catchElement(GameElement gameElement) {
		if(gameElement instanceof Empilhadora) {
			super.removeElement();
			super.gameEngine.bobcat.addBattery(10);
		}
	}
}