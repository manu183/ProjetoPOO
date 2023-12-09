package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Point2D;

public class Teleporte extends GameElement implements Interectable {

	private static final String imageName = "Teleporte";

	public Teleporte(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);
	}

	// Metodo para obter o outro teleporte par
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

	// Saber se o teleporte esta disponivel, ou seja, se nao existe nenhum
	// GameElement isTranposable na mesma posicao que ele
	public boolean isAvailable() {
		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(super.getPosition());// Obter elementos que se ocupam o teleporte par
		for (GameElement actual : elements) {
			if (!actual.getTransposable()) {
				return false;
			}
		}
		return true;
	}
	

	//Implementa o metodo interact que teleporta o objeto que interaje com o teleporte
	@Override
	public Point2D interact(GameElement gameElement) {
		Teleporte teleporte = new Teleporte(getPosition());
		Teleporte otherTeleporte = teleporte.getOtherTeleporte();

		if (((Movable) gameElement).isValidMove(getPosition())) {//So e possivel realizar o teleporte caso o metodo isValidMove retorne true
			if (otherTeleporte.isAvailable()) {//Verifica se o teleporte par esta disponivel
				return otherTeleporte.getPosition();
			} else {
				
				System.out.println(gameElement.getName()
						+ " nao se pode teleportar porque existe um outro GameElement na posicao do outro teleporte");
				return getPosition();
			}
		}else {
			System.err.println("Not possible to move!");
			return gameElement.getPosition();
		}

	}


}