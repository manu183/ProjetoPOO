package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends GameElement implements Interectable {

	private static final String imageName = "ParedeRachada";

	public ParedeRachada(Point2D position) {
		super(position, imageName, 0);
	}

	@Override
	public void interact(GameElement gameElement) {
		if (gameElement instanceof Empilhadora) { //Uma parede só pode ser rachada se de facto o objeto que a tenta partir é uma empilhadora
			if (((Empilhadora) gameElement).hasMartelo()) {//Verifica se o gameElement, que é uma empilhadora, tem um martelo para poder partir a parede
				super.removeElement(); //Remove esta ParedeRachada o gameMap, e consequentemente da GUI
				super.gameEngine.gameMap.updateElementPosition(gameElement, getPosition());//Move o gameElement, que tem de ser uma empilhadora, para o lugar desta ParedeRachada
			}
		}
	}
}