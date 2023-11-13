package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile {
	GameEngine gameEngine = GameEngine.getInstance();
	public GameElement(Point2D position, String imageName) {
		List<String> elementos = new ArrayList<>();
		elementos.add(imageName);
		gameEngine.map.put(position, (ArrayList<String>) elementos);
		//System.out.println(map);
		
	}


	public void move(Direction direction) {
	}

	public void move(Direction direction, Point2D newPosition) {
		// TODO Auto-generated method stub
		gameEngine.updateHashMap(getPosition(), newPosition, getName());
	}
	


	// Calcula a posição final de um movimento
	public Point2D calculateFinalPosition(Point2D initialPosition, Direction direction) {
		Point2D finalPosition = initialPosition.plus(direction.asVector());

		return finalPosition;
	}

	public boolean isValidMove( Point2D initialPosition, Direction direction) {
		// TODO Auto-generated method stub
		//Verifica se existe algum obstáculo na direção que pretende que seja uma parede ou então dois caixotes seguidos
		Point2D finalPosition = calculateFinalPosition(initialPosition, direction);
		
		//Ver se a posição final é parede
		if(gameEngine.map.containsKey(finalPosition)) {
			ArrayList<String> elementos = gameEngine.map.get(finalPosition);
			if(elementos.contains("Parede")) {
				return false;
			}
		}
		
		return true;
		
	}

}
