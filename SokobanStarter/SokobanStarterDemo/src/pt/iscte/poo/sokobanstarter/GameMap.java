package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class GameMap implements Comparator<Point2D> {
	private Map<Point2D, List<GameElement>> map;
	private static GameMap INSTANCE;

	private GameMap() {
		map = new HashMap<>();
	}

	// Implementacao do singleton para o GameMap
	public static GameMap getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new GameMap();
		return INSTANCE;
	}

	// Adicionar um gameElement
	public void addElement(GameElement gameElement) {
		// Verifica se um objeto já exista nessa posição de modo a não haver dois
		// GameElements repetidos na mesma posição
		if (!existsOnPosition(gameElement)) {
			List<GameElement> elements = getElementsAt(gameElement.getPosition());
			elements.add(gameElement);
			map.put(gameElement.getPosition(), elements);
		}

	}

	public void removeElement(GameElement gameElement) {
		Point2D position = gameElement.getPosition();
		List<GameElement> elements = getElementsAt(position);

		Iterator<GameElement> iterator = elements.iterator();
		while (iterator.hasNext()) {
			GameElement actual = iterator.next();
			if (actual.getName().equals(gameElement.getName())) {
				iterator.remove();
				break;
			}
		}

		// Atualizar o arraylist
		map.put(position, elements);
	}

	public void updateElementPosition(GameElement gameElement, Point2D newPosition) {
		// Remove o elemento antigo
		removeElement(gameElement);
		// Atualiza a posição do elemento
		gameElement.setPosition(newPosition);
		// Adiciona o elemento com a nova posição
		addElement(gameElement);
	}

	public void deleteAll() {
		map.clear();
	}

	public List<GameElement> getElementsAt(Point2D position) {
		List<GameElement> elements = map.get(position);

		if (elements == null) {
			elements = new ArrayList<>();
		}

		return elements;
	}

	public GameElement getSpecificElementAt(GameElement gameElement) {
		if (existsOnPosition(gameElement)) {
			List<GameElement> elements = map.get(gameElement.getPosition());
			for (GameElement actual : elements) {
				if (actual.getName().equals(gameElement.getName())) {
					gameElement = actual;
					break;
				}
			}
		} else {
			throw new IllegalArgumentException("There isn't no such object type in this position!");
		}

		return gameElement;

	}

	public boolean existsOnPosition(Point2D position, String imageName) {
		List<GameElement> elements = getElementsAt(position);
		for (GameElement actual : elements) {
			if (actual.getName().equals(imageName)) {
				return true;
			}
		}
		return false;
	}

	public boolean existsOnPosition(GameElement gameElement) {
		List<GameElement> elements = getElementsAt(gameElement.getPosition());
		for (GameElement actual : elements) {
			if (actual.getClass().equals(gameElement.getClass())) {
				return true;
			}
		}
		return false;
	}
	
	//Verificação que verifica se o nível foi ganho, ou seja, se 
	public boolean winsLevel() {
		boolean res = true;
		List<GameElement> elements = convertToArrayList();

		// Um arrayList que contém todas as posições que contém um alvo
		List<Point2D> target_positions = new ArrayList<>();

		for (GameElement actual : elements) {
			if (actual.getName().equals(Alvo.imageName)) {
				target_positions.add(actual.getPosition());
			}
		}
		// Verificar se existe algum posição alvo que não contém um caixote
		for (Point2D actual : target_positions) {
			if (!existsOnPosition(actual, Caixote.imageName)) {
				res = false;
			}
		}
		return res;
	}

	// Este método serve para obter um ArrayList de modo a que seja possível
	// atualizar a GUI, que recebe objetos ImageTile
	public List<ImageTile> arrayToGUI() {
		List<ImageTile> allElements = new ArrayList<>();
		List<Point2D> keys = new ArrayList<>(map.keySet());
		keys.sort((o1, o2) -> compare(o1, o2));
		for (Point2D actual : keys) {
			List<GameElement> gameElements = getElementsAt(actual);
			allElements.addAll(gameElements);
		}
		return allElements;
	}
	
	//Retorna uma lista com todos os GameElements
	public List<GameElement> convertToArrayList() {
		List<GameElement> allElements = new ArrayList<>();
		List<Point2D> keys = new ArrayList<>(map.keySet());
		keys.sort((o1, o2) -> compare(o1, o2));
		for (Point2D actual : keys) {
			List<GameElement> gameElements = getElementsAt(actual);
			allElements.addAll(gameElements);
		}
		return allElements;
	}

	@Override
	public String toString() {
		String res = "";
		List<Point2D> keys = new ArrayList<>(map.keySet());
		// Ordenar a lista de pontos
		// i é o iterador para se poder ver até quando se deve imprimir ' | '
		int i = 0;
		int size = keys.size();
		keys.sort((o1, o2) -> compare(o1, o2));
		for (Point2D actual : keys) {
			List<GameElement> gameElements = getElementsAt(actual);
			res += "(" + actual.getX() + ", " + actual.getY() + ")-> ";

			// j é o iterador para se poder ver até quando se deve imprimir ', '
			int j = 0;
			int elementsSize = gameElements.size();
			for (GameElement element : gameElements) {
				res += element.getName();
				if (j != elementsSize - 1)
					res += ", ";
				j++;
			}
			if (i != size - 1)
				res += " | ";
			i++;
		}
		return res;
	}

	@Override
	public int compare(Point2D o1, Point2D o2) {
		// TODO Auto-generated method stub
		if (o1.getX() == o2.getX()) {
			return o1.getY() - o2.getY();
		} else {
			return o1.getX() - o2.getX();
		}
	}

}
