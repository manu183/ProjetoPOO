package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Bateria extends Catchable {

	public static final String imageName = "Bateria";

	public Bateria(Point2D position) {
		super(position, imageName, 0);

	}
	
	@Override
	public void catchElement() {
		super.catchElement();
		//Adiciona 50 de energia à bateria da empilhadora
		super.gameEngine.bobcat.addBattery(50);
	}
}