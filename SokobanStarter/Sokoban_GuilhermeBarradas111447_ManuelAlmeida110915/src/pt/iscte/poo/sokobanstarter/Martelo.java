package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Martelo extends Catchable {

	private static final String imageName = "Martelo";

	public Martelo(Point2D position) {
		super(position, imageName, 0);
	}
	
	@Override
	public void catchElement() {
		super.catchElement();//Invoca o metodo catchElement da classe Catchable que apanha este elemento
		super.gameEngine.bobcat.setMartelo(true);//Como o martelo foi apanhado entao o atributo hasMartelo da empilhadora passa a ter o valor true
	}

}