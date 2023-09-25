import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Encryption {
    public String sourceFilePath = "MyText.txt";
    public String encryptedFilePath = "EncryptedText.txt";
    public String decryptedFilePath = "DecryptedText.txt";
    //
//    public Map<Character, Integer> TextAnalyzer(String Text) {
//        Map<Character, Integer> letterFrequency = new HashMap<>();
//        try (BufferedReader reader = new BufferedReader(new StringReader(Text))) {
//            int currentChar;
//            while ((currentChar = reader.read()) != -1) {
//                char convert = (char) currentChar;
//                if (Character.isLetter(convert)) {
//                    convert = Character.toLowerCase(convert);
//                    letterFrequency.put(convert, letterFrequency.getOrDefault(convert, 0) + 1);
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        for (Map.Entry<Character, Integer> entry : letterFrequency.entrySet()
//        ) {
//            System.out.println("Буква: " + entry.getKey() + "   Частота: " + entry.getValue());
//
//        }
//        return letterFrequency;
//    }
    public void encryptText() {
        Scanner scanner=new Scanner(System.in);
        int shift=scanner.nextInt();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(sourceFilePath), StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(Paths.get(encryptedFilePath), StandardCharsets.UTF_8)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String encryptText = encryptCaesar(line, shift);
                writer.write(encryptText);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String encryptCaesar(String text, int shift) {
        StringBuilder encryptText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'А' : 'а';
                encryptText.append((char) ((c - base + shift) % 33 + base));
            } else {
                encryptText.append(c);
            }
        }
        return encryptText.toString();
    }
}