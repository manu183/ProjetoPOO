package pt.iscte.poo.sokobanstarter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class GameMap {
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

		Point2D position = gameElement.getPosition();// Obter a posicao de gameElement

		// Verificar se ja existe a key position
		if (map.containsKey(position)) {

			List<GameElement> elements = map.get(position);// Obter uma lista de objetos GameElement na posicao do
															// gameElement

			// Verificamos se o atual gameElement ainda nao existe, de modo a nao termos
			// vários objetos GameElement iguais que estejam associados à mesma chave
			if (!elements.contains(gameElement)) {

				elements.add(gameElement);// Se de facto, nao ainda nao existir e adicionado

				map.put(position, elements);// Atualização do hashMap com o a list elements

			}
		} else {

			List<GameElement> elements = new ArrayList<>();// Criamos uma nova lista elements
			elements.add(gameElement);

			map.put(position, elements);// Associamos a lista elements à chave position

		}
	}

	// Metodo para remover um certo objeto GameElement do tabuleiro de jogo
	public void removeElement(GameElement gameElement) {

		Point2D position = gameElement.getPosition();// Obter a posicao de gameElement

		List<GameElement> elements = map.get(position);// Obter a lista de objetos GameElement na posicao do gameElement

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

	// Metodo para atualizar a posicao de um objeto GameElement no tabuleiro de jogo
	public void updateElementPosition(GameElement gameElement, Point2D newPosition) {
		if (exists(gameElement)) {
			removeElement(gameElement);
			gameElement.setPosition(newPosition);
			addElement(gameElement);
		} else {
			System.err.println("This GameElement cannot be updated since it doesn't exists!");
		}
	}

	public void deleteAll() {
		map.clear();
	}

	public List<GameElement> getElementsAt(Point2D position) {
		List<GameElement> elements = map.get(position);

		if (elements == null) {
			System.err.println("No elements found at position: " + position);
			return new ArrayList<>(); // Retorna uma lista vazia
		}

		for (GameElement element : elements) {
			if (!element.getPosition().equals(position)) {
				System.err.println("Inconsistent position for element " + element.getName() + ". Expected: " + position
						+ ", Actual: " + element.getPosition());
				return new ArrayList<>(); // Retorna uma lista vazia
			}
		}

		return new ArrayList<>(elements); // Retorna uma copia para prevenir modificacoes externas
	}

	// Metodo que verifica se existe um certo objeto
	public boolean exists(GameElement gameElement) {
		List<GameElement> elements = getElementsAt(gameElement.getPosition());
		for (GameElement actual : elements) {
			if (actual.getClass().equals(gameElement.getClass())) {
				return true;
			}
		}
		return false;
	}

	// Metodo que verifica se o nivel foi ganho, ou seja, se todas as posicoes onde
	// existem alvos contem um caixote
	public boolean winsLevel() {
		boolean res = true;
		List<GameElement> elements = convertToArrayList();

		// Um arrayList que contem todas as posicoes que contem um alvo
		List<Point2D> target_positions = new ArrayList<>();

		for (GameElement actual : elements) {
			if (actual instanceof Alvo) {
				target_positions.add(actual.getPosition());
			}
		}
		// Verificar se existe algum posicao alvo que nao contem um caixote
		for (Point2D actual : target_positions) {
			if (!exists(new Caixote(actual))) {
				res = false;
			}
		}
		return res;
	}

	// Metodo que verifica se o nivel foi perdido
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

	// Metodo que serve para obter todos os objetos do map que sejam de um certo
	// GameElement
	public List<GameElement> getAllOfTheseGameElement(GameElement gameElement) {
		List<GameElement> elements = convertToArrayList();

		// Esta lista serve para guardar todos os gameElement que nao
		// pertencam a mesma classe de gameElement
		List<GameElement> toRemove = new ArrayList<>();
		for (GameElement actual : elements) {
			if (!(actual.getClass().equals(gameElement.getClass()))) {
				toRemove.add(actual);
			}
		}
		// Remover os elementos de toRemove de elements
		elements.removeAll(toRemove);

		return elements;
	}

	// Este metodo serve para obter um ArrayList de modo a que seja possivel
	// atualizar a GUI, que recebe objetos ImageTile
	public List<ImageTile> arrayToGUI() {
		List<ImageTile> allElements = new ArrayList<>();
		allElements.addAll(convertToArrayList());
		return allElements;
	}

	// Retorna uma lista com todos os GameElements
	public List<GameElement> convertToArrayList() {
		List<GameElement> allElements = new ArrayList<>();
		List<Point2D> keys = new ArrayList<>(map.keySet());
		keys.sort(new ComparatorPoints());
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
		// i e o iterador para se poder ver ate quando se deve imprimir ' | '
		int i = 0;
		int size = keys.size();
		keys.sort(new ComparatorPoints());
		for (Point2D actual : keys) {
			List<GameElement> gameElements = getElementsAt(actual);
			res += "(" + actual.getX() + ", " + actual.getY() + ")-> ";

			// j e o iterador para se poder ver ate quando se deve imprimir ', '
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

	// Implementacao de uma classe para a comparacao de objetos Point2D
	public class ComparatorPoints implements Comparator<Point2D> {
		// Implementa o metodo compare que serve para a ordenacao
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

}
