package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Caixote extends Movable {

	private static final String imageName = "Caixote";
	private boolean onAlvo;

	public Caixote(Point2D position) {
		super(position, imageName, 0);
		this.onAlvo=false;
	}
	public void setOnAlvo(boolean value) {
		this.onAlvo=value;
	}
	
	
	@Override
	protected void move(Direction direction) {
		onAlvo=false;//Por defeito o onAlvo é false
		super.move(direction);//Invoca o método move da classe Movable
		//No método move, se o caixote estiver no alvo o onAlvo passa a ser true
		if(onAlvo==true) {
			super.setName("CaixoteAlvo");
		}else {
			super.setName("Caixote");
		}
		
	}
}