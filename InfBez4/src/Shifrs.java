import java.io.*;
import java.util.*;

public class Shifrs {
    public static final String ALPHABET = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ_";

    public static void main(String[] args) throws IOException {
        String path = "Text.txt";
        processFile(path, new Cesar(), 3);
        processFile(path, new Vigenere(), "АКОТ");
        processFile(path, new VigenereR(), "АКОТ");
    }

    public static void processFile(String path, CipherMethod method, Object key) throws IOException {
        String text = getText(path);
        System.out.println("Расшифровка и зашифровка :" + method.getName());
        System.out.println("Исходное  | " + text.substring(0, Math.min(19, text.length())));

        String encrypted = method.encrypt(text, key);
        System.out.println("Закодированное | " + encrypted.substring(0, Math.min(19, encrypted.length())));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("enc" + method.getName() + "_" + path))) {
            writer.write(encrypted);
        }

        String decrypted = method.decrypt(encrypted, key);
        System.out.println("Раскодированное | " + decrypted.substring(0, Math.min(19, decrypted.length())) + "\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dec" + method.getName() + "_" + path))) {
            writer.write(decrypted);
        }
    }

    public static String getText(String path) throws IOException {
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            int ch;
            while ((ch = reader.read()) != -1) {
                char character = Character.toUpperCase((char) ch);
                if (ALPHABET.contains(String.valueOf(character)) || character == '_') {
                    text.append(character);
                }
            }
        }
        return text.toString().replace(" ", "_");
    }
}

interface CipherMethod {
    String encrypt(String text, Object key);
    String decrypt(String text, Object key);
    String getName();
}

class Cesar implements CipherMethod {
    @Override
    public String encrypt(String text, Object key) {
        int shift = (int) key;
        StringBuilder result = new StringBuilder();
        int end = Shifrs.ALPHABET.length();

        for (char symbol : text.toCharArray()) {
            int newPosition = (Shifrs.ALPHABET.indexOf(symbol) + shift) % end;
            result.append(Shifrs.ALPHABET.charAt(newPosition));
        }
        return result.toString();
    }

    @Override
    public String decrypt(String text, Object key) {
        int shift = (int) key;
        StringBuilder result = new StringBuilder();
        int end = Shifrs.ALPHABET.length();

        for (char symbol : text.toCharArray()) {
            int oldPosition = (Shifrs.ALPHABET.indexOf(symbol) - shift + end) % end;
            result.append(Shifrs.ALPHABET.charAt(oldPosition));
        }
        return result.toString();
    }

    @Override
    public String getName() {
        return "Cesar";
    }
}

class Vigenere implements CipherMethod {
    @Override
    public String encrypt(String text, Object key) {
        String k = key.toString();
        StringBuilder result = new StringBuilder();
        int end = Shifrs.ALPHABET.length();

        for (int i = 0; i < text.length(); i++) {
            int shift = Shifrs.ALPHABET.indexOf(k.charAt(i % k.length()));
            int newPosition = (Shifrs.ALPHABET.indexOf(text.charAt(i)) + shift) % end;
            result.append(Shifrs.ALPHABET.charAt(newPosition));
        }
        return result.toString();
    }

    @Override
    public String decrypt(String text, Object key) {
        String k = key.toString();
        StringBuilder result = new StringBuilder();
        int end = Shifrs.ALPHABET.length();

        for (int i = 0; i < text.length(); i++) {
            int shift = Shifrs.ALPHABET.indexOf(k.charAt(i % k.length()));
            int oldPosition = (Shifrs.ALPHABET.indexOf(text.charAt(i)) - shift + end) % end;
            result.append(Shifrs.ALPHABET.charAt(oldPosition));
        }
        return result.toString();
    }

    @Override
    public String getName() {
        return "Vigenere";
    }
}

class VigenereR implements CipherMethod {
    private String shuffledAlphabet;
    private final String originalAlphabet;

    public VigenereR() {
        this.originalAlphabet = Shifrs.ALPHABET; // Сохраняем оригинальный алфавит
        this.shuffledAlphabet = shuffleAlphabet(originalAlphabet);
    }

    private String shuffleAlphabet(String alphabet) {
        List<Character> alphabetList = new ArrayList<>();
        for (char c : alphabet.toCharArray()) {
            alphabetList.add(c);
        }
        Collections.shuffle(alphabetList);
        StringBuilder sb = new StringBuilder();
        for (char c : alphabetList) {
            sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public String encrypt(String text, Object key) {
        String k = key.toString();
        StringBuilder result = new StringBuilder();
        int end = shuffledAlphabet.length();

        for (int i = 0; i < text.length(); i++) {
            int shift = shuffledAlphabet.indexOf(k.charAt(i % k.length()));
            int newPosition = (shuffledAlphabet.indexOf(text.charAt(i)) + shift) % end;
            result.append(shuffledAlphabet.charAt(newPosition));
        }
        return result.toString();
    }

    @Override
    public String decrypt(String text, Object key) {
        String k = key.toString();
        StringBuilder result = new StringBuilder();
        int end = shuffledAlphabet.length();

        for (int i = 0; i < text.length(); i++) {
            int shift = shuffledAlphabet.indexOf(k.charAt(i % k.length()));
            int oldPosition = (shuffledAlphabet.indexOf(text.charAt(i)) - shift + end) % end;
            result.append(shuffledAlphabet.charAt(oldPosition));
        }
        return result.toString();
    }

    @Override
    public String getName() {
        return "VigenereR";
    }

}

