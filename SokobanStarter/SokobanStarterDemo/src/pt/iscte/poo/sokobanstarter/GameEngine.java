package pt.iscte.poo.sokobanstarter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

// Note que esta classe e' um exemplo - nao pretende ser o inicio do projeto, 
// embora tambem possa ser usada para isso.
//
// No seu projeto e' suposto haver metodos diferentes.
//  
// As coisas que comuns com o projeto, e que se pretendem ilustrar aqui, sao:
// - GameEngine implementa Observer - para  ter o metodo update(...)  
// - Configurar a janela do interface grafico (GUI):
//        + definir as dimensoes
//        + registar o objeto GameEngine ativo como observador da GUI
//        + lancar a GUI
// - O metodo update(...) e' invocado automaticamente sempre que se carrega numa tecla
//
// Tudo o mais podera' ser diferente!

public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private static GameEngine INSTANCE; // Referencia para o unico objeto GameEngine (singleton)
	private ImageMatrixGUI gui; // Referencia para ImageMatrixGUI (janela de interface com o utilizador)
	private List<ImageTile> tileList; // Lista de imagens
	private Empilhadora bobcat; // Referencia para a empilhadora

	// Guarda as posições de cada elemento numa classe GameMap baseada num HashMap
	public GameMap gameMap;
	

	// Construtor - neste exemplo apenas inicializa uma lista de ImageTiles
	private GameEngine() {
		tileList = new ArrayList<>();
		gameMap = gameMap.getInstance();
	}

	// Implementacao do singleton para o GameEngine
	public static GameEngine getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}

	public List<ImageTile> getTile() {
		return tileList;
	}

	// Define o mapa e atualiza automaticamente o tileList com os Valores do mapa

	

	// Adiciona um elemento lido do ficheiro ao map e ao tileList
	private void addToGame(GameElement gameElement) {
		gameMap.addElement(gameElement);
	}
	
	public void sycronizeTileList() {
		tileList.removeAll(tileList);
		tileList.addAll(gameMap.convertToArrayList());
	}

	public void setEmpilhadora(Empilhadora bobcat) {
		this.bobcat = bobcat;
	}

	// Função para ler os ficheiros que armazenam as diferentes disposições do
	// armazém
	public void readFiles(int levelNum) {
		if (levelNum < 0 || levelNum > 6) {
			throw new IllegalArgumentException("There is no level number " + levelNum);
		}
		String fileName = "level" + levelNum + ".txt";
		File file = new File("levels/" + fileName);
		
		// Cada ficheiro de nível tem 10 linhas e 10 colunas
		int linhas = 0;
		int colunas = 0;
		String linha = "";
		try (Scanner scanner = new Scanner(file)) {
			for (linhas = 0; linhas < GRID_HEIGHT; linhas++) {
				// Verifica se existe realmente uma linha seguinte
				if (scanner.hasNextLine()) {
					linha = scanner.nextLine();
				} else {
					// Senão existe nada na linha seguinte, então o ficheiro tem um formato inválido
					throw new IllegalArgumentException("The file " + fileName + "doesn't have a valid format");
				}
				for (colunas = 0; colunas < GRID_WIDTH; colunas++) {
					// detectString(linha.charAt(colunas), new Point2D(colunas, linhas));
					GameElement gameElement;
					if (linha.charAt(colunas) == 'E') {
						this.bobcat = new Empilhadora(new Point2D(colunas, linhas));
						addToGame(bobcat);
					} else {
						gameElement = GameElement.createElement(linha.charAt(colunas), new Point2D(colunas, linhas));
						addToGame(gameElement);
					}

				}
			}
			sycronizeTileList();
			scanner.close();
			
			// drawHashMap();
//			updateTileList();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Inicio
	public void start() {

		// Setup inicial da janela que faz a interface com o utilizador
		// algumas coisas poderiam ser feitas no main, mas estes passos tem sempre que
		// ser feitos!

		gui = ImageMatrixGUI.getInstance(); // 1. obter instancia ativa de ImageMatrixGUI
		gui.setSize(GRID_HEIGHT, GRID_WIDTH); // 2. configurar as dimensoes
		gui.registerObserver(this); // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go(); // 4. lancar a GUI

		// Criar o cenario de jogo
		createWarehouse(); // criar o armazem
//		createMoreStuff();      // criar mais algun objetos (empilhadora, caixotes,...)
		readFiles(1);
		sendImagesToGUI(); // enviar as imagens para a GUI

		// Escrever uma mensagem na StatusBar
		gui.setStatusMessage("Sokoban Starter - demo");
	}

	// O metodo update() e' invocado automaticamente sempre que o utilizador carrega
	// numa tecla
	// no argumento do metodo e' passada uma referencia para o objeto observado
	// (neste caso a GUI)
	@Override
	public void update(Observed source) {

		int key = gui.keyPressed(); // obtem o codigo da tecla pressionada

		Direction direction = Direction.directionFor(key);

		bobcat.move(direction);
		sendImagesToGUI();
		gui.update(); // redesenha a lista de ImageTiles na GUI,
						// tendo em conta as novas posicoes dos objetos
	}

	// Criacao da planta do armazem - so' chao neste exemplo
	private void createWarehouse() {
		for (int y = 0; y < GRID_HEIGHT; y++)
			for (int x = 0; x < GRID_HEIGHT; x++)
				gameMap.addElement(new Chao(new Point2D(x, y)));
	}

	// Criacao de mais objetos - neste exemplo e' uma empilhadora e dois caixotes
//	private void createMoreStuff() {
//		bobcat = new Empilhadora( new Point2D(5,5));
//		tileList.add(bobcat);
//
//		tileList.add(new Caixote(new Point2D(3,3)));
//		tileList.add(new Caixote(new Point2D(3,2)));
//	}

	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no
	// inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes
	private void sendImagesToGUI() {
		gui.addImages(tileList);
	}
}
