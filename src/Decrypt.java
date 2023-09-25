import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Decrypt {
    Encryption encryption=new Encryption();
    public void decryptText() {
        Scanner scanner=new Scanner(System.in);
        int shift=scanner.nextInt();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(encryption.encryptedFilePath), StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(Paths.get(encryption.decryptedFilePath), StandardCharsets.UTF_8)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String decryptText = decryptCaesar(line, shift);
                writer.write(decryptText);
            }
//
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String decryptCaesar(String text, int shift) {
        StringBuilder decryptText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'А' : 'а';
                decryptText.append((char) ((c - base - shift + 33) % 33 + base));
            } else {
                decryptText.append(c);
            }
        }
        return decryptText.toString();
    }
}
