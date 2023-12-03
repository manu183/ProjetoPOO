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
//		if (!containsOnPosition(gameElement)) {
//			List<GameElement> elements = getElementsAt(gameElement.getPosition());
//			elements.add(gameElement);
//			map.put(gameElement.getPosition(), elements);
//		}
		List<GameElement> elements = getElementsAt(gameElement.getPosition());
		elements.add(gameElement);
		map.put(gameElement.getPosition(), elements);

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

	// TODO Apagar esta função
//	public boolean existsOnPosition(Point2D position, String imageName) {
//		List<GameElement> elements = getElementsAt(position);
//		for (GameElement actual : elements) {
//			if (actual.getName().equals(imageName)) {
//				return true;
//			}
//		}
//		return false;
//	}

	public boolean containsOnPosition(GameElement gameElement) {
		List<GameElement> elements = getElementsAt(gameElement.getPosition());
		for (GameElement actual : elements) {
			if (actual.getClass().equals(gameElement.getClass())) {
				return true;
			}
		}
		return false;
	}

	public boolean hasCaixote() {
		List<GameElement> elements = convertToArrayList();
		for (GameElement actual : elements) {
			if (actual instanceof Caixote) {
				return true;
			}
		}
		return false;
	}

	// Verificação que verifica se o nível foi ganho, ou seja, se
	public boolean winsLevel() {
		boolean res = true;
		List<GameElement> elements = convertToArrayList();

		// Um arrayList que contém todas as posições que contém um alvo
		List<Point2D> target_positions = new ArrayList<>();

		for (GameElement actual : elements) {
			if (actual instanceof Alvo) {
				target_positions.add(actual.getPosition());
			}
		}
		// Verificar se existe algum posição alvo que não contém um caixote
		for (Point2D actual : target_positions) {
			if (!containsOnPosition(new Caixote(actual))) {
				res = false;
			}
		}
		return res;
	}

	// Esta função serve para obter todos os objetos de um certo GameElement
	public List<GameElement> getAllGameElement(GameElement gameElement) {
		List<GameElement> elements = convertToArrayList();

		// Esta lista serve para guardar todos os gameElement que não
		// pertençam à mesma classe de gameElement
		List<GameElement> toRemove = new ArrayList<>();
		for (GameElement actual : elements) {
			if (!(actual.getClass().equals(gameElement.getClass()))) {
				toRemove.add(actual);
			}
		}
		// Remover de elements os elementos de toRemove
		elements.removeAll(toRemove);

		return elements;
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

	// Retorna uma lista com todos os GameElements
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
				res += element.getName() + ",isTranponsable:" + element.getTransposable();
				if (j != elementsSize - 1)
					res += "; ";
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
