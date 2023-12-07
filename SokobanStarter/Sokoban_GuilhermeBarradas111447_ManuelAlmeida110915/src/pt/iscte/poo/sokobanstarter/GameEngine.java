package pt.iscte.poo.sokobanstarter;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
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
	
	private static final int MAX_LEVELS=7;
	// Guarda as posições de cada elemento numa classe GameMap baseada num HashMap
	public GameMap gameMap;
	// Guarda o nível atual do jogador
	private int level;
	// Guarda a pontuação do jogador
	private int score;
	// Guarda o nome do Jogador
	private String userName;

	// Guarda o objeto da classe Score que é utilizado para guardar o score
	private Score registScore;

	// Construtor - neste exemplo apenas inicializa uma lista de ImageTiles
	private GameEngine() {
//		this.tileList = new ArrayList<>();
		this.gameMap = GameMap.getInstance();
		this.gui = ImageMatrixGUI.getInstance();
		this.level = 5;
		this.score = 0;
		this.userName = "NOT_DEFINED";
		this.registScore = Score.getInstance();
	}

	// Implementacao do singleton para o GameEngine
	public static GameEngine getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}

	// Define o mapa e atualiza automaticamente o tileList com os Valores do mapa

	// Adiciona um elemento lido do ficheiro ao map e ao tabuleiro de jogo
	private void addToGame(GameElement gameElement) {
		gameMap.addElement(gameElement);
	}

	//Apaga o tabuleiro de jogo
	private void deleteGameMap() {
		gameMap.deleteAll();
	}
	
	//Aumenta o score, que corresponde aos movimentos totais, em uma unidade
	public void increaseScore() {
		score++;
	}

	// Função para ler os ficheiros que armazenam as diferentes disposições do
	// armazém
	private void readFiles(int levelNum) {
		if (levelNum < 0 || levelNum > MAX_LEVELS) {
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

		// Durante a leitura de um ficheiro é também verificado se existe mais do
		// que dois teleportes, algo que não pode acontecer
		int numTeleportes = 0;

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
						if (gameElement instanceof Teleporte) {
							System.out.println("Teleporte:" + gameElement);
							numTeleportes++;
						}
					}

				}
			}
			scanner.close();

			// Verifica que se caso existam teleportes, eles não correspondam a um par
//			if (numTeleportes > 0 && numTeleportes != 2) {
//				throw new IllegalArgumentException("The file can only have 2 teleportes!");
//			}

			sendImagesToGUI();
			System.out.println("GUI:" + gameMap);
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


		readFiles(level);
		sendImagesToGUI(); // enviar as imagens para a GUI

		// Escrever uma mensagem na StatusBar
		gui.setStatusMessage("Nome:" + userName + "  |  Nível=" + level + "  |  Energia:" + bobcat.getBattery()
				+ "  |  " + "Movimentos:" + score);
	}

	// O metodo update() e' invocado automaticamente sempre que o utilizador carrega
	// numa tecla
	// no argumento do metodo e' passada uma referencia para o objeto observado
	// (neste caso a GUI)
	@Override
	public void update(Observed source) {

		int key = gui.keyPressed(); // obtem o codigo da tecla pressionada
		
		//Verifica se o key é aceite
		if(AcceptedKeys.isAcceptedKey(key)) {
			if (key == KeyEvent.VK_SPACE) {
				restartLevel();
			} else {
				Direction direction = Direction.directionFor(key);
				bobcat.move(direction);

			}
			sendImagesToGUI();
			System.out.println("GUI:" + gameMap.convertToArrayList());
			System.out.println(".".repeat(50));
			gui.update(); // redesenha a lista de ImageTiles na GUI,
							// tendo em conta as novas posicoes dos objetos

			// Atualiza o título da GUI
			gui.setStatusMessage("Nome:" + userName + "  |  Nível=" + level + "  |  Energia:" + bobcat.getBattery()
					+ "  |  " + "Movimentos:" + score);

			winLevel();
			loseLevel();
			
			
		}else {
			gui.setMessage("Essa tecla não é aceite");
		}
		

		
	}

	// Cria o menu de introdução que pergunta o nome ao jogador
	public void introductionMenu() {
		gui.setMessage("Olá! Bem-vindo ao Sokoban");
		userName = gui.askUser("Qual é o teu nome?");
		//Verifica se o utilizador deu um nome nulo, ou só composto por espaços
		while (userName.isBlank()) {
			gui.setMessage("O teu nome tem de ser definido!");
			userName = gui.askUser("Qual é o teu nome?");
		}
	}

	// Método que verifica se o nível foi ganho e que no futuro irá aumentar o nível
	public void winLevel() {
		if (gameMap.winsLevel()) {
			if (level == MAX_LEVELS) {
				gui.setStatusMessage("Ganhaste o jogo!!!");
				gui.setMessage("Ganhaste o jogo!");
				winsGame();
				registScore.addNewScore(userName, score);
				showScores();
			}else {
				gui.setStatusMessage("Ganhaste o nível!");
				gui.setMessage("Ganhaste o nível!");
				levelUp();
			}
		}
	}

	// Método que verifica se o nível foi perdido de forma a que se for verdade o
	// mesmo recomece
	public void loseLevel() {
		if (gameMap.loseLevel()) {
			gui.setMessage("Perdeste o nível!");
			restartLevel();
		}
		if (bobcat.getBattery()<=0) {
			gui.setMessage("Ficaste sem bateria!");
			restartLevel();
		}
	}

	public void levelUp() {
		if (level >= 0 && level + 1 <= MAX_LEVELS) {
			level++;
			gui.setStatusMessage("Nome:" + userName + "  |  Nível=" + level + "  |  Energia:" + bobcat.getBattery()
			+ "  |  " + "Movimentos:" + score);
			deleteGameMap();
			readFiles(level);
		}
	}

	// mostrar os resultados na gui, referindo ao utilizador o seu rank
	public void showScores() {
		String message = "";
		int rank = registScore.getRank(userName, score);
		System.out.println(rank);
		if (rank != -1) {
			message += "Parabéns!!! Ficaste no rank " + rank + "\n";
		} else {
			message += "Não ficaste no top 5. Tenta outra vez!\n";
		}
		message += "Melhores resultados:\n";

		try {
			// Lê o ficheiro scores
			Scanner scanner = new Scanner(new File("levels/scores.txt"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				message += line + "\n";
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		gui.setMessage(message);

	}

	public void restartLevel() {
		deleteGameMap();
		readFiles(level);
	}

	public void winsGame() {
		gui.dispose();
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