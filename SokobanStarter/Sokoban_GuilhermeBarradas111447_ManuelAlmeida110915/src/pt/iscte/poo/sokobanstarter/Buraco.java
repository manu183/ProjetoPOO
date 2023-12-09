package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Buraco extends GameElement implements Interectable {

	public static final String imageName = "Buraco";

	public Buraco(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);
	}

	@Override
	public void interact(GameElement gameElement) {

		Palete palete = new Palete(super.getPosition());// Um objeto do tipo palete na posição do Buraco

		if (!(gameElement instanceof Palete) && !super.gameEngine.gameMap.exists(palete)) {
			gameElement.removeElement(); // Caso o buraco não tenha nenhuma palete sobreposta e caso o gameElement não
											// seja do tipo Palete o gameElement é removido porque cai no buraco
		} else {
			super.gameEngine.gameMap.updateElementPosition(gameElement, super.getPosition()); // Caso contrário significa que o gameElement pode transitar para cima do buraco sem que caia
		}
	}

}