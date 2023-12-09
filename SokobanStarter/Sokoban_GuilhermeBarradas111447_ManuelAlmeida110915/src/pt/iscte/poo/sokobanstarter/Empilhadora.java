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

	// Metodo em que e possivel adicionar nivel de energia na bateria
	public void addBattery(int energy) {
		battery_energy += energy;
	}

	// Metodo onde e possível remover um nivel de energia na bateria
	public void decreaseBattery() {
		battery_energy--;
	}

	// Metodo onde e possivel verificar se a empilhadora tem martelo
	public boolean hasMartelo() {
		return hasMartelo;
	}

	// Metodo onde e possivel definir se a empilhadora tem martelo
	public void setMartelo(boolean hasMartelo) {
		this.hasMartelo = hasMartelo;
	}

	// Metodo que atualiza o name da Empilhadora, que varia consoante a sua direcao
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
	protected void move(Direction direction) {

		updateImageName(direction);// Redefine o imageName para a imagem da Empilhadora alterar

		Point2D nextPosition = super.calculateFinalPosition(getPosition(), direction);// Calcula o nova posicao da
																						// empilhadora

		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(nextPosition);// Obtem uma lista com os
																							// objetos existentes na
																							// posicao seguinte
		for (GameElement actual : elements) {
			if (actual instanceof Movable) {
				((Movable) actual).move(direction);
			} else if (actual instanceof Catchable) {
				((Catchable) actual).catchElement();
			}
		}

		super.move(direction);// Invoca-se a funcao global que move objetos Movable, de modo a mover a
								// Empilhadora
	}

	@Override
	protected boolean isValidMove(Point2D finalPosition) {
		// O metodo retorna falso caso a bateria depois do movimento seja menor que 0 ou entao se o metodo isValidMove da classe Movable retornar false
		if (battery_energy - 1 < 0 || !super.isValidMove(finalPosition)) {

			return false;
		}
		
		return true;
	}

}