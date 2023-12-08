package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Martelo extends GameElement implements Catchable {

	public static final String imageName = "Martelo";

	public Martelo(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);
	}
	
	@Override
	public void catchElement(GameElement gameElement) {
		if(gameElement instanceof Empilhadora) {
			super.removeElement();
			super.gameEngine.bobcat.addMartelo(true);			
		}
	}

}