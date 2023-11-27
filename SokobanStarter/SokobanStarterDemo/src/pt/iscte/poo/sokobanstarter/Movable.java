package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class Movable extends GameElement {

	public Movable(Point2D position, String name, int layer) {
		super(position, name, layer);

	}

	public static boolean isMovable(GameElement gameElement) {
		if (gameElement instanceof Movable) {
			return true;
		}
		return false;
	}

	public static Movable createMovable(char key, Point2D position) {
		switch (key) {
		case 'C':
			return new Caixote(position);
		case 'E':
			return new Empilhadora(position);
		case 'P':
			return new Palete(position);
		default:
			throw new IllegalArgumentException("Unrecognized key '" + key + "' for creating Movable element");
		}
	}

	public static Movable createMovable(String name, Point2D position) {

		switch (name) {
		case Caixote.imageName:
			return new Caixote(position);
		case Empilhadora.initialImageName:
			return new Empilhadora(position);
		case Palete.imageName:
			return new Palete(position);
		default:
			throw new IllegalArgumentException("Unrecognized name " + "name" + " for creating Movable element");
		}
	}

	public static Movable createMovable(GameElement gameElement) {

		switch (gameElement.getName()) {
		case Caixote.imageName:
			return new Caixote(gameElement.getPosition());
		case Empilhadora.initialImageName:
			return new Empilhadora(gameElement.getPosition());
		case Palete.imageName:
			return new Palete(gameElement.getPosition());
		default:
			throw new IllegalArgumentException(
					"Unrecognized name " + gameElement.getName() + " for creating Movable element");
		}
	}

	public void move(Direction direction) {
		System.out.println("Last GameElement:" + this);

		// Calcular a nova posição
		Point2D nextPosition = calculateFinalPosition(getPosition(),direction);
		// Verifica através da função isValidMove se o objeto se pode mover
		if (isValidMove(super.getPosition(), direction)) {
//			List<GameElement> elementsInPos = super.gameEngine.gameMap.getElementsAt(getPosition());

//			List<GameElement> nextElements = super.gameEngine.gameMap.getElementsAt(nextPosition);
//			System.out.println("This position Elements of " + getName() + ":" + elementsInPos);
//			System.out.println("Next Elements of " + getName() + ":" + nextElements);
			
			//1.Verifica-se se este elemento é uma palete de modo a que não possa ser removido
			//2.Verifica-se se no próxima posição existe um buraco e se não existe uma palete
			if (!(this instanceof Palete) && super.gameEngine.gameMap.containsOnPosition(new Buraco(nextPosition))
					&& !super.gameEngine.gameMap.containsOnPosition(new Palete(nextPosition))) {
				System.out.println("BURACO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				//Como o elemento cai num buraco o mesmo desaparece
				super.removeElement();
			}
			//Verificamos o caso de quando a próxima posição é um teleporte
			else if(super.gameEngine.gameMap.containsOnPosition(new Teleporte(nextPosition))){
				Teleporte teleporte = new Teleporte(nextPosition);
				//Obter a posição do outro teleporte
				Point2D teleportePos = teleporte.getOtherTeleportPosition();
				this.setPosition(teleportePos);
				
			}else {
				// Atualiza o elemento no mapa de jogo
				super.gameEngine.gameMap.updateElementPosition(this, nextPosition);
			}
//			System.out.println("GameMap:" + super.gameEngine.gameMap);
		} else {
			System.err.println("It was not possible to move  " + getName() + " to position" + nextPosition);
		}
//		System.out.println();
	}

	protected boolean isValidMove(Point2D initialPosition, Direction direction) {
		// Verifica se existe algum obstáculo na direção que pretende que seja uma
		// parede ou então dois caixotes seguidos
		Point2D nextPosition = calculateFinalPosition(initialPosition, direction);

		boolean isValidMove = true;

		// Obtém todos os gameElements que existem no nextPosition
		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(nextPosition);

		// Verifica se existe algum objeto de elements que não seja Transposable.
		// Verifica também se existe algum objeto que não esteja dentro do tabuleiro de
		// jogo.
		for (GameElement atual : elements) {
			if (!atual.getTransposable() || !isOnBoard(nextPosition)) {
				isValidMove = false;
			}
		}
		System.out.println("isValidMove:" + isValidMove);

		return isValidMove;

	}

	public boolean isOnBoard(Point2D nextPosition) {
		if (nextPosition.getX() < 0 && nextPosition.getX() >= 10 && nextPosition.getY() < 0
				&& nextPosition.getY() >= 10) {
			return false;
		}
		return true;
	}

	// Calcula a posição final de um movimento
	public Point2D calculateFinalPosition(Point2D initialPosition, Direction direction) {
		Point2D finalPosition = initialPosition.plus(direction.asVector());

		return finalPosition;
	}

	// Verifica se existe algum elemento numa certa posição, chamando a função do
	// GameMap
	public boolean checkElementAtPosition(Point2D position, String imageName) {
		return super.gameEngine.gameMap.existsOnPosition(position, imageName);
	}

}
