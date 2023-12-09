package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends GameElement implements Interectable {

	private static final String imageName = "ParedeRachada";

	public ParedeRachada(Point2D position) {
		super(position, imageName, 0);
	}

	@Override
	public Point2D interact(GameElement gameElement) {
	    if (gameElement instanceof Empilhadora && ((Empilhadora) gameElement).hasMartelo()) {
	        super.removeElement();
	        return super.getPosition();
	    } else {
	        return gameElement.getPosition();
	    }
	}

}