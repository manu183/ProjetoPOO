package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Point2D;

public class Teleporte extends GameElement {

	private static final String imageName = "Teleporte";

	public Teleporte(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);
	}
	
	public Point2D getOtherTeleportPosition() {
		System.out.println("Este teleporte"+this);
		//Obter todos os Teleportes que se encontram no mapa
		List<GameElement> elements = super.gameEngine.gameMap.getAllGameElement(this);
		
		Point2D res =null;
		GameElement teleportePar = null;
		//Obter o teleporte par
		for(GameElement actual:elements) {
			if(!(actual.getPosition().equals(this.getPosition()))) {
				teleportePar=actual;
				break;
			}
		}
		
		System.out.println("Teleporte par:"+teleportePar);
		res=teleportePar.getPosition();
		return res;
	}
	

}
