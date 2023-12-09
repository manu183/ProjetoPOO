package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Point2D;

public class Teleporte extends GameElement implements Interectable {

	private static final String imageName = "Teleporte";

	public Teleporte(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);
	}

	// Método para obter o outro teleporte par
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
		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(super.getPosition());// Obter elementos que se ocupam o teleporte par
		for (GameElement actual : elements) {
			if (!actual.getTransposable()) {
				return false;
			}
		}
		return true;
	}
	
	//Método que teleporta um certo gameElement para uma certa posião
	public void teleporte(GameElement gameElement, Point2D nextPosition) {
		Teleporte teleporte = new Teleporte(nextPosition);
		Teleporte otherTeleporte = teleporte.getOtherTeleporte();

		if (((Movable) gameElement).isValidMove(nextPosition)) {//Só é possível realizar o teleporte caso o método isValidMove retorn true
			if (otherTeleporte.isAvailable()) {//Verifica se o teleporte par está disponível
				super.gameEngine.gameMap.updateElementPosition(gameElement, otherTeleporte.getPosition());//Altera a posição de gameElement para a posição do teleporte par

			} else {
				gameEngine.gameMap.updateElementPosition(gameElement, nextPosition);
				System.out.println(gameElement.getName()
						+ " não se pode teleportar porque existe um outro GameElement na posição do outro teleporte");
			}
		}else {
			System.err.println("Not possible to move!");
		}

	}
	
	//Implementa o método interact que teleporta o objeto que interaje com o teleporte
	@Override
	public void interact(GameElement gameElement) {
		teleporte(gameElement, super.getPosition());

	}

}