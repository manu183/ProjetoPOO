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

		// Verifica se existe um caixote para a posição que deseja mover e
		// faz com que o objeto se mexa

		if (checkCaixote(nextPosition)) {
			// Obter o caixote do mapa
			GameElement caixote = super.gameEngine.gameMap.getSpecificElementAt(nextPosition, Caixote.imageName);
			// Criar o objeto Movable para poder mover o caixote
			Movable caixoteTemp = new Caixote(caixote.getPosition());
			caixoteTemp.move(direction);

		}
		// Verifica se o caixote ainda se encontra na posição de modo a que a
		// empilhadora
		// possa se mover para lá sem que o caixote esteja nessa posição

		if (!checkCaixote(nextPosition)) {
			super.move(direction);
		}

		// Atualizar a posição
		super.gameEngine.sycronizeTileList();

		// Atualiza o nível de energia
		battery_energy--;
		System.out.println("Energia na bateria:" + battery_energy);

		System.out.println("Wins level:" + super.gameEngine.gameMap.winsLevel());

	}

	public boolean checkCaixote(Point2D position) {
		return super.gameEngine.gameMap.existsOnPosition(position, Caixote.imageName);
	}

	@Override
	public boolean isValidMove(Point2D initialPosition, Direction direction) {
		if (battery_energy <= 0) {
			return false;
		}
		return super.isValidMove(initialPosition, direction);

	}

}