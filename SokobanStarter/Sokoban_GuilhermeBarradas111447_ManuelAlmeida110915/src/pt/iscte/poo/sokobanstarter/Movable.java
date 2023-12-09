package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class Movable extends GameElement {

	public Movable(Point2D position, String name, int layer) {
		super(position, name, layer);

	}

	// Metodo isValidMove que indica se um movimento e valido ou nao
	protected boolean isValidMove(Point2D finalPosition) {
		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(finalPosition);
		for (GameElement current : elements) {
			if (!current.getTransposable() || !isOnBoard(finalPosition) || super.getTransposable()) {
				return false;
			}
		}
		return true;
	}

	// Metodo move que implementacao a movimentacao dos objetos Movable
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
				nextPosition=((Interectable) next).interact(this);
				if(!super.getPosition().equals(nextPosition)) {
					super.gameEngine.bobcat.decreaseBattery();// Reduzir a energia da bateria da empilhadora					
					if(isValidMove(nextPosition))
						super.gameEngine.gameMap.updateElementPosition(this, nextPosition);
					super.gameEngine.increaseScore();
				}
				

			}

		} else if (isValidMove(nextPosition)) {
			super.gameEngine.bobcat.decreaseBattery();// Reduzir a energia da bateria da empilhadora
			super.gameEngine.gameMap.updateElementPosition(this, nextPosition); //Atualiza a posicao deste Movable, atualizando o gameMap com esta alteracao
			super.gameEngine.increaseScore();
		}

	}
	
	//Metodo que verifica se a nextPosition fica dentro dos limites do tabuleiro de jogo
	public boolean isOnBoard(Point2D nextPosition) {
		if (nextPosition.getX() >= 0 && nextPosition.getX() < 10 && nextPosition.getY() >= 0
				&& nextPosition.getY() < 10) {
			return true;
		}
		return false;
	}

	// Metodo que calcula a posicao final de um movimento
	public Point2D calculateFinalPosition(Point2D initialPosition, Direction direction) {
		return initialPosition.plus(direction.asVector());

	}

}