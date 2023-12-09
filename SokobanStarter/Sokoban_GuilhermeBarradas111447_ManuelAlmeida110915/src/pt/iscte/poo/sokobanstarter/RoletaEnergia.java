package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class RoletaEnergia extends GameElement implements Interectable {
	private static final String imageName = "RoletaEnergia";

	protected RoletaEnergia(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);
	}

	// Metodo para sorteiar e alterar o nivel de energia da bateria da empilhadora
	private void sortBattery() {
		int sorted = (int) (Math.random() * (121)) - 50;
		if (sorted > 0) {
			gui.setMessage("Ganhou " + sorted + " energias!");
		} else if (sorted < 0) {
			gui.setMessage("Perdeu " + Math.abs(sorted) + " energias!");
		}
		super.gameEngine.bobcat.addBattery(sorted);
	}

	@Override
	public Point2D interact(GameElement gameElement) {
		// TODO Auto-generated method stub
		if (gameElement instanceof Empilhadora) {
			sortBattery();// Invoca-se o metodo sortBattery para sorteiar a energia
			return super.getPosition();// Move-se o objeto que
		}
		return gameElement.getPosition();
	}

}
