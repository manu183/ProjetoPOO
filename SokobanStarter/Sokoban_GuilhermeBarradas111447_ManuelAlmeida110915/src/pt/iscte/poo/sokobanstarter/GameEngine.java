package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageMatrixGUI;
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
	public Empilhadora bobcat; // Referencia para a empilhadora

	// Guarda as posições de cada elemento numa classe GameMap baseada num HashMap
	public GameMap gameMap;
	// Guarda o nível atual do jogador
	private int level;
	// Guarda a pontuação do jogador
	private int score;
	// Guarda o nome do Jogador
	private String userName;

	// Construtor - neste exemplo apenas inicializa uma lista de ImageTiles
	private GameEngine() {
//		this.tileList = new ArrayList<>();
		this.gameMap = GameMap.getInstance();
		this.gui = ImageMatrixGUI.getInstance();
		this.level = 6;
		this.userName = "NOT_DEFINED";
	}

	// Implementacao do singleton para o GameEngine
	public static GameEngine getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}



	// Define o mapa e atualiza automaticamente o tileList com os Valores do mapa

	// Adiciona um elemento lido do ficheiro ao map e ao tileList
	private void addToGame(GameElement gameElement) {
		gameMap.addElement(gameElement);
	}

	private void deleteGameMap() {
		gameMap.deleteAll();
	}
	
	public void increaseScore() {
		score++;
	}



	// Função para ler os ficheiros que armazenam as diferentes disposições do
	// armazém
	public void readFiles(int levelNum) {
		if (levelNum < 0 || levelNum > 6) {
			throw new IllegalArgumentException("There is no level number " + levelNum);
		}
		// Define todos os elementos do gameMap como chão por defeito
		createWarehouse();

		String fileName = "level" + levelNum + ".txt";
		File file = new File("levels/" + fileName);

		// Cada ficheiro de nível tem 10 linhas e 10 colunas
		int linhas = 0;
		int colunas = 0;
		String linha = "";
		
		//Durante a leitura de um ficheiro é também verificado se existe mais do
		//que dois teleportes, algo que não pode acontecer
		int numTeleportes=0;
		
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
						if(gameElement instanceof Teleporte) {
							System.out.println("Teleporte:"+gameElement);
							numTeleportes++;
						}
					}

				}
			}
			scanner.close();
			
			//Verifica que se caso existam teleportes, eles não correspondam a um par
			if(numTeleportes>0 && numTeleportes!=2) {
				throw new IllegalArgumentException("The file can only have 2");
			}
			
			sendImagesToGUI();
			System.out.println("GUI:"+gameMap);
			System.out.println(".".repeat(50));
			gui.update();
			
			
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Inicio
	public void start() {

//		introductionMenu();

		// Setup inicial da janela que faz a interface com o utilizador
		// algumas coisas poderiam ser feitas no main, mas estes passos tem sempre que
		// ser feitos!
//		gui = ImageMatrixGUI.getInstance(); // 1. obter instancia ativa de ImageMatrixGUI
		gui.setSize(GRID_HEIGHT, GRID_WIDTH); // 2. configurar as dimensoes
		gui.registerObserver(this); // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go(); // 4. lancar a GUI

		// Criar o cenario de jogo
//		createWarehouse(); // criar o armazem
//		createMoreStuff();      // criar mais algun objetos (empilhadora, caixotes,...)
//		readFiles(level);
		readFiles(level);
		sendImagesToGUI(); // enviar as imagens para a GUI

		// Escrever uma mensagem na StatusBar
		gui.setStatusMessage("Sokoban Starter | Player name:" + userName + " | level=" + level + " | Battery energy:"
				+ bobcat.getBattery());
	}

	// O metodo update() e' invocado automaticamente sempre que o utilizador carrega
	// numa tecla
	// no argumento do metodo e' passada uma referencia para o objeto observado
	// (neste caso a GUI)
	@Override
	public void update(Observed source) {

		int key = gui.keyPressed(); // obtem o codigo da tecla pressionada

		if (key == KeyEvent.VK_SPACE) {
			restartLevel();
		} else {
			Direction direction = Direction.directionFor(key);

			bobcat.move(direction);

		}
		

		sendImagesToGUI();
		System.out.println("GUI:"+gameMap);
		System.out.println(".".repeat(50));
		gui.update(); // redesenha a lista de ImageTiles na GUI,
						// tendo em conta as novas posicoes dos objetos

		// Atualiza o título da GUI
		gui.setStatusMessage("Sokoban Starter | Player name:" + userName + " | level=" + level + " | Battery energy:"
				+ bobcat.getBattery());

		winLevel();
	}

	// Cria o menu de introdução que pergunta o nome ao jogador
	public void introductionMenu() {
//		gui = ImageMatrixGUI.getInstance();
		gui.setMessage("Hi! Welcome to Sokoban");
		userName = gui.askUser("What is your name?");
		System.out.println(userName);
	}

	// Método que verifica se o nível foi ganho e que no futuro irá aumentar o nível
	public void winLevel() {
		if (gameMap.winsLevel()) {
			System.out.println("Won the level!");
			gui.setStatusMessage("You won this level!");
			gui.setMessage("Won the level");
//			score+=bobcat.getBattery();
			levelUp();
		}
	}
	

	public void levelUp() {
		if(level >=0 && level+1<6) {
			level++;
			deleteGameMap();
			readFiles(level);			
		}else {
			//Significa que o utilizador ganhou o jogou
			//Escreve as pontuações no ficheiro "levels/scores.txt"
			Score registScore = Score.getInstance();
			registScore.addNewScore(userName, score);
		}
	}

	public void restartLevel() {
		deleteGameMap();
		readFiles(level);
	}

	// Criacao da planta do armazem - so' chao neste exemplo
	private void createWarehouse() {
		for (int y = 0; y < GRID_HEIGHT; y++)
			for (int x = 0; x < GRID_HEIGHT; x++)
				gameMap.addElement(new Chao(new Point2D(x, y)));
	}

	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no
	// inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes
	private void sendImagesToGUI() {
		gui.addImages(gameMap.arrayToGUI());
	}
}
