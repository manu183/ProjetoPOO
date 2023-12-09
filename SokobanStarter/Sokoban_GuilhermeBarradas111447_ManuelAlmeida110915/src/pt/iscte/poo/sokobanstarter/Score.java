package pt.iscte.poo.sokobanstarter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Score {
	private File file;
	private final User user;

	// Guarda o numero de scores que o ficheiro deve guardar
	public static final int SCORES_TO_WRITE = 3;

	private Map<Integer, List<User>> scores;

	private Score(String userName, int score, String fileName) {
		this.file = new File(fileName);
		this.user=new User(userName,score);
		scores = new HashMap<>();
		
	}
	public static Score addScore(String userName,int score, String fileName) {
		Score now = new Score(userName,score,fileName);
		now.saveScore();
		return now;
	}
	
	
	public File getFile() {
		return file;
	}
	
	
	// Adicionar um novo registo
	private void saveScore() {
		if (file.exists()) {
			System.out.println("File exists");
			readScoreFile();
		} else {
			System.out.println("File does not exists");
		}
		add(user);
		writeScoreFile();
	}

	// Implementacao da classe interna User
	private class User {
		private final String userName;
		private final int score;

		public User(String userName, int score) {
			this.userName = userName;
			this.score = score;
		}

		public int getScore() {
			return score;
		}

		public String getUserName() {
			return userName;
		}
		
		// Saber se um certo objeto e igual a este
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			User otherUser = (User) obj;

			if (score != otherUser.getScore()) {
				return false;
			}

			return userName.equals(otherUser.getUserName());
		}

		@Override
		public String toString() {
			return userName + " " + score;
		}
	}

	
	//Metodo para obter o rank
	public int getRank() {
		List<String> sortedScores = sortScores();

		int index = 1;
		for (String actual : sortedScores) {
			String[] now = actual.split(" ");

			int actualScore = Integer.parseInt(now[1]);

			if (now[0].equals(user.getUserName()) && actualScore == user.getScore()) {
				if (index <= SCORES_TO_WRITE) {
					return index;
				}
			}
			index++;
		}
		return -1;
	}
	
	//Metodo para ler os ficheiros de pontuacao
	private void readScoreFile() {
		try (Scanner scanner = new Scanner(file)) {
			// Verifica se ha mais linhas antes de tentar ler
			while (scanner.hasNextLine()) {
				// Ler a linha inteira
				String line = scanner.nextLine();
				Scanner lineScanner = new Scanner(line);

				String rank = lineScanner.next(); //Rank

				String userName = lineScanner.next();//Nome do jogador

				// Verifica se ha um proximo inteiro antes de chama-lo
				if (lineScanner.hasNextInt()) {
					int score = lineScanner.nextInt(); //Score do jogador
					User newUser = new User(userName, score); //Guardar o registo num objeto User
					add(newUser); //Adicionar este user ao HashMap
				} else {
					lineScanner.close();
					throw new IllegalArgumentException("Erro ao ler pontuacao. Score nao e um inteiro valido.");
				}

				lineScanner.close();
			}
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Error reading file. Invalid format.");
		}
	}
	
	//Metodo para adicionar um certo User ao HashMap
	private void add(User user) {
		if (!alreadyExists(user)) {//Adiciona o user na HashMap se ele nao existir ja na mesma
			List<User> users = getUsersByScore(user.getScore());
			users.add(user);
			scores.put(user.getScore(), users);
		} else {
			System.out.println("This user and this pontuaction is already registed!");
		}
	}
	
	//Metodo que verifica se existe o user no HashMap
	private boolean alreadyExists(User user) {
		List<User> users = getUsersByScore(user.getScore());
		for (User actual : users) {
			if (actual.equals(user)) {
				return true;
			}
		}
		return false;
	}
	
	//Metodo para obter todos os objetos User com um certo score
	private List<User> getUsersByScore(int score) {
		List<User> users = scores.get(score);
		if (users == null) {
			users = new ArrayList<>();
		}
		return users;
	}
	
	//Metodo retorna uma lista de String com os objetos User ordenados de forma crescente pelo score
	private List<String> sortScores() {
		List<Integer> sortedKeys = new ArrayList<>(scores.keySet());
		sortedKeys.sort((a, b) -> a - b);//Ordena sortedKeys de forma crescente
		List<String> result = new ArrayList<>();

		for (Integer key : sortedKeys) {
			List<User> usersKey = getUsersByScore(key);
			for (User actual : usersKey) {
				result.add(actual.toString());
			}
		}
		return result;
	}
	
	//Escreve num ficheiro .txt os 3 melhores User ordenados
	private void writeScoreFile() {
		List<String> sortedScores = sortScores();
		try {
			int numScoresToWrite = Math.min(sortedScores.size(), SCORES_TO_WRITE);
			PrintWriter writer = new PrintWriter(this.file);
			for (int i = 0; i < numScoresToWrite; i++) {
				String now = sortedScores.get(i);
				String ordinal = (i + 1) + ".";
				writer.println(ordinal + " " + now);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
