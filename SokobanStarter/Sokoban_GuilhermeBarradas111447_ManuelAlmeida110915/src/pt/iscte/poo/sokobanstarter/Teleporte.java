package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Point2D;

public class Teleporte extends GameElement implements Interectable {

	private static final String imageName = "Teleporte";

	public Teleporte(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);
	}

	// Obter o outro teleporte par
	public Teleporte getOtherTeleporte() {
		// Obter todos os Teleportes que se encontram no mapa
		List<GameElement> elements = super.gameEngine.gameMap.getAllOfTheseGameElement(this);
		Teleporte teleportePar = null;
		// Obter o teleporte par
		for (GameElement actual : elements) {
			if (!(actual.getPosition().equals(super.getPosition()))) {
				teleportePar = ((Teleporte) actual);
				break;
			}
		}
		return teleportePar;
	}

	// Saber se o teleporte está disponível, ou seja, se não existe nenhum
	// GameElement isTranposable na mesma posição que ele
	public boolean isAvailable() {
		// Obter elementos que se ocupam o teleporte par
		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(super.getPosition());
		for (GameElement actual : elements) {
			if (!actual.getTransposable()) {
				return false;
			}
		}
		return true;
	}

	public void teleporte(GameElement gameElement, Point2D nextPosition) {
		Teleporte teleporte = new Teleporte(nextPosition);
		Teleporte otherTeleporte = teleporte.getOtherTeleporte();

		if (((Movable) gameElement).isValidMove(nextPosition)) {
			if (otherTeleporte.isAvailable()) {
				super.gameEngine.gameMap.updateElementPosition(gameElement, otherTeleporte.getPosition());

			} else {
				gameEngine.gameMap.updateElementPosition(gameElement, nextPosition);
				System.out.println(gameElement.getName()
						+ " não se pode mexer porque existe um outro GameElement na posição do outro teleporte");
			}
		}else {
			System.err.println("Not possible to move!");
		}

	}

	@Override
	public void interact(GameElement gameElement) {
//		// TODO Auto-generated method stub
		teleporte(gameElement, super.getPosition());

	}

}