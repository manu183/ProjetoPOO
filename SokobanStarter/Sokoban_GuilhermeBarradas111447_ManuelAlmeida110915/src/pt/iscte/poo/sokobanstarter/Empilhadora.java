package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends Movable {

	private static final int INITIAL_BATTERY_ENERGY = 100; // Define a energia inicial da bateria
	private int battery_energy; // Atributo que armazena o valor atual da energia da bateria
	private boolean hasMartelo; // Atributo que guarda se atualmente a empilhadora possui um martelo

	private static final String initialImageName = "Empilhadora_D";

	public Empilhadora(Point2D initialPosition) {
		super(initialPosition, initialImageName, 0);
		this.battery_energy = INITIAL_BATTERY_ENERGY;
		this.hasMartelo = false;
	}

	public int getBattery() {
		return battery_energy;
	}

	// Método em que é possível adicionar nível de energia à bateria
	public void addBattery(int energy) {
		battery_energy += energy;
	}

	// Método onde é possível remover um nível de energia à bateria
	public void decreaseBattery() {
		battery_energy--;
	}

	// Método onde é possível verificar se a empilhadora tem martelo
	public boolean hasMartelo() {
		return hasMartelo;
	}

	// Método onde é possível definir se a empilhadora tem martelo
	public void setMartelo(boolean hasMartelo) {
		this.hasMartelo = hasMartelo;
	}

	// Método que atualiza o name da Empilhadora, que varia consoante a sua direção.
	private void updateImageName(Direction direction) {
		switch (direction) {
		case RIGHT:
			super.setName("Empilhadora_R");
			break;
		case LEFT:
			super.setName("Empilhadora_L");
			break;
		case DOWN:
			super.setName("Empilhadora_D");
			break;
		case UP:
			super.setName("Empilhadora_U");
			break;
		}
	}

	@Override
	public void move(Direction direction) {

		updateImageName(direction);// Redefine o imageName para a imagem da Empilhadora alterar

		Point2D nextPosition = super.calculateFinalPosition(getPosition(), direction);// Calcula o nova posição da
																						// empilhadora

		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(nextPosition);// Obtém uma lista com os
																							// objetos existentes na
																							// posição seguinte

		GameElement next = null;
		for (GameElement actual : elements) {
			if (actual instanceof Movable) {
				System.out.println("To move:" + actual);
				next = actual;
				((Movable) next).move(direction);
			} else if (actual instanceof Catchable) {
				next = actual;
				((Catchable) next).catchElement();
			}
		}

		// Invoca-se a função global que move objetos Movable, de modo a mover a
		// Empilhadora
		super.move(direction);
	}

	@Override
	protected boolean isValidMove(Point2D finalPosition) {
		// A função retorna falso caso a bateria
		if (battery_energy - 1 < 0 || !super.isValidMove(finalPosition))
			return false;
		// Reduz a battery_energy

		// Cada vez que existe um movimento válido o score do gameEngine aumenta
		// de modo a registar o total de movimentações
		super.gameEngine.increaseScore();
		return true;
	}

}