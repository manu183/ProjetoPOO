package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Buraco extends GameElement implements Interectable {

	public static final String imageName = "Buraco";

	public Buraco(Point2D position) {
		super(position, imageName, 0);
		super.setTransposable(true);
	}

	@Override
	public void interact(GameElement gameElement) {
		System.out.println("Buraco!!!!!");
		Palete palete = new Palete(super.getPosition());
		if (!(gameElement instanceof Palete) && !super.gameEngine.gameMap.exists(palete)) {
			System.out.println("Existe uma palete no buraco");
			gameElement.removeElement();
			System.out.println("Should have removed "+gameElement);
		} 
		else {
			super.gameEngine.gameMap.updateElementPosition(gameElement, super.getPosition());
		}
	}

	
}