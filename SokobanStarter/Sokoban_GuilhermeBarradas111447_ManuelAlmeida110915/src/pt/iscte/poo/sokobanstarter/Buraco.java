package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Buraco extends GameElement implements Interectable {

	private static final String imageName = "Buraco";

	public Buraco(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);
	}

	@Override
	public Point2D interact(GameElement gameElement) {

		Palete palete = new Palete(super.getPosition());// Um objeto do tipo palete na posicao do Buraco

		if (!(gameElement instanceof Palete) && !super.gameEngine.gameMap.exists(palete)) {
			gameElement.removeElement(); // Caso o buraco nao tenha nenhuma palete sobreposta e caso o gameElement nao
											// seja do tipo Palete o gameElement e removido porque cai no buraco
			return gameElement.getPosition();
		} else {
			return super.getPosition(); // Caso contrario significa que o gameElement pode transitar para cima
																								// do buraco sem que
																								// caia
		}
	}

}