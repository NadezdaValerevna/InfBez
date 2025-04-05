import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LoginApp {
    private static final String FILE_NAME = "users.txt";  // Файл для хранения пользователей

    private static Map<String, User> users = new HashMap<>();

//    private static int attemptsLeft = 3;





    // Окно входа
    static class LoginWindow extends JFrame {
        private JTextField usernameField;
        private JPasswordField passwordField;

        LoginWindow() {
            setTitle("Авторизация");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new GridLayout(3, 2));

            add(new JLabel("Логин:"));
            usernameField = new JTextField();

            add(usernameField);

            add(new JLabel("Пароль:"));
            passwordField = new JPasswordField();
            add(passwordField);

            JButton loginButton = new JButton("Войти");
            add(loginButton);

            JButton registerButton = new JButton("Регистрация");
            add(registerButton);

            loginButton.addActionListener(e -> authenticate());
            registerButton.addActionListener(e -> new RegistrationWindow());

            setVisible(true);
        }

        private void authenticate() {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (!users.containsKey(username)) {
                JOptionPane.showMessageDialog(this, "Пользователь не найден");
                return;
            }

            User user = users.get(username);
            if (user.isBlocked()) {
                JOptionPane.showMessageDialog(this, "Вы заблокированы");
                return;
            }

            if (!user.getPassword().equals(password)) {
//                attemptsLeft--;
//                if (attemptsLeft == 0) {
//                    user.block();
//                    saveUsers();
//                    JOptionPane.showMessageDialog(this, "Вы заблокированы из-за 3 неверных попыток!");
//                    System.exit(0);
//                } else {
//                    JOptionPane.showMessageDialog(this, "Неверный пароль! Осталось попыток: " + attemptsLeft);
//                }
//                JOptionPane.showMessageDialog(this, "Неверный пароль! Осталось попыток: ");
                return;

            }

            JOptionPane.showMessageDialog(this, "Добро пожаловать, " + username);
            new MainWindow(username, user.isAdmin());
            dispose();
        }
    }

    // Окно регистрации
    static class RegistrationWindow extends JFrame {
        private JTextField usernameField;
        private JPasswordField passwordField;

        RegistrationWindow() {
            setTitle("Регистрация");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(3, 2));

            add(new JLabel("Логин:"));
            usernameField = new JTextField();
            add(usernameField);

            add(new JLabel("Пароль:"));
            passwordField = new JPasswordField();
            add(passwordField);

            JButton registerButton = new JButton("Зарегистрироваться");
            add(registerButton);

            registerButton.addActionListener(e -> registerUser());

            setVisible(true);
        }

        private void registerUser() {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());



            if (users.containsKey(username)) {
                JOptionPane.showMessageDialog(this, "Пользователь уже существует");
                return;
            }

            users.put(username, new User(username, password, false));
            saveUsers();
            JOptionPane.showMessageDialog(this, "Регистрация успешна!");
            dispose();
        }
    }

    // Главное окно после входа
    static class MainWindow extends JFrame {
        MainWindow(String username, boolean isAdmin) {
            setTitle("Главное меню");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new GridLayout(2, 1));

            add(new JLabel("Вы вошли как: " + username));

            JButton logoutButton = new JButton("Выход");
            add(logoutButton);

            logoutButton.addActionListener(e -> {
                new LoginWindow();
                dispose();
            });

            if (isAdmin) {
                JButton manageUsersButton = new JButton("Управление пользователями");
                add(manageUsersButton);
                manageUsersButton.addActionListener(e -> new UserManagementWindow());
            }

            setVisible(true);
        }
    }

    // Окно управления пользователями (только для админа)
    static class UserManagementWindow extends JFrame {
        UserManagementWindow() {
            setTitle("Управление пользователями");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(users.size() + 1, 3));

            for (String username : users.keySet()) {
                User user = users.get(username);
                JLabel userLabel = new JLabel(username + (user.isBlocked() ? " (Заблокирован)" : ""));
                JButton blockButton = new JButton(user.isBlocked() ? "Разблокировать" : "Заблокировать");


                blockButton.addActionListener(e -> {
                    user.toggleBlock();
                    saveUsers();
                    dispose();
                    new UserManagementWindow();
                });

                add(userLabel);
                add(blockButton);
            }

            setVisible(true);
        }
    }

    // Класс пользователя
    static class User {
        private String username;
        private String password;
        private Integer pass;
        private boolean isAdmin;
        private boolean isBlocked;

        User(String username, String password, boolean isAdmin) {
            this.username = username;
            this.password = password;
            this.pass = pass;
            this.isAdmin = isAdmin;
            this.isBlocked = false;
        }

        String getPassword() {
            return password;
        }

        public Integer getPass() {
            return pass;
        }

        public void setPass(Integer pass) {
            this.pass = pass;
        }

        boolean isAdmin() {
            return isAdmin;
        }

        boolean isBlocked() {
            return isBlocked;
        }

        void block() {
            this.isBlocked = true;
        }

        void toggleBlock() {
            this.isBlocked = !this.isBlocked;
        }

        @Override
        public String toString() {
            return username + "," + password + "," + isAdmin + "," + isBlocked+ "," +pass;
        }

        static User fromString(String data) {
            String[] parts = data.split(",");
            return new User(parts[0], parts[1], Boolean.parseBoolean(parts[2]));
        }
    }

    // Загрузка пользователей из файла
    public static void loadUsers() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            users.put("admin", new User("admin", "admin123", true)); // Создаем админа по умолчанию
            saveUsers();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);
                users.put(user.username, user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Сохранение пользователей в файл
    private static void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User user : users.values()) {
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////



















}