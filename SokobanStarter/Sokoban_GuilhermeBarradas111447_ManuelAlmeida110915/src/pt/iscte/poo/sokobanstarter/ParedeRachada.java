package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends GameElement implements Interectable {

	public static final String imageName = "ParedeRachada";

	public ParedeRachada(Point2D position) {
		super(position, imageName, 0);
	}

	@Override
	public void interact(GameElement gameElement, Point2D nextPosition) {
		if (gameElement instanceof Empilhadora) {
			if (((Empilhadora) gameElement).hasMartelo()) {
//				gui.setMessage("É empilhadora e tem martelo");
				this.removeElement();
				super.gameEngine.gameMap.updateElementPosition(gameElement, this.getPosition());
			}

		}

	}

}