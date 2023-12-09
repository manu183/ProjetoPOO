package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Bateria extends Catchable {

	private static final String imageName = "Bateria";

	public Bateria(Point2D position) {
		super(position, imageName, 0);

	}

	@Override
	public void catchElement() {
		super.catchElement();// Invoca o metodo catchElement da classe Catchable que apanha este elemento
		super.gameEngine.bobcat.addBattery(50);// Adiciona 50 de energia na bateria da empilhadora
	}
}