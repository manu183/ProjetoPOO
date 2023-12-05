package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends Movable {

	private static final int INITIAL_BATTERY_ENERGY = 100;
	private int battery_energy;
	private boolean hasMartelo;

	public static final String initialImageName = "Empilhadora_D";

	public Empilhadora(Point2D initialPosition) {
		super(initialPosition, initialImageName, 0);
		this.battery_energy = INITIAL_BATTERY_ENERGY;
		this.hasMartelo = false;
	}

	public int getBattery() {
		return battery_energy;
	}

	public void addBattery(int energy) {
		battery_energy += energy;
	}

	public boolean hasMartelo() {
		return hasMartelo;
	}

	public void addMartelo(boolean hasMartelo) {
		this.hasMartelo = hasMartelo;
	}

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
		// Redefine o imageName para a imagem da Empilhadora alterar
		updateImageName(direction);

		// Calcula o nova posição da empilhadora
		Point2D nextPosition = super.calculateFinalPosition(getPosition(), direction);
		// Obtém uma lista com os objetos existentes na posição seguinte
		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(nextPosition);
		GameElement element = null;
		for (GameElement actual : elements) {
			if (actual instanceof Catchable) {
				element = actual;
				break;

			} else if (actual instanceof ParedeRachada && hasMartelo == true) {
				element = actual;
				break;

			} else if (actual instanceof Movable && !actual.getTransposable()) {
				System.out.println("To move:" + actual);
				element = actual;
				break;
			}
		}
		if (element != null) {
			if (element instanceof Movable) {
				((Movable) element).move(direction);
				elements = super.gameEngine.gameMap.getElementsAt(nextPosition);
			}
			if (element instanceof Catchable)
				((Catchable) element).catchElement();
			if (element instanceof ParedeRachada)
				((Interectable) element).interact(this, nextPosition);
			;
		}

		// Chamo a função global que move objetos Movable, de modo a mover a Empilhadora
		super.move(direction);

	}

	@Override
	protected boolean isValidMove(Point2D finalPosition) {
		// Return falso caso a battery_energy-1, que corresponde à energia do próximo
		// movimento, seja menor do que 0 ou então se a isValidMove do Movable retornar
		// false
		if (battery_energy - 1 < 0 || !super.isValidMove(finalPosition))
			return false;
		// Reduz a battery_energy
		battery_energy--;

		// Cada vez que existe um movimento válido o score do gameEngine aumenta
		// de modo a registar o total de movimentações
		super.gameEngine.increaseScore();
		return true;
	}

}