package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class Movable extends GameElement {

	public Movable(Point2D position, String name, int layer) {
		super(position, name, layer);

	}

	// Método isValidMove que indica se um movimento é válido ou não
	protected boolean isValidMove(Point2D finalPosition) {
		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(finalPosition);
		System.out.println("THIS:" + this);
		for (GameElement current : elements) {
			if (!current.getTransposable() || !isOnBoard(finalPosition) || super.getTransposable()) {
				System.out.println("isValidMove:false");
				return false;
			}
		}
		System.out.println("isValidMove:true");
		return true;
	}

	// Método move que implementação a movimentação dos objetos Movable
	protected void move(Direction direction) {
		// Calcular a nova posição
		Point2D nextPosition = calculateFinalPosition(getPosition(), direction);
		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(nextPosition);
		
		GameElement next = null;
		for (GameElement actual : elements) {
			if (actual instanceof Interectable) {
				next = actual;
				break;
			}
		}
		if (next != null) {
			if (next instanceof Interectable) {
				System.out.println("Interectable detected!!!!!");
				((Interectable) next).interact(this);
				super.gameEngine.bobcat.decreaseBattery();// Reduzir a energia da bateria da empilhadora

			}

		} else if (isValidMove(nextPosition)) {
			System.out.println("Mover");
			super.gameEngine.bobcat.decreaseBattery();// Reduzir a energia da bateria da empilhadora
			super.gameEngine.gameMap.updateElementPosition(this, nextPosition); //Atualiza a posição deste Movable, atualizando o gameMap com esta alteração
		}

	}
	
	//Método que verifica se a nextPosition fica dentro dos limites do tabuleiro de jogo
	public boolean isOnBoard(Point2D nextPosition) {
		if (nextPosition.getX() >= 0 && nextPosition.getX() < 10 && nextPosition.getY() >= 0
				&& nextPosition.getY() < 10) {
			return true;
		}
		return false;
	}

	// Método que calcula a posição final de um movimento
	public Point2D calculateFinalPosition(Point2D initialPosition, Direction direction) {
		Point2D finalPosition = initialPosition.plus(direction.asVector());

		return finalPosition;
	}

}