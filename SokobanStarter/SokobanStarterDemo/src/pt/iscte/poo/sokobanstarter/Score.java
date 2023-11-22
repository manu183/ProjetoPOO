package pt.iscte.poo.sokobanstarter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Score {
	private String fileName;
	private File file;

	// Guarda o número de scores que o ficheiro deve guardar
	private static final int SCORES_TO_WRITE = 5;

	private static Score INSTANCE;
	private Map<Integer, List<String>> scores;

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
	
	

	private void addScore(String userName, int score) {
		if(userName == null || score == -1) {
			throw new IllegalArgumentException("userName or score is null or invalid!");
		}
		
		List<String> userNames = getUsers(score);

		if (userNames == null) {
			userNames = new ArrayList<>();
			scores.put(score, userNames);
		}

		userNames.add(userName + " " + score);

		scores.put(score, userNames);
	}

	private List<String> getUsers(int score) {
		List<String> userNames = scores.get(score);

		if (userNames == null) {
			userNames = new ArrayList<>();
		}

		return userNames;
	}

	private List<String> sortScores() {
		// Obtemos todas as keys e guardamos num ArrayList
		List<Map.Entry<Integer, List<String>>> keyList = new ArrayList<>(scores.entrySet());
		System.out.println(keyList);
		
		// Ordenar a lista por ordem decrescente
//		keyList.sort((a, b) -> b - a);

		// Criar uma lista do tipo String que irá devolver a pontuação concatenada com o
		// userName
		// para ser usada na função writeScoreFile
		List<String> sortedScores = new ArrayList<>();
		
//		for(Integer actual : keyList) {
//			sortedScores.addAll(getUsers(actual));
//		}
//		System.out.println(sortedScores);

		return sortedScores;

	}

	
	

	private void readScoreFile() {
		try {
			Scanner scanner = new Scanner(file);
			if(scanner.hasNextLine()) {				
				scanner.nextLine();
				while (scanner.hasNext()) {
					// position guarda por exemplo o "1." algo que não interessa para ler o ficheiro
					String position = scanner.next();
//					System.out.println(position);
					
					String userName = scanner.next();
//					System.out.println(userName);
					
					//Defini-se o score como -1 de modo a saber, que se caso, ele não tenha sido bem lido
					//tem o valor -1.
					int score=-1;
					if (scanner.hasNextInt()) {
	                    score = scanner.nextInt();
//	                    System.out.println(score);
	                    addScore(userName, score);
	                } else {
	                    System.err.println("Erro ao ler pontuação. Score não é um inteiro válido.");
	                    // Avançar para o próximo token para evitar um loop infinito
	                    scanner.next();
	                }
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Error reading file. Invalid format.");
		}
	}

	private void writeScoreFile() {
		try {
			PrintWriter writer = new PrintWriter(file);
			// String separador para o cabeçalho do ficheiro, em que repete 32 vezes "-"
			String separator = "-".repeat(20);
			writer.println(separator + " Scores " + separator);
			List<String> userNames = sortScores();
			List<Integer> keyList = new ArrayList<>(scores.keySet());
			
			//O Math.min(SCORES_TO_WRITE, toWrite.size()) serve para calcular o int mínimo uma vez
			//que podem não existir o 5 registos
			for (int i = 0; i < Math.min(SCORES_TO_WRITE, userNames.size()); i++) {
				String now = userNames.get(i);
				// Escreve no ficheiro. O (i+1)+"."+" " corresponde à numeração, por exemplo o
				// "1. "
				writer.println((i + 1) + "." + " " + now);
			}
			writer.close();
//			System.out.println(fileName);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Única função pública que trata de chamar as funções necessárias para que seja
	// gerado um ficheiro
	// com as melhores pontuações
	public void getFile(String userName, int score) {
		// Verifica se o ficheiro já existe de modo a que não se tente ler se o mesmo
		// não existir
		if (file.exists()) {
			readScoreFile();			
		}
		addScore(userName, score);
		writeScoreFile();
	}

}
