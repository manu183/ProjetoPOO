package pt.iscte.poo.sokobanstarter;

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

		// Vê se existe alguma bateria na próxima posição
		if (checkElementAtPosition(nextPosition, Bateria.imageName)) {
			addBattery(10);
			GameElement bateria = super.gameEngine.gameMap.getSpecificElementAt(nextPosition, Bateria.imageName);
			super.gameEngine.gameMap.removeElement(bateria);
		}
		
		// Vê se existe algum Martelo na próxima posição
		if (checkElementAtPosition(nextPosition, Martelo.imageName)) {
			GameElement martelo = super.gameEngine.gameMap.getSpecificElementAt(nextPosition, Martelo.imageName);
			super.gameEngine.gameMap.removeElement(martelo);
			this.hasMartelo=true;
		}
		
		

		// Verifica se existe um caixote para a posição que deseja mover e
		// faz com que o objeto se mexa
		if (checkElementAtPosition(nextPosition, Caixote.imageName)) {
			// Obter o caixote da próxima posição
			GameElement caixote = super.gameEngine.gameMap.getSpecificElementAt(nextPosition, Caixote.imageName);
			// Criar o objeto Caixote para poder mover o caixote
			Caixote caixoteTemp = new Caixote(caixote.getPosition());
			// Mover o caixote
			caixoteTemp.move(direction);
		}

		// Verifica se o caixote ainda se encontra na posição de modo a que a
		// empilhadora possa se mover para lá sem que o caixote esteja nessa posição
		if (!checkElementAtPosition(nextPosition, Caixote.imageName)
				&& !checkElementAtPosition(nextPosition, Parede.imageName)) {
			super.move(direction);
			// Atualiza o nível de energia
			if (battery_energy - 1 >= 0) {
				battery_energy--;
			}
			System.out.println("Battery energy:" + battery_energy);
			
			//Verifica se existe um buraco na próxima posição de modo a que se haja o user perca o jogo
			if(checkElementAtPosition(nextPosition, Buraco.imageName)) {
				GameElement empilhadora = super.gameEngine.gameMap.getSpecificElementAt(getPosition(), initialImageName);
				System.out.println("Perdeste o jogo!!!!!!!!!!!!!!!!!!");
				super.gameEngine.gameMap.removeElement(empilhadora);
			}
		}

		// Atualizar a posição
		super.gameEngine.sycronizeTileList();
		System.out.println("Has Martelo:"+ hasMartelo);
		System.out.println("Wins level:" + super.gameEngine.gameMap.winsLevel());

	}

	@Override
	public boolean isValidMove(Point2D initialPosition, Direction direction) {
		if (battery_energy <= 0) {
			return false;
		}
		return super.isValidMove(initialPosition, direction);

	}

}