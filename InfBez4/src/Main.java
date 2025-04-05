//import java.io.*;
//import java.util.*;
//
//public class Shifrs {
//    public static final String ALPHABET = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ_";
//
//    public static void main(String[] args) throws IOException {
//        String path = "ghosts.txt";
//        processFile(path, new Cesar(), 7);
//        processFile(path, new Vigenere(), "АРМАДА");
//    }
//
//    public static void processFile(String path, CipherMethod method, Object key) throws IOException {
//        String text = getText(path);
//        System.out.println("Encrypted and decrypted with the " + method.getName() + " method");
//        System.out.println("Original  | " + text.substring(0, Math.min(19, text.length())));
//
//        String encrypted = method.encrypt(text, key);
//        System.out.println("Encrypted | " + encrypted.substring(0, Math.min(19, encrypted.length())));
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("enc" + method.getName() + "_" + path))) {
//            writer.write(encrypted);
//        }
//
//        String decrypted = method.decrypt(encrypted, key);
//        System.out.println("Decrypted | " + decrypted.substring(0, Math.min(19, decrypted.length())) + "\n");
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dec" + method.getName() + "_" + path))) {
//            writer.write(decrypted);
//        }
//    }
//
//    public static String getText(String path) throws IOException {
//        StringBuilder text = new StringBuilder();
//        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
//            int ch;
//            while ((ch = reader.read()) != -1) {
//                char character = Character.toUpperCase((char) ch);
//                if (ALPHABET.contains(String.valueOf(character)) || character == '_') {
//                    text.append(character);
//                }
//            }
//        }
//        return text.toString().replace(" ", "_");
//    }
//}
//
//interface CipherMethod {
//    String encrypt(String text, Object key);
//    String decrypt(String text, Object key);
//    String getName();
//}
//
//class Cesar implements CipherMethod {
//    @Override
//    public String encrypt(String text, Object key) {
//        int shift = (int) key;
//        StringBuilder result = new StringBuilder();
//        int end = Cipher.ALPHABET.length();
//
//        for (char symbol : text.toCharArray()) {
//            int newPosition = (Cipher.ALPHABET.indexOf(symbol) + shift) % end;
//            result.append(Cipher.ALPHABET.charAt(newPosition));
//        }
//        return result.toString();
//    }
//
//    @Override
//    public String decrypt(String text, Object key) {
//        int shift = (int) key;
//        StringBuilder result = new StringBuilder();
//        int end = Cipher.ALPHABET.length();
//
//        for (char symbol : text.toCharArray()) {
//            int oldPosition = (Cipher.ALPHABET.indexOf(symbol) - shift + end) % end;
//            result.append(Cipher.ALPHABET.charAt(oldPosition));
//        }
//        return result.toString();
//    }
//
//    @Override
//    public String getName() {
//        return "Cesar";
//    }
//}
//
//class Vigenere implements CipherMethod {
//    @Override
//    public String encrypt(String text, Object key) {
//        String k = key.toString();
//        StringBuilder result = new StringBuilder();
//        int end = Cipher.ALPHABET.length();
//
//        for (int i = 0; i < text.length(); i++) {
//            int shift = Cipher.ALPHABET.indexOf(k.charAt(i % k.length()));
//            int newPosition = (Cipher.ALPHABET.indexOf(text.charAt(i)) + shift) % end;
//            result.append(Cipher.ALPHABET.charAt(newPosition));
//        }
//        return result.toString();
//    }
//
//    @Override
//    public String decrypt(String text, Object key) {
//        String k = key.toString();
//        StringBuilder result = new StringBuilder();
//        int end = Cipher.ALPHABET.length();
//
//        for (int i = 0; i < text.length(); i++) {
//            int shift = Cipher.ALPHABET.indexOf(k.charAt(i % k.length()));
//            int oldPosition = (Cipher.ALPHABET.indexOf(text.charAt(i)) - shift + end) % end;
//            result.append(Cipher.ALPHABET.charAt(oldPosition));
//        }
//        return result.toString();
//    }
//
//    @Override
//    public String getName() {
//        return "Vigenere";
//    }
//}
