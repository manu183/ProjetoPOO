package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends Movable {
	private ImageMatrixGUI gui=ImageMatrixGUI.getInstance();
	
	public static final String initialImageName = "Empilhadora_D";
	

	public Empilhadora(Point2D initialPosition) {
		super(initialPosition, initialImageName, 0);

	}
	
	
	
	@Override
	public void move(Direction direction) {
		
		//Redefine o imageName para a imagem da Empilhadora alterar
		if (direction == Direction.RIGHT) {
			super.setName("Empilhadora_R");
			
		} else if (direction ==Direction.LEFT) {
			super.setName("Empilhadora_L");
			
		} else if (direction == Direction.DOWN) {
			super.setName("Empilhadora_D");
			
		} else if (direction == Direction.UP) {
			super.setName("Empilhadora_U");
			
		}
		Point2D nextPosition =super.calculateFinalPosition(getPosition(), direction);

		
		//Verifica se existe um caixote para a posição que deseja mover e faz com que o objeto se mexa
		if(checkCaixote(nextPosition)) {
			//Obter o caixote do mapa
			GameElement caixote = super.gameEngine.gameMap.getSpecificElementAt(nextPosition, Caixote.imageName);
			//Criar o objeto Movable para poder mover o caixote
			Movable caixoteTemp = new Caixote(caixote.getPosition());				
			caixoteTemp.move(direction);
			
		}
		//Verifica se o caixote ainda se encontra na posição de modo a que a empilhadora 
		//possa se mover para lá sem que o caixote esteja nessa posição
		
		if(!checkCaixote(nextPosition)) {
			super.move(direction);			
		}
		System.out.println("Move empilhadora!!!");
		System.out.println();
		System.out.println();
		
		// Atualizar a posição
		super.gameEngine.sycronizeTileList();
		
		gui.setStatusMessage("Empilhadore andou para" +getPosition());
		
	}
	
	public boolean checkCaixote(Point2D position) {
		return super.gameEngine.gameMap.existsOnPosition(position, Caixote.imageName);
	}
	
	
	
	
}