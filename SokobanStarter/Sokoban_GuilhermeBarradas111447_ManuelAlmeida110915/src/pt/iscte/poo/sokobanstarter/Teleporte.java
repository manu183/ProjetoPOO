package pt.iscte.poo.sokobanstarter;

import java.util.List;

import pt.iscte.poo.utils.Point2D;

public class Teleporte extends GameElement {

	private static final String imageName = "Teleporte";

	public Teleporte(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);
	}
	
	public Teleporte getOtherTeleporte() {
		
		//Obter todos os Teleportes que se encontram no mapa
		List<GameElement> elements = super.gameEngine.gameMap.getAllGameElement(this);
//		System.out.println("Teleportes:"+elements);
		Teleporte teleportePar = null;
		//Obter o teleporte par
		for(GameElement actual:elements) {
			if(!(actual.getPosition().equals(super.getPosition()))) {
				teleportePar=((Teleporte)actual);
				break;
			}
		}
		
//		System.out.println("Teleporte par:"+teleportePar);

		return teleportePar;
	}
	
	public boolean isAvaible() {
		//Obter elementos que se ocupam o teleporte par
		List<GameElement> elements = super.gameEngine.gameMap.getElementsAt(super.getPosition());
		System.out.println("Teleporte position elements:"+elements);
		for(GameElement actual:elements) {
			if(!actual.getTransposable()) {
				return false;
			}
		}
		System.out.println("isAvaible:True");
		return true;
	}
	
	public void teleportElement(GameElement gameElement) {
	
	}
	

}
