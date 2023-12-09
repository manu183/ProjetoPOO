package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class RoletaEnergia extends GameElement implements Interectable {
	private static final String imageName = "RoletaEnergia";

	protected RoletaEnergia(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);
	}

	// Método para sorteiar e alterar o nível de energia da bateria da empilhadora
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
	public void interact(GameElement gameElement) {
		// TODO Auto-generated method stub
		if (gameElement instanceof Empilhadora) {
			super.gameEngine.gameMap.updateElementPosition(gameElement, super.getPosition());// Move-se o objeto que
																								// interage com esta
																								// RoletaEnergia para a
																								// posição da mesma
			super.gui.update();// Chama-se a gui de modo a que a tela de jogo atualiza antes de ser mostrada a
								// tela do sorteio
			sortBattery();// Invoca-se o método sortBattery para sorteiar a energia
		}
	}

}
