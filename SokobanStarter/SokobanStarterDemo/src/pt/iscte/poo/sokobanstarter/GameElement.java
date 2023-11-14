package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile {
	GameEngine gameEngine = GameEngine.getInstance();
	
	
	//Criar um objeto filho de GameELement
	public static GameElement criateElement(char key, Point2D position) {
	
		
		switch(key) {
		case '#': return new Parede(position);
		case ' ': return new Chao(position);
		case '=': return new Vazio(position);
		case 'C': return new Caixote(position);
		case 'X': return new Alvo(position);
		case 'E': return new Empilhadora(position);
		case 'B': return new Bateria(position);
		case 'O': return new Buraco(position);
		case 'P': return new Palete(position);
		case '%': return new Parede(position);
		case 'T': return new Teleporte(position);
		default: throw new IllegalArgumentException(key + " is not recognizable");
		}
	}
	
	
	

	
	
	
	 
	
	// Calcula a posição final de um movimento
	public Point2D calculateFinalPosition(Point2D initialPosition, Direction direction) {
		Point2D finalPosition = initialPosition.plus(direction.asVector());

		return finalPosition;
	}
	
	
	//Devolve a arrayList que existe numa certa posição
	public List<ImageTile> imageTileInPosition(Point2D position) {
		List<ImageTile> result = new ArrayList<>();
		HashMap<Point2D, List<ImageTile>> map = gameEngine.getMap();
		if(map.containsKey(position)) {
			result = map.get(position);
			
		}
		return result;
	}
	
	public boolean existsInList(String className, List<ImageTile> list) {
		for(ImageTile atual : list) {
			if(atual.getName().equals(className)) {
				return true;
			}
		}
		return false;
	}
	

	public boolean isValidMove( Point2D initialPosition, Direction direction) {
		// TODO Auto-generated method stub
		//Verifica se existe algum obstáculo na direção que pretende que seja uma parede ou então dois caixotes seguidos
		Point2D finalPosition = calculateFinalPosition(initialPosition, direction);
		
		List<ImageTile> nextPosition = imageTileInPosition(finalPosition);
		//Verificar se a posição seguinte é uma parede
		if(existsInList("Parede",nextPosition)) {
			return false;
		}
		//Caso a nextPosition seja um caixote também é necessário calcular se a posição seguinte a esse caixote é
		//também um caixote. Se for, então não é permitido o movimento
		//ArraYlist que guarda o ArrayList da posição a seguir a nextPosition
		List<ImageTile> afterNextPosition = imageTileInPosition(calculateFinalPosition(finalPosition, direction));
		if(existsInList("Caixote", nextPosition) && existsInList("Caixote",afterNextPosition) || existsInList("Caixote", nextPosition) && existsInList("Parede",afterNextPosition)){
			return false;
		}
		
		return true;
		
	}
	
	@Override
	public String toString() {
		return getName();
	}

}
