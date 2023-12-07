package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Buraco extends GameElement implements Interectable {

	public static final String imageName = "Buraco";

	public Buraco(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);
	}

	@Override
	public void interact(GameElement gameElement, Point2D nextPosition) {
		Palete palete = new Palete(nextPosition);
		if (!(gameElement instanceof Palete) && !super.gameEngine.gameMap.containsOnPosition(palete)) {
			System.out.println("Existe uma palete no buraco");
			gameElement.removeElement();
//			super.gameEngine.gameMap.updateElementPosition(gameElement, nextPosition);
		} else {
			super.gameEngine.gameMap.updateElementPosition(gameElement, nextPosition);
		}
	}
}