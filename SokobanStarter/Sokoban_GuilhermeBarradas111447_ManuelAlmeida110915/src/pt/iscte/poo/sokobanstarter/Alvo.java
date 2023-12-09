package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Alvo extends GameElement implements Interectable{

	private static final String imageName = "Alvo";

	public Alvo(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);

	}

	@Override
	public Point2D interact(GameElement gameElement) {
		if(gameElement instanceof Caixote) {
			((Caixote)gameElement).setOnAlvo(true);
		}
		return super.getPosition();
	}
}
