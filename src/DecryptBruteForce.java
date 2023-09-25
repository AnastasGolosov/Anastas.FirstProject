import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DecryptBruteForce {
    Encryption encryption=new Encryption();
    Decrypt decrypt=new Decrypt();
    public Map<Character, Double> calculateActualProbabilities(String text) {
        Map<Character, Double> actualProbabilities = new HashMap<>();
        int totalLetters = 0;
        for (char c:text.toCharArray()) {
            if(Character.isLetter(c)){
                c=Character.toLowerCase(c);
                actualProbabilities.put(c,actualProbabilities.getOrDefault(c,0.0)+1.0);
                totalLetters++;
            }
        }
        for (char c:actualProbabilities.keySet()
        ) {
            double probability=actualProbabilities.get(c)/totalLetters;
            actualProbabilities.put(c,probability);

        }
        return actualProbabilities;
    }
    public Map<Character, Double> createExpectedProbabilities() {
        Map<Character, Double> expectedProbabilities = new HashMap<>();
        expectedProbabilities.put('а', 0.0801);
        expectedProbabilities.put('б', 0.0159);
        expectedProbabilities.put('в', 0.0454);
        expectedProbabilities.put('г', 0.0170);
        expectedProbabilities.put('д', 0.0298);
        expectedProbabilities.put('е', 0.0845);
        expectedProbabilities.put('ё', 0.0004);
        expectedProbabilities.put('ж', 0.0094);
        expectedProbabilities.put('з', 0.0165);
        expectedProbabilities.put('и', 0.0735);
        expectedProbabilities.put('й', 0.0121);
        expectedProbabilities.put('к', 0.0349);
        expectedProbabilities.put('л', 0.0440);
        expectedProbabilities.put('м', 0.0321);
        expectedProbabilities.put('н', 0.0670);
        expectedProbabilities.put('о', 0.1097);
        expectedProbabilities.put('п', 0.0281);
        expectedProbabilities.put('р', 0.0473);
        expectedProbabilities.put('с', 0.0547);
        expectedProbabilities.put('т', 0.0626);
        expectedProbabilities.put('у', 0.0262);
        expectedProbabilities.put('ф', 0.0026);
        expectedProbabilities.put('х', 0.0097);
        expectedProbabilities.put('ц', 0.0048);
        expectedProbabilities.put('ч', 0.0144);
        expectedProbabilities.put('ш', 0.0073);
        expectedProbabilities.put('щ', 0.0036);
        expectedProbabilities.put('ъ', 0.0004);
        expectedProbabilities.put('ы', 0.0190);
        expectedProbabilities.put('ь', 0.0174);
        expectedProbabilities.put('э', 0.0032);
        expectedProbabilities.put('ю', 0.0064);
        expectedProbabilities.put('я', 0.0201);

        return expectedProbabilities;
    }
    public double calculateSimilarityScore(Map<Character, Double> expectedProbabilities,
                                           Map<Character, Double> actualProbabilities) {
        double dotProduct = 0.0;
        double expectedMagnitude = 0.0;
        double actualMagnitude = 0.0;
        for (char c:expectedProbabilities.keySet()
        ) {dotProduct+=expectedProbabilities.get(c)*actualProbabilities.getOrDefault(c,0.0);
            expectedMagnitude += Math.pow(expectedProbabilities.get(c), 2);
            actualMagnitude += Math.pow(actualProbabilities.getOrDefault(c, 0.0), 2);
        }
        expectedMagnitude = Math.sqrt(expectedMagnitude);
        actualMagnitude = Math.sqrt(actualMagnitude);

        if (expectedMagnitude == 0.0 || actualMagnitude == 0.0) {
            return 0.0; // Защита от деления на ноль
        }

        return dotProduct / (expectedMagnitude * actualMagnitude);
    }

    private Map<Character, Double> expectedProbabilities = createExpectedProbabilities();
    public void BruteForce(){
        int shift;
        //??
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(encryption.encryptedFilePath), StandardCharsets.UTF_8)) {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(encryption.decryptedFilePath), StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Map<String, Double> decryptionResults = new HashMap<>();
                    for (shift = 0; shift <= 33; shift++) {
                        String decryptedText =decrypt.decryptCaesar(line, shift);
                        Map<Character, Double> actualProbabilities = calculateActualProbabilities(decryptedText);
                        double similarityScore = calculateSimilarityScore(expectedProbabilities, actualProbabilities);
                        decryptionResults.put(decryptedText, similarityScore);
                    }

                    // Найти наилучший результат (максимальное сходство)
                    double maxSimilarityScore = -1.0;
                    String bestDecryption = "";

                    for (Map.Entry<String, Double> entry : decryptionResults.entrySet()) {
                        if (entry.getValue() > maxSimilarityScore) {
                            maxSimilarityScore = entry.getValue();
                            bestDecryption = entry.getKey();
                        }
                    }
                    writer.write(bestDecryption);


                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
