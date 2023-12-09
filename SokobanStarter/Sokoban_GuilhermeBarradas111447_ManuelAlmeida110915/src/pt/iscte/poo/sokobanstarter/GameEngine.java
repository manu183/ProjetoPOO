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

public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private static GameEngine INSTANCE; // Referencia para o unico objeto GameEngine (singleton)
	private ImageMatrixGUI gui; // Referencia para ImageMatrixGUI (janela de interface com o utilizador)
	public Empilhadora bobcat; // Referencia para a empilhadora

	private static final int MAX_LEVEL = 8; // Guarda o número do maior nível que o jogo tem

	public GameMap gameMap;// Guarda todos os objetos GameElement numa classe GameMap baseada num HashMap

	private int level;// Guarda o nível atual do jogador

	private int score;// Guarda os movimentos do jogador em cada nível

	private int total_score; // Guarda todos os movimentos do jogador até ao momento

	private String userName;// Guarda o nome do Jogador

	// Construtor
	private GameEngine() {

		this.gameMap = GameMap.getInstance();
		this.gui = ImageMatrixGUI.getInstance(); // 1. obter instancia ativa de ImageMatrixGUI
		this.level = 0;
		this.score = 0;
		this.total_score = 0;
		this.userName = "NOT_DEFINED";

	}

	// Implementacao do singleton para o GameEngine
	public static GameEngine getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}

	// Adiciona um elemento lido do ficheiro ao map e ao tabuleiro de jogo
	private void addToGame(GameElement gameElement) {
		gameMap.addElement(gameElement);
	}

	// Apaga o tabuleiro de jogo
	private void deleteGameMap() {
		gameMap.deleteAll();
	}

	// Aumenta o score e o total score em uma unidade
	public void increaseScore() {
		score++;
		total_score++;
	}

	// Método para definir o título da janela de jogo
	public void setStatusBar() {
		gui.setStatusMessage("Nome:" + userName + "  |  Nível=" + level + "  |  Energia:" + bobcat.getBattery()
				+ "  |  " + "Movimentos:" + score + " | Movimentos totais:" + total_score);
	}

	// Função para ler os ficheiros que armazenam as diferentes disposições do
	// armazém
	private void readFile(int levelNum) {
		if (levelNum < 0 || levelNum > MAX_LEVEL) {
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
					if (linha.charAt(colunas) == 'E') {
						this.bobcat = new Empilhadora(new Point2D(colunas, linhas));
						addToGame(bobcat);
					} else {
						GameElement gameElement = GameElement.createElement(linha.charAt(colunas), new Point2D(colunas, linhas));
						addToGame(gameElement);
						if (gameElement instanceof Teleporte) {
							System.out.println("Teleporte:" + gameElement);
							numTeleportes++;
						}
					}

				}
			}
			scanner.close();

			// Verifica que se caso existam teleportes eles não sejam mais que 2, que
			// correspondem a um par
			if (numTeleportes > 0 && numTeleportes != 2) {
				throw new IllegalArgumentException("The file can only have 2 teleportes!");
			}
			// Verifica se foi lida alguma empilhadora, verificando se o objeto bobcat é
			// nulo
			if (bobcat == null) {
				throw new IllegalArgumentException("The file must have a bobcat!!!");
			}

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

		introductionMenu();

		// Setup inicial da janela que faz a interface com o utilizador
		// algumas coisas poderiam ser feitas no main, mas estes passos tem sempre que
		// ser feitos!
		gui.setSize(GRID_HEIGHT, GRID_WIDTH); // 1. configurar as dimensoes
		gui.registerObserver(this); // 2. registar o objeto ativo GameEngine como observador da GUI
		gui.go(); // 3. lancar a GUI

		readFile(level); // Ler um certo ficheiro de um certo nível

		sendImagesToGUI(); // enviar as imagens para a GUI

		setStatusBar();// Escrever uma mensagem na StatusBar
	}

	// O metodo update() e' invocado automaticamente sempre que o utilizador carrega
	// numa tecla
	// no argumento do metodo e' passada uma referencia para o objeto observado
	// (neste caso a GUI)
	@Override
	public void update(Observed source) {

		int key = gui.keyPressed(); // obtem o codigo da tecla pressionada

		// Verifica se o key é aceite
		if (AcceptedKeys.isAcceptedKey(key)) {
			if (key == KeyEvent.VK_ESCAPE) {
				System.out.println("User pressed ESC to close the game!");
				System.exit(0); // Termina o programa porque o jogador clicou em ESC
			}
			if (key == KeyEvent.VK_SPACE) {
				restartLevel(); // Como o utilizador clicou no espaço o nível reinicia
			} else {
				Direction direction = Direction.directionFor(key); // Obtém a direção através da key
				bobcat.move(direction); // Invoca o método move da empilhadora

			}
			sendImagesToGUI();// enviar as imagens para a GUI

			System.out.println("GUI:" + gameMap.convertToArrayList());
			System.out.println(".".repeat(50));
			gui.update(); // redesenha a lista de ImageTiles na GUI,
							// tendo em conta as novas posicoes dos objetos

			setStatusBar();// Atualiza o título da GUI

			winLevel(); // Invoca o método que verifica se o nível atual foi ganho
			loseLevel(); // Invoca o método que verifica se o nível atual foi perdido

		} else {
			gui.setMessage("Essa tecla não é aceite"); // Como o utilizador clicou em uma tecla que não é aceite é
														// impressa uma mensagem na GUI
		}

	}

	// Cria o menu de introdução que pergunta o nome ao jogador
	public void introductionMenu() {
		gui.setMessage("Olá! Bem-vindo ao Sokoban!\n");

		userName = gui.askUser("Qual é o teu nome?");
		// Verifica se o utilizador deu um nome nulo, ou só composto por espaços
		while (userName.isBlank()) {
			gui.setMessage("O teu nome tem de ser definido!");
			userName = gui.askUser("Qual é o teu nome?");
		}
		gui.setStatusMessage("SOKOBAN");
		String message = "Objetivo do jogo: Preencher com o menor número de movimentos possíveis todos os alvos com um caixote!\nRegras do jogo:\n1. A empilhadora só pode empurrar um elemento movível.\n2. Só é possível passar por cima de buracos caso os mesmos tenham uma palete por baixo.Caso não tenha um palete o elemento cai no buraco.\n3. Só é possível teleportar um elemento se não houver outro elemento na posição do outro teleporte.";
		gui.setMessage(message + "\n\nSe quiseres sair durante o jogo pressiona a tecla ESC");
	}

	// Método que verifica se o nível foi ganho e que no futuro irá aumentar o nível
	public void winLevel() {
		if (gameMap.winsLevel()) {
			Score.addScore(userName, score, "scores/level" + level + ".txt");// Guarda os melhores scores de cada nível

			if (level == MAX_LEVEL) {
				gui.setStatusMessage("Ganhaste o jogo!!!");
				gui.setMessage("Ganhaste o jogo!");
				gui.dispose();//Fecha a janela de jogo da GUI
				// Guarda os melhores scores do jogo todo
				Score totalScore = Score.addScore(userName, total_score, "scores/scores.txt"); // Guarda os pontuações
																								// finais através da
																								// classe Score e guarda
																								// o objeto dessa class
																								// na variável score
				showScores(totalScore);//Invoca o método showScores para mostrar as melhores pontuações

				showAllLevelsScores();//Invoca o método showAllLevelsScores para mostrar as melhores pontuações de cada nível
				System.exit(0);//Termina a execução do programa
			} else {
				gui.setStatusMessage("Ganhaste o nível!");
				gui.setMessage("Ganhaste o nível!");
				levelUp();//Invoca o método levelUp para subir de nível
			}
		}
	}

	// Método que verifica se o nível foi perdido de forma a que se for verdade o
	// mesmo recomece
	public void loseLevel() {
		if (gameMap.loseLevel()) { //Invoca o método loseLevel do gameMap para verificar se o nível já foi perdido, ou seja se não existe empilhadora ou número suficientes de caixotes para o número de alvos
			gui.setMessage("Perdeste o nível!");
			restartLevel();//Invoca o método para reiniciar o nível
		}
		if (bobcat.getBattery() <= 0 && !gameMap.winsLevel()) {
			gui.setMessage("Ficaste sem bateria!");
			restartLevel();//Invoca o método para reiniciar o nível
		}
		
	}
	
	//Método que implementa a subida de nível
	public void levelUp() {
		if (level >= 0 && level + 1 <= MAX_LEVEL) {
			level++;
			score = 0;
			setStatusBar();// Atualiza o título da GUI
			deleteGameMap();//Invoca para apagar o conteúdo do gameMap deste nível 
			readFile(level);//Lê o ficheiro do novo nível
		}
	}

	// Mostrar os resultados na GUI, referindo ao utilizador o seu rank
	public void showScores(Score score) {
		String message = "";
		int rank = score.getRank();
		System.out.println(rank);
		if (rank != -1) {
			message += "Parabéns!!! Ficaste no rank " + rank + "\n";
		} else {
			message += "Não ficaste no top "+Score.SCORES_TO_WRITE +".Tenta outra vez!\n";
		}
		message += "Melhores resultados:\n";

		try {
			// Lê o ficheiro scores
			Scanner scanner = new Scanner(score.getFile());
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
	
	//Mostrar as melhores pontuações de todos os níveis
	public void showAllLevelsScores() {
		String message = "Best Levels scores!\n";
		for (int i = 0; i <= MAX_LEVEL; i++) {
			File level = new File("scores/level" + i + ".txt");
			message += "LEVEL " + i + "\n";
			try {
				// Lê o ficheiro scores
				Scanner scanner = new Scanner(level);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					message += line + "\n";
				}
				scanner.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			message += "\n";
		}
		gui.setMessage(message);
	}
	
	//Método para reiniciar o nível atual
	public void restartLevel() {
		deleteGameMap();
		readFile(level);
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
		gui.addImages(gameMap.arrayToGUI());//Envia para a GUI uma lista de  objetos ImageTile através do método arrayToGUI do gameMap
	}
}