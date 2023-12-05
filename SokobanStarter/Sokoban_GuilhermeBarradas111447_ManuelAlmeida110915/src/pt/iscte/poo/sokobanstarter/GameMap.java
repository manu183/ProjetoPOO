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
			INSTANCE = new GameMap();
		return INSTANCE;
	}

	// Adicionar um objeto GameElement ao tabuleiro de jogo
	public void addElement(GameElement gameElement) {
		// Obter a posição de gameElement
		Point2D position = gameElement.getPosition();

		// Verificar se já existe a key position
		if (map.containsKey(position)) {

			List<GameElement> elements = map.get(position);

			// Verificamos se o atual gameElement ainda não existe, de modo a não termos
			// vários objetos GameElement iguais que estejam associados à mesma chave
			if (!elements.contains(gameElement)) {
				// Se de facto, não ainda não existir é adicionado
				elements.add(gameElement);

				// Atualização do hashMap com o a list elements
				map.put(position, elements);

//				System.out.println("Element added successfully at position: " + position);
			} else {
//				System.err.println("Element already exists at the specified position.");
			}
		} else {
			// Criamos uma nova lista elements
			List<GameElement> elements = new ArrayList<>();
			elements.add(gameElement);

			// Assiciamos a lista elements à chave position
			map.put(position, elements);

//			System.out.println("Element added successfully at position: " + position);
		}
	}

	// Função para remover um certo objeto GameElement do tabuleiro de jogo
	public void removeElement(GameElement gameElement) {
		// Obter a posição de gameElement
		Point2D position = gameElement.getPosition();
		
		List<GameElement> elements = map.get(position);

		if (elements != null) {
			if (elements.remove(gameElement)) {
				if (elements.isEmpty()) {
					map.remove(position);
				}
			} else {
				System.err.println("Element not found at the specified position.");
			}
		} else {
			System.err.println("No elements found at the specified position.");
		}
	}

	// Função para atualizar a posição de um objeto GameElement no tabuleiro de jogo
	public void updateElementPosition(GameElement gameElement, Point2D newPosition) {
		removeElement(gameElement);
		gameElement.setPosition(newPosition);
		addElement(gameElement);
	}

	public void deleteAll() {
		map.clear();
	}

	public List<GameElement> getElementsAt(Point2D position) {
		List<GameElement> elements = map.get(position);

		if (elements == null) {
			System.err.println("No elements found at position: " + position);
			return new ArrayList<>(); // Return an empty list
		}

		// Check if elements have inconsistent positions
		for (GameElement element : elements) {
			if (!element.getPosition().equals(position)) {
				System.err.println("Inconsistent position for element " + element.getName() + ". Expected: " + position
						+ ", Actual: " + element.getPosition());
				return new ArrayList<>(); // Return an empty list
			}
		}

		return new ArrayList<>(elements); // Return a copy to prevent external modification
	}

	// Função que verifica se existe um certo objeto
	public boolean containsOnPosition(GameElement gameElement) {
		List<GameElement> elements = getElementsAt(gameElement.getPosition());
		for (GameElement actual : elements) {
			if (actual.getClass().equals(gameElement.getClass())) {
				return true;
			}
		}
		return false;
	}

	// Função que verifica se o nível foi ganho, ou seja, se
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

	// Função que verifica se o nível foi ganho, ou seja, se
	public boolean loseLevel() {
		List<GameElement> elements = convertToArrayList();

		int numEmpilhadora = 0;
		int numCaixotes = 0;
		int numAlvos = 0;
		// Pretende-se calcular o número de caixotes, de alvo, e se existe empilhadora
		for (GameElement actual : elements) {
			if (actual instanceof Empilhadora) {
				numEmpilhadora++;
			} else if (actual instanceof Caixote) {
				numCaixotes++;
			} else if (actual instanceof Alvo) {
				numAlvos++;
			}
		}
		if (numEmpilhadora != 1 || numCaixotes == 0 || numAlvos == 0 || numCaixotes < numAlvos) {
			return true;
		}
		return false;

	}

	// Esta função serve para obter todos os objetos de um certo GameElement
	public List<GameElement> getAllOfTheseGameElement(GameElement gameElement) {
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
//		List<Point2D> keys = new ArrayList<>(map.keySet());
//		keys.sort((o1, o2) -> compare(o1, o2));
//		for (Point2D actual : keys) {
//			List<GameElement> gameElements = getElementsAt(actual);
//			allElements.addAll(gameElements);
//		}
		allElements.addAll(convertToArrayList());
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
				res += element.getPosition() + " " + element.getName() + ",isTranponsable:" + element.getTransposable();
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
