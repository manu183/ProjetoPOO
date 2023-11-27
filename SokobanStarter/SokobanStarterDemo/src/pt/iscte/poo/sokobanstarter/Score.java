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
	private String fileName;
	private File file;

	// Guarda o número de scores que o ficheiro deve guardar
	private static final int SCORES_TO_WRITE = 5;

	private static Score INSTANCE;
	private Map<Integer, List<User>> scores;

	private Score() {
		this.fileName = "levels/scores.txt";
		this.file = new File(fileName);
		scores = new HashMap<>();
	}

	// Implementacao do singleton para o Score
	public static Score getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new Score();
		return INSTANCE;
	}

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

	public void addNewScore(String userName, int score) {
		if (file.exists()) {
			System.out.println("File exists");
			readScoreFile();
		} else {
			System.out.println("File does not exists");
		}
		add(new User(userName, score));
		writeScoreFile();
	}

	private void readScoreFile() {
		try (Scanner scanner = new Scanner(file)) {
			// Verifica se há mais linhas antes de tentar ler
			while (scanner.hasNextLine()) {
				// Leia a linha inteira e depois crie um Scanner para analisar os tokens
				String line = scanner.nextLine();
				Scanner lineScanner = new Scanner(line);

				String position = lineScanner.next();
//	            System.out.println(position);

				String userName = lineScanner.next();
//	            System.out.println(userName);

				// Verifica se há um próximo token inteiro antes de chamá-lo
				if (lineScanner.hasNextInt()) {
					int score = lineScanner.nextInt();
					System.out.println(score);
					User newUser = new User(userName, score);
					System.out.println("New user:" + newUser.toString());
					add(newUser);
				} else {
					lineScanner.close(); // Certifique-se de fechar o scanner da linha
					throw new IllegalArgumentException("Erro ao ler pontuação. Score não é um inteiro válido.");
				}

				lineScanner.close(); // Certifique-se de fechar o scanner da linha
			}
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Error reading file. Invalid format.");
		}
	}

	private void add(User user) {
		if (!alreadyExists(user)) {
			List<User> users = getUsersByScore(user.getScore());
			users.add(user);
			scores.put(user.getScore(), users);
			System.out.println(scores);
		} else {
			System.out.println("This user and this pontuaction is already registed!");
		}
	}

	private boolean alreadyExists(User user) {
		List<User> users = getUsersByScore(user.getScore());
		for (User actual : users) {
			if (actual.equals(user)) {
				return true;
			}
		}
		return false;
	}

	private List<User> getUsersByScore(int score) {
		List<User> users = scores.get(score);
		if (users == null) {
			users = new ArrayList<>();
		}
		return users;
	}

	private List<String> sortScores() {
		List<Integer> sortedKeys = new ArrayList<>(scores.keySet());
//	    System.out.println("Sorting:...");
		sortedKeys.sort((a, b) -> a - b);
		System.out.println("Keys:" + sortedKeys);

		List<String> result = new ArrayList<>();

		for (Integer key : sortedKeys) {
			List<User> usersKey = getUsersByScore(key);
			for (User actual : usersKey) {
				result.add(actual.toString());
			}
		}
		return result;
	}

	private void writeScoreFile() {
		List<String> sortedScores = sortScores();
		System.out.println("SortedScores:" + sortedScores);

		try {
			int numScoresToWrite = Math.min(sortedScores.size(), SCORES_TO_WRITE);
			PrintWriter writer = new PrintWriter(fileName);
			for (int i = 0; i < numScoresToWrite; i++) {
				String now = sortedScores.get(i);
				String ordinal = (i + 1) + ".";
				writer.println(ordinal + " " + now);
				System.out.println("Should have printed!");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Única função pública que trata de chamar as funções necessárias para que seja
	// gerado um ficheiro
	// com as melhores pontuações

}
