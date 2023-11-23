package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public abstract class Catchable extends GameElement {
	public Catchable(Point2D position, String name, int layer) {
		super(position, name, layer);

	}

	public static Catchable createCatchable(char key, Point2D position) {

		switch (key) {
		case 'B':
			return new Bateria(position);
		case 'M':
			return new Martelo(position);
		default:
			throw new IllegalArgumentException("Unrecognized key '" + key + "' for creating Movable element");
		}
	}

	public static Catchable createMovable(String name, Point2D position) {

		switch (name) {
		case Bateria.imageName:
			return new Bateria(position);

		case Martelo.imageName:
			return new Martelo(position);
		default:
			throw new IllegalArgumentException("Unrecognized key '" + name + "' for creating Movable element");
		}
	}

	public static Catchable createCatchable(GameElement gameElement) {

		switch (gameElement.getName()) {
		case Bateria.imageName:
			return new Bateria(gameElement.getPosition());

		case Martelo.imageName:
			return new Martelo(gameElement.getPosition());
		default:
			throw new IllegalArgumentException(
					"Unrecognized key '" + gameElement.getName() + "' for creating Movable element");
		}
	}

	public static boolean isCachable(GameElement gameElement) {
		if (gameElement instanceof Catchable) {
			return true;
		}
		return false;
	}

	public void catchElement() {
		removeElement();
	}

}
