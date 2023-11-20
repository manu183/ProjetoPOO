package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends Movable {
	
	private int battery_energy;

	public static final String initialImageName = "Empilhadora_D";

	public Empilhadora(Point2D initialPosition) {
		super(initialPosition, initialImageName, 0);
		this.battery_energy = 75;

	}
	
	public int getBattery() {
		return battery_energy;
	}
	
	public void addBattery(int energy) {
		battery_energy += energy;
	}

	@Override
	public void move(Direction direction) {
		// Redefine o imageName para a imagem da Empilhadora alterar
		if (direction == Direction.RIGHT) {
			super.setName("Empilhadora_R");

		} else if (direction == Direction.LEFT) {
			super.setName("Empilhadora_L");

		} else if (direction == Direction.DOWN) {
			super.setName("Empilhadora_D");

		} else if (direction == Direction.UP) {
			super.setName("Empilhadora_U");

		}

		// Calcula o nova posição da empilhadora
		Point2D nextPosition = super.calculateFinalPosition(getPosition(), direction);

		// Vê se existe alguma bateria
		if (checkElementAtPosition(nextPosition, Bateria.imageName)) {
			addBattery(10);
			GameElement bateria = super.gameEngine.gameMap.getSpecificElementAt(nextPosition, Bateria.imageName);
			super.gameEngine.gameMap.removeElement(bateria);
			System.out.println("Battery energy: " + battery_energy);
		}

		// Verifica se existe um caixote para a posição que deseja mover e
		// faz com que o objeto se mexa
		if (checkElementAtPosition(nextPosition, Caixote.imageName)) {
			// Obter o caixote do mapa
			GameElement caixote = super.gameEngine.gameMap.getSpecificElementAt(nextPosition, Caixote.imageName);
			// Criar o objeto Movable para poder mover o caixote
			Caixote caixoteTemp = new Caixote(caixote.getPosition());
			caixoteTemp.move(direction);

		}
		// Verifica se o caixote ainda se encontra na posição de modo a que a
		// empilhadora possa se mover para lá sem que o caixote esteja nessa posição

		if (!checkElementAtPosition(nextPosition, Caixote.imageName)) {
			super.move(direction);
			// Atualiza o nível de energia
			if (battery_energy - 1 >= 0 && isValidMove(getPosition(), direction)) {
				battery_energy--;
			}
			System.out.println("Battery energy:" + battery_energy);
		}

		// Atualizar a posição
		super.gameEngine.sycronizeTileList();

		


		System.out.println("Wins level:" + super.gameEngine.gameMap.winsLevel());

//		gui.setStatusMessage("Battery energy:" + battery_energy);
	}

	@Override
	public boolean isValidMove(Point2D initialPosition, Direction direction) {
		if (battery_energy <= 0) {
			return false;
		}
		return super.isValidMove(initialPosition, direction);

	}

}