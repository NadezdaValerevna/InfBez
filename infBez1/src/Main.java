import java.io.*;


public class Main {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        System.out.println("Введите пароль:");
        String password = bufferedReader.readLine();



       // System.out.println("Алфавит:" + methods.ChangeAlphabet(password));
       // System.out.println("Кол-во возможный комбинаций:" + methods.Power(password));
        System.out.println("Время для перебора всех паролей:" + methods.TimeVF(password));
        System.out.println();


    }
}


