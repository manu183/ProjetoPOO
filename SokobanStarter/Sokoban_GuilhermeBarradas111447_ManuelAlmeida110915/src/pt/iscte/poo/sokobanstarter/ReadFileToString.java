package pt.iscte.poo.sokobanstarter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadFileToString {
    public static void main(String[] args) {
        String filePath = "levels/scores.txt";

        try {
            String fileContent = readFileToString(filePath);
            System.out.println(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFileToString(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] fileBytes = Files.readAllBytes(path);
        return new String(fileBytes);
    }
}
