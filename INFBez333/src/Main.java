public class Main {
    public static void main(String[] args) {
        // Загружаем пользователей из файла
        LoginApp.loadUsers();
        new LoginApp.LoginWindow();

//        try {
//            // Пример: клик в (500, 300) и ввод слова "Hello123!"
//            BrutForce.clickAndType(500, 300, "Hello123!");
//            System.out.println("Готово!");
//        } catch (AWTException e) {
//            System.err.println("Ошибка: " + e.getMessage());
//        }
    }
}