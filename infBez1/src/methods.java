public class methods {

    public static double s = 36; //Скорость перебора пароля в секунду
    public static int m = 18; // Количество неправильный попыток после которых идет пауза

    public static int v = 10; //Пауза сек.

    public static int Power(String password){ //Мощность пространства паролей
        int M = (int) Math.pow(ChangeAlphabet(password), password.length());
        return M;
    }

    public static double TimeP(String password){
        double a = Math.floor(Power(password)/m);
        if(Power(password)%m==0){
            a -=1;
        }
        double b = a*v;
        double c = (Power(password)/s);
        double time = c+b;
        return time;
    }
    public static String TimeVF(String password){
        int sec = 0;
        int min = 0;
        int hour = 0;
        int day = 0;
        int month = 0;
        int year = 0;
        int x = (int)TimeP(password);

        sec = x%60;
        x = (int) Math.floor(x/60);

        min = x%60;
        x = (int) Math.floor(x/60);

        hour = x%24;
        x = (int) Math.floor(x/24);

        day = x%30;
        x = (int) Math.floor(x/30);

        month = x%12;
        x = (int) Math.floor(x/12);

        year = x;






        String time = " ГГ: " + year + " ММ: " +month+ " ДД: " + day + " ЧЧ: " + hour + " ММ: " + min + " СС: " + sec;
        return time;
    }



    public static int ChangeAlphabet(String password){//Определение алфаыита
        int digit = 0;
        int UpperLetter = 0;
        int letter = 0;
        char[] pass = password.toCharArray();
        for (char c : pass) {
            if(Character.isLetterOrDigit(c) == false){
                return 95;

            }
            else if (Character.isDigit(c)){
                digit +=1;
            }
            else if (Character.isUpperCase(c)){
                UpperLetter+=1;
            }
            else  {
                letter +=1;
            }
        }
        if(digit==0 & UpperLetter!=0 & letter!=0){
            return 52;
        }
        else if(digit!=0 & (UpperLetter==0 | letter==0)){
            return 36;
        }

        else if(digit==0 & (UpperLetter==0 | letter==0)){
            return 26;

        }
        else{
            return 62;
        }
    }




}
