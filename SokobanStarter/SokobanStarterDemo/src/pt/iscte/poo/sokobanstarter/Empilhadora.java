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
//		System.out.println("Elementos da posição:"+elements);

		// Verifica se os objetos na posição seguinte é Cachable
		Catchable cat = null;
		for (GameElement actual : elements) {
			if (Catchable.isCachable(actual)) {
				cat = Catchable.createCatchable(actual);
				System.out.println("Chatchable:" + cat);
			}
		}
		if (cat != null) {
			cat.catchElement();
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

		// Verifica se existe um buraco e caso exista acaba o jogo

		Buraco buraco = new Buraco(nextPosition);
		Palete palete = new Palete(nextPosition);
		if (super.gameEngine.gameMap.containsOnPosition(buraco) && !super.gameEngine.gameMap.containsOnPosition(palete)) {
			gui.setStatusMessage("gg");
			gui.setMessage("Lost the level");

		}


		// verifica se existe uma parede rachada e se já foi apanhado o martelo
		ParedeRachada p = null;
		for (GameElement actual : elements) {
			if (actual instanceof ParedeRachada && hasMartelo == true) {
				actual.setTransposable(true);
				p = new ParedeRachada(actual.getPosition());
			}
		}
		if (p != null) {
			p.breakElement();
		}
		
		
		

		// Chamo a função global que move objetos Movable
		super.move(direction);
		
		

	}

	@Override
	protected boolean isValidMove(Point2D initialPosition, Direction direction) {
		// Return falso caso a battery_energy-1, que corresponde à energia do próximo
		// movimento, seja menor do que 0 ou então se a isValidMove do Movable retornar
		// false
		if (battery_energy - 1 < 0 || !super.isValidMove(getPosition(), direction))
			return false;
		// Reduz a battery_energy
		battery_energy--;
		
		//Cada vez que existe um movimento válido o score do gameEngine aumenta
		//de modo a registar o total de movimentações
		super.gameEngine.increaseScore();
		return true;
	}

}