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

	public void move(Direction direction) {
//		System.out.println("Last GameElement:" + this);

		// Calcular a nova posição
		Point2D nextPosition = calculateFinalPosition(getPosition(), direction);
		// Verifica através da função isValidMove se o objeto se pode mover
		if (isValidMove(nextPosition)) {

			// 1.Verifica-se se este elemento é uma palete de modo a que não possa ser
			// removido
			// 2.Verifica-se se no próxima posição existe um buraco e se não existe uma
			// palete
			if (!(this instanceof Palete) && super.gameEngine.gameMap.containsOnPosition(new Buraco(nextPosition))
					&& !super.gameEngine.gameMap.containsOnPosition(new Palete(nextPosition))) {
				System.out.println("BURACO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				// Como o elemento cai num buraco o mesmo desaparece
				super.removeElement();
			}
			// Verificamos o caso de quando a próxima posição é um teleporte
			else if (super.gameEngine.gameMap.containsOnPosition(new Teleporte(nextPosition))) {
				System.out.println("Teleporte!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				Teleporte teleporte = new Teleporte(nextPosition);
				// Obter a posição do outro teleporte
				Point2D teleportePos = teleporte.getOtherTeleportPosition();
//				this.setPosition(teleportePos);

				// Só é possível entrar no teleporte se não se encontrar que não seja
				// transposable no teleporte par
				if (isValidMove(nextPosition)) {
					super.gameEngine.gameMap.updateElementPosition(this, teleportePos);
				}
			} else {
				// Atualiza o elemento no mapa de jogo
				super.gameEngine.gameMap.updateElementPosition(this, nextPosition);
			}
//			System.out.println("GameMap:" + super.gameEngine.gameMap);
		} else {
			System.err.println("It was not possible to move  " + getName() + " to position" + nextPosition);
		}
	}

	protected boolean isValidMove(Point2D finalPosition) {
		// Verifica se existe algum obstáculo na direção que pretende que seja uma
		// parede ou então dois caixotes seguidos

		boolean isValidMove = true;

		// Obtém todos os gameElements que existem no nextPosition
		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(finalPosition);
//		System.out.println("Next Elements:"+elements);
		// Verifica se existe algum objeto de elements que não seja Transposable.
		// Verifica também se existe algum objeto que não esteja dentro do tabuleiro de
		// jogo.
		for (GameElement atual : elements) {
			if (!atual.getTransposable() || !isOnBoard(finalPosition)) {
				isValidMove = false;
				System.out.println("Atual:" + atual);
				System.out.println("!Transposable:" + !atual.getTransposable());
				System.out.println("isInboard:" + !isOnBoard(finalPosition));
				System.out.println("aqui");
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

}
