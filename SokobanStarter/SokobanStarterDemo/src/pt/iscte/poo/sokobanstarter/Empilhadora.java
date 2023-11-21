package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends Movable {

	private int battery_energy;
	private boolean hasMartelo;

	public static final String initialImageName = "Empilhadora_D";

	public Empilhadora(Point2D initialPosition) {
		super(initialPosition, initialImageName, 0);
		this.battery_energy = 75;
		this.hasMartelo = false;

	}

	public int getBattery() {
		return battery_energy;
	}

	public void addBattery(int energy) {
		battery_energy += energy;
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
		System.out.println(elements);

		// Verifica se os objetos na posição seguinte é Cachable
		Catchable cat = null;
		for (GameElement actual : elements) {
			if (Catchable.isCachable(actual)) {
				cat = Catchable.createCatchable(actual);
				System.out.println("Chatchable:" + cat);
			}
		}
		if (cat != null) {
			cat.action();
		}

		// Verifica se existe um objeto movable na próxima posição
		Movable mov = null;
		for (GameElement actual : elements) {
			if (Movable.isMovable(actual)) {
				mov = Movable.createMovable(actual);
				System.out.println("Movable:" + mov);
			}
		}
		if (mov != null) {
			mov.move(direction);
		}

		// Chamo a função global que move objetos Movable
		super.move(direction);

	}

}