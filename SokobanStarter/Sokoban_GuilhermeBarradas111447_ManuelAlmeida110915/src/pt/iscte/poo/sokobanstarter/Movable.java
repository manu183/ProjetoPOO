package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class Movable extends GameElement {

	public Movable(Point2D position, String name, int layer) {
		super(position, name, layer);

	}

	public static boolean isMovable(GameElement gameElement) {
	    return gameElement instanceof Movable;
	}

	
	protected boolean isValidMove(Point2D finalPosition) {
        List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(finalPosition);

        for (GameElement current : elements) {
            if (!current.getTransposable() || !isOnBoard(finalPosition)) {
                System.out.println("isValidMove:false");
            	return false;
            }
        }
        System.out.println("isValidMove:true");
        return true;
    }
	
	protected void checkMovablesToMove(Direction direction) {
		Point2D nextPosition = calculateFinalPosition(getPosition(), direction);
		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(nextPosition);

		GameElement next = null;

		//TODO Adicionar um break em cada if
		for (GameElement actual : elements) {
			if (actual instanceof Movable) {
				if(!actual.getTransposable()) {
					next = actual;					
				}
			}

		}

		if (next != null) {
			if (next instanceof Movable && !next.getTransposable()) {
				System.out.println("Movable:"+next);
				((Movable) next).move(direction);
				System.out.println("Fi");
			}
		}
	}
	
	protected void move(Direction direction) {
		// Calcular a nova posição
		Point2D nextPosition = calculateFinalPosition(getPosition(), direction);
		checkMovablesToMove(direction);
		// Verifica através da função isValidMove se o objeto se pode mover
		if (isValidMove(nextPosition)) {
			if (!(this instanceof Palete) && super.gameEngine.gameMap.containsOnPosition(new Buraco(nextPosition))
					&& !super.gameEngine.gameMap.containsOnPosition(new Palete(nextPosition))) {
				// Como o elemento cai num buraco o mesmo desaparece
				super.removeElement();
			}
			// Verificamos o caso de quando a próxima posição é um teleporte
			else if (super.gameEngine.gameMap.containsOnPosition(new Teleporte(nextPosition))) {
				Teleporte teleporte = new Teleporte(nextPosition);
				teleporte.teleporte(this, nextPosition);

			} else {
				// Atualiza o elemento no mapa de jogo
				super.gameEngine.gameMap.updateElementPosition(this, nextPosition);
			}

		} else {
			System.err.println("It was not possible to move  " + getName() + " to position" + nextPosition);
		}

	}

	
	public boolean isOnBoard(Point2D nextPosition) {
		if (nextPosition.getX() >= 0 && nextPosition.getX() < 10 && nextPosition.getY() >= 0
				&& nextPosition.getY() < 10) {
			return true;
		}
		return false;
	}

	// Calcula a posição final de um movimento
	public Point2D calculateFinalPosition(Point2D initialPosition, Direction direction) {
		Point2D finalPosition = initialPosition.plus(direction.asVector());

		return finalPosition;
	}

}
