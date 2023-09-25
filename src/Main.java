import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        System.out.println("Введите: \" 1\" для зашифровки текста\n" +
                "Введите: \" 2\" для расшифровки текста\n" +
                "Введите: \"3\" для расшифровки текста методом перебора");

        Encryption encryption = new Encryption();
        Decrypt decrypt=new Decrypt();
        DecryptBruteForce decryptBruteForce=new DecryptBruteForce();
        Scanner scanner = new Scanner(System.in);
        int start = scanner.nextInt();
        switch (start) {
            case 1 -> {
                System.out.println("Укажите шаг шифрования");
                encryption.encryptText();
            }
            case 2 -> {
                System.out.println("Укажите шаг дешифрования");
               decrypt.decryptText();
            }
            case 3 -> decryptBruteForce.BruteForce();
            default -> System.out.println("Что-то пошло не так");
        }
    }

}
