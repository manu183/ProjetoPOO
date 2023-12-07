package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Sorteio extends GameElement implements Interectable{
	public static final String imageName = "Sorteio";

	protected Sorteio(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);
	}
	
	//Sorteiar e alterar o nível de energia da bateria da empilhadora
	private void sortBattery() {
		int sorted = (int) (Math.random() * (101)) - 50;
		if(sorted>0) {
			gui.setMessage("Ganhou "+sorted+" energias!");
		}else if(sorted<0) {
			gui.setMessage("Perdeu "+Math.abs(sorted)+" energias!");
		}
		super.gameEngine.bobcat.addBattery(sorted);
	}

	@Override
	public void interact(GameElement gameElement, Point2D nextPosition) {
		// TODO Auto-generated method stub
		System.out.println("Sorteio!!!!!!!!!");
		sortBattery();
//		super.gameEngine.gameMap.updateElementPosition(gameElement, nextPosition);
	}

}
